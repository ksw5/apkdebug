package com.good.gd.ovnkx;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class orlrx extends fdyxd {
    private final Class dbjc = orlrx.class;

    @Override // com.good.gd.ovnkx.fdyxd
    public JSONObject dbjc(Context context) {
        JSONObject dbjc = super.dbjc(context);
        try {
            String dbjc2 = com.blackberry.bis.core.yfdke.wpejt().dbjc("bbdSDKVersion");
            if (dbjc2 == null || dbjc2.trim().isEmpty()) {
                dbjc2 = ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).jsgtu();
            }
            dbjc.put("bbdsdkversion", dbjc2);
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "Unable to prepare batched header: " + e.getLocalizedMessage());
        }
        return dbjc;
    }
}
