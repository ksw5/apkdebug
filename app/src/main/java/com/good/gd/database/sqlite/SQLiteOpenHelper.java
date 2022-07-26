package com.good.gd.database.sqlite;

import android.content.Context;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public abstract class SQLiteOpenHelper {
    private static final boolean DEBUG_STRICT_READONLY = false;
    private static final String TAG = "SQLiteOpenHelper";
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mEnableWriteAheadLogging;
    private final DatabaseErrorHandler mErrorHandler;
    private final SQLiteDatabase.CursorFactory mFactory;
    private boolean mIsInitializing;
    private final String mName;
    private final int mNewVersion;

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, null);
    }

    private SQLiteDatabase getDatabaseLocked(boolean z) {
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null) {
            if (!sQLiteDatabase.isOpen()) {
                this.mDatabase = null;
            } else if (!z || !this.mDatabase.isReadOnly()) {
                return this.mDatabase;
            }
        }
        if (!this.mIsInitializing) {
            SQLiteDatabase sQLiteDatabase2 = this.mDatabase;
            try {
                this.mIsInitializing = true;
                if (sQLiteDatabase2 != null) {
                    if (z && sQLiteDatabase2.isReadOnly()) {
                        sQLiteDatabase2.reopenReadWrite();
                    }
                } else {
                    String str = this.mName;
                    if (str == null) {
                        sQLiteDatabase2 = SQLiteDatabase.create(null);
                    } else {
                        try {
                            sQLiteDatabase2 = SQLiteDatabase.openOrCreateDatabase(str, this.mFactory, this.mErrorHandler);
                        } catch (SQLiteException e) {
                            if (!z) {
                                GDLog.DBGPRINTF(12, "SQLiteOpenHelper.getDatabaseLocked Couldn't open " + this.mName + " for writing (will try read-only):", e);
                                sQLiteDatabase2 = SQLiteDatabase.openDatabase(this.mName, this.mFactory, 1, this.mErrorHandler);
                            } else {
                                throw e;
                            }
                        }
                    }
                }
                onConfigure(sQLiteDatabase2);
                int version = sQLiteDatabase2.getVersion();
                if (version != this.mNewVersion) {
                    if (!sQLiteDatabase2.isReadOnly()) {
                        sQLiteDatabase2.beginTransaction();
                        if (version == 0) {
                            onCreate(sQLiteDatabase2);
                        } else {
                            int i = this.mNewVersion;
                            if (version > i) {
                                onDowngrade(sQLiteDatabase2, version, i);
                            } else {
                                onUpgrade(sQLiteDatabase2, version, i);
                            }
                        }
                        sQLiteDatabase2.setVersion(this.mNewVersion);
                        sQLiteDatabase2.setTransactionSuccessful();
                        sQLiteDatabase2.endTransaction();
                    } else {
                        throw new SQLiteException("Can't upgrade read-only database from version " + sQLiteDatabase2.getVersion() + " to " + this.mNewVersion + ": " + this.mName);
                    }
                }
                onOpen(sQLiteDatabase2);
                if (sQLiteDatabase2.isReadOnly()) {
                    GDLog.DBGPRINTF(13, "SQLiteOpenHelper.getDatabaseLocked Opened " + this.mName + " in read-only mode");
                }
                this.mDatabase = sQLiteDatabase2;
                this.mIsInitializing = false;
                return sQLiteDatabase2;
            } catch (Throwable th) {
                this.mIsInitializing = false;
                if (sQLiteDatabase2 != null && sQLiteDatabase2 != this.mDatabase) {
                    sQLiteDatabase2.close();
                }
                throw th;
            }
        }
        throw new IllegalStateException("getDatabase called recursively");
    }

    public synchronized void close() {
        if (!this.mIsInitializing) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.mDatabase.close();
                this.mDatabase = null;
            }
        } else {
            throw new IllegalStateException("Closed during initialization");
        }
    }

    public String getDatabaseName() {
        return this.mName;
    }

    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(false);
        }
        return databaseLocked;
    }

    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(true);
        }
        return databaseLocked;
    }

    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        throw new SQLiteException("Can't downgrade database from version " + i + " to " + i2);
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public void setWriteAheadLoggingEnabled(boolean z) {
        synchronized (this) {
            if (this.mEnableWriteAheadLogging != z) {
                SQLiteDatabase sQLiteDatabase = this.mDatabase;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
                    if (z) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                }
                this.mEnableWriteAheadLogging = z;
            }
        }
    }

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        if (i >= 1) {
            if (str != null) {
                this.mContext = context;
                this.mName = str;
                this.mFactory = cursorFactory;
                this.mNewVersion = i;
                this.mErrorHandler = databaseErrorHandler;
                return;
            }
            throw new IllegalArgumentException("In GD Database name cannot be null");
        }
        throw new IllegalArgumentException("Version must be >= 1, was " + i);
    }
}
