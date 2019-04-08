package com.vino.xmonitor.service;


import com.vino.xmonitor.bean.hardware.Cpu;
import com.vino.xmonitor.bean.hardware.CpuCore;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.constant.DataNames;
import com.vino.xmonitor.mcore.OsUtils;
import com.vino.xmonitor.utils.StringUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author phantom
 */
@Service
public final class CpuService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public CpuCore[] getCpuCores(String... args) {
        return Cpu.getCpuCores();
    }

    public Cpu getCpu() throws SigarException {
        return Cpu.getInstance();
    }

}
