package com.vino.xmonitor;

import com.vino.xmonitor.controller.encoder.CpuCoreEncoder;
import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.CpuInfo;
import org.hyperic.sigar.cmd.Netstat;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.Shell;
import org.junit.Test;
import sun.nio.ch.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTest {


    @Test
    public void testTasklist() throws IOException, InterruptedException {
        InputStream in = null;
//        String currLoadPath = this.getClass().getResource("/").getPath();
//        System.out.println(currLoadPath);
        Process p = Runtime.getRuntime().exec("netstat -ano");
//        p.wait(500L);
        BufferedReader bw = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
        String s;
        int i = 0;
        while ((s = bw.readLine()) != null) {
            System.out.println(s);

            i++;
        }
        System.out.println(i);
    }

    @Test
    public void test2() {
        System.out.println("              ".length());
        String a = "TCP    [::]:5672              [::]:0                 LISTENING       5520";
        String b[] = a.split(" ");
        //TODO:


    }


    public static List<String> split(String s) {
        String[] sa = s.split(" ");
        List<String> res = new ArrayList<>();
        for (String str : sa) {
            if ("" != str.trim()) {
                res.add(str);
            }
        }

        return res;
    }


    public static NetstatInfo convertNetstat(String line) {

        List<String> list = split(line);
        if (5 != list.size()) {
            return null;
        } else if (!isInteger(list.get(3))) {
            return null;
        }
        return new NetstatInfo();
        //TODO
    }


    private static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    @Test
    public void test3() {
        Netstat netstat = new Netstat();

    }


    @Test
    public void test4() throws SigarException {
        StringBuilder sb = new StringBuilder(200);

        Sigar sigar = new Sigar();

        String[] type = new String[]{"State.Name.sw=java"};
        long[] pids = Shell.getPids(sigar, type);

        for (int i = 0; i < pids.length; i++) {
//            appendLineBreak(sb, i);
//
//            sb.append(TYPE_JAVA_X + i);
//            sb.append(separator);
            long pid = pids[i];

            String cpuPerc = "?";
            @SuppressWarnings("unchecked")
            List<Object> info = new ArrayList<Object>(Ps.getInfo(sigar, pid));
            ProcCpu cpu = sigar.getProcCpu(pid);
            cpuPerc = CpuPerc.format(cpu.getPercent());
            info.add(info.size() - 1, cpuPerc);
            sb.append(Ps.join(info));
        }

        System.out.println(sb.toString());
        ;

    }

    @Test
    public void testPS() {

        System.out.println("start==============");
        List<ProcessInfo> processInfos = getProcessInfo();

        System.out.println();
    }


    //获取进程的相关信息

    private List<ProcessInfo> getProcessInfo() {

        Ps ps = new Ps();
        Sigar sigar = new Sigar();

        List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();

        try {

            long[] pids = sigar.getProcList();

            for (long pid : pids) {
                List<String> list = null;
                try {
                    list = ps.getInfo(sigar, pid);

                } catch (SigarException e) {
//                    e.printStackTrace();
                    if ("No such process".equals(e.getMessage())) {
                        continue;
                    }
                }

                ProcessInfo info = new ProcessInfo();

//                for(int i = 0; i <= list.size(); i++){
//
//                    switch(i){
//
//                        case 0 : info.setPid(list.get(0)); break;
//
//                        case 1 : info.setUser(list.get(1)); break;
//
//                        case 2 : info.setStartTime(list.get(2)); break;
//
//                        case 3 : info.setMemSize(list.get(3)); break;
//
//                        case 4 : info.setMemUse(list.get(4)); break;
//
//                        case 5 : info.setMemhare(list.get(5)); break;
//
//                        case 6 : info.setState(list.get(6)); break;
//
//                        case 7 : info.setCpuTime(list.get(7)); break;
//
//                        case 8 : info.setName(list.get(8)); break;
//
//                    }

//                }

                processInfos.add(info);

            }

        } catch (SigarException e) {

            e.printStackTrace();

        }

        return processInfos;

    }


    class ProcessInfo {


        private String pid;


        private String user;


        private String startTime;


        private String memSize;


        private String memUse;


        private String memhare;


        private String state;


        private String cpuTime;


        private String name;


        public String getPid() {

            return pid;

        }


        public void setPid(String pid) {

            this.pid = pid;

        }


        public String getUser() {

            return user;

        }


        public void setUser(String user) {

            this.user = user;

        }


        public String getStartTime() {

            return startTime;

        }


        public void setStartTime(String startTime) {

            this.startTime = startTime;

        }


        public String getMemSize() {

            return memSize;

        }


        public void setMemSize(String memSize) {

            this.memSize = memSize;

        }


        public String getMemUse() {

            return memUse;

        }


        public void setMemUse(String memUse) {

            this.memUse = memUse;

        }


        public String getMemhare() {

            return memhare;

        }


        public void setMemhare(String memhare) {

            this.memhare = memhare;

        }


        public String getState() {

            return state;

        }


        public void setState(String state) {

            this.state = state;

        }


        public String getCpuTime() {

            return cpuTime;

        }


        public void setCpuTime(String cpuTime) {

            this.cpuTime = cpuTime;

        }


        public String getName() {

            return name;

        }


        public void setName(String name) {

            this.name = name;

        }

    }

    @Test
    public void test5() throws SigarException {
        Sigar sigar = new Sigar();
        long[] pids = sigar.getProcList();
        System.out.println(pids.length);
//        sigar.getProcPort()
        CpuCoreEncoder cpuCoreEncoder = new CpuCoreEncoder();
        Cpu cpu = sigar.getCpu();
        CpuPerc cpuPerc = sigar.getCpuPerc();
//        sigar.getProcTime();
        CpuInfo cpuInfo = new CpuInfo();



    }


    @Test
    public void test6() throws SigarException, InterruptedException {
        Map<String,Object> map = new HashMap<String,Object>();

//        String ip = getAddress().toString().replace("/","");
        String ip = "";
        Sigar sigar = new Sigar();
        String[] ifNames = sigar.getNetInterfaceList();
        long rxbps = 0;
        long txbps = 0;
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if (ifconfig.getAddress().equals(ip)) {
                long start = System.currentTimeMillis();
                NetInterfaceStat statStart = sigar.getNetInterfaceStat(name);
                long rxBytesStart = statStart.getRxBytes();
                long txBytesStart = statStart.getTxBytes();
                Thread.sleep(1000);
                long end = System.currentTimeMillis();
                NetInterfaceStat statEnd = sigar.getNetInterfaceStat(name);
                long rxBytesEnd = statEnd.getRxBytes();
                long txBytesEnd = statEnd.getTxBytes();

                rxbps = ((rxBytesEnd - rxBytesStart)*8/(end-start)*1000)/1024/8;
                txbps = ((txBytesEnd - txBytesStart)*8/(end-start)*1000)/1024/8;
                break;
            }
        }
        map.put("rxbps",rxbps);
        map.put("txbps",txbps);
    }
}
