package com.good.gd.mtd;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.mtd.GDSafeUrlImpl;

/* loaded from: classes.dex */
public final class GDSafeUrl {
    private GDSafeUrlImpl _impl;
    private boolean _isUrlSafe = true;
    private Object lock = new Object();
    private boolean _notify = true;

    public GDSafeUrl() {
        this._impl = null;
        this._impl = new GDSafeUrlImpl(this);
    }

    public boolean checkUrl(String str) {
        synchronized (this.lock) {
            long checkUrl = this._impl.checkUrl(str);
            try {
                this.lock.wait();
            } catch (InterruptedException e) {
                GDLog.DBGPRINTF(12, "SafeUrl: .checkUrl Failed for App ref: " + checkUrl + "Exception:" + e + "\n");
                this._notify = false;
            }
        }
        return this._isUrlSafe;
    }

    public void onCheckUrlResponse(boolean z) {
        synchronized (this.lock) {
            this._isUrlSafe = z;
            this.lock.notify();
        }
    }
}
