package com.good.gd.bsvvm;

import android.content.Context;
import com.good.gd.whhmi.orlrx;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class pmoiy extends orlrx {
    public pmoiy(com.blackberry.bis.core.pmoiy pmoiyVar) {
        super(pmoiyVar);
    }

    @Override // com.good.gd.whhmi.orlrx
    protected String dbjc() {
        return "bb.analytics.bbd";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.whhmi.orlrx
    public JSONObject dbjc(Context context, JSONObject jSONObject, JSONArray jSONArray, JSONArray jSONArray2) throws JSONException {
        String odlf;
        JSONObject dbjc = super.dbjc(context, jSONObject, jSONArray, jSONArray2);
        String string = jSONObject.getString("bbdsdkversion");
        if (string == null || string.trim().isEmpty()) {
            string = ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).jsgtu();
        }
        com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc;
        boolean kwm = fdyxdVar.kwm();
        String tlske = fdyxdVar.tlske();
        String ugfcv = fdyxdVar.ugfcv();
        String wuird = fdyxdVar.wuird();
        if (com.good.gd.idl.hbfhc.pqq().odlf() || kwm) {
            String uxw = fdyxdVar.uxw();
            String mloj = fdyxdVar.mloj();
            if (uxw != null && true != uxw.trim().isEmpty()) {
                dbjc.put("uem_eeco_id", uxw);
            }
            if (mloj != null && true != mloj.trim().isEmpty()) {
                dbjc.put("uem_user_guid", mloj);
            }
        }
        if (ugfcv != null && true != ugfcv.trim().isEmpty()) {
            dbjc.put("uem_tenant_id", ugfcv);
        }
        if (tlske != null && true != tlske.trim().isEmpty()) {
            dbjc.put("bbd_device_id", tlske);
        }
        if (wuird != null && true != wuird.trim().isEmpty()) {
            dbjc.put("bbd_shared_user_id", wuird);
        }
        dbjc.put("bbd_ver", string);
        if (kwm && (odlf = ((com.blackberry.analytics.analyticsengine.fdyxd) this.dbjc).odlf()) != null && true != odlf.trim().isEmpty()) {
            dbjc.put("container_id", odlf);
        }
        return dbjc;
    }
}
