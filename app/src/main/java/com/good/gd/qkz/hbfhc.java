package com.good.gd.qkz;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import com.good.gt.ndkproxy.util.GTLog;

/* loaded from: classes.dex */
public class hbfhc {
    public static boolean dbjc(Context context) {
        int i = Build.VERSION.SDK_INT;
        if (28 <= i) {
            dbjc(new Throwable(), 13, "DCLU", "SDK Version " + i + " is greater than or equal to Android P.");
            return jwxax(context);
        }
        dbjc(new Throwable(), 13, "DCLU", "SDK Version " + i + " is lower than Android P.");
        return qkduk(context);
    }

    public static void jwxax(Throwable th, String str, String str2) {
        dbjc(th, 13, str, str2);
    }

    public static void qkduk(Throwable th, String str, String str2) {
        dbjc(th, 12, str, str2);
    }

    private static boolean jwxax(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null) {
            dbjc(new Throwable(), 13, "DCLU", "Unable to instantiate LocationManager.");
            return false;
        }
        boolean isLocationEnabled = locationManager.isLocationEnabled();
        dbjc(new Throwable(), 13, "DCLU", "Device Location Services Enabled: " + isLocationEnabled);
        return isLocationEnabled;
    }

    private static boolean qkduk(Context context) {
        boolean z = false;
        try {
            if (Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0) {
                z = true;
            }
            dbjc(new Throwable(), 13, "DCLU", "Device Location Services Enabled: " + z);
            return z;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void dbjc(Throwable th, String str, String str2) {
        dbjc(th, 16, str, str2);
    }

    public static void dbjc(String str, String str2, Throwable th) {
        dbjc(th, 12, str, str2);
        dbjc(th, 12, str, "Exception occurred " + th.toString());
        dbjc(th, 12, str, "stack: trace:");
        StackTraceElement[] stackTrace = th.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            GTLog.DBGPRINTF(12, i + ": " + stackTrace[i].toString() + "\n");
        }
    }

    private static void dbjc(Throwable th, int i, String str, String str2) {
        GTLog.DBGPRINTF(i, "[M]" + str + "(" + th.getStackTrace()[0].getLineNumber() + "):" + str2 + ".\n");
    }
}
