package com.good.gd.ndkproxy.auth.biometric.prompt;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;

/* loaded from: classes.dex */
public interface BiometricPromptProvider {
    BiometricPrompt getBiometricPrompt(Context context, BiometricPromptData biometricPromptData);
}
