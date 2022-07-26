package com.good.gd.zwn;

import androidx.core.view.PointerIconCompat;
import com.good.gd.zwn.mjbm;
import com.good.gd.zwn.vzw;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ooowe {
    private final com.good.gd.ghhwi.hbfhc dbjc;
    private Runnable jcpqe;
    private final com.good.gd.oqpvt.yfdke jwxax;
    private Runnable liflu;
    private com.good.gd.ghhwi.yfdke mvf;
    private boolean odlf;
    private pmoiy qkduk;
    private final Class tlske;
    private boolean wxau;
    private final AtomicBoolean ztwf = new AtomicBoolean(false);
    private final AtomicInteger lqox = new AtomicInteger(2);
    private ScheduledExecutorService jsgtu = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService wuird = Executors.newScheduledThreadPool(1);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        final /* synthetic */ aqdzk jwxax;
        final /* synthetic */ byte[] wxau;
        final aqdzk dbjc = new C0042hbfhc();
        final vzw.yfdke qkduk = new yfdke();

        /* renamed from: com.good.gd.zwn.ooowe$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0042hbfhc implements aqdzk {
            C0042hbfhc() {
            }

            @Override // com.good.gd.zwn.aqdzk
            public void dbjc(boolean z, int i, String str) {
                String lqox = mjbm.lqox();
                com.blackberry.bis.core.yfdke.bucpw().dbjc(ooowe.this.tlske, String.format("END_HISTORICAL_POST - Response for post historical events network request: %s with Response Code: %d (%s) & datapoint ID: %s", mjbm.ztwf(), Integer.valueOf(i), mjbm.qkduk(i), lqox));
                if (z) {
                    ooowe.this.ztwf.set(false);
                    ooowe.this.jcpqe = null;
                }
                aqdzk aqdzkVar = hbfhc.this.jwxax;
                if (aqdzkVar != null) {
                    if (!z) {
                        i = 1999;
                    }
                    aqdzkVar.dbjc(z, i, str);
                }
            }
        }

        /* loaded from: classes.dex */
        class yfdke implements vzw.yfdke {
            yfdke() {
            }

            @Override // com.good.gd.zwn.vzw.yfdke
            public void dbjc(boolean z, int i, String str) {
                com.good.gd.kloes.hbfhc.jwxax(ooowe.this.tlske, String.format("END_HISTORICAL_POST - All retries completed for historical events network request with datapoint ID: %s", mjbm.lqox()));
                if (true != z) {
                    com.good.gd.kloes.hbfhc.wxau(ooowe.this.tlske, "POST Historical Events Network Request Failed with Response: " + str);
                }
                ooowe.this.jcpqe = null;
                if (ooowe.this.mvf != null) {
                    if (z && com.good.gd.ovnkx.mjbm.jsgtu("historical") && com.good.gd.ovnkx.mjbm.jsgtu("appIntelligence")) {
                        ooowe.this.ztwf.set(false);
                        ooowe.this.lqox.set(2);
                        ooowe.this.mvf.qkduk(true);
                    } else if (true != z) {
                        ooowe.this.ztwf.set(false);
                        ooowe.this.lqox.set(2);
                        ooowe.this.mvf.qkduk(false);
                    }
                }
                ooowe.this.mvf();
            }
        }

        hbfhc(aqdzk aqdzkVar, byte[] bArr) {
            this.jwxax = aqdzkVar;
            this.wxau = bArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            ooowe.this.ztwf.set(true);
            com.good.gd.kloes.hbfhc.jwxax(ooowe.this.tlske, "Create Historical POST Network Request.");
            orlrx dbjc = com.blackberry.bis.core.yfdke.kwm().dbjc(this.dbjc, this.wxau, ooowe.this.dbjc, ooowe.this.jwxax, ooowe.this);
            ooowe.this.dbjc(dbjc);
            yjh glwwi = com.blackberry.bis.core.yfdke.glwwi();
            ScheduledExecutorService scheduledExecutorService = ooowe.this.wuird;
            vzw.yfdke yfdkeVar = this.qkduk;
            if (((com.good.gd.npgvd.ooowe) glwwi) != null) {
                new com.good.gd.npgvd.yjh(scheduledExecutorService, dbjc, yfdkeVar).dbjc();
                return;
            }
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        final /* synthetic */ com.good.gd.ovnkx.yfdke jwxax;
        final /* synthetic */ aqdzk wxau;
        final aqdzk dbjc = new hbfhc();
        final vzw.yfdke qkduk = new C0043yfdke();

        /* loaded from: classes.dex */
        class hbfhc implements aqdzk {
            hbfhc() {
            }

            @Override // com.good.gd.zwn.aqdzk
            public void dbjc(boolean z, int i, String str) {
                pmoiy pmoiyVar;
                com.good.gd.kloes.hbfhc.jwxax(ooowe.this.tlske, String.format("END_GET_CONFIG - Received Response for GET Config network request: %s with response code: %d (%s)", mjbm.wxau(), Integer.valueOf(i), mjbm.qkduk(i)));
                if (z) {
                    if (!com.good.gd.whhmi.yfdke.dbjc()) {
                        com.good.gd.kloes.hbfhc.dbjc(ooowe.this.tlske, "Configuration fetched but analytics got disabled. Avoiding to store configuration.");
                    } else {
                        com.good.gd.kloes.hbfhc.dbjc((Object) mjbm.hbfhc.lqox, "Create Configuration.");
                        try {
                            JSONObject jSONObject = new JSONObject(str);
                            JSONObject jSONObject2 = jSONObject.getJSONObject("getClientSettings");
                            JSONObject jSONObject3 = jSONObject.getJSONObject("uploadEventsSettings");
                            pmoiyVar = new pmoiy(jSONObject2.getInt("getInterval"), jSONObject2.getInt("randomizationWindow"), jSONObject2.optBoolean("userCorrelation", false), jSONObject3.getInt("uploadInterval"), jSONObject3.getInt("randomizationWindow"));
                        } catch (JSONException e) {
                            com.good.gd.kloes.hbfhc.wxau(mjbm.hbfhc.lqox, "Unable to parse the network configuration response: " + e.getLocalizedMessage());
                            pmoiyVar = null;
                        }
                        if (pmoiyVar == null || true == ooowe.this.qkduk.dbjc(pmoiyVar)) {
                            com.good.gd.kloes.hbfhc.dbjc(ooowe.this.tlske, "Configuration fetched isn't valid or same as local.");
                        } else {
                            ooowe.this.qkduk = pmoiyVar;
                            ooowe.this.qkduk.dbjc();
                            yfdke yfdkeVar = yfdke.this;
                            yfdkeVar.jwxax.dbjc(ooowe.this.qkduk);
                        }
                    }
                    ooowe.this.ztwf.set(false);
                    ooowe.this.liflu = null;
                }
                aqdzk aqdzkVar = yfdke.this.wxau;
                if (aqdzkVar != null) {
                    aqdzkVar.dbjc(z, z ? i : 1999, str);
                }
            }
        }

        /* renamed from: com.good.gd.zwn.ooowe$yfdke$yfdke  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0043yfdke implements vzw.yfdke {
            C0043yfdke() {
            }

            @Override // com.good.gd.zwn.vzw.yfdke
            public void dbjc(boolean z, int i, String str) {
                com.good.gd.kloes.hbfhc.jwxax(ooowe.this.tlske, String.format("END_GET_CONFIG - All retries completed for GET Config network request: %s", mjbm.wxau()));
                if (z) {
                    com.good.gd.kloes.ehnkx.qkduk(ooowe.this.tlske, "GET Network Request Response: " + str);
                } else {
                    com.good.gd.kloes.ehnkx.qkduk(ooowe.this.tlske, "GET Network Request Failed with Response: " + str);
                }
                if (ooowe.this.mvf != null) {
                    ooowe.this.mvf.dbjc(z);
                }
                ooowe.this.ztwf.set(false);
                ooowe.this.liflu = null;
                ooowe.this.lqox.set(2);
                ooowe.this.mvf();
            }
        }

        yfdke(com.good.gd.ovnkx.yfdke yfdkeVar, aqdzk aqdzkVar) {
            this.jwxax = yfdkeVar;
            this.wxau = aqdzkVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            ooowe.this.ztwf.set(true);
            com.good.gd.kloes.hbfhc.jwxax(ooowe.this.tlske, "Create GET Config Network Request.");
            fdyxd dbjc = com.blackberry.bis.core.yfdke.yrp().dbjc(this.dbjc, ooowe.this.dbjc, ooowe.this.jwxax, ooowe.this);
            ooowe.this.dbjc(dbjc);
            yjh glwwi = com.blackberry.bis.core.yfdke.glwwi();
            ScheduledExecutorService scheduledExecutorService = ooowe.this.wuird;
            vzw.yfdke yfdkeVar = this.qkduk;
            if (((com.good.gd.npgvd.ooowe) glwwi) != null) {
                new com.good.gd.npgvd.yjh(scheduledExecutorService, dbjc, yfdkeVar).dbjc();
                return;
            }
            throw null;
        }
    }

    public ooowe(com.good.gd.ovnkx.yfdke yfdkeVar, com.good.gd.oqpvt.yfdke yfdkeVar2, com.good.gd.oqpvt.hbfhc hbfhcVar) {
        Class<?> cls = getClass();
        this.tlske = cls;
        com.good.gd.kloes.ehnkx.qkduk(cls, "Creating Upload Manager...");
        this.qkduk = yfdkeVar.qkduk();
        this.jwxax = yfdkeVar2;
        yfdkeVar2.dbjc(hbfhcVar);
        com.good.gd.ghhwi.hbfhc dbjc = com.blackberry.bis.core.yfdke.vfle().dbjc();
        this.dbjc = dbjc;
        ((com.good.gd.jnupj.hbfhc) dbjc).dbjc(20000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mvf() {
        String str;
        if (true != this.ztwf.getAndSet(true)) {
            if (this.lqox.get() == 0) {
                str = "QUEUE_GET";
            } else {
                str = this.lqox.get() == 1 ? "QUEUE_POST" : "QUEUE_ANY";
            }
            com.good.gd.kloes.hbfhc.dbjc((Object) null, String.format(Locale.getDefault(), "Current Request State: [%s]", str));
            if (this.liflu != null && this.lqox.getAndSet(0) == 2) {
                odlf();
                this.jsgtu.execute(this.liflu);
            } else if (this.jcpqe != null && (this.lqox.getAndSet(1) == 2 || this.lqox.getAndSet(1) == 1)) {
                odlf();
                this.jsgtu.execute(this.jcpqe);
            } else {
                dbjc();
                this.ztwf.set(false);
            }
        }
    }

    private void odlf() {
        ScheduledExecutorService scheduledExecutorService = this.jsgtu;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.tlske, "Upload Manager Scheduler executor service is either null or shutdown. Restarting!!");
            this.jsgtu = Executors.newSingleThreadScheduledExecutor();
        }
        ScheduledExecutorService scheduledExecutorService2 = this.wuird;
        if (scheduledExecutorService2 == null || scheduledExecutorService2.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.tlske, "Upload Manager Network executor service is either null or shutdown. Restarting!!");
            this.wuird = Executors.newScheduledThreadPool(1);
        }
    }

    protected void dbjc() {
        throw null;
    }

    protected void dbjc(ehnkx ehnkxVar) {
        throw null;
    }

    public void jsgtu() {
        this.ztwf.set(false);
    }

    public void wuird() {
        com.good.gd.kloes.ehnkx.qkduk(this.tlske, "Shutdown Upload Manager.");
        this.jsgtu.shutdown();
        try {
            if (true == this.jsgtu.awaitTermination(200L, TimeUnit.MILLISECONDS)) {
                return;
            }
            this.jsgtu.shutdownNow();
        } catch (InterruptedException e) {
            this.jsgtu.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public boolean jcpqe() {
        return this.jwxax.tlske();
    }

    public int jwxax() {
        return this.qkduk.wxau();
    }

    public void liflu() {
        this.jwxax.mvf();
    }

    public long lqox() {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        pmoiy pmoiyVar = this.qkduk;
        return timeUnit.toMillis(pmoiyVar.ztwf + pmoiyVar.wxau);
    }

    public boolean tlske() {
        return this.wxau;
    }

    public int wxau() {
        return this.qkduk.qkduk();
    }

    public long ztwf() {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        pmoiy pmoiyVar = this.qkduk;
        return timeUnit.toMillis(pmoiyVar.qkduk + pmoiyVar.dbjc);
    }

    public void qkduk() {
        this.jwxax.odlf();
    }

    public void dbjc(boolean z) {
        this.wxau = z;
    }

    public void dbjc(com.good.gd.ghhwi.yfdke yfdkeVar) {
        if (true != this.odlf) {
            this.mvf = yfdkeVar;
            this.odlf = true;
        }
    }

    public void dbjc(byte[] bArr, aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.wxau(this.tlske, "Post method called.");
        if (true != this.wxau) {
            com.good.gd.kloes.hbfhc.wxau(this.tlske, "Upload Manager is not active to perform network tasks.");
            return;
        }
        pmoiy pmoiyVar = this.qkduk;
        if (pmoiyVar != null && true == pmoiyVar.ztwf()) {
            this.jcpqe = new hbfhc(aqdzkVar, bArr);
            mvf();
            return;
        }
        aqdzkVar.dbjc(false, 1002, "Invalid Configuration.");
    }

    public void dbjc(com.good.gd.ovnkx.yfdke yfdkeVar, aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.wxau(this.tlske, "GET Config.");
        if (true == com.good.gd.whhmi.yfdke.dbjc() && true == ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).vfle().booleanValue()) {
            if (true != this.jwxax.tlske()) {
                com.good.gd.kloes.hbfhc.jwxax(this.tlske, "Internet connection not available.");
                aqdzkVar.dbjc(false, PointerIconCompat.TYPE_HELP, "network is turned off, can't perform operation");
                return;
            } else if (true != this.wxau) {
                com.good.gd.kloes.hbfhc.wxau(this.tlske, "Upload Manager is not active to perform network tasks.");
                return;
            } else {
                this.qkduk = yfdkeVar.qkduk();
                com.good.gd.kloes.hbfhc.dbjc((Object) null, "Current settings of client: " + this.qkduk);
                this.liflu = new yfdke(yfdkeVar, aqdzkVar);
                mvf();
                return;
            }
        }
        com.good.gd.kloes.hbfhc.dbjc(this.tlske, "Analytics Not Supported or not configured yet, Exit.");
    }
}
