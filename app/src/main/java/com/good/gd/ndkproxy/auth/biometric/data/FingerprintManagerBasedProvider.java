package com.good.gd.ndkproxy.auth.biometric.data;

import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class FingerprintManagerBasedProvider implements BiometricDataProvider {
    private FingerprintManager fingerprintManager = (FingerprintManager) GTBaseContext.getInstance().getApplicationContext().getSystemService(FingerprintManager.class);

    public FingerprintManagerBasedProvider() throws Exception {
        PackageManager packageManager = GTBaseContext.getInstance().getApplicationContext().getPackageManager();
        if (packageManager.hasSystemFeature("android.hardware.fingerprint") || this.fingerprintManager != null) {
            return;
        }
        throw new UnsupportedOperationException("No fingerprint feature available");
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider
    public boolean hasEnrolledFingerprints() {
        return this.fingerprintManager.hasEnrolledFingerprints();
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.data.BiometricDataProvider
    public boolean isHardwareDetected() {
        return this.fingerprintManager.isHardwareDetected();
    }
}
