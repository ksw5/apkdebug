package com.good.gd.net;

import com.good.gd.net.GDNetUtility;
import com.good.gd.net.GDNetUtilityImpl;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class yfdke implements Runnable {
    final /* synthetic */ int dbjc;
    final /* synthetic */ GDNetUtilityImpl.GDNslookupCallbackWrapper qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public yfdke(GDNetUtilityImpl.GDNslookupCallbackWrapper gDNslookupCallbackWrapper, int i) {
        this.qkduk = gDNslookupCallbackWrapper;
        this.dbjc = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        GDNetUtility.GDNslookupCallback gDNslookupCallback;
        GDNetUtility.GDNslookupCallback gDNslookupCallback2;
        gDNslookupCallback = this.qkduk.dbjc;
        if (gDNslookupCallback != null) {
            gDNslookupCallback2 = this.qkduk.dbjc;
            gDNslookupCallback2.onNslookupResponseFailure(GDNetUtility.GDNetUtilityErr.fromInt(this.dbjc));
        }
    }
}
