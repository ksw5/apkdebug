package com.good.gd.ndkproxy;

import android.util.Log;
import com.good.gd.utils.GDSDKType;

/* loaded from: classes.dex */
public final class GDLog {
    public static final int DEFAULT_LEVEL = 18;
    public static final int DETAIL_BOUNDARY = 15;
    public static final int DO_NOT_LOG_BOUNDARY = 10;
    public static final String GDWEAR_LOG_TAG = "GDWEARABLE";
    public static final int LOG_ALWAYS = 11;
    public static final int LOG_DETAIL = 16;
    public static final int LOG_ERROR = 12;
    public static final int LOG_INFO = 14;
    public static final int LOG_NET_ANALYTICS = 17;
    public static final int LOG_OFF = 19;
    public static final int LOG_WARNING = 13;
    private static boolean _isInitialized = false;
    private static GDSDKType _sdkType;

    public static void DBGPRINTF(int i, String str) {
        if (_isInitialized) {
            if (_sdkType == GDSDKType.ESDKTypeHandheld) {
                log(i, str);
            } else if (_sdkType != GDSDKType.ESDKTypeWearable) {
            } else {
                log(i, str);
            }
        }
    }

    public static void DBGPRINTF_UNSECURE(int i, String str, String str2) {
        if (_isInitialized) {
            DBGPRINTF(i, str2);
        } else {
            mapToAndroidLog(i, str, str2);
        }
    }

    public static void a(int i, String str) {
        DBGPRINTF(i, str);
    }

    public static void initialize(GDSDKType gDSDKType) {
        _isInitialized = true;
        _sdkType = gDSDKType;
    }

    private static native void log(int i, String str);

    private static void mapToAndroidLog(int i, String str, String str2) {
        switch (i) {
            case 12:
                Log.e(str, str2);
                return;
            case 13:
                Log.w(str, str2);
                return;
            case 14:
                Log.i(str, str2);
                return;
            default:
                Log.d(str, str2);
                return;
        }
    }

    public static native void printLongValue(long j);

    public static void a(int i, String str, String... strArr) {
        DBGPRINTF(i, str, strArr);
    }

    public static void a(int i, String str, Throwable th) {
        DBGPRINTF(i, str, th);
    }

    public static void DBGPRINTF(int i, String str, String... strArr) {
        StringBuffer append = new StringBuffer(str).append(" ");
        for (String str2 : strArr) {
            append.append(str2);
        }
        DBGPRINTF(i, append.toString());
    }

    public static void DBGPRINTF(int i, String str, Throwable th) {
        DBGPRINTF(i, str + " [" + th.toString() + "]\n");
    }
}
