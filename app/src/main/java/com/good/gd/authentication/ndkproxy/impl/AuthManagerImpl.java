package com.good.gd.authentication.ndkproxy.impl;

import android.content.Intent;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.authentication.AuthenticationManager;
import com.good.gd.authentication.ReAuthResult;
import com.good.gd.authentication.ReAuthType;

/* loaded from: classes.dex */
public class AuthManagerImpl {
    private static final String GD_RE_AUTH_RESULT_EXTRA = "gd_re_authentication_result_extra";
    private static final String GD_RE_AUTH_TOKEN_EXTRA = "gd_re_authentication_token_extra";
    private static final String GD_RE_AUTH_TYPE_EXTRA = "gd_re_authentication_auth_type_extra";

    public static ReAuthType getReauthenticationAuthType(Intent intent) {
        return ReAuthType.get(intent.getIntExtra(GD_RE_AUTH_TYPE_EXTRA, ReAuthType.None.getCode()));
    }

    public static ReAuthResult getReauthenticationResult(Intent intent) {
        return ReAuthResult.get(intent.getIntExtra(GD_RE_AUTH_RESULT_EXTRA, ReAuthResult.ErrorInvalidRequest.getCode()));
    }

    public static String getReauthenticationToken(Intent intent) {
        return intent.getStringExtra(GD_RE_AUTH_TOKEN_EXTRA);
    }

    private static native long ndkInit();

    private static void onReAuthResult(String str, int i, int i2) {
        sendAuthenticationEventBroadcast(str, i, i2);
    }

    public static String reauthenticate(String str, String str2, int i, int i2, boolean z, boolean z2) {
        return reauthenticateNative(str, str2, i, i2, z, z2);
    }

    private static native String reauthenticateNative(String str, String str2, int i, int i2, boolean z, boolean z2);

    private static void sendAuthenticationEventBroadcast(String str, int i, int i2) {
        Intent intent = new Intent();
        intent.setAction(AuthenticationManager.GD_RE_AUTHENTICATION_EVENT);
        intent.putExtra(GD_RE_AUTH_TOKEN_EXTRA, str);
        intent.putExtra(GD_RE_AUTH_RESULT_EXTRA, i);
        intent.putExtra(GD_RE_AUTH_TYPE_EXTRA, i2);
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }
}
