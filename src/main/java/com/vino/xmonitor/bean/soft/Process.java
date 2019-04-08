package com.vino.xmonitor.bean.soft;

import java.util.Date;
/**
 * 
 * @author phantom
 */
public class Process {

    private long pid;
    private String user;
    private Date startTime;
    private long mem;
    private long rss;
    private long share;
    private Character state;
    private long cpuTime;
    private String name;
    private double cpuUsage;
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return long return the pid
     */
    public long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(long pid) {
        this.pid = pid;
    }

    /**
     * @return String return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return String return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return long return the mem
     */
    public long getMem() {
        return mem;
    }

    /**
     * @param mem the mem to set
     */
    public void setMem(long mem) {
        this.mem = mem;
    }

    /**
     * @return long return the rss
     */
    public long getRss() {
        return rss;
    }

    /**
     * @param rss the rss to set
     */
    public void setRss(long rss) {
        this.rss = rss;
    }

    /**
     * @return long return the share
     */
    public long getShare() {
        return share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(long share) {
        this.share = share;
    }

    /**
     * @return String return the state
     */
    public Character getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(Character state) {
        this.state = state;
    }

    /**
     * @return String return the cpuTime
     */
    public long getCpuTime() {
        return cpuTime;
    }

    /**
     * @param cpuTime the cpuTime to set
     */
    public void setCpuTime(long cpuTime) {
        this.cpuTime = cpuTime;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    public Process(){}


    /**
     * @return double return the cpuUsage
     */
    public double getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage the cpuUsage to set
     */
    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}