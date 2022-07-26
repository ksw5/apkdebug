package com.good.gd.bsvvm;

import com.good.gd.background.detection.AppForegroundChangedListener;
import com.good.gd.background.detection.BBDAppBackgroundDetector;

/* loaded from: classes.dex */
public class aqdzk extends com.blackberry.bis.core.mjbm implements AppForegroundChangedListener {
    private boolean jwxax = false;
    private final Class wxau = aqdzk.class;

    public aqdzk() {
        BBDAppBackgroundDetector.getInstance().addForegroundChangeListener(this);
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onBackgroundEntering() {
        com.good.gd.kloes.hbfhc.jwxax(this.wxau, "LifeCycleEventsGD.onBackgroundEntering");
        qkduk();
    }

    @Override // com.good.gd.background.detection.AppForegroundChangedListener
    public void onForegroundEntering() {
        com.good.gd.kloes.hbfhc.jwxax(this.wxau, "LifeCycleEventsGD.onForegroundEntering");
        if (!this.jwxax) {
            dbjc();
            this.jwxax = true;
        }
        jwxax();
    }
}
