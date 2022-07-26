package com.good.gd.ndkproxy.auth.authenticator;

import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public interface GDFingerprintAuthenticator {
    public static final int AUTHENTICATOR_TYPE_ACTIVATION = 1;
    public static final int AUTHENTICATOR_TYPE_COLD_START = 2;
    public static final int AUTHENTICATOR_TYPE_WARM_START = 3;
    public static final int HAS_USER_ENROLLED_FINGERPRINTS = 2;
    public static final int IS_AUTH_ACCEPTED_BY_USER = 32;
    public static final int IS_AUTH_ACTIVATED = 64;
    public static final int IS_AUTH_ALLOWED_BY_EXPIRY = 16;
    public static final int IS_AUTH_ALLOWED_BY_POLICY = 8;
    public static final int IS_CRYPTO_READY = 4;
    public static final int IS_DEVICE_FINGERPRINT_CAPABLE = 1;

    /* loaded from: classes.dex */
    public interface Callback {
        void onAuthenticationError(int i, CharSequence charSequence);

        void onAuthenticationFailed();

        void onAuthenticationHelp(int i, CharSequence charSequence);

        void onAuthenticationOperationDenied(String str);

        void onAuthenticationTimedOut();

        void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z);

        void onSensorLockout();
    }

    void cancel();

    boolean hasEnrolledFingerprints();

    boolean listen(Callback callback);
}
