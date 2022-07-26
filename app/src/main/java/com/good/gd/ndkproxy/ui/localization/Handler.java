package com.good.gd.ndkproxy.ui.localization;

import android.text.TextUtils;

/* loaded from: classes.dex */
public class Handler {
    public static native void applyCustomLocalization(String str, String str2, String str3);

    public static native void applyDefaultLocalization();

    public static synchronized void executeApplyCustomLocalization(String str, String str2, String str3) {
        synchronized (Handler.class) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                applyCustomLocalization(str, str2, str3);
            } else {
                throw new RuntimeException("param values should not be empty");
            }
        }
    }

    public static synchronized void executeApplyDefaultLocalization() {
        synchronized (Handler.class) {
            applyDefaultLocalization();
        }
    }

    public static synchronized void executeResetLocalization(String str) {
        synchronized (Handler.class) {
            if (!TextUtils.isEmpty(str)) {
                resetLocalization(str);
            } else {
                throw new RuntimeException("ID can not be empty");
            }
        }
    }

    public static native void resetLocalization(String str);
}
