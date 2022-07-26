package com.good.gd.reauth;

/* loaded from: classes.dex */
public class BiometricReAuthDataHolder {
    private BiometricReAuthCallback listener;

    public BiometricReAuthDataHolder(BiometricReAuthCallback biometricReAuthCallback) {
        this.listener = biometricReAuthCallback;
    }

    public BiometricReAuthCallback getListener() {
        return this.listener;
    }

    public void setListener(BiometricReAuthCallback biometricReAuthCallback) {
        this.listener = biometricReAuthCallback;
    }
}
