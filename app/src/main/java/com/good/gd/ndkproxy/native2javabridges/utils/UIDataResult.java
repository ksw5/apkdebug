package com.good.gd.ndkproxy.native2javabridges.utils;

/* loaded from: classes.dex */
public class UIDataResult {
    public int mCode;
    public String mErrorMessage;
    public boolean mSuccess;

    public UIDataResult(boolean z, int i, String str) {
        this.mSuccess = z;
        this.mCode = i;
        this.mErrorMessage = str;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public int getResultCode() {
        return this.mCode;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }
}
