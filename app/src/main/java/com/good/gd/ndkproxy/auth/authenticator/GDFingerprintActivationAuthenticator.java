package com.good.gd.ndkproxy.auth.authenticator;

import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public interface GDFingerprintActivationAuthenticator extends GDFingerprintAuthenticator {

    /* loaded from: classes.dex */
    public interface Callback extends GDFingerprintAuthenticator.Callback {
        void onActivationSuccess(byte[] bArr);
    }

    boolean canConfigureFingerprint(AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2);
}
