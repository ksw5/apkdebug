package com.good.gd.ndkproxy.auth;

import android.content.Context;
import com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public interface GDFingerprintAuthenticationHandler {
    String createColdStartDataHashString(byte[] bArr);

    void deleteKeyStoreKey();

    BBDAndroidAuthenticator getBBDAndroidAuthenticator();

    int getHandlerType();

    int getUiUnlockType();

    boolean hasUserEnrolledFingerprints();

    void initCrypto(boolean z);

    boolean isCryptoReady();

    boolean isDeviceFingerprintCapable();

    boolean isKeyguardSecure();

    boolean isListeningForFingerprint();

    void startFingerprintEnrollment(Context context);

    GDFingerprintAuthenticationCancellationSignal startListeningForDecrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException;

    GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) throws GeneralSecurityException;
}
