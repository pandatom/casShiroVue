package com.pandatom.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandatom.example.entity.Users;
import com.pandatom.example.service.IUsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@RestController
@RequestMapping("users")
public class UsersController  {

 	@Autowired
    IUsersService usersService;

    /**
     * <p>
     *  列表页
     * </p>
     * @return
     */
    @RequestMapping("main")
    public String main() {
        return "users/main";
    }


//    @GetMapping("/login")
//    public String login(Users user) {
//        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
//            return "请输入用户名和密码！";
//        }
//        //用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
//                user.getUsername(),
//                user.getPassword()
//        );
//        try {
//            //进行验证，这里可以捕获异常，然后返回对应信息
//            subject.login(usernamePasswordToken);
////            subject.checkRole("admin");
////            subject.checkPermissions("query", "add");
//        } catch (UnknownAccountException e) {
//            System.out.println("用户名不存在！");
//            return "用户名不存在！";
//        } catch (AuthenticationException e) {
//            System.out.println("账号或密码错误！");
//            return "账号或密码错误！";
//        } catch (AuthorizationException e) {
//            System.out.println("没有权限！");
//            return "没有权限";
//        }
//        return "login success";
//    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @GetMapping("/index")
    public String index() {
        return "index success!";
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }



}

