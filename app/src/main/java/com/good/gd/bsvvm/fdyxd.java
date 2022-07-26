package com.good.gd.bsvvm;

import android.content.Context;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class fdyxd extends com.good.gd.whhmi.aqdzk {
    public fdyxd(com.blackberry.bis.core.pmoiy pmoiyVar) {
        super(pmoiyVar);
    }

    @Override // com.good.gd.whhmi.aqdzk
    protected String dbjc() {
        return "N";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.whhmi.aqdzk
    public JSONObject dbjc(Context context, JSONObject jSONObject) throws JSONException {
        String odlf;
        String string = jSONObject != null ? jSONObject.getString("bbdsdkversion") : null;
        com.good.gd.kloes.hbfhc.wxau("ContentValues", "Create POST network request header section.");
        JSONObject dbjc = super.dbjc(context, jSONObject);
        if (TextUtils.isEmpty(string)) {
            string = ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).jsgtu();
        }
        dbjc.put("bbdsdkver", string);
        if (((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).kwm() && (odlf = ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).odlf()) != null && true != odlf.trim().isEmpty()) {
            dbjc.put("contid", odlf);
        }
        return dbjc;
    }
}
