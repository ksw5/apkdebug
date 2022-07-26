package com.good.gd.ndkproxy.auth;

import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public interface GDFingerprintAuthenticationCallback {
    boolean checkFingerprintAuthenticationExpiry();

    int getAuthenticatorType();

    void handleKeyPermanentlyInvalidated();

    void onAuthenticationError(int i, CharSequence charSequence);

    void onAuthenticationFailed();

    void onAuthenticationHelp(int i, CharSequence charSequence);

    void onAuthenticationTimedOut();

    void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z);

    void onDecryptSuccess(byte[] bArr);

    void onEncryptSuccess(byte[] bArr);

    void onSensorLockout();
}
