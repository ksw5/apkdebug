package com.good.gd.database.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.CancellationSignal;
import android.os.Looper;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Pair;
import android.util.Printer;
import com.good.gd.database.DefaultDatabaseErrorHandler;
import com.good.gd.database.SQLException;
import com.good.gd.database.sqlite.SQLiteDebug;
import com.good.gd.error.GDError;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.file.File;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDSDKState;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class SQLiteDatabase extends SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final boolean DEBUG_CLOSE_IDLE_CONNECTIONS = false;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "SQLiteDatabase";
    private final SQLiteDatabaseConfiguration mConfigurationLocked;
    private SQLiteConnectionPool mConnectionPoolLocked;
    private final CursorFactory mCursorFactory;
    private final DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<>();
    private static final String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private final ThreadLocal<SQLiteSession> mThreadSession = new ThreadLocal<SQLiteSession>() { // from class: com.good.gd.database.sqlite.SQLiteDatabase.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        /* renamed from: initialValue */
        public SQLiteSession mo291initialValue() {
            return SQLiteDatabase.this.createSession();
        }
    };
    private final Object mLock = new Object();

    /* loaded from: classes.dex */
    public interface CursorFactory {
        Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery);
    }

    /* loaded from: classes.dex */
    public interface CustomFunction {
        void callback(String[] strArr);
    }

    /* loaded from: classes.dex */
    public static final class OpenParams {
        private final CursorFactory mCursorFactory;
        private final DatabaseErrorHandler mErrorHandler;
        private long mIdleConnectionTimeout;
        private final int mLookasideSlotCount;
        private final int mLookasideSlotSize;
        private final int mOpenFlags;

        public CursorFactory getCursorFactory() {
            return this.mCursorFactory;
        }

        public DatabaseErrorHandler getErrorHandler() {
            return this.mErrorHandler;
        }

        public long getIdleConnectionTimeout() {
            return this.mIdleConnectionTimeout;
        }

        public int getLookasideSlotCount() {
            return this.mLookasideSlotCount;
        }

        public int getLookasideSlotSize() {
            return this.mLookasideSlotSize;
        }

        public int getOpenFlags() {
            return this.mOpenFlags;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        private OpenParams(int i, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler, int i2, int i3, long j) {
            this.mOpenFlags = i;
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = databaseErrorHandler;
            this.mLookasideSlotSize = i2;
            this.mLookasideSlotCount = i3;
            this.mIdleConnectionTimeout = j;
        }

        /* loaded from: classes.dex */
        public static final class Builder {
            private CursorFactory mCursorFactory;
            private DatabaseErrorHandler mErrorHandler;
            private long mIdleConnectionTimeout;
            private int mLookasideSlotCount;
            private int mLookasideSlotSize;
            private int mOpenFlags;

            public Builder() {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
            }

            public Builder addOpenFlags(int i) {
                this.mOpenFlags = i | this.mOpenFlags;
                return this;
            }

            public OpenParams build() {
                return new OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout);
            }

            public boolean isWriteAheadLoggingEnabled() {
                return (this.mOpenFlags & SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING) != 0;
            }

            public Builder removeOpenFlags(int i) {
                this.mOpenFlags = (~i) & this.mOpenFlags;
                return this;
            }

            public Builder setCursorFactory(CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public Builder setErrorHandler(DatabaseErrorHandler databaseErrorHandler) {
                this.mErrorHandler = databaseErrorHandler;
                return this;
            }

            public Builder setIdleConnectionTimeout(long j) {
                this.mIdleConnectionTimeout = j;
                return this;
            }

            public Builder setLookasideConfig(int i, int i2) {
                this.mLookasideSlotSize = i;
                this.mLookasideSlotCount = i2;
                return this;
            }

            public Builder setOpenFlags(int i) {
                this.mOpenFlags = i;
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean z) {
                if (z) {
                    addOpenFlags(SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
                } else {
                    removeOpenFlags(SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
                }
            }

            public Builder(OpenParams openParams) {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
                this.mLookasideSlotSize = openParams.mLookasideSlotSize;
                this.mLookasideSlotCount = openParams.mLookasideSlotCount;
                this.mOpenFlags = openParams.mOpenFlags;
                this.mCursorFactory = openParams.mCursorFactory;
                this.mErrorHandler = openParams.mErrorHandler;
            }
        }
    }

    private SQLiteDatabase(String str, int i, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler, int i2, int i3, long j) {
        GDSDKState.getInstance().checkAuthorized();
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = databaseErrorHandler == null ? new DefaultDatabaseErrorHandler() : databaseErrorHandler;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = new SQLiteDatabaseConfiguration(str, i);
        this.mConfigurationLocked = sQLiteDatabaseConfiguration;
        sQLiteDatabaseConfiguration.lookasideSlotSize = i2;
        sQLiteDatabaseConfiguration.lookasideSlotCount = i3;
        sQLiteDatabaseConfiguration.idleConnectionTimeoutMs = (sQLiteDatabaseConfiguration.isInMemoryDb() || j < 0) ? Long.MAX_VALUE : j;
    }

    private void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        synchronized (this.mLock) {
            SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
            if (sQLiteConnectionPool != null) {
                sQLiteConnectionPool.collectDbStats(arrayList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CancellationSignal convertToGDCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal == null) {
            return null;
        }
        final CancellationSignal cancellationSignal2 = new CancellationSignal();
        if (cancellationSignal.isCanceled()) {
            cancellationSignal2.cancel();
        }
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.good.gd.database.sqlite.SQLiteDatabase.3
            @Override // android.os.CancellationSignal.OnCancelListener
            public void onCancel() {
                CancellationSignal.this.cancel();
            }
        });
        return cancellationSignal2;
    }

    public static SQLiteDatabase create(CursorFactory cursorFactory) throws GDNotAuthorizedError {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, cursorFactory, CREATE_IF_NECESSARY);
    }

    public static SQLiteDatabase createInMemory(OpenParams openParams) {
        return openDatabase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH, openParams.toBuilder().addOpenFlags(CREATE_IF_NECESSARY).build());
    }

    public static boolean deleteDatabase(File file) {
        if (file != null) {
            boolean delete = file.delete() | false | new File(file.getPath() + "-journal").delete() | new File(file.getPath() + "-shm").delete() | new File(file.getPath() + "-wal").delete();
            java.io.File parentFile = file.getParentFile();
            if (parentFile != null) {
                final String str = file.getName() + "-mj";
                java.io.File[] listFiles = parentFile.listFiles(new FileFilter() { // from class: com.good.gd.database.sqlite.SQLiteDatabase.2
                    @Override // java.io.FileFilter
                    public boolean accept(java.io.File file2) {
                        return file2.getName().startsWith(str);
                    }
                });
                if (listFiles != null) {
                    for (java.io.File file2 : listFiles) {
                        delete |= file2.delete();
                    }
                }
            }
            return delete;
        }
        throw new IllegalArgumentException("file must not be null");
    }

    private void dispose(boolean z) {
        SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            sQLiteConnectionPool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (!z) {
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
            if (sQLiteConnectionPool == null) {
                return;
            }
            sQLiteConnectionPool.close();
        }
    }

    private void dump(Printer printer, boolean z) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                printer.println("");
                this.mConnectionPoolLocked.dump(printer, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void dumpAll(Printer printer, boolean z) {
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().dump(printer, z);
        }
    }

    private int executeSql(String str, Object[] objArr) throws SQLException {
        acquireReference();
        try {
            if (DatabaseUtils.getSqlStatementType(str) == 3) {
                boolean z = false;
                synchronized (this.mLock) {
                    if (!this.mHasAttachedDbsLocked) {
                        this.mHasAttachedDbsLocked = true;
                        z = true;
                    }
                }
                if (z) {
                    disableWriteAheadLogging();
                }
            }
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, str, objArr);
            int executeUpdateDelete = sQLiteStatement.executeUpdateDelete();
            sQLiteStatement.close();
            return executeUpdateDelete;
        } finally {
            releaseReference();
        }
    }

    public static String findEditTable(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(32);
            int indexOf2 = str.indexOf(44);
            if (indexOf > 0 && (indexOf < indexOf2 || indexOf2 < 0)) {
                return str.substring(0, indexOf);
            }
            return indexOf2 > 0 ? (indexOf2 < indexOf || indexOf < 0) ? str.substring(0, indexOf2) : str : str;
        }
        throw new IllegalStateException("Invalid tables");
    }

    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> arrayList = new ArrayList<>();
        synchronized (sActiveDatabases) {
            arrayList.addAll(sActiveDatabases.keySet());
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayList<SQLiteDebug.DbStats> getDbStats() {
        ArrayList<SQLiteDebug.DbStats> arrayList = new ArrayList<>();
        Iterator<SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().collectDbStats(arrayList);
        }
        return arrayList;
    }

    public static boolean importDatabase(String str, String str2) throws GDNotAuthorizedError {
        GDSDKState.getInstance().checkAuthorized();
        if (GDSDKState.getInstance().isWiped()) {
            return false;
        }
        return SQLiteConnection.importDatabase(str, str2);
    }

    private static boolean isMainThread() {
        Looper myLooper = Looper.myLooper();
        return myLooper != null && myLooper == Looper.getMainLooper();
    }

    private boolean isReadOnlyLocked() {
        return (this.mConfigurationLocked.openFlags & 1) == 1;
    }

    private void open() {
        try {
            try {
                openInner();
            } catch (SQLiteDatabaseCorruptException e) {
                onCorruption();
                openInner();
            }
        } catch (SQLiteException e2) {
            GDLog.DBGPRINTF(12, "Failed to open database '" + getLabel() + "'.", e2);
            close();
            throw e2;
        } catch (GDError e3) {
            GDLog.DBGPRINTF(12, "SQLiteDatabase openDatabase error (authorize not called)", e3);
            throw e3;
        }
    }

    private static SQLiteDatabase openDatabase(String str, OpenParams openParams) {
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    private static SQLiteDatabase openDb(String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) throws GDNotAuthorizedError {
        if (GDSDKState.getInstance().isWiped()) {
            return null;
        }
        SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(str, i, cursorFactory, databaseErrorHandler, -1, -1, -1L);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    private void openInner() {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                throw new AssertionError();
            }
            this.mConnectionPoolLocked = SQLiteConnectionPool.open(this.mConfigurationLocked);
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory cursorFactory) throws GDNotAuthorizedError {
        return openOrCreateDatabase(file.getPath(), cursorFactory);
    }

    public static int releaseMemory() {
        return SQLiteGlobal.releaseMemory();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked != null) {
            return;
        }
        throw new IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
    }

    private boolean yieldIfContendedHelper(boolean z, long j) {
        acquireReference();
        try {
            return getThreadSession().yieldTransaction(j, z, null);
        } finally {
            releaseReference();
        }
    }

    public void addCustomFunction(String str, int i, CustomFunction customFunction) {
        SQLiteCustomFunction sQLiteCustomFunction = new SQLiteCustomFunction(str, i, customFunction);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customFunctions.add(sQLiteCustomFunction);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.customFunctions.remove(sQLiteCustomFunction);
                throw e;
            }
        }
    }

    public void beginTransaction() {
        beginTransaction(null, true);
    }

    public void beginTransactionNonExclusive() {
        beginTransaction(null, false);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, false);
    }

    public SQLiteStatement compileStatement(String str) throws SQLException {
        acquireReference();
        try {
            return new SQLiteStatement(this, str, null);
        } finally {
            releaseReference();
        }
    }

    SQLiteSession createSession() {
        SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            sQLiteConnectionPool = this.mConnectionPoolLocked;
        }
        return new SQLiteSession(sQLiteConnectionPool);
    }

    public int delete(String str, String str2, String[] strArr) {
        acquireReference();
        try {
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, "DELETE FROM " + str + (!TextUtils.isEmpty(str2) ? " WHERE " + str2 : ""), strArr);
            int executeUpdateDelete = sQLiteStatement.executeUpdateDelete();
            sQLiteStatement.close();
            return executeUpdateDelete;
        } finally {
            releaseReference();
        }
    }

    public void disableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if ((this.mConfigurationLocked.openFlags & ENABLE_WRITE_AHEAD_LOGGING) == 0) {
                return;
            }
            this.mConfigurationLocked.openFlags &= -536870913;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
                sQLiteDatabaseConfiguration.openFlags = 536870912 | sQLiteDatabaseConfiguration.openFlags;
                throw e;
            }
        }
    }

    public boolean enableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if ((this.mConfigurationLocked.openFlags & ENABLE_WRITE_AHEAD_LOGGING) != 0) {
                return true;
            }
            if (isReadOnlyLocked()) {
                return false;
            }
            if (this.mConfigurationLocked.isInMemoryDb()) {
                GDLog.DBGPRINTF(12, "can't enable WAL for memory databases.");
                return false;
            } else if (this.mHasAttachedDbsLocked) {
                return false;
            } else {
                SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
                sQLiteDatabaseConfiguration.openFlags = 536870912 | sQLiteDatabaseConfiguration.openFlags;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                    return true;
                } catch (RuntimeException e) {
                    this.mConfigurationLocked.openFlags &= -536870913;
                    throw e;
                }
            }
        }
    }

    public void endTransaction() {
        acquireReference();
        try {
            getThreadSession().endTransaction(null);
        } finally {
            releaseReference();
        }
    }

    public void execSQL(String str) throws SQLException {
        executeSql(str, null);
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public List<Pair<String, String>> getAttachedDbs() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked == null) {
                return null;
            }
            if (!this.mHasAttachedDbsLocked) {
                arrayList.add(new Pair("main", this.mConfigurationLocked.path));
                return arrayList;
            }
            acquireReference();
            try {
                Cursor rawQuery = rawQuery("pragma database_list;", null);
                while (rawQuery.moveToNext()) {
                    arrayList.add(new Pair(rawQuery.getString(1), rawQuery.getString(2)));
                }
                rawQuery.close();
                return arrayList;
            } finally {
                releaseReference();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getLabel() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    public long getMaximumSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null) * getPageSize();
    }

    public long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    public final String getPath() {
        String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.path;
        }
        return str;
    }

    @Deprecated
    public Map<String, String> getSyncedTables() {
        return new HashMap(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getThreadDefaultConnectionFlags(boolean z) {
        int i = z ? 1 : 2;
        return isMainThread() ? i | 4 : i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    public int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public boolean inTransaction() {
        acquireReference();
        try {
            return getThreadSession().hasTransaction();
        } finally {
            releaseReference();
        }
    }

    public long insert(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 0);
        } catch (SQLException e) {
            GDLog.DBGPRINTF(12, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long insertOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 0);
    }

    public long insertWithOnConflict(String str, String str2, ContentValues contentValues, int i) {
        acquireReference();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT");
            sb.append(CONFLICT_VALUES[i]);
            sb.append(" INTO ");
            sb.append(str);
            sb.append('(');
            Object[] objArr = null;
            int i2 = 0;
            int size = (contentValues == null || contentValues.size() <= 0) ? 0 : contentValues.size();
            if (size > 0) {
                objArr = new Object[size];
                int i3 = 0;
                for (String str3 : contentValues.keySet()) {
                    sb.append(i3 > 0 ? "," : "");
                    sb.append(str3);
                    objArr[i3] = contentValues.get(str3);
                    i3++;
                }
                sb.append(')');
                sb.append(" VALUES (");
                while (i2 < size) {
                    sb.append(i2 > 0 ? ",?" : "?");
                    i2++;
                }
            } else {
                sb.append(str2 + ") VALUES (NULL");
            }
            sb.append(')');
            SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sb.toString(), objArr);
            long executeInsert = sQLiteStatement.executeInsert();
            sQLiteStatement.close();
            return executeInsert;
        } finally {
            releaseReference();
        }
    }

    public boolean isDatabaseIntegrityOk() {
        ArrayList arrayList;
        List<Pair<String, String>> attachedDbs;
        acquireReference();
        try {
            try {
                attachedDbs = getAttachedDbs();
            } catch (SQLiteException e) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(new Pair("main", getPath()));
                arrayList = arrayList2;
            }
            if (attachedDbs == null) {
                throw new IllegalStateException("databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
            }
            arrayList = attachedDbs;
            for (int i = 0; i < arrayList.size(); i++) {
                Pair<String, String> pair = arrayList.get(i);
                SQLiteStatement compileStatement = compileStatement("PRAGMA " + ((String) pair.first) + ".integrity_check(1);");
                String simpleQueryForString = compileStatement.simpleQueryForString();
                if (!simpleQueryForString.equalsIgnoreCase("ok")) {
                    GDLog.DBGPRINTF(12, "PRAGMA integrity_check on " + ((String) pair.second) + " returned: " + simpleQueryForString);
                    compileStatement.close();
                    return false;
                }
                compileStatement.close();
            }
            releaseReference();
            return true;
        } finally {
            releaseReference();
        }
    }

    public boolean isDbLockedByCurrentThread() {
        acquireReference();
        try {
            return getThreadSession().hasConnection();
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    public boolean isInMemoryDatabase() {
        boolean isInMemoryDb;
        synchronized (this.mLock) {
            isInMemoryDb = this.mConfigurationLocked.isInMemoryDb();
        }
        return isInMemoryDb;
    }

    public boolean isOpen() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionPoolLocked != null;
        }
        return z;
    }

    public boolean isReadOnly() {
        boolean isReadOnlyLocked;
        synchronized (this.mLock) {
            isReadOnlyLocked = isReadOnlyLocked();
        }
        return isReadOnlyLocked;
    }

    public boolean isWriteAheadLoggingEnabled() {
        boolean z;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            z = (this.mConfigurationLocked.openFlags & ENABLE_WRITE_AHEAD_LOGGING) != 0;
        }
        return z;
    }

    @Deprecated
    public void markTableSyncable(String str, String str2) {
    }

    @Deprecated
    public void markTableSyncable(String str, String str2, String str3) {
    }

    public boolean needUpgrade(int i) {
        return i > getVersion();
    }

    @Override // com.good.gd.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        dispose(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onCorruption() {
        EventLog.writeEvent((int) EVENT_DB_CORRUPT, getLabel());
        this.mErrorHandler.onCorruption(this);
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return queryWithFactoryPrivate(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    Cursor queryPrivate(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        return queryWithFactoryPrivate(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, cancellationSignal);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return queryWithFactoryPrivate(cursorFactory, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    Cursor queryWithFactoryPrivate(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            return rawQueryWithFactoryPrivate(cursorFactory, SQLiteQueryBuilder.buildQueryString(z, str, strArr, str2, str3, str4, str5, str6), strArr2, findEditTable(str), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public Cursor rawQuery(String str, String[] strArr) {
        return rawQueryWithFactoryPrivate(null, str, strArr, null, null);
    }

    Cursor rawQueryPrivate(String str, String[] strArr, CancellationSignal cancellationSignal) {
        return rawQueryWithFactoryPrivate(null, str, strArr, null, cancellationSignal);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String str, String[] strArr, String str2) {
        return rawQueryWithFactoryPrivate(cursorFactory, str, strArr, str2, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Cursor rawQueryWithFactoryPrivate(CursorFactory cursorFactory, String str, String[] strArr, String str2, CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, str, str2, cancellationSignal);
            if (cursorFactory == null) {
                cursorFactory = this.mCursorFactory;
            }
            return sQLiteDirectCursorDriver.query(cursorFactory, strArr);
        } finally {
            releaseReference();
        }
    }

    public void reopenReadWrite() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (!isReadOnlyLocked()) {
                return;
            }
            int i = this.mConfigurationLocked.openFlags;
            SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
            sQLiteDatabaseConfiguration.openFlags = (sQLiteDatabaseConfiguration.openFlags & (-2)) | 0;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.openFlags = i;
                throw e;
            }
        }
    }

    public long replace(String str, String str2, ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 5);
        } catch (SQLException e) {
            GDLog.DBGPRINTF(12, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long replaceOrThrow(String str, String str2, ContentValues contentValues) throws SQLException {
        return insertWithOnConflict(str, str2, contentValues, 5);
    }

    public void setForeignKeyConstraintsEnabled(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled == z) {
                return;
            }
            this.mConfigurationLocked.foreignKeyConstraintsEnabled = z;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (RuntimeException e) {
                this.mConfigurationLocked.foreignKeyConstraintsEnabled = !z;
                throw e;
            }
        }
    }

    public void setLocale(Locale locale) {
        if (locale != null) {
            synchronized (this.mLock) {
                throwIfNotOpenLocked();
                Locale locale2 = this.mConfigurationLocked.locale;
                this.mConfigurationLocked.locale = locale;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException e) {
                    this.mConfigurationLocked.locale = locale2;
                    throw e;
                }
            }
            return;
        }
        throw new IllegalArgumentException("locale must not be null.");
    }

    @Deprecated
    public void setLockingEnabled(boolean z) {
    }

    public void setMaxSqlCacheSize(int i) {
        if (i <= 100 && i >= 0) {
            synchronized (this.mLock) {
                throwIfNotOpenLocked();
                int i2 = this.mConfigurationLocked.maxSqlCacheSize;
                this.mConfigurationLocked.maxSqlCacheSize = i;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (RuntimeException e) {
                    this.mConfigurationLocked.maxSqlCacheSize = i2;
                    throw e;
                }
            }
            return;
        }
        throw new IllegalStateException("expected value between 0 and 100");
    }

    public long setMaximumSize(long j) {
        long pageSize = getPageSize();
        long j2 = j / pageSize;
        if (j % pageSize != 0) {
            j2++;
        }
        return DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + j2, null) * pageSize;
    }

    public void setPageSize(long j) {
        execSQL("PRAGMA page_size = " + j);
    }

    public void setTransactionSuccessful() {
        acquireReference();
        try {
            getThreadSession().setTransactionSuccessful();
        } finally {
            releaseReference();
        }
    }

    public void setVersion(int i) {
        execSQL("PRAGMA user_version = " + i);
    }

    public String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    public int update(String str, ContentValues contentValues, String str2, String[] strArr) {
        return updateWithOnConflict(str, contentValues, str2, strArr, 0);
    }

    public int updateWithOnConflict(String str, ContentValues contentValues, String str2, String[] strArr, int i) {
        if (contentValues != null && contentValues.size() != 0) {
            acquireReference();
            try {
                StringBuilder sb = new StringBuilder(120);
                sb.append("UPDATE ");
                sb.append(CONFLICT_VALUES[i]);
                sb.append(str);
                sb.append(" SET ");
                int size = contentValues.size();
                int length = strArr == null ? size : strArr.length + size;
                Object[] objArr = new Object[length];
                int i2 = 0;
                for (String str3 : contentValues.keySet()) {
                    sb.append(i2 > 0 ? "," : "");
                    sb.append(str3);
                    objArr[i2] = contentValues.get(str3);
                    sb.append("=?");
                    i2++;
                }
                if (strArr != null) {
                    for (int i3 = size; i3 < length; i3++) {
                        objArr[i3] = strArr[i3 - size];
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    sb.append(" WHERE ");
                    sb.append(str2);
                }
                SQLiteStatement sQLiteStatement = new SQLiteStatement(this, sb.toString(), objArr);
                int executeUpdateDelete = sQLiteStatement.executeUpdateDelete();
                sQLiteStatement.close();
                return executeUpdateDelete;
            } finally {
                releaseReference();
            }
        }
        throw new IllegalArgumentException("Empty values");
    }

    @Deprecated
    public boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1L);
    }

    private void beginTransaction(SQLiteTransactionListener sQLiteTransactionListener, boolean z) {
        acquireReference();
        try {
            getThreadSession().beginTransaction(z ? 2 : 1, sQLiteTransactionListener, getThreadDefaultConnectionFlags(false), null);
        } finally {
            releaseReference();
        }
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, CursorFactory cursorFactory) throws GDNotAuthorizedError {
        return openDatabase(str, cursorFactory, CREATE_IF_NECESSARY, null);
    }

    public void execSQL(String str, Object[] objArr) throws SQLException {
        if (objArr != null) {
            executeSql(str, objArr);
            return;
        }
        throw new IllegalArgumentException("Empty bindArgs");
    }

    public Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        return queryPrivate(z, str, strArr, str2, strArr2, str3, str4, str5, str6, convertToGDCancellationSignal(cancellationSignal));
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, CancellationSignal cancellationSignal) {
        return queryWithFactoryPrivate(cursorFactory, z, str, strArr, str2, strArr2, str3, str4, str5, str6, convertToGDCancellationSignal(cancellationSignal));
    }

    public Cursor rawQuery(String str, String[] strArr, CancellationSignal cancellationSignal) {
        return rawQueryPrivate(str, strArr, convertToGDCancellationSignal(cancellationSignal));
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String str, String[] strArr, String str2, CancellationSignal cancellationSignal) {
        return rawQueryWithFactoryPrivate(cursorFactory, str, strArr, str2, convertToGDCancellationSignal(cancellationSignal));
    }

    public boolean yieldIfContendedSafely(long j) {
        return yieldIfContendedHelper(true, j);
    }

    public static SQLiteDatabase openOrCreateDatabase(String str, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) throws GDNotAuthorizedError {
        return openDatabase(str, cursorFactory, CREATE_IF_NECESSARY, databaseErrorHandler);
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, null);
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, str6);
    }

    public static SQLiteDatabase openDatabase(String str, CursorFactory cursorFactory, int i) throws GDNotAuthorizedError {
        return openDatabase(str, cursorFactory, i, null);
    }

    public static SQLiteDatabase openDatabase(String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) throws GDNotAuthorizedError {
        SQLiteDatabase openDb = openDb(str, cursorFactory, i, databaseErrorHandler);
        if (openDb != null) {
            return openDb;
        }
        GDLog.DBGPRINTF(12, "Failed to open the database, wiped?");
        throw new SQLiteException("DB reference returned null " + str);
    }
}
