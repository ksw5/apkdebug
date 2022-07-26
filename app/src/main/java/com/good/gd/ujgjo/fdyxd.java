package com.good.gd.ujgjo;

import com.good.gd.ovnkx.mjbm;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class fdyxd implements mjbm.pmoiy {
    private final StringBuilder dbjc = new StringBuilder();
    final /* synthetic */ yfdke jwxax;
    final /* synthetic */ String qkduk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fdyxd(yfdke yfdkeVar, String str) {
        this.jwxax = yfdkeVar;
        this.qkduk = str;
    }

    @Override // com.good.gd.ovnkx.mjbm.pmoiy
    public String dbjc(com.good.gd.dvql.hbfhc hbfhcVar) throws JSONException {
        Long qkduk;
        if (hbfhcVar == null || hbfhcVar.dbjc() == null) {
            return null;
        }
        if (this.qkduk.equals("historical")) {
            JSONObject dbjc = hbfhcVar.dbjc();
            if (dbjc.optString("label").equals("FI") && dbjc.optLong("start", -1L) == -1) {
                qkduk = this.jwxax.qkduk(dbjc.optString("label"));
                dbjc.put("start", qkduk);
            }
        }
        StringBuilder sb = this.dbjc;
        sb.delete(0, sb.length());
        this.dbjc.append(hbfhcVar.dbjc());
        StringBuilder sb2 = this.dbjc;
        if (sb2.charAt(sb2.length() - 1) != '\n') {
            this.dbjc.append('\n');
        }
        return this.dbjc.toString();
    }
}
