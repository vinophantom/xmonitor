package com.vino.xmonitor;

import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.Uptime;

import java.util.List;

public
class Top {
    private static final int SLEEP_TIME = 5000;
    private static final String HEADER = "PID\tUSER\tSTIME\tSIZE\tRSS\tSHARE\tSTATE\tTIME\t%CPU\tCOMMAND";

    public Top() {
    }

    private static String toString(ProcStat stat) {
        return stat.getTotal() + " processes: " + stat.getSleeping() + " sleeping, " + stat.getRunning() + " running, " + stat.getZombie() + " zombie, " + stat.getStopped() + " stopped... " + stat.getThreads() + " threads";
    }

    public static void main(String[] args) throws Exception {
        Sigar sigarImpl = new Sigar();
        SigarProxy sigar = SigarProxyCache.newInstance(sigarImpl, 5000);

        while(true) {
            Shell.clearScreen();
            System.out.println(Uptime.getInfo(sigar));
            System.out.println(toString(sigar.getProcStat()));
            System.out.println(sigar.getCpuPerc());
            System.out.println(sigar.getMem());
            System.out.println(sigar.getSwap());
            System.out.println();
            System.out.println("PID\tUSER\tSTIME\tSIZE\tRSS\tSHARE\tSTATE\tTIME\t%CPU\tCOMMAND");
            long[] pids = Shell.getPids(sigar, args);

            for(int i = 0; i < pids.length; ++i) {
                long pid = pids[i];
                String cpuPerc = "?";

                List info;
                try {
                    info = Ps.getInfo(sigar, pid);
                } catch (SigarException var11) {
                    continue;
                }

                try {
                    ProcCpu cpu = sigar.getProcCpu(pid);
                    cpuPerc = CpuPerc.format(cpu.getPercent());
                } catch (SigarException var10) {
                }

                info.add(info.size() - 1, cpuPerc);
                System.out.println(Ps.join(info));
            }

            Thread.sleep(5000L);
            SigarProxyCache.clear(sigar);
        }
    }
}
