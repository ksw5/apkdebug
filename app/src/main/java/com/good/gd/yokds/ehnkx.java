package com.good.gd.yokds;

import com.good.gd.profileoverride.GDBISProfileOverrideCallback;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
class ehnkx implements GDBISProfileOverrideCallback {
    final /* synthetic */ String dbjc;
    final /* synthetic */ yfdke jwxax;
    final /* synthetic */ com.good.gd.yokds.hbfhc qkduk;

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
                com.good.gd.tpgyf.yfdke.ztwf().dbjc(ehnkx.this.dbjc);
            }
            com.good.gd.kloes.hbfhc.jwxax(ehnkx.this.jwxax.dbjc, String.format("[Assign Profile] Request completed with result: %s for tracking ID: %s", yfdke.dbjc(ehnkx.this.jwxax, this.dbjc), this.qkduk));
            com.good.gd.yokds.hbfhc hbfhcVar = ehnkx.this.qkduk;
            if (hbfhcVar != null) {
                hbfhcVar.dbjc(z, this.dbjc, this.qkduk);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ehnkx(yfdke yfdkeVar, String str, com.good.gd.yokds.hbfhc hbfhcVar) {
        this.jwxax = yfdkeVar;
        this.dbjc = str;
        this.qkduk = hbfhcVar;
    }

    @Override // com.good.gd.profileoverride.GDBISProfileOverrideCallback
    public void onProfileOverrideApplied(int i, String str) {
        ExecutorService executorService;
        this.jwxax.qkduk();
        executorService = this.jwxax.jwxax;
        executorService.execute(new hbfhc(i, str));
    }
}
