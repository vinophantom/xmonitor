package com.vino.xmonitor.service;


import com.vino.xmonitor.bean.NetSpeed;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.constant.DataNames;
import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

/**
 * @author phantom
 */
@Service
public class NetService {

    public NetSpeed getCurrNetSpeed () throws SigarException {
        return OsUtils.getCurrNetSpeed();
    }

    public NetSpeed getCacheNetSpeed () {
        return (NetSpeed) CacheHelper.getFromPersisCache(DataNames.NET_SPEED_NAME);
    }

}
