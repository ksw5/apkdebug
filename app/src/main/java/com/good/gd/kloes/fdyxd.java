package com.good.gd.kloes;

import android.os.Process;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes.dex */
public class fdyxd {
    public static String dbjc(boolean z, Object obj, String str, String str2, Throwable th) {
        int i;
        String stringWriter;
        int i2 = 0;
        if (th == null) {
            if (str2 != null) {
                i2 = str2.length();
            }
            i = i2 + 64;
        } else {
            if (str2 != null) {
                i2 = str2.length();
            }
            i = i2 + 512;
        }
        StringBuilder sb = new StringBuilder(i);
        sb.append("ANALYTICS_LIB").append(":");
        if (z) {
            if (obj != null) {
                if (obj instanceof Class) {
                    sb.append(((Class) obj).getSimpleName());
                } else {
                    sb.append(obj.getClass().getSimpleName());
                }
            }
            sb.append(":");
        }
        sb.append(Process.myTid());
        sb.append(":");
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        } else {
            sb.append(" ");
        }
        sb.append(str2);
        sb.append("\n");
        if (th != null) {
            StringBuilder append = sb.append("StackTrace:\n");
            if (th == null) {
                stringWriter = "";
            } else {
                StringWriter stringWriter2 = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter2));
                stringWriter = stringWriter2.toString();
            }
            append.append(stringWriter);
        }
        return sb.toString();
    }
}
