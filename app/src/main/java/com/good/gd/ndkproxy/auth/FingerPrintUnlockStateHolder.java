package com.good.gd.ndkproxy.auth;

/* loaded from: classes.dex */
public interface FingerPrintUnlockStateHolder {
    int getUnlockState();

    boolean hasColdStartData();

    void invalidateColdStartData();

    void setUnlockState(int i);
}
