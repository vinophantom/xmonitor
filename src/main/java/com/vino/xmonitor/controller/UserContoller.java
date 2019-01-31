package com.vino.xmonitor.controller;


import com.vino.xmonitor.bean.hardware.CpuCore;
import com.vino.xmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author phantom
 */
@Controller
@RequestMapping("/user")
public class UserContoller {

    @Autowired
    private UserService userService;
    // @Autowired
    // private CpuService cpuService;


    @RequestMapping(value={"/count"}, method = {RequestMethod.GET})
    @ResponseBody
    public int countUsers() {
        return userService.countUsers();
    }


    @RequestMapping("/info")
    @ResponseBody
    public List<CpuCore> cpuCores() {
//        try {
//            return cpuService.getCpuCores();
//
//        } catch (Exception e) {
//            return
//        }
        return null;
    }

//    @RequestMapping("/info")
//    @ResponseBody
//    public Result<User> info(Model model, User user) {
//        return Result.success(user);
//    }

}
