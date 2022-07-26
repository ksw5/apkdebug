package com.good.gd;

/* loaded from: classes.dex */
public final class GDResult {
    public static final int GDSuccess = 0;
    int code;
    String description;
    int type;

    public GDResult(int i) {
        this.type = i;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public int getType() {
        return this.type;
    }

    public boolean isSuccess() {
        return this.type == 0;
    }

    public GDResult(int i, int i2, String str) {
        this.type = i;
        this.code = i2;
        this.description = str;
    }
}
