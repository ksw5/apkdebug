package com.good.gd.whhmi;

import android.os.Build;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class yfdke {
    private static boolean dbjc;
    private static final Pattern qkduk;

    static {
        try {
            Class.forName("com.good.gd.GDAndroid");
            dbjc = true;
        } catch (ClassNotFoundException e) {
            dbjc = false;
        }
        qkduk = Pattern.compile("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
    }

    public static boolean dbjc() {
        return ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue();
    }

    public static boolean jwxax() {
        return BlackberryAnalyticsCommon.rynix().jwxax().checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
    }

    public static boolean liflu() {
        return !dbjc;
    }

    public static boolean lqox() {
        return dbjc;
    }

    public static boolean qkduk() {
        return ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).muee();
    }

    public static boolean wxau() {
        return Build.VERSION.SDK_INT < 24;
    }

    public static boolean ztwf() {
        return BlackberryAnalyticsCommon.rynix().jwxax().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static Double dbjc(String str) {
        if (true == qkduk(str) || !qkduk.matcher(str).matches()) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static boolean qkduk(String str) {
        return str == null || str.trim().isEmpty();
    }
}
