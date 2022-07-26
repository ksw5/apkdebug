package com.good.gd.backgroundexecution;

import com.good.gd.ndkproxy.net.GDSocketEventsListener;

/* loaded from: classes.dex */
public class GDBackgroundExecutionSocketEventListener implements GDSocketEventsListener {
    @Override // com.good.gd.ndkproxy.net.GDSocketEventsListener
    public void onDataSaverEnforce() {
        GDBackgroundExecutionHelper.disconnectPushConnection();
        GDBackgroundExecutionHelper.drainSocketPoolAsync();
    }

    @Override // com.good.gd.ndkproxy.net.GDSocketEventsListener
    public void onGDSocketClosed() {
        GDBackgroundExecutionManager.getInstance().notifyClosingGDSocketEvent();
    }

    @Override // com.good.gd.ndkproxy.net.GDSocketEventsListener
    public void onGDSocketCreated() {
        GDBackgroundExecutionManager.getInstance().notifyCreatingGDSocketEvent();
    }
}
