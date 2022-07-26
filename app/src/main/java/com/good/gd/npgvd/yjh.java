package com.good.gd.npgvd;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.zwn.vzw;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes.dex */
public class yjh extends vzw {
    public yjh(ScheduledExecutorService scheduledExecutorService, com.good.gd.zwn.ehnkx ehnkxVar, yfdke yfdkeVar) {
        super(scheduledExecutorService, ehnkxVar, yfdkeVar);
    }

    @Override // com.good.gd.zwn.vzw
    protected void jwxax() {
        com.good.gd.zwn.ehnkx ehnkxVar = this.liflu;
        if (ehnkxVar instanceof com.good.gd.zwn.fdyxd) {
            if (4 == BlackberryAnalyticsCommon.rynix().ztwf()) {
                BlackberryAnalyticsCommon.rynix().dbjc(3);
                com.good.gd.zwn.mjbm.dbjc(true);
                return;
            }
            this.wxau = this.jwxax;
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "Stopping remaining get config network request retries.");
        } else if (!(ehnkxVar instanceof com.good.gd.zwn.orlrx)) {
        } else {
            int lqox = BlackberryAnalyticsCommon.rynix().lqox();
            if (4 == lqox) {
                BlackberryAnalyticsCommon.rynix().qkduk(3);
                com.good.gd.zwn.mjbm.dbjc(true);
            } else if (3 == lqox || 1 == lqox) {
                this.wxau = this.jwxax;
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "Stopping remaining historical post network retries.");
            } else if (2 != lqox) {
            } else {
                BlackberryAnalyticsCommon.rynix().qkduk(1);
                this.wxau = 0;
                this.liflu.wuird();
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "Current API End point not found, falling back to lower API version so restarting retries.");
            }
        }
    }

    @Override // com.good.gd.zwn.vzw
    protected void qkduk() {
        int i;
        com.good.gd.zwn.ehnkx ehnkxVar = this.liflu;
        if (ehnkxVar instanceof com.good.gd.zwn.fdyxd) {
            i = BlackberryAnalyticsCommon.rynix().ztwf();
        } else if (!(ehnkxVar instanceof com.good.gd.zwn.orlrx)) {
            i = 0;
        } else {
            i = BlackberryAnalyticsCommon.rynix().lqox();
        }
        if (i == 4) {
            com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", "Analytics.Events", "");
        } else {
            com.blackberry.analytics.analyticsengine.fdyxd.pqq().rynix().dbjc(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.zwn.vzw
    public void wxau() {
        super.wxau();
        com.blackberry.analytics.analyticsengine.fdyxd.pqq().rynix().jwxax(this.liflu.lqox());
    }
}
