package com.good.gd.lqnsz;

import com.good.gd.lqnsz.hbfhc;
import com.good.gd.zwn.aqdzk;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class yfdke {
    /* JADX WARN: Removed duplicated region for block: B:19:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00be A[Catch: JSONException -> 0x0105, TryCatch #0 {JSONException -> 0x0105, blocks: (B:7:0x0017, B:10:0x0029, B:13:0x0038, B:14:0x003e, B:17:0x004c, B:21:0x005a, B:23:0x0060, B:24:0x006e, B:28:0x0081, B:31:0x0098, B:34:0x00a9, B:36:0x00be, B:38:0x00c4, B:42:0x00d3, B:44:0x00d9, B:45:0x00e7, B:48:0x00f9, B:52:0x00cd, B:53:0x0094, B:55:0x0054, B:56:0x0048), top: B:6:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c4 A[Catch: JSONException -> 0x0105, TryCatch #0 {JSONException -> 0x0105, blocks: (B:7:0x0017, B:10:0x0029, B:13:0x0038, B:14:0x003e, B:17:0x004c, B:21:0x005a, B:23:0x0060, B:24:0x006e, B:28:0x0081, B:31:0x0098, B:34:0x00a9, B:36:0x00be, B:38:0x00c4, B:42:0x00d3, B:44:0x00d9, B:45:0x00e7, B:48:0x00f9, B:52:0x00cd, B:53:0x0094, B:55:0x0054, B:56:0x0048), top: B:6:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0094 A[Catch: JSONException -> 0x0105, TryCatch #0 {JSONException -> 0x0105, blocks: (B:7:0x0017, B:10:0x0029, B:13:0x0038, B:14:0x003e, B:17:0x004c, B:21:0x005a, B:23:0x0060, B:24:0x006e, B:28:0x0081, B:31:0x0098, B:34:0x00a9, B:36:0x00be, B:38:0x00c4, B:42:0x00d3, B:44:0x00d9, B:45:0x00e7, B:48:0x00f9, B:52:0x00cd, B:53:0x0094, B:55:0x0054, B:56:0x0048), top: B:6:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0054 A[Catch: JSONException -> 0x0105, TryCatch #0 {JSONException -> 0x0105, blocks: (B:7:0x0017, B:10:0x0029, B:13:0x0038, B:14:0x003e, B:17:0x004c, B:21:0x005a, B:23:0x0060, B:24:0x006e, B:28:0x0081, B:31:0x0098, B:34:0x00a9, B:36:0x00be, B:38:0x00c4, B:42:0x00d3, B:44:0x00d9, B:45:0x00e7, B:48:0x00f9, B:52:0x00cd, B:53:0x0094, B:55:0x0054, B:56:0x0048), top: B:6:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0048 A[Catch: JSONException -> 0x0105, TryCatch #0 {JSONException -> 0x0105, blocks: (B:7:0x0017, B:10:0x0029, B:13:0x0038, B:14:0x003e, B:17:0x004c, B:21:0x005a, B:23:0x0060, B:24:0x006e, B:28:0x0081, B:31:0x0098, B:34:0x00a9, B:36:0x00be, B:38:0x00c4, B:42:0x00d3, B:44:0x00d9, B:45:0x00e7, B:48:0x00f9, B:52:0x00cd, B:53:0x0094, B:55:0x0054, B:56:0x0048), top: B:6:0x0017 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public hbfhc dbjc(String r12) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.lqnsz.yfdke.dbjc(java.lang.String):com.good.gd.lqnsz.hbfhc");
    }

    public abstract void dbjc(boolean z, hbfhc hbfhcVar, aqdzk aqdzkVar);

    private hbfhc.C0015hbfhc dbjc(hbfhc.C0015hbfhc c0015hbfhc, JSONObject jSONObject) {
        JSONObject jSONObject2 = null;
        if (c0015hbfhc == null || jSONObject == null || jSONObject.length() <= 0) {
            return null;
        }
        if (!jSONObject.isNull("ecoId")) {
            jSONObject.optString("ecoId");
        }
        if (c0015hbfhc != null) {
            if (!jSONObject.isNull("behavioralRiskLevel")) {
                jSONObject.optString("behavioralRiskLevel");
            }
            if (!jSONObject.isNull("datetime")) {
                jSONObject.optString("datetime");
            }
            JSONObject optJSONObject = jSONObject.isNull("identityAndBehavioralRisk") ? null : jSONObject.optJSONObject("identityAndBehavioralRisk");
            c0015hbfhc.qkduk(optJSONObject);
            if (optJSONObject != null && optJSONObject.length() > 0) {
                String optString = optJSONObject.isNull("level") ? null : optJSONObject.optString("level");
                c0015hbfhc.getClass();
                hbfhc.C0015hbfhc.yfdke yfdkeVar = new hbfhc.C0015hbfhc.yfdke(c0015hbfhc);
                yfdkeVar.dbjc = optString;
                com.good.gd.kloes.hbfhc.wxau("yfdke", "[SRA] Received Identity and Behavioral risk level value: " + optString);
                JSONArray optJSONArray = optJSONObject.optJSONArray("factors");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        arrayList.add(optJSONArray.optString(i));
                    }
                }
                c0015hbfhc.dbjc(yfdkeVar);
            }
            JSONObject optJSONObject2 = jSONObject.isNull("geozoneRisk") ? null : jSONObject.optJSONObject("geozoneRisk");
            c0015hbfhc.dbjc(optJSONObject2);
            if (optJSONObject2 != null && optJSONObject2.length() > 0) {
                String optString2 = optJSONObject2.isNull("level") ? null : optJSONObject2.optString("level");
                c0015hbfhc.getClass();
                hbfhc.C0015hbfhc.C0016hbfhc c0016hbfhc = new hbfhc.C0015hbfhc.C0016hbfhc(c0015hbfhc);
                c0016hbfhc.dbjc = optString2;
                com.good.gd.kloes.hbfhc.wxau("yfdke", "[SRA] Received Geozone risk level value: " + optString2);
                JSONArray optJSONArray2 = optJSONObject2.optJSONArray("factors");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        arrayList2.add(optJSONArray2.optString(i2));
                    }
                }
                c0015hbfhc.dbjc(c0016hbfhc);
            }
            if (!jSONObject.isNull("datapoint")) {
                jSONObject.optJSONObject("datapoint");
            }
            if (!jSONObject.isNull("mappings")) {
                jSONObject2 = jSONObject.optJSONObject("mappings");
            }
            c0015hbfhc.jwxax(jSONObject2);
            return c0015hbfhc;
        }
        throw null;
    }

    private hbfhc.yfdke dbjc(hbfhc.yfdke yfdkeVar, JSONObject jSONObject) {
        if (yfdkeVar == null || jSONObject == null || jSONObject.length() <= 0) {
            return null;
        }
        if (!jSONObject.isNull("ecoId")) {
            jSONObject.optString("ecoId");
        }
        if (yfdkeVar != null) {
            if (!jSONObject.isNull("policyGuid")) {
                jSONObject.optString("policyGuid");
            }
            if (!jSONObject.isNull("policyName")) {
                jSONObject.optString("policyName");
            }
            JSONArray optJSONArray = jSONObject.isNull("actions") ? null : jSONObject.optJSONArray("actions");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                HashMap<String, hbfhc.yfdke.C0017hbfhc> hashMap = new HashMap<>();
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = optJSONArray.isNull(i) ? null : optJSONArray.optJSONObject(i);
                    if (optJSONObject != null && optJSONObject.length() > 0) {
                        yfdkeVar.getClass();
                        hbfhc.yfdke.C0017hbfhc c0017hbfhc = new hbfhc.yfdke.C0017hbfhc(yfdkeVar);
                        String optString = optJSONObject.isNull("actionType") ? null : optJSONObject.optString("actionType");
                        if (true != com.good.gd.whhmi.yfdke.qkduk(optString) && optString.contains("app:")) {
                            c0017hbfhc.dbjc(optString);
                            JSONObject optJSONObject2 = optJSONObject.isNull("actionAttributes") ? null : optJSONObject.optJSONObject("actionAttributes");
                            if (optJSONObject2 != null) {
                                c0017hbfhc.dbjc(optJSONObject2);
                            }
                            hashMap.put(optString, c0017hbfhc);
                        } else {
                            com.good.gd.kloes.hbfhc.ztwf("yfdke", "[SRA] Ignored unknown action type: " + optString);
                        }
                    }
                }
                yfdkeVar.dbjc(hashMap);
            }
            return yfdkeVar;
        }
        throw null;
    }

    public boolean dbjc(hbfhc hbfhcVar, String str) {
        hbfhc.yfdke wxau;
        HashMap<String, hbfhc.yfdke.C0017hbfhc> dbjc;
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            return false;
        }
        if (hbfhcVar != null && (wxau = hbfhcVar.wxau()) != null && (dbjc = wxau.dbjc()) != null && true != dbjc.isEmpty() && dbjc.get(str) != null) {
            return true;
        }
        com.good.gd.kloes.hbfhc.wxau("yfdke", "[SRA] SRA response does not contains the action: " + str);
        return false;
    }
}
