package com.good.gd.ndkproxy;

import com.good.gd.utils.GDNDKLibraryLoader;

/* loaded from: classes.dex */
public class CppDirectives {
    public static boolean GD_ENTERPRISE_UNIT_TEST;
    public static boolean GD_SECURE_CONTAINER;
    public static boolean GD_USE_ENTERPRISE_PROVISIONING;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
        init();
    }

    static native void init();
}
