package com.good.gd.datasaver;

/* loaded from: classes.dex */
public final class GDDataSaverManager implements GDDataSaverControl {
    private GDDataSaverControl dscImpl;

    public GDDataSaverManager(GDDataSaverControl gDDataSaverControl) {
        this.dscImpl = gDDataSaverControl;
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void addObserver(GDDataSaverObserver gDDataSaverObserver) {
        this.dscImpl.addObserver(gDDataSaverObserver);
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void onGDForegroundEvent(boolean z) {
        this.dscImpl.onGDForegroundEvent(z);
    }

    @Override // com.good.gd.datasaver.GDDataSaverControl
    public void removeObserver(GDDataSaverObserver gDDataSaverObserver) {
        this.dscImpl.removeObserver(gDDataSaverObserver);
    }
}
