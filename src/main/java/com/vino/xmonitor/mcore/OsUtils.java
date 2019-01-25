package com.vino.xmonitor.mcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.IntStream;

import com.sun.management.OperatingSystemMXBean;
import com.vino.xmonitor.bean.NetSpeed;
import com.vino.xmonitor.bean.hardware.Storage;
import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.Netstat;
import org.hyperic.sigar.cmd.Ps;

/**
 * @author phantom
 */
public class OsUtils {

    private static long lastGetSpeedTime = System.currentTimeMillis();

    private static long lastTimeRxBytes = 0L;
    private static long lastTimeTxBytes = 0L;

    public static int getNetSpeedCounts = 0;

    private static OperatingSystemMXBean mxbean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();


    public static List<Storage> getStorages () throws SigarException {
        FileSystem[] fileSystems = OsHolder.getFileSystem();
        List<Storage> res  = new ArrayList<>();

        Arrays.asList(fileSystems).stream().forEach(fs -> {
            int type = fs.getType();
            String dirName = fs.getDirName();
            if (type == 2) {
                FileSystemUsage usage = null;
                try {
                    usage = OsHolder.getFileSystemUsageByName(dirName);
                } catch (SigarException e) {
                    e.printStackTrace();
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
        double downSpeed = (rxBytes - lastTimeRxBytes)*1000 / (currTime - lastGetSpeedTime) ;
        double upSpeed = (txBytes - lastTimeTxBytes)*1000 / (currTime - lastGetSpeedTime) ;
        lastGetSpeedTime = currTime;
        lastTimeRxBytes = rxBytes;
        lastTimeTxBytes = txBytes;
        return new NetSpeed(upSpeed,downSpeed);
    }


    public static Map<String,Object> getSysInfo () throws UnknownHostException {

        Map<String, Object> res = new HashMap<>();

        Runtime r = Runtime.getRuntime();

        Properties props = System.getProperties();

        InetAddress addr = InetAddress.getLocalHost();

        String ip = addr.getHostAddress();

        Map<String, String> map = System.getenv();


        res.put("sys", mxbean.getName());
        res.put("numOfCore", mxbean.getAvailableProcessors());
        res.put("arch", props.getProperty("os.arch"));
        res.put("currentIP", addr.getHostAddress());
        res.put("home", props.getProperty("user.home"));
        res.put("version", props.getProperty("os.version"));
        res.put("computerName", map.get("COMPUTERNAME"));
        res.put("user", map.get("USERNAME"));
        return res;
    }

    public static long getProcessCpuTime () {
        return mxbean.getProcessCpuTime();
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


    public static Map<String, String> getSystemInfo() {


        return null;
    }

    public static void main(String[] args) {
        try {
//            Sigar sigar = new Sigar();
//            Netstat netstat = new Netstat();
//            System.out.println(netstat.getUsageShort());
//            Ps ps = new Ps();
////            ps.
//            sigar.getThreadCpu();
//
//            OperatingSystemMXBean mxbean =
//
//                    (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//
//            System.out.println("操作系统: "+mxbean.getName());
//            System.out.println("CPU数量:" + mxbean.getAvailableProcessors());
//            System.out.println("虚拟内存:"+mxbean.getCommittedVirtualMemorySize() /1024 + "KB");
//            System.out.println("可用物理内存:"+mxbean.getFreePhysicalMemorySize() / 1024 + "KB");
//            System.out.println("Free Swap Space Size :"+mxbean.getFreeSwapSpaceSize() / 1024 + "KB");
//            System.out.println("Process CPU Time : "+mxbean.getProcessCpuTime());
//            System.out.println("总物理内存大小:"+mxbean.getTotalPhysicalMemorySize() / 1024 + "KB");
//            System.out.println("Total Swap Space Size:"+mxbean.getTotalSwapSpaceSize() / 1024 + "KB");
//            System.out.println("processors:" + mxbean.getAvailableProcessors());
//            CpuPerc c = OSHolder.getCpuPerc();
//            CpuPerc c2 = OSHolder.getCpuPerc();
//            System.out.println(Runtime.getRuntime());
//            Thread.sleep(1000);
//            System.out.println(Runtime.getRuntime());
//
//
//            System.out.println(System.getProperty("sun.jnu.encoding"));
//            System.out.println(System.getProperty("file.encoding"));
//            System.out.println(Charset.defaultCharset());
//            System.out.println(System.getProperty("user.language"));
//            System.getProperties().list(System.out);
//            System.getProperties().put("file.encoding", "GBK");
//            getProcessList();
            //            // System信息，从jvm获取
//                        property();
            //            System.out.println("----------------------------------");
            //            // cpu信息
//            cpu();
            //            System.out.println("----------------------------------");
            //            // 内存信息
//            memory();
            //            System.out.println("----------------------------------");
            //            // 操作系统信息
//                        os();
            //            System.out.println("----------------------------------");
            //            // 用户信息
//                        who();
            //            System.out.println("----------------------------------");
            //            // 文件系统信息
            file();
            //            System.out.println("----------------------------------");
//             网络信息
                        net();
            //            System.out.println("----------------------------------");
            // 以太网信息
                        ethernet();
            //            System.out.println("----------------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void property() throws UnknownHostException {
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        // 获取用户名
        String userName = map.get("USERNAME");
        // 获取计算机名
        String computerName = map.get("COMPUTERNAME");
        // 获取计算机域名
        String userDomain = map.get("USERDOMAIN");

        System.out.println("用户名:    " + userName);
        System.out.println("计算机名:    " + computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地ip地址:    " + ip);
        System.out.println("本地主机名:    " + addr.getHostName());
        System.out.println("JVM可以使用的总内存:    " + r.totalMemory());
        System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory());
        System.out.println("JVM可以使用的处理器个数:    " + r.availableProcessors());
        System.out.println("Java的运行环境版本：    " + props.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：    " + props.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：    " + props.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：    " + props.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：    " + props.getProperty("java.class.version"));
        System.out.println("Java的类路径：    " + props.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：    " + props.getProperty("os.name"));
        System.out.println("操作系统的构架：    " + props.getProperty("os.arch"));
        System.out.println("操作系统的版本：    " + props.getProperty("os.version"));
        System.out.println("文件分隔符：    " + props.getProperty("file.separator"));
        System.out.println("路径分隔符：    " + props.getProperty("path.separator"));
        System.out.println("行分隔符：    " + props.getProperty("line.separator"));
        System.out.println("用户的账户名称：    " + props.getProperty("user.name"));
        System.out.println("用户的主目录：    " + props.getProperty("user.home"));
        System.out.println("用户的当前工作目录：    " + props.getProperty("user.dir"));
    }

    private static void memory() throws SigarException {
        Sigar sigar = new Sigar();

        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        // 当前内存使用量
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        // 当前内存剩余量
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        System.out.println("toMap:    " + mem.toMap());
        System.out.println("getFreePercent:    " + mem.getFreePercent());
        System.out.println("getActualFree:    " + mem.getActualFree());
        System.out.println("getActualUsed:    " + mem.getActualUsed());
        System.out.println("getUsedPercent:    " + mem.getUsedPercent());


        Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");

        System.out.println("toMap():    " + swap.toMap());

        System.out.println("PageIn:    " + swap.getPageIn());
        System.out.println("getTotal:    " + swap.getTotal() / 1024L + "K free");
        System.out.println("getPageOut:    " + swap.getPageOut() + "K free");
    }


    private static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo[] infos = sigar.getCpuInfoList();
        Cpu cpu = sigar.getCpu();
        CpuPerc[] cpuList = sigar.getCpuPercList();
        // 不管是单块CPU还是多CPU都适用
        // CPU的总量MHz
        // 获得CPU的卖主，如：Intel
        // 获得CPU的类别，如：Celeron
        // 缓冲存储器数量
        System.out.println("softirq" + cpu.getSoftIrq());
        System.out.println("softirq" + cpu.getNice());
        System.out.println("softirq" + cpu.getSoftIrq());
        IntStream.range(0, infos.length).forEach(i -> {
            CpuInfo info = infos[i];

            System.out.println("第" + (i + 1) + "块CPU信息");
            System.out.println("CPU的总量MHz:    " + info.getMhz());
            System.out.println("CPU生产商:    " + info.getVendor());
            System.out.println("CPU类别:    " + info.getModel());
            System.out.println("CPU缓存数量:    " + info.getCacheSize());
            System.out.println("getcache:    " + info.getCacheSize());
            printCpuPerc(cpuList[i]);
        });
    }

    private static void printCpuPerc(CpuPerc cpu) {
        // 用户使用率
        System.out.println("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));
        // 系统使用率
        System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));
        // 当前等待率
        System.out.println("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));
        //错误率
        System.out.println("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));
        // 当前空闲率
        System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));
        // 总的使用率
        System.out.println("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));
    }

    private static void os() {
        OperatingSystem os = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        System.out.println("操作系统:    " + os.getArch());
        System.out.println("操作系统CpuEndian():    " + os.getCpuEndian());
        System.out.println("操作系统DataModel():    " + os.getDataModel());
        // 系统描述
        System.out.println("操作系统的描述:    " + os.getDescription());
        // 操作系统类型
        System.out.println("OS.getName():    " + os.getName());
        System.out.println("OS.getPatchLevel():    " + os.getPatchLevel());
        // 操作系统的卖主
        System.out.println("操作系统的卖主:    " + os.getVendor());
        // 卖主名称
        System.out.println("操作系统的卖主名:    " + os.getVendorCodeName());
        // 操作系统名称
        System.out.println("操作系统名称:    " + os.getVendorName());
        // 操作系统卖主类型
        System.out.println("操作系统卖主类型:    " + os.getVendorVersion());
        // 操作系统的版本号
        System.out.println("操作系统的版本号:    " + os.getVersion());
    }

    private static void who() throws SigarException {
        Sigar sigar = new Sigar();
        Who[] who = sigar.getWhoList();
        if (who != null && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                System.out.println("当前系统进程表中的用户名" + i);
                Who _who = who[i];
                System.out.println("用户控制台:    " + _who.getDevice());
                System.out.println("用户host:    " + _who.getHost());
                System.out.println("getTime():    " + _who.getTime());
                // 当前系统进程表中的用户名
                System.out.println("当前系统进程表中的用户名:    " + _who.getUser());
            }
        }
    }

    private static void file() throws Exception {
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        try {
            for (int i = 0; i < fslist.length; i++) {
                System.out.println("分区的盘符名称" + i);
                FileSystem fs = fslist[i];
                // 分区的盘符名称
                System.out.println("盘符名称:    " + fs.getDevName());
                // 分区的盘符名称
                System.out.println("盘符路径:    " + fs.getDirName());
                System.out.println("盘符标志:    " + fs.getFlags());
                // 文件系统类型，比如 FAT32、NTFS
                System.out.println("盘符类型:    " + fs.getSysTypeName());
                // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
                System.out.println("盘符类型名:    " + fs.getTypeName());
                // 文件系统类型
                System.out.println("盘符文件系统类型:    " + fs.getType());
                FileSystemUsage usage = null;
                usage = sigar.getFileSystemUsage(fs.getDirName());
                switch (fs.getType()) {
                    // TYPE_UNKNOWN ：未知
                    case 0:
                        break;
                    // TYPE_NONE
                    case 1:
                        break;
                    // TYPE_LOCAL_DISK : 本地硬盘
                    case 2:
                        // 文件系统总大小
                        System.out.println(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
                        // 文件系统剩余大小
                        System.out.println(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
                        // 文件系统可用大小
                        System.out.println(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
                        // 文件系统已经使用量
                        System.out.println(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
                        double usePercent = usage.getUsePercent() * 100D;
                        // 文件系统资源的利用率
                        System.out.println(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
                        break;
                    // TYPE_NETWORK ：网络
                    case 3:
                        break;
                    // TYPE_RAM_DISK ：闪存
                    case 4:
                        break;
                    // TYPE_CDROM ：光驱
                    case 5:
                        break;
                    // TYPE_SWAP ：页面交换
                    case 6:
                        break;
                    default:
                }
                System.out.println(fs.getDevName() + "读出：    " + usage.getDiskReads());
                System.out.println(fs.getDevName() + "写入：    " + usage.getDiskWrites());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return;
    }

    private static void net() throws Exception {
        Sigar sigar = new Sigar();
        String[] ifNames = sigar.getNetInterfaceList();
        for (String name : ifNames) {
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            // 网络设备名
            System.out.println("网络设备名:    " + name);
            // IP地址
            System.out.println("IP地址:    " + ifconfig.getAddress());
            // 子网掩码
            System.out.println("子网掩码:    " + ifconfig.getNetmask());
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            // 接收的总包裹数
            System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());
            // 发送的总包裹数
            System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());
            // 接收到的总字节数
            System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());
            // 发送的总字节数
            System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());
            // 接收到的错误包数
            System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());
            // 发送数据包时的错误数
            System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());
            // 接收时丢弃的包数
            System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());
            // 发送时丢弃的包数
            System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());
        }
    }

    private static void ethernet() throws SigarException {
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        for (String iface : ifaces) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(iface);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                    || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }
            // IP地址
            System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());
            // 网关广播地址
            System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());
            // 网卡MAC地址
            System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());
            // 子网掩码
            System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());
            // 网卡描述信息
            System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());
            System.out.println(cfg.getName() + "网卡类型" + cfg.getType());
        }
    }

    public static void kill() throws IOException {
        Properties props = System.getProperties();

        // 获取当前系统名称，win和linux的用法是不一样的
        String os = props.getProperty("os.name");
        if (os != null) {
            os = os.toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe");
            } else {
                Runtime.getRuntime().exec("pkill -f 'phantomjs'");
            }
        }
    }


    public static void getProcessList() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("tasklist /v");
            System.out.println(p);
            BufferedReader bw = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
            String s;
            while ((s = bw.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static final void windowsRestart(String port, String path, String param)
            throws IOException, InterruptedException
    {
        String cmd = "for /f \"tokens=5\" %a in ('netstat -ao^|findstr " + port + "') do @taskkill /F /PID %a";
        String[] command = { "cmd", "-c", "start", cmd };
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        Thread.sleep(5000L);
        cmd = "cmd /c start " + path;
        pro = Runtime.getRuntime().exec(cmd);
        pro.waitFor();
        pro.exitValue();
    }

    public static final void linuxRestart(String port, String path, String param)
            throws IOException, InterruptedException
    {
        String cmd = "kill -9 $(netstat -tlnp|grep " + port + "|awk '{print $7}'|awk -F '/' '{print $1}')";
        String[] command = { "sh", "-c", cmd };
        Process pro = Runtime.getRuntime().exec(command);
        pro.waitFor();
        cmd = path;
        pro = Runtime.getRuntime().exec(cmd);
        pro.waitFor();
    }



}
