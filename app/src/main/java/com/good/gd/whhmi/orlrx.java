package com.good.gd.whhmi;

import android.content.Context;
import android.os.Build;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class orlrx {
    private static final Class qkduk = mjbm.class;
    protected com.blackberry.bis.core.pmoiy dbjc;

    public orlrx(com.blackberry.bis.core.pmoiy pmoiyVar) {
        this.dbjc = pmoiyVar;
    }

    protected String dbjc() {
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JSONObject dbjc(Context context, File file) {
        InputStream inputStream;
        JSONObject jSONObject;
        JSONArray jSONArray;
        JSONArray jSONArray2;
        Class cls = qkduk;
        com.good.gd.kloes.hbfhc.wxau(cls, "Preparing event request JSON Payload.");
        JSONObject jSONObject2 = new JSONObject();
        InputStream inputStream2 = null;
        try {
            try {
                inputStream = com.good.gd.ujgjo.hbfhc.jcpqe().dbjc(file);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            HashMap<String, Object> ztwf = com.good.gd.ovnkx.mjbm.ztwf(inputStream);
            jSONObject = (JSONObject) ztwf.get("LogFileBatchHeader");
            jSONArray = (JSONArray) ztwf.get("EventLogs");
            jSONArray2 = (JSONArray) ztwf.get("ActivityLogs");
        } catch (Exception e2) {
            e = e2;
            inputStream2 = inputStream;
            com.good.gd.kloes.hbfhc.qkduk(qkduk, "Unable to wrap logs into JSON." + e.getMessage());
            inputStream = inputStream2;
            inputStream2 = inputStream2;
            com.good.gd.ovnkx.mjbm.dbjc(inputStream);
            com.good.gd.kloes.hbfhc.wxau(qkduk, "Wrapped logs into JSON.");
            return jSONObject2;
        } catch (Throwable th2) {
            th = th2;
            inputStream2 = inputStream;
            com.good.gd.ovnkx.mjbm.dbjc(inputStream2);
            throw th;
        }
        if ((jSONArray != null && jSONArray.length() != 0) || (jSONArray2 != null && jSONArray2.length() != 0)) {
            jSONObject2.put("namespace", dbjc());
            jSONObject2.put("schema", "4.0");
            String str = "Create Batch Events JSON Array.";
            com.good.gd.kloes.hbfhc.wxau(cls, str);
            JSONArray jSONArray3 = new JSONArray();
            jSONArray3.put(dbjc(context, jSONObject, jSONArray, jSONArray2));
            jSONObject2.put("batch", jSONArray3);
            inputStream2 = str;
            com.good.gd.ovnkx.mjbm.dbjc(inputStream);
            com.good.gd.kloes.hbfhc.wxau(qkduk, "Wrapped logs into JSON.");
            return jSONObject2;
        }
        com.good.gd.ovnkx.mjbm.dbjc(inputStream);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JSONObject dbjc(Context context, JSONObject jSONObject, JSONArray jSONArray, JSONArray jSONArray2) throws JSONException {
        String str;
        String str2;
        String str3;
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject != null && jSONObject.length() > 0) {
            str2 = jSONObject.getString("appVersion");
            if (str2 == null || str2.trim().isEmpty()) {
                str2 = com.blackberry.bis.core.aqdzk.dbjc(context);
            }
            str3 = jSONObject.getString("tz");
            if (str3 == null || str3.trim().isEmpty()) {
                str3 = TimeZone.getDefault().getID();
            }
            str = jSONObject.getString("osversion");
            if (str == null || str.trim().isEmpty()) {
                str = com.blackberry.bis.core.aqdzk.jwxax();
            }
        } else {
            str = null;
            str2 = null;
            str3 = null;
        }
        jSONObject2.put("app_id", ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).jcpqe());
        jSONObject2.put("app_name", (String) context.getApplicationInfo().loadLabel(context.getPackageManager()));
        jSONObject2.put("app_ver", str2);
        if (com.blackberry.bis.core.yfdke.jcpqe() != null) {
            com.blackberry.analytics.analyticsengine.hbfhc.dbjc();
            jSONObject2.put("sdk_ver", "4.0.0.326");
            jSONObject2.put("locale", com.blackberry.bis.core.aqdzk.qkduk());
            jSONObject2.put("os", "ANDROID");
            jSONObject2.put("os_ver", str);
            jSONObject2.put("device_type", com.blackberry.bis.core.aqdzk.jwxax(context));
            jSONObject2.put("device_model", Build.MODEL);
            jSONObject2.put("tz", str3);
            jSONObject2.put("instance_id", com.good.gd.ovnkx.hbfhc.qkduk().dbjc());
            jSONObject2.put("org_id", ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).iulf());
            String uuid = UUID.randomUUID().toString();
            jSONObject2.put("sissdk_datapointId", uuid);
            com.good.gd.zwn.mjbm.lqox(uuid);
            com.good.gd.kloes.hbfhc.wxau(qkduk, "Datapoint id for historical event payload: " + uuid);
            com.good.gd.idl.hbfhc.pqq().odlf();
            if (jSONArray != null && jSONArray.length() > 0) {
                jSONObject2.put("events", jSONArray);
            }
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                jSONObject2.put("activities", jSONArray2);
            }
            return jSONObject2;
        }
        throw null;
    }
}
