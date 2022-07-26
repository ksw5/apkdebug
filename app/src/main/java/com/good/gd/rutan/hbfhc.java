package com.good.gd.rutan;

import com.blackberry.analytics.analyticsengine.fdyxd;
import com.good.gd.kloes.ehnkx;
import com.good.gd.zwn.aqdzk;
import com.good.gd.zwn.mjbm;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private static hbfhc jsgtu;
    private String dbjc;
    private String qkduk;
    private AtomicBoolean jwxax = new AtomicBoolean(false);
    private AtomicBoolean wxau = new AtomicBoolean(false);
    private boolean ztwf = false;
    private int lqox = 443;
    private long liflu = 0;
    private ScheduledExecutorService jcpqe = Executors.newSingleThreadScheduledExecutor();
    private aqdzk tlske = new yfdke();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.rutan.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class RunnableC0026hbfhc implements Runnable {
        RunnableC0026hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SDE] Start BIS service discovery endpoints network task.");
            new com.good.gd.hssa.hbfhc().dbjc(hbfhc.this.tlske);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements aqdzk {
        yfdke() {
        }

        @Override // com.good.gd.zwn.aqdzk
        public void dbjc(boolean z, int i, String str) {
            if (!z || (200 != i && 304 != i)) {
                hbfhc.this.jwxax.set(false);
            } else if (!com.good.gd.whhmi.yfdke.qkduk(str)) {
                com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SDE] Handle BIS service discovery endpoints response.");
                ehnkx.qkduk("hbfhc", "[SDE] BIS service discovery endpoints response: " + str);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    JSONArray optJSONArray = jSONObject.isNull("services") ? null : jSONObject.optJSONArray("services");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        int length = optJSONArray.length();
                        for (int i2 = 0; i2 < length; i2++) {
                            JSONObject optJSONObject = optJSONArray.isNull(i2) ? null : optJSONArray.optJSONObject(i2);
                            if (optJSONObject != null && optJSONObject.length() > 0) {
                                String optString = optJSONObject.isNull("id") ? null : optJSONObject.optString("id");
                                if (!com.good.gd.whhmi.yfdke.qkduk(optString)) {
                                    String optString2 = optJSONObject.isNull("url") ? null : optJSONObject.optString("url");
                                    if (!com.good.gd.whhmi.yfdke.qkduk(optString2)) {
                                        if ("bis.riskassessment".equals(optString)) {
                                            hbfhc.tlske().qkduk(optString2);
                                        } else if ("bis.receiver".equals(optString)) {
                                            if (!((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ofrex()) {
                                                mjbm.jwxax(optString2);
                                                com.good.gd.fwwhw.hbfhc.dbjc(optString2);
                                            }
                                        } else {
                                            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Unknown endpoint JSON Object with Service ID: " + optString);
                                        }
                                    } else {
                                        com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Service URL is not found for given Service ID: " + optString);
                                    }
                                } else {
                                    com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Service ID is not found for current inner services JSON object.");
                                }
                            } else {
                                com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Inner endpoint JSON object is either null or empty.");
                            }
                        }
                    } else {
                        com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Invalid response, services JSON array is null or empty.");
                    }
                } catch (JSONException e) {
                    com.good.gd.kloes.hbfhc.qkduk("hbfhc", "[SDE] Error is parsing response: " + e.getLocalizedMessage());
                }
                hbfhc tlske = hbfhc.tlske();
                if (tlske.dbjc("SRARequest") && tlske.dbjc("WANIPRequest")) {
                    com.good.gd.kloes.hbfhc.jwxax("hbfhc", "[SDE] BIS service discovery successfully parsed required endpoints.");
                    hbfhc.this.jwxax.set(true);
                } else {
                    com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] BIS service discovery failed to parse required endpoints.");
                    hbfhc.this.jwxax.set(false);
                }
            } else {
                com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Service discovery response is null or empty.");
                hbfhc.this.jwxax.set(false);
            }
            if (!hbfhc.this.jwxax.get()) {
                hbfhc.this.liflu = System.currentTimeMillis();
            } else {
                hbfhc.this.liflu = 0L;
            }
            hbfhc.this.ztwf = true;
            com.good.gd.idl.hbfhc.pqq().muee();
            hbfhc.this.wxau.set(false);
        }
    }

    private hbfhc() {
    }

    public static hbfhc tlske() {
        if (jsgtu == null) {
            synchronized (hbfhc.class) {
                if (jsgtu == null) {
                    jsgtu = new hbfhc();
                }
            }
        }
        return jsgtu;
    }

    public boolean jcpqe() {
        return this.ztwf;
    }

    public void liflu() {
        if (!com.blackberry.bis.core.aqdzk.wxau() || !com.good.gd.idl.hbfhc.pqq().odlf()) {
            return;
        }
        if (tlske().ztwf && this.jwxax.get()) {
            com.good.gd.idl.hbfhc.pqq().muee();
        } else {
            tlske().qkduk();
        }
    }

    public ScheduledExecutorService lqox() {
        ScheduledExecutorService scheduledExecutorService = this.jcpqe;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ehnkx.dbjc("hbfhc", "SDE executor service is either null or shutdown. Restarting!!");
            this.jcpqe = Executors.newSingleThreadScheduledExecutor();
        }
        return this.jcpqe;
    }

    public int wxau() {
        return this.lqox;
    }

    public String ztwf() {
        String str;
        synchronized (this) {
            str = this.dbjc;
        }
        return str;
    }

    public String jwxax() {
        if (com.good.gd.whhmi.yfdke.qkduk(this.qkduk)) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SDE] Continuing with default server details.");
            return "discovery.bis.blackberry.com";
        }
        return this.qkduk;
    }

    public synchronized void qkduk() {
        if (this.wxau.get()) {
            com.good.gd.kloes.hbfhc.jwxax("hbfhc", "[SDE] BIS service discovery endpoints network request is already in progress.");
        } else if (!((fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).qkduk()) {
        } else {
            this.wxau.set(true);
            ScheduledExecutorService scheduledExecutorService = this.jcpqe;
            if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
                ehnkx.dbjc("hbfhc", "SDE executor service is either null or shutdown. Restarting!!");
                this.jcpqe = Executors.newSingleThreadScheduledExecutor();
            }
            this.jcpqe.execute(new RunnableC0026hbfhc());
        }
    }

    public void dbjc(String str, int i) {
        com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SDE] Setting base URL and Port.");
        this.qkduk = str;
        this.lqox = i;
    }

    public boolean dbjc(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] Proper request type is required to get if BIS SDE discovered.");
            return false;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1234228627) {
            if (hashCode != 976604123) {
                if (hashCode == 1010451748 && str.equals("WANIPRequest")) {
                    c = 1;
                }
            } else if (str.equals("SDERequest")) {
                c = 2;
            }
        } else if (str.equals("SRARequest")) {
            c = 0;
        }
        if (c != 0) {
            if (c == 1) {
                return true;
            }
            if (c == 2) {
                return this.jwxax.get();
            }
            return false;
        } else if (!com.good.gd.whhmi.yfdke.qkduk(this.dbjc)) {
            com.good.gd.kloes.hbfhc.wxau("hbfhc", "[SDE] SRA endpoint is available.");
            return true;
        } else {
            com.good.gd.kloes.hbfhc.ztwf("hbfhc", "[SDE] SRA endpoint is not available.");
            return false;
        }
    }

    public void qkduk(String str) {
        synchronized (this) {
            this.dbjc = str;
        }
    }

    public void dbjc(boolean z) {
        this.ztwf = z;
    }

    public void dbjc() {
        if (!com.good.gd.whhmi.yfdke.liflu() || this.jwxax.get()) {
            return;
        }
        if (!(this.liflu > 0 && 180000 <= System.currentTimeMillis() - this.liflu)) {
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("hbfhc", String.format(Locale.US, "[SDE] Triggering SDE as SDE is not discovered and threshold time is expired.", new Object[0]));
        qkduk();
    }
}
