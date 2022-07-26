package com.good.gd.ndkproxy.auth.authenticator;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class ColdStartAuthenticator extends AbstractUnlockAuthenticator implements GDFingerprintUnlockAuthenticator {
    private String coldStartDataHash;

    /* loaded from: classes.dex */
    private class yfdke extends hbfhc {
        @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator.hbfhc
        protected boolean dbjc() {
            return GDFingerprintAuthenticationManager.hasFlags(ColdStartAuthenticator.this.getFlags(), 64);
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public int getAuthenticatorType() {
            return 2;
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onDecryptSuccess(byte[] bArr) {
            GDFingerprintUnlockAuthenticator.Callback callback = (GDFingerprintUnlockAuthenticator.Callback) this.dbjc.get();
            if (callback != null) {
                callback.onColdStartAuthenticationSuccess(bArr);
            }
            if (ColdStartAuthenticator.this.coldStartDataHash == null) {
                GDLog.DBGPRINTF(14, "Update cold start data hash\n");
                byte[] coldStartData = GDFingerprintAuthenticationManager.getColdStartData();
                GDFingerprintAuthenticationManager.setColdStartData(coldStartData, ColdStartAuthenticator.this.handler.createColdStartDataHashString(coldStartData));
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onEncryptSuccess(byte[] bArr) {
            throw new IllegalStateException("Should not have been called");
        }

        private yfdke(GDFingerprintUnlockAuthenticator.Callback callback) {
            super(ColdStartAuthenticator.this, callback);
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator
    protected int getFlags() {
        int baseFlags = getBaseFlags();
        int unlockState = GDFingerprintAuthenticationManager.getUnlockState();
        if (GDFingerprintAuthenticationManager.hasFlags(unlockState, 256)) {
            baseFlags |= 8;
        }
        if (GDFingerprintAuthenticationManager.hasFlags(unlockState, 1024)) {
            baseFlags |= 64;
        }
        return !GDFingerprintAuthenticationManager.hasFlags(unlockState, 512) ? baseFlags | 32 : baseFlags;
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator
    public boolean listen(GDFingerprintAuthenticator.Callback callback) {
        try {
            if (getFingerprintUsage() == FingerprintUsage.ALLOWED) {
                assertCanListen();
                byte[] coldStartData = GDFingerprintAuthenticationManager.getColdStartData();
                this.coldStartDataHash = GDFingerprintAuthenticationManager.getColdStartDataHash();
                GDLog.DBGPRINTF(14, "Stored fingerprint cold start data hash = '" + this.coldStartDataHash + "'\n");
                if (coldStartData != null) {
                    try {
                        GDFingerprintAuthenticationCancellationSignal startListeningForDecrypt = this.handler.startListeningForDecrypt(new yfdke((GDFingerprintUnlockAuthenticator.Callback) callback), coldStartData);
                        this.cancellationSignal = startListeningForDecrypt;
                        return startListeningForDecrypt != null;
                    } catch (IllegalArgumentException e) {
                        GDLog.DBGPRINTF(13, "Cold start data is bad, deactivating \n");
                        GDFingerprintAuthenticationManager.changeUnlockState(1024, 0);
                        GDFingerprintAuthenticationManager.invalidateColdStartData();
                    }
                }
            }
        } catch (GeneralSecurityException e2) {
            GDLog.DBGPRINTF(13, "Unexpected " + e2 + "\n");
        }
        return false;
    }
}
