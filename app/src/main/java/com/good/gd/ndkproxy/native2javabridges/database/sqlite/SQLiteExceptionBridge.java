package com.good.gd.ndkproxy.native2javabridges.database.sqlite;

import com.good.gd.database.sqlite.SQLiteException;

/* loaded from: classes.dex */
final class SQLiteExceptionBridge extends SQLiteException {
    public SQLiteExceptionBridge() {
    }

    public SQLiteExceptionBridge(String str) {
        super(str);
    }

    public SQLiteExceptionBridge(String str, Throwable th) {
        super(str, th);
    }
}
