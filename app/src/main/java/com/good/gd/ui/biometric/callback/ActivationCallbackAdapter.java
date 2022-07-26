package com.good.gd.ui.biometric.callback;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;

/* loaded from: classes.dex */
public class ActivationCallbackAdapter extends CallbackAdapter implements GDFingerprintActivationAuthenticator.Callback {
    private GDFingerprintActivationAuthenticator.Callback callback;

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator.Callback
    public void onActivationSuccess(byte[] bArr) {
        GDFingerprintActivationAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onActivationSuccess(bArr);
        }
    }

    public void setActivationCallback(GDFingerprintActivationAuthenticator.Callback callback) {
        this.callback = callback;
    }
}
