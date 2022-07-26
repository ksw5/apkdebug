package com.good.gd.efxoe;

/* loaded from: classes.dex */
class yfdke implements Runnable {
    final /* synthetic */ boolean dbjc;
    final /* synthetic */ String jwxax;
    final /* synthetic */ String qkduk;
    final /* synthetic */ String wxau;

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(mjbm mjbmVar, boolean z, String str, String str2, String str3) {
        this.dbjc = z;
        this.qkduk = str;
        this.jwxax = str2;
        this.wxau = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.good.gd.kofoa.hbfhc.wxau().dbjc(this.dbjc, this.jwxax, this.wxau);
    }
}
