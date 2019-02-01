package com.vino.xmonitor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import com.vino.xmonitor.mcore.OsUtils;

import com.vino.xmonitor.mcore.SigarHolder;
import org.hyperic.sigar.DirUsage;
import org.hyperic.sigar.FileInfo;
import org.hyperic.sigar.NetConnection;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcUtil;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.Tcp;
import org.hyperic.sigar.cmd.Netstat;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * uptime 命令显示当前时间、系统正常运行的时间长度、联机用户数目和平均负载。负载平均值是以 1 分钟、5 分钟、15 分钟时间间隔开头的可运行的进程。uptime 命令的输出实质上就是 w 命令提供的标题行。
 * 
 * 
 * 
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class CommandTest {
    @Before
    public void init() {
        System.out.println("==================================############################=================================");
        // Linux MacOS 分隔符 : Windows 是;
        String osName = System.getProperty("os.name", "generic").toLowerCase();
        String splitSymbol = osName.contains("win") ? ";" : ":";

        // 寻找 classpath 根目录下的 sigar 文件夹
        URL sigarURL = SigarHolder.class.getResource("/sigar");
        if (null == sigarURL) {
            // 找不到抛异常
            throw new MissingResourceException("miss classpath:/sigar folder", SigarHolder.class.getName(), "classpath:/sigar");
        }

        File classPath = new File(sigarURL.getFile());
        String oldLibPath = System.getProperty("java.library.path");

        try {
            // 追加库路径
            String newLibPath = oldLibPath + splitSymbol + classPath.getCanonicalPath();
            System.setProperty("java.library.path", newLibPath);
        } catch (IOException e) {
        }
    }
    @Test
    public void testPs() throws Exception {
        Sigar sigar = SigarHolder.getSigarInstance();
        for (double v : sigar.getLoadAverage()) {
            System.out.println(v);
        }
        Tcp tcp = sigar.getTcp();
        System.out.println(tcp);

        DirUsage usage = sigar.getDirUsage("/home/phantom/github");
        System.out.println(usage);

        FileInfo fileInfo = sigar.getFileInfo("/etc/profile");
        System.out.println(fileInfo);

        Ps.main(new String[]{});





    }


    @Test
    public void testPs1() throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        Shell shell = new Shell();
        String[] args = {};
        Ps ps = new Ps();

        long[] pids;
        if (args.length == 0) {
            pids = sigar.getProcList();
        } else {
            pids = shell.findPids(args);
        }

        for(int i = 0; i < pids.length; ++i) {
            long pid = pids[i];

            try {
//                this.output(pid);
                System.out.println(join(getInfo(sigar, pid)));
            } catch (SigarException e) {
//                .println("Exception getting process info for " + pid + ": " + e.getMessage());
            }
        }
    }








    public static List getInfo(SigarProxy sigar, long pid) throws SigarException {
        ProcState state = sigar.getProcState(pid);
        ProcTime time = null;
        String unknown = "???";
        List info = new ArrayList();
        // info：Pid
        info.add(String.valueOf(pid));
        // info：用户
        try {
            ProcCredName cred = sigar.getProcCredName(pid);
            info.add(cred.getUser());
        } catch (SigarException var10) {
            info.add(unknown);
        }
        // info：开始时间
        try {
            time = sigar.getProcTime(pid);
            info.add(getStartTime(time.getStartTime()));
        } catch (SigarException var9) {
            info.add(unknown);
        }

        try {
            ProcMem mem = sigar.getProcMem(pid);
            // info：占用内存
            info.add(Sigar.formatSize(mem.getSize()));
            // info：所占用固定内存
            info.add(Sigar.formatSize(mem.getRss()));
            // info：
            info.add(Sigar.formatSize(mem.getShare()));
        } catch (SigarException var8) {
            info.add(unknown);
        }
        // info：状态
        /**
         * S（-l 和 l 标志）进程或内核线程的状态：
         *              对于进程：

                        O不存在
                        A活动
                        W已交换
                        I空闲（等待启动）
                        Z已取消
                        T已停止
                        对于内核线程：

                        O不存在
                        R正在运行
                        S正在休眠
                        W已交换
                        Z已取消
                        T已停止
         */
        info.add(String.valueOf(state.getState()));
        if (time != null) {
            // info：CPU 时间
            info.add(getCpuTime(time));
        } else {
            info.add(unknown);
        }

        // info：名字 
        String name = ProcUtil.getDescription(sigar, pid);
        info.add(name);
        return info;
    }



    public static String getCpuTime(long total) {
        long t = total / 1000L;
        return t / 60L + ":" + t % 60L;
    }

    public static String getCpuTime(ProcTime time) {
        return getCpuTime(time.getTotal());
    }

    private static String getStartTime(long time) {
        if (time == 0L) {
            return "00:00";
        } else {
            long timeNow = System.currentTimeMillis();
            String fmt = "MMMd";
            if (timeNow - time < 86400000L) {
                fmt = "HH:mm";
            }

            return (new SimpleDateFormat(fmt)).format(new Date(time));
        }
    }


    public static String join(List info) {
        StringBuffer buf = new StringBuffer();
        Iterator i = info.iterator();
        boolean hasNext = i.hasNext();

        while(hasNext) {
            buf.append((String)i.next());
            hasNext = i.hasNext();
            if (hasNext) {
                buf.append("\t");
            }
        }

        return buf.toString();

    }


    @Test
    public void testNetstat() throws Exception {
        Netstat.main(new String[]{"n"});

    }

    @Test
    public void testPs2() throws SigarException {
        System.out.println(OsUtils.isPortAvailable(9051));

        int flags = NetFlags.CONN_TCP | NetFlags.CONN_SERVER | NetFlags.CONN_CLIENT;

        Sigar sigar = SigarHolder.getSigarInstance();
        NetConnection[] netConnections = sigar.getNetConnectionList(flags);
        System.out.println(netConnections);
        long port = sigar.getProcPort(NetFlags.getConnectionProtocol("tcp"),9051L);
        System.out.println(port);
//        String address = sigar.getNetListenAddress("9051");
        
    }


    @Test
    public void name() throws SigarException , InterruptedException {
        Sigar sigar = SigarHolder.getSigarInstance();
        // String[] p = sigar.getProcArgs(15908L);
        ProcExe p = sigar.getProcExe(15908L);
        int flags = NetFlags.CONN_TCP | NetFlags.CONN_SERVER | NetFlags.CONN_CLIENT;
        // flags = NetFlags.CONN_PROTOCOLS;
        NetConnection[] a = sigar.getNetConnectionList(flags);
        long port =  sigar.getProcPort("tcp", "39750");
        System.out.println(OsUtils.isPortAvailable(8080));
        System.out.println(port);
        // while (true) {
        //     Thread.sleep(200L);
        //     ProcCpu p = sigar.getProcCpu(20640L);
        //     System.out.println(p.getPercent()  + "  " + p.getUser());
            
        // }
    }
    @Test
    public void testget() throws SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
        long pid = 7106;
        Object o = sigar.getProcArgs(pid);
        System.out.println(o);
    }


    public String first(String... a ) {
        return a[5];
    }

    @Test
    public void test() {
        System.out.println(first("1","2","3"));
    }


    @Test
    public void cpu() throws InterruptedException {
        int busyTime = 10;
		int idleTime = busyTime;
//
//		while(true){
//			long startTime = System.currentTimeMillis();
//			//busy loop:
//			while((System.currentTimeMillis()-startTime)<=busyTime)
//				;
//			Thread.sleep(idleTime);
//        }
    }

    @Test
    public void testCPU() throws InterruptedException, SigarException {
        Sigar sigar = SigarHolder.getSigarInstance();
//        while (true) {
//                Thread.sleep(1000L);
//                ProcCpu p = sigar.getProcCpu(7656L);
//                System.out.println(p.getPercent()  + "  " + p.getUser());
//
//            }
    }

}

