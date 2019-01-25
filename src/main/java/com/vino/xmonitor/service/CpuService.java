package com.vino.xmonitor.service;


import com.vino.xmonitor.bean.hardware.Cpu;
import com.vino.xmonitor.bean.hardware.CpuCore;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.constant.DataNames;
import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author phantom
 */
@Service
public class CpuService {


    public List<CpuCore> getCpuCores() throws SigarException {
        try {
            List<CpuCore> list = (List<CpuCore>) CacheHelper.getFromPersisCache(DataNames.CPU_CORES_NAME);
            if (list == null) {
                return getCpuCoresFromSys();
            } else {
//                System.out.println("Cache有值了=======================");
                return list;
            }
        } catch (SigarException e) {
            e.printStackTrace();
            throw e;
        }

    }


    public List<CpuCore> getCpuCoresFromSys() throws SigarException {
        List<CpuCore> res = new ArrayList<>();
        CpuPerc[] cs = OsUtils.getCpuPerc();
        Arrays.stream(cs).forEach(c -> {
            res.add(new CpuCore(
                    c.getUser(),
                    c.getSys(),
                    c.getNice(),
                    c.getWait(),
                    c.getIdle(),
                    c.getIrq(),
                    c.getSoftIrq(),
                    c.getStolen(),
                    c.getCombined()
            ));
        });
        return res;
    }

    public Cpu getCpu() throws SigarException {
        try {
            CpuInfo cpuInfo = OsUtils.getCpu();
            return new Cpu(cpuInfo.getMhz(), cpuInfo.getVendor(), cpuInfo.getModel(), cpuInfo.getTotalCores());
        } catch (SigarException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
