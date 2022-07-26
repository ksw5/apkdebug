package com.good.gd.ndkproxy.auth.biometric.prompt;

import android.os.Build;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.biometric.prompt.provider.AndroidPBiometricPromptProvider;
import com.good.gd.ndkproxy.auth.biometric.prompt.provider.AndroidQBiometricPromptProvider;
import com.good.gd.ndkproxy.auth.biometric.prompt.provider.AndroidRBiometricPromptProvider;

/* loaded from: classes.dex */
public class BiometricFactory {
    private static BiometricFactory instance;
    private BiometricPromptProvider promptProvider;

    private BiometricFactory() {
    }

    private BiometricPromptProvider createBiometricPromptProvider() {
        if (GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().getUiUnlockType() == 2) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 30) {
                return new AndroidRBiometricPromptProvider();
            }
            if (i >= 29) {
                return new AndroidQBiometricPromptProvider();
            }
            if (i < 28) {
                return null;
            }
            return new AndroidPBiometricPromptProvider();
        }
        return null;
    }

    public static BiometricFactory getInstance() {
        if (instance == null) {
            instance = new BiometricFactory();
        }
        return instance;
    }

    public BiometricPromptProvider getBiometricPromptProvider() {
        if (this.promptProvider == null) {
            this.promptProvider = createBiometricPromptProvider();
        }
        return this.promptProvider;
    }
}
