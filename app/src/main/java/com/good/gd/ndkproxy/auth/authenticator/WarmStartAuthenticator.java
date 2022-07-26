package com.good.gd.ndkproxy.auth.authenticator;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class WarmStartAuthenticator extends AbstractUnlockAuthenticator implements GDFingerprintUnlockAuthenticator {

    /* loaded from: classes.dex */
    private class yfdke extends hbfhc {
        @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator.hbfhc
        protected boolean dbjc() {
            return GDFingerprintAuthenticationManager.hasFlags(WarmStartAuthenticator.this.getFlags(), 64);
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public int getAuthenticatorType() {
            return 3;
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onDecryptSuccess(byte[] bArr) {
            throw new IllegalStateException("Should not have been called");
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onEncryptSuccess(byte[] bArr) {
            GDFingerprintUnlockAuthenticator.Callback callback = (GDFingerprintUnlockAuthenticator.Callback) this.dbjc.get();
            if (callback != null) {
                callback.onWarmStartAuthenticationSuccess();
            }
        }

        private yfdke(GDFingerprintUnlockAuthenticator.Callback callback) {
            super(WarmStartAuthenticator.this, callback);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator
    protected int getFlags() {
        int baseFlags = getBaseFlags();
        int unlockState = GDFingerprintAuthenticationManager.getUnlockState();
        if (GDFingerprintAuthenticationManager.hasFlags(unlockState, 1)) {
            baseFlags |= 8;
        }
        if (GDFingerprintAuthenticationManager.hasFlags(unlockState, 4)) {
            baseFlags |= 64;
        }
        return !GDFingerprintAuthenticationManager.hasFlags(unlockState, 2) ? baseFlags | 32 : baseFlags;
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator
    public boolean listen(GDFingerprintAuthenticator.Callback callback) {
        try {
        } catch (GeneralSecurityException e) {
            GDLog.DBGPRINTF(13, "Unexpected " + e + "\n");
        }
        if (getFingerprintUsage() == FingerprintUsage.ALLOWED) {
            assertCanListen();
            GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt = this.handler.startListeningForEncrypt(new yfdke((GDFingerprintUnlockAuthenticator.Callback) callback), new byte[]{0});
            this.cancellationSignal = startListeningForEncrypt;
            return startListeningForEncrypt != null;
        }
        GDLog.DBGPRINTF(13, "Not able to start listening, so touch is going to fail. \n");
        return false;
    }
}
