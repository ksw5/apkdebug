package com.good.gd.npgvd;

import com.good.gd.zwn.vzw;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
class aqdzk implements com.good.gd.bmuat.hbfhc {
    final /* synthetic */ vzw.fdyxd dbjc;
    final /* synthetic */ orlrx qkduk;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ String dbjc;

        hbfhc(String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            Map map;
            map = ((com.good.gd.zwn.orlrx) aqdzk.this.qkduk).tlske;
            com.good.gd.zwn.mjbm.qkduk(map, this.dbjc);
            aqdzk.this.dbjc.dbjc(false);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        final /* synthetic */ int dbjc;

        yfdke(int i) {
            this.dbjc = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            aqdzk.this.qkduk.dbjc(this.dbjc);
            int i = this.dbjc;
            if (1009 == i) {
                aqdzk.this.qkduk.dbjc("Error in generating Authorization Token");
            } else if (1015 == i) {
                aqdzk.this.qkduk.dbjc("Unable to generate GNP Token. Token timed out.");
            }
            aqdzk.this.dbjc.dbjc(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public aqdzk(orlrx orlrxVar, vzw.fdyxd fdyxdVar) {
        this.qkduk = orlrxVar;
        this.dbjc = fdyxdVar;
    }

    @Override // com.good.gd.bmuat.hbfhc
    public void dbjc(String str) {
        Class cls;
        ExecutorService executorService;
        cls = this.qkduk.yrp;
        com.good.gd.kloes.hbfhc.jwxax(cls, "JWT Token received successfully for the Historical POST network request.");
        executorService = ((com.good.gd.zwn.ehnkx) this.qkduk).wxau;
        executorService.execute(new hbfhc(str));
    }

    @Override // com.good.gd.bmuat.hbfhc
    public void dbjc(String str, int i) {
        Class cls;
        ExecutorService executorService;
        cls = this.qkduk.yrp;
        com.good.gd.kloes.hbfhc.dbjc(cls, "JWT Token not received for the Historical POST network request. " + i);
        executorService = ((com.good.gd.zwn.ehnkx) this.qkduk).wxau;
        executorService.execute(new yfdke(i));
    }
}
