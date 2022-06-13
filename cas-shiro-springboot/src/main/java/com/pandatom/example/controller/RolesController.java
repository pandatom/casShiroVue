package com.pandatom.example.controller;

import com.pandatom.example.entity.Roles;
import com.pandatom.example.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcly
 * @since 2022-03-28
 */
//@Api("角色查询")
@RestController
@RequestMapping("roles")
public class RolesController {

 	@Autowired
    IRolesService rolesService;

    /**
     * <p>
     *  列表页
     * </p>
     * @return
     */
    @RequestMapping("main")
    public String main() {
        return "roles/main";
    }


    @RequestMapping("list")
    public List<Roles> getList() {
        List<Roles> list = rolesService.list();
        return list;
    }

}

