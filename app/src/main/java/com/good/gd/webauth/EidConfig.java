package com.good.gd.webauth;

/* loaded from: classes.dex */
public class EidConfig {
    private final String clientId;
    private final String eidHostDiscovery;
    private final String redirectUri;
    private final String scope;

    public EidConfig(String str, String str2, String str3, String str4) {
        this.eidHostDiscovery = str;
        this.clientId = str2;
        this.redirectUri = str3;
        this.scope = str4;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getEidHostDiscovery() {
        return this.eidHostDiscovery;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public String getScope() {
        return this.scope;
    }
}
