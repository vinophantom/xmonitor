package com.vino.xmonitor.mcore;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.vino.xmonitor.bean.NetSpeed;
import com.vino.xmonitor.bean.hardware.Storage;
import com.vino.xmonitor.bean.soft.Process;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetConnection;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vino.xmonitor.utils.StringUtils;

/**
 * @author phantom
 */
public final class OsUtils {

    private static Logger logger = LoggerFactory.getLogger(OsUtils.class);


    // TODO: 端口范围判断
    private final static int MIN_PORT_NUMBER = 0;
    private final static int MAX_PORT_NUMBER = 65535;
    private static long lastGetSpeedTime = System.currentTimeMillis();

    private static long lastTimeRxBytes = 0L;
    private static long lastTimeTxBytes = 0L;

    public static int getNetSpeedCounts = 0;

    // private static OperatingSystemMXBean mxbean =
    //         (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();



    public static long currentPid() {
        return SigarHolder.getSigarInstance().getPid();
    }
    /**
     * 判断端口可用性
     * @param port
     * @return
     */
    public static boolean isPortAvailable(int port) throws SigarException {
        Sigar sigar = new Sigar();
        if (MIN_PORT_NUMBER > port || MAX_PORT_NUMBER < port) {return false;}
        int flags = NetFlags.CONN_TCP | NetFlags.CONN_SERVER | NetFlags.CONN_CLIENT;
        NetConnection[] netConnectionList = sigar.getNetConnectionList(flags);
        for (NetConnection netConnection : netConnectionList) {
            if ( netConnection.getLocalPort() == port ) {
                return false;
            }
        }
        return true;
    }


    public static List<Process> getProcs() throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        // Shell shell = new Shell();

