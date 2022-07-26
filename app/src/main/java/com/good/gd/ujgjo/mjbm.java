package com.good.gd.ujgjo;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class mjbm implements Runnable {
    final /* synthetic */ boolean dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public mjbm(yfdke yfdkeVar, boolean z) {
        this.dbjc = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        BlackberryAnalyticsCommon.rynix().setBBRYAnalyticsStatusInNDK(this.dbjc);
    }
}
