package com.pandatom.example.expection;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年05月10日 2:27 PM
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv;
        System.out.println(ex instanceof UnauthenticatedException);
        //进行异常判断。如果捕获异常请求跳转。
        if (ex instanceof UnauthorizedException) {
            mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "你的级别还不够高,加油吧！少年。");
            return mv;
        }
        else if(ex instanceof UnauthenticatedException){
            mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "没有此权限！");
            return mv;
        }
        else {
            mv = new ModelAndView("/error/error");
            ex.printStackTrace();
            mv.addObject("msg", "我勒个去，页面被外星人挟持了!");
            return mv;

        }

    }
}

