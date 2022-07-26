package com.good.gd.ndkproxy.auth.android;

import android.os.Build;

/* loaded from: classes.dex */
public class BBDAndroidAuthenticatorCreator {
    public BBDAndroidAuthenticator createAuthenticator() {
        boolean equalsIgnoreCase = Build.MANUFACTURER.equalsIgnoreCase("samsung");
        if (!(Build.VERSION.SDK_INT >= 28)) {
            return new BBDAndroidMAuthenticator();
        }
        return equalsIgnoreCase ? new BBDAndroidPSamsungAuthenticator() : new BBDAndroidPAuthenticator();
    }
}
