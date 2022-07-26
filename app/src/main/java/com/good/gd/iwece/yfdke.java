package com.good.gd.iwece;

import com.good.gd.ndkproxy.GDLog;
import java.io.Closeable;

/* loaded from: classes.dex */
public class yfdke {
    private static yfdke dbjc;

    private yfdke() {
    }

    public static yfdke dbjc() {
        if (dbjc == null) {
            dbjc = new yfdke();
        }
        return dbjc;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00fc  */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v15, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v28, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v29, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v46, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v47, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v8, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v11, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r17v0, types: [com.good.gd.iwece.yfdke] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.Closeable[]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public String dbjc(String r18) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.iwece.yfdke.dbjc(java.lang.String):java.lang.String");
    }

    private void dbjc(String str, Closeable... closeableArr) {
        if (closeableArr == null) {
            return;
        }
        try {
            for (Closeable closeable : closeableArr) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(13, "CustomResourceLoader Error C" + str + e.toString() + "\n");
        }
    }
}
