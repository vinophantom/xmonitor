package com.vino.xmonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author phantom
 */
@Controller
@RequestMapping(value = "/")
public class NavController extends ControllerBase {


    @Autowired
    ServletContext webContext;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;



    @RequestMapping(value = {"/","dashboard"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        IContext iContext = new WebContext(request, response, webContext);
        String content = thymeleafViewResolver.getTemplateEngine().process("maindashboard", iContext);
        String html = getOverAllDomString(request, response, map, content, thymeleafViewResolver);

        return html;
    }











//    @RequestMapping(value = {"test"}, method = {RequestMethod.GET})
//    public String index1(ModelMap map) {
//        map.addAttribute("headerContent", "<>");
//        map.addAttribute("divContent", "<div><b>12321321321321312213</b></div>");
//
//        return "background";
//    }

}
