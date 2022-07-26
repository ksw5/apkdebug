package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager;
import com.good.gd.ndkproxy.reauth.BiometricReAuth;
import com.good.gd.reauth.BiometricReAuthCallback;
import com.good.gd.utils.UserChecker;

/* loaded from: classes.dex */
public class ActivateFingerprintWithConfirmationUI extends ActivateFingerprintUI implements BiometricReAuthCallback {
    private byte[] activationBlob;

    public ActivateFingerprintWithConfirmationUI(long j, UserChecker userChecker, FingerprintAuthenticationManager fingerprintAuthenticationManager) {
        super(j, userChecker, fingerprintAuthenticationManager);
        BiometricReAuth.subsrcibeForPendingReAuth(this);
    }

    @Override // com.good.gd.ndkproxy.ui.data.ActivateFingerprintUI, com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator.Callback
    public void onActivationSuccess(byte[] bArr) {
        this.fingerprintAuthenticationManager.setUnlockAcceptedByUser(true);
        this.activationBlob = bArr;
        BiometricReAuth.reAuthenticateForBiometrics(this);
    }

    @Override // com.good.gd.reauth.BiometricReAuthCallback
    public void onReAuthDone(boolean z) {
        if (z) {
            super.onActivationSuccess(this.activationBlob);
        } else {
            handleCancelEvent();
        }
    }
}
