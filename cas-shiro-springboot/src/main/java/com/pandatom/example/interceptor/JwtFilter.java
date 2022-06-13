package com.pandatom.example.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pandatom.example.entity.CommonConstant;
import com.pandatom.example.entity.Record;
import com.pandatom.example.service.IRecordService;
import com.pandatom.example.util.JWTUtils;
import com.pandatom.example.util.JwtToken;
import com.pandatom.example.util.SpringUtils;
import io.netty.util.internal.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月29日 3:56 PM
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            return false;
            //throw new AuthenticationException("Token失效，请重新登录", e);
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(CommonConstant.X_ACCESS_TOKEN);

        if(StringUtil.isNullOrEmpty(token)) return false;

        String time = JWTUtils.getTime(token);
        String username = JWTUtils.getUsername(token);

        IRecordService recordService = SpringUtils.getBean(IRecordService.class);
        List<Record> list = recordService.list();
        List<Record> record = recordService.list(new QueryWrapper<Record>().eq("username", username).orderByDesc("login_time").last(" limit 1"));
        if (null!=record&&record.size()>0){
            if (!record.get(0).getLoginTime().equals(time)){
                SecurityUtils.getSubject().logout();
                return false;
            }
        }

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("--prehandle login");
//
//        return true;
//    }

}
