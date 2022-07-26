package com.good.gd.utils;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class LogUtils {
    public static void logStackTrace(int i, Exception exc) {
        StackTraceElement[] stackTrace = exc.getStackTrace();
        GDLog.DBGPRINTF(i, "stack: trace:");
        for (int i2 = 0; i2 < stackTrace.length; i2++) {
            GDLog.DBGPRINTF(i, "" + i2 + ": " + stackTrace[i2].toString());
        }
    }

    public static void logWhereAmI(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        GDLog.DBGPRINTF(16, str + " - stack trace:");
        for (int i = 0; i < stackTrace.length; i++) {
            GDLog.DBGPRINTF(16, "" + i + ": " + stackTrace[i].toString());
        }
    }

    public static void logStackTrace(Exception exc) {
        logStackTrace(16, exc);
    }
}
