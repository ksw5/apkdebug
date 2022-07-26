package com.good.gd.dvql;

import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private JSONObject dbjc;
    private boolean jwxax;
    private boolean qkduk;
    private final Class wxau = hbfhc.class;

    public hbfhc(String str, JSONObject jSONObject) {
        this.dbjc = jSONObject;
        if (str.equals("appIntelligence") || str.equals("continuousAuth")) {
            return;
        }
        try {
            JSONObject jSONObject2 = this.dbjc;
            if (jSONObject2 != null && jSONObject2.has("name")) {
                if ("CRASH".equals(this.dbjc.getString("name"))) {
                    return;
                }
            }
        } catch (JSONException e) {
        }
        this.qkduk = true;
    }

    public JSONObject dbjc() {
        return this.dbjc;
    }

    public boolean jwxax() {
        return this.jwxax;
    }

    public boolean qkduk() {
        return this.qkduk;
    }

    public void dbjc(JSONObject jSONObject) {
        this.dbjc = jSONObject;
    }

    public void qkduk(boolean z) {
        this.jwxax = z;
    }

    public void dbjc(boolean z) {
        this.qkduk = z;
    }

    public hbfhc(hbfhc hbfhcVar) {
        if (hbfhcVar == null) {
            com.good.gd.kloes.hbfhc.dbjc(hbfhc.class, "Analytics Events Details data is Null.");
            return;
        }
        try {
            this.dbjc = new JSONObject(hbfhcVar.dbjc().toString());
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.dbjc(this.wxau, "Analytics Events Details has Json Exception: " + e.getLocalizedMessage());
            this.dbjc = new JSONObject();
        }
        this.qkduk = hbfhcVar.qkduk();
        this.jwxax = hbfhcVar.jwxax();
    }
}
