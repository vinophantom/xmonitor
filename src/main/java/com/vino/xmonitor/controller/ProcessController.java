package com.vino.xmonitor.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.result.Result;
import com.vino.xmonitor.service.ProcessService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;



/**
 * @author phantom
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends ControllerBase {


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private final ThymeleafViewResolver thymeleafViewResolver;

    private final ProcessService processService;

    @Autowired
    public ProcessController(ThymeleafViewResolver thymeleafViewResolver, ProcessService processService) {
        this.thymeleafViewResolver = thymeleafViewResolver;
        this.processService = processService;
    }


    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET})
    @ResponseBody
    public String process(HttpServletRequest request, HttpServletResponse response, ModelMap map) {



        try {
            IContext iContext = new WebContext(request, response, request.getServletContext(),request.getLocale());

            String content = thymeleafViewResolver.getTemplateEngine().process("process", iContext);

            return getOverAllDomString(request, response, map, content, thymeleafViewResolver);
        } catch (Exception e) {
            //TODO: handle exception
            logger.error("服务器异常", e);
        }
        return null;
    }


    @RequestMapping(value="/{pid}" , method=RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> requestMethodName(@PathVariable("pid") String pid) {
        try {
            pid = pid.trim();
            processService.killProc(pid);
        } catch (Exception e) {
            //TODO: handle exception
            logger.error(e.getMessage(), e);
            return Result.error(new CodeMsg(500, e.getMessage()));
        }
        return Result.success("进程已结束！");
    }

}
