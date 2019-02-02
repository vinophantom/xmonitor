package com.vino.xmonitor;

import java.net.UnknownHostException;
import java.util.Map;

import com.vino.xmonitor.bean.hardware.Memory;
import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.mcore.OsUtils;
import com.vino.xmonitor.service.MemService;
import com.vino.xmonitor.service.SysService;

import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author phantom
 */
@Component
@Order(value = 1)
public class InitService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void run(ApplicationArguments args) {}



    @Autowired
    private void loadSysInfo (SysService sysService, MemService memService) {
        try {
            logger.info("Loading Cache(System Main Information)...");
            Memory mem = memService.getMem();
            CacheHelper.saveToPersisCache("total_mem", mem.getTotal());
        } catch (SigarException e) {
            logger.error("Loading Info Error", e);
        } catch (Exception e) {
            logger.error("Loading Cache Error", e);
        }

    }
}

