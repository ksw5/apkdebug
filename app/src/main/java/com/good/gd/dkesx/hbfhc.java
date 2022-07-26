package com.good.gd.dkesx;

import android.content.Intent;
import android.os.Bundle;
import com.blackberry.analytics.analyticsengine.SIS.BISBroadcasts.BISAction;
import com.blackberry.analytics.analyticsengine.SIS.BISThreatManagement.Public.BISThreatLevelInfo;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.lqnsz.hbfhc;
import com.good.gd.whhmi.yfdke;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc extends com.good.gd.vbqbi.hbfhc {
    private AtomicReference<String> jwxax = new AtomicReference<>();
    private AtomicReference<String> qkduk = new AtomicReference<>();
    private AtomicReference<JSONObject> wxau = new AtomicReference<>();
    private AtomicReference<JSONObject> ztwf = new AtomicReference<>();

    @Override // com.good.gd.vbqbi.hbfhc
    public void dbjc(hbfhc.C0015hbfhc c0015hbfhc) {
        String str;
        String str2;
        String dbjc;
        if (c0015hbfhc != null) {
            c0015hbfhc.ztwf();
            hbfhc.C0015hbfhc.yfdke jwxax = c0015hbfhc.jwxax();
            String str3 = null;
            if (jwxax == null) {
                str = null;
            } else {
                str = jwxax.dbjc();
            }
            hbfhc.C0015hbfhc.C0016hbfhc dbjc2 = c0015hbfhc.dbjc();
            if (dbjc2 == null) {
                str2 = null;
            } else {
                str2 = dbjc2.dbjc();
            }
            ArrayList<String> arrayList = new ArrayList<>();
            if (!yfdke.qkduk(str)) {
                arrayList.add(str);
            }
            if (!yfdke.qkduk(str2)) {
                arrayList.add(str2);
            }
            String dbjc3 = dbjc(arrayList);
            if (this.qkduk == null) {
                this.qkduk = new AtomicReference<>();
            }
            this.qkduk.set(dbjc3);
            if (this.jwxax == null) {
                this.jwxax = new AtomicReference<>();
            }
            if (!com.good.gd.vnzf.yfdke.odlf().ztwf()) {
                AtomicReference<String> atomicReference = this.qkduk;
                if (atomicReference != null) {
                    dbjc = atomicReference.get();
                    this.jwxax.set(dbjc);
                } else {
                    this.jwxax.set(null);
                    dbjc = null;
                }
            } else {
                ArrayList<String> arrayList2 = new ArrayList<>();
                AtomicReference<String> atomicReference2 = this.qkduk;
                if (atomicReference2 != null) {
                    arrayList2.add(atomicReference2.get());
                }
                dbjc = dbjc(arrayList2);
                this.jwxax.set(dbjc);
            }
            com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format("Updated threat level is %s.", dbjc));
            ztwf();
            if (this.ztwf == null) {
                this.ztwf = new AtomicReference<>();
            }
            JSONObject jSONObject = new JSONObject();
            try {
                hbfhc.C0015hbfhc.yfdke jwxax2 = c0015hbfhc.jwxax();
                jSONObject.put("identityAndBehavioralRiskGroup", c0015hbfhc.wxau());
                if (jwxax2 != null) {
                    String dbjc4 = jwxax2.dbjc();
                    if (!yfdke.qkduk(dbjc4)) {
                        jSONObject.put("identityAndBehavioralRisk", dbjc4);
                        jSONObject.put("behavioralRiskLevel", dbjc4);
                    }
                }
                hbfhc.C0015hbfhc.C0016hbfhc dbjc5 = c0015hbfhc.dbjc();
                jSONObject.put("geozoneRiskGroup", c0015hbfhc.qkduk());
                if (dbjc5 != null) {
                    String dbjc6 = dbjc5.dbjc();
                    if (!yfdke.qkduk(dbjc6)) {
                        jSONObject.put("geozoneRisk", dbjc6);
                        jSONObject.put("geozoneRiskLevel", dbjc6);
                    }
                }
                JSONObject ztwf = c0015hbfhc.ztwf();
                if (ztwf != null && ztwf.length() > 0) {
                    jSONObject.put("mappings", ztwf);
                }
                if (jSONObject.length() <= 0) {
                    jSONObject = null;
                }
                this.ztwf.set(jSONObject);
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.wxau("hbfhc", "Unable to prepare SRA threat details.");
                this.ztwf.set(null);
            }
            JSONObject ztwf2 = c0015hbfhc.ztwf();
            if (ztwf2 != null && ztwf2.length() > 0) {
                try {
                    str3 = ztwf2.getJSONObject("defined").getJSONObject("mappings").getString("geozoneName");
                } catch (JSONException e2) {
                    com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SRA] Could not fetch geozone name");
                }
            }
            String dbjc7 = dbjc(ztwf2, "defined");
            String dbjc8 = dbjc(ztwf2, "learned");
            com.good.gd.vocp.hbfhc dbjc9 = dbjc();
            if (dbjc9 != null) {
                dbjc9.dbjc(str2, str3, dbjc7, dbjc8);
            }
            String dbjc10 = dbjc(ztwf2, "behavioral");
            String dbjc11 = dbjc(ztwf2, "ipAddress");
            String dbjc12 = dbjc(ztwf2, "networkAnomalyDetection");
            String dbjc13 = dbjc(ztwf2, "appAnomalyDetection");
            if (dbjc9 == null) {
                return;
            }
            dbjc9.dbjc(str, dbjc10, dbjc11, dbjc13, dbjc12);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "Unable to calculate threat levels, as received assessment is null.");
    }

    public JSONObject jwxax() {
        AtomicReference<JSONObject> atomicReference = this.ztwf;
        if (atomicReference != null) {
            AtomicReference<JSONObject> atomicReference2 = this.wxau;
            if (atomicReference2 != null) {
                atomicReference2.compareAndSet(atomicReference2.get(), this.ztwf.get());
            }
        } else if (atomicReference != null) {
            AtomicReference<JSONObject> atomicReference3 = this.wxau;
            if (atomicReference3 != null) {
                atomicReference3.compareAndSet(atomicReference3.get(), this.ztwf.get());
            }
        } else {
            AtomicReference<JSONObject> atomicReference4 = this.wxau;
            if (atomicReference4 != null) {
                atomicReference4.set(null);
            }
        }
        AtomicReference<JSONObject> atomicReference5 = this.wxau;
        if (atomicReference5 != null) {
            return atomicReference5.get();
        }
        return null;
    }

    @Override // com.good.gd.vbqbi.hbfhc
    public void qkduk() {
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "Reset threat level and threat details.");
        AtomicReference<String> atomicReference = this.jwxax;
        if (atomicReference != null) {
            atomicReference.set(null);
        }
        AtomicReference<String> atomicReference2 = this.qkduk;
        if (atomicReference2 != null) {
            atomicReference2.set(null);
        }
        AtomicReference<JSONObject> atomicReference3 = this.wxau;
        if (atomicReference3 != null) {
            atomicReference3.set(null);
        }
        AtomicReference<JSONObject> atomicReference4 = this.ztwf;
        if (atomicReference4 != null) {
            atomicReference4.set(null);
        }
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "Updated threat level is null.");
        ztwf();
        com.good.gd.vocp.hbfhc dbjc = dbjc();
        if (dbjc != null) {
            if (!BlackberryAnalyticsCommon.ugfcv()) {
                dbjc.dbjc();
                return;
            }
            dbjc.dbjc("", "", "", "", "");
            dbjc.dbjc("", "", "", "");
        }
    }

    public String wxau() {
        AtomicReference<String> atomicReference = this.jwxax;
        if (atomicReference == null) {
            return null;
        }
        return atomicReference.get();
    }

    protected void ztwf() {
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "Sending threat level change notification.");
        Intent intent = new Intent(BISAction.BIS_THREAT_STATE_CHANGED_ACTION);
        Bundle bundle = new Bundle();
        AtomicReference<String> atomicReference = this.jwxax;
        bundle.putString(BISThreatLevelInfo.BIS_THREAT_LEVEL, atomicReference != null ? atomicReference.get() : null);
        intent.putExtra(BISThreatLevelInfo.BIS_THREAT_LEVEL_EXTRA, bundle);
        GDLocalBroadcastManager gDLocalBroadcastManager = GDLocalBroadcastManager.getInstance();
        if (gDLocalBroadcastManager != null) {
            gDLocalBroadcastManager.sendBroadcast(intent);
        }
    }

    protected String dbjc(JSONObject jSONObject, String str) {
        if (jSONObject == null || jSONObject.length() <= 0 || yfdke.qkduk(str)) {
            return null;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            if (jSONObject2 == null) {
                return null;
            }
            String optString = jSONObject2.optString("assessedRiskLevel");
            return yfdke.qkduk(optString) ? jSONObject2.optString("riskLevel") : optString;
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SRA] Could not fetch the risk level of given Threat Type: " + str);
            return null;
        }
    }
}
