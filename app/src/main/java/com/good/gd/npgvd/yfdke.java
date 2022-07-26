package com.good.gd.npgvd;

import com.good.gd.zwn.vzw;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
class yfdke implements com.good.gd.bmuat.hbfhc {
    final /* synthetic */ vzw.fdyxd dbjc;
    final /* synthetic */ fdyxd qkduk;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ String dbjc;

        hbfhc(String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            Map map;
            map = ((com.good.gd.zwn.fdyxd) yfdke.this.qkduk).tlske;
            com.good.gd.zwn.mjbm.dbjc(map, this.dbjc);
            yfdke.this.dbjc.dbjc(false);
        }
    }

    /* renamed from: com.good.gd.npgvd.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0019yfdke implements Runnable {
        final /* synthetic */ int dbjc;

        RunnableC0019yfdke(int i) {
            this.dbjc = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            yfdke.this.qkduk.dbjc(this.dbjc);
            int i = this.dbjc;
            if (1009 == i) {
                yfdke.this.qkduk.dbjc("Error in generating Authorization Token");
            } else if (1015 == i) {
                yfdke.this.qkduk.dbjc("Unable to generate GNP Token. Token timed out.");
            }
            yfdke.this.dbjc.dbjc(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(fdyxd fdyxdVar, vzw.fdyxd fdyxdVar2) {
        this.qkduk = fdyxdVar;
        this.dbjc = fdyxdVar2;
    }

    @Override // com.good.gd.bmuat.hbfhc
    public void dbjc(String str) {
        Class cls;
        ExecutorService executorService;
        cls = this.qkduk.odlf;
        com.good.gd.kloes.hbfhc.jwxax(cls, "JWT Token received successfully for the GET Config network request.");
        executorService = ((com.good.gd.zwn.ehnkx) this.qkduk).wxau;
        executorService.execute(new hbfhc(str));
    }

    @Override // com.good.gd.bmuat.hbfhc
    public void dbjc(String str, int i) {
        Class cls;
        ExecutorService executorService;
        cls = this.qkduk.odlf;
        com.good.gd.kloes.hbfhc.dbjc(cls, "JWT Token not received for the GET Config network request. " + i);
        executorService = ((com.good.gd.zwn.ehnkx) this.qkduk).wxau;
        executorService.execute(new RunnableC0019yfdke(i));
    }
}
