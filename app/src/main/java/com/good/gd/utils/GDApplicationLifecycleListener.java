package com.good.gd.utils;

import com.good.gd.ndkproxy.ui.GDLibraryUI;
import com.good.gd.ndkproxy.ui.data.ApplicationLifecycleListener;

/* loaded from: classes.dex */
public class GDApplicationLifecycleListener implements ApplicationLifecycleListener {
    public void applicationEnteringBackground() {
        GDLibraryUI.getInstance().applicationEnteringBackground();
    }

    @Override // com.good.gd.ndkproxy.ui.data.ApplicationLifecycleListener
    public void applicationEnteringForeground(boolean z) {
        GDLibraryUI.getInstance().applicationEnteringForeground(z);
    }

    public void applicationRestart() {
        GDLibraryUI.getInstance().applicationRestart();
    }
}
