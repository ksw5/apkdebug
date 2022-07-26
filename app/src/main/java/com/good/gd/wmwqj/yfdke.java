package com.good.gd.wmwqj;

import com.blackberry.security.mtd.policy.MTDPolicy;
import com.good.gd.ndkproxy.util.DarkModeConfig;
import java.util.HashMap;

/* loaded from: classes.dex */
public class yfdke extends DarkModeConfig {
    private static yfdke dbjc;

    protected yfdke() {
        qkduk();
    }

    public static yfdke ztwf() {
        if (dbjc == null) {
            dbjc = new yfdke();
        }
        return dbjc;
    }

    public void dbjc(long j, String str, float f) {
        injectDataFloat(j, str, f);
    }

    public final boolean jwxax() {
        return isContainerLocked();
    }

    public void qkduk() {
        try {
            init();
        } catch (ClassNotFoundException e) {
        }
    }

    public final boolean wxau() {
        return isMTDEnabled();
    }

    public void dbjc(long j, String str, String str2) {
        injectDataString(j, str, str2);
    }

    public void dbjc(long j, String str, Object[] objArr) {
        injectDataArray(j, str, objArr);
    }

    public void dbjc(long j, String str, HashMap hashMap) {
        injectDataHashMap(j, str, hashMap);
    }

    public MTDPolicy dbjc() {
        return getMTDPolicy(false);
    }

    public final MTDPolicy dbjc(boolean z) {
        return getMTDPolicy(z);
    }
}
