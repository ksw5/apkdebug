package com.good.gd.ui.biometric.callback;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class CallbackAdapter implements GDFingerprintAuthenticator.Callback {
    private GDFingerprintAuthenticator.Callback callback;

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onAuthenticationError(i, charSequence);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationFailed() {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onAuthenticationFailed();
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onAuthenticationHelp(i, charSequence);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationOperationDenied(String str) {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onAuthenticationOperationDenied(str);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationTimedOut() {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onAuthenticationTimedOut();
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onCryptoFailure(generalSecurityException, z);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onSensorLockout() {
        GDFingerprintAuthenticator.Callback callback = this.callback;
        if (callback != null) {
            callback.onSensorLockout();
        }
    }

    public void setCallback(GDFingerprintAuthenticator.Callback callback) {
        this.callback = callback;
    }
}
