package com.good.gd.gahh;

import com.blackberry.analytics.analyticsengine.fdyxd;
import com.good.gd.GDAndroidAPI;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.whhmi.pmoiy;
import com.good.gd.whhmi.yfdke;

/* loaded from: classes.dex */
public final class hbfhc {
    private static hbfhc ztwf;
    private String jwxax;
    private String qkduk;
    private boolean dbjc = !yfdke.qkduk(com.blackberry.bis.core.yfdke.wpejt().dbjc("geoFenceBlockIDKey"));
    private GDAndroidAPI wxau = com.blackberry.analytics.analyticsengine.yfdke.wxau();

    private hbfhc() {
        this.qkduk = null;
        this.jwxax = null;
        this.qkduk = "Application Blocked";
        this.jwxax = "This app has been blocked by BlackBerry Persona because one or more risk factors have been detected. For more information, contact your administrator.";
    }

    public static hbfhc jwxax() {
        if (ztwf == null) {
            synchronized (hbfhc.class) {
                ztwf = new hbfhc();
            }
        }
        return ztwf;
    }

    public void dbjc(String str, String str2) {
        if (jwxax().dbjc) {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "Geo block already exists. Unable to process the request.");
        } else if (true != ((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).muee()) {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "BIS Entitlement is disabled, cannot enforce Geo block.");
        } else {
            if (yfdke.qkduk(str)) {
                str = this.qkduk;
            }
            if (yfdke.qkduk(str2)) {
                str2 = this.jwxax;
            }
            if (str.equalsIgnoreCase(this.qkduk) && str2.equalsIgnoreCase(this.jwxax)) {
                str = GDLocalizer.getLocalizedString("BISGeofenceBlockDefaultTitle");
                str2 = GDLocalizer.getLocalizedString("BISGeofenceBlockDefaultMessage");
            }
            com.blackberry.bis.core.yfdke.wpejt().dbjc("geoFenceBlockIDKey", "Geo_Block");
            this.dbjc = true;
            this.wxau.executeLocalBlock("Geo_Block", str, str2);
            com.good.gd.kloes.hbfhc.jwxax("hbfhc", "Geo block has been applied.");
        }
    }

    public boolean qkduk() {
        return this.dbjc;
    }

    public void dbjc() {
        pmoiy wpejt = com.blackberry.bis.core.yfdke.wpejt();
        String dbjc = wpejt.dbjc("geoFenceBlockIDKey");
        if (yfdke.qkduk(dbjc)) {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "Block Id does not exists to perform Geo unblock.");
            return;
        }
        this.wxau.executeLocalUnblock(dbjc);
        wpejt.dbjc("geoFenceBlockIDKey", (String) null);
        this.dbjc = false;
        com.good.gd.kloes.hbfhc.jwxax("hbfhc", "Geo block has been removed.");
    }
}
