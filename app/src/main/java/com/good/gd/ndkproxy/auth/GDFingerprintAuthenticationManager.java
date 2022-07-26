package com.good.gd.ndkproxy.auth;

import android.os.Build;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.authenticator.ActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.ColdStartAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.WarmStartAuthenticator;
import com.good.gd.ndkproxy.auth.handler.AndroidMFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.handler.AndroidPBiometricAuthenticationHandler;

/* loaded from: classes.dex */
public class GDFingerprintAuthenticationManager implements FingerprintAuthenticationManager {
    public static final int HANDLER_TYPE_ANDROID_BIOMETRIC = 196608;
    public static final int HANDLER_TYPE_FLAGS = 16711680;
    public static final int HANDLER_TYPE_NOOP = 65536;
    public static final int HANDLER_TYPE_NOT_SET = 0;
    public static final int UI_TYPE_BIOMETRIC_FINGERPRINT = 2;
    public static final int UI_TYPE_FINGERPRINT = 1;
    public static final int UI_TYPE_NONE = 0;
    public static final int UNLOCK_STATE_COLD_START_ACTIVATED = 1024;
    public static final int UNLOCK_STATE_COLD_START_ALLOWED = 256;
    public static final int UNLOCK_STATE_COLD_START_DECLINED = 512;
    public static final int UNLOCK_STATE_COLD_START_FLAGS = 65280;
    private static final int UNLOCK_STATE_DECLINED = 514;
    public static final int UNLOCK_STATE_WARM_START_ACTIVATED = 4;
    public static final int UNLOCK_STATE_WARM_START_ALLOWED = 1;
    public static final int UNLOCK_STATE_WARM_START_DECLINED = 2;
    public static final int UNLOCK_STATE_WARM_START_FLAGS = 255;
    private static final GDFingerprintAuthenticationManager instance = new GDFingerprintAuthenticationManager();
    private GDFingerprintAuthenticationHandler handler;
    private boolean isHandlerValidated = false;
    private FingerPrintUnlockStateHolder fingerPrintUnlockStateHolder = new GDFingerPrintUnlockStateHolder();

    private GDFingerprintAuthenticationManager() {
    }

    public static boolean changeUnlockState(int i, int i2) {
        int unlockState = getUnlockState();
        int i3 = ((~i) & unlockState) | i2;
        if (unlockState != i3) {
            setUnlockState(i3);
            return true;
        }
        return false;
    }

    private boolean checkHandler(GDFingerprintAuthenticationHandler gDFingerprintAuthenticationHandler, int i) {
        int i2 = i & HANDLER_TYPE_FLAGS;
        if (i2 == 0) {
            return changeUnlockState(0, gDFingerprintAuthenticationHandler.getHandlerType());
        }
        if (i2 == gDFingerprintAuthenticationHandler.getHandlerType()) {
            return false;
        }
        GDLog.DBGPRINTF(13, "Handler has changed, resetting \n");
        int handlerType = gDFingerprintAuthenticationHandler.getHandlerType();
        gDFingerprintAuthenticationHandler.initCrypto(false);
        invalidateColdStartData();
        return changeUnlockState(i2 | 4 | 1024, handlerType);
    }

    public static native byte[] getColdStartData();

    public static native String getColdStartDataHash();

    public static native byte[] getColdStartKey();

    public static GDFingerprintAuthenticationManager getInstance() {
        return instance;
    }

    public static native String getKeyStoreAlias();

    public static native int getUnlockState();

    private boolean handleAllowed(boolean z, boolean z2, boolean z3) {
        return initState(z, z2, z3);
    }

    public static native boolean hasColdStartData();

    public static boolean hasFlags(int i, int i2) {
        return (i & i2) == i2;
    }

    public static native void invalidateColdStartData();

    private static native boolean isColdStartAllowedByPolicy();

