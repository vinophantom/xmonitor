package com.vino.xmonitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


/**
 * @author phantom
 */
public class ControllerBase {


    String getOverAllDomString(HttpServletRequest request, HttpServletResponse response, ModelMap map, String content, ThymeleafViewResolver thymeleafViewResolver) {
        map.put("content", content);
        IContext iContext1 = new WebContext(request, response, request.getServletContext(), request.getLocale(), map);
        //结果输出
        return thymeleafViewResolver.getTemplateEngine().process("frame", iContext1);
    }


}