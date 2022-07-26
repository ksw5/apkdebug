package com.good.gd.kloes;

import android.content.Context;
import com.good.gd.kloes.yfdke;
import com.good.gd.ndkproxy.GDLog;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class hbfhc {
    static {
        Pattern.compile("[\r\n]");
        Pattern.compile("GMT([-+]\\d{4})$");
    }

    public static void dbjc(Context context) {
        new yfdke.AsyncTaskC0013yfdke().execute(context);
    }

    public static int jwxax(Object obj, String str) {
        return dbjc(14, "", obj, str, null);
    }

    public static int qkduk(Object obj, String str) {
        return dbjc(12, "", obj, str, null);
    }

    public static int wxau(Object obj, String str) {
        return dbjc(16, "", obj, str, null);
    }

    public static int ztwf(Object obj, String str) {
        return dbjc(13, "", obj, str, null);
    }

    public static int dbjc(Object obj, String str) {
        return dbjc(16, "", obj, str, null);
    }

    public static int dbjc(Object obj, String str, String str2) {
        return dbjc(16, str, obj, str2, null);
    }

    public static int dbjc(Class cls, String str) {
        return dbjc(13, "", cls, str, null);
    }

    public static int dbjc(Object obj, String str, String str2, Throwable th) {
        return dbjc(12, str, obj, str2, th);
    }

    private static int dbjc(int i, String str, Object obj, String str2, Throwable th) {
        if (com.blackberry.bis.core.yfdke.ugfcv() != null) {
            try {
                String dbjc = fdyxd.dbjc(true, obj, str, str2, th);
                GDLog.DBGPRINTF(i, dbjc);
                return dbjc.length();
            } catch (Exception e) {
                return 0;
            }
        }
        return -1;
    }
}
