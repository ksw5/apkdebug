package com.good.gd.database.sqlite;

import com.good.gd.database.sqlite.SQLiteDatabase;

/* loaded from: classes.dex */
public final class SQLiteCustomFunction {
    public final SQLiteDatabase.CustomFunction callback;
    public final String name;
    public final int numArgs;

    public SQLiteCustomFunction(String str, int i, SQLiteDatabase.CustomFunction customFunction) {
        if (str != null) {
            this.name = str;
            this.numArgs = i;
            this.callback = customFunction;
            return;
        }
        throw new IllegalArgumentException("name must not be null.");
    }

    private void dispatchCallback(String[] strArr) {
        this.callback.callback(strArr);
    }
}
