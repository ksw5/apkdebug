package com.good.gd.efxoe;

import com.good.gd.zwn.aqdzk;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class mjbm extends com.good.gd.lqnsz.yfdke {
    private final Class dbjc = mjbm.class;
    private com.good.gd.yufa.hbfhc qkduk = new com.good.gd.yufa.hbfhc();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements com.good.gd.yokds.hbfhc {
        hbfhc(mjbm mjbmVar) {
        }

        @Override // com.good.gd.yokds.hbfhc
        public void dbjc(boolean z, int i, String str) {
        }
    }

    private void qkduk(JSONObject jSONObject) {
        String dbjc;
        JSONObject jSONObject2;
        boolean z = jSONObject == null || jSONObject.length() < 0;
        com.good.gd.yokds.yfdke dbjc2 = com.good.gd.yokds.yfdke.dbjc();
        String str = "";
        String optString = (z || jSONObject.isNull("entityDefId")) ? str : jSONObject.optString("entityDefId");
        if (!z && !jSONObject.isNull("mType") && !com.good.gd.whhmi.yfdke.qkduk(jSONObject.optString("mType"))) {
            dbjc = jSONObject.optString("mType");
        } else {
            dbjc = dbjc2.dbjc(optString);
        }
        if (!z && !jSONObject.isNull("settings")) {
            jSONObject2 = jSONObject.optJSONObject("settings");
        } else {
            jSONObject2 = new JSONObject();
        }
        if (!z && !jSONObject.isNull("entityInstanceId")) {
            str = jSONObject.optString("entityInstanceId");
        }
        String dbjc3 = com.good.gd.zwn.mjbm.dbjc(8);
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format("[Assign Profile] Calling the profile override API for policy type: %s and tracking ID: %s", dbjc, dbjc3));
        com.good.gd.yokds.yfdke.dbjc().dbjc(dbjc, dbjc3, str, jSONObject2, new pmoiy(this));
    }

    /* JADX WARN: Removed duplicated region for block: B:249:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0102  */
    @Override // com.good.gd.lqnsz.yfdke
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dbjc(boolean r21, com.good.gd.lqnsz.hbfhc r22, aqdzk r23) {
        /*
            Method dump skipped, instructions count: 1266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.efxoe.mjbm.dbjc(boolean, com.good.gd.lqnsz.hbfhc, com.good.gd.zwn.aqdzk):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0025 A[Catch: Exception -> 0x0010, TryCatch #0 {Exception -> 0x0010, blocks: (B:24:0x0007, B:6:0x0016, B:9:0x001d, B:11:0x0025, B:14:0x002c, B:15:0x0030, B:17:0x003f, B:20:0x0047), top: B:23:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f A[Catch: Exception -> 0x0010, TryCatch #0 {Exception -> 0x0010, blocks: (B:24:0x0007, B:6:0x0016, B:9:0x001d, B:11:0x0025, B:14:0x002c, B:15:0x0030, B:17:0x003f, B:20:0x0047), top: B:23:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0047 A[Catch: Exception -> 0x0010, TRY_LEAVE, TryCatch #0 {Exception -> 0x0010, blocks: (B:24:0x0007, B:6:0x0016, B:9:0x001d, B:11:0x0025, B:14:0x002c, B:15:0x0030, B:17:0x003f, B:20:0x0047), top: B:23:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void dbjc(JSONObject r7) {
        /*
            r6 = this;
            java.lang.String r0 = "geo_block_message"
            java.lang.String r1 = "geo_block_title"
            r2 = 1
            if (r7 == 0) goto L12
            int r3 = r7.length()     // Catch: java.lang.Exception -> L10
            if (r3 >= 0) goto Le
            goto L12
        Le:
            r3 = 0
            goto L13
        L10:
            r7 = move-exception
            goto L4f
        L12:
            r3 = r2
        L13:
            r4 = 0
            if (r3 != 0) goto L22
            boolean r5 = r7.isNull(r1)     // Catch: java.lang.Exception -> L10
            if (r5 == 0) goto L1d
            goto L22
        L1d:
            java.lang.String r1 = r7.optString(r1)     // Catch: java.lang.Exception -> L10
            goto L23
        L22:
            r1 = r4
        L23:
            if (r3 != 0) goto L30
            boolean r3 = r7.isNull(r0)     // Catch: java.lang.Exception -> L10
            if (r3 == 0) goto L2c
            goto L30
        L2c:
            java.lang.String r4 = r7.optString(r0)     // Catch: java.lang.Exception -> L10
        L30:
            com.good.gd.yufa.hbfhc r7 = new com.good.gd.yufa.hbfhc     // Catch: java.lang.Exception -> L10
            com.blackberry.bis.core.pmoiy r7 = com.blackberry.bis.core.hbfhc.dbjc()     // Catch: java.lang.Exception -> L10
            com.blackberry.analytics.analyticsengine.fdyxd r7 = (com.blackberry.analytics.analyticsengine.fdyxd) r7     // Catch: java.lang.Exception -> L10
            boolean r7 = r7.ssosk()     // Catch: java.lang.Exception -> L10
            if (r2 == r7) goto L47
            java.lang.String r7 = "hbfhc"
            java.lang.String r0 = "Cannot enforce Geo block since application is not authorized once."
            com.good.gd.kloes.hbfhc.ztwf(r7, r0)     // Catch: java.lang.Exception -> L10
            goto L6b
        L47:
            com.good.gd.gahh.hbfhc r7 = com.good.gd.gahh.hbfhc.jwxax()     // Catch: java.lang.Exception -> L10
            r7.dbjc(r1, r4)     // Catch: java.lang.Exception -> L10
            goto L6b
        L4f:
            java.lang.Class r0 = r6.dbjc
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[SRA] Exception generated while enforcing Geo Block: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r7 = r7.getLocalizedMessage()
            java.lang.StringBuilder r7 = r1.append(r7)
            java.lang.String r7 = r7.toString()
            com.good.gd.kloes.hbfhc.qkduk(r0, r7)
        L6b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.efxoe.mjbm.dbjc(org.json.JSONObject):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0028 A[Catch: Exception -> 0x0012, TryCatch #0 {Exception -> 0x0012, blocks: (B:42:0x0009, B:6:0x0019, B:9:0x0020, B:11:0x0028, B:14:0x002f, B:25:0x0065, B:36:0x005e, B:37:0x0053, B:38:0x0048, B:39:0x003a), top: B:41:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005e A[Catch: Exception -> 0x0012, TryCatch #0 {Exception -> 0x0012, blocks: (B:42:0x0009, B:6:0x0019, B:9:0x0020, B:11:0x0028, B:14:0x002f, B:25:0x0065, B:36:0x005e, B:37:0x0053, B:38:0x0048, B:39:0x003a), top: B:41:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0053 A[Catch: Exception -> 0x0012, TryCatch #0 {Exception -> 0x0012, blocks: (B:42:0x0009, B:6:0x0019, B:9:0x0020, B:11:0x0028, B:14:0x002f, B:25:0x0065, B:36:0x005e, B:37:0x0053, B:38:0x0048, B:39:0x003a), top: B:41:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0048 A[Catch: Exception -> 0x0012, TryCatch #0 {Exception -> 0x0012, blocks: (B:42:0x0009, B:6:0x0019, B:9:0x0020, B:11:0x0028, B:14:0x002f, B:25:0x0065, B:36:0x005e, B:37:0x0053, B:38:0x0048, B:39:0x003a), top: B:41:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x003a A[Catch: Exception -> 0x0012, TryCatch #0 {Exception -> 0x0012, blocks: (B:42:0x0009, B:6:0x0019, B:9:0x0020, B:11:0x0028, B:14:0x002f, B:25:0x0065, B:36:0x005e, B:37:0x0053, B:38:0x0048, B:39:0x003a), top: B:41:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dbjc(JSONObject r13, aqdzk r14) {
        /*
            r12 = this;
            java.lang.String r0 = "message"
            java.lang.String r1 = "title"
            r2 = 1
            r3 = 0
            if (r13 == 0) goto L15
            int r4 = r13.length()     // Catch: java.lang.Exception -> L12
            if (r4 >= 0) goto L10
            goto L15
        L10:
            r4 = r3
            goto L16
        L12:
            r13 = move-exception
            goto L75
        L15:
            r4 = r2
        L16:
            r5 = 0
            if (r4 != 0) goto L25
            boolean r6 = r13.isNull(r1)     // Catch: java.lang.Exception -> L12
            if (r6 == 0) goto L20
            goto L25
        L20:
            java.lang.String r1 = r13.optString(r1)     // Catch: java.lang.Exception -> L12
            goto L26
        L25:
            r1 = r5
        L26:
            if (r4 != 0) goto L33
            boolean r6 = r13.isNull(r0)     // Catch: java.lang.Exception -> L12
            if (r6 == 0) goto L2f
            goto L33
        L2f:
            java.lang.String r5 = r13.optString(r0)     // Catch: java.lang.Exception -> L12
        L33:
            r6 = r5
            r0 = 600(0x258, float:8.41E-43)
            if (r4 == 0) goto L3a
            r7 = r0
            goto L42
        L3a:
            java.lang.String r5 = "timeout"
            int r0 = r13.optInt(r5, r0)     // Catch: java.lang.Exception -> L12
            r7 = r0
        L42:
            r0 = 300(0x12c, float:4.2E-43)
            if (r4 == 0) goto L48
            r8 = r0
            goto L4f
        L48:
            java.lang.String r5 = "gracePeriod"
            int r0 = r13.optInt(r5, r0)     // Catch: java.lang.Exception -> L12
            r8 = r0
        L4f:
            if (r4 == 0) goto L53
            r9 = r2
            goto L5a
        L53:
            java.lang.String r0 = "enforce"
            boolean r2 = r13.optBoolean(r0, r2)     // Catch: java.lang.Exception -> L12
            r9 = r2
        L5a:
            if (r4 == 0) goto L5e
            r10 = r3
            goto L65
        L5e:
            java.lang.String r0 = "requiredPassword"
            boolean r13 = r13.optBoolean(r0, r3)     // Catch: java.lang.Exception -> L12
            r10 = r13
        L65:
            com.good.gd.yufa.hbfhc r13 = new com.good.gd.yufa.hbfhc     // Catch: java.lang.Exception -> L12
            com.good.gd.efxoe.hbfhc r11 = new com.good.gd.efxoe.hbfhc     // Catch: java.lang.Exception -> L12
            r11.<init>(r12)     // Catch: java.lang.Exception -> L12
            com.good.gd.lsxdu.hbfhc r4 = com.good.gd.lsxdu.hbfhc.dbjc()     // Catch: java.lang.Exception -> L12
            r5 = r1
            r4.dbjc(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Exception -> L12
            goto L9a
        L75:
            java.lang.Class r0 = r12.dbjc
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[SRA] Exception generated while handling REACH: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r13 = r13.getLocalizedMessage()
            java.lang.StringBuilder r13 = r1.append(r13)
            java.lang.String r13 = r13.toString()
            com.good.gd.kloes.hbfhc.qkduk(r0, r13)
            if (r14 == 0) goto L9a
            r13 = 200(0xc8, float:2.8E-43)
            java.lang.String r0 = "Exception in handling SRA's REACH action."
            r14.dbjc(r3, r13, r0)
        L9a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.efxoe.mjbm.dbjc(org.json.JSONObject, com.good.gd.zwn.aqdzk):void");
    }

    private void dbjc(aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[SRA] Perform default Unblock.");
        this.qkduk.dbjc();
        if (aqdzkVar != null) {
            aqdzkVar.dbjc(true, 200, com.good.gd.zwn.mjbm.qkduk(200));
        }
    }

    private void dbjc(List<String> list) {
        com.good.gd.yokds.yfdke.dbjc().dbjc(list, new hbfhc(this));
    }
}
