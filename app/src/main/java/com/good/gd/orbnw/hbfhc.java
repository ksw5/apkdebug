package com.good.gd.orbnw;

import com.blackberry.analytics.analyticsengine.fdyxd;
import com.blackberry.bis.core.pmoiy;
import com.good.gd.whhmi.yfdke;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc extends com.good.gd.hdyaf.hbfhc {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.hdyaf.hbfhc
    public JSONObject dbjc(JSONObject jSONObject, com.good.gd.cth.hbfhc hbfhcVar) throws JSONException {
        JSONObject dbjc = super.dbjc(jSONObject, hbfhcVar);
        String uxw = fdyxd.pqq().uxw();
        if (uxw != null && !uxw.trim().isEmpty()) {
            dbjc.put("ecoId", uxw);
        }
        return dbjc;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.hdyaf.hbfhc
    public JSONObject qkduk(JSONObject jSONObject, com.good.gd.cth.hbfhc hbfhcVar) throws JSONException {
        JSONObject qkduk = super.qkduk(jSONObject, hbfhcVar);
        String uxw = fdyxd.pqq().uxw();
        if (uxw != null && !uxw.trim().isEmpty()) {
            qkduk.put("ecoId", uxw);
        }
        return qkduk;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.hdyaf.hbfhc
    public JSONObject dbjc() throws JSONException {
        String odlf;
        pmoiy dbjc = com.blackberry.bis.core.hbfhc.dbjc();
        JSONObject dbjc2 = super.dbjc();
        fdyxd fdyxdVar = (fdyxd) dbjc;
        String iulf = fdyxdVar.iulf();
        if (iulf != null && !iulf.trim().isEmpty()) {
            dbjc2.put("sn", iulf);
        }
        dbjc2.put("instanceId", com.good.gd.ovnkx.hbfhc.qkduk().dbjc());
        if (fdyxdVar.kwm() && (odlf = fdyxdVar.odlf()) != null && true != odlf.trim().isEmpty()) {
            dbjc2.put("containerId", odlf);
        }
        String jcpqe = fdyxdVar.jcpqe();
        if (jcpqe != null && !jcpqe.trim().isEmpty()) {
            dbjc2.put("appId", jcpqe);
        }
        String mloj = fdyxdVar.mloj();
        if (true != yfdke.qkduk(mloj)) {
            dbjc2.put("uemUserGuid", mloj);
        }
        String wuird = fdyxdVar.wuird();
        if (true != yfdke.qkduk(wuird)) {
            dbjc2.put("clientId", wuird);
        }
        return dbjc2;
    }
}
