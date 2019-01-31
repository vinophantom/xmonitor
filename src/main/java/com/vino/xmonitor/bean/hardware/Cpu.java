package com.vino.xmonitor.bean.hardware;


/**
 * @author phantom
 */
public class Cpu {


    private int mhz;
    private String vendor;
    private String model;
    private int totalCores;
    private long processCpuTime;

    public Cpu(int mhz, String vendor, String model, int totalCores, long processCpuTime) {
        this.mhz = mhz;
        this.vendor = vendor;
        this.model = model;
        this.totalCores = totalCores;
        this.processCpuTime = processCpuTime;
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
