package com.good.gd.ndkproxy.native2javabridges.database;

import com.good.gd.database.sqlite.SQLiteDebug;

/* loaded from: classes.dex */
public final class SQLiteDebugBridge {

    /* loaded from: classes.dex */
    private static final class PagerStats extends SQLiteDebug.PagerStats {
        private PagerStats() {
        }
    }

    public static SQLiteDebug.PagerStats getDatabaseInfo() {
        PagerStats pagerStats = new PagerStats();
        nativeGetPagerStats(pagerStats);
        return pagerStats;
    }

    private static native void nativeGetPagerStats(PagerStats pagerStats);
}
