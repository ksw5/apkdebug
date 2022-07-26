package com.good.gd.ndkproxy.native2javabridges.database.sqlite;

import com.good.gd.database.sqlite.SQLiteCustomFunction;

/* loaded from: classes.dex */
final class SQLiteCustomFunctionBridge {
    SQLiteCustomFunctionBridge() {
    }

    private static void dispatchCallback(SQLiteCustomFunction sQLiteCustomFunction, String[] strArr) {
        sQLiteCustomFunction.callback.callback(strArr);
    }

    private static String getName(SQLiteCustomFunction sQLiteCustomFunction) {
        return sQLiteCustomFunction.name;
    }

    private static int getNumArgs(SQLiteCustomFunction sQLiteCustomFunction) {
        return sQLiteCustomFunction.numArgs;
    }
}
