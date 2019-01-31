package com.vino.xmonitor.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vino.xmonitor.bean.soft.Process;
import com.vino.xmonitor.mcore.OsUtils;
import com.vino.xmonitor.utils.DateUtils;
import com.vino.xmonitor.utils.StringUtils;
import com.vino.xmonitor.vo.ProcessVo;

import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;


/**
 * @author phantom
 */
@Service
public class ProcessService {

    private final static int SORT_BY_CPU = 0;
    private final static int SORT_BY_MEM = 1;

    private final static String SORT_BY_CPU_ARG = "sort-by-cpu";
    private final static String SORT_BY_MEM_ARG = "sort-by-mem";


    public void killProc(String pid) throws SigarException{
        OsUtils.killProc(pid);
    }

    public void killProc(long pid)throws SigarException {
        OsUtils.killProc(pid);
    }

    public List <Process> getProcessList(String... args) throws SigarException {

        List<Process> list = OsUtils.getProcs();
        int sortFlag = SORT_BY_MEM;
        for (String arg : args) {
            if (!StringUtils.isEmptyString(arg)) {
                if (SORT_BY_CPU_ARG.equals(arg)) {
                    sortFlag = SORT_BY_CPU;
                }
            }
        }
        sortList(sortFlag, list);
        return list;
    }


    private void sortList (int flag, List<Process> list) {
        if(flag == SORT_BY_CPU) {
            sortProcByCpu(list);
        } else if(flag == SORT_BY_MEM) {
            sortProcByMem(list);
        }
    }

    private void sortProcByCpu(List<Process> li) {
        li.sort((p1, p2) -> {
            if(p1 == null && p2 == null) {
                return 0;
            } else if(p1 == null) {
                return -1;
            } else if(p2 == null) {
                return 1;
            }
            double c1 = p1.getCpuUsage();
            double c2 = p2.getCpuUsage();
            return c2 > c1 ? 1 : -1;
        });
    }
    private void sortProcByMem(List<Process> li) {
        li.sort((p1, p2) -> {
            if(p1 == null && p2 == null) {
                return 0;
            }
            if(p1 == null) {
                return -1;
            }
            if(p2 == null) {
                return 1;
            }
            long m1 = p1.getMem();
            long m2 = p2.getMem();
            return m2 > m1 ? 1 : -1;
        });
    }

    public List<ProcessVo> getProcessVoList(String... args) throws SigarException {
        List<Process> list = getProcessList(args);
        List<ProcessVo> res = new ArrayList<>();
        for (Process p : list) {
            res.add(convertToProcessVo(p));
        }
        return res;
    }



    private ProcessVo convertToProcessVo (Process p) {
        ProcessVo pv = new ProcessVo();
        pv.setPid(p.getPid() + "");
        String name = p.getName();
        pv.setName(name);
        pv.setCmd(p.getCmd());
        pv.setUser(p.getUser());
        pv.setState(State.convertState(p.getState()));
        pv.setRss(convertMem(p.getRss()));
        pv.setMem(convertMem(p.getMem()));
        pv.setShare(convertMem(p.getShare()));
        pv.setStartTime(DateUtils.formatDate(p.getStartTime(), "MM-dd HH:mm"));
        pv.setCpuTime(convertTime(p.getCpuTime()));
        pv.setCpuUsage(convertPercent(p.getCpuUsage()));
        return pv;
    }


    private String convertPercent(double per) {
        BigDecimal bigDecimal = new BigDecimal(per);
        //保留2位小数。
        return bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
    }

    private String convertMem (long size) {
        if (size < 1024) {
            return (size >> 10) + " KiB";
        }
        return (size >> 20) + "MiB";
    }
    private String convertTime(long t) {
        if(t < 1000) {
            return "< 1s";
        } else if(t < 60000) {
            return Math.round((double)(t / 1000)) + "s";
        } else if(t < 36000000) {
            return Math.round((double)(t / 60000)) + "m";
        } else if(t < 864000000) {
            return Math.round((double)(t / 36000000)) + "h";
        } else {
            return Math.round(t / 864000000) + "day";
        }
    }
 

    enum State {

        /**
         * 休眠
         */
        SLEEP('S',"休眠"),
        /**
         * 运行中
         */
        RUN('R',"运行中"),
        /**
         * 中止
         */
        STOP('T', "中止"),
        /**
         * 完成
         */
        ZOMBIE('Z',"完成"),
        /**
         * 空闲
         */
        IDLE('D',"空闲");
        

        private char flag;
        private String state;

        State(char f, String state) {
            this.flag = f;
            this.state = state;
        }
        public char getFlag() {return this.flag;}
        public String getState() {return this.state;}

        static String convertState(char flag) {
            for(State s : State.values()){
                if (s.flag == flag) {
                    return s.getState();
                }
            }
            return null;
        }
    }



}