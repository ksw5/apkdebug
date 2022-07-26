package com.good.gd.ndkproxy.ui.event;

/* loaded from: classes.dex */
public class BBDBiometricsUpdateEvent extends BBDUIUpdateEvent {
    private boolean enableSwitch;
    private boolean notifySwitchUpdate;
    private boolean setSwitchChecked;

    public BBDBiometricsUpdateEvent(boolean z, boolean z2, boolean z3) {
        super(UIEventType.UI_UPDATE_BIOMETRY_STATE);
        this.setSwitchChecked = z;
        this.enableSwitch = z2;
        this.notifySwitchUpdate = z3;
    }

    public boolean isSwitchChecked() {
        return this.setSwitchChecked;
    }

    public boolean isSwitchEnabled() {
        return this.enableSwitch;
    }

    public boolean notifySwitchUpdate() {
        return this.notifySwitchUpdate;
    }
}
