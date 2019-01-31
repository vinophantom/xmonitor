package com.vino.xmonitor.service;

import java.net.UnknownHostException;
import java.util.Map;

import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.mcore.OsUtils;

import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author phantom
 */
@Service
public class SysService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public System getSysInfo() throws UnknownHostException, SigarException {
        Map<String, Object> sysInfo = OsUtils.getSysInfo();
        try{
            return new System(
                    (String) sysInfo.get("computerName"),
                    (String) sysInfo.get("sys"),
                    (String) sysInfo.get("currentIP"),
                    (String) sysInfo.get("version"),
                    (String) sysInfo.get("arch"),
                    (int)sysInfo.get("numOfCore"),
                    (String) sysInfo.get("home"),
                    (String) sysInfo.get("user")
            );
        } catch (Exception e) {
            logger.error("com.vino.xmonitor.bean.soft.System 构造方法出错");
            throw e;
        }
    }

}
