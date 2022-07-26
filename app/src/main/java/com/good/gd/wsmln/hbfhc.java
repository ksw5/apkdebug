package com.good.gd.wsmln;

import android.os.Debug;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;
import java.io.IOException;

/* loaded from: classes.dex */
public class hbfhc {
    private static hbfhc qkduk;
    private int dbjc = 0;

    private hbfhc() {
    }

    public static hbfhc jwxax() {
        if (qkduk == null) {
            qkduk = new hbfhc();
        }
        return qkduk;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x005e, code lost:
        com.good.gd.ndkproxy.GDLog.DBGPRINTF(16, "CustomUIControl::Init_dc\n");
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int qkduk() throws IOException {
        /*
            r8 = this;
            r0 = 16
            java.lang.String r1 = "CustomUIControl::Init_ctx\n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r0, r1)
            java.lang.String r1 = "GwX0vAboN4iukiXqj2JXKA=="
            java.lang.String r1 = com.blackberry.dynamics.ndkproxy.utils.UserPreferences.dbjc(r1)
            java.lang.String r2 = "pQatZwHpspG08v5QFZQz3IoIOfrP9cV+HnPhSSxsXJM="
            java.lang.String r2 = com.blackberry.dynamics.ndkproxy.utils.UserPreferences.dbjc(r2)
            r3 = -1000(0xfffffffffffffc18, float:NaN)
            r4 = 0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            r7.<init>(r2)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
            r2 = 1000(0x3e8, float:1.401E-42)
            r5.<init>(r6, r2)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L71
        L27:
            java.lang.String r2 = r5.readLine()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r4 = 0
            if (r2 == 0) goto L64
            int r6 = r2.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r7 = r1.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            if (r6 <= r7) goto L27
            int r6 = r1.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.String r4 = r2.substring(r4, r6)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            boolean r4 = r4.equalsIgnoreCase(r1)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            if (r4 == 0) goto L27
            int r4 = r1.length()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r4 = r4 + 1
            java.lang.String r2 = r2.substring(r4)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.String r2 = r2.trim()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.Integer r2 = java.lang.Integer.decode(r2)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            if (r2 <= 0) goto L27
            java.lang.String r1 = "CustomUIControl::Init_dc\n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r0, r1)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            goto L65
        L64:
            r3 = r4
        L65:
            r5.close()
            return r3
        L69:
            r0 = move-exception
            r4 = r5
            goto L7e
        L6c:
            r1 = move-exception
            r4 = r5
            goto L72
        L6f:
            r0 = move-exception
            goto L7e
        L71:
            r1 = move-exception
        L72:
            java.lang.String r1 = "CustomUIControl::Ipro_exp\n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r0, r1)     // Catch: java.lang.Throwable -> L7d
            if (r4 == 0) goto L7c
            r4.close()
        L7c:
            return r3
        L7d:
            r0 = move-exception
        L7e:
            if (r4 == 0) goto L83
            r4.close()
        L83:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.wsmln.hbfhc.qkduk():int");
    }

    public boolean dbjc() {
        int i;
        int i2;
        if (this.dbjc == 0) {
            try {
                if (qkduk() != 0) {
                    return true;
                }
                if (Debug.isDebuggerConnected()) {
                    GDLog.DBGPRINTF(16, "CustomUIControl::Init_db\n");
                    i = 2;
                } else {
                    i = 1;
                }
                this.dbjc = i;
                if (i == 2) {
                    return true;
                }
                if ((GTBaseContext.getInstance().getApplicationContext().getApplicationInfo().flags & 2) != 0) {
                    GDLog.DBGPRINTF(16, "CustomUIControl::Init_da\n");
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                this.dbjc = i2;
                if (i2 == 2) {
                    return true;
                }
            } catch (IOException e) {
                GDLog.DBGPRINTF(16, "CustomUIControl::Init_exp\n");
                return true;
            }
        }
        return this.dbjc == 2;
    }
}
