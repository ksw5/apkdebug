package com.good.gd.ui;

import com.good.gd.ui.GDDisclaimerView;

/* loaded from: classes.dex */
class hbfhc implements Runnable {
    final /* synthetic */ GDDisclaimerView.fdyxd dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc(GDDisclaimerView.fdyxd fdyxdVar) {
        this.dbjc = fdyxdVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.dbjc.dbjc.fullScroll(130);
    }
}
