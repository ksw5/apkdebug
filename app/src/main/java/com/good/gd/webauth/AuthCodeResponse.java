package com.good.gd.webauth;

import android.net.Uri;
import com.good.gd.ndkproxy.GDLog;
import java.util.Set;

/* loaded from: classes.dex */
public class AuthCodeResponse {
    public static final String AUTH_CODE_KEY = "code";
    public static final String STATE_KEY = "state";
    private final String authCode;
    private final String state;

    public AuthCodeResponse(String str, String str2) {
        this.authCode = str;
        this.state = str2;
    }

    public static AuthCodeResponse parseURL(Uri uri) {
        if (uri == null) {
            return null;
        }
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        if (queryParameterNames.contains(AUTH_CODE_KEY) && queryParameterNames.contains(STATE_KEY)) {
            return new AuthCodeResponse(uri.getQueryParameter(AUTH_CODE_KEY), uri.getQueryParameter(STATE_KEY));
        }
        GDLog.DBGPRINTF(14, "AuthCodeResponse::parseURL invalid URL\n");
        return null;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public String getState() {
        return this.state;
    }
}
