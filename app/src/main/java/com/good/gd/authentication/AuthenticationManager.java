package com.good.gd.authentication;

import android.content.Intent;
import com.good.gd.authentication.ndkproxy.impl.AuthManagerImpl;

/* loaded from: classes.dex */
public class AuthenticationManager {
    public static final String GD_RE_AUTHENTICATION_EVENT = "com.good.gd.GD_RE_AUTHENTICATION_EVENT";

    public static ReAuthType getReauthenticationAuthType(Intent intent) {
        return AuthManagerImpl.getReauthenticationAuthType(intent);
    }

    public static ReAuthResult getReauthenticationResult(Intent intent) {
        return AuthManagerImpl.getReauthenticationResult(intent);
    }

    public static String getReauthenticationToken(Intent intent) {
        return AuthManagerImpl.getReauthenticationToken(intent);
    }

    public static String reauthenticate(String str, String str2, int i, int i2, boolean z, boolean z2) {
        return AuthManagerImpl.reauthenticate(str, str2, i, i2, z, z2);
    }

    public static String reauthenticate(String str, String str2, int i, int i2) {
        return reauthenticate(str, str2, i, i2, false, false);
    }

    public static String reauthenticate(String str, String str2, int i) {
        return reauthenticate(str, str2, i, 0);
    }
}
