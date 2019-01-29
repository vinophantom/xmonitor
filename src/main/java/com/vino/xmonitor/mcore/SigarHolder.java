package com.vino.xmonitor.mcore;

import com.vino.xmonitor.ds.CircularLinkedElement;
import com.vino.xmonitor.ds.CircularLinkedList;
import org.hyperic.sigar.Sigar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;

class SigarHolder {

    static {

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
            System.out.println("set sigar java.library.path=" + newLibPath);

        } catch (IOException e) {
            System.out.println("append sigar to java.library.path error");
            e.printStackTrace();
        }
    }


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
