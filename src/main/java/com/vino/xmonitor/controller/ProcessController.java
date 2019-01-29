package com.vino.xmonitor.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import com.vino.xmonitor.bean.soft.Process;
import com.vino.xmonitor.service.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@Controller
@RequestMapping("/process")
public class ProcessController extends ControllerBase {


    @Autowired
    ServletContext servletContext;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ProcessService processService;


    @RequestMapping(value = {""}, method = {RequestMethod.GET})
    @ResponseBody
    public String process(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

        try {
            // List<Process>  list = processService.getProcessList();
            map.put("procList", processService.getProcessVoList());
            IContext iContext = new WebContext(request, response, request.getServletContext(),request.getLocale(), map);
            String content = thymeleafViewResolver.getTemplateEngine().process("process", iContext);

            String html = getOverAllDomString(request, response, map, content, thymeleafViewResolver);

            return html;    
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
        
    }
    
    


}
