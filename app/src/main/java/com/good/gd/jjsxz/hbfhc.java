package com.good.gd.jjsxz;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.SIS.BISPreUnlockStorage.BISPreUnlockStorageManager;
import com.blackberry.bis.core.yfdke;
import com.good.gd.ovnkx.mjbm;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private static JSONArray dbjc() {
        File qkduk;
        File qkduk2;
        String[] split;
        com.good.gd.ujgjo.hbfhc dbjc = yfdke.wuird().dbjc(BlackberryAnalyticsCommon.rynix().jwxax());
        JSONArray jSONArray = null;
        if (dbjc != null) {
            if (new File(dbjc.dbjc(), "ndk_crash_timestamp_file.txt").exists() && (qkduk = dbjc.qkduk()) != null && qkduk.exists()) {
                String qkduk3 = mjbm.qkduk(qkduk.getAbsolutePath());
                if (qkduk3 != null && true != qkduk3.trim().isEmpty()) {
                    jSONArray = new JSONArray();
                    for (String str : Pattern.compile(",").split(qkduk3)) {
                        if (str != null && true != str.trim().isEmpty()) {
                            jSONArray.put(String.valueOf(Long.parseLong(str) / 1000));
                        }
                    }
                }
                com.good.gd.ujgjo.hbfhc dbjc2 = yfdke.wuird().dbjc(BlackberryAnalyticsCommon.rynix().jwxax());
                if (dbjc2 != null && (qkduk2 = dbjc2.qkduk()) != null && qkduk2.exists()) {
                    qkduk2.delete();
                }
            }
            return jSONArray;
        }
        throw null;
    }

    public static void qkduk() {
        com.good.gd.whhmi.hbfhc wxau = com.good.gd.whhmi.hbfhc.wxau();
        BISPreUnlockStorageManager sbesx = yfdke.sbesx();
        JSONObject jSONObject = new JSONObject();
        try {
            if (wxau.qkduk("Analytics_Status")) {
                jSONObject.put("AnalyticsEntitlementStatus", wxau.wxau("Analytics_Status"));
                wxau.jwxax("Analytics_Status");
            }
            if (wxau.qkduk("Geo_Location_Status")) {
                jSONObject.put("BISEntitlementStatus", wxau.wxau("Geo_Location_Status"));
                wxau.jwxax("Geo_Location_Status");
            }
            if (wxau.qkduk("location_consent_option")) {
                jSONObject.put("BISConsentStatus", wxau.dbjc("location_consent_option", -1));
                wxau.jwxax("location_consent_option");
            }
            if (wxau.qkduk("is_location_permission_granted")) {
                jSONObject.put("isLocationPermissionGranted", wxau.wxau("is_location_permission_granted"));
                wxau.jwxax("is_location_permission_granted");
            }
            if (wxau.qkduk("is_never_ask_option_selected")) {
                jSONObject.put("isNeverAskAgainSelected", wxau.wxau("is_never_ask_option_selected"));
                wxau.jwxax("is_never_ask_option_selected");
            }
            if (wxau.qkduk("is_deny_option_selected_explictly")) {
                jSONObject.put("isLocationDenyOptionSelectedExplicitLy", wxau.wxau("is_deny_option_selected_explictly"));
                wxau.jwxax("is_deny_option_selected_explictly");
            }
            JSONArray jSONArray = null;
            com.good.gd.whhmi.hbfhc wxau2 = com.good.gd.whhmi.hbfhc.wxau();
            ArrayDeque<String> jwxax = wxau2.jwxax();
            if (jwxax != null && !jwxax.isEmpty()) {
                jSONArray = new JSONArray();
                Iterator<String> it = jwxax.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (true != com.good.gd.whhmi.yfdke.qkduk(next)) {
                        jSONArray.put(next);
                    }
                }
                wxau2.jwxax("Crash_Array");
            }
            if (jSONArray != null && !jSONArray.toString().equals("[]")) {
                jSONObject.put("crashArray", jSONArray);
            }
            JSONArray dbjc = dbjc();
            if (dbjc != null && !dbjc.toString().equals("[]")) {
                jSONObject.put("nativeCrashArray", dbjc);
            }
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "Unable to perform metadata-2 migrations,");
        }
        if (!jSONObject.toString().equals("{}")) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "Metadata-2 migration result: " + sbesx.writeContentToPreUnlockStorage(jSONObject.toString()));
        }
    }
}
