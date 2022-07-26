package com.good.gd.database.sqlite;

import com.good.gd.ndkproxy.GDLog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SQLiteCursor extends AbstractWindowedCursor {
    static final int NO_COUNT = -1;
    static final String TAG = "SQLiteCursor";
    private Map<String, Integer> mColumnNameMap;
    private final String[] mColumns;
    private int mCount;
    private int mCursorWindowCapacity;
    private final SQLiteCursorDriver mDriver;
    private final String mEditTable;
    private final SQLiteQuery mQuery;
    private final Throwable mStackTrace;

    @Deprecated
    public SQLiteCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
        this(sQLiteCursorDriver, str, sQLiteQuery);
    }

    private void fillWindow(int i) {
        clearOrCreateWindow(getDatabase().getPath());
        try {
            if (this.mCount == -1) {
                this.mCount = this.mQuery.fillWindow(this.mWindow, DatabaseUtils.cursorPickFillWindowStartPosition(i, 0), i, true);
                this.mCursorWindowCapacity = this.mWindow.getNumRows();
                return;
            }
            this.mQuery.fillWindow(this.mWindow, DatabaseUtils.cursorPickFillWindowStartPosition(i, this.mCursorWindowCapacity), i, false);
        } catch (RuntimeException e) {
            closeWindow();
            throw e;
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        synchronized (this) {
            this.mQuery.close();
            this.mDriver.cursorClosed();
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        this.mDriver.cursorDeactivated();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.database.sqlite.AbstractCursor
    public void finalize() {
        try {
            if (this.mWindow != null) {
                if (this.mStackTrace != null) {
                    String sql = this.mQuery.getSql();
                    int length = sql.length();
                    StringBuilder append = new StringBuilder().append("Finalizing a Cursor that has not been deactivated or closed. database = ").append(this.mQuery.getDatabase().getLabel()).append(", table = ").append(this.mEditTable).append(", query = ");
                    if (length > 1000) {
                        length = 1000;
                    }
                    GDLog.DBGPRINTF(18, append.append(sql.substring(0, length)).append("").append(this.mStackTrace).toString());
                }
                close();
            }
        } finally {
            super.finalize();
        }
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getColumnIndex(String str) {
        if (this.mColumnNameMap == null) {
            String[] strArr = this.mColumns;
            int length = strArr.length;
            HashMap hashMap = new HashMap(length, 1.0f);
            for (int i = 0; i < length; i++) {
                hashMap.put(strArr[i], Integer.valueOf(i));
            }
            this.mColumnNameMap = hashMap;
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            GDLog.DBGPRINTF(12, "requesting column name with table name -- " + str, new Exception());
            str = str.substring(lastIndexOf + 1);
        }
        Integer num = this.mColumnNameMap.get(str);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        return this.mColumns;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public int getCount() {
        if (this.mCount == -1) {
            fillWindow(0);
        }
        return this.mCount;
    }

    public SQLiteDatabase getDatabase() {
        return this.mQuery.getDatabase();
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, com.good.gd.database.sqlite.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        CursorWindow cursorWindow = this.mWindow;
        if (cursorWindow == null || i2 < cursorWindow.getStartPosition() || i2 >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
            fillWindow(i2);
            return true;
        }
        return true;
    }

    @Override // com.good.gd.database.sqlite.AbstractCursor, android.database.Cursor
    public boolean requery() {
        if (isClosed()) {
            return false;
        }
        synchronized (this) {
            if (!this.mQuery.getDatabase().isOpen()) {
                return false;
            }
            if (this.mWindow != null) {
                this.mWindow.clear();
            }
            this.mPos = -1;
            this.mCount = -1;
            this.mDriver.cursorRequeried(this);
            try {
                return super.requery();
            } catch (IllegalStateException e) {
                GDLog.DBGPRINTF(13, "SQLiteCursor.requery() failed " + e.getMessage(), e);
                return false;
            }
        }
    }

    public void setSelectionArguments(String[] strArr) {
        this.mDriver.setBindArguments(strArr);
    }

    @Override // com.good.gd.database.sqlite.AbstractWindowedCursor
    public void setWindow(CursorWindow cursorWindow) {
        super.setWindow(cursorWindow);
        this.mCount = -1;
    }

    public SQLiteCursor(SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
        this.mCount = -1;
        if (sQLiteQuery != null) {
            this.mStackTrace = null;
            this.mDriver = sQLiteCursorDriver;
            this.mEditTable = str;
            this.mColumnNameMap = null;
            this.mQuery = sQLiteQuery;
            String[] columnNames = sQLiteQuery.getColumnNames();
            this.mColumns = columnNames;
            this.mRowIdColumnIndex = DatabaseUtils.findRowIdColumnIndex(columnNames);
            return;
        }
        throw new IllegalArgumentException("query object cannot be null");
    }
}
