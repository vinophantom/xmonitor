package com.vino.xmonitor.service;

import com.vino.xmonitor.bean.hardware.Memory;
import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

/**
 * @author phantom
 */
@Service
public class MemService {

    public Memory getMem() throws SigarException {
        return Memory.getInstance();
    }

}
