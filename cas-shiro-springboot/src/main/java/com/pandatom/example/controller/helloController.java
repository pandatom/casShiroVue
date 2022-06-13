package com.pandatom.example.controller;

import com.pandatom.example.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月25日 9:59 AM
 */
@Api(tags="任务用户状态关联表")
@RestController
@RequestMapping("hello")
//@CrossOrigin
public class helloController {

    @RequestMapping("/test")
    public Result<?> test(){
        return Result.OK("hello world!----系统1111");
    }

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value={"user"}, method= RequestMethod.GET)
    public String getUserList() {
        return "啊啊啊啊啊";
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    })
    @RequestMapping(value = "/swaggerTest/{id}",method= RequestMethod.GET)
    public String swaggerTest(@PathVariable("id") String id) {
        System.out.println(id+"----"+id);
        return "用户编码是："+id;
    }

    @RequiresPermissions("sys:user:list")
    @RequestMapping("/perm")
    public String testPer(){
        return "bbbb";
    }
}
