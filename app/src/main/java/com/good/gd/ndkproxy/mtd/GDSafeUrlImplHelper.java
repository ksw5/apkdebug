package com.good.gd.ndkproxy.mtd;

/* loaded from: classes.dex */
public class GDSafeUrlImplHelper {
    private static GDSafeUrlImplHelper _instance;
    private long _referenceId = 0;

    public GDSafeUrlImplHelper() {
        NDK_Init();
    }

    private native void NDK_Init();

    public static GDSafeUrlImplHelper getInstance() {
        if (_instance == null) {
            synchronized (GDSafeUrlImplHelper.class) {
                if (_instance == null) {
                    _instance = new GDSafeUrlImplHelper();
                }
            }
        }
        return _instance;
    }

    public long getReferenceId() {
        long j;
        synchronized (this) {
            j = this._referenceId + 1;
            this._referenceId = j;
        }
        return j;
    }
}
