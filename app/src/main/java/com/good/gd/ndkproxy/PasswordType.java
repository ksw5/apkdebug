package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public final class PasswordType {
    public static final String SMNOPASS = "SM_NO_PASS";
    public static final String SMNOTYETSET = "UNKNOWN";
    public static final String SMPASS = "SM_PASS";
    public static final String SMPIN = "SM_PIN";
    public static final String SMREMOTE = "SM_REMOTE";
    public static final String SMSSO = "SM_SSO";
    public static final String SMUDID = "SM_UDID";
    public static final int SM_NOT_YET_SET = 100;
    public static final int SM_NO_PASS = 5;
    public static final int SM_PASS = 2;
    public static final int SM_PASS_ERROR = 10;
    public static final int SM_PIN = 1;
    public static final int SM_REMOTE = 3;
    public static final int SM_SSO = 4;
    public static final int SM_UDID = 0;

    public static native void initialize();

    public static String stringFor(int i) {
        switch (i) {
            case 0:
                return SMUDID;
            case 1:
                return SMPIN;
            case 2:
                return SMPASS;
            case 3:
                return SMREMOTE;
            case 4:
                return SMSSO;
            case 5:
                return SMNOPASS;
            default:
                return SMNOTYETSET;
        }
    }
}
