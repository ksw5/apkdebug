package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public final class PasswordFailReason {
    public static final int REASON_CHANGE_TOO_SOON = 3;
    public static final int REASON_OLD_PASSWORD_WRONG = 2;
    public static final int REASON_PASSWORD_HISTORY = 1;
    public static final int REASON_UKNOWN = 0;

    public static native void initialize();
}
