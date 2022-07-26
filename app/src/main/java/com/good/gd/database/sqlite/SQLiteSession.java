package com.good.gd.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteSession {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TRANSACTION_MODE_DEFERRED = 0;
    public static final int TRANSACTION_MODE_EXCLUSIVE = 2;
    public static final int TRANSACTION_MODE_IMMEDIATE = 1;
    private SQLiteConnection mConnection;
    private int mConnectionFlags;
    private final SQLiteConnectionPool mConnectionPool;
    private int mConnectionUseCount;
    private Transaction mTransactionPool;
    private Transaction mTransactionStack;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Transaction {
        public boolean mChildFailed;
        public SQLiteTransactionListener mListener;
        public boolean mMarkedSuccessful;
        public int mMode;
        public Transaction mParent;

        private Transaction() {
        }
    }

    public SQLiteSession(SQLiteConnectionPool sQLiteConnectionPool) {
        if (sQLiteConnectionPool != null) {
            this.mConnectionPool = sQLiteConnectionPool;
            return;
        }
        throw new IllegalArgumentException("connectionPool must not be null");
    }

    private void acquireConnection(String str, int i, CancellationSignal cancellationSignal) {
        if (this.mConnection == null) {
            if (this.mConnectionUseCount != 0) {
                throw new AssertionError();
            }
            this.mConnection = this.mConnectionPool.acquireConnection(str, i, cancellationSignal);
            this.mConnectionFlags = i;
        }
        this.mConnectionUseCount++;
    }

    private void beginTransactionUnchecked(int i, SQLiteTransactionListener sQLiteTransactionListener, int i2, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (this.mTransactionStack == null) {
            acquireConnection(null, i2, cancellationSignal);
        }
        try {
            if (this.mTransactionStack == null) {
                if (i == 1) {
                    this.mConnection.execute("BEGIN IMMEDIATE;", null, cancellationSignal);
                } else if (i != 2) {
                    this.mConnection.execute("BEGIN;", null, cancellationSignal);
                } else {
                    this.mConnection.execute("BEGIN EXCLUSIVE;", null, cancellationSignal);
                }
            }
            if (sQLiteTransactionListener != null) {
                try {
                    sQLiteTransactionListener.onBegin();
                } catch (RuntimeException e) {
                    if (this.mTransactionStack == null) {
                        this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                    }
                    throw e;
                }
            }
            Transaction obtainTransaction = obtainTransaction(i, sQLiteTransactionListener);
            obtainTransaction.mParent = this.mTransactionStack;
            this.mTransactionStack = obtainTransaction;
            if (obtainTransaction != null) {
                return;
            }
            releaseConnection();
        } catch (Throwable th) {
            if (this.mTransactionStack == null) {
                releaseConnection();
            }
            throw th;
        }
    }

    private void endTransactionUnchecked(CancellationSignal cancellationSignal, boolean z) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Transaction transaction = this.mTransactionStack;
        boolean z2 = false;
        boolean z3 = (transaction.mMarkedSuccessful || z) && !transaction.mChildFailed;
        SQLiteTransactionListener sQLiteTransactionListener = transaction.mListener;
        if (sQLiteTransactionListener != null) {
            try {
                if (z3) {
                    sQLiteTransactionListener.onCommit();
                } else {
                    sQLiteTransactionListener.onRollback();
                }
            } catch (RuntimeException e) {
                e = e;
            }
        }
        z2 = z3;
        e = null;
        this.mTransactionStack = transaction.mParent;
        recycleTransaction(transaction);
        Transaction transaction2 = this.mTransactionStack;
        if (transaction2 == null) {
            try {
                if (z2) {
                    this.mConnection.execute("COMMIT;", null, cancellationSignal);
                } else {
                    this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                }
            } finally {
                releaseConnection();
            }
        } else if (!z2) {
            transaction2.mChildFailed = true;
        }
        if (e == null) {
            return;
        }
        throw e;
    }

    private boolean executeSpecial(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        switch (DatabaseUtils.getSqlStatementType(str)) {
            case 4:
                beginTransaction(2, null, i, cancellationSignal);
                return true;
            case 5:
                setTransactionSuccessful();
                endTransaction(cancellationSignal);
                return true;
            case 6:
                endTransaction(cancellationSignal);
                return true;
            default:
                return false;
        }
    }

    private Transaction obtainTransaction(int i, SQLiteTransactionListener sQLiteTransactionListener) {
        Transaction transaction = this.mTransactionPool;
        if (transaction != null) {
            this.mTransactionPool = transaction.mParent;
            transaction.mParent = null;
            transaction.mMarkedSuccessful = false;
            transaction.mChildFailed = false;
        } else {
            transaction = new Transaction();
        }
        transaction.mMode = i;
        transaction.mListener = sQLiteTransactionListener;
        return transaction;
    }

    private void recycleTransaction(Transaction transaction) {
        transaction.mParent = this.mTransactionPool;
        transaction.mListener = null;
        this.mTransactionPool = transaction;
    }

    private void releaseConnection() {
        SQLiteConnection sQLiteConnection = this.mConnection;
        if (sQLiteConnection == null) {
            throw new AssertionError();
        }
        int i = this.mConnectionUseCount;
        if (i <= 0) {
            throw new AssertionError();
        }
        int i2 = i - 1;
        this.mConnectionUseCount = i2;
        if (i2 != 0) {
            return;
        }
        try {
            this.mConnectionPool.releaseConnection(sQLiteConnection);
        } finally {
            this.mConnection = null;
        }
    }

    private void throwIfNestedTransaction() {
        if (!hasNestedTransaction()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because a nested transaction is in progress.");
    }

    private void throwIfNoTransaction() {
        if (this.mTransactionStack != null) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because there is no current transaction.");
    }

    private void throwIfTransactionMarkedSuccessful() {
        Transaction transaction = this.mTransactionStack;
        if (transaction == null || !transaction.mMarkedSuccessful) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
    }

    private boolean yieldTransactionUnchecked(long j, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (!this.mConnectionPool.shouldYieldConnection(this.mConnection, this.mConnectionFlags)) {
            return false;
        }
        Transaction transaction = this.mTransactionStack;
        int i = transaction.mMode;
        SQLiteTransactionListener sQLiteTransactionListener = transaction.mListener;
        int i2 = this.mConnectionFlags;
        endTransactionUnchecked(cancellationSignal, true);
        if (j > 0) {
            try {
                Thread.sleep(j);
            } catch (InterruptedException e) {
            }
        }
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
        return true;
    }

    public void beginTransaction(int i, SQLiteTransactionListener sQLiteTransactionListener, int i2, CancellationSignal cancellationSignal) {
        throwIfTransactionMarkedSuccessful();
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
    }

    public void endTransaction(CancellationSignal cancellationSignal) {
        throwIfNoTransaction();
        if (this.mConnection == null) {
            throw new AssertionError();
        }
        endTransactionUnchecked(cancellationSignal, false);
    }

    public void execute(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (executeSpecial(str, objArr, i, cancellationSignal)) {
                return;
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                this.mConnection.execute(str, objArr, cancellationSignal);
                return;
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public int executeForChangedRowCount(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (executeSpecial(str, objArr, i, cancellationSignal)) {
                return 0;
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                return this.mConnection.executeForChangedRowCount(str, objArr, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public int executeForCursorWindow(String str, Object[] objArr, CursorWindow cursorWindow, int i, int i2, boolean z, int i3, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (cursorWindow == null) {
                throw new IllegalArgumentException("window must not be null.");
            }
            if (executeSpecial(str, objArr, i3, cancellationSignal)) {
                cursorWindow.clear();
                return 0;
            }
            acquireConnection(str, i3, cancellationSignal);
            try {
                return this.mConnection.executeForCursorWindow(str, objArr, cursorWindow, i, i2, z, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public long executeForLastInsertedRowId(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (executeSpecial(str, objArr, i, cancellationSignal)) {
                return 0L;
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                return this.mConnection.executeForLastInsertedRowId(str, objArr, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public long executeForLong(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (executeSpecial(str, objArr, i, cancellationSignal)) {
                return 0L;
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                return this.mConnection.executeForLong(str, objArr, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public String executeForString(String str, Object[] objArr, int i, CancellationSignal cancellationSignal) {
        if (str != null) {
            if (executeSpecial(str, objArr, i, cancellationSignal)) {
                return null;
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                return this.mConnection.executeForString(str, objArr, cancellationSignal);
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public boolean hasConnection() {
        return this.mConnection != null;
    }

    public boolean hasNestedTransaction() {
        Transaction transaction = this.mTransactionStack;
        return (transaction == null || transaction.mParent == null) ? false : true;
    }

    public boolean hasTransaction() {
        return this.mTransactionStack != null;
    }

    public void prepare(String str, int i, CancellationSignal cancellationSignal, SQLiteStatementInfo sQLiteStatementInfo) {
        if (str != null) {
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            acquireConnection(str, i, cancellationSignal);
            try {
                this.mConnection.prepare(str, sQLiteStatementInfo);
                return;
            } finally {
                releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public void setTransactionSuccessful() {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mTransactionStack.mMarkedSuccessful = true;
    }

    public boolean yieldTransaction(long j, boolean z, CancellationSignal cancellationSignal) {
        if (z) {
            throwIfNoTransaction();
            throwIfTransactionMarkedSuccessful();
            throwIfNestedTransaction();
        } else {
            Transaction transaction = this.mTransactionStack;
            if (transaction == null || transaction.mMarkedSuccessful || transaction.mParent != null) {
                return false;
            }
        }
        if (this.mConnection == null) {
            throw new AssertionError();
        }
        if (!this.mTransactionStack.mChildFailed) {
            return yieldTransactionUnchecked(j, cancellationSignal);
        }
        return false;
    }
}
