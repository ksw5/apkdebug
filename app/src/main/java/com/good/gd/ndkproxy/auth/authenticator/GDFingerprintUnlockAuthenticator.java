package com.good.gd.ndkproxy.auth.authenticator;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;

/* loaded from: classes.dex */
public interface GDFingerprintUnlockAuthenticator extends GDFingerprintAuthenticator {

    /* loaded from: classes.dex */
    public interface Callback extends GDFingerprintAuthenticator.Callback {
        void onColdStartAuthenticationSuccess(byte[] bArr);

        void onWarmStartAuthenticationSuccess();
    }

    /* loaded from: classes.dex */
    public enum FingerprintUsage {
        ALLOWED,
        EXPIRED,
        NOT_ALLOWED
    }

    FingerprintUsage getFingerprintUsage();
}
