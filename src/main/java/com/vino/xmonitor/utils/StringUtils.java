package com.vino.xmonitor.utils;

public class StringUtils {

    public static boolean isEmptyString (String s) {
        if (s == null) { return true; }
        return "".equals(s.trim());
    }


}
