package com.good.gd.ndkproxy.util;

/* loaded from: classes.dex */
public class PrefsConfig {
    protected static native String getApplicationId();

    protected static native String getDeviceId();

    protected static native String getUserId();

    /* JADX INFO: Access modifiers changed from: protected */
    public static native void initDeviceInfo(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14);

    protected static native void setLegacyProvID(String str);

    protected native void finishNative();

    /* JADX INFO: Access modifiers changed from: protected */
    public native void ndkInit();

    protected native void obtainSharedUserId();

    protected native void setProvDeviceId(String str);

    protected native void setUserId(String str);
}
