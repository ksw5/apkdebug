package com.good.gd.hdyaf;

import android.content.Context;
import android.os.Build;
import com.blackberry.analytics.analyticsengine.fdyxd;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.aqdzk;
import com.blackberry.bis.core.pmoiy;
import com.bold360.natwest.UtilsKt;
import com.good.gd.kloes.ehnkx;
import com.good.gd.ovnkx.mjbm;
import com.good.gd.whhmi.yfdke;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private Class dbjc = getClass();

    public JSONObject dbjc(JSONObject jSONObject, boolean z, String str, String str2) {
        if (jSONObject != null) {
            try {
                if (jSONObject.length() > 0) {
                    if (str != null && str.length() > 0) {
                        if (str2 != null && str2.length() > 0) {
                            JSONObject jSONObject2 = new JSONObject();
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("challengeResponseResult", z);
                            jSONObject3.put("authType", str);
                            jSONObject3.put(UtilsKt.Error, str2);
                            jSONObject2.put("reAuthenticateToConfirmResponse", jSONObject3);
                            jSONObject.put("assessmentContext", jSONObject2);
                            ehnkx.jwxax(this.dbjc, "[SRA] Event payload after re-authentication: " + jSONObject);
                            return jSONObject;
                        }
                        com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[SRA] Cannot form payload, as Auth Result is null or empty.");
                        return null;
                    }
                    com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[SRA] Cannot form payload, as Auth Type is null or empty.");
                    return null;
                }
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.qkduk(this.dbjc, "[SRA] Failed to prepare JSON payload after re-authentication. \t" + e.getLocalizedMessage());
                return null;
            }
        }
        com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[SRA] Events are required to form payload, after re-authentication.");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025 A[Catch: JSONException -> 0x000a, TryCatch #0 {JSONException -> 0x000a, blocks: (B:23:0x0003, B:9:0x001f, B:11:0x0025, B:14:0x002d, B:16:0x0033, B:18:0x003b, B:4:0x000e, B:6:0x0014, B:20:0x0054), top: B:22:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d A[Catch: JSONException -> 0x000a, TryCatch #0 {JSONException -> 0x000a, blocks: (B:23:0x0003, B:9:0x001f, B:11:0x0025, B:14:0x002d, B:16:0x0033, B:18:0x003b, B:4:0x000e, B:6:0x0014, B:20:0x0054), top: B:22:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public JSONObject jwxax(JSONObject r4, com.good.gd.cth.hbfhc r5) {
        /*
            r3 = this;
            r0 = 0
            if (r4 == 0) goto Lc
            int r1 = r4.length()     // Catch: org.json.JSONException -> La
            if (r1 > 0) goto L1f
            goto Lc
        La:
            r4 = move-exception
            goto L5c
        Lc:
            if (r5 == 0) goto L54
            java.util.ArrayList r1 = r5.dbjc()     // Catch: org.json.JSONException -> La
            if (r1 == 0) goto L54
            java.util.ArrayList r1 = r5.dbjc()     // Catch: org.json.JSONException -> La
            boolean r1 = r1.isEmpty()     // Catch: org.json.JSONException -> La
            if (r1 == 0) goto L1f
            goto L54
        L1f:
            org.json.JSONObject r4 = r3.qkduk(r4, r5)     // Catch: org.json.JSONException -> La
            if (r4 != 0) goto L2d
            java.lang.Class r4 = r3.dbjc     // Catch: org.json.JSONException -> La
            java.lang.String r5 = "[SRA] Formed JSON payload is null."
            com.good.gd.kloes.hbfhc.dbjc(r4, r5)     // Catch: org.json.JSONException -> La
            return r0
        L2d:
            int r5 = r4.length()     // Catch: org.json.JSONException -> La
            if (r5 > 0) goto L3b
            java.lang.Class r4 = r3.dbjc     // Catch: org.json.JSONException -> La
            java.lang.String r5 = "[SRA] Formed JSON payload is empty."
            com.good.gd.kloes.hbfhc.dbjc(r4, r5)     // Catch: org.json.JSONException -> La
            return r0
        L3b:
            java.lang.Class r5 = r3.dbjc     // Catch: org.json.JSONException -> La
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: org.json.JSONException -> La
            r1.<init>()     // Catch: org.json.JSONException -> La
            java.lang.String r2 = "[SRA] Event payload: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: org.json.JSONException -> La
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch: org.json.JSONException -> La
            java.lang.String r1 = r1.toString()     // Catch: org.json.JSONException -> La
            com.good.gd.kloes.ehnkx.jwxax(r5, r1)     // Catch: org.json.JSONException -> La
            return r4
        L54:
            java.lang.Class r4 = r3.dbjc     // Catch: org.json.JSONException -> La
            java.lang.String r5 = "[SRA] Events are required to form payload."
            com.good.gd.kloes.hbfhc.dbjc(r4, r5)     // Catch: org.json.JSONException -> La
            return r0
        L5c:
            java.lang.Class r5 = r3.dbjc
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[SRA] Failed to prepare JSON payload. "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r4 = r4.getLocalizedMessage()
            java.lang.StringBuilder r4 = r1.append(r4)
            java.lang.String r4 = r4.toString()
            com.good.gd.kloes.hbfhc.qkduk(r5, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.hdyaf.hbfhc.jwxax(org.json.JSONObject, com.good.gd.cth.hbfhc):org.json.JSONObject");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JSONObject qkduk(JSONObject jSONObject, com.good.gd.cth.hbfhc hbfhcVar) throws JSONException {
        pmoiy dbjc = com.blackberry.bis.core.hbfhc.dbjc();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("actionsRequired", true);
        jSONObject2.put("responseRequired", true);
        jSONObject2.put("effectivePolicyDataRequired", true);
        jSONObject2.put("datapoint", dbjc(jSONObject, hbfhcVar));
        if (yfdke.liflu()) {
            if (((fdyxd) dbjc) != null) {
                if (true != yfdke.qkduk(null)) {
                    jSONObject2.put("sourceType", (Object) null);
                } else {
                    jSONObject2.put("sourceType", "nativeapp");
                }
            } else {
                throw null;
            }
        } else {
            jSONObject2.put("sourceType", "bbd");
        }
        return jSONObject2;
    }

    public byte[] dbjc(String str) {
        byte[] bArr = null;
        int i = 1;
        while (true) {
            if (i > 3) {
                break;
            }
            bArr = mjbm.dbjc(str);
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("[SRA] Trying to create request payload gzip: %s", Integer.valueOf(i)));
            if (bArr != null && bArr.length > 0) {
                com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[SRA] Gzip compression successful.");
                break;
            }
            com.good.gd.kloes.hbfhc.qkduk(this.dbjc, "[SRA] Gzip compression failed.");
            i++;
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:107:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x020a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public JSONObject dbjc(JSONObject r18, com.good.gd.cth.hbfhc r19) throws JSONException {
        /*
            Method dump skipped, instructions count: 726
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.hdyaf.hbfhc.dbjc(org.json.JSONObject, com.good.gd.cth.hbfhc):org.json.JSONObject");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JSONObject dbjc() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context jwxax = BlackberryAnalyticsCommon.rynix().jwxax();
        String ugfcv = ((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ugfcv();
        if (true != yfdke.qkduk(ugfcv)) {
            jSONObject.put("tenantId", ugfcv);
        }
        jSONObject.put("appName", aqdzk.qkduk(jwxax));
        jSONObject.put("appVersion", aqdzk.dbjc(jwxax));
        jSONObject.put("os", "ANDROID");
        jSONObject.put("osVersion", Build.VERSION.RELEASE);
        jSONObject.put("deviceType", aqdzk.jwxax(jwxax));
        jSONObject.put("deviceModel", Build.MODEL);
        jSONObject.put("timezone", TimeZone.getDefault().getID());
        if (yfdke.liflu()) {
            if (true != yfdke.qkduk(null)) {
                jSONObject.put("sourceType", (Object) null);
            } else {
                jSONObject.put("sourceType", "nativeapp");
            }
        } else {
            jSONObject.put("sourceType", "bbd");
        }
        return jSONObject;
    }
}
