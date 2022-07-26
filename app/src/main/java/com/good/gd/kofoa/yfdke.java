package com.good.gd.kofoa;

import com.good.gd.zwn.aqdzk;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class yfdke implements aqdzk {
    final /* synthetic */ int dbjc;
    final /* synthetic */ hbfhc qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(hbfhc hbfhcVar, int i) {
        this.qkduk = hbfhcVar;
        this.dbjc = i;
    }

    @Override // com.good.gd.zwn.aqdzk
    public void dbjc(boolean z, int i, String str) {
        aqdzk aqdzkVar;
        aqdzk aqdzkVar2;
        aqdzkVar = this.qkduk.wxau;
        if (aqdzkVar != null) {
            aqdzkVar2 = this.qkduk.wxau;
            aqdzkVar2.dbjc(z, this.dbjc, str);
        }
        this.qkduk.ztwf();
    }
}
