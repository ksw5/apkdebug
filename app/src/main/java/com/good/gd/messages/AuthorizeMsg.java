package com.good.gd.messages;

/* loaded from: classes.dex */
public class AuthorizeMsg {
    public String appId = null;
    public String appVersion = null;
    public boolean isBackgroundAuth = false;
    public String activationProvisionId = null;
    public String activationAccessKey = null;

    public String toString() {
        return "AuthorizeMsg";
    }
}
