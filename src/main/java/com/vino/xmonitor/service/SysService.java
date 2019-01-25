package com.vino.xmonitor.service;

import com.vino.xmonitor.mcore.OsUtils;
import org.springframework.stereotype.Service;
import com.vino.xmonitor.bean.soft.System;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author phantom
 */
@Service
public class SysService {

    public System getSysInfo() throws UnknownHostException {
        Map<String, Object> sysInfo = OsUtils.getSysInfo();

        System system = new System(
                (String) sysInfo.get("computerName"),
                (String) sysInfo.get("sys"),
                (String) sysInfo.get("currentIP"),
                (String) sysInfo.get("version"),
                (String) sysInfo.get("arch"),
                (int)sysInfo.get("numOfCore"),
                (String) sysInfo.get("home"),
                (String) sysInfo.get("user")
        );
        return system;
    }

}