        long[] pids = sigar.getProcList();
        List <Process> res = new ArrayList<Process>();
        for(int i = 0; i < pids.length; ++i) {
            long pid = pids[i];
            Process p = getProcObj(sigar, pid);
            if (p != null) {
                res.add(p);
            } else {
                continue;
            }
        }
        return res;
    }




    public static void killProc(long pid) throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        sigar.kill(pid, 9);
    }

    public static void killProc(String pid) throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        sigar.kill(pid, 9);
    }




    public static Process getProcObj(SigarProxy sigar, long pid) throws SigarException {
        ProcState state;
        try {
            state = sigar.getProcState(pid);
        } catch (SigarException e) {
            return null;
        }
        ProcTime time = null;
        String unknown = "unkown";
        Process p = new Process();
        // info：Pid
        p.setPid(pid);
        // info：用户
        try {
            ProcCredName cred = sigar.getProcCredName(pid);
            p.setUser(cred.getUser());
        } catch (SigarException var10) {
            p.setUser(unknown);
        }
        // info：开始时间
        try {
            time = sigar.getProcTime(pid);
            p.setStartTime(new Date(time.getStartTime()));
        } catch (SigarException var9) {
            p.setStartTime(null);
        }

        try {
            ProcMem mem = sigar.getProcMem(pid);
            // info：占用内存
            //noinspection deprecation,AliDeprecation
            p.setMem(mem.getRss() + mem.getShare());
            // info：所占用固定内存
            //noinspection deprecation,AliDeprecation
            p.setRss(mem.getRss());
            // info：
            p.setShare(mem.getShare());
        } catch (SigarException var8) {
            p.setMem(0);
            p.setRss(0);
            p.setShare(0);
        }
        // info：状态
        p.setState(state.getState());
        if (time != null) {
            // info：CPU 时间
            p.setCpuTime(getCpuTime(time));
        } else {
            p.setCpuTime(0);
        }

        // info：名字
        String cmd = ProcUtil.getDescription(sigar, pid);
        String[] args = StringUtils.split(cmd," ");
        if (0 != args.length){
            p.setName(args[0]);
        } else {
            int i = cmd.indexOf("--");
            p.setName(cmd.substring(0, i == -1 ? cmd.length() : i));
        }
        p.setCmd(cmd);
        try {
            double cpuUsage = sigar.getProcCpu(pid).getPercent();
            p.setCpuUsage(cpuUsage);
        } catch (Exception e) {
            return null;
        }
        return p;
    }




    private static long getCpuTime(ProcTime time) {
        return System.currentTimeMillis() - time.getStartTime();
    }



    public static List<Storage> getStorages () throws SigarException {
        FileSystem[] fileSystems = OsHolder.getFileSystem();
        List<Storage> res  = new ArrayList<>();

        Arrays.stream(fileSystems).forEach(fs -> {
            int type = fs.getType();
            String dirName = fs.getDirName();
            // TODO
            if (type != 5) {
                FileSystemUsage usage = null;
                try {
                    usage = OsHolder.getFileSystemUsageByName(dirName);
                } catch (SigarException e) {
                    logger.error("获取文件系统使用率失败", e);
                }
                res.add(new Storage(
                        fs.getDirName(),
                        fs.getDevName(),
                        fs.getSysTypeName(),
                        fs.getTypeName(),
                        fs.getOptions(),
                        fs.getType(),
                        fs.getFlags(),
                        usage.getTotal(),
                        usage.getFree(),
                        usage.getUsed(),
                        usage.getAvail(),
                        usage.getFiles(),
                        usage.getFreeFiles(),
                        usage.getDiskReads(),
                        usage.getDiskWrites(),
                        usage.getDiskReadBytes(),
                        usage.getDiskWriteBytes(),
                        usage.getDiskQueue(),
                        usage.getDiskServiceTime(),
                        usage.getUsePercent()
                ));
            } else {
                res.add(new Storage(
                        fs.getDirName(),
                        fs.getDevName(),
                        fs.getSysTypeName(),
                        fs.getTypeName(),
                        fs.getOptions(),
                        fs.getType(),
                        fs.getFlags()));
            }

        });



        return res;
    }
    /**
     * 获取网速
     * @return
     * @throws SigarException
     */
    public static synchronized NetSpeed getCurrNetSpeed () throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        String[] ifNames = sigar.getNetInterfaceList();
        long rxBytes = 0L;
        long txBytes = 0L;
        for (String name : ifNames) {
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            // 接收到的总字节数
            rxBytes += ifstat.getRxBytes();

            // 发送的总字节数
            txBytes += ifstat.getTxBytes();
        }

        long currTime = System.currentTimeMillis();
        @SuppressWarnings("Unchecked")
        double downSpeed = (rxBytes - lastTimeRxBytes)*1000 / (currTime - lastGetSpeedTime) ;
        double upSpeed = (txBytes - lastTimeTxBytes)*1000 / (currTime - lastGetSpeedTime) ;
        lastGetSpeedTime = currTime;
        lastTimeRxBytes = rxBytes;
        lastTimeTxBytes = txBytes;
        return new NetSpeed(upSpeed,downSpeed);
    }


    public static Map<String,Object> getSysInfo () throws SigarException, UnknownHostException {

        Map<String, Object> res = new HashMap<>(16);
        Sigar sigar = new Sigar();

        Properties props = System.getProperties();

        InetAddress addr = InetAddress.getLocalHost();

        res.put("sys", props.getProperty("os.name"));
        res.put("numOfCore", sigar.getCpuInfoList().length);
        res.put("arch", props.getProperty("os.arch"));
        res.put("currentIP", addr.getHostAddress());
        res.put("home", props.getProperty("user.home"));
        res.put("version", props.getProperty("os.version"));
        res.put("computerName", getHostName());
        res.put("user", getCurrentUser());
        return res;
    }

    private static String getCurrentUser() {
        Map<String, String> map = System.getenv();
            if(map.get("USERNAME") != null) {
                return map.get("USERNAME");
            } else {
                return map.get("LOGNAME");
            }
    }


    private static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage();
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }


    private static String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    public static CpuInfo getCpu() throws SigarException {
        return OsHolder.getCpuInfo()[0];
    }

    public static CpuPerc[] getCpuPerc() throws SigarException {
        return OsHolder.getCpuPercList();
    }


    public static Mem getMem() throws SigarException {
        return OsHolder.getMem();
    }
}
