package com.vino.xmonitor.mcore;

import com.vino.xmonitor.ds.CircularLinkedElement;
import com.vino.xmonitor.ds.CircularLinkedList;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;

public class SigarHolder {

    private static Logger logger = LoggerFactory.getLogger(SigarHolder.class);
    static {
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
            logger.info("set sigar java.library.path=" + newLibPath);
        } catch (IOException e) {
            logger.error("append sigar to java.library.path error", e);
        }
    }


    private static final int SIZE = 16;
    private static CircularLinkedElement<Sigar> temp;


    static {
        CircularLinkedList<Sigar> sigarPool = new CircularLinkedList<>();
        sigarPool.initList();
        //插入
        for (int i = 0; i < SIZE; i++) {
            sigarPool.insertList(new Sigar());
        }

        temp = sigarPool.header;
    }



    /**
     * 获取Sigar实例
     * @return
     */
    static Sigar getSigarInstance() {
        return (Sigar) temp.next().value();
    }

}
