package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public final class GDActivateFingerprintViewHandler {
    private static final GDActivateFingerprintViewHandler _instance = new GDActivateFingerprintViewHandler();

    public static GDActivateFingerprintViewHandler getInstance() {
        return _instance;
    }

    public void openFingerprintSettingsUIFromApplication() {
        if (!CoreUI.requestBiometricSettingsUI()) {
            GDLog.DBGPRINTF(13, "GDActivateFingerprintViewHandler.openFingerprintSettingsUIFromApplication(): Unable to open the UI\n");
        } else {
            GDLog.DBGPRINTF(14, "GDActivateFingerprintViewHandler.openFingerprintSettingsUIFromApplication(): Opened\n");
        }
    }
}
