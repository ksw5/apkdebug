package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public class GDClipboardCryptoUtil {
    private GDClipboardCryptoUtil() {
    }

    public static native String decryptString(String str);

    public static native String encryptString(String str);
}
