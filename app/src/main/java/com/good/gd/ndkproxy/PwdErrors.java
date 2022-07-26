package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public final class PwdErrors {
    public static final int BANNED_PWD = 10;
    public static final int NOT_ALPHA_NUM = 8;
    public static final int NOT_MIXED_CASE = 9;
    public static final int NO_DIGIT = 4;
    public static final int NO_NUM_SEQUENCE = 7;
    public static final int NO_REPEATS = 6;
    public static final int NO_SPECIAL = 5;
    public static final int NO_UPPERCASE = 3;
    public static final int NULL_PWD = 1;
    public static final int OK_SUCCESS = 0;
    public static final int SHORT_LENTH = 2;

    public static native void initialize();
}
