package com.pandatom.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandatom.example.entity.Urs;
import com.pandatom.example.service.IUrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@Controller
@RequestMapping("urs")
public class UrsController  {

 	@Autowired
    IUrsService ursService;

    /**
     * <p>
     *  列表页
     * </p>
     * @return
     */
    @RequestMapping("main")
    public String main() {
        return "urs/main";
    }


}

