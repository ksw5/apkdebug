package com.good.gd.machines.powermanagment;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
class hbfhc implements BBDStandbyBucketsControl {
    private UsageStatsManager dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc(Context context) {
        this.dbjc = (UsageStatsManager) context.getSystemService("usagestats");
    }

    @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
    public int getStandbyBucketType() {
        UsageStatsManager usageStatsManager = this.dbjc;
        if (usageStatsManager == null) {
            return -1;
        }
        try {
            return usageStatsManager.getAppStandbyBucket();
        } catch (SecurityException e) {
            GDLog.DBGPRINTF(13, "BBDStandbyBuckets exception: " + e + "\n");
            return -1;
        }
    }

    @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
    public void printStandbyBucketInfo() {
        GDLog.DBGPRINTF(14, "BBDStandbyBuckets type: " + getStandbyBucketType() + "\n");
    }
}
