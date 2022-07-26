package com.good.gd.error;

/* loaded from: classes.dex */
public class GDError extends Error {
    private static final long serialVersionUID = -3968415486823381780L;

    public GDError(String str) {
        super(str);
    }

    public GDError(Throwable th) {
        super(th);
    }
}
