package com.good.gd.ndkproxy.auth.biometric.data.manager;

import android.hardware.biometrics.BiometricManager;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class BiometricManagerCreator {
    public static BiometricManager createManagerOrThrow() {
        BiometricManager biometricManager = (BiometricManager) GTBaseContext.getInstance().getApplicationContext().getSystemService(BiometricManager.class);
        if (biometricManager != null) {
            return biometricManager;
        }
        throw new UnsupportedOperationException("No biometric feature available");
    }
}
