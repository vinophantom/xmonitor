package com.vino.xmonitor.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vino.xmonitor.result.Result;
import com.vino.xmonitor.service.ProcessService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;



@Controller
@RequestMapping("/process")
public class ProcessController extends ControllerBase {


    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    ServletContext servletContext;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ProcessService processService;


    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
    @ResponseBody
    public String process(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

        try {
            // List<Process>  list = processService.getProcessList();
            // map.put("procList", processService.getProcessVoList());
            IContext iContext = new WebContext(request, response, request.getServletContext(),request.getLocale());
            String content = thymeleafViewResolver.getTemplateEngine().process("process", iContext);

            String html = getOverAllDomString(request, response, map, content, thymeleafViewResolver);

            return html;
        } catch (Exception e) {
            //TODO: handle exception
            logger.error("服务器异常", e);
        }
        return null;
    }


    @RequestMapping(value="/{pid}" , method=RequestMethod.DELETE)
    @ResponseBody
    public Result requestMethodName(@PathVariable("pid") String pid) {
        try {
            pid = pid.trim();
            processService.killProc(pid);
        } catch (Exception e) {
            //TODO: handle exception
            return Result.error(null);
        }
        return Result.success("进程已结束！");
    }



}
