package com.vino.xmonitor.utils;


import java.util.UUID;


/**
 * @author phantom
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
