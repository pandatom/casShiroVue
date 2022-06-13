package com.pandatom.example.controller;


import com.pandatom.example.service.IRpsService;
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
@RequestMapping("rps")
public class RpsController   {

 	@Autowired
    IRpsService rpsService;

    /**
     * <p>
     *  列表页
     * </p>
     * @return
     */
    @RequestMapping("main")
    public String main() {
        return "rps/main";
    }



}

