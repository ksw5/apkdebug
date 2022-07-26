package com.good.gd.database.sqlite;

import com.good.gd.database.SQLException;

/* loaded from: classes.dex */
public class SQLiteException extends SQLException {
    private static final long serialVersionUID = 8046790972602852283L;

    public SQLiteException() {
    }

    public SQLiteException(String str) {
        super(str);
    }

    public SQLiteException(String str, Throwable th) {
        super(str, th);
    }
}
