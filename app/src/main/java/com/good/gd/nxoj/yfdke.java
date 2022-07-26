package com.good.gd.nxoj;

/* loaded from: classes.dex */
public class yfdke {
    private static yfdke dbjc;

    private yfdke() {
    }

    public static yfdke dbjc() {
        if (dbjc == null) {
            synchronized (yfdke.class) {
                if (dbjc == null) {
                    dbjc = new yfdke();
                }
            }
        }
        return dbjc;
    }

    public void dbjc(String str, String str2, String str3, boolean z, com.good.gd.bmuat.hbfhc hbfhcVar) {
        ((com.good.gd.ooyuj.hbfhc) com.blackberry.bis.core.yfdke.wxau()).dbjc(str, str2, str3, z, hbfhcVar);
    }

    public void dbjc(String str, String str2, String str3) {
        ((com.good.gd.ooyuj.hbfhc) com.blackberry.bis.core.yfdke.wxau()).dbjc(str, str2, str3);
    }
}
