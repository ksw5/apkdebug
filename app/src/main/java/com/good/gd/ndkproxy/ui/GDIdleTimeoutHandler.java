package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.service.GDActivityTimerProvider;

/* loaded from: classes.dex */
public final class GDIdleTimeoutHandler {
    private static GDIdleTimeoutHandler _instance;

    public static synchronized GDIdleTimeoutHandler getInstance() {
        GDIdleTimeoutHandler gDIdleTimeoutHandler;
        synchronized (GDIdleTimeoutHandler.class) {
            if (_instance == null) {
                _instance = new GDIdleTimeoutHandler();
            }
            gDIdleTimeoutHandler = _instance;
        }
        return gDIdleTimeoutHandler;
    }

    public static native void idleTimerExceededNDK();

    public void idleTimerExceeded() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            idleTimerExceededNDK();
        }
    }

    public void initialize() {
        try {
            synchronized (NativeExecutionHandler.nativeLock) {
                ndkInit();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDIdleTimeoutHandler: Cannot initialize C++ peer", e);
        }
    }

    public boolean modifyIdleTimeInterval(int i) {
        GDLog.DBGPRINTF(16, "GDIdleTimeoutHandler.modifyIdleTimeInterval(" + i + ")\n");
        GDActivityTimerProvider.getInstance().getActivityTimer().setTimeoutValue(i);
        return true;
    }

    native void ndkInit();

    public void resumeIdleTimer() {
        GDActivityTimerProvider.getInstance().getActivityTimer().resumeTimer();
    }
}
