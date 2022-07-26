package com.good.gd.database.sqlite;

/* loaded from: classes.dex */
public class CursorIndexOutOfBoundsException extends IndexOutOfBoundsException {
    private static final long serialVersionUID = 8374118005314709713L;

    public CursorIndexOutOfBoundsException(int i, int i2) {
        super("Index " + i + " requested, with a size of " + i2);
    }

    public CursorIndexOutOfBoundsException(String str) {
        super(str);
    }
}
