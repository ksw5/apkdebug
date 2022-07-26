package com.good.gd.yokds;

import com.good.gd.profileoverride.GDBISProfileOverrideCallback;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
class fdyxd implements GDBISProfileOverrideCallback {
    final /* synthetic */ com.good.gd.yokds.hbfhc dbjc;
    final /* synthetic */ yfdke qkduk;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ int dbjc;
        final /* synthetic */ String qkduk;

        hbfhc(int i, String str) {
            this.dbjc = i;
            this.qkduk = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z = this.dbjc == 0;
            if (z) {
                com.good.gd.tpgyf.yfdke ztwf = com.good.gd.tpgyf.yfdke.ztwf();
                if (com.blackberry.analytics.analyticsengine.fdyxd.pqq().muee()) {
                    ztwf.dbjc();
                } else {
                    ztwf.qkduk();
                }
            }
            com.good.gd.kloes.hbfhc.jwxax(fdyxd.this.qkduk.dbjc, String.format("[Revert Profile] Request completed with result: %s for tracking ID: %s", yfdke.dbjc(fdyxd.this.qkduk, this.dbjc), this.qkduk));
            com.good.gd.yokds.hbfhc hbfhcVar = fdyxd.this.dbjc;
            if (hbfhcVar != null) {
                hbfhcVar.dbjc(z, this.dbjc, this.qkduk);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public fdyxd(yfdke yfdkeVar, com.good.gd.yokds.hbfhc hbfhcVar) {
        this.qkduk = yfdkeVar;
        this.dbjc = hbfhcVar;
    }

    @Override // com.good.gd.profileoverride.GDBISProfileOverrideCallback
    public void onProfileOverrideApplied(int i, String str) {
        ExecutorService executorService;
        this.qkduk.qkduk();
        executorService = this.qkduk.jwxax;
        executorService.execute(new hbfhc(i, str));
    }
}
