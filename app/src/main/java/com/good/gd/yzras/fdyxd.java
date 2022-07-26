package com.good.gd.yzras;

import android.content.Context;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.aqdzk;
import com.blackberry.bis.core.pmoiy;
import com.good.gd.ovnkx.mjbm;
import com.good.gd.ujgjo.yfdke;
import java.util.List;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class fdyxd extends yfdke {
    private final Class sbesx = fdyxd.class;
    private final com.good.gd.ghhwi.yfdke kwm = new C0038fdyxd();

    /* loaded from: classes.dex */
    protected class ehnkx extends yfdke.ooowe {
        ehnkx(Context context, pmoiy pmoiyVar, com.good.gd.idl.hbfhc hbfhcVar) {
            super(context, pmoiyVar, hbfhcVar);
        }

        @Override // com.good.gd.ujgjo.yfdke.ooowe, com.blackberry.bis.core.pmoiy.hbfhc
        public void dbjc() {
            super.dbjc();
        }

        @Override // com.good.gd.ujgjo.yfdke.ooowe, com.blackberry.bis.core.pmoiy.hbfhc
        public void jwxax() {
            super.jwxax();
            if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue()) {
                fdyxd.dbjc(fdyxd.this);
            } else {
                ((com.good.gd.bsvvm.hbfhc) com.blackberry.bis.core.yfdke.wpejt()).qkduk();
            }
        }

        @Override // com.good.gd.ujgjo.yfdke.ooowe, com.blackberry.bis.core.pmoiy.hbfhc
        public void qkduk() {
            super.qkduk();
            if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).muee()) {
                com.good.gd.idl.hbfhc.pqq().dbjc();
                com.good.gd.tpgyf.yfdke ztwf = com.good.gd.tpgyf.yfdke.ztwf();
                List<String> jwxax = ztwf.jwxax();
                if (jwxax != null && true != jwxax.isEmpty()) {
                    com.good.gd.kloes.hbfhc.wxau(fdyxd.this.sbesx, "[Revert Profile] Calling revert profile API, on BIS entitlement disabled.");
                    com.good.gd.yokds.yfdke.dbjc().dbjc(jwxax, (com.good.gd.yokds.hbfhc) null);
                    return;
                }
                ztwf.qkduk();
            }
        }

        @Override // com.good.gd.ujgjo.yfdke.ooowe, com.blackberry.bis.core.pmoiy.hbfhc
        public void wxau() {
            fdyxd.dbjc(fdyxd.this);
            if (fdyxd.this != null) {
                com.good.gd.whhmi.pmoiy wpejt = com.blackberry.bis.core.yfdke.wpejt();
                com.good.gd.whhmi.hbfhc wxau = com.good.gd.whhmi.hbfhc.wxau();
                if (wxau.qkduk("geoFenceBlockIDKey")) {
                    String dbjc = wxau.dbjc("geoFenceBlockIDKey");
                    if (true != com.good.gd.whhmi.yfdke.qkduk(dbjc)) {
                        wpejt.dbjc("geoFenceBlockIDKey", dbjc);
                    }
                    wxau.jwxax("geoFenceBlockIDKey");
                }
                if (wxau.qkduk("appBehavioralBlockIDKey")) {
                    String dbjc2 = wxau.dbjc("appBehavioralBlockIDKey");
                    if (true != com.good.gd.whhmi.yfdke.qkduk(dbjc2)) {
                        wpejt.dbjc("appBehavioralBlockIDKey", dbjc2);
                    }
                    wxau.jwxax("appBehavioralBlockIDKey");
                }
                super.wxau();
                if (mjbm.liflu()) {
                    return;
                }
                com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
                fdyxdVar.gbb();
                fdyxdVar.liflu();
                return;
            }
            throw null;
        }
    }

    /* renamed from: com.good.gd.yzras.fdyxd$fdyxd  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    protected class C0038fdyxd extends yfdke.opjy {
        protected C0038fdyxd() {
            super();
        }

        @Override // com.good.gd.ujgjo.yfdke.opjy, com.good.gd.ghhwi.yfdke
        public void dbjc(boolean z) {
            if (BlackberryAnalyticsCommon.rynix().ztwf() != 4) {
                BlackberryAnalyticsCommon.rynix().dbjc(3);
                com.good.gd.kloes.hbfhc.wxau(fdyxd.this.sbesx, "Re-Setting GET receiver spec version.");
            }
            super.dbjc(z);
        }

        @Override // com.good.gd.ujgjo.yfdke.opjy, com.good.gd.ghhwi.yfdke
        public void qkduk(boolean z) {
            if (BlackberryAnalyticsCommon.rynix().lqox() != 4) {
                BlackberryAnalyticsCommon.rynix().qkduk(3);
                com.good.gd.kloes.hbfhc.wxau(fdyxd.this.sbesx, "Re-Setting POST receiver spec version.");
            }
            super.qkduk(z);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {

        /* renamed from: com.good.gd.yzras.fdyxd$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0039hbfhc implements mjbm.aqdzk {
            C0039hbfhc(hbfhc hbfhcVar) {
            }

            @Override // com.good.gd.ovnkx.mjbm.aqdzk
            public void dbjc(boolean z) {
                com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
                fdyxdVar.gbb();
                fdyxdVar.liflu();
            }
        }

        hbfhc(fdyxd fdyxdVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            mjbm.dbjc(new C0039hbfhc(this));
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements com.good.gd.oqpvt.hbfhc {
        yfdke() {
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void dbjc() {
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void dbjc(boolean z) {
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void jwxax() {
            com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
            if (fdyxdVar.vfle().booleanValue() && fdyxdVar.dbjc().booleanValue()) {
                fdyxd.this.tlske();
            }
            com.good.gd.rutan.hbfhc.tlske().liflu();
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void qkduk() {
        }
    }

    public fdyxd(Context context) {
        super(context);
        pmoiy dbjc = com.blackberry.bis.core.hbfhc.dbjc();
        ((com.blackberry.analytics.analyticsengine.fdyxd) dbjc).dbjc(new ehnkx(context, dbjc, com.good.gd.idl.hbfhc.pqq()));
    }

    @Override // com.good.gd.ujgjo.yfdke
    protected com.good.gd.oqpvt.hbfhc dbjc() {
        return new yfdke();
    }

    @Override // com.good.gd.ujgjo.yfdke
    protected boolean jcpqe() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ujgjo.yfdke
    public com.good.gd.ghhwi.yfdke wxau() {
        return this.kwm;
    }

    @Override // com.good.gd.ujgjo.yfdke
    protected Runnable ztwf() {
        return new hbfhc(this);
    }

    static /* synthetic */ void dbjc(fdyxd fdyxdVar) {
        com.good.gd.kloes.hbfhc.wxau(fdyxdVar.sbesx, "Storing tracked headers into GD Shared Preferences.");
        fdyxdVar.qkduk("O.S. Version", "currentOsVersion", aqdzk.jwxax());
        fdyxdVar.qkduk("Time Zone", "timezone", TimeZone.getDefault().getID());
        fdyxdVar.qkduk("App Version", "appVersion", aqdzk.dbjc(BlackberryAnalyticsCommon.rynix().jwxax()));
        fdyxdVar.qkduk("BBD SDK Version", "bbdSDKVersion", ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).jsgtu());
    }

    private void qkduk(String str, String str2, String str3) {
        com.good.gd.whhmi.pmoiy wpejt = com.blackberry.bis.core.yfdke.wpejt();
        com.good.gd.whhmi.hbfhc wxau = com.good.gd.whhmi.hbfhc.wxau();
        if (wxau.qkduk(str2)) {
            String dbjc = wxau.dbjc(str2);
            if (dbjc == null) {
                wpejt.dbjc(str2, str3);
                com.good.gd.kloes.hbfhc.wxau(this.sbesx, "Storing current value of " + str + " into BIS GD preferences.");
            } else {
                wpejt.dbjc(str2, dbjc);
                com.good.gd.kloes.hbfhc.wxau(this.sbesx, "Storing the value of " + str + " for existing/ fresh provisioned users.");
            }
            wxau.jwxax(str2);
        } else if (wpejt.dbjc(str2) != null) {
        } else {
            wpejt.dbjc(str2, str3);
            com.good.gd.kloes.hbfhc.wxau(this.sbesx, "Storing " + str + " for existing/ fresh provisioned users.");
        }
    }

    @Override // com.good.gd.ujgjo.yfdke
    protected boolean dbjc(com.good.gd.whhmi.pmoiy pmoiyVar) {
        boolean z;
        String dbjc = pmoiyVar.dbjc("timezone");
        String id = TimeZone.getDefault().getID();
        String dbjc2 = pmoiyVar.dbjc("currentOsVersion");
        String jwxax = aqdzk.jwxax();
        Context jwxax2 = BlackberryAnalyticsCommon.rynix().jwxax();
        String dbjc3 = pmoiyVar.dbjc("appVersion");
        String dbjc4 = aqdzk.dbjc(jwxax2);
        String dbjc5 = pmoiyVar.dbjc("bbdSDKVersion");
        String jsgtu = ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).jsgtu();
        boolean z2 = true;
        if (dbjc == null || true == dbjc.equals(id)) {
            z = false;
        } else {
            pmoiyVar.dbjc("timezone", id);
            z = true;
        }
        if (dbjc2 != null && true != dbjc2.equals(jwxax)) {
            pmoiyVar.dbjc("currentOsVersion", jwxax);
            z = true;
        }
        if (dbjc3 != null && true != dbjc3.equals(dbjc4)) {
            pmoiyVar.dbjc("appVersion", dbjc4);
            z = true;
        }
        if (dbjc5 != null && true != dbjc5.equals(jsgtu)) {
            pmoiyVar.dbjc("bbdSDKVersion", jsgtu);
        } else {
            z2 = z;
        }
        com.good.gd.kloes.hbfhc.wxau(this.sbesx, "Tracked Header Change: " + z2);
        return z2;
    }
}
