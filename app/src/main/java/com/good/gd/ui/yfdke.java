package com.good.gd.ui;

import android.os.Bundle;
import com.good.gd.ui.GDDisclaimerView;

/* loaded from: classes.dex */
class yfdke implements Runnable {
    final /* synthetic */ Bundle dbjc;
    final /* synthetic */ GDDisclaimerView.fdyxd qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(GDDisclaimerView.fdyxd fdyxdVar, Bundle bundle) {
        this.qkduk = fdyxdVar;
        this.dbjc = bundle;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.qkduk.dbjc.smoothScrollTo(0, (int) (this.dbjc.getDouble("scroll_position_extra_key", 0.0d) * this.qkduk.dbjc.getHeight()));
    }
}
