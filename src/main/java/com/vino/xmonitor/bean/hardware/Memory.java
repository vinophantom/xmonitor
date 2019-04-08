package com.vino.xmonitor.bean.hardware;

import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.Mem;

/**
 * @author phantom
 */
public class Memory {

    private long total = 0L;
    private long ram = 0L;
    private long used = 0L;
    private long free = 0L;
    private long actualUsed = 0L;
    private long actualFree = 0L;
    private double usedPercent = 0.0D;
    private double freePercent = 0.0D;

    private static Memory instance;

    public static Memory getInstance() {
        return instance;
    }

    public static void update() {
        Mem mem = OsUtils.getMem();
        instance = new Memory(
                mem.getTotal(),
                mem.getRam(),
                mem.getUsed(),
                mem.getFree(),
                mem.getActualUsed(),
                mem.getActualFree(),
                mem.getUsedPercent(),
                mem.getFreePercent()
        );
    }

    static {
        update();
    }


    public Memory() {
    }


    public Memory(long total, long ram, long used, long free, long actualUsed, long actualFree, double usedPercent, double freePercent) {
        this.total = total;
        this.ram = ram;
        this.used = used;
        this.free = free;
        this.actualUsed = actualUsed;
        this.actualFree = actualFree;
        this.usedPercent = usedPercent;
        this.freePercent = freePercent;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public long getActualUsed() {
        return actualUsed;
    }

    public void setActualUsed(long actualUsed) {
        this.actualUsed = actualUsed;
    }

    public long getActualFree() {
        return actualFree;
    }

    public void setActualFree(long actualFree) {
        this.actualFree = actualFree;
    }

    public double getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }

    public double getFreePercent() {
        return freePercent;
    }

    public void setFreePercent(double freePercent) {
        this.freePercent = freePercent;
    }
}

