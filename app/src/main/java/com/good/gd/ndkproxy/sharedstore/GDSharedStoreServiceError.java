package com.good.gd.ndkproxy.sharedstore;

/* loaded from: classes.dex */
public class GDSharedStoreServiceError {
    public static final String TAG = "GDSharedStoreServiceError";
    private int code;
    private String message;

    public GDSharedStoreServiceError() {
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public GDSharedStoreServiceError(int i, String str) {
        this.code = i;
        this.message = str;
    }
}
