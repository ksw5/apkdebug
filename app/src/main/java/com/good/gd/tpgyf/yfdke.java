package com.good.gd.tpgyf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class yfdke {
    private static yfdke jwxax;
    private final com.good.gd.ovnkx.aqdzk dbjc = new com.good.gd.ovnkx.aqdzk(com.good.gd.ovnkx.mjbm.lqox(), "dynamics_profile");
    private JSONArray qkduk;

    private yfdke() {
        try {
            wxau();
            lqox();
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.ztwf("yfdke", "Failed to create dynamics profile override storage: " + e.getLocalizedMessage());
        }
    }

    private void lqox() {
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Preparing in memory dynamics profile override data.");
        try {
            com.good.gd.ovnkx.aqdzk aqdzkVar = this.dbjc;
            if (aqdzkVar == null) {
                return;
            }
            String liflu = aqdzkVar.liflu();
            if (true == com.good.gd.whhmi.yfdke.qkduk(liflu)) {
                return;
            }
            this.qkduk = new JSONArray(liflu);
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.ztwf("yfdke", "Failed to prepare dynamics profile storage data.");
        }
    }

    private boolean qkduk(String str) {
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Checking if dynamics profile override data already exist into storage.");
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau("yfdke", "Policy type is required to check, if the policy Type exist into storage.");
            return false;
        }
        JSONArray jSONArray = this.qkduk;
        if (jSONArray != null && jSONArray.length() > 0) {
            int length = this.qkduk.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = this.qkduk.optJSONObject(i);
                if (optJSONObject != null && optJSONObject.length() > 0 && optJSONObject.has("mtype")) {
                    String optString = optJSONObject.optString("mtype");
                    if (true != com.good.gd.whhmi.yfdke.qkduk(optString) && optString.equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void wxau() throws IOException {
        if (true != this.dbjc.jwxax()) {
            this.dbjc.dbjc();
            com.good.gd.kloes.hbfhc.wxau("yfdke", "Created dynamics profile override storage.");
        }
    }

    public static yfdke ztwf() {
        if (jwxax == null) {
            synchronized (yfdke.class) {
                if (jwxax == null) {
                    jwxax = new yfdke();
                }
            }
        }
        return jwxax;
    }

    public void dbjc(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.ztwf("yfdke", "Dynamics profile override data is required to store into storage.");
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Store dynamics profile override data into storage.");
        try {
            if (true != qkduk(str)) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("mtype", str);
                if (this.qkduk == null) {
                    this.qkduk = new JSONArray();
                }
                com.good.gd.kloes.ehnkx.dbjc("yfdke", "Previously assigned dynamics profile override JSON from storage: " + this.qkduk);
                this.qkduk.put(jSONObject);
                if (true != this.dbjc.jwxax()) {
                    this.dbjc.dbjc();
                    com.good.gd.kloes.hbfhc.wxau("yfdke", "Created dynamics profile override storage.");
                }
                com.good.gd.kloes.ehnkx.dbjc("yfdke", "Updated dynamics profile override JSON data: " + this.qkduk);
                if (this.dbjc.dbjc(this.qkduk.toString())) {
                    com.good.gd.kloes.hbfhc.wxau("yfdke", "Dynamics profile override data stored into storage.");
                    return;
                } else {
                    com.good.gd.kloes.hbfhc.wxau("yfdke", "Failed to store dynamics profile override data.");
                    return;
                }
            }
            com.good.gd.kloes.hbfhc.wxau("yfdke", "Dynamics profile override type/ data already exist into storage.");
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.ztwf("yfdke", "Failed to create dynamics profile override data storage file: " + e.getLocalizedMessage());
        } catch (JSONException e2) {
            com.good.gd.kloes.hbfhc.ztwf("yfdke", "Failed to store dynamics profile override data: " + e2.getLocalizedMessage());
        }
    }

    public List<String> jwxax() {
        ArrayList arrayList = new ArrayList();
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Get dynamics profile override storage data.");
        JSONArray jSONArray = this.qkduk;
        if (jSONArray == null || jSONArray.length() <= 0) {
            com.good.gd.kloes.hbfhc.wxau("yfdke", "Dynamics profile override data is not available.");
        } else {
            com.good.gd.kloes.ehnkx.dbjc("yfdke", "Previously assigned dynamics profile JSON from storage: " + this.qkduk);
            int length = this.qkduk.length();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = this.qkduk.optJSONObject(i);
                if (optJSONObject != null && optJSONObject.length() > 0) {
                    arrayList.add(optJSONObject.optString("mtype"));
                }
            }
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    public void qkduk() {
        com.good.gd.ovnkx.aqdzk aqdzkVar = this.dbjc;
        if (aqdzkVar == null || !aqdzkVar.jwxax()) {
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Remove dynamics profile override data storage file.");
        this.dbjc.qkduk();
    }

    public boolean dbjc() {
        com.good.gd.kloes.hbfhc.wxau("yfdke", "Remove dynamics profile override data from storage.");
        if (this.qkduk != null) {
            this.qkduk = null;
        }
        com.good.gd.ovnkx.aqdzk aqdzkVar = this.dbjc;
        if (aqdzkVar == null || !aqdzkVar.jwxax()) {
            return false;
        }
        return pmoiy.jwxax(this.dbjc.wxau());
    }
}
