package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public class GDLangInterface {
    public static synchronized String lookup(String str) {
        String nativeLookup;
        synchronized (GDLangInterface.class) {
            nativeLookup = nativeLookup(str);
        }
        return nativeLookup;
    }

    private static native String nativeLookup(String str);
}
