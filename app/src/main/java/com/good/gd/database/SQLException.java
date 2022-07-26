package com.good.gd.database;

/* loaded from: classes.dex */
public class SQLException extends RuntimeException {
    private static final long serialVersionUID = 6534309159622786366L;

    public SQLException() {
    }

    public SQLException(String str) {
        super(str);
    }

    public SQLException(String str, Throwable th) {
        super(str, th);
    }
}
