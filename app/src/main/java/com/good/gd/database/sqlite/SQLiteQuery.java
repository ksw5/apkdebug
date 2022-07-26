package com.good.gd.database.sqlite;

import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public final class SQLiteQuery extends SQLiteProgram {
    private static final String TAG = "SQLiteQuery";
    private final CancellationSignal mCancellationSignal;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SQLiteQuery(SQLiteDatabase sQLiteDatabase, String str, CancellationSignal cancellationSignal) {
        super(sQLiteDatabase, str, null, cancellationSignal);
        this.mCancellationSignal = cancellationSignal;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int fillWindow(CursorWindow cursorWindow, int i, int i2, boolean z) {
        acquireReference();
        try {
            cursorWindow.acquireReference();
            try {
                int executeForCursorWindow = getSession().executeForCursorWindow(getSql(), getBindArgs(), cursorWindow, i, i2, z, getConnectionFlags(), this.mCancellationSignal);
                cursorWindow.releaseReference();
                return executeForCursorWindow;
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                throw e;
            } catch (SQLiteException e2) {
                GDLog.DBGPRINTF(12, "SQLiteQuery.fillWindow exception: " + e2.getMessage() + "; query: " + getSql());
                throw e2;
            }
        } finally {
            releaseReference();
        }
    }

    public String toString() {
        return "SQLiteQuery: " + getSql();
    }
}
