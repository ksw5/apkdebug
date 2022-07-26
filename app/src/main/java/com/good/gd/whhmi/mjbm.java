package com.good.gd.whhmi;

import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class mjbm {
    static final /* synthetic */ boolean dbjc = true;

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
        r0 = new com.good.gd.bsvvm.pmoiy(com.blackberry.bis.core.hbfhc.dbjc()).dbjc(r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
        if (com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix().lqox() == 4) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
        com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix().qkduk(3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:?, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008a, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0072, code lost:
        r0 = new com.good.gd.bsvvm.fdyxd(com.blackberry.bis.core.hbfhc.dbjc()).dbjc(r5, r6);
        com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix().qkduk(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static JSONObject dbjc(android.content.Context r5, java.io.File r6) {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            com.good.gd.ujgjo.hbfhc r1 = com.good.gd.ujgjo.hbfhc.jcpqe()     // Catch: java.io.FileNotFoundException -> L5b
            java.io.InputStream r1 = r1.dbjc(r6)     // Catch: java.io.FileNotFoundException -> L5b
            java.lang.String r1 = com.good.gd.ovnkx.mjbm.liflu(r1)     // Catch: java.io.FileNotFoundException -> L5b
            r2 = 0
            if (r1 == 0) goto L5f
            r3 = 1
            java.lang.String r4 = r1.trim()     // Catch: java.io.FileNotFoundException -> L5b
            boolean r4 = r4.isEmpty()     // Catch: java.io.FileNotFoundException -> L5b
            if (r3 == r4) goto L5f
            java.lang.String r3 = "4.0"
            boolean r1 = r3.equals(r1)     // Catch: java.io.FileNotFoundException -> L5b
            if (r1 == 0) goto L5f
            com.good.gd.bsvvm.mjbm r1 = com.blackberry.bis.core.yfdke.mloj()     // Catch: java.io.FileNotFoundException -> L5b
            boolean r3 = com.good.gd.whhmi.mjbm.dbjc     // Catch: java.io.FileNotFoundException -> L5b
            if (r3 != 0) goto L38
            if (r1 == 0) goto L32
            goto L38
        L32:
            java.lang.AssertionError r5 = new java.lang.AssertionError     // Catch: java.io.FileNotFoundException -> L5b
            r5.<init>()     // Catch: java.io.FileNotFoundException -> L5b
            throw r5     // Catch: java.io.FileNotFoundException -> L5b
        L38:
            if (r1 == 0) goto L5d
            com.good.gd.bsvvm.pmoiy r1 = new com.good.gd.bsvvm.pmoiy     // Catch: java.io.FileNotFoundException -> L5b
            com.blackberry.bis.core.pmoiy r2 = com.blackberry.bis.core.hbfhc.dbjc()     // Catch: java.io.FileNotFoundException -> L5b
            r1.<init>(r2)     // Catch: java.io.FileNotFoundException -> L5b
            org.json.JSONObject r0 = r1.dbjc(r5, r6)     // Catch: java.io.FileNotFoundException -> L5b
            com.blackberry.bis.core.BlackberryAnalyticsCommon r5 = com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix()     // Catch: java.io.FileNotFoundException -> L5b
            int r5 = r5.lqox()     // Catch: java.io.FileNotFoundException -> L5b
            r6 = 4
            if (r5 == r6) goto L8a
            com.blackberry.bis.core.BlackberryAnalyticsCommon r5 = com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix()     // Catch: java.io.FileNotFoundException -> L5b
            r6 = 3
            r5.qkduk(r6)     // Catch: java.io.FileNotFoundException -> L5b
            goto L8a
        L5b:
            r5 = move-exception
            goto L8a
        L5d:
            throw r2     // Catch: java.io.FileNotFoundException -> L5b
        L5f:
            com.good.gd.bsvvm.ehnkx r1 = com.blackberry.bis.core.yfdke.uxw()     // Catch: java.io.FileNotFoundException -> L5b
            boolean r3 = com.good.gd.whhmi.mjbm.dbjc     // Catch: java.io.FileNotFoundException -> L5b
            if (r3 != 0) goto L70
            if (r1 == 0) goto L6a
            goto L70
        L6a:
            java.lang.AssertionError r5 = new java.lang.AssertionError     // Catch: java.io.FileNotFoundException -> L5b
            r5.<init>()     // Catch: java.io.FileNotFoundException -> L5b
            throw r5     // Catch: java.io.FileNotFoundException -> L5b
        L70:
            if (r1 == 0) goto L88
            com.good.gd.bsvvm.fdyxd r1 = new com.good.gd.bsvvm.fdyxd     // Catch: java.io.FileNotFoundException -> L5b
            com.blackberry.bis.core.pmoiy r2 = com.blackberry.bis.core.hbfhc.dbjc()     // Catch: java.io.FileNotFoundException -> L5b
            r1.<init>(r2)     // Catch: java.io.FileNotFoundException -> L5b
            org.json.JSONObject r0 = r1.dbjc(r5, r6)     // Catch: java.io.FileNotFoundException -> L5b
            com.blackberry.bis.core.BlackberryAnalyticsCommon r5 = com.blackberry.bis.core.BlackberryAnalyticsCommon.rynix()     // Catch: java.io.FileNotFoundException -> L5b
            r6 = 2
            r5.qkduk(r6)     // Catch: java.io.FileNotFoundException -> L5b
            goto L8a
        L88:
            throw r2     // Catch: java.io.FileNotFoundException -> L5b
        L8a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.whhmi.mjbm.dbjc(android.content.Context, java.io.File):org.json.JSONObject");
    }

    public static String dbjc(JSONObject jSONObject) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject optJSONObject3;
        if (jSONObject == null || jSONObject.length() <= 0) {
            return "";
        }
        String optString = jSONObject.optString("schema");
        if (optString != null && true != optString.trim().isEmpty() && "4.0".equals(optString)) {
            JSONArray optJSONArray = jSONObject.optJSONArray("batch");
            return (optJSONArray == null || (optJSONObject3 = optJSONArray.optJSONObject(0)) == null) ? "" : optJSONObject3.optString("sissdk_datapointId");
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("batchevents");
        return (optJSONArray2 == null || (optJSONObject = optJSONArray2.optJSONObject(0)) == null || (optJSONObject2 = optJSONObject.optJSONObject("appbatch")) == null) ? "" : optJSONObject2.optString("sissdk_datapointId");
    }
}
