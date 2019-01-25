package com.vino.xmonitor.service;

import com.vino.xmonitor.bean.hardware.Memory;
import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

@Service
public class MemService {

    public Memory getMem() throws SigarException {
        try {
            Mem mem = OsUtils.getMem();
            return new Memory(
                mem.getTotal(),
                mem.getRam(),
                mem.getUsed(),
                mem.getFree(),
                mem.getActualUsed(),
                mem.getActualFree(),
                mem.getUsedPercent(),
                mem.getFreePercent()
            );
        } catch (SigarException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
