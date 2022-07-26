package com.good.gd.backgroundexecution;

/* loaded from: classes.dex */
public class GDBackgroundExecutionManager implements GDBackgroundExecutionControl {
    private static GDBackgroundExecutionManager _instance;
    private GDBackgroundExecutionControl mImpl;

    private GDBackgroundExecutionManager(GDBackgroundExecutionControl gDBackgroundExecutionControl) {
        this.mImpl = gDBackgroundExecutionControl;
    }

    public static GDBackgroundExecutionManager createInstance(GDBackgroundExecutionControl gDBackgroundExecutionControl) {
        if (_instance == null) {
            _instance = new GDBackgroundExecutionManager(gDBackgroundExecutionControl);
        }
        return _instance;
    }

    public static GDBackgroundExecutionManager getInstance() {
        return _instance;
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyClosingGDSocketEvent() {
        this.mImpl.notifyClosingGDSocketEvent();
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyCreatingGDSocketEvent() {
        this.mImpl.notifyCreatingGDSocketEvent();
    }

    @Override // com.good.gd.backgroundexecution.GDBackgroundExecutionControl
    public void notifyGDForegroundEvent(boolean z) {
        this.mImpl.notifyGDForegroundEvent(z);
    }
}
