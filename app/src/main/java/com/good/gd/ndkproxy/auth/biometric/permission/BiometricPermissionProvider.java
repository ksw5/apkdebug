package com.good.gd.ndkproxy.auth.biometric.permission;

import android.os.Build;

/* loaded from: classes.dex */
public class BiometricPermissionProvider implements BiometricPermission {
    private BiometricPermission permissions;

    public BiometricPermissionProvider() {
        if (Build.VERSION.SDK_INT >= 28) {
            this.permissions = new BiometricPermissions();
        } else {
            this.permissions = new FingerprintPermissions();
        }
    }

    @Override // com.good.gd.ndkproxy.auth.biometric.permission.BiometricPermission
    public String getPermissions() {
        return this.permissions.getPermissions();
    }
}
