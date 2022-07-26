package com.good.gd.efxoe;

import com.good.gd.lqnsz.hbfhc;
import com.good.gd.zwn.aqdzk;

/* loaded from: classes.dex */
class fdyxd implements Runnable {
    final /* synthetic */ hbfhc.yfdke.C0017hbfhc dbjc;
    final /* synthetic */ mjbm jwxax;
    final /* synthetic */ aqdzk qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fdyxd(mjbm mjbmVar, hbfhc.yfdke.C0017hbfhc c0017hbfhc, aqdzk aqdzkVar) {
        this.jwxax = mjbmVar;
        this.dbjc = c0017hbfhc;
        this.qkduk = aqdzkVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Class cls;
        cls = this.jwxax.dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "[SRA] Calling REACH after [3] seconds delay.");
        this.jwxax.dbjc(this.dbjc.dbjc(), this.qkduk);
    }
}
