package com.vino.xmonitor.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vino.xmonitor.bean.soft.Process;
import com.vino.xmonitor.cache.CacheHelper;
import com.vino.xmonitor.mcore.OsUtils;
import com.vino.xmonitor.utils.DateUtils;
import com.vino.xmonitor.vo.ProcessVo;

import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;


/**
 * @author phantom
 */
@Service
public class ProcessService {





    public void killProc(String pid) throws SigarException{
        OsUtils.killProc(pid);
    }
    
    public void killProc(long pid)throws SigarException {
        OsUtils.killProc(pid);
    }

    public List <Process> getProcessList() throws SigarException {
        List<Process> list = OsUtils.getProcs();
        sortProcByMem(list);
        return list;
    }


    private void sortProcByMem(List<Process> li) {
        long totalMem = (long)CacheHelper.getFromPersisCache("total_mem");
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
            // double c1 = p1.getCpuUsage();
            // double c2 = p2.getCpuUsage();
            // if (p1.getState().equals('R')) m1 += totalMem * 1000;
            // if (p2.getState().equals('R')) m2 += totalMem * 1000;
            // if (c1 < c2)  m2 += totalMem * 1000;
            return new Long(m2).intValue() - new Long(m1).intValue() > 0 ? 1 : -1;
        });
    }

    public List<ProcessVo> getProcessVoList() throws SigarException {
        List<Process> list = getProcessList();
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
        // int i = name.indexOf("--");
        // pv.setName(name.substring(0, i == -1 ? name.length() : i));
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
        if (size < 1024) return (size >> 10) + " KiB";
        return (size >> 20) + "MiB";
    }
    private String convertTime(long t) {
        if(t < 1000) return "< 1s";
        else if(t < 60000) return Math.round((double)(t / 1000)) + "s";
        else if(t < 36000000) return Math.round((double)(t / 60000)) + "m";
        else if(t < 864000000) return Math.round((double)(t / 36000000)) + "h";
        else return Math.round(t / 864000000) + "day";
    }
 

    enum State {

        SLEEP('S',"休眠"),
        RUN('R',"运行中"),
        STOP('T', "中止"),
        ZOMBIE('Z',"完成"),
        IDLE('D',"空闲");
        // Z已取消
        // T已停止

        private char flag;
        private String state;

        private State(char f, String state) {
            this.flag = f;
            this.state = state;
        }
        public char getFlag() {return this.flag;}
        public String getState() {return this.state;}

        static String convertState(char flag) {
            for(State s : State.values()){
                if (s.flag == flag) return s.getState();
            }
            return null;
        }
    }



}