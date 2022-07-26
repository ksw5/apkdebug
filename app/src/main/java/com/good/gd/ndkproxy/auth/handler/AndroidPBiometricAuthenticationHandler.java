package com.good.gd.ndkproxy.auth.handler;

import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.android.AuthenticationCallback;
import com.good.gd.ndkproxy.auth.android.BBDAndroidAuthenticatorCreator;
import com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes.dex */
public class AndroidPBiometricAuthenticationHandler extends AbstractCommonAuthenticationHandler implements GDFingerprintAuthenticationHandler {

    /* loaded from: classes.dex */
    private abstract class fdyxd extends CryptoLogic implements AuthenticationCallback {
        fdyxd(AndroidPBiometricAuthenticationHandler androidPBiometricAuthenticationHandler, GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback) {
            super(gDFingerprintAuthenticationCallback);
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic, com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
            super.onAuthenticationError(i, charSequence);
            if (i == 3) {
                getAuthenticationCallback().onAuthenticationTimedOut();
            } else if (i == 7) {
                getAuthenticationCallback().onSensorLockout();
            } else if (i != 5) {
                getAuthenticationCallback().onAuthenticationError(i, charSequence);
            } else {
                onFingerprintCancelled();
            }
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic, com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic, com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
            super.onAuthenticationHelp(i, charSequence);
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic, com.good.gd.ndkproxy.auth.android.AuthenticationCallback
        public void onAuthenticationSucceeded(Cipher cipher) {
            super.onAuthenticationSucceeded(cipher);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends fdyxd {
        final /* synthetic */ byte[] dbjc;
        final /* synthetic */ GDFingerprintAuthenticationCallback qkduk;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        hbfhc(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr, GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback2) {
            super(AndroidPBiometricAuthenticationHandler.this, gDFingerprintAuthenticationCallback);
            this.dbjc = bArr;
            this.qkduk = gDFingerprintAuthenticationCallback2;
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic
        protected void initCipher(Cipher cipher, SecretKey secretKey) throws GeneralSecurityException {
            cipher.init(1, secretKey);
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic
        protected void transform(Cipher cipher) throws GeneralSecurityException {
            byte[] doFinal = cipher.doFinal(this.dbjc);
            byte[] blob = AndroidPBiometricAuthenticationHandler.this.toBlob(cipher.getIV(), doFinal);
            if (blob != null) {
                this.qkduk.onEncryptSuccess(blob);
            }
        }
    }

    /* loaded from: classes.dex */
    class yfdke extends fdyxd {
        final /* synthetic */ byte[] dbjc;
        final /* synthetic */ GDFingerprintAuthenticationCallback jwxax;
        final /* synthetic */ byte[] qkduk;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        yfdke(AndroidPBiometricAuthenticationHandler androidPBiometricAuthenticationHandler, GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr, byte[] bArr2, GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback2) {
            super(androidPBiometricAuthenticationHandler, gDFingerprintAuthenticationCallback);
            this.dbjc = bArr;
            this.qkduk = bArr2;
            this.jwxax = gDFingerprintAuthenticationCallback2;
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic
        protected void initCipher(Cipher cipher, SecretKey secretKey) throws GeneralSecurityException {
            cipher.init(2, secretKey, new IvParameterSpec(this.dbjc));
        }

        @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler.CryptoLogic
        protected void transform(Cipher cipher) throws GeneralSecurityException {
            this.jwxax.onDecryptSuccess(cipher.doFinal(this.qkduk));
        }
    }

    public AndroidPBiometricAuthenticationHandler() throws Exception {
        this.bbdAndroidAuthenticator = new BBDAndroidAuthenticatorCreator().createAuthenticator();
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public int getHandlerType() {
        return GDFingerprintAuthenticationManager.HANDLER_TYPE_ANDROID_BIOMETRIC;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public int getUiUnlockType() {
        return 2;
    }

    @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler
    public boolean isListeningForFingerprint() {
        return this.isListening;
    }

    @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler
    protected CryptoLogic obtainDecryptCryptologic(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr, byte[] bArr2) {
        if (AbstractCommonAuthenticationHandler.decryptCryptoLogic == null) {
            AbstractCommonAuthenticationHandler.decryptCryptoLogic = new yfdke(this, gDFingerprintAuthenticationCallback, bArr, bArr2, gDFingerprintAuthenticationCallback);
        }
        return AbstractCommonAuthenticationHandler.decryptCryptoLogic;
    }

    @Override // com.good.gd.ndkproxy.auth.handler.AbstractCommonAuthenticationHandler
    protected CryptoLogic obtainEncryptCryptologic(GDFingerprintAuthenticationCallback gDFingerprintAuthenticationCallback, byte[] bArr) {
        if (AbstractCommonAuthenticationHandler.encryptCryptoLogic == null) {
            AbstractCommonAuthenticationHandler.encryptCryptoLogic = new hbfhc(gDFingerprintAuthenticationCallback, bArr, gDFingerprintAuthenticationCallback);
        }
        return AbstractCommonAuthenticationHandler.encryptCryptoLogic;
    }

    public boolean shouldRestartListenOnError() {
        return false;
    }
}
