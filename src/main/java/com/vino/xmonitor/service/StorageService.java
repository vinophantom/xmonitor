package com.vino.xmonitor.service;

import com.vino.xmonitor.bean.hardware.Storage;
import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author phantom
 */
@Service
public class StorageService {

    public List<Storage> getStorages () throws SigarException {
        return OsUtils.getStorages();
    }
}
