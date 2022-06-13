package com.pandatom.example.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pandatom.example.entity.CommonConstant;
import com.pandatom.example.entity.Record;
import com.pandatom.example.entity.Result;
import com.pandatom.example.entity.Users;
import com.pandatom.example.service.IRecordService;
import com.pandatom.example.service.IUsersService;
import com.pandatom.example.util.CASServiceUtil;
import com.pandatom.example.util.JWTUtils;
import com.pandatom.example.util.RedisUtil;
import com.pandatom.example.util.XmlUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * CAS单点登录客户端登录认证
 * </p>
 *
 * @Author zhoujf
 * @since 2018-12-20
 */
@Slf4j
@RestController
public class CasClientController {

    @Autowired
    private IUsersService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IRecordService recordService;
    @Value("${cas.server.url}")
    private String serverurl;
//    @Value("${cas.project.url}")
//    private String projecturl;


    @RequestMapping("/validateLogin")
    public Object validateLogin(@RequestParam(name="ticket") String ticket,
                                @RequestParam(name="service") String service,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        Result<JSONObject> result = new Result<JSONObject>();
        log.info("Rest api login.");
        try {
            String validateUrl = serverurl+"/p3/serviceValidate";
            String res = CASServiceUtil.getSTValidate(validateUrl, ticket, service);
            log.info("res."+res);
            final String error = XmlUtils.getTextForElement(res, "authenticationFailure");
                if(!StringUtils.isEmpty(error)) {
                throw new Exception(error);
            }
            final String principal = XmlUtils.getTextForElement(res, "user");
            if (StringUtils.isEmpty(principal)) {
                throw new Exception("No principal was found in the response from the CAS server.");
            }
            log.info("-------token----username---"+principal);
            //1. 校验用户是否有效
            Users users = userService.queryUserByUsername(principal);

            if(users==null) {
//                response.sendRedirect("http://localhost:3000/type=0");
                return result;
            }
            // 保存登录记录
            Record record = new Record();
            record.setLoginTime(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
            record.setUsername(users.getUsername());
            recordService.save(record);

            String taken = JWTUtils.createToken(users.getUsername(), record.getLoginTime(),users.getPassword());

            // 设置超时时间
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + taken, taken);
            redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + taken, JWTUtils.EXPIRE_TIME*2 / 1000);
            JSONObject obj = new JSONObject();
            obj.put("taken",taken);
            obj.put("userInfo",users);
                result.setResult(obj);
//            response.sendRedirect("http://localhost:3000/taken=token&type=1");
            result.success("登录成功");
        } catch (Exception e) {
            //e.printStackTrace();
            result.error500(e.getMessage());
        }
        return new HttpEntity<>(result);
    }

    @RequestMapping(value = "/casLogout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if(token.isEmpty()) {
            return "退出登录失败！";
        }
        String username = JWTUtils.getUsername(token);
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("username",username);

        Users sysUser = userService.getOne(usersQueryWrapper);
        if(sysUser!=null) {
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
//            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
//            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();

            return "http://localhost:8080/cas/logout?service=http://localhost:3000/#loginout?taken=123";
        }else {
            return "http://localhost:8080/cas/logout?service=http://localhost:3000/";
        }
    }


}
