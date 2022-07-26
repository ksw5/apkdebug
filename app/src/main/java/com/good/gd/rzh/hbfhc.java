package com.good.gd.rzh;

import androidx.core.view.PointerIconCompat;
import com.blackberry.bis.core.yfdke;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.zwn.aqdzk;
import com.good.gd.zwn.mjbm;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class hbfhc {
    private int wxau;
    private aqdzk ztwf;
    private String dbjc = hbfhc.class.getSimpleName();
    private final HashMap<String, String> qkduk = new HashMap<>();
    private com.good.gd.ghhwi.hbfhc jwxax = null;

    public void dbjc(aqdzk aqdzkVar) {
        this.ztwf = aqdzkVar;
        if (yfdke.vfle() != null) {
            com.good.gd.jnupj.hbfhc hbfhcVar = new com.good.gd.jnupj.hbfhc();
            this.jwxax = hbfhcVar;
            hbfhcVar.dbjc(30000);
            if (!this.qkduk.isEmpty()) {
                this.qkduk.clear();
            }
            this.wxau = 1;
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[WAN IP] Preparing network request headers.");
            this.qkduk.put("Accept", "application/json; charset=utf-8");
            this.qkduk.put("X-BBRY-Correlation-ID", mjbm.jwxax());
            dbjc(false);
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "START_GET_WAN_IP - network request: " + this.qkduk.get("X-BBRY-Correlation-ID"));
            return;
        }
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.rzh.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0027hbfhc implements com.good.gd.bmuat.hbfhc {

        /* renamed from: com.good.gd.rzh.hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0028hbfhc implements Runnable {
            final /* synthetic */ String dbjc;

            RunnableC0028hbfhc(String str) {
                this.dbjc = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (hbfhc.this.qkduk != null) {
                    hbfhc.this.qkduk.put(AUTH.WWW_AUTH_RESP, "BBRY-JWT-TOKEN " + this.dbjc);
                }
                hbfhc.this.dbjc();
            }
        }

        /* renamed from: com.good.gd.rzh.hbfhc$hbfhc$yfdke */
        /* loaded from: classes.dex */
        class yfdke implements Runnable {
            final /* synthetic */ String dbjc;

            yfdke(String str) {
                this.dbjc = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                hbfhc.this.dbjc(PointerIconCompat.TYPE_VERTICAL_TEXT, this.dbjc);
            }
        }

        C0027hbfhc() {
        }

        @Override // com.good.gd.bmuat.hbfhc
        public void dbjc(String str) {
            com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, "[WAN IP] EID Token received successfully for the network request.");
            com.good.gd.idl.hbfhc.pqq().jsgtu().execute(new RunnableC0028hbfhc(str));
        }

        @Override // com.good.gd.bmuat.hbfhc
        public void dbjc(String str, int i) {
            com.good.gd.kloes.hbfhc.ztwf(hbfhc.this.dbjc, "[WAN IP] EID Token not received for the network request.");
            com.good.gd.idl.hbfhc.pqq().jsgtu().execute(new yfdke(str));
        }
    }

    private void dbjc(boolean z) {
        com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", "Analytics.Events", "", z, new C0027hbfhc());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc() {
        JSONObject jSONObject;
        String str = this.qkduk.get("X-BBRY-Correlation-ID");
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "GET WAN IP Retry [" + this.wxau + "/3] : " + str);
        try {
            String str2 = null;
            ((com.good.gd.jnupj.hbfhc) this.jwxax).dbjc(3, this.qkduk, null);
            String dbjc = ((com.good.gd.jnupj.hbfhc) this.jwxax).dbjc();
            int qkduk = ((com.good.gd.jnupj.hbfhc) this.jwxax).qkduk();
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, "Get WAN IP Network Response Headers: ");
            mjbm.dbjc(((com.good.gd.jnupj.hbfhc) this.jwxax).jwxax());
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format(Locale.US, "END_GET_WAN_IP - network request: %s completed with response code: %d (%s)", str, Integer.valueOf(qkduk), mjbm.qkduk(qkduk)));
            if (200 == qkduk) {
                try {
                    Object nextValue = new JSONTokener(dbjc).nextValue();
                    if (nextValue instanceof JSONObject) {
                        jSONObject = new JSONObject(dbjc);
                    } else if (!(nextValue instanceof JSONArray)) {
                        jSONObject = null;
                    } else {
                        jSONObject = new JSONArray(dbjc).getJSONObject(0);
                    }
                    if (jSONObject != null) {
                        str2 = jSONObject.optString("ip");
                    }
                } catch (JSONException e) {
                }
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "END_GET_WAN_IP - All retries completed.");
                aqdzk aqdzkVar = this.ztwf;
                if (aqdzkVar == null) {
                    return;
                }
                aqdzkVar.dbjc(true, qkduk, str2);
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format(Locale.US, "END_GET_WAN_IP - Failed with response: %s", dbjc));
            dbjc(qkduk, dbjc);
        } catch (SocketException e2) {
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "WAN IP request " + str + " is failed: " + e2.getLocalizedMessage());
            aqdzk aqdzkVar2 = this.ztwf;
            if (aqdzkVar2 == null) {
                return;
            }
            aqdzkVar2.dbjc(false, 1021, "WAN IP request is failed: " + e2.getLocalizedMessage());
        } catch (IOException e3) {
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "WAN IP request " + str + " is failed: " + e3.getLocalizedMessage());
            dbjc(1000, "WAN IP request is failed: " + e3.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(int i, String str) {
        String str2 = this.qkduk.get("X-BBRY-Correlation-ID");
        int i2 = this.wxau;
        if (i2 >= 3) {
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "END_GET_WAN_IP - All retries completed for network request: " + str2);
            com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "END_GET_WAN_IP - All retries completed.");
            aqdzk aqdzkVar = this.ztwf;
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, PointerIconCompat.TYPE_GRAB, "All retries failed for get WAN IP request.");
            return;
        }
        this.wxau = i2 + 1;
        switch (i) {
            case 400:
                String str3 = this.qkduk.get(AUTH.WWW_AUTH_RESP);
                if (str3 != null && !str3.isEmpty()) {
                    dbjc(i);
                    dbjc();
                    return;
                }
                dbjc(false);
                return;
            case 401:
                dbjc(i);
                com.good.gd.nxoj.yfdke.dbjc().dbjc("b13faba7-efdd-45b6-990f-21c00242f38b", "Analytics.Events", "");
                dbjc(true);
                return;
            case HttpStatus.SC_NOT_FOUND /* 404 */:
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format(Locale.US, "END_GET_WAN_IP - All retries completed for network request: %s completed with response %d (%s)", str2, Integer.valueOf(i), mjbm.qkduk(i)));
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "END_GET_WAN_IP - All retries completed");
                aqdzk aqdzkVar2 = this.ztwf;
                if (aqdzkVar2 == null) {
                    return;
                }
                aqdzkVar2.dbjc(false, i, str);
                return;
            case PointerIconCompat.TYPE_VERTICAL_TEXT /* 1009 */:
            case 1021:
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, String.format(Locale.US, "END_GET_WAN_IP - All retries completed for network request: %s completed with response %d (%s)", str2, Integer.valueOf(i), mjbm.qkduk(i)));
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "END_GET_WAN_IP - All retries completed");
                this.ztwf.dbjc(false, i, str);
                return;
            default:
                dbjc(i);
                dbjc();
                return;
        }
    }

    private void dbjc(int i) {
        String str = this.qkduk.get("X-BBRY-Correlation-ID");
        String jwxax = mjbm.jwxax();
        if (i != 400) {
            this.qkduk.put("X-BBRY-Correlation-ID", jwxax);
        } else {
            this.qkduk.put("Accept", "application/json; charset=utf-8");
            this.qkduk.put("X-BBRY-Correlation-ID", jwxax);
        }
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[WAN IP] Updated headers for GET WAN IP address network request: " + str + " -> " + jwxax);
    }
}
