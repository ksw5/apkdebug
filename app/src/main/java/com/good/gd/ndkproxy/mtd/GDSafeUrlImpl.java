package com.good.gd.ndkproxy.mtd;

import com.good.gd.mtd.GDSafeUrl;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDSafeUrlImpl {
    private long _ref;
    private GDSafeUrl _safeUrl;

    public GDSafeUrlImpl(GDSafeUrl gDSafeUrl) {
        this._safeUrl = gDSafeUrl;
    }

    private native void NDK_checkUrl(String str, long j);

    public long checkUrl(String str) {
        this._ref = GDSafeUrlImplHelper.getInstance().getReferenceId();
        GDLog.DBGPRINTF(16, "SafeUrl: Apache: Impl::checkUrl App Ref: " + this._ref + "\n");
        NDK_checkUrl(str, this._ref);
        return this._ref;
    }

    public void onUrlCheckResponse(boolean z) {
        GDLog.DBGPRINTF(16, "SafeUrl: Apache: Impl::onUrlCheckResponse App Ref: " + this._ref + "\n");
        this._safeUrl.onCheckUrlResponse(z);
    }
}
