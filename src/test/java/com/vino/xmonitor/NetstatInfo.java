package com.vino.xmonitor;

public class NetstatInfo {
    private String protocol;
    private String localAddress;
    private String externalAddress;
    private int pid;

    public NetstatInfo() {}
    public NetstatInfo(String protocol, String localAddress, String externalAddress, int pid) {
        this.protocol = protocol;
        this.localAddress = localAddress;
        this.externalAddress = externalAddress;
        this.pid = pid;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getExternalAddress() {
        return externalAddress;
    }

    public void setExternalAddress(String externalAddress) {
        this.externalAddress = externalAddress;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
