package com.vino.xmonitor.controller;

import com.vino.xmonitor.exception.GlobalException;
import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.result.Result;
import com.vino.xmonitor.service.UserService;
import com.vino.xmonitor.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author root
 */
@Controller
public class LoginController {

    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = {
                LOGIN_PATH,
                LOGIN_PATH + "/"
            },
            method = {
                    RequestMethod.GET
            },
            headers = {
                    "accept=text/html**"
            })
    public ModelAndView login(HttpServletRequest request) {
//        Enumeration<String> headers =  request.getHeaderNames();
        return new ModelAndView("login");
    }

    @RequestMapping(
            value = {
                    LOGIN_PATH,
                    LOGIN_PATH + "/"
            },
            method = {
                    RequestMethod.POST
            })
    @ResponseBody
    public Result toLogin(HttpServletResponse response, LoginVo loginVo) {
        try {
            return Result.success(userService.login(response, loginVo));
        } catch (GlobalException e) {
            return Result.error(e.getCodeMsg());
        }
    }


    @RequestMapping(
            value = {
                    LOGOUT_PATH,
                    LOGOUT_PATH + "/"
            },
            method = {
                    RequestMethod.GET
            },
            headers = {
                    "accept=text/html**"
            })
    public ModelAndView logout(HttpServletResponse response) {
        userService.logout(response);
        return new ModelAndView("login");
    }


}
