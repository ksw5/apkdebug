package com.good.gd.ndkproxy.auth;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;

/* loaded from: classes.dex */
public interface FingerprintAuthenticationManager {
    void activateFingerprint(byte[] bArr);

    GDFingerprintActivationAuthenticator getActivationAuthenticator(boolean z);

    GDFingerprintAuthenticationHandler getAuthenticationHandler();

    GDFingerprintUnlockAuthenticator getUnlockAuthenticator(boolean z);

    boolean hasDeviceFingerprintsChanged();

    boolean hasDevicePasswordSettingsChanged();

    void notifyUnlockAcknowledgedByUser();

    void resetActivation();

    void setDeviceFingerprintsChanged(boolean z);

    void setDeviceFingerprintsDeactivated();

    void setUnlockAcceptedByUser(boolean z);

    void setUnlockAcceptedByUserAndNotify(boolean z);

    boolean unlockWithFingerprint();

    boolean unlockWithFingerprint(byte[] bArr);
}
