package com.good.gd.cqlap;

/* loaded from: classes.dex */
public final class hbfhc {
    private static hbfhc dbjc;

    private hbfhc() {
    }

    public static hbfhc dbjc() {
        if (dbjc == null) {
            dbjc = new hbfhc();
        }
        return dbjc;
    }
}
