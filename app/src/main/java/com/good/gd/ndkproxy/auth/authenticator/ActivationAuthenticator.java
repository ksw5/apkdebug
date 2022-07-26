package com.good.gd.ndkproxy.auth.authenticator;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import java.security.UnrecoverableKeyException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class ActivationAuthenticator extends AbstractAuthenticator implements GDFingerprintActivationAuthenticator {

    /* loaded from: classes.dex */
    private class yfdke extends hbfhc {
        private final WeakReference<GDFingerprintActivationAuthenticator.Callback> jwxax;
        private final boolean qkduk;

        @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator.hbfhc
        protected boolean dbjc() {
            return false;
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public int getAuthenticatorType() {
            return 1;
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onDecryptSuccess(byte[] bArr) {
            throw new IllegalStateException("Should not have been called");
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onEncryptSuccess(byte[] bArr) {
            GDFingerprintActivationAuthenticator.Callback callback = this.jwxax.get();
            if (callback != null) {
                if (!this.qkduk) {
                    bArr = null;
                }
                callback.onActivationSuccess(bArr);
            }
        }

        private yfdke(ActivationAuthenticator activationAuthenticator, boolean z, GDFingerprintActivationAuthenticator.Callback callback) {
            super(activationAuthenticator, callback);
            this.qkduk = z;
            this.jwxax = new WeakReference<>(callback);
        }
    }

    private int getCanConfigureFingerprintFlags() {
        return 9;
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator
    public synchronized boolean canConfigureFingerprint(AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2) {
        boolean z;
        int canConfigureFingerprintFlags = getCanConfigureFingerprintFlags();
        int flags = getFlags();
        if (GDFingerprintAuthenticationManager.hasFlags(flags, canConfigureFingerprintFlags)) {
            if (atomicBoolean != null) {
                atomicBoolean.set(GDFingerprintAuthenticationManager.hasFlags(flags, 32));
            }
            if (atomicBoolean2 != null) {
                atomicBoolean2.set(GDFingerprintAuthenticationManager.hasFlags(flags, 64));
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator
    protected int getCanUseFingerprintFlags() {
        return 11;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
        if (r7 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0040, code lost:
        if (r2 != false) goto L10;
     */
    @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int getFlags() {
        /*
            r9 = this;
            int r0 = r9.getBaseFlags()
            int r1 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.getUnlockState()
            r2 = 256(0x100, float:3.59E-43)
            boolean r2 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r2)
            r3 = 1
            boolean r4 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r3)
            r5 = 1024(0x400, float:1.435E-42)
            boolean r5 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r5)
            r6 = 4
            boolean r6 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r6)
            r7 = 512(0x200, float:7.175E-43)
            boolean r7 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r7)
            r7 = r7 ^ r3
            r8 = 2
            boolean r1 = com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.hasFlags(r1, r8)
            r1 = r1 ^ r3
            if (r2 != 0) goto L2f
            if (r4 == 0) goto L31
        L2f:
            r0 = r0 | 8
        L31:
            r8 = 0
            if (r4 == 0) goto L3c
            if (r2 == 0) goto L3c
            if (r6 == 0) goto L43
            if (r5 == 0) goto L43
            r5 = r3
            goto L44
        L3c:
            if (r4 == 0) goto L40
            r5 = r6
            goto L44
        L40:
            if (r2 == 0) goto L43
            goto L44
        L43:
            r5 = r8
        L44:
            if (r5 == 0) goto L48
            r0 = r0 | 64
        L48:
            if (r4 == 0) goto L51
            if (r2 == 0) goto L51
            if (r1 == 0) goto L59
            if (r7 == 0) goto L59
            goto L5a
        L51:
            if (r4 == 0) goto L55
            r3 = r1
            goto L5a
        L55:
            if (r2 == 0) goto L59
            r3 = r7
            goto L5a
        L59:
            r3 = r8
        L5a:
            if (r3 == 0) goto L5e
            r0 = r0 | 32
        L5e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ndkproxy.auth.authenticator.ActivationAuthenticator.getFlags():int");
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator
    public synchronized boolean listen(GDFingerprintAuthenticator.Callback callback) {
        boolean z = false;
        try {
            if (getFingerprintUsage() == GDFingerprintUnlockAuthenticator.FingerprintUsage.ALLOWED) {
                assertCanListen();
                int unlockState = GDFingerprintAuthenticationManager.getUnlockState();
                if (GDFingerprintAuthenticationManager.hasFlags(unlockState, 256) && !GDFingerprintAuthenticationManager.hasFlags(unlockState, 1024)) {
                    GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt = this.handler.startListeningForEncrypt(new yfdke(true, (GDFingerprintActivationAuthenticator.Callback) callback), GDFingerprintAuthenticationManager.getColdStartKey());
                    this.cancellationSignal = startListeningForEncrypt;
                    if (startListeningForEncrypt != null) {
                        z = true;
                    }
                    return z;
                }
                GDFingerprintAuthenticationCancellationSignal startListeningForEncrypt2 = this.handler.startListeningForEncrypt(new yfdke(false, (GDFingerprintActivationAuthenticator.Callback) callback), new byte[]{0});
                this.cancellationSignal = startListeningForEncrypt2;
                if (startListeningForEncrypt2 != null) {
                    z = true;
                }
                return z;
            }
        } catch (UnrecoverableKeyException e) {
            GDLog.DBGPRINTF(12, "Reset keystore and activation due to exception : " + e + "\n");
            this.handler.deleteKeyStoreKey();
            GDFingerprintAuthenticationManager.getInstance().resetActivation();
        } catch (GeneralSecurityException e2) {
            GDLog.DBGPRINTF(13, "Unexpected " + e2 + "\n");
        }
        return false;
    }
}
