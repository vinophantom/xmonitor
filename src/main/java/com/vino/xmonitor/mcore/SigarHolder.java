package com.vino.xmonitor.mcore;

import com.vino.xmonitor.ds.CircularLinkedElement;
import com.vino.xmonitor.ds.CircularLinkedList;
import org.hyperic.sigar.Sigar;

public class SigarHolder {


    private static final int SIZE = 16;


    private static CircularLinkedList<Sigar> sigarpool;
    private static CircularLinkedElement<Sigar> temp = null;


    static {
        sigarpool = new CircularLinkedList<Sigar>();
        sigarpool.initList();
        //插入
        for (int i = 0; i < SIZE; i++) {
            sigarpool.insertList(new Sigar());
        }

        temp = sigarpool.header;
    }



    /**
     * 获取Sigar实例
     * @return
     */
    protected static Sigar getSigarInstance() {
        return (Sigar) temp.next().value();
    }




}
