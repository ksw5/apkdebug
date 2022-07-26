package com.good.gd.apache.commons.logging.impl;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.ndkproxy.GDLog;
import java.io.Serializable;

/* loaded from: classes.dex */
public class GDLogger implements Log, Serializable {
    private String message(Object obj) {
        if (obj != null && (obj instanceof MessageWithSessionId)) {
            MessageWithSessionId messageWithSessionId = (MessageWithSessionId) obj;
            if (messageWithSessionId.sessionId != null && messageWithSessionId.message != null) {
                return messageWithSessionId.sessionId + "   GDAPACHE:   " + messageWithSessionId.message + "\n";
            }
        }
        return "GDAPACHE:   " + String.valueOf(obj) + "\n";
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void debug(Object obj) {
        GDLog.DBGPRINTF(16, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void error(Object obj) {
        GDLog.DBGPRINTF(12, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void fatal(Object obj) {
        GDLog.DBGPRINTF(12, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void info(Object obj) {
        GDLog.DBGPRINTF(14, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isDebugEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isErrorEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isFatalEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isInfoEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isTraceEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public boolean isWarnEnabled() {
        return true;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void trace(Object obj) {
        GDLog.DBGPRINTF(16, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void warn(Object obj) {
        GDLog.DBGPRINTF(13, message(obj));
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void debug(Object obj, Throwable th) {
        GDLog.DBGPRINTF(16, message(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void error(Object obj, Throwable th) {
        GDLog.DBGPRINTF(12, message(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void fatal(Object obj, Throwable th) {
        GDLog.DBGPRINTF(12, message(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void info(Object obj, Throwable th) {
        GDLog.DBGPRINTF(14, message(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void trace(Object obj, Throwable th) {
        GDLog.DBGPRINTF(16, message(obj), th);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public void warn(Object obj, Throwable th) {
        GDLog.DBGPRINTF(13, message(obj), th);
    }
}
