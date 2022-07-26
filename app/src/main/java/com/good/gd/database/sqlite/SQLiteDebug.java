package com.good.gd.database.sqlite;

import android.util.Printer;
import com.good.gd.ndkproxy.native2javabridges.database.SQLiteDebugBridge;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class SQLiteDebug {
    public static final boolean DEBUG_LOG_SLOW_QUERIES = false;
    public static final boolean DEBUG_SQL_LOG = false;
    public static final boolean DEBUG_SQL_STATEMENTS = false;
    public static final boolean DEBUG_SQL_TIME = false;

    /* loaded from: classes.dex */
    public static class DbStats {
        public String cache;
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String str, long j, long j2, int i, int i2, int i3, int i4) {
            this.dbName = str;
            this.pageSize = j2 / 1024;
            this.dbSize = (j * j2) / 1024;
            this.lookaside = i;
            this.cache = i2 + "/" + i3 + "/" + i4;
        }
    }

    /* loaded from: classes.dex */
    public static class PagerStats {
        public ArrayList<DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

    private SQLiteDebug() {
    }

    public static void dump(Printer printer, String[] strArr) {
        boolean z = false;
        for (String str : strArr) {
            if (str.equals("-v")) {
                z = true;
            }
        }
        SQLiteDatabase.dumpAll(printer, z);
    }

    public static PagerStats getDatabaseInfo() {
        PagerStats databaseInfo = SQLiteDebugBridge.getDatabaseInfo();
        databaseInfo.dbStats = SQLiteDatabase.getDbStats();
        return databaseInfo;
    }

    public static final boolean shouldLogSlowQuery(long j) {
        return false;
    }

    public static boolean vmSqliteObjectLeaksEnabled() {
        return true;
    }
}
