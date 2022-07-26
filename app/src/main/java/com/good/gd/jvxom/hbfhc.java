package com.good.gd.jvxom;

import com.good.gd.error.GDError;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.util.PrefsConfig;

/* loaded from: classes.dex */
public class hbfhc extends PrefsConfig {
    public void dbjc() throws Exception {
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (GDError e) {
            com.good.gd.qkz.hbfhc.qkduk(new Throwable(), "DCAI", "" + e);
            throw e;
        } catch (Throwable th) {
            com.good.gd.qkz.hbfhc.dbjc("DCAI", "", th);
        }
    }

    public static void dbjc(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", "");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                PrefsConfig.initDeviceInfo(str, str2, str3, str4, str5, str6, i, str7, str8, str9, str10, str11, str12, str13, str14);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str2);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str3);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str4);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str5);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str6);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + i);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str8);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str9);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str10);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str11);
                com.good.gd.qkz.hbfhc.dbjc(new Throwable(), "DCAI", " " + str12);
            }
        } catch (Exception e) {
            com.good.gd.qkz.hbfhc.dbjc("DCAI", "", e);
        }
    }
}
