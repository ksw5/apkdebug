package com.good.gd.database.sqlite;

import android.util.LruCache;
import android.util.Printer;
import com.good.gd.database.sqlite.CancellationSignal;
import com.good.gd.database.sqlite.SQLiteDebug;
import com.good.gd.ndkproxy.GDLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class SQLiteConnection implements CancellationSignal.OnCancelListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "SQLiteConnection";
    private int mCancellationSignalAttachCount;
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private long mConnectionPtr;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final SQLiteConnectionPool mPool;
    private PreparedStatementCache mPreparedStatementCache;
    private PreparedStatement mPreparedStatementPool;
    private final OperationLog mRecentOperations = new OperationLog();
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final Pattern TRIM_SQL_PATTERN = Pattern.compile("[\\s]*\\n+[\\s]*");

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Operation {
        private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        public ArrayList<Object> mBindArgs;
        public int mCookie;
        public long mEndTime;
        public Exception mException;
        public boolean mFinished;
        public String mKind;
        public String mSql;
        public long mStartTime;

        private Operation() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getFormattedStartTime() {
            return sDateFormat.format(new Date(this.mStartTime));
        }

        private String getStatus() {
            return !this.mFinished ? "running" : this.mException != null ? "failed" : "succeeded";
        }

        public void describe(StringBuilder sb, boolean z) {
            ArrayList<Object> arrayList;
            sb.append(this.mKind);
            if (this.mFinished) {
                sb.append(" took ").append(this.mEndTime - this.mStartTime).append("ms");
            } else {
                sb.append(" started ").append(System.currentTimeMillis() - this.mStartTime).append("ms ago");
            }
            sb.append(" - ").append(getStatus());
            if (this.mSql != null) {
                sb.append(", sql=\"").append(SQLiteConnection.trimSqlForDisplay(this.mSql)).append("\"");
            }
            if (z && (arrayList = this.mBindArgs) != null && arrayList.size() != 0) {
                sb.append(", bindArgs=[");
                int size = this.mBindArgs.size();
                for (int i = 0; i < size; i++) {
                    Object obj = this.mBindArgs.get(i);
                    if (i != 0) {
                        sb.append(", ");
                    }
                    if (obj == null) {
                        sb.append("null");
                    } else if (obj instanceof byte[]) {
                        sb.append("<byte[]>");
                    } else if (obj instanceof String) {
                        sb.append("\"").append((String) obj).append("\"");
                    } else {
                        sb.append(obj);
                    }
                }
                sb.append("]");
            }
            if (this.mException != null) {
                sb.append(", exception=\"").append(this.mException.getMessage()).append("\"");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final int MAX_RECENT_OPERATIONS = 20;
        private int mGeneration;
        private int mIndex;
        private final Operation[] mOperations;

        private OperationLog() {
            this.mOperations = new Operation[20];
        }

        private boolean endOperationDeferLogLocked(int i) {
            Operation operationLocked = getOperationLocked(i);
            if (operationLocked != null) {
                operationLocked.mEndTime = System.currentTimeMillis();
                operationLocked.mFinished = true;
            }
            return false;
        }

        private Operation getOperationLocked(int i) {
            Operation operation = this.mOperations[i & 255];
            if (operation.mCookie == i) {
                return operation;
            }
            return null;
        }

        private void logOperationLocked(int i, String str) {
            Operation operationLocked = getOperationLocked(i);
            StringBuilder sb = new StringBuilder();
            operationLocked.describe(sb, false);
            if (str != null) {
                sb.append(", ").append(str);
            }
        }

        private int newOperationCookieLocked(int i) {
            int i2 = this.mGeneration;
            this.mGeneration = i2 + 1;
            return i | (i2 << 8);
        }

        public int beginOperation(String str, String str2, Object[] objArr) {
            int newOperationCookieLocked;
            synchronized (this.mOperations) {
                int i = (this.mIndex + 1) % 20;
                Operation operation = this.mOperations[i];
                if (operation == null) {
                    operation = new Operation();
                    this.mOperations[i] = operation;
                } else {
                    operation.mFinished = false;
                    operation.mException = null;
                    if (operation.mBindArgs != null) {
                        operation.mBindArgs.clear();
                    }
                }
                operation.mStartTime = System.currentTimeMillis();
                operation.mKind = str;
                operation.mSql = str2;
                if (objArr != null) {
                    if (operation.mBindArgs == null) {
                        operation.mBindArgs = new ArrayList<>();
                    } else {
                        operation.mBindArgs.clear();
                    }
                    for (Object obj : objArr) {
                        if (obj != null && (obj instanceof byte[])) {
                            operation.mBindArgs.add(SQLiteConnection.EMPTY_BYTE_ARRAY);
                        } else {
                            operation.mBindArgs.add(obj);
                        }
                    }
                }
                newOperationCookieLocked = newOperationCookieLocked(i);
                operation.mCookie = newOperationCookieLocked;
                this.mIndex = i;
            }
            return newOperationCookieLocked;
        }

        public String describeCurrentOperation() {
            synchronized (this.mOperations) {
                Operation operation = this.mOperations[this.mIndex];
                if (operation == null || operation.mFinished) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                operation.describe(sb, false);
                return sb.toString();
            }
        }

        public void dump(Printer printer, boolean z) {
            synchronized (this.mOperations) {
                printer.println("  Most recently executed operations:");
                int i = this.mIndex;
                Operation operation = this.mOperations[i];
                if (operation != null) {
                    int i2 = 0;
                    do {
                        StringBuilder sb = new StringBuilder();
                        sb.append("    ").append(i2).append(": [");
                        sb.append(operation.getFormattedStartTime());
                        sb.append("] ");
                        operation.describe(sb, z);
                        printer.println(sb.toString());
                        i = i > 0 ? i - 1 : 19;
                        i2++;
                        operation = this.mOperations[i];
                        if (operation == null) {
                            break;
                        }
                    } while (i2 < 20);
                } else {
                    printer.println("    <none>");
                }
            }
        }

        public void endOperation(int i) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(i)) {
                    logOperationLocked(i, null);
                }
            }
        }

        public boolean endOperationDeferLog(int i) {
            boolean endOperationDeferLogLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogLocked = endOperationDeferLogLocked(i);
            }
            return endOperationDeferLogLocked;
        }

        public void failOperation(int i, Exception exc) {
            synchronized (this.mOperations) {
                Operation operationLocked = getOperationLocked(i);
                if (operationLocked != null) {
                    operationLocked.mException = exc;
                }
            }
        }

        public void logOperation(int i, String str) {
            synchronized (this.mOperations) {
                logOperationLocked(i, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public String mSql;
        public long mStatementPtr;
        public int mType;

        private PreparedStatement() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class PreparedStatementCache extends LruCache<String, PreparedStatement> {
        public PreparedStatementCache(int i) {
            super(i);
        }

        public void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map<String, PreparedStatement> snapshot = snapshot();
            if (!snapshot.isEmpty()) {
                int i = 0;
                for (Map.Entry<String, PreparedStatement> entry : snapshot.entrySet()) {
                    PreparedStatement value = entry.getValue();
                    if (value.mInCache) {
                        printer.println("    " + i + ": statementPtr=0x" + Long.toHexString(value.mStatementPtr) + ", numParameters=" + value.mNumParameters + ", type=" + value.mType + ", readOnly=" + value.mReadOnly + ", sql=\"" + SQLiteConnection.trimSqlForDisplay(entry.getKey()) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, String str, PreparedStatement preparedStatement, PreparedStatement preparedStatement2) {
            preparedStatement.mInCache = false;
            if (!preparedStatement.mInUse) {
                SQLiteConnection.this.finalizePreparedStatement(preparedStatement);
            }
        }
    }

    private SQLiteConnection(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) {
        this.mPool = sQLiteConnectionPool;
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration2 = new SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.mConfiguration = sQLiteDatabaseConfiguration2;
        this.mConnectionId = i;
        this.mIsPrimaryConnection = z;
        this.mIsReadOnlyConnection = (sQLiteDatabaseConfiguration.openFlags & 1) == 0 ? false : true;
        this.mPreparedStatementCache = new PreparedStatementCache(sQLiteDatabaseConfiguration2.maxSqlCacheSize);
    }

    private PreparedStatement acquirePreparedStatement(String str) {
        boolean z;
        PreparedStatement preparedStatement = this.mPreparedStatementCache.get(str);
        if (preparedStatement == null) {
            z = false;
        } else if (!preparedStatement.mInUse) {
            return preparedStatement;
        } else {
            z = true;
        }
        long nativePrepareStatement = nativePrepareStatement(this.mConnectionPtr, str);
        try {
            int nativeGetParameterCount = nativeGetParameterCount(this.mConnectionPtr, nativePrepareStatement);
            int sqlStatementType = DatabaseUtils.getSqlStatementType(str);
            preparedStatement = obtainPreparedStatement(str, nativePrepareStatement, nativeGetParameterCount, sqlStatementType, nativeIsReadOnly(this.mConnectionPtr, nativePrepareStatement));
            if (!z && isCacheable(sqlStatementType)) {
                this.mPreparedStatementCache.put(str, preparedStatement);
                preparedStatement.mInCache = true;
            }
            preparedStatement.mInUse = true;
            return preparedStatement;
        } catch (RuntimeException e) {
            if (preparedStatement == null || !preparedStatement.mInCache) {
                nativeFinalizeStatement(this.mConnectionPtr, nativePrepareStatement);
            }
            throw e;
        }
    }

    private void attachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
            int i = this.mCancellationSignalAttachCount + 1;
            this.mCancellationSignalAttachCount = i;
            if (i != 1) {
                return;
            }
            nativeResetCancel(this.mConnectionPtr, true);
            cancellationSignal.setOnCancelListener(this);
        }
    }

    private void bindArguments(PreparedStatement preparedStatement, Object[] objArr) {
        int length = objArr != null ? objArr.length : 0;
        if (length == preparedStatement.mNumParameters) {
            if (length == 0) {
                return;
            }
            long j = preparedStatement.mStatementPtr;
            for (int i = 0; i < length; i++) {
                Object obj = objArr[i];
                int typeOfObject = DatabaseUtils.getTypeOfObject(obj);
                if (typeOfObject == 0) {
                    nativeBindNull(this.mConnectionPtr, j, i + 1);
                } else if (typeOfObject == 1) {
                    nativeBindLong(this.mConnectionPtr, j, i + 1, ((Number) obj).longValue());
                } else if (typeOfObject == 2) {
                    nativeBindDouble(this.mConnectionPtr, j, i + 1, ((Number) obj).doubleValue());
                } else if (typeOfObject != 4) {
                    if (obj instanceof Boolean) {
                        nativeBindLong(this.mConnectionPtr, j, i + 1, ((Boolean) obj).booleanValue() ? 1L : 0L);
                    } else {
                        nativeBindString(this.mConnectionPtr, j, i + 1, obj.toString());
                    }
                } else {
                    nativeBindBlob(this.mConnectionPtr, j, i + 1, (byte[]) obj);
                }
            }
            return;
        }
        throw new SQLiteBindOrColumnIndexOutOfRangeException("Expected " + preparedStatement.mNumParameters + " bind arguments but " + length + " were provided.");
    }

    private static String canonicalizeSyncMode(String str) {
        return str.equals("0") ? "OFF" : str.equals("1") ? "NORMAL" : str.equals("2") ? "FULL" : str;
    }

    private void detachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            int i = this.mCancellationSignalAttachCount;
            if (i <= 0) {
                throw new AssertionError();
            }
            int i2 = i - 1;
            this.mCancellationSignalAttachCount = i2;
            if (i2 == 0) {
                cancellationSignal.setOnCancelListener(null);
                nativeResetCancel(this.mConnectionPtr, false);
            }
        }
    }

    private void dispose(boolean z) {
        if (this.mConnectionPtr != 0) {
            int beginOperation = this.mRecentOperations.beginOperation("close", null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0L;
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizePreparedStatement(PreparedStatement preparedStatement) {
        nativeFinalizeStatement(this.mConnectionPtr, preparedStatement.mStatementPtr);
        recyclePreparedStatement(preparedStatement);
    }

    private SQLiteDebug.DbStats getMainDbStatsUnsafe(int i, long j, long j2) {
        String str;
        String str2 = this.mConfiguration.path;
        if (!this.mIsPrimaryConnection) {
            str = str2 + " (" + this.mConnectionId + ")";
        } else {
            str = str2;
        }
        return new SQLiteDebug.DbStats(str, j, j2, i, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size());
    }

    private static native boolean importDB(String str, String str2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean importDatabase(String str, String str2) {
        return importDB(str, str2);
    }

    private static boolean isCacheable(int i) {
        return i == 2 || i == 1;
    }

    private static native void nativeBindBlob(long j, long j2, int i, byte[] bArr);

    private static native void nativeBindDouble(long j, long j2, int i, double d);

    private static native void nativeBindLong(long j, long j2, int i, long j3);

    private static native void nativeBindNull(long j, long j2, int i);

    private static native void nativeBindString(long j, long j2, int i, String str);

    private static native void nativeCancel(long j);

    private static native void nativeClose(long j);

    private static native void nativeExecute(long j, long j2);

    private static native int nativeExecuteForChangedRowCount(long j, long j2);

    private static native long nativeExecuteForCursorWindow(long j, long j2, long j3, int i, int i2, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(long j, long j2);

    private static native long nativeExecuteForLong(long j, long j2);

    private static native String nativeExecuteForString(long j, long j2);

    private static native void nativeFinalizeStatement(long j, long j2);

    private static native int nativeGetColumnCount(long j, long j2);

    private static native String nativeGetColumnName(long j, long j2, int i);

    private static native int nativeGetDbLookaside(long j);

    private static native int nativeGetParameterCount(long j, long j2);

    private static native boolean nativeIsReadOnly(long j, long j2);

    private static native long nativeOpen(String str, int i, String str2, boolean z, boolean z2);

    private static native long nativePrepareStatement(long j, String str);

    private static native void nativeRegisterCustomFunction(long j, SQLiteCustomFunction sQLiteCustomFunction);

    private static native void nativeRegisterLocalizedCollators(long j, String str);

    private static native void nativeResetCancel(long j, boolean z);

    private static native void nativeResetStatementAndClearBindings(long j, long j2);

    private PreparedStatement obtainPreparedStatement(String str, long j, int i, int i2, boolean z) {
        PreparedStatement preparedStatement = this.mPreparedStatementPool;
        if (preparedStatement != null) {
            this.mPreparedStatementPool = preparedStatement.mPoolNext;
            preparedStatement.mPoolNext = null;
            preparedStatement.mInCache = false;
        } else {
            preparedStatement = new PreparedStatement();
        }
        preparedStatement.mSql = str;
        preparedStatement.mStatementPtr = j;
        preparedStatement.mNumParameters = i;
        preparedStatement.mType = i2;
        preparedStatement.mReadOnly = z;
        return preparedStatement;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SQLiteConnection open(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) {
        SQLiteConnection sQLiteConnection = new SQLiteConnection(sQLiteConnectionPool, sQLiteDatabaseConfiguration, i, z);
        try {
            sQLiteConnection.open();
            return sQLiteConnection;
        } catch (SQLiteException e) {
            sQLiteConnection.dispose(false);
            throw e;
        }
    }

    private void recyclePreparedStatement(PreparedStatement preparedStatement) {
        preparedStatement.mSql = null;
        preparedStatement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = preparedStatement;
    }

    private void releasePreparedStatement(PreparedStatement preparedStatement) {
        preparedStatement.mInUse = false;
        if (preparedStatement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, preparedStatement.mStatementPtr);
                return;
            } catch (SQLiteException e) {
                this.mPreparedStatementCache.remove(preparedStatement.mSql);
                return;
            }
        }
        finalizePreparedStatement(preparedStatement);
    }

    private void resizePreparedStatementCache(int i) {
        synchronized (this) {
            PreparedStatementCache preparedStatementCache = this.mPreparedStatementCache;
            if (i > 0) {
                if (preparedStatementCache != null && i < preparedStatementCache.maxSize()) {
                    throw new IllegalStateException("cacheSize < old maxSize");
                }
                this.mPreparedStatementCache = new PreparedStatementCache(i);
                if (preparedStatementCache != null) {
                    for (Map.Entry<String, PreparedStatement> entry : preparedStatementCache.snapshot().entrySet()) {
                        this.mPreparedStatementCache.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                throw new IllegalStateException("cacheSize <= 0");
            }
        }
    }

    private void setAutoCheckpointInterval() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long wALAutoCheckpoint = SQLiteGlobal.getWALAutoCheckpoint();
        if (executeForLong("PRAGMA wal_autocheckpoint", null, null) == wALAutoCheckpoint) {
            return;
        }
        executeForLong("PRAGMA wal_autocheckpoint=" + wALAutoCheckpoint, null, null);
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long j = this.mConfiguration.foreignKeyConstraintsEnabled ? 1L : 0L;
            if (executeForLong("PRAGMA foreign_keys", null, null) == j) {
                return;
            }
            execute("PRAGMA foreign_keys=" + j, null, null);
        }
    }

    private void setJournalMode(String str) {
        String executeForString = executeForString("PRAGMA journal_mode", null, null);
        if (!executeForString.equalsIgnoreCase(str)) {
            try {
                if (executeForString("PRAGMA journal_mode=" + str, null, null).equalsIgnoreCase(str)) {
                    return;
                }
            } catch (SQLiteDatabaseLockedException e) {
            }
            GDLog.DBGPRINTF(13, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + executeForString + "' to '" + str + "' because the database is locked.\n");
        }
    }

    private void setJournalSizeLimit() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long journalSizeLimit = SQLiteGlobal.getJournalSizeLimit();
        if (executeForLong("PRAGMA journal_size_limit", null, null) == journalSizeLimit) {
            return;
        }
        executeForLong("PRAGMA journal_size_limit=" + journalSizeLimit, null, null);
    }

    private void setLocaleFromConfiguration() {
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfiguration;
        if ((sQLiteDatabaseConfiguration.openFlags & 16) != 0) {
            return;
        }
        String locale = sQLiteDatabaseConfiguration.locale.toString();
        nativeRegisterLocalizedCollators(this.mConnectionPtr, locale);
        if (this.mIsReadOnlyConnection) {
            return;
        }
        try {
            execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
            String executeForString = executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", null, null);
            if (executeForString == null) {
                return;
            }
            executeForString.equals(locale);
        } catch (RuntimeException e) {
            throw new SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + locale + "'.", e);
        }
    }

    private void setPageSize() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        long defaultPageSize = SQLiteGlobal.getDefaultPageSize();
        if (executeForLong("PRAGMA page_size", null, null) == defaultPageSize) {
            return;
        }
        execute("PRAGMA page_size=" + defaultPageSize, null, null);
    }

    private void setSyncMode(String str) {
        if (!canonicalizeSyncMode(executeForString("PRAGMA synchronous", null, null)).equalsIgnoreCase(canonicalizeSyncMode(str))) {
            execute("PRAGMA synchronous=" + str, null, null);
        }
    }

    private void setWalModeFromConfiguration() {
        if (this.mConfiguration.isInMemoryDb() || this.mIsReadOnlyConnection) {
            return;
        }
        if ((this.mConfiguration.openFlags & SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING) != 0) {
            setJournalMode("WAL");
            setSyncMode(SQLiteGlobal.getWALSyncMode());
            return;
        }
        setJournalMode(SQLiteGlobal.getDefaultJournalMode());
        setSyncMode(SQLiteGlobal.getDefaultSyncMode());
    }

    private void throwIfStatementForbidden(PreparedStatement preparedStatement) {
        if (!this.mOnlyAllowReadOnlyOperations || preparedStatement.mReadOnly) {
            return;
        }
        throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String trimSqlForDisplay(String str) {
        return TRIM_SQL_PATTERN.matcher(str).replaceAll(" ");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void close() {
        dispose(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0047 A[Catch: all -> 0x00de, SQLiteException -> 0x00e3, TRY_LEAVE, TryCatch #1 {SQLiteException -> 0x00e3, blocks: (B:9:0x0032, B:10:0x0041, B:12:0x0047, B:18:0x0093, B:20:0x00ac, B:21:0x00c8), top: B:8:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ac A[Catch: all -> 0x00de, SQLiteException -> 0x00e3, TryCatch #1 {SQLiteException -> 0x00e3, blocks: (B:9:0x0032, B:10:0x0041, B:12:0x0047, B:18:0x0093, B:20:0x00ac, B:21:0x00c8), top: B:8:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void collectDbStats(ArrayList<SQLiteDebug.DbStats> r27) {
        /*
            r26 = this;
            r9 = r26
            r10 = r27
            java.lang.String r11 = "PRAGMA "
            long r0 = r9.mConnectionPtr
            int r2 = nativeGetDbLookaside(r0)
            r12 = 0
            r14 = 0
            java.lang.String r0 = "PRAGMA page_count;"
            long r3 = r9.executeForLong(r0, r14, r14)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L1f
            java.lang.String r0 = "PRAGMA page_size;"
            long r0 = r9.executeForLong(r0, r14, r14)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L1d
            r5 = r0
            goto L22
        L1d:
            r0 = move-exception
            goto L21
        L1f:
            r0 = move-exception
            r3 = r12
        L21:
            r5 = r12
        L22:
            r1 = r26
            com.good.gd.database.sqlite.SQLiteDebug$DbStats r0 = r1.getMainDbStatsUnsafe(r2, r3, r5)
            r10.add(r0)
            com.good.gd.database.sqlite.CursorWindow r15 = new com.good.gd.database.sqlite.CursorWindow
            java.lang.String r0 = "collectDbStats"
            r15.<init>(r0)
            java.lang.String r2 = "PRAGMA database_list;"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r26
            r4 = r15
            r1.executeForCursorWindow(r2, r3, r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r1 = 1
            r2 = r1
        L41:
            int r0 = r15.getNumRows()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            if (r2 >= r0) goto Le4
            java.lang.String r3 = r15.getString(r2, r1)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r0 = 2
            java.lang.String r4 = r15.getString(r2, r0)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            r0.<init>()     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            java.lang.String r5 = ".page_count;"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            java.lang.String r0 = r0.toString()     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            long r5 = r9.executeForLong(r0, r14, r14)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8d java.lang.Throwable -> Lde
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            r0.<init>()     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            java.lang.String r7 = ".page_size;"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            java.lang.String r0 = r0.toString()     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            long r7 = r9.executeForLong(r0, r14, r14)     // Catch: com.good.gd.database.sqlite.SQLiteException -> L8b java.lang.Throwable -> Lde
            r18 = r5
            r20 = r7
            goto L93
        L8b:
            r0 = move-exception
            goto L8f
        L8d:
            r0 = move-exception
            r5 = r12
        L8f:
            r18 = r5
            r20 = r12
        L93:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r0.<init>()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.String r5 = "  (attached) "
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            boolean r3 = r4.isEmpty()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            if (r3 != 0) goto Lc6
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r3.<init>()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.String r3 = ": "
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r17 = r0
            goto Lc8
        Lc6:
            r17 = r0
        Lc8:
            com.good.gd.database.sqlite.SQLiteDebug$DbStats r0 = new com.good.gd.database.sqlite.SQLiteDebug$DbStats     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r16 = r0
            r16.<init>(r17, r18, r20, r22, r23, r24, r25)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            r10.add(r0)     // Catch: java.lang.Throwable -> Lde com.good.gd.database.sqlite.SQLiteException -> Le3
            int r2 = r2 + 1
            goto L41
        Lde:
            r0 = move-exception
            r15.close()
            throw r0
        Le3:
            r0 = move-exception
        Le4:
            r15.close()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.database.sqlite.SQLiteConnection.collectDbStats(java.util.ArrayList):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void collectDbStatsUnsafe(ArrayList<SQLiteDebug.DbStats> arrayList) {
        arrayList.add(getMainDbStatsUnsafe(0, 0L, 0L));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    public void dump(Printer printer, boolean z) {
        dumpUnsafe(printer, z);
    }

    void dumpUnsafe(Printer printer, boolean z) {
        printer.println("Connection #" + this.mConnectionId + ":");
        if (z) {
            printer.println("  connectionPtr: 0x" + Long.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        this.mRecentOperations.dump(printer, z);
        if (z) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    public void execute(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str != null) {
            int beginOperation = this.mRecentOperations.beginOperation("execute", str, objArr);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    try {
                        throwIfStatementForbidden(acquirePreparedStatement);
                        bindArguments(acquirePreparedStatement, objArr);
                        attachCancellationSignal(cancellationSignal);
                        nativeExecute(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        detachCancellationSignal(cancellationSignal);
                        return;
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public int executeForChangedRowCount(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str != null) {
            int i = 0;
            int beginOperation = this.mRecentOperations.beginOperation("executeForChangedRowCount", str, objArr);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    try {
                        throwIfStatementForbidden(acquirePreparedStatement);
                        bindArguments(acquirePreparedStatement, objArr);
                        attachCancellationSignal(cancellationSignal);
                        i = nativeExecuteForChangedRowCount(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        detachCancellationSignal(cancellationSignal);
                        return i;
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                if (this.mRecentOperations.endOperationDeferLog(beginOperation)) {
                    this.mRecentOperations.logOperation(beginOperation, "changedRows=" + i);
                }
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x017f A[Catch: all -> 0x01b6, TryCatch #9 {all -> 0x01b6, blocks: (B:6:0x001e, B:34:0x006e, B:36:0x0076, B:48:0x0177, B:50:0x017f, B:51:0x01b5), top: B:5:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int executeForCursorWindow(String r25, Object[] r26, CursorWindow r27, int r28, int r29, boolean r30, CancellationSignal r31) {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.database.sqlite.SQLiteConnection.executeForCursorWindow(java.lang.String, java.lang.Object[], com.good.gd.database.sqlite.CursorWindow, int, int, boolean, com.good.gd.database.sqlite.CancellationSignal):int");
    }

    public long executeForLastInsertedRowId(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str != null) {
            int beginOperation = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", str, objArr);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    try {
                        throwIfStatementForbidden(acquirePreparedStatement);
                        bindArguments(acquirePreparedStatement, objArr);
                        attachCancellationSignal(cancellationSignal);
                        long nativeExecuteForLastInsertedRowId = nativeExecuteForLastInsertedRowId(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        detachCancellationSignal(cancellationSignal);
                        return nativeExecuteForLastInsertedRowId;
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public long executeForLong(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str != null) {
            int beginOperation = this.mRecentOperations.beginOperation("executeForLong", str, objArr);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    try {
                        throwIfStatementForbidden(acquirePreparedStatement);
                        bindArguments(acquirePreparedStatement, objArr);
                        attachCancellationSignal(cancellationSignal);
                        long nativeExecuteForLong = nativeExecuteForLong(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        detachCancellationSignal(cancellationSignal);
                        return nativeExecuteForLong;
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public String executeForString(String str, Object[] objArr, CancellationSignal cancellationSignal) {
        if (str != null) {
            int beginOperation = this.mRecentOperations.beginOperation("executeForString", str, objArr);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    try {
                        throwIfStatementForbidden(acquirePreparedStatement);
                        bindArguments(acquirePreparedStatement, objArr);
                        attachCancellationSignal(cancellationSignal);
                        String nativeExecuteForString = nativeExecuteForString(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        detachCancellationSignal(cancellationSignal);
                        return nativeExecuteForString;
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    protected void finalize() throws Throwable {
        try {
            SQLiteConnectionPool sQLiteConnectionPool = this.mPool;
            if (sQLiteConnectionPool != null && this.mConnectionPtr != 0) {
                sQLiteConnectionPool.onConnectionLeaked();
            }
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPreparedStatementInCache(String str) {
        return this.mPreparedStatementCache.get(str) != null;
    }

    public boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    @Override // com.good.gd.database.sqlite.CancellationSignal.OnCancelListener
    public void onCancel() {
        nativeCancel(this.mConnectionPtr);
    }

    public void prepare(String str, SQLiteStatementInfo sQLiteStatementInfo) {
        if (str != null) {
            int beginOperation = this.mRecentOperations.beginOperation("prepare", str, null);
            try {
                try {
                    PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                    if (sQLiteStatementInfo != null) {
                        try {
                            sQLiteStatementInfo.numParameters = acquirePreparedStatement.mNumParameters;
                            sQLiteStatementInfo.readOnly = acquirePreparedStatement.mReadOnly;
                            int nativeGetColumnCount = nativeGetColumnCount(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                            if (nativeGetColumnCount == 0) {
                                sQLiteStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                            } else {
                                sQLiteStatementInfo.columnNames = new String[nativeGetColumnCount];
                                for (int i = 0; i < nativeGetColumnCount; i++) {
                                    sQLiteStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr, i);
                                }
                            }
                        } finally {
                            releasePreparedStatement(acquirePreparedStatement);
                        }
                    }
                    return;
                } catch (RuntimeException e) {
                    this.mRecentOperations.failOperation(beginOperation, e);
                    throw e;
                }
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reconfigure(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        boolean z = false;
        this.mOnlyAllowReadOnlyOperations = false;
        int size = sQLiteDatabaseConfiguration.customFunctions.size();
        for (int i = 0; i < size; i++) {
            SQLiteCustomFunction sQLiteCustomFunction = sQLiteDatabaseConfiguration.customFunctions.get(i);
            if (!this.mConfiguration.customFunctions.contains(sQLiteCustomFunction)) {
                nativeRegisterCustomFunction(this.mConnectionPtr, sQLiteCustomFunction);
            }
        }
        boolean z2 = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled;
        if (((sQLiteDatabaseConfiguration.openFlags ^ this.mConfiguration.openFlags) & SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING) != 0) {
            z = true;
        }
        boolean z3 = !sQLiteDatabaseConfiguration.locale.equals(this.mConfiguration.locale);
        this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
        int maxSize = this.mPreparedStatementCache.maxSize();
        int i2 = sQLiteDatabaseConfiguration.maxSqlCacheSize;
        if (maxSize < i2) {
            resizePreparedStatementCache(i2);
        }
        if (z2) {
            setForeignKeyModeFromConfiguration();
        }
        if (z) {
            setWalModeFromConfiguration();
        }
        if (z3) {
            setLocaleFromConfiguration();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setOnlyAllowReadOnlyOperations(boolean z) {
        this.mOnlyAllowReadOnlyOperations = z;
    }

    public String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + ")";
    }

    private void open() {
        SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfiguration;
        this.mConnectionPtr = nativeOpen(sQLiteDatabaseConfiguration.path, sQLiteDatabaseConfiguration.openFlags, sQLiteDatabaseConfiguration.label, false, false);
        setPageSize();
        setForeignKeyModeFromConfiguration();
        setWalModeFromConfiguration();
        setJournalSizeLimit();
        setAutoCheckpointInterval();
        setLocaleFromConfiguration();
        int size = this.mConfiguration.customFunctions.size();
        for (int i = 0; i < size; i++) {
            nativeRegisterCustomFunction(this.mConnectionPtr, this.mConfiguration.customFunctions.get(i));
        }
    }
}
