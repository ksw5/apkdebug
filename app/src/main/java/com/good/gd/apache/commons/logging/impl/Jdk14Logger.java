package com.good.gd.apache.commons.logging.impl;

import androidx.core.os.EnvironmentCompat;
import com.good.gd.apache.commons.logging.Log;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public class Jdk14Logger implements Log, Serializable {
    protected static final Level dummyLevel = Level.FINE;
    protected transient Logger logger;
    protected String name;

    public Jdk14Logger(String str) {
        this.logger = null;
        this.name = null;
        this.name = str;
        this.logger = getLogger();
    }

    private void log(Level level, String str, Throwable th) {
        String str2;
        Logger logger = getLogger();
        if (logger.isLoggable(level)) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            String str3 = EnvironmentCompat.MEDIA_UNKNOWN;
            if (stackTrace == null || stackTrace.length <= 2) {
                str2 = str3;
            } else {
                StackTraceElement stackTraceElement = stackTrace[2];
                str3 = stackTraceElement.getClassName();
                str2 = stackTraceElement.getMethodName();
            }
            if (th == null) {
                logger.logp(level, str3, str2, str);
            } else {
                logger.logp(level, str3, str2, str, th);
            }
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void debug(Object obj) {
        log(Level.FINE, String.valueOf(obj), null);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void error(Object obj) {
        log(Level.SEVERE, String.valueOf(obj), null);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void fatal(Object obj) {
        log(Level.SEVERE, String.valueOf(obj), null);
    }

    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = Logger.getLogger(this.name);
        }
        return this.logger;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void info(Object obj) {
        log(Level.INFO, String.valueOf(obj), null);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isDebugEnabled() {
        return getLogger().isLoggable(Level.FINE);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isErrorEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isFatalEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isInfoEnabled() {
        return getLogger().isLoggable(Level.INFO);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isTraceEnabled() {
        return getLogger().isLoggable(Level.FINEST);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isWarnEnabled() {
        return getLogger().isLoggable(Level.WARNING);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void trace(Object obj) {
        log(Level.FINEST, String.valueOf(obj), null);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void warn(Object obj) {
        log(Level.WARNING, String.valueOf(obj), null);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void debug(Object obj, Throwable th) {
        log(Level.FINE, String.valueOf(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void error(Object obj, Throwable th) {
        log(Level.SEVERE, String.valueOf(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void fatal(Object obj, Throwable th) {
        log(Level.SEVERE, String.valueOf(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void info(Object obj, Throwable th) {
        log(Level.INFO, String.valueOf(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void trace(Object obj, Throwable th) {
        log(Level.FINEST, String.valueOf(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void warn(Object obj, Throwable th) {
        log(Level.WARNING, String.valueOf(obj), th);
    }
}
