package com.vino.xmonitor.bean.soft;

/**
 * @author phantom
 */
public class System {
    private String computerName;
    private String sys;
    private String currentIP;
    private String version;
    private String arch;
    private int numOfCores;
    private String home;
    private String user;

    public System(String computerName, String sys, String currentIP, String version, String arch, int numOfCores, String home, String user) {
        this.computerName = computerName;
        this.sys = sys;
        this.currentIP = currentIP;
        this.version = version;
        this.arch = arch;
        this.numOfCores = numOfCores;
        this.home = home;
        this.user = user;
    }

    public System() {
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getCurrentIP() {
        return currentIP;
    }

    public void setCurrentIP(String currentIP) {
        this.currentIP = currentIP;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public int getNumOfCores() {
        return numOfCores;
    }

    public void setNumOfCores(int numOfCore) {
        this.numOfCores = numOfCore;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
