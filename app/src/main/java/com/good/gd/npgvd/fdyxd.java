package com.good.gd.npgvd;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.zwn.vzw;

/* loaded from: classes.dex */
public class fdyxd extends com.good.gd.zwn.fdyxd {
    private final hzzdh mvf;
    private final Class odlf = fdyxd.class;

    public fdyxd(com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.ghhwi.hbfhc hbfhcVar, hzzdh hzzdhVar, com.good.gd.oqpvt.yfdke yfdkeVar, com.good.gd.zwn.ooowe oooweVar) {
        super(aqdzkVar, hbfhcVar, yfdkeVar, oooweVar);
        this.mvf = hzzdhVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.zwn.ehnkx
    public void dbjc(boolean z, vzw.fdyxd fdyxdVar) {
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt() && !com.good.gd.zwn.mjbm.liflu()) {
            BlackberryAnalyticsCommon.rynix().dbjc(4);
            com.good.gd.kloes.hbfhc.wxau(this.odlf, " Obtaining JWT token for GET Config network request.");
            com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", "Analytics.Events", "", z, new yfdke(this, fdyxdVar));
            return;
        }
        BlackberryAnalyticsCommon.rynix().dbjc(3);
        com.good.gd.kloes.hbfhc.wxau(this.odlf, " Obtaining GNP token for GET Config network request.");
        hzzdh hzzdhVar = this.mvf;
        hzzdhVar.getClass();
        this.mvf.dbjc(new hbfhc(this, hzzdhVar, fdyxdVar));
    }

    @Override // com.good.gd.zwn.fdyxd, com.good.gd.zwn.ehnkx
    public void dbjc(vzw.fdyxd fdyxdVar, vzw.yfdke yfdkeVar) {
        this.tlske.put("X-BBRY-OrgId", ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).iulf());
        super.dbjc(fdyxdVar, yfdkeVar);
    }
}
