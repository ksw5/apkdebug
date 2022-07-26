package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.NativeExecutionHandler;

/* loaded from: classes.dex */
public final class GDRemoteLock {
    private static GDRemoteLock _instance;

    public static native void executeRemoteLock();

    public static native void executeRemoteLockPublicRequest();

    public static synchronized GDRemoteLock getInstance() {
        GDRemoteLock gDRemoteLock;
        synchronized (GDRemoteLock.class) {
            if (_instance == null) {
                _instance = new GDRemoteLock();
            }
            gDRemoteLock = _instance;
        }
        return gDRemoteLock;
    }

    public static native void handleAuthDelegateFailure();

    public static native boolean passwordValidationComplete(long j);

    public static native void tempUnlock();

    public static native boolean validatePassword(String str);

    public void handleAuthDelegateFailedRequest() {
        synchronized (NativeExecutionHandler.nativeLock) {
            handleAuthDelegateFailure();
        }
    }

    public void handleRemoteLockPublicAPIRequest() {
        synchronized (NativeExecutionHandler.nativeLock) {
            executeRemoteLockPublicRequest();
        }
    }

    public void handleRemoteLockRequest() {
        synchronized (NativeExecutionHandler.nativeLock) {
            executeRemoteLock();
        }
    }

    public void tempUnlockRequest() {
        synchronized (NativeExecutionHandler.nativeLock) {
            tempUnlock();
        }
    }
}
