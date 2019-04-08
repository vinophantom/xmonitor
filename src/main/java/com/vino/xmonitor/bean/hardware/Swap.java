package com.vino.xmonitor.bean.hardware;

public class Swap {
    private long total = 0L;
    private long used = 0L;
    private long free = 0L;
    private long pageIn = 0L;
    private long pageOut = 0L;

    public Swap() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public long getPageIn() {
        return pageIn;
    }

    public void setPageIn(long pageIn) {
        this.pageIn = pageIn;
    }

    public long getPageOut() {
        return pageOut;
    }

    public void setPageOut(long pageOut) {
        this.pageOut = pageOut;
    }

    public Swap(long total, long used, long free, long pageIn, long pageOut) {
        this.total = total;
        this.used = used;
        this.free = free;
        this.pageIn = pageIn;
        this.pageOut = pageOut;
    }
}
