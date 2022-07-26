package com.good.gd.machines.powermanagment;

import android.content.Context;
import android.os.Build;

/* loaded from: classes.dex */
public class BBDStandbyBucketsManager implements BBDStandbyBucketsControl {
    private BBDStandbyBucketsControl standbyBucketsManagerImpl;

    /* loaded from: classes.dex */
    private static class yfdke implements BBDStandbyBucketsControl {
        private yfdke() {
        }

        @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
        public int getStandbyBucketType() {
            return -1;
        }

        @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
        public void printStandbyBucketInfo() {
        }
    }

    public BBDStandbyBucketsManager(Context context) {
        if (Build.VERSION.SDK_INT < 28) {
            this.standbyBucketsManagerImpl = new yfdke();
        } else {
            this.standbyBucketsManagerImpl = new hbfhc(context);
        }
    }

    @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
    public int getStandbyBucketType() {
        return this.standbyBucketsManagerImpl.getStandbyBucketType();
    }

    @Override // com.good.gd.machines.powermanagment.BBDStandbyBucketsControl
    public void printStandbyBucketInfo() {
        this.standbyBucketsManagerImpl.printStandbyBucketInfo();
    }
}
