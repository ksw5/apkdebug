package com.good.gd.database;

/* loaded from: classes.dex */
public class DatabaseObjectNotClosedException extends RuntimeException {
    private static final String s = "Application did not close the cursor or database object that was opened here";
    private static final long serialVersionUID = 1616703651943129083L;

    public DatabaseObjectNotClosedException() {
        super(s);
    }
}
