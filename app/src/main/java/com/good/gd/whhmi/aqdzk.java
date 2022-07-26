package com.good.gd.whhmi;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class aqdzk {
    protected com.blackberry.bis.core.pmoiy dbjc;

    public aqdzk(com.blackberry.bis.core.pmoiy pmoiyVar) {
        this.dbjc = pmoiyVar;
    }

    protected String dbjc() {
        throw null;
    }

    public JSONObject dbjc(Context context, File file) {
        InputStream inputStream;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        JSONArray jSONArray;
        com.good.gd.kloes.hbfhc.wxau("ContentValues", "Wrap Logs Into Json.");
        JSONObject jSONObject3 = new JSONObject();
        InputStream inputStream2 = null;
        try {
            try {
                inputStream = com.good.gd.ujgjo.hbfhc.jcpqe().dbjc(file);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            HashMap<String, Object> wxau = com.good.gd.ovnkx.mjbm.wxau(inputStream);
            jSONObject = (JSONObject) wxau.get("LogFileHeader");
            jSONObject2 = (JSONObject) wxau.get("LogFileBatchHeader");
            jSONArray = (JSONArray) wxau.get("EventLogs");
        } catch (Exception e2) {
            e = e2;
            inputStream2 = inputStream;
            com.good.gd.kloes.hbfhc.qkduk("ContentValues", "Unable to wrap logs into JSON." + e.getMessage());
            inputStream = inputStream2;
            com.good.gd.ovnkx.mjbm.dbjc(inputStream);
            com.good.gd.kloes.hbfhc.wxau("ContentValues", "Wrapped logs into JSON.");
            return jSONObject3;
        } catch (Throwable th2) {
            th = th2;
            inputStream2 = inputStream;
            com.good.gd.ovnkx.mjbm.dbjc(inputStream2);
            throw th;
        }
        if (jSONArray != null && jSONArray.length() != 0) {
            JSONObject dbjc = dbjc(context, jSONObject);
            JSONArray dbjc2 = dbjc(context, jSONObject2, jSONArray);
            jSONObject3.put("header", dbjc);
            jSONObject3.put("batchevents", dbjc2);
            com.good.gd.ovnkx.mjbm.dbjc(inputStream);
            com.good.gd.kloes.hbfhc.wxau("ContentValues", "Wrapped logs into JSON.");
            return jSONObject3;
        }
        com.good.gd.ovnkx.mjbm.dbjc(inputStream);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JSONObject dbjc(Context context, JSONObject jSONObject) throws JSONException {
        String str;
        String str2;
        com.good.gd.kloes.hbfhc.wxau("ContentValues", "Create POST network request header section.");
        if (jSONObject == null) {
            str = null;
            str2 = null;
        } else {
            str2 = jSONObject.getString("tz");
            str = jSONObject.getString("osversion");
        }
        JSONObject jSONObject2 = new JSONObject();
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        jSONObject2.put("schema", "3.0");
        if (com.blackberry.bis.core.yfdke.jcpqe() != null) {
            com.blackberry.analytics.analyticsengine.hbfhc.dbjc();
            jSONObject2.put("asdkver", "4.0.0.326");
            jSONObject2.put("appid", ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).jcpqe());
            jSONObject2.put("os", "ANDROID");
            jSONObject2.put("lang", com.blackberry.bis.core.aqdzk.qkduk());
            if (TextUtils.isEmpty(str)) {
                str = Build.VERSION.RELEASE;
            }
            jSONObject2.put("osversion", str);
            jSONObject2.put("ts", Long.toString(valueOf.longValue()));
            jSONObject2.put("model", Build.MODEL);
            jSONObject2.put("ffactor", com.blackberry.bis.core.aqdzk.jwxax(context));
            jSONObject2.put("oem", Build.MANUFACTURER);
            if (TextUtils.isEmpty(str2)) {
                str2 = TimeZone.getDefault().getID();
            }
            jSONObject2.put("tz", str2);
            jSONObject2.put("sn", ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).iulf());
            return jSONObject2;
        }
        throw null;
    }

    private JSONArray dbjc(Context context, JSONObject jSONObject, JSONArray jSONArray) throws JSONException {
        String str;
        com.good.gd.kloes.hbfhc.wxau("ContentValues", "Create Batch Events JSON Array.");
        if (jSONObject == null) {
            str = null;
        } else {
            str = jSONObject.getString("appVersion");
            if (str == null || str.trim().isEmpty()) {
                str = com.blackberry.bis.core.aqdzk.dbjc(context);
            }
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", com.blackberry.bis.core.aqdzk.wxau(context));
        jSONObject2.put("name", (String) context.getApplicationInfo().loadLabel(context.getPackageManager()));
        jSONObject2.put("instance", com.good.gd.ovnkx.hbfhc.qkduk().dbjc());
        jSONObject2.put("bbd", dbjc());
        String uuid = UUID.randomUUID().toString();
        jSONObject2.put("sissdk_datapointId", uuid);
        com.good.gd.zwn.mjbm.lqox(uuid);
        com.good.gd.kloes.hbfhc.wxau("ContentValues", "Datapoint id for historical event payload: " + uuid);
        jSONObject2.put("appevents", jSONArray);
        jSONObject2.put("version", str);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("appbatch", jSONObject2);
        JSONArray jSONArray2 = new JSONArray();
        jSONArray2.put(jSONObject3);
        return jSONArray2;
    }
}
