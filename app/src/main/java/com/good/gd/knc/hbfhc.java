package com.good.gd.knc;

import android.content.Context;
import android.telephony.TelephonyManager;

/* loaded from: classes.dex */
public class hbfhc {
    static final /* synthetic */ boolean qkduk = true;
    private TelephonyManager dbjc;

    public hbfhc(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.dbjc = telephonyManager;
        if (qkduk || telephonyManager != null) {
            return;
        }
        throw new AssertionError();
    }

    public String dbjc() {
        TelephonyManager telephonyManager = this.dbjc;
        return (telephonyManager == null || telephonyManager.getNetworkOperator() == null) ? "" : this.dbjc.getNetworkOperator();
    }
}
