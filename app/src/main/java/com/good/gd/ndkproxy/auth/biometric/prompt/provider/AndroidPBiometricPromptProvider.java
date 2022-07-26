package com.good.gd.ndkproxy.auth.biometric.prompt.provider;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptData;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptProvider;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class AndroidPBiometricPromptProvider implements BiometricPromptProvider {
    private WeakReference<BiometricPrompt> biometricPromptCached;
    private BiometricPromptData cachedData;

    /* JADX INFO: Access modifiers changed from: protected */
    public BiometricPrompt.Builder createPromptBuilderFromData(Context context, BiometricPromptData biometricPromptData) {
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context);
        builder.setTitle(biometricPromptData.getTitle()).setDescription(biometricPromptData.getDescription()).setNegativeButton(biometricPromptData.getNegativeButtonText(), context.getMainExecutor(), biometricPromptData.getCancelListener());
        return builder;
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptProvider
    public BiometricPrompt getBiometricPrompt(Context context, BiometricPromptData biometricPromptData) {
        WeakReference<BiometricPrompt> weakReference = this.biometricPromptCached;
        boolean z = false;
        boolean z2 = weakReference == null || weakReference.get() == null;
        BiometricPromptData biometricPromptData2 = this.cachedData;
        if (biometricPromptData2 == null || !biometricPromptData2.equals(biometricPromptData)) {
            z = true;
        }
        if (z2 || z) {
            this.biometricPromptCached = new WeakReference<>(createPromptBuilderFromData(context, biometricPromptData).build());
            this.cachedData = biometricPromptData;
        }
        return this.biometricPromptCached.get();
    }
}
