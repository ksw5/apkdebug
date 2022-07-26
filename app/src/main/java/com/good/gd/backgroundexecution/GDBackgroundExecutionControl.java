package com.good.gd.backgroundexecution;

/* loaded from: classes.dex */
public interface GDBackgroundExecutionControl {
    void notifyClosingGDSocketEvent();

    void notifyCreatingGDSocketEvent();

    void notifyGDForegroundEvent(boolean z);
}
