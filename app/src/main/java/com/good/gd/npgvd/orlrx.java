package com.good.gd.npgvd;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.zwn.vzw;

/* loaded from: classes.dex */
public class orlrx extends com.good.gd.zwn.orlrx {
    private final hzzdh odlf;
    private final Class yrp = orlrx.class;

    public orlrx(com.good.gd.zwn.aqdzk aqdzkVar, byte[] bArr, com.good.gd.ghhwi.hbfhc hbfhcVar, hzzdh hzzdhVar, com.good.gd.oqpvt.yfdke yfdkeVar, com.good.gd.zwn.ooowe oooweVar) {
        super(aqdzkVar, bArr, hbfhcVar, yfdkeVar, oooweVar);
        this.odlf = hzzdhVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.zwn.ehnkx
    public void dbjc(boolean z, vzw.fdyxd fdyxdVar) {
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt() && !com.good.gd.zwn.mjbm.liflu()) {
            BlackberryAnalyticsCommon.rynix().qkduk(4);
            com.good.gd.kloes.hbfhc.wxau(this.yrp, " Obtaining JWT token for Historical POST network request.");
            com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", "Analytics.Events", "", z, new aqdzk(this, fdyxdVar));
            return;
        }
        BlackberryAnalyticsCommon.rynix().qkduk(3);
        com.good.gd.kloes.hbfhc.wxau(this.yrp, " Obtaining GNP token for Historical POST network request.");
        hzzdh hzzdhVar = this.odlf;
        hzzdhVar.getClass();
        this.odlf.dbjc(new mjbm(this, hzzdhVar, fdyxdVar));
    }
}
