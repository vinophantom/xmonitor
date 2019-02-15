package com.vino.xmonitor.mcore;

import com.vino.xmonitor.ds.CircularLinkedElement;
import com.vino.xmonitor.ds.CircularLinkedList;
import com.vino.xmonitor.utils.CloseableUtil;
import com.vino.xmonitor.utils.FileUtils;
import com.vino.xmonitor.utils.NativeUtils;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.MissingResourceException;
import java.util.Objects;

public class SigarHolder {

    private static Logger logger = LoggerFactory.getLogger(SigarHolder.class);

    private static final int SIZE = 16;
    private static CircularLinkedElement<Sigar> temp;

    static {
        // 为Sigar构建环境
        //
        File tempFile = null;
        try {
            tempFile = File.createTempFile("sigar_lib", "zip");
            tempFile.deleteOnExit();
        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }
        InputStream is = SigarHolder.class.getResourceAsStream("/sigar_lib.zip");
        OutputStream os = null;
        try {
            os = new FileOutputStream(Objects.requireNonNull(tempFile));
        } catch (FileNotFoundException e) {
            logger.error("找不到临时文件！");
        }
        byte[] buffer = new byte[1024];
        int readBytes;
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                Objects.requireNonNull(os).write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            logger.error("io异常！", e);
        } finally {
            // If read/write fails, close streams safely before throwing an exception
            try {
                CloseableUtil.close(is);
                CloseableUtil.close(os);
            } catch (IOException e) {
                logger.error("关闭异常！", e);
            }

        }
        String sigarDirPath = System.getProperty("user.dir") + "/sigar";
        File sigarDir = new File(sigarDirPath);
        if(!sigarDir.exists()) {
            if(!sigarDir.mkdir()) {
                logger.error("创建文件夹失败！");
            }
        }
        try {
            FileUtils.unZipFiles(tempFile, System.getProperty("user.dir") + "/sigar");
        } catch (IOException e) {
            logger.error("解压失败！", e);
        }
        // Linux MacOS 分隔符 : Windows 是;
        String osName = System.getProperty("os.name", "generic").toLowerCase();
        String splitSymbol = osName.contains("win") ? ";" : ":";

        String oldLibPath = System.getProperty("java.library.path");
        String newLibPath = oldLibPath + splitSymbol + System.getProperty("user.dir") + "/sigar";
        System.setProperty("java.library.path", newLibPath);
        logger.info(System.getProperty("java.library.path"));
//        // 寻找 classpath 根目录下的 sigar 文件夹
//        URL sigarURL = SigarHolder.class.getResource("/sigar");
//        if (null == sigarURL) {
//            // 找不到抛异常
//            throw new MissingResourceException("miss classpath:/sigar folder", SigarHolder.class.getName(), "classpath:/sigar");
//        }
//
//        File classPath = new File(sigarURL.getFile());
//        String oldLibPath = System.getProperty("java.library.path");
////        System.load(SigarHolder.class.getClassLoader().getResource("/").getPath() + "/sigar/");
//        try {
//            // 追加库路径
//            classPath.getCanonicalPath();
////            String newLibPath = oldLibPath + splitSymbol + classPath.getCanonicalPath();
//            String newLibPath = oldLibPath + splitSymbol + "/resources/sigar";
//
//            System.setProperty("java.library.path", newLibPath);
//            logger.info("set sigar java.library.path=" + newLibPath);
//        } catch (IOException e) {
//            logger.error("append sigar to java.library.path error", e);
//        }

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
