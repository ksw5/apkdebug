package com.good.gd.ndkproxy.auth.android;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public class BBDAndroidMAuthenticator extends FingerprintManager.AuthenticationCallback implements BBDAndroidAuthenticator {
    public static final String FINGERPRINT_ERROR_MANAGER_UNAVAILABLE_TEXT = "Context is null";
    private AuthenticationCallback callback;
    private FingerprintManager fingerprintManager;

    @Override // com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator
    public void authenticate(Cipher cipher, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback) {
        this.callback = authenticationCallback;
        FingerprintManager fingerprintManager = this.fingerprintManager;
        if (fingerprintManager == null) {
            authenticationCallback.onAuthenticationError(1, "Context is null");
        } else {
            fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), cancellationSignal, 0, this, null);
        }
    }

    public void initFingerprintManager(Context context) {
        if (context != null) {
            this.fingerprintManager = (FingerprintManager) context.getApplicationContext().getSystemService(FingerprintManager.class);
        }
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        super.onAuthenticationError(i, charSequence);
        this.callback.onAuthenticationError(i, charSequence);
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.callback.onAuthenticationFailed();
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        super.onAuthenticationHelp(i, charSequence);
        this.callback.onAuthenticationHelp(i, charSequence);
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
        super.onAuthenticationSucceeded(authenticationResult);
        this.callback.onAuthenticationSucceeded(authenticationResult.getCryptoObject().getCipher());
    }
}
