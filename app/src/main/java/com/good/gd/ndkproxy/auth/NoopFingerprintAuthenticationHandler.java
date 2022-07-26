package com.good.gd.ndkproxy.auth;

import android.content.Context;
import com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class NoopFingerprintAuthenticationHandler implements GDFingerprintAuthenticationHandler {
    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public String createColdStartDataHashString(byte[] bArr) {
        return "N/A";
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void deleteKeyStoreKey() {
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public BBDAndroidAuthenticator getBBDAndroidAuthenticator() {
        return null;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public int getHandlerType() {
        return 65536;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public int getUiUnlockType() {
        return 0;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean hasUserEnrolledFingerprints() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void initCrypto(boolean z) {
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isCryptoReady() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isDeviceFingerprintCapable() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isKeyguardSecure() {
        return true;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isListeningForFingerprint() {
        return false;
    }

    public boolean shouldRestartListenOnError() {
        return false;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public void startFingerprintEnrollment(Context context) {
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public GDFingerprintAuthenticationCancellationSignal startListeningForDecrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException {
        return null;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException {
        return null;
    }

    public String toString() {
        return "'No fingerprint support' GDFingerprintAuthenticationHandler";
    }
}
