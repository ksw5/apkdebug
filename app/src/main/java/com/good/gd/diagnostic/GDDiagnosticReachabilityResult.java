package com.good.gd.diagnostic;

/* loaded from: classes.dex */
public class GDDiagnosticReachabilityResult {
    private int mRequestID;
    private String mResult;

    public GDDiagnosticReachabilityResult(String str, int i) {
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
