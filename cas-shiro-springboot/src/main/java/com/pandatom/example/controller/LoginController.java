package com.pandatom.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pandatom.example.entity.CommonConstant;
import com.pandatom.example.entity.Record;
import com.pandatom.example.entity.Result;
import com.pandatom.example.entity.Users;
import com.pandatom.example.service.IRecordService;
import com.pandatom.example.service.IRolesService;
import com.pandatom.example.service.IUsersService;
import com.pandatom.example.util.JWTUtils;

import com.pandatom.example.util.PasswordUtil;
import com.pandatom.example.util.RedisUtil;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月28日 3:19 PM
 */
@RestController
public class LoginController {


    @Autowired
    private IUsersService userService;

    @Autowired
    private IRolesService rolesService;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IRecordService recordService;

//    private static final  String salt="123";

    @PostMapping("/login")
    public Result login(@RequestBody Users loginUser, HttpServletResponse response) {
            QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
            usersQueryWrapper.eq("username",loginUser.getUsername());

            Users users = userService.getOne(usersQueryWrapper);

//        String userpassword = PasswordUtil.encrypt(loginUser.getUsername(), loginUser.getPassword(), salt);
//        String syspassword = loginUser.getPassword();
            Md5Hash md5Hash2 = new Md5Hash(loginUser.getPassword(),loginUser.getUsername());
            System.out.println("--->>>"+md5Hash2);

            if (users==null|| !users.getPassword().equals(md5Hash2.toString())){
                return Result.error("用户密码错误");
            }

            // 生成token
            Record record = new Record();
            record.setLoginTime(DateUtils.formatDate(new Date()));
            record.setUsername(users.getUsername());
            recordService.save(record);
            String token = JWTUtils.createToken(users.getUsername(),record.getLoginTime(), users.getPassword());

            // 设置token缓存有效时间
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
            redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JWTUtils.EXPIRE_TIME * 2 / 1000);

            users.setTaken(token);
            return Result.OK(users);
    }

    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if(token.isEmpty()) {
            return Result.error("退出登录失败！");
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
            return Result.ok("退出登录成功！");
        }else {
            return Result.error("Token无效!");
        }
    }
    @PostMapping(value = "/register")
    public Result<Object> register(@RequestBody Users loginUser, HttpServletResponse response) {
        System.out.println(loginUser.toString());

//        String passwordEncode = PasswordUtil.encrypt(loginUser.getUsername(), loginUser.getPassword(), salt);
//        loginUser.setPassword(passwordEncode);

        // shiro加密
        //注册的时候要对密码进行加密存储
//        Md5Hash md5Hash = new Md5Hash(loginUser.getPassword());
//        System.out.println("--->>>"+ md5Hash.toHex());

        //加盐加密
        // loginUser.getUsername() 做盐
        Md5Hash md5Hash2 = new Md5Hash(loginUser.getPassword(),loginUser.getUsername());
        System.out.println("--->>>"+md5Hash2);
        //加盐加密+多次hash
//        Md5Hash md5Hash3 = new Md5Hash(loginUser.getPassword(),salt,3);
//        System.out.println("--->>>"+md5Hash3);

        loginUser.setPassword(md5Hash2.toString());
        userService.save(loginUser);
        //用户退出逻辑
        return Result.OK("退出登录成功！");
    }



}
