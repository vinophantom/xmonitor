package com.vino.xmonitor.controller;

import java.net.UnknownHostException;

import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.result.Result;
import com.vino.xmonitor.service.SysService;

import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author phantom
 */
@RequestMapping("/sys")
@Controller
public class SysController  {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SysService sysService;

    @Autowired
    public SysController(SysService sysService) {
        this.sysService = sysService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Result<System> getSysInfo() {
        try {
            return Result.success(sysService.getSysInfo());
        } catch (UnknownHostException e) {
            logger.error("未知主机名！", e);
            return Result.error(CodeMsg.SERVER_ERROR);
        } catch (SigarException e) {
            logger.error("Sigar包出错！", e);
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }


}
