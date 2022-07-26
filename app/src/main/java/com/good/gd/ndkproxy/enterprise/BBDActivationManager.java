package com.good.gd.ndkproxy.enterprise;

import com.good.gd.machines.activation.ActivationStateUpdate;

/* loaded from: classes.dex */
public class BBDActivationManager {
    private ActivationStateUpdate mStateUpdate;

    public BBDActivationManager(ActivationStateUpdate activationStateUpdate) {
        this.mStateUpdate = activationStateUpdate;
        ndkInit();
    }

    private native void ndkInit();

    public void onActivationError(int i, int i2) {
        ActivationStateUpdate activationStateUpdate = this.mStateUpdate;
        if (activationStateUpdate != null) {
            activationStateUpdate.onActivationFailed(i, i2);
        }
    }

    public native boolean setActivationInfo(String str, String str2, String str3, String str4, boolean z);

    public native void setShowStartupUI(boolean z);

    public native boolean shouldShowStartupUI();

    public void stateUpdate(int i) {
        ActivationStateUpdate activationStateUpdate = this.mStateUpdate;
        if (activationStateUpdate != null) {
            activationStateUpdate.activationStateUpdate(i);
        }
    }
}
