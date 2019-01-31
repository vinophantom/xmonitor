package com.vino.xmonitor.schedule;


import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.constant.DataNames;
import com.vino.xmonitor.service.CpuService;
import com.vino.xmonitor.service.NetService;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author phantom
 */
@Component
public class DataLoadScheduleService {
    private final static long ONE_SECOND = 1000L;
    private final static long THREE_SECOND = 3000L;


    private final CpuService cpuService;
    private final NetService netService;

    @Autowired
    public DataLoadScheduleService(CpuService cpuService, NetService netService) {
        this.cpuService = cpuService;
        this.netService = netService;
    }

    @Scheduled(fixedRate = ONE_SECOND)
    public void loadCpuSituation () throws SigarException {
        CacheHelper.deleteFromPersisCache(DataNames.CPU_CORES_NAME);
        CacheHelper.saveToPersisCache(DataNames.CPU_CORES_NAME, cpuService.getCpuCoresFromSys());
    }

    @Scheduled(fixedDelay = THREE_SECOND)
    public void loadNetSpeed () throws SigarException {
        CacheHelper.deleteFromPersisCache(DataNames.NET_SPEED_NAME);
        CacheHelper.saveToPersisCache(DataNames.NET_SPEED_NAME, netService.getCurrNetSpeed());
    }

}
