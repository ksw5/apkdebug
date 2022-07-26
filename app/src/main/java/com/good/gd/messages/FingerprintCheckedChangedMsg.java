package com.good.gd.messages;

/* loaded from: classes.dex */
public class FingerprintCheckedChangedMsg {
    private boolean isSwitchChecked;
    private boolean isUiUpdate;

    public FingerprintCheckedChangedMsg(boolean z, boolean z2) {
        this.isSwitchChecked = z2;
        this.isUiUpdate = z;
    }

    public boolean isSwitchChecked() {
        return this.isSwitchChecked;
    }

    public boolean isUiUpdate() {
        return this.isUiUpdate;
    }
}
