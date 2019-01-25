package com.vino.xmonitor.controller;

import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.result.Result;
import com.vino.xmonitor.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;

@RequestMapping("/sys")
@Controller
public class SysController {

    @Autowired
    private SysService sysService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Result getSysInfo() {
        try {
            return Result.success(sysService.getSysInfo());
        } catch (UnknownHostException e) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
