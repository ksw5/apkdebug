package com.good.gd.webauth;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
class AuthProviderConfig {
    private final String dbjc;

    private AuthProviderConfig(String str) {
        this.dbjc = str;
    }

    public static AuthProviderConfig dbjc(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        return new AuthProviderConfig(jSONObject.getString("authorization_endpoint"));
    }

    public String dbjc() {
        return this.dbjc;
    }
}
