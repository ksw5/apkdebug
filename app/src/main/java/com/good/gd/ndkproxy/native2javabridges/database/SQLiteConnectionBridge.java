package com.good.gd.ndkproxy.native2javabridges.database;

import com.good.gd.database.sqlite.SQLiteConnection;

/* loaded from: classes.dex */
final class SQLiteConnectionBridge {
    SQLiteConnectionBridge() {
    }

    private static String getSQLiteConnectionClassString() {
        return SQLiteConnection.class.getName().replace(".", "/");
    }
}
