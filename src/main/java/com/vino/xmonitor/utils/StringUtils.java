package com.vino.xmonitor.utils;

import org.springframework.lang.Nullable;

public class StringUtils {

    public static boolean isEmptyString (String s) {
        if (s == null) { return true; }
        return "".equals(s.trim());
    }



    @Nullable
    public static String[] split(@Nullable String toSplit, @Nullable String delimiter) {
        if (!hasLength(toSplit) || !hasLength(delimiter)) {
            return new String[0];
        }
        int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return new String[0];
        }
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + delimiter.length());
        return new String[] {beforeDelimiter, afterDelimiter};
    }
    private static boolean hasLength(@Nullable String str) {
        return (str != null && !str.isEmpty());
    }


}
