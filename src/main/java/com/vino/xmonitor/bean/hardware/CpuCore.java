package com.vino.xmonitor.bean.hardware;


/**
 * @author phantom
 */
public class CpuCore {


    private double userUsage;
    private double sytemUsage;
    private double nice;
    private double wait;
    private double idle;
    private double irq;
    private double softirq;
    private double stolen;

    public CpuCore() {
    }

    public CpuCore(double userUsage, double sytemUsage, double nice, double wait, double idle, double irq, double softirq, double stolen, double combined) {
        this.userUsage = userUsage;
        this.sytemUsage = sytemUsage;
        this.nice = nice;
        this.wait = wait;
        this.idle = idle;
        this.irq = irq;
        this.softirq = softirq;
        this.stolen = stolen;
        this.combined = combined;
    }

    private double combined;

    public double getUserUsage() {
        return userUsage;
    }

    public void setUserUsage(double userUsage) {
        this.userUsage = userUsage;
    }

    public double getSytemUsage() {
        return sytemUsage;
    }

    public void setSytemUsage(double sytemUsage) {
        this.sytemUsage = sytemUsage;
    }

    public double getNice() {
        return nice;
    }

    public void setNice(double nice) {
        this.nice = nice;
    }

    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getIdle() {
        return idle;
    }

    public void setIdle(double idle) {
        this.idle = idle;
    }

    public double getIrq() {
        return irq;
    }

    public void setIrq(double irq) {
        this.irq = irq;
    }

    public double getSoftirq() {
        return softirq;
    }

    public void setSoftirq(double softirq) {
        this.softirq = softirq;
    }

    public double getStolen() {
        return stolen;
    }

    public void setStolen(double stolen) {
        this.stolen = stolen;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }
}
