package com.vino.xmonitor;

import java.net.UnknownHostException;

import com.vino.xmonitor.bean.hardware.Memory;
import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.service.MemService;
import com.vino.xmonitor.service.SysService;

import org.hyperic.sigar.SigarException;
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
public class InitService  implements ApplicationRunner {



    @Override
    public void run(ApplicationArguments args) throws Exception {

    }



    @Autowired
    private void loadSysInfo (SysService sysService, MemService memService) {
        try {
            System system = sysService.getSysInfo();
            Memory mem = memService.getMem();
            CacheHelper.saveToPersisCache("current_system", system.getSys());
            CacheHelper.saveToPersisCache("num_of_cores", system.getNumOfCores());
            CacheHelper.saveToPersisCache("total_mem", mem.getTotal());
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SigarException e) {
            e.printStackTrace();
        }

    }
}

