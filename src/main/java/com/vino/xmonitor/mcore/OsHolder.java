package com.vino.xmonitor.mcore;


import org.hyperic.sigar.*;

public class OsHolder {

    private static Runtime runtime = Runtime.getRuntime();

    private static final OperatingSystem os = OperatingSystem.getInstance();

    //////////////////////////////////////////////Software/////////////////////////////////////////////////////////


    //////////////////////////////////////////////Hardware////////////////////////////////////////////////////////

    /**
     * 获取内存实例
     *
     * @return
     * @throws SigarException
     */
    protected static Mem getMem() throws SigarException {
        return SigarHolder.getSigarInstance().getMem();
    }
    ////////////////////////////////////////////CPU//////////////////////////////////
    /**
     * 获取每块cpu的基本信息，频率、生产商、
     *
     * @return
     * @throws SigarException
     */
    protected static CpuInfo[] getCpuInfo() throws SigarException {
        return SigarHolder.getSigarInstance().getCpuInfoList();
    }


    /**
     * 获取所有核心的信息
     *
     * @return
     * @throws SigarException
     */
    protected static CpuPerc[] getCpuPercList() throws SigarException {
        return SigarHolder.getSigarInstance().getCpuPercList();
    }


    /**
     * @return
     * @throws SigarException
     */
    protected static CpuPerc getCpuPerc() throws SigarException {
        return SigarHolder.getSigarInstance().getCpuPerc();
    }
    ///////////////////////////////////////文件系统、磁盘 ////////////////////////////////////
    /**
     * @return
     * @throws SigarException
     */
    protected static FileSystem[] getFileSystem() throws SigarException {
        return SigarHolder.getSigarInstance().getFileSystemList();
    }


    /**
     * @param fsname
     * @return
     * @throws SigarException
     */
    protected static FileSystemUsage getFileSystemUsageByName(String fsname) throws SigarException {
        return SigarHolder.getSigarInstance().getFileSystemUsage(fsname);
    }



    /////////////////////////////////////////////////网络/////////////////////////////////////////
    /**
     * 获取网络接口集合
     * @return
     * @throws SigarException
     */
    protected static String[] getInterfaceList () throws SigarException {
        return SigarHolder.getSigarInstance().getNetInterfaceList();
    }

    /**
     *
     * @param name
     * @return
     * @throws SigarException
     */
    protected static NetInterfaceConfig getNetInterfaceConfigByName (String name) throws SigarException {
        return SigarHolder.getSigarInstance().getNetInterfaceConfig(name);
    }

    /**
     *
     * @return
     * @throws SigarException
     */
    protected static NetInterfaceConfig getNetInterfaceConfig () throws SigarException {
        return SigarHolder.getSigarInstance().getNetInterfaceConfig();
    }


    /**
     *
     * @param name
     * @return
     * @throws SigarException
     */
    protected static NetInterfaceStat getNetInterfaceStatByName (String name) throws SigarException {
        return SigarHolder.getSigarInstance().getNetInterfaceStat(name);
    }

}
