package com.pandatom.example.controller;


import com.pandatom.example.service.IPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@Controller
@RequestMapping("permissions")
public class PermissionsController{

 	@Autowired
    IPermissionsService permissionsService;

    /**
     * <p>
     *  列表页
     * </p>
     * @return
     */
    @RequestMapping("main")
    public String main() {
        return "permissions/main";
    }



}

