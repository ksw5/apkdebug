package com.good.gd.machines.doze;

/* loaded from: classes.dex */
public interface GDDozeLightMonitorControl {

    /* loaded from: classes.dex */
    public enum Mode {
        LIGHT_ENABLED,
        FULL_ENABLED,
        DISABLED
    }

    void onDozeLightModeDetected();

    void onDozeModeChangedEvent(Mode mode);

    void onGDForegroundEvent(boolean z);
}
