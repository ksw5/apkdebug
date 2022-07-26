package com.good.gd.database.sqlite;

/* loaded from: classes.dex */
public interface SQLiteTransactionListener {
    void onBegin();

    void onCommit();

    void onRollback();
}
