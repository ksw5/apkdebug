package com.good.gd.database.sqlite;

import android.database.Cursor;
import com.good.gd.database.sqlite.SQLiteDatabase;

/* loaded from: classes.dex */
public final class SQLiteDirectCursorDriver implements SQLiteCursorDriver {
    private final CancellationSignal mCancellationSignal;
    private final SQLiteDatabase mDatabase;
    private final String mEditTable;
    private SQLiteQuery mQuery;
    private final String mSql;

    public SQLiteDirectCursorDriver(SQLiteDatabase sQLiteDatabase, String str, String str2, CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        this.mEditTable = str2;
        this.mSql = str;
        this.mCancellationSignal = cancellationSignal;
    }

    @Override // com.good.gd.database.sqlite.SQLiteCursorDriver
    public void cursorClosed() {
    }

    @Override // com.good.gd.database.sqlite.SQLiteCursorDriver
    public void cursorDeactivated() {
    }

    @Override // com.good.gd.database.sqlite.SQLiteCursorDriver
    public void cursorRequeried(Cursor cursor) {
    }

    @Override // com.good.gd.database.sqlite.SQLiteCursorDriver
    public Cursor query(SQLiteDatabase.CursorFactory cursorFactory, String[] strArr) {
        Cursor newCursor;
        SQLiteQuery sQLiteQuery = new SQLiteQuery(this.mDatabase, this.mSql, this.mCancellationSignal);
        try {
            sQLiteQuery.bindAllArgsAsStrings(strArr);
            if (cursorFactory == null) {
                newCursor = new SQLiteCursor(this, this.mEditTable, sQLiteQuery);
            } else {
                newCursor = cursorFactory.newCursor(this.mDatabase, this, this.mEditTable, sQLiteQuery);
            }
            this.mQuery = sQLiteQuery;
            return newCursor;
        } catch (RuntimeException e) {
            sQLiteQuery.close();
            throw e;
        }
    }

    @Override // com.good.gd.database.sqlite.SQLiteCursorDriver
    public void setBindArguments(String[] strArr) {
        this.mQuery.bindAllArgsAsStrings(strArr);
    }

    public String toString() {
        return "SQLiteDirectCursorDriver: " + this.mSql;
    }
}
