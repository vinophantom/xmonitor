package com.vino.xmonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author phantom
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;



    @RequestMapping(value = {"/","dashboard"}, method = {RequestMethod.GET})
    public String index(Model model) {
        //结果输出
        return "index";
    }
}