    private boolean isDeviceFingerprintCapable() {
        return this.handler.isDeviceFingerprintCapable();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        if (hasFlags(r1, 2) == false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean isFingerprintEnabledByPolicyAndUser() {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 0
            com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler r1 = r4.handler     // Catch: java.lang.Throwable -> L32
            boolean r1 = r1.isDeviceFingerprintCapable()     // Catch: java.lang.Throwable -> L32
            r2 = 1
            if (r1 == 0) goto L30
            com.good.gd.ndkproxy.auth.FingerPrintUnlockStateHolder r1 = r4.fingerPrintUnlockStateHolder     // Catch: java.lang.Throwable -> L32
            int r1 = r1.getUnlockState()     // Catch: java.lang.Throwable -> L32
            r3 = 256(0x100, float:3.59E-43)
            boolean r3 = hasFlags(r1, r3)     // Catch: java.lang.Throwable -> L32
            if (r3 == 0) goto L21
            r3 = 512(0x200, float:7.175E-43)
            boolean r3 = hasFlags(r1, r3)     // Catch: java.lang.Throwable -> L32
            if (r3 == 0) goto L2f
        L21:
        L22:
            boolean r3 = hasFlags(r1, r2)     // Catch: java.lang.Throwable -> L32
            if (r3 == 0) goto L30
            r3 = 2
            boolean r1 = hasFlags(r1, r3)     // Catch: java.lang.Throwable -> L32
            if (r1 != 0) goto L30
        L2f:
            r0 = r2
        L30:
            monitor-exit(r4)
            return r0
        L32:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.isFingerprintEnabledByPolicyAndUser():boolean");
    }

    public static native boolean isUnlockAllowedByExpiry();

    private static native boolean isWarmStartAllowedByPolicy();

    private native void ndkInit();

    public static native void notifyUnlockAcceptedByUser();

    public static native void setColdStartData(byte[] bArr, String str);

    private static native void setDeviceFingerprintSetChanged(boolean z);

    public static native void setKeyStoreAlias(String str);

    public static native void setUnlockState(int i);

    private static native boolean unlockWithFingerprint0();

    private static native boolean unlockWithFingerprint1(byte[] bArr);

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public synchronized void activateFingerprint(byte[] bArr) {
        int i = 4;
        if (bArr != null) {
            setColdStartData(bArr, this.handler.createColdStartDataHashString(bArr));
            i = 1028;
        }
        changeUnlockState(UNLOCK_STATE_DECLINED, i);
    }

    public native byte[] createHash(byte[] bArr);

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public GDFingerprintActivationAuthenticator getActivationAuthenticator(boolean z) {
        if (z) {
            GDLog.DBGPRINTF(12, "Can't use activation fingerprint authenticator when container locked \n");
            return null;
        }
        GDLog.DBGPRINTF(14, "Using activation fingerprint authenticator \n");
        return new ActivationAuthenticator();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public GDFingerprintAuthenticationHandler getAuthenticationHandler() {
        return this.handler;
    }

    public native int getRequirePwdNotFingerprintPeriod();

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public GDFingerprintUnlockAuthenticator getUnlockAuthenticator(boolean z) {
        if (z) {
            GDLog.DBGPRINTF(14, "Using cold start fingerprint authenticator \n");
            return new ColdStartAuthenticator();
        }
        GDLog.DBGPRINTF(14, "Using warm start fingerprint authenticator \n");
        return new WarmStartAuthenticator();
    }

    public native void handleInvalidFingerprint();

    public void handlerWasValidated() {
        this.isHandlerValidated = true;
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public native boolean hasDeviceFingerprintsChanged();

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public native boolean hasDevicePasswordSettingsChanged();

    public void init() {
        ndkInit();
        if (Build.VERSION.SDK_INT >= 28) {
            GDLog.DBGPRINTF(14, "Trying Android P biometric support \n");
            try {
                AndroidPBiometricAuthenticationHandler androidPBiometricAuthenticationHandler = new AndroidPBiometricAuthenticationHandler();
                this.handler = androidPBiometricAuthenticationHandler;
                if (!androidPBiometricAuthenticationHandler.isDeviceFingerprintCapable()) {
                    this.handler = null;
                    GDLog.DBGPRINTF(14, "Device does not support Android P biometric \n");
                } else {
                    GDLog.DBGPRINTF(14, "Android P biometric support being used \n");
                }
            } catch (Throwable th) {
                GDLog.DBGPRINTF(14, "Android P biometric not supported (" + th + ")\n");
            }
        } else {
            GDLog.DBGPRINTF(14, "Trying Android N fingerprint support \n");
            try {
                AndroidMFingerprintAuthenticationHandler androidMFingerprintAuthenticationHandler = new AndroidMFingerprintAuthenticationHandler();
                this.handler = androidMFingerprintAuthenticationHandler;
                if (!androidMFingerprintAuthenticationHandler.isDeviceFingerprintCapable()) {
                    this.handler = null;
                    GDLog.DBGPRINTF(14, "Device does not support Android N fingerprint \n");
                } else {
                    GDLog.DBGPRINTF(14, "Android N fingerprint support being used \n");
                }
            } catch (Throwable th2) {
                GDLog.DBGPRINTF(14, "Android N fingerprint not supported (" + th2 + ")\n");
            }
        }
        GDFingerprintAuthenticationHandler gDFingerprintAuthenticationHandler = this.handler;
        if (gDFingerprintAuthenticationHandler == null || (gDFingerprintAuthenticationHandler != null && !gDFingerprintAuthenticationHandler.isDeviceFingerprintCapable())) {
            GDLog.DBGPRINTF(14, "Fingerprint/Biometric is not supported");
            this.handler = new NoopFingerprintAuthenticationHandler();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x015b A[Catch: all -> 0x01a2, TryCatch #0 {, blocks: (B:3:0x0001, B:9:0x000c, B:11:0x001b, B:13:0x002a, B:14:0x0035, B:17:0x0053, B:18:0x005b, B:20:0x0063, B:22:0x006b, B:23:0x0074, B:25:0x007c, B:26:0x0084, B:28:0x008b, B:33:0x00bf, B:34:0x00c7, B:36:0x00ce, B:38:0x00d6, B:39:0x00df, B:41:0x00e7, B:42:0x00f3, B:44:0x00fb, B:45:0x0103, B:47:0x010a, B:48:0x0155, B:50:0x015b, B:51:0x0192, B:54:0x019d, B:59:0x0110, B:61:0x0118, B:63:0x011e, B:65:0x0127, B:67:0x012e, B:69:0x0134, B:71:0x013c, B:72:0x0146, B:73:0x014d, B:74:0x0091, B:76:0x0098, B:78:0x009e, B:80:0x00a7, B:82:0x00ae, B:83:0x00b4), top: B:2:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    synchronized boolean initState(boolean r11, boolean r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager.initState(boolean, boolean, boolean):boolean");
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public void notifyUnlockAcknowledgedByUser() {
        notifyUnlockAcceptedByUser();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public synchronized void resetActivation() {
        changeUnlockState(1028, 0);
        invalidateColdStartData();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public synchronized void setDeviceFingerprintsChanged(boolean z) {
        setDeviceFingerprintSetChanged(z);
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public synchronized void setDeviceFingerprintsDeactivated() {
        changeUnlockState(1028, 0);
    }

    public void setFingerPrintUnlockStateHolder(FingerPrintUnlockStateHolder fingerPrintUnlockStateHolder) {
        this.fingerPrintUnlockStateHolder = fingerPrintUnlockStateHolder;
    }

    public void setFingerprintAuthenticationHandler(GDFingerprintAuthenticationHandler gDFingerprintAuthenticationHandler) {
        this.handler = gDFingerprintAuthenticationHandler;
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public synchronized void setUnlockAcceptedByUser(boolean z) {
        if (z) {
            changeUnlockState(UNLOCK_STATE_DECLINED, 0);
        } else {
            changeUnlockState(1028, UNLOCK_STATE_DECLINED);
            invalidateColdStartData();
        }
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public void setUnlockAcceptedByUserAndNotify(boolean z) {
        setUnlockAcceptedByUser(z);
        notifyUnlockAcceptedByUser();
    }

    public boolean supportsFingerprintAuthentication(boolean z) {
        isWarmStartAllowedByPolicy();
        isColdStartAllowedByPolicy();
        GDFingerprintActivationAuthenticator activationAuthenticator = getActivationAuthenticator(z);
        return activationAuthenticator != null && activationAuthenticator.canConfigureFingerprint(null, null);
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public boolean unlockWithFingerprint(byte[] bArr) {
        return unlockWithFingerprint1(bArr);
    }

    @Override // com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager
    public boolean unlockWithFingerprint() {
        return unlockWithFingerprint0();
    }
}
