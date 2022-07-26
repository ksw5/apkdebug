package com.good.gd.yokds;

import com.good.gd.ndkproxy.PasswordType;
import com.good.gd.profileoverride.GDBISProfileOverride;
import com.good.gd.zwn.mjbm;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class yfdke {
    private static yfdke wxau;
    private final Class dbjc = yfdke.class;
    private ExecutorService jwxax = Executors.newSingleThreadExecutor();
    private GDBISProfileOverride qkduk = new GDBISProfileOverride("com.blackberry.entitlement.geoanalytics");

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ String qkduk;
        final /* synthetic */ JSONObject wxau;
        final /* synthetic */ com.good.gd.yokds.hbfhc ztwf;

        hbfhc(String str, String str2, String str3, JSONObject jSONObject, com.good.gd.yokds.hbfhc hbfhcVar) {
            this.dbjc = str;
            this.qkduk = str2;
            this.jwxax = str3;
            this.wxau = jSONObject;
            this.ztwf = hbfhcVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("mtype", this.dbjc);
                jSONObject.put("trackingID", this.qkduk);
                jSONObject.put("entityInstanceId", this.jwxax);
                jSONObject.put("value", this.wxau);
                jSONArray.put(jSONObject);
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.dbjc, String.format(Locale.US, "[Assign Profile] Initiated request with tracking ID: %s", this.qkduk));
                com.good.gd.kloes.ehnkx.qkduk(yfdke.this.dbjc, "[Assign Profile] Calling the assign profile API with data : \n" + jSONArray.toString());
                GDBISProfileOverride gDBISProfileOverride = yfdke.this.qkduk;
                yfdke yfdkeVar = yfdke.this;
                String str = this.dbjc;
                com.good.gd.yokds.hbfhc hbfhcVar = this.ztwf;
                if (yfdkeVar != null) {
                    gDBISProfileOverride.setProfile(jSONArray, new ehnkx(yfdkeVar, str, hbfhcVar));
                    return;
                }
                throw null;
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.qkduk(yfdke.this.dbjc, "[Assign Profile] Unable to call BBD SDK API setProfile " + e.getLocalizedMessage());
                com.good.gd.yokds.hbfhc hbfhcVar2 = this.ztwf;
                if (hbfhcVar2 == null) {
                    return;
                }
                hbfhcVar2.dbjc(false, 2, this.qkduk);
            }
        }
    }

    /* renamed from: com.good.gd.yokds.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0037yfdke implements Runnable {
        final /* synthetic */ List dbjc;
        final /* synthetic */ com.good.gd.yokds.hbfhc qkduk;

        RunnableC0037yfdke(List list, com.good.gd.yokds.hbfhc hbfhcVar) {
            this.dbjc = list;
            this.qkduk = hbfhcVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONArray jSONArray = new JSONArray();
                for (String str : this.dbjc) {
                    JSONObject jSONObject = new JSONObject();
                    String dbjc = mjbm.dbjc(8);
                    jSONObject.put("mtype", str);
                    jSONObject.put("trackingID", dbjc);
                    jSONArray.put(jSONObject);
                }
                com.good.gd.kloes.ehnkx.qkduk(yfdke.this.dbjc, "[Revert Profile] Calling the revert profile API with data : \n" + jSONArray.toString());
                GDBISProfileOverride gDBISProfileOverride = yfdke.this.qkduk;
                yfdke yfdkeVar = yfdke.this;
                com.good.gd.yokds.hbfhc hbfhcVar = this.qkduk;
                if (yfdkeVar != null) {
                    gDBISProfileOverride.revertProfile(jSONArray, new fdyxd(yfdkeVar, hbfhcVar));
                    return;
                }
                throw null;
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.qkduk(yfdke.this.dbjc, "[Revert Profile] Unable to call BBD SDK API revertProfile " + e.getLocalizedMessage());
                com.good.gd.yokds.hbfhc hbfhcVar2 = this.qkduk;
                if (hbfhcVar2 == null) {
                    return;
                }
                hbfhcVar2.dbjc(false, 2, null);
            }
        }
    }

    private yfdke() {
        com.blackberry.analytics.analyticsengine.fdyxd.pqq().mvf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ String dbjc(yfdke yfdkeVar, int i) {
        if (yfdkeVar != null) {
            switch (i) {
                case 0:
                    return "SUCCESS";
                case 1:
                    return "DOES_NOT_HAVE_ENTITLEMENT";
                case 2:
                    return "INVALID_JSON";
                case 3:
                    return "COLD_LOCKED";
                default:
                    return PasswordType.SMNOTYETSET;
            }
        }
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void qkduk() {
        ExecutorService executorService = this.jwxax;
        if (executorService == null || executorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.dbjc, "[Profile Override] Executor Service is either null or shutdown. Restarting!!");
            this.jwxax = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public static yfdke dbjc() {
        if (wxau == null) {
            synchronized (yfdke.class) {
                if (wxau == null) {
                    wxau = new yfdke();
                }
            }
        }
        return wxau;
    }

    public void dbjc(String str, String str2, String str3, JSONObject jSONObject, com.good.gd.yokds.hbfhc hbfhcVar) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[Assign Profile] Request not possible as Policy Type is required.");
            if (hbfhcVar == null) {
                return;
            }
            hbfhcVar.dbjc(false, 1040, str2);
        } else if (com.good.gd.whhmi.yfdke.qkduk(str3)) {
            com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[Assign Profile] Request not possible as Entity Instance Id is required.");
            if (hbfhcVar == null) {
                return;
            }
            hbfhcVar.dbjc(false, 1041, str2);
        } else {
            if (jSONObject == null || jSONObject.length() < 0) {
                com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[Assign Profile] Request not possible as Profile Override settings is required.");
                if (hbfhcVar == null) {
                    return;
                }
                hbfhcVar.dbjc(false, 1042, str2);
                return;
            }
            qkduk();
            this.jwxax.execute(new hbfhc(str, str2, str3, jSONObject, hbfhcVar));
        }
    }

    public void dbjc(List<String> list, com.good.gd.yokds.hbfhc hbfhcVar) {
        if (list != null && !list.isEmpty()) {
            if (com.blackberry.bis.core.yfdke.ztwf() != null) {
                com.good.gd.kofoa.hbfhc.wxau().jwxax();
                qkduk();
                this.jwxax.execute(new RunnableC0037yfdke(list, hbfhcVar));
                return;
            }
            throw null;
        }
        com.good.gd.kloes.hbfhc.dbjc(this.dbjc, "[Revert Profile] No need to revert the profile as any assigned override profile not found.");
        if (hbfhcVar == null) {
            return;
        }
        hbfhcVar.dbjc(false, 1040, null);
    }

    public String dbjc(String str) {
        if (!com.good.gd.whhmi.yfdke.qkduk(str)) {
            return str.equals("com.blackberry.mdm.common.entity.profile.DynamicsSecurityOverrideProfile") ? "SecurityPolicy" : "";
        }
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[Assign Profile] Entity Definition Id is required.");
        return "";
    }
}
