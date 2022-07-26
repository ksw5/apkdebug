package com.good.gd.ujgjo;

import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class pmoiy implements com.good.gd.zwn.aqdzk {
    final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;
    final /* synthetic */ yfdke qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public pmoiy(yfdke yfdkeVar, com.good.gd.zwn.aqdzk aqdzkVar) {
        this.qkduk = yfdkeVar;
        this.dbjc = aqdzkVar;
    }

    @Override // com.good.gd.zwn.aqdzk
    public void dbjc(boolean z, int i, String str) {
        com.good.gd.kloes.hbfhc.jwxax(this.qkduk.ugfcv, String.format(Locale.getDefault(), "Currently Scheduled POST Historical Request - Delivered: %b Reason: %d (%s)", Boolean.valueOf(z), Integer.valueOf(i), com.good.gd.zwn.mjbm.qkduk(i)));
        com.good.gd.kloes.ehnkx.qkduk(this.qkduk.ugfcv, "POST Historical request Response : " + str);
        com.good.gd.zwn.aqdzk aqdzkVar = this.dbjc;
        if (aqdzkVar != null) {
            aqdzkVar.dbjc(z, i, str);
        }
        if (true != z && i == 1007) {
            this.qkduk.wxau().qkduk(true);
        } else if (true != z && i == 1008) {
            this.qkduk.wxau().qkduk(true);
        } else if (true != z && i == 1004) {
        } else {
            if ((true == z || (i != 1005 && i != 1003 && i != 1002 && i != 1010)) && i != 1013 && i != 1014) {
                return;
            }
            this.qkduk.mvf.jcpqe();
        }
    }
}
