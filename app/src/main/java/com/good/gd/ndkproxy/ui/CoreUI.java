package com.good.gd.ndkproxy.ui;

import com.good.gd.ui.GDQRCodeScanView;
import com.good.gd.ui.utils.permissions.ButtonClickListener;

/* loaded from: classes.dex */
public final class CoreUI {
    static {
        init();
    }

    public static native void closeActivationWebAuthLoadingUI();

    public static native void closeAuthDelegationBlockedUI();

    public static native void closeScreenCaptureAlertUI();

    public static native void closeUI(long j);

    private static native void init();

    public static native boolean isMandatoryActivationCompletionUIPending();

    public static void openSettings() {
        GDQRCodeScanView.openSettings();
    }

    public static native boolean requestActivationUI(String str, String str2);

    public static native boolean requestActivationWebAuthLoadingUI();

    public static native boolean requestActivationWebAuthUI(String str, String str2);

    public static native boolean requestAdvancedSettingsUI(String str, String str2, String str3);

    public static native boolean requestBISPrimerUI(int i);

    public static native boolean requestBISSettingsUI();

    public static native boolean requestBiometricSettingsUI();

    public static native boolean requestChangePasswordOptionalUI();

    public static native boolean requestIDPActivationErrorUI();

    public static native boolean requestInsecureWiFiPermissionsUI();

    public static native boolean requestLoadingUI();

    public static native boolean requestLocationServicesDisabledAlert(boolean z, long j);

    public static native boolean requestLogUploadUI();

    public static native boolean requestPermissionAlertUI(String str, String str2, boolean z, ButtonClickListener buttonClickListener);

    public static native boolean requestQRCodeNoPasswordUI();

    public static native boolean requestQRCodeNotSupportedUI();

    public static native boolean requestQRCodePermissionRequiredUI();

    public static native boolean requestQRCodeScanUI();

    public static native boolean requestScreenCaptureAlertUI();
}
