package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public class GdJniIdCache {
    public static void initialize() {
        synchronized (NativeExecutionHandler.nativeLock) {
            ndkInit();
        }
    }

    private static native void ndkInit();
}
