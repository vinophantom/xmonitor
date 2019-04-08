package com.vino.xmonitor.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseableUtil {

    public static void close(Closeable c) throws IOException {
        if (c != null) {
            c.close();
        }
    }

}
