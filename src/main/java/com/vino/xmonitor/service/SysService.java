package com.vino.xmonitor.service;

import java.net.UnknownHostException;
import java.util.Map;

import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.cache.CacheHelper;
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
        return System.getInstance();
    }

}
