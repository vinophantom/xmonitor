package com.vino.xmonitor.filter;

import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.config.WebConfig;
import com.vino.xmonitor.controller.LoginController;
import com.vino.xmonitor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author root
 */
public class LoginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session
//        HttpSession session = request.getSession(true);
        //判断用户ID是否存在，不存在就跳转到登录界面

//        if(session.getAttribute(WebConfig.LOGIN_USER) == null){
//            logger.info("------跳转到login页面-----");
//            response.sendRedirect(request.getContextPath()+"/index");
//            return false;
//        }else{
//            session.setAttribute(WebConfig.LOGIN_USER, session.getAttribute(WebConfig.LOGIN_USER));
//            return true;
//        }


        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect(LoginController.LOGIN_PATH);
            return false;
        }
        for (Cookie c : cookies) {
            if (UserService.COOKIE_NAME_TOKEN.equals(c.getName()) && CacheHelper.getFromLoginCache(c.getValue()) != null) {
                return true;
            }
        }
        response.sendRedirect(LoginController.LOGIN_PATH);
        return false;
    }
}
