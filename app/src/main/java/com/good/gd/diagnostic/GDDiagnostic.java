package com.good.gd.diagnostic;

import java.util.List;

/* loaded from: classes.dex */
public final class GDDiagnostic {
    private static GDDiagnostic _instance;
    private com.good.gd.ndkproxy.diagnostic.GDDiagnostic mImpl = new com.good.gd.ndkproxy.diagnostic.GDDiagnostic();

    private GDDiagnostic() {
    }

    public static synchronized GDDiagnostic getInstance() {
        GDDiagnostic gDDiagnostic;
        synchronized (GDDiagnostic.class) {
            if (_instance == null) {
                _instance = new GDDiagnostic();
            }
            gDDiagnostic = _instance;
        }
        return gDDiagnostic;
    }

    public Integer checkApplicationServerReachability(String str, int i, boolean z, boolean z2, int i2) {
        return this.mImpl.checkApplicationServerReachability(str, i, z, z2, i2);
    }

    public List<Integer> checkManagementConsoleReachability(int i) {
        return this.mImpl.checkManagementConsoleReachability(i);
    }

    public String getCurrentSettings() {
        return this.mImpl.getCurrentSettings();
    }

    public void setReachabilityListener(GDDiagnosticReachabilityListener gDDiagnosticReachabilityListener) {
        this.mImpl.setReachabilityListener(gDDiagnosticReachabilityListener);
    }
}
