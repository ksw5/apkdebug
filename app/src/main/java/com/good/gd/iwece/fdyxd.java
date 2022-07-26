package com.good.gd.iwece;

import com.good.gd.ndkproxy.GDLog;
import com.good.gt.util.OSCheckUtils;
import java.util.Map;

/* loaded from: classes.dex */
public final class fdyxd {
    private static fdyxd wxau;
    private String dbjc = "n";
    private String qkduk = "s";
    private String jwxax = "j";

    private fdyxd() {
    }

    public static fdyxd dbjc() {
        if (wxau == null) {
            wxau = new fdyxd();
        }
        return wxau;
    }

    public String dbjc(String str, String str2) {
        String str3;
        if (str2.equals(this.dbjc)) {
            if (str == null || str.isEmpty()) {
                return "";
            }
            Map<String, String> nativeProperties = OSCheckUtils.getInstance().getNativeProperties();
            GDLog.DBGPRINTF(19, "CustomLayoutControl attr = " + str + "\n");
            String str4 = nativeProperties.get(str);
            return str4 == null ? "" : str4;
        } else if (str2.equals(this.qkduk)) {
            if (str == null || str.isEmpty()) {
                return "";
            }
            GDLog.DBGPRINTF(19, "CustomLayoutControl attr = " + str + "\n");
            String property = System.getProperty(str);
            return property == null ? "" : property;
        } else if (!str2.equals(this.jwxax)) {
            return "";
        } else {
            GDLog.DBGPRINTF(16, "CustomLayoutControl attr = " + str + "\n");
            try {
                int lastIndexOf = str.lastIndexOf(46);
                String substring = str.substring(lastIndexOf + 1);
                Class<?> cls = Class.forName(str.substring(0, lastIndexOf));
                str3 = cls.getDeclaredField(substring).get(cls).toString();
            } catch (Throwable th) {
                GDLog.DBGPRINTF(12, " CustomLayoutControl error  = " + str + "\n");
                str3 = "";
            }
            return str3 == null ? "" : str3;
        }
    }
}
