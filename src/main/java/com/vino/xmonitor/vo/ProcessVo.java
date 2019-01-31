package com.vino.xmonitor.vo;


public class ProcessVo {
    
    private String pid;
    private String user;
    private String startTime;
    private String mem;
    private String rss;
    private String share;
    private String state;
    private String cpuTime;
    private String name;
    private String cpuUsage;
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return String return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
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
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return String return the mem
     */
    public String getMem() {
        return mem;
    }

    /**
     * @param mem the mem to set
     */
    public void setMem(String mem) {
        this.mem = mem;
    }

    /**
     * @return String return the rss
     */
    public String getRss() {
        return rss;
    }

    /**
     * @param rss the rss to set
     */
    public void setRss(String rss) {
        this.rss = rss;
    }

    /**
     * @return String return the share
     */
    public String getShare() {
        return share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(String share) {
        this.share = share;
    }

    /**
     * @return String return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return String return the cpuTime
     */
    public String getCpuTime() {
        return cpuTime;
    }

    /**
     * @param cpuTime the cpuTime to set
     */
    public void setCpuTime(String cpuTime) {
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


    /**
     * @return String return the cpuUsage
     */
    public String getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage the cpuUsage to set
     */
    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}