package com.good.gd.database.sqlite;

import android.content.res.Resources;
import android.os.StatFs;

/* loaded from: classes.dex */
public final class SQLiteGlobal {
    private static final String TAG = "SQLiteGlobal";
    private static long sDefaultPageSize;
    private static final Object sLock = new Object();

    private SQLiteGlobal() {
    }

    public static String getDefaultJournalMode() {
        return getSystemString(getId("db_default_journal_mode", "string", "android"), "PERSIST");
    }

    public static long getDefaultPageSize() {
        long j;
        synchronized (sLock) {
            if (sDefaultPageSize == 0) {
                sDefaultPageSize = new StatFs("/data").getBlockSizeLong();
            }
            j = sDefaultPageSize;
        }
        return j;
    }

    public static String getDefaultSyncMode() {
        return getSystemString(getId("db_default_sync_mode", "string", "android"), "FULL");
    }

    private static int getId(String str, String str2, String str3) {
        return Resources.getSystem().getIdentifier(str, str2, str3);
    }

    public static int getIdleConnectionTimeout() {
        return getSystemInteger(getId("db_default_idle_connection_timeout", "integer", "android"), 60000);
    }

    public static int getJournalSizeLimit() {
        return getSystemInteger(getId("db_journal_size_limit", "integer", "android"), 524288);
    }

    private static int getSystemInteger(int i, int i2) {
        try {
            return Resources.getSystem().getInteger(i);
        } catch (Resources.NotFoundException e) {
            return i2;
        }
    }

    private static String getSystemString(int i, String str) {
        try {
            return Resources.getSystem().getString(i);
        } catch (Resources.NotFoundException e) {
            return str;
        }
    }

    public static int getWALAutoCheckpoint() {
        return Math.max(1, getSystemInteger(getId("db_wal_autocheckpoint", "integer", "android"), 100));
    }

    public static int getWALConnectionPoolSize() {
        return Math.max(2, getSystemInteger(getId("db_connection_pool_size", "integer", "android"), 4));
    }

    public static String getWALSyncMode() {
        return getSystemString(getId("db_wal_sync_mode", "string", "android"), "FULL");
    }

    private static native int nativeReleaseMemory();

    public static int releaseMemory() {
        return nativeReleaseMemory();
    }
}
