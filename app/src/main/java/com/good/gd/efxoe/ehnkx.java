package com.good.gd.efxoe;

import com.good.gd.zwn.aqdzk;

/* loaded from: classes.dex */
class ehnkx implements Runnable {
    final /* synthetic */ com.good.gd.lqnsz.hbfhc dbjc;
    final /* synthetic */ mjbm jwxax;
    final /* synthetic */ aqdzk qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ehnkx(mjbm mjbmVar, com.good.gd.lqnsz.hbfhc hbfhcVar, aqdzk aqdzkVar) {
        this.jwxax = mjbmVar;
        this.dbjc = hbfhcVar;
        this.qkduk = aqdzkVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Class cls;
        cls = this.jwxax.dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "[SRA] Calling REACH after [3] seconds delay.");
        this.jwxax.dbjc(this.dbjc.wxau().dbjc().get("app:reAuthenticateToConfirm").dbjc(), this.qkduk);
    }
}
