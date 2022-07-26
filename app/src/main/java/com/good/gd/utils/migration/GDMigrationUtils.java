package com.good.gd.utils.migration;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDMigrationUtils {
    public static boolean isAuthKeyValid(byte[] bArr) {
        try {
            return validateAuthKey(bArr);
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDMigrationUtils isAuthKeyValid", e);
            return false;
        }
    }

    private static native void signalMigration(String str, String str2);

    public static void signalMigrationStart(String str, String str2) {
        signalMigration(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native boolean startMigration(byte[] bArr, String str);

    private static native boolean validateAuthKey(byte[] bArr);
}
