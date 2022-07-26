package com.good.gd.ndkproxy.reauth;

import com.good.gd.reauth.BiometricReAuthCallback;
import com.good.gd.reauth.BiometricReAuthDataHolder;

/* loaded from: classes.dex */
public class BiometricReAuth {
    private static BiometricReAuthDataHolder pendingReAuthData;

    private static synchronized void onReAuthDone(boolean z) {
        synchronized (BiometricReAuth.class) {
            BiometricReAuthDataHolder biometricReAuthDataHolder = pendingReAuthData;
            if (biometricReAuthDataHolder != null) {
                biometricReAuthDataHolder.getListener().onReAuthDone(z);
                pendingReAuthData = null;
            }
        }
    }

    public static native void reAuthenticateForBiometrics();

    public static synchronized void reAuthenticateForBiometrics(BiometricReAuthCallback biometricReAuthCallback) {
        synchronized (BiometricReAuth.class) {
            if (pendingReAuthData == null) {
                pendingReAuthData = new BiometricReAuthDataHolder(biometricReAuthCallback);
                reAuthenticateForBiometrics();
            }
        }
    }

    public static synchronized void subsrcibeForPendingReAuth(BiometricReAuthCallback biometricReAuthCallback) {
        synchronized (BiometricReAuth.class) {
            BiometricReAuthDataHolder biometricReAuthDataHolder = pendingReAuthData;
            if (biometricReAuthDataHolder != null) {
                biometricReAuthDataHolder.setListener(biometricReAuthCallback);
            }
        }
    }
}
