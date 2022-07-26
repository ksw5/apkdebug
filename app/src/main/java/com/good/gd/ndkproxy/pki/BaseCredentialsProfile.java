package com.good.gd.ndkproxy.pki;

import com.good.gd.pki.CredentialsProfile;

/* loaded from: classes.dex */
public class BaseCredentialsProfile extends CredentialsProfile {
    private void setId(String str) {
        this.id = str;
    }

    private void setName(String str) {
        this.name = str;
    }

    private void setProviderSettings(String str) {
        this.providerSettings = str;
    }

    private void setRequired(boolean z) {
        this.required = Boolean.valueOf(z);
    }

    private void setState(State state) {
        this.state = state;
    }

    private void setType(String str) {
        this.type = str;
    }
}
