package com.vino.xmonitor.bean;

public class NetSpeed {


    private double upLinkNetSpeed;

    public double getUpLinkNetSpeed() {
        return upLinkNetSpeed;
    }

    public NetSpeed() {
    }

    public void setUpLinkNetSpeed(double upLinkNetSpeed) {
        this.upLinkNetSpeed = upLinkNetSpeed;
    }

    public double getDownLinkNetSpeed() {
        return downLinkNetSpeed;
    }

    public void setDownLinkNetSpeed(double downLinkNetSpeed) {
        this.downLinkNetSpeed = downLinkNetSpeed;
    }

    public NetSpeed(double upLinkNetSpeed, double downLinkNetSpeed) {
        this.upLinkNetSpeed = upLinkNetSpeed;
        this.downLinkNetSpeed = downLinkNetSpeed;
    }

    private double downLinkNetSpeed;
}
