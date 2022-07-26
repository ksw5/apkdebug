package com.good.gd.kloes;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import android.util.Log;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.pmoiy;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ehnkx {
    private static boolean dbjc = false;
    private static final String[] jwxax = {"com.blackberry.example.analytics", "com.analytics.android.analyticstest", "com.blackberry.bis.standalone.app"};
    private static boolean qkduk = false;

    public static void dbjc(Class cls, String str, String str2) {
        dbjc(2, cls != null ? cls.getSimpleName() : "", str, str2);
    }

    public static void jwxax(Class cls, String str) {
        dbjc(2, cls != null ? cls.getSimpleName() : "", "", str);
    }

    public static void qkduk(Class cls, String str) {
        dbjc(4, cls != null ? cls.getSimpleName() : "", "", str);
    }

    public static void wxau(Class cls, String str) {
        dbjc(5, cls != null ? cls.getSimpleName() : "", "", str);
    }

    public static void dbjc(Class cls, String str) {
        dbjc(3, cls != null ? cls.getSimpleName() : "", "", str);
    }

    public static void jwxax(String str, String str2) {
        dbjc(5, str, "", str2);
    }

    public static void qkduk(String str, String str2) {
        dbjc(2, str, "", str2);
    }

    public static void dbjc(String str, String str2) {
        dbjc(4, str, "", str2);
    }

    private static void dbjc(int i, String str, String str2, String str3) {
        int identifier;
        String[] strArr;
        if (qkduk) {
            pmoiy dbjc2 = com.blackberry.bis.core.hbfhc.dbjc();
            if (!dbjc) {
                return;
            }
            com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) dbjc2;
            if (!fdyxdVar.muee() && !fdyxdVar.sbesx().booleanValue()) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(Process.myTid());
            sb.append(" - ");
            sb.append(str);
            sb.append(":");
            sb.append("SENSITIVE");
            sb.append(" - ");
            sb.append(str3);
            String sb2 = sb.toString();
            if (3000 >= sb2.length()) {
                dbjc(i, (str2 + " " + sb2).trim());
                return;
            }
            if (sb2.trim().length() <= 0) {
                strArr = new String[0];
            } else if (3000 >= sb2.length()) {
                strArr = new String[]{str2 + " " + sb2};
            } else {
                int length = sb2.length() / PathInterpolatorCompat.MAX_NUM_POINTS;
                String[] strArr2 = new String[length + 1];
                if (str2 == null) {
                    str2 = "";
                }
                int i2 = 0;
                while (i2 <= length) {
                    int i3 = i2 * PathInterpolatorCompat.MAX_NUM_POINTS;
                    int i4 = i2 + 1;
                    int i5 = i4 * PathInterpolatorCompat.MAX_NUM_POINTS;
                    if (i5 > sb2.length()) {
                        i5 = sb2.length();
                    }
                    strArr2[i2] = "Chunk " + i4 + " " + str2 + "--------------->" + sb2.substring(i3, i5);
                    i2 = i4;
                }
                strArr = strArr2;
            }
            for (String str4 : strArr) {
                dbjc(i, str4.trim());
            }
            return;
        }
        Context jwxax2 = BlackberryAnalyticsCommon.rynix().jwxax();
        Resources resources = jwxax2.getResources();
        String packageName = jwxax2.getPackageName();
        if (resources != null && true != com.good.gd.whhmi.yfdke.qkduk(packageName) && (identifier = resources.getIdentifier("BISSensLog", "bool", packageName)) != 0 && resources.getBoolean(identifier) && Arrays.asList(jwxax).contains(packageName)) {
            dbjc = true;
        }
        qkduk = true;
        dbjc(i, str, str2, str3);
    }

    private static void dbjc(int i, String str) {
        switch (i) {
            case 2:
                Log.v("ANALYTICS_LIB", str);
                return;
            case 3:
                Log.d("ANALYTICS_LIB", str);
                return;
            case 4:
                Log.i("ANALYTICS_LIB", str);
                return;
            case 5:
                Log.w("ANALYTICS_LIB", str);
                return;
            case 6:
                Log.e("ANALYTICS_LIB", str);
                return;
            default:
                return;
        }
    }
}
