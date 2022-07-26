package com.good.gd.ndkproxy.auth.authenticator;

import android.content.Context;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCancellationSignal;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public abstract class AbstractAuthenticator implements GDFingerprintAuthenticator {
    protected GDFingerprintAuthenticationCancellationSignal cancellationSignal;
    protected GDFingerprintAuthenticationHandler handler = GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler();

    /* loaded from: classes.dex */
    abstract class hbfhc implements GDFingerprintAuthenticationCallback {
        protected final WeakReference<Callback> dbjc;

        /* JADX INFO: Access modifiers changed from: package-private */
        public hbfhc(AbstractAuthenticator abstractAuthenticator, Callback callback) {
            this.dbjc = new WeakReference<>(callback);
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public boolean checkFingerprintAuthenticationExpiry() {
            if (!dbjc() || GDFingerprintAuthenticationManager.isUnlockAllowedByExpiry()) {
                return false;
            }
            GDLog.DBGPRINTF(18, "Fingerprint has timed out and password must be given\n");
            return true;
        }

        protected abstract boolean dbjc();

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void handleKeyPermanentlyInvalidated() {
            GDLog.DBGPRINTF(13, "Fingerprint has been permanently invalidated\n");
            GDFingerprintAuthenticationManager.changeUnlockState(1024, 0);
            GDFingerprintAuthenticationManager.invalidateColdStartData();
            GDFingerprintAuthenticationManager.getInstance().setDeviceFingerprintsChanged(true);
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onAuthenticationError(int i, CharSequence charSequence) {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onAuthenticationError(i, charSequence);
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onAuthenticationFailed() {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onAuthenticationFailed();
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onAuthenticationHelp(i, charSequence);
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onAuthenticationTimedOut() {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onAuthenticationTimedOut();
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onCryptoFailure(generalSecurityException, z);
            }
        }

        @Override // com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationCallback
        public void onSensorLockout() {
            Callback callback = this.dbjc.get();
            if (callback != null) {
                callback.onSensorLockout();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void assertCanListen() {
        if (this.cancellationSignal == null) {
            return;
        }
        throw new IllegalStateException("Cannot listen more than once on an authenticator");
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator
    public void cancel() {
        GDFingerprintAuthenticationCancellationSignal gDFingerprintAuthenticationCancellationSignal = this.cancellationSignal;
        if (gDFingerprintAuthenticationCancellationSignal != null) {
            gDFingerprintAuthenticationCancellationSignal.cancel();
            this.cancellationSignal = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getBaseFlags() {
        if (this.handler.isDeviceFingerprintCapable()) {
            int i = 1;
            if (this.handler.hasUserEnrolledFingerprints()) {
                i = 3;
            }
            if (this.handler.isCryptoReady()) {
                i |= 4;
            }
            GDFingerprintAuthenticationManager.getInstance();
            return GDFingerprintAuthenticationManager.isUnlockAllowedByExpiry() ? i | 16 : i;
        }
        return 0;
    }

    protected abstract int getCanUseFingerprintFlags();

    public synchronized GDFingerprintUnlockAuthenticator.FingerprintUsage getFingerprintUsage() {
        GDFingerprintUnlockAuthenticator.FingerprintUsage fingerprintUsage;
        int canUseFingerprintFlags = getCanUseFingerprintFlags();
        int flags = getFlags();
        boolean hasFlags = GDFingerprintAuthenticationManager.hasFlags(flags, canUseFingerprintFlags);
        boolean z = !hasFlags && GDFingerprintAuthenticationManager.hasFlags(flags, canUseFingerprintFlags & (-17));
        GDLog.DBGPRINTF(14, String.format("getFingerprintUsage required=0x%x actual=0x%x allowed=%b allowedButExpired=%b  \n", Integer.valueOf(canUseFingerprintFlags), Integer.valueOf(flags), Boolean.valueOf(hasFlags), Boolean.valueOf(z)));
        if (hasFlags) {
            fingerprintUsage = GDFingerprintUnlockAuthenticator.FingerprintUsage.ALLOWED;
        } else {
            fingerprintUsage = z ? GDFingerprintUnlockAuthenticator.FingerprintUsage.EXPIRED : GDFingerprintUnlockAuthenticator.FingerprintUsage.NOT_ALLOWED;
        }
        return fingerprintUsage;
    }

    protected abstract int getFlags();

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator
    public boolean hasEnrolledFingerprints() {
        return GDFingerprintAuthenticationManager.hasFlags(getFlags(), 2);
    }

    public boolean isKeyguardSecure() {
        return this.handler.isKeyguardSecure();
    }

    public void startFingerprintEnrollment(Context context) {
        this.handler.startFingerprintEnrollment(context);
    }
}
