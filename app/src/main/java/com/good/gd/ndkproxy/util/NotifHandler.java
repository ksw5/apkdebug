package com.good.gd.ndkproxy.util;

import android.content.Context;
import com.blackberry.security.mtd.MTDScan$Status;
import com.blackberry.security.mtd.MTDScan$Type;
import com.blackberry.security.mtd.hbfhc;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class NotifHandler {
    void checkConfig(Context context) {
        hbfhc.dbjc().jwxax(GTBaseContext.getInstance().getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public native void init();

    void resume(Context context) {
        hbfhc.dbjc().dbjc(GTBaseContext.getInstance().getApplicationContext());
    }

    void sleep(Context context) {
        hbfhc.dbjc().qkduk(GTBaseContext.getInstance().getApplicationContext());
    }

    void updateStatusByValues(Context context, int i, int i2) {
        hbfhc.dbjc().dbjc(GTBaseContext.getInstance().getApplicationContext(), MTDScan$Type.dbjc(i), MTDScan$Status.dbjc(i2));
    }
}
