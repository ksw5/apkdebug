package com.good.gd.datasaver;

/* loaded from: classes.dex */
public interface GDDataSaverControl {
    void addObserver(GDDataSaverObserver gDDataSaverObserver);

    void onGDForegroundEvent(boolean z);

    void removeObserver(GDDataSaverObserver gDDataSaverObserver);
}
