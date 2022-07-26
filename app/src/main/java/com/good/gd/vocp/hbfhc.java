package com.good.gd.vocp;

import com.blackberry.security.threat.bis.ndkproxy.BISThreatStatusListenerImpl;

/* loaded from: classes.dex */
public class hbfhc {
    private BISThreatStatusListenerImpl dbjc = new BISThreatStatusListenerImpl();

    public void dbjc(String str, String str2, String str3, String str4) {
        this.dbjc.onNewGeoZoneStatus(str, str2, str3, str4);
    }

    public void dbjc(String str, String str2, String str3, String str4, String str5) {
        this.dbjc.onNewIdentityStatus(str, str2, str3, str4, str5);
    }

    public void dbjc() {
        this.dbjc.resetThreatStatus();
    }
}
