package com.vino.xmonitor;

import com.vino.xmonitor.bean.soft.System;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Date;

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
    private void loadSysInfo (SysService sysService) {
        try {
            System system = sysService.getSysInfo();
            CacheHelper.saveToPersisCache("current_system", system.getSys());
            CacheHelper.saveToPersisCache("num_of_cores", system.getNumOfCores());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}

