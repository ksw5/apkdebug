package com.good.gd.ndkproxy.auth.android;

import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import java.lang.ref.WeakReference;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public class BBDAndroidPAuthenticator extends BiometricPrompt.AuthenticationCallback implements BBDAndroidAuthenticator {
    public static final String BIOMETRIC_ERROR_CONTEXT_UNAVAILABLE_TEXT = "Context is null";
    public static final String BIOMETRIC_ERROR_PROMPT_UNAVAILABLE_TEXT = "BiometricPrompt is null";
    private BiometricPrompt biometricPrompt;
    private AuthenticationCallback callback;
    private WeakReference<Context> contextWeakReference;

    public void authenticate(CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback) {
        this.callback = authenticationCallback;
        if (this.biometricPrompt == null) {
            authenticationCallback.onAuthenticationError(-3, BIOMETRIC_ERROR_PROMPT_UNAVAILABLE_TEXT);
            return;
        }
        WeakReference<Context> weakReference = this.contextWeakReference;
        if (weakReference == null) {
            authenticationCallback.onAuthenticationError(-3, "Context is null");
            return;
        }
        Context context = weakReference.get();
        if (context == null) {
            authenticationCallback.onAuthenticationError(-3, "Context is null");
        } else {
            this.biometricPrompt.authenticate(cancellationSignal, context.getMainExecutor(), this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public BiometricPrompt getBiometricPrompt() {
        return this.biometricPrompt;
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        super.onAuthenticationError(i, charSequence);
        this.callback.onAuthenticationError(i, charSequence);
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.callback.onAuthenticationFailed();
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        super.onAuthenticationHelp(i, charSequence);
        this.callback.onAuthenticationHelp(i, charSequence);
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
        super.onAuthenticationSucceeded(authenticationResult);
        this.callback.onAuthenticationSucceeded(authenticationResult.getCryptoObject().getCipher());
    }

    public void setBiometricPrompt(BiometricPrompt biometricPrompt) {
        this.biometricPrompt = biometricPrompt;
    }

    public void setContext(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    @Override // com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticator
    public void authenticate(Cipher cipher, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback) {
        this.callback = authenticationCallback;
        if (this.biometricPrompt == null) {
            authenticationCallback.onAuthenticationError(-3, BIOMETRIC_ERROR_PROMPT_UNAVAILABLE_TEXT);
            return;
        }
        WeakReference<Context> weakReference = this.contextWeakReference;
        if (weakReference == null) {
            authenticationCallback.onAuthenticationError(-3, "Context is null");
            return;
        }
        Context context = weakReference.get();
        if (context == null) {
            authenticationCallback.onAuthenticationError(-3, "Context is null");
        } else {
            this.biometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipher), cancellationSignal, context.getMainExecutor(), this);
        }
    }
}
