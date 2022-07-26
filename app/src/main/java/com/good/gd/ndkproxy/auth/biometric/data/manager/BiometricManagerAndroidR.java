package com.good.gd.ndkproxy.auth.biometric.data.manager;

import android.hardware.biometrics.BiometricManager;
import com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider;

/* loaded from: classes.dex */
public class BiometricManagerAndroidR implements BiometricDataProvider {
    private BiometricManager biometricManager = BiometricManagerCreator.createManagerOrThrow();

    @Override // com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider
    public boolean hasEnrolledFingerprints() {
        return this.biometricManager.canAuthenticate(15) != 11;
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider
    public boolean isHardwareDetected() {
        return this.biometricManager.canAuthenticate(15) != 12;
    }
}
