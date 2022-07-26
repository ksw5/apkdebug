package com.good.gd.ndkproxy.auth.biometric.permission;

/* loaded from: classes.dex */
public class FingerprintPermissions implements BiometricPermission {
    @Override // com.good.gd.ndkproxy.auth.biometric.permission.BiometricPermission
    public String getPermissions() {
        return "android.permission.USE_FINGERPRINT";
    }
}
