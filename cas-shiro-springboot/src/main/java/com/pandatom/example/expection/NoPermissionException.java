package com.pandatom.example.expection;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年05月10日 2:52 PM
 */
import com.pandatom.example.entity.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;

/***
 * 添加全局拦截异常！
 */

@ControllerAdvice
public class NoPermissionException {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result handleShiroException(Exception ex) {
        return Result.error("授权异常--handleShiroException");
    }
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Result AuthorizationException(Exception ex) {
        return Result.error("授权异常--AuthorizationException");
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public Result AuthenticationException(Exception ex) {
        return Result.error("认证异常--AuthenticationException");
    }

    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public Result UnauthenticatedException(Exception ex) {
        return Result.error("认证异常--请重新登录");
    }
}
