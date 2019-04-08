package com.vino.xmonitor.bean.hardware;


import com.vino.xmonitor.mcore.OsUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;

import java.util.Arrays;

/**
 * @author phantom
 */
public class Cpu {


    private int mhz;
    private String vendor;
    private String model;
    private int totalCores;
    private long processCpuTime;

    static {

    }

    private static Cpu instance;
    private static CpuCore[] cpuCores;

    static {
        update();
        cpuCores = new CpuCore[instance.totalCores];
        updateCpuCores();
    }

    public static Cpu getInstance() {
        return instance;
    }

    public static CpuCore[] getCpuCores() {
        return cpuCores;
    }
    public static synchronized void updateCpuCores() {
        CpuPerc[] cs = OsUtils.getCpuPerc();
        CpuPerc c;
        for (int i = 0; i < cpuCores.length; i++) {
            assert cs != null;
            c = cs[i];
            cpuCores[i] = new CpuCore(
                    c.getUser(),
                    c.getSys(),
                    c.getNice(),
                    c.getWait(),
                    c.getIdle(),
                    c.getIrq(),
                    c.getSoftIrq(),
                    c.getStolen(),
                    c.getCombined()
            );
        }
        c = null;
    }
    public static synchronized void update() {
        CpuInfo cpuInfo = OsUtils.getCpu();
        instance = new Cpu(cpuInfo.getMhz(), cpuInfo.getVendor(), cpuInfo.getModel(), cpuInfo.getTotalCores());
    }

    public int getMhz() {
        return mhz;
    }

    public void setMhz(int mhz) {
        this.mhz = mhz;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTotalCores() {
        return totalCores;
    }

    public void setTotalCores(int totalCores) {
        this.totalCores = totalCores;
    }

    public Cpu(int mhz, String vendor, String model, int totalCores) {
        this.mhz = mhz;
        this.vendor = vendor;
        this.model = model;
        this.totalCores = totalCores;
    }

    public Cpu() {
    }
}
