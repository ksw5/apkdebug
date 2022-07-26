package com.good.gd.database.sqlite;

import android.database.Cursor;

/* loaded from: classes.dex */
public interface CrossProcessCursor extends Cursor {
    void fillWindow(int i, CursorWindow cursorWindow);

    CursorWindow getWindow();

    boolean onMove(int i, int i2);
}
