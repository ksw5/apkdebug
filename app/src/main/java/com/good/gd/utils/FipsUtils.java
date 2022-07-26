package com.good.gd.utils;

/* loaded from: classes.dex */
public class FipsUtils {
    public static native void EnterFipsMode();

    public static native boolean isInFipsMode();

    public static native boolean isOpenSslFipsDefined();
}
