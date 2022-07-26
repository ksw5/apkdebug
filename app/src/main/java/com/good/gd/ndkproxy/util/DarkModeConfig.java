package com.good.gd.ndkproxy.util;

import com.blackberry.security.mtd.MTDScan$Status;
import com.blackberry.security.mtd.MTDScan$Type;
import com.blackberry.security.mtd.policy.MTDPolicy;
import java.util.Date;
import java.util.HashMap;

/* loaded from: classes.dex */
public class DarkModeConfig {
    protected static MTDPolicy mMTDPolicy;

    protected native MTDPolicy getMTDPolicy();

    /* JADX INFO: Access modifiers changed from: protected */
    public MTDPolicy getMTDPolicy(boolean z) {
        if (mMTDPolicy == null || z) {
            mMTDPolicy = getMTDPolicy();
        }
        if (mMTDPolicy == null) {
            mMTDPolicy = new MTDPolicy();
        }
        return mMTDPolicy;
    }

    protected MTDScan$Status getScanStatus(MTDScan$Type mTDScan$Type) {
        return MTDScan$Status.dbjc(getScanStatusValueByTypeValue(mTDScan$Type.dbjc()));
    }

    protected native int getScanStatusValueByTypeValue(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void init() throws ClassNotFoundException;

    /* JADX INFO: Access modifiers changed from: protected */
    public native void injectDataArray(long j, String str, Object[] objArr);

    protected native void injectDataDate(long j, String str, Date date);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void injectDataFloat(long j, String str, float f);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void injectDataHashMap(long j, String str, HashMap hashMap);

    protected native void injectDataInt(long j, String str, int i);

    protected native void injectDataLong(long j, String str, long j2);

    /* JADX INFO: Access modifiers changed from: protected */
    public native void injectDataString(long j, String str, String str2);

    protected native boolean isCompliant();

    /* JADX INFO: Access modifiers changed from: protected */
    public native boolean isContainerLocked();

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isMTDEnabled() {
        return getMTDPolicy(false).isEnabled();
    }
}
