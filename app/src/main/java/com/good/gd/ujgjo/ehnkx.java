package com.good.gd.ujgjo;

import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ehnkx implements com.good.gd.zwn.aqdzk {
    final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;
    final /* synthetic */ yfdke jwxax;
    final /* synthetic */ boolean qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ehnkx(yfdke yfdkeVar, com.good.gd.zwn.aqdzk aqdzkVar, boolean z) {
        this.jwxax = yfdkeVar;
        this.dbjc = aqdzkVar;
        this.qkduk = z;
    }

    @Override // com.good.gd.zwn.aqdzk
    public void dbjc(boolean z, int i, String str) {
        com.good.gd.kloes.hbfhc.jwxax(this.jwxax.ugfcv, String.format(Locale.getDefault(), "GET Config Response - Delivered: %b Reason: %d (%s)", Boolean.valueOf(z), Integer.valueOf(i), com.good.gd.zwn.mjbm.qkduk(i)));
        com.good.gd.kloes.ehnkx.qkduk(this.jwxax.ugfcv, "GET Config Response: " + str);
        com.good.gd.zwn.aqdzk aqdzkVar = this.dbjc;
        if (aqdzkVar != null) {
            aqdzkVar.dbjc(z, i, str);
        }
        if (z) {
            if (!this.qkduk) {
                return;
            }
            this.jwxax.wxau().qkduk(true);
        } else if (true == z) {
        } else {
            if (i != 1005 && i != 1003 && i != 1010 && i != 1013 && i != 1014) {
                return;
            }
            this.jwxax.mvf.liflu();
        }
    }
}
