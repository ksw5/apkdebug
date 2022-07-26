package com.good.gd.yzras;

import android.content.Context;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;

/* loaded from: classes.dex */
public class hbfhc extends com.good.gd.ujgjo.hbfhc {
    public hbfhc(Context context) {
        super(context);
    }

    @Override // com.good.gd.ujgjo.hbfhc
    public synchronized boolean jwxax() {
        boolean z;
        if (!GDActivitySupport.isContainerCurrentlyLocked()) {
            if (!GDActivitySupport.isCurrentlyBlockedByPolicyOrLocally()) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    @Override // com.good.gd.ujgjo.hbfhc
    public synchronized boolean wxau() {
        boolean z;
        boolean jwxax = com.blackberry.bis.core.yfdke.sbesx().jwxax("AnalyticsEntitlementStatus");
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ssosk() && jwxax) {
            if (!this.qkduk) {
                z = true;
            }
        }
        z = false;
        return z;
    }

    @Override // com.good.gd.ujgjo.hbfhc
    public synchronized boolean ztwf() {
        boolean z;
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).dbjc().booleanValue()) {
            if (!this.qkduk) {
                z = true;
            }
        }
        z = false;
        return z;
    }
}
