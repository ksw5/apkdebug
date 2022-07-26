package com.good.gd.net;

import com.good.gd.net.GDNetUtility;
import com.good.gd.net.GDNetUtilityImpl;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class hbfhc implements Runnable {
    final /* synthetic */ String dbjc;
    final /* synthetic */ GDNetUtilityImpl.GDNslookupCallbackWrapper qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc(GDNetUtilityImpl.GDNslookupCallbackWrapper gDNslookupCallbackWrapper, String str) {
        this.qkduk = gDNslookupCallbackWrapper;
        this.dbjc = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        GDNetUtility.GDNslookupCallback gDNslookupCallback;
        GDNetUtility.GDNslookupCallback gDNslookupCallback2;
        JSONObject nslookupResponseHelper;
        gDNslookupCallback = this.qkduk.dbjc;
        if (gDNslookupCallback != null) {
            gDNslookupCallback2 = this.qkduk.dbjc;
            nslookupResponseHelper = GDNetUtilityImpl.this.nslookupResponseHelper(this.dbjc);
            gDNslookupCallback2.onNslookupResponseSuccess(nslookupResponseHelper);
        }
    }
}
