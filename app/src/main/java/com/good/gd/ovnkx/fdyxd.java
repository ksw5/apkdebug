package com.good.gd.ovnkx;

import android.content.Context;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class fdyxd {
    public JSONObject dbjc(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            com.good.gd.whhmi.pmoiy wpejt = com.blackberry.bis.core.yfdke.wpejt();
            String dbjc = wpejt.dbjc("timezone");
            String dbjc2 = wpejt.dbjc("currentOsVersion");
            String dbjc3 = wpejt.dbjc("appVersion");
            if (dbjc == null || dbjc.trim().isEmpty()) {
                dbjc = TimeZone.getDefault().getID();
            }
            jSONObject.put("tz", dbjc);
            if (dbjc2 == null || dbjc2.trim().isEmpty()) {
                dbjc2 = com.blackberry.bis.core.aqdzk.jwxax();
            }
            jSONObject.put("osversion", dbjc2);
            if (dbjc3 == null || dbjc3.trim().isEmpty()) {
                dbjc3 = com.blackberry.bis.core.aqdzk.dbjc(context);
            }
            jSONObject.put("appVersion", dbjc3);
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}
