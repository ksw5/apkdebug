package com.good.gd.utils;

import com.good.gd.ndkproxy.GDLog;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public class GDAttestationLogger extends Logger {
    private static GDAttestationLogger instance;
    private static final Object lockObject = new Object();

    private GDAttestationLogger() {
        super("gd.logger", null);
    }

    public static GDAttestationLogger getInstance() {
        synchronized (lockObject) {
            if (instance == null) {
                instance = new GDAttestationLogger();
            }
        }
        return instance;
    }

    @Override // java.util.logging.Logger
    public void log(LogRecord logRecord) {
        Level level = logRecord.getLevel();
        if (level == Level.OFF) {
            GDLog.DBGPRINTF(19, logRecord.getMessage());
        } else if (level == Level.WARNING) {
            GDLog.DBGPRINTF(13, logRecord.getMessage());
        } else if (level == Level.INFO) {
            GDLog.DBGPRINTF(14, logRecord.getMessage());
        } else if (level == Level.ALL) {
            GDLog.DBGPRINTF(11, logRecord.getMessage());
        } else if (level == Level.SEVERE) {
            GDLog.DBGPRINTF(18, logRecord.getMessage());
        } else if (level == Level.CONFIG) {
            GDLog.DBGPRINTF(16, logRecord.getMessage());
        } else if (level == Level.FINE) {
            GDLog.DBGPRINTF(15, logRecord.getMessage());
        } else if (level == Level.FINER) {
            GDLog.DBGPRINTF(12, logRecord.getMessage());
        } else if (level != Level.FINEST) {
        } else {
            GDLog.DBGPRINTF(10, logRecord.getMessage());
        }
    }
}
