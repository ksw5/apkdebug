package com.good.gd.ui.biometric.callback;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;

/* loaded from: classes.dex */
public class UnlockCallbackAdapter extends CallbackAdapter implements GDFingerprintUnlockAuthenticator.Callback {
    private GDFingerprintUnlockAuthenticator.Callback callback;

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator.Callback
    public void onColdStartAuthenticationSuccess(byte[] bArr) {
        GDFingerprintUnlockAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onColdStartAuthenticationSuccess(bArr);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator.Callback
    public void onWarmStartAuthenticationSuccess() {
        GDFingerprintUnlockAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onWarmStartAuthenticationSuccess();
        }
    }

    public void setUnlockCallback(GDFingerprintUnlockAuthenticator.Callback callback) {
        this.callback = callback;
    }
}
