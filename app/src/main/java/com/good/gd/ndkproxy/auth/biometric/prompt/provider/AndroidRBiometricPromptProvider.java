package com.good.gd.ndkproxy.auth.biometric.prompt.provider;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptData;

/* loaded from: classes.dex */
public class AndroidRBiometricPromptProvider extends AndroidPBiometricPromptProvider {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ndkproxy.auth.biometric.prompt.provider.AndroidPBiometricPromptProvider
    public BiometricPrompt.Builder createPromptBuilderFromData(Context context, BiometricPromptData biometricPromptData) {
        BiometricPrompt.Builder createPromptBuilderFromData = super.createPromptBuilderFromData(context, biometricPromptData);
        createPromptBuilderFromData.setConfirmationRequired(true);
        createPromptBuilderFromData.setAllowedAuthenticators(15);
        return createPromptBuilderFromData;
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.prompt.provider.AndroidPBiometricPromptProvider, com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptProvider
    public BiometricPrompt getBiometricPrompt(Context context, BiometricPromptData biometricPromptData) {
        return createPromptBuilderFromData(context, biometricPromptData).build();
    }
}
