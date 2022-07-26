package com.good.gd.ndkproxy.auth.authenticator;

import kotlinx.coroutines.scheduling.WorkQueueKt;

/* loaded from: classes.dex */
public abstract class AbstractUnlockAuthenticator extends AbstractAuthenticator implements GDFingerprintUnlockAuthenticator {
    @Override // com.good.gd.ndkproxy.auth.authenticator.AbstractAuthenticator
    protected int getCanUseFingerprintFlags() {
        return WorkQueueKt.MASK;
    }
}
