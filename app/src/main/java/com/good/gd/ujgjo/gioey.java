package com.good.gd.ujgjo;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class gioey implements com.good.gd.zwn.aqdzk {
    final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;
    final /* synthetic */ yfdke jwxax;
    final /* synthetic */ com.good.gd.zwn.yfdke qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public gioey(yfdke yfdkeVar, com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.zwn.yfdke yfdkeVar2) {
        this.jwxax = yfdkeVar;
        this.dbjc = aqdzkVar;
        this.qkduk = yfdkeVar2;
    }

    @Override // com.good.gd.zwn.aqdzk
    public void dbjc(boolean z, int i, String str) {
        if (z) {
            yfdke.dbjc(this.jwxax, str, this.dbjc, this, this.qkduk, false);
            return;
        }
        com.good.gd.zwn.aqdzk aqdzkVar = this.dbjc;
        if (aqdzkVar == null) {
            return;
        }
        aqdzkVar.dbjc(false, i, str);
    }
}
