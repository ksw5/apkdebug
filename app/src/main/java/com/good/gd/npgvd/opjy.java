package com.good.gd.npgvd;

/* loaded from: classes.dex */
public class opjy extends com.good.gd.zwn.ooowe {
    private final Class vfle = opjy.class;
    private hzzdh yrp = com.blackberry.analytics.analyticsengine.fdyxd.pqq().rynix();

    public opjy(com.good.gd.ovnkx.yfdke yfdkeVar, com.good.gd.oqpvt.yfdke yfdkeVar2, com.good.gd.oqpvt.hbfhc hbfhcVar) {
        super(yfdkeVar, yfdkeVar2, hbfhcVar);
    }

    @Override // com.good.gd.zwn.ooowe
    protected void dbjc(com.good.gd.zwn.ehnkx ehnkxVar) {
        if (!((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt()) {
            this.yrp.dbjc(ehnkxVar.lqox());
        }
    }

    @Override // com.good.gd.zwn.ooowe
    protected void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.vfle, "All GET CONFIG/POST HISTORICAL requests completed, try to release GNP Token, if applicable");
        this.yrp.dbjc(false);
    }
}
