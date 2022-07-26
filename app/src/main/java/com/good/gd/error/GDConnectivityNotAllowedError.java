package com.good.gd.error;

/* loaded from: classes.dex */
public class GDConnectivityNotAllowedError extends GDError {
    private static final long serialVersionUID = 7132299694020287728L;

    public GDConnectivityNotAllowedError() {
        super("Connectivity is not allowed in this Mode.");
    }

    public GDConnectivityNotAllowedError(String str) {
        super(str);
    }
}
