package com.good.gd.ndkproxy.auth;

/* loaded from: classes.dex */
public class GDFingerPrintUnlockStateHolder implements FingerPrintUnlockStateHolder {
    @Override // com.good.gd.ndkproxy.auth.FingerPrintUnlockStateHolder
    public int getUnlockState() {
        return GDFingerprintAuthenticationManager.getUnlockState();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerPrintUnlockStateHolder
    public boolean hasColdStartData() {
        return GDFingerprintAuthenticationManager.hasColdStartData();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerPrintUnlockStateHolder
    public void invalidateColdStartData() {
        GDFingerprintAuthenticationManager.invalidateColdStartData();
    }

    @Override // com.good.gd.ndkproxy.auth.FingerPrintUnlockStateHolder
    public void setUnlockState(int i) {
        GDFingerprintAuthenticationManager.setUnlockState(i);
    }
}
