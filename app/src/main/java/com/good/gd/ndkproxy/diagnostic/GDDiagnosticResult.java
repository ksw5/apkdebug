package com.good.gd.ndkproxy.diagnostic;

/* loaded from: classes.dex */
public class GDDiagnosticResult {
    private int mRequestID;
    private String mResult;

    public GDDiagnosticResult(String str, int i) {
        this.mResult = str;
        this.mRequestID = i;
    }

    public int getRequestID() {
        return this.mRequestID;
    }

    public String getResult() {
        return this.mResult;
    }
}
