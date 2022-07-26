package com.good.gd.npgvd;

import com.good.gd.npgvd.hzzdh;
import com.good.gd.zwn.vzw;
import java.util.Map;

/* loaded from: classes.dex */
class mjbm extends hzzdh.hbfhc {
    final /* synthetic */ vzw.fdyxd dbjc;
    final /* synthetic */ orlrx qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public mjbm(orlrx orlrxVar, hzzdh hzzdhVar, vzw.fdyxd fdyxdVar) {
        super(hzzdhVar);
        this.qkduk = orlrxVar;
        this.dbjc = fdyxdVar;
        hzzdhVar.getClass();
    }

    @Override // com.good.gd.npgvd.hzzdh.hbfhc
    public void dbjc(String str) {
        Class cls;
        Map map;
        cls = this.qkduk.yrp;
        com.good.gd.kloes.hbfhc.jwxax(cls, "GNP Token received successfully for the Historical POST network request.");
        map = ((com.good.gd.zwn.orlrx) this.qkduk).tlske;
        com.good.gd.zwn.mjbm.qkduk(map, str);
        this.dbjc.dbjc(false);
    }

    @Override // com.good.gd.npgvd.hzzdh.hbfhc
    public void dbjc(int i) {
        Class cls;
        cls = this.qkduk.yrp;
        com.good.gd.kloes.hbfhc.dbjc(cls, "GNP Token not received for the Historical POST network request. " + i);
        this.qkduk.dbjc(i);
        if (1009 == i) {
            this.qkduk.dbjc("Error in generating Authorization Token");
        } else if (1015 == i) {
            this.qkduk.dbjc("Unable to generate GNP Token. Token timed out.");
        }
        this.dbjc.dbjc(true);
    }
}
