package com.good.gd.database.sqlite;

import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class SQLiteProgram extends SQLiteClosable {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final Object[] mBindArgs;
    private final String[] mColumnNames;
    private final SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    private final String mSql;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SQLiteProgram(SQLiteDatabase sQLiteDatabase, String str, Object[] objArr, CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        String trim = str.trim();
        this.mSql = trim;
        int sqlStatementType = DatabaseUtils.getSqlStatementType(trim);
        switch (sqlStatementType) {
            case 4:
            case 5:
            case 6:
                this.mReadOnly = false;
                this.mColumnNames = EMPTY_STRING_ARRAY;
                this.mNumParameters = 0;
                break;
            default:
                boolean z = sqlStatementType != 1 ? false : true;
                SQLiteStatementInfo sQLiteStatementInfo = new SQLiteStatementInfo();
                sQLiteDatabase.getThreadSession().prepare(trim, sQLiteDatabase.getThreadDefaultConnectionFlags(z), cancellationSignal, sQLiteStatementInfo);
                this.mReadOnly = sQLiteStatementInfo.readOnly;
                this.mColumnNames = sQLiteStatementInfo.columnNames;
                this.mNumParameters = sQLiteStatementInfo.numParameters;
                break;
        }
        if (objArr != null && objArr.length > this.mNumParameters) {
            throw new IllegalArgumentException("Too many bind arguments.  " + objArr.length + " arguments were provided but the statement needs " + this.mNumParameters + " arguments.");
        }
        int i = this.mNumParameters;
        if (i != 0) {
            Object[] objArr2 = new Object[i];
            this.mBindArgs = objArr2;
            if (objArr == null) {
                return;
            }
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
            return;
        }
        this.mBindArgs = null;
    }

    private void bind(int i, Object obj) {
        if (i >= 1 && i <= this.mNumParameters) {
            this.mBindArgs[i - 1] = obj;
            return;
        }
        throw new IllegalArgumentException("Cannot bind argument at index " + i + " because the index is out of range.  The statement has " + this.mNumParameters + " parameters.");
    }

    public void bindAllArgsAsStrings(String[] strArr) {
        if (strArr != null) {
            for (int length = strArr.length; length != 0; length--) {
                bindString(length, strArr[length - 1]);
            }
        }
    }

    public void bindBlob(int i, byte[] bArr) {
        if (bArr != null) {
            bind(i, bArr);
            return;
        }
        throw new IllegalArgumentException("the bind value at index " + i + " is null");
    }

    public void bindDouble(int i, double d) {
        bind(i, Double.valueOf(d));
    }

    public void bindLong(int i, long j) {
        bind(i, Long.valueOf(j));
    }

    public void bindNull(int i) {
        bind(i, null);
    }

    public void bindString(int i, String str) {
        if (str != null) {
            bind(i, str);
            return;
        }
        throw new IllegalArgumentException("the bind value at index " + i + " is null");
    }

    public void clearBindings() {
        Object[] objArr = this.mBindArgs;
        if (objArr != null) {
            Arrays.fill(objArr, (Object) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Object[] getBindArgs() {
        return this.mBindArgs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String[] getColumnNames() {
        return this.mColumnNames;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String getSql() {
        return this.mSql;
    }

    @Deprecated
    public final int getUniqueId() {
        return -1;
    }

    @Override // com.good.gd.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        clearBindings();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void onCorruption() {
        this.mDatabase.onCorruption();
    }
}
