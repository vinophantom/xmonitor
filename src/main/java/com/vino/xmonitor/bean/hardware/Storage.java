package com.vino.xmonitor.bean.hardware;

import com.vino.xmonitor.mcore.OsUtils;

/**
 * @author phantom
 */
public class Storage {
    private String dirName = null;
    private String devName = null;
    private String typeName = null;
    private String sysTypeName = null;
    private String options = null;
    // 0 TYPE_UNKNOWN ：未知
    // 1 TYPE_NONE
    // 2 TYPE_LOCAL_DISK : 本地硬盘
    // 3 TYPE_NETWORK ：网络
    // 4 TYPE_RAM_DISK ：闪存
    // 5 TYPE_CDROM ：光驱
    // 6 TYPE_SWAP ：页面交换
    private int type = 0;
    private long flags = 0L;

    private long total = 0L;
    private long free = 0L;
    private long used = 0L;
    private long avail = 0L;
    private long files = 0L;
    private long freeFiles = 0L;
    private long diskReads = 0L;
    private long diskWrites = 0L;
    private long diskReadBytes = 0L;
    private long diskWriteBytes = 0L;
    private double diskQueue = 0.0D;
    private double diskServiceTime = 0.0D;
    private double usePercent = 0.0D;

    public static void update() {
        OsUtils.getMem();
    }

    public Storage(String dirName, String devName, String typeName, String sysTypeName, String options, int type, long flags) {
        this.dirName = dirName;
        this.devName = devName;
        this.typeName = typeName;
        this.sysTypeName = sysTypeName;
        this.options = options;
        this.type = type;
        this.flags = flags;
    }



    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSysTypeName() {
        return sysTypeName;
    }

    public void setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(long flags) {
        this.flags = flags;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getAvail() {
        return avail;
    }

    public void setAvail(long avail) {
        this.avail = avail;
    }

    public long getFiles() {
        return files;
    }

    public void setFiles(long files) {
        this.files = files;
    }

    public long getFreeFiles() {
        return freeFiles;
    }

    public void setFreeFiles(long freeFiles) {
        this.freeFiles = freeFiles;
    }

    public long getDiskReads() {
        return diskReads;
    }

    public void setDiskReads(long diskReads) {
        this.diskReads = diskReads;
    }

    public long getDiskWrites() {
        return diskWrites;
    }

    public void setDiskWrites(long diskWrites) {
        this.diskWrites = diskWrites;
    }

    public long getDiskReadBytes() {
        return diskReadBytes;
    }

    public void setDiskReadBytes(long diskReadBytes) {
        this.diskReadBytes = diskReadBytes;
    }

    public long getDiskWriteBytes() {
        return diskWriteBytes;
    }

    public void setDiskWriteBytes(long diskWriteBytes) {
        this.diskWriteBytes = diskWriteBytes;
    }

    public double getDiskQueue() {
        return diskQueue;
    }

    public void setDiskQueue(double diskQueue) {
        this.diskQueue = diskQueue;
    }

    public double getDiskServiceTime() {
        return diskServiceTime;
    }

    public void setDiskServiceTime(double diskServiceTime) {
        this.diskServiceTime = diskServiceTime;
    }

    public double getUsePercent() {
        return usePercent;
    }

    public void setUsePercent(double usePercent) {
        this.usePercent = usePercent;
    }

    public Storage(String dirName, String devName, String typeName, String sysTypeName, String options, int type, long flags, long total, long free, long used, long avail, long files, long freeFiles, long diskReads, long diskWrites, long diskReadBytes, long diskWriteBytes, double diskQueue, double diskServiceTime, double usePercent) {
        this.dirName = dirName;
        this.devName = devName;
        this.typeName = typeName;
        this.sysTypeName = sysTypeName;
        this.options = options;
        this.type = type;
        this.flags = flags;
        this.total = total;
        this.free = free;
        this.used = used;
        this.avail = avail;
        this.files = files;
        this.freeFiles = freeFiles;
        this.diskReads = diskReads;
        this.diskWrites = diskWrites;
        this.diskReadBytes = diskReadBytes;
        this.diskWriteBytes = diskWriteBytes;
        this.diskQueue = diskQueue;
        this.diskServiceTime = diskServiceTime;
        this.usePercent = usePercent;
    }

    public Storage() {
    }
}
