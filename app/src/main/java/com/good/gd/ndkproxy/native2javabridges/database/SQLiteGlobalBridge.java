package com.good.gd.ndkproxy.native2javabridges.database;

import com.good.gd.database.sqlite.SQLiteGlobal;

/* loaded from: classes.dex */
final class SQLiteGlobalBridge {
    SQLiteGlobalBridge() {
    }

    private static String getSQLiteGlobalClassString() {
        return SQLiteGlobal.class.getName().replace(".", "/");
    }
}
