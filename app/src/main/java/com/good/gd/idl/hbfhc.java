package com.good.gd.idl;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import androidx.core.os.EnvironmentCompat;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.SIS.BISPreUnlockStorage.BISPreUnlockStorageManager;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.xohxj.hbfhc;
import com.good.gd.zwn.aqdzk;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class hbfhc {
    private static hbfhc ujr;
    private boolean bucpw;
    private com.good.gd.alu.hbfhc glwwi;
    private ScheduledFuture jsgtu;
    private boolean jwxax;
    private Location kwm;
    private boolean mloj;
    private final com.good.gd.qvfxc.hbfhc muee;
    private long mvf;
    private volatile Activity sbesx;
    private boolean ugfcv;
    private ScheduledExecutorService vfle;
    private ScheduledFuture wuird;
    private com.good.gd.daq.fdyxd wxau;
    private com.good.gd.oqpvt.yfdke dbjc = null;
    private com.good.gd.xohxj.hbfhc qkduk = null;
    private boolean ztwf = false;
    private final Class lqox = hbfhc.class;
    private AtomicBoolean liflu = new AtomicBoolean(false);
    private boolean jcpqe = false;
    private boolean tlske = false;
    private final ArrayDeque<com.good.gd.dvql.hbfhc> odlf = new ArrayDeque<>();
    private AtomicBoolean rynix = new AtomicBoolean(false);
    private boolean uxw = false;
    private String ssosk = null;
    private final com.good.gd.daq.yfdke wpejt = new yfdke();
    private ScheduledExecutorService yrp = Executors.newSingleThreadScheduledExecutor();
    private ScheduledExecutorService iulf = Executors.newSingleThreadScheduledExecutor();

    /* loaded from: classes.dex */
    class ehnkx implements com.good.gd.qvfxc.hbfhc {

        /* renamed from: com.good.gd.idl.hbfhc$ehnkx$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0008hbfhc implements Runnable {
            final /* synthetic */ int dbjc;

            RunnableC0008hbfhc(int i) {
                this.dbjc = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                hbfhc.dbjc(hbfhc.this, this.dbjc);
            }
        }

        ehnkx() {
        }

        @Override // com.good.gd.qvfxc.hbfhc
        public void onPrimerUpdated(int i) {
            hbfhc.this.ugfcv = true;
            com.blackberry.bis.core.yfdke.sbesx().dbjc("BISConsentStatus", i);
            hbfhc.lqox(hbfhc.this);
            hbfhc.this.vfle.execute(new RunnableC0008hbfhc(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            hbfhc.ztwf(hbfhc.this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.idl.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class RunnableC0009hbfhc implements Runnable {

        /* renamed from: com.good.gd.idl.hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0010hbfhc implements aqdzk {
            final /* synthetic */ boolean dbjc;
            final /* synthetic */ boolean qkduk;

            C0010hbfhc(boolean z, boolean z2) {
                this.dbjc = z;
                this.qkduk = z2;
            }

            @Override // com.good.gd.zwn.aqdzk
            public void dbjc(boolean z, int i, String str) {
                com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] Request completed with response code: " + i + " and message: " + str);
                boolean z2 = false;
                hbfhc.this.rynix.set(false);
                if (z) {
                    com.good.gd.kloes.ehnkx.jwxax(hbfhc.this.lqox, "[SRA] Successfully uploaded event.");
                    if (this.dbjc) {
                        hbfhc.this.mvf = SystemClock.elapsedRealtime();
                        com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] Clearing the SRA event queue since we successfully uploaded the SRA event.");
                        if (hbfhc.this.odlf != null) {
                            synchronized (hbfhc.this.odlf) {
                                hbfhc.this.odlf.clear();
                            }
                        }
                    }
                    if (!this.qkduk) {
                        return;
                    }
                    com.good.gd.vnzf.yfdke.odlf().jcpqe();
                    if (true == this.dbjc) {
                        return;
                    }
                    hbfhc.this.muee();
                    return;
                }
                com.good.gd.kloes.ehnkx.jwxax(hbfhc.this.lqox, "[SRA] Failed to upload event.");
                hbfhc.this.mvf = 0L;
                boolean z3 = i >= 400 && i < 500;
                if (i >= 500 && i < 600) {
                    z2 = true;
                }
                if (z3 || z2) {
                    com.good.gd.kloes.hbfhc.dbjc(hbfhc.this.lqox, "[SRA] Unable to process further events from queue.");
                    com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] Resetting the threat levels, as unable to contact to BIS Services.");
                    com.blackberry.bis.core.yfdke.muee().qkduk();
                    return;
                }
                hbfhc.this.muee();
            }
        }

        RunnableC0009hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.good.gd.dvql.hbfhc hbfhcVar;
            JSONObject jSONObject;
            com.good.gd.cth.hbfhc hbfhcVar2;
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] Start event network task.");
            synchronized (hbfhc.this.odlf) {
                hbfhcVar = (com.good.gd.dvql.hbfhc) hbfhc.this.odlf.pollLast();
            }
            boolean z = false;
            boolean z2 = hbfhcVar != null;
            boolean wxau = com.good.gd.vnzf.yfdke.odlf().wxau();
            if (wxau || z2) {
                z = true;
            }
            if (true != z) {
                com.good.gd.kloes.hbfhc.dbjc(hbfhc.this.lqox, "[SRA] No SRA or CA event(s) found to upload.");
                return;
            }
            C0010hbfhc c0010hbfhc = new C0010hbfhc(z2, wxau);
            if (!z2) {
                jSONObject = null;
            } else {
                jSONObject = hbfhcVar.dbjc();
            }
            if (!wxau) {
                hbfhcVar2 = null;
            } else {
                hbfhcVar2 = com.good.gd.vnzf.yfdke.odlf().jwxax();
                if (hbfhcVar2 != null) {
                    hbfhcVar2.dbjc(com.good.gd.vnzf.yfdke.odlf().liflu());
                }
            }
            if (com.blackberry.bis.core.yfdke.ztwf() != null) {
                com.good.gd.kofoa.hbfhc.wxau().dbjc(jSONObject, hbfhcVar2, c0010hbfhc);
                return;
            }
            throw null;
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements Runnable {
        mjbm() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] WAN IP retrofitting timer elapsed, Trying to execute the pending SRA network request.");
            hbfhc.this.liflu.set(false);
            hbfhc.this.muee();
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements Runnable {
        final /* synthetic */ String dbjc;

        pmoiy(String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.lqox, "[SRA] Retrofitting IP data into SRA events.");
            hbfhc.this.qkduk(this.dbjc);
            hbfhc.this.muee();
        }
    }

    private hbfhc() {
        this.ugfcv = false;
        this.bucpw = false;
        ehnkx ehnkxVar = new ehnkx();
        this.muee = ehnkxVar;
        com.good.gd.alu.hbfhc dbjc = com.blackberry.bis.core.yfdke.dbjc();
        this.glwwi = dbjc;
        ((com.good.gd.gectx.hbfhc) dbjc).dbjc(ehnkxVar);
        ((com.good.gd.gectx.hbfhc) this.glwwi).dbjc(com.good.gd.daq.hbfhc.qkduk().qkduk());
        ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).vducr();
        int liflu = liflu();
        BISPreUnlockStorageManager sbesx = com.blackberry.bis.core.yfdke.sbesx();
        if (!com.blackberry.bis.core.aqdzk.wxau()) {
            spwnx();
        } else if (liflu == 0 || 2 == liflu) {
            this.ugfcv = true;
        }
        if (com.good.gd.whhmi.yfdke.ztwf()) {
            this.bucpw = true;
        }
        if (com.good.gd.daq.hbfhc.dbjc()) {
            this.mloj = true;
        }
        if (com.good.gd.whhmi.yfdke.ztwf() || sbesx.jwxax("isNeverAskAgainSelected")) {
            this.bucpw = true;
        }
    }

    private synchronized boolean lzfct() {
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar == null) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location settings dialog : Location provider is Null.");
            return false;
        } else if (!(hbfhcVar instanceof com.good.gd.xohxj.yfdke)) {
            return true;
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location settings dialog : No need to check location mode for Framework Location Provider.");
            return false;
        }
    }

    private synchronized void nkvbq() {
        if (this.dbjc != null) {
            if (com.blackberry.bis.core.yfdke.ztwf() != null) {
                com.good.gd.kofoa.hbfhc wxau = com.good.gd.kofoa.hbfhc.wxau();
                if (true != this.dbjc.tlske()) {
                    wxau.qkduk();
                }
            } else {
                throw null;
            }
        }
        if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).jwxax()) {
            return;
        }
        if (this.rynix.get()) {
            if (com.blackberry.bis.core.yfdke.ztwf() != null) {
                if (com.good.gd.kofoa.hbfhc.wxau().dbjc()) {
                    com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Event network task is already in progress, waiting for EID Token.");
                } else {
                    com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Event network task is already in progress.");
                }
                return;
            }
            throw null;
        }
        com.good.gd.dvql.hbfhc peekLast = this.odlf.peekLast();
        boolean z = false;
        boolean z2 = peekLast != null;
        boolean wxau2 = com.good.gd.vnzf.yfdke.odlf().wxau();
        if (wxau2 || z2) {
            z = true;
        }
        if (true != z) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] No SRA or CA event(s) found to upload.");
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mvf;
        if (z2 && elapsedRealtime <= 60000) {
            com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Self Throttling, Ignore uploading event as we successfully uploaded a SRA event just now.");
        } else if (z2 && peekLast.qkduk()) {
            com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Waiting for location data for SRA Event.");
        } else {
            AtomicBoolean atomicBoolean = this.liflu;
            if (atomicBoolean != null && atomicBoolean.get()) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Waiting for WAN IP data.");
                return;
            }
            if (wxau2) {
                if (com.good.gd.vnzf.yfdke.odlf().jwxax() == null) {
                    com.good.gd.cth.hbfhc hbfhcVar = new com.good.gd.cth.hbfhc();
                    hbfhcVar.dbjc(mvf());
                    com.good.gd.vnzf.yfdke.odlf().dbjc(hbfhcVar);
                } else {
                    com.good.gd.vnzf.yfdke.odlf().jwxax();
                }
                if (true != z2) {
                    AtomicBoolean atomicBoolean2 = this.liflu;
                    if (atomicBoolean2 != null && atomicBoolean2.get()) {
                        com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Waiting for WAN IP data.");
                        return;
                    } else if (com.good.gd.vnzf.yfdke.odlf().jwxax().wxau()) {
                        com.good.gd.vnzf.yfdke.odlf().jsgtu();
                        return;
                    }
                }
            }
            this.rynix.set(true);
            tlske().execute(new RunnableC0009hbfhc());
        }
    }

    private JSONObject ohjp() throws JSONException {
        String wuird;
        String str;
        JSONObject jSONObject = new JSONObject();
        if (bucpw()) {
            hbfhc pqq = pqq();
            com.good.gd.oqpvt.yfdke yfdkeVar = pqq.dbjc;
            if (yfdkeVar != null) {
                if (yfdkeVar.wuird()) {
                    str = pqq.dbjc.lqox();
                    if (str != null && true != str.isEmpty()) {
                        jSONObject.put("router_ip", str);
                    }
                } else {
                    pqq.dbjc.jcpqe();
                }
            }
            str = null;
            if (str != null) {
                jSONObject.put("router_ip", str);
            }
        }
        if (kwm() && (wuird = pqq().wuird()) != null && true != wuird.isEmpty()) {
            jSONObject.put("wan_ip", wuird);
        }
        if (jSONObject.length() == 0) {
            return null;
        }
        return jSONObject;
    }

    public static hbfhc pqq() {
        if (ujr == null) {
            synchronized (hbfhc.class) {
                if (ujr == null) {
                    ujr = new hbfhc();
                }
            }
        }
        return ujr;
    }

    private void spwnx() {
        int liflu = liflu();
        BISPreUnlockStorageManager sbesx = com.blackberry.bis.core.yfdke.sbesx();
        if (liflu == 0) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Resetting BIS primer status to May be Later if the BIS enabled meta-data not available.");
            sbesx.dbjc("BISConsentStatus", 1);
        }
        this.ugfcv = true;
        com.blackberry.bis.core.yfdke.muee().qkduk();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void vducr() {
        ScheduledFuture scheduledFuture = this.jsgtu;
        if (scheduledFuture != null) {
            if (scheduledFuture.cancel(false)) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Timer to wait for location data for SRA events has been cancelled.");
                this.tlske = false;
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Timer to wait for location data for SRA events is already cancelled.");
        }
    }

    private void vnvgl() {
        znfb();
        if (true != this.tlske) {
            this.tlske = true;
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Starting SRA events retrofitting timer.");
            this.jsgtu = this.yrp.schedule(new fdyxd(), 30000L, TimeUnit.MILLISECONDS);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.lqox, "SRA events retrofitting timer is already running.");
    }

    private void wrfrc() {
        if (this.wuird != null && this.liflu.get()) {
            boolean cancel = this.wuird.cancel(false);
            this.liflu.set(false);
            if (cancel) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] WAN IP retrofitting timer is cancelled.");
                return;
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] WAN IP retrofitting timer is already cancelled.");
                return;
            }
        }
        com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] WAN IP retrofitting timer is not running.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void znfb() {
        ScheduledExecutorService scheduledExecutorService = this.yrp;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.lqox, "Telemetry executor service is either null or shutdown. Restarting!!");
            this.yrp = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public boolean bucpw() {
        return odlf() && liflu() == 0;
    }

    public void gbb() {
        if (true != (this.tlske || com.good.gd.vnzf.yfdke.odlf().lqox())) {
            com.good.gd.kloes.hbfhc.jwxax(this.lqox, "Stop fetching geo location update since all retrofitting timer got expired.");
            ofrex();
        }
    }

    public void glwwi() {
        com.good.gd.oqpvt.yfdke yfdkeVar = this.dbjc;
        if (yfdkeVar != null) {
            if (yfdkeVar.wuird()) {
                this.dbjc.liflu();
            } else {
                this.dbjc.jcpqe();
            }
        }
    }

    public synchronized boolean iulf() {
        if (liflu() == 0) {
            if (this.mloj) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location settings dialog : Device location settings dialog is already shown in current app lifecycle.");
                return false;
            } else if (true != com.good.gd.whhmi.yfdke.ztwf()) {
                com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location settings dialog : Permission is denied.");
                return false;
            } else if (true != com.good.gd.daq.hbfhc.dbjc()) {
                return true;
            } else {
                return lzfct();
            }
        }
        com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location settings dialog : SIS participation is disabled, No need to display.");
        return false;
    }

    public boolean kwm() {
        return odlf() && liflu() == 0;
    }

    public String lqox() {
        com.good.gd.oqpvt.yfdke yfdkeVar = this.dbjc;
        if (yfdkeVar != null) {
            return yfdkeVar.ztwf();
        }
        return null;
    }

    public boolean mloj() {
        com.good.gd.oqpvt.yfdke yfdkeVar = this.dbjc;
        return yfdkeVar != null && yfdkeVar.jsgtu();
    }

    public synchronized void muee() {
        if (uxw()) {
            nkvbq();
        } else if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ztwf()) {
            com.blackberry.bis.core.yfdke.muee().qkduk();
        }
    }

    public synchronized void ofrex() {
        com.good.gd.kloes.hbfhc.jwxax(this.lqox, "Stop Geo Location Update.");
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar != null) {
            if (hbfhcVar.lqox()) {
                this.qkduk.tlske();
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Location request/ monitoring is not initiated yet.");
            }
            this.qkduk.qkduk(false);
        }
    }

    public synchronized boolean rynix() {
        boolean z = false;
        if (this.ugfcv) {
            return false;
        }
        int wxau = com.blackberry.bis.core.yfdke.sbesx().wxau("BISConsentStatus");
        if (-1 == wxau || 1 == wxau) {
            z = true;
        }
        return z;
    }

    public synchronized void sbesx() {
        com.good.gd.kloes.hbfhc.jwxax(this.lqox, "Device Location Settings Changed.");
        if (this.qkduk == null) {
            return;
        }
        boolean dbjc = com.good.gd.daq.hbfhc.dbjc();
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar instanceof com.good.gd.xohxj.fdyxd) {
            if (true != dbjc) {
                ofrex();
            }
        } else if (hbfhcVar instanceof com.good.gd.xohxj.yfdke) {
            ofrex();
            if ((this.tlske || com.good.gd.vnzf.yfdke.odlf().lqox()) && yrp()) {
                wxau();
            }
        }
    }

    public void ssosk() {
        AtomicBoolean atomicBoolean = this.liflu;
        if (atomicBoolean == null || !atomicBoolean.get()) {
            return;
        }
        wrfrc();
        muee();
    }

    public synchronized boolean ugfcv() {
        if (liflu() == 0) {
            if (true != this.bucpw) {
                if (true != com.good.gd.whhmi.yfdke.ztwf()) {
                    if (!com.blackberry.bis.core.yfdke.sbesx().jwxax("isNeverAskAgainSelected")) {
                        return true;
                    }
                    com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location system permission Dialog : Already denied with Don't ask again.");
                    return false;
                }
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location system permission Dialog : Permission already allowed.");
                return false;
            }
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location system permission dialog : Dialog was already displayed for this launch.");
            return false;
        }
        com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location system permission dialog : SIS participation is disabled, No need to display.");
        return false;
    }

    public void ujr() {
        znfb();
        if (true != this.liflu.get()) {
            this.liflu.set(true);
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Started WAN IP retrofitting timer.");
            this.wuird = this.yrp.schedule(new mjbm(), 10000L, TimeUnit.MILLISECONDS);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] WAN IP retrofitting timer is already running.");
    }

    public boolean uxw() {
        if (true != com.blackberry.bis.core.aqdzk.wxau()) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] Network request is not possible now, as Blackberry Persona metadata not available or disabled.");
            return false;
        } else if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).yrp()) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] Network request not possible now, as entitlements has not been received.");
            return false;
        } else if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt()) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] Network request not possible now, as EID infrastructure is not activated.");
            return false;
        } else if (true == com.good.gd.rutan.hbfhc.tlske().dbjc("SRARequest")) {
            return true;
        } else {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] Network request not possible now, as SRA end point is not available.");
            return false;
        }
    }

    public synchronized boolean vfle() {
        boolean z = false;
        if (liflu() == 0) {
            if (true != this.uxw) {
                if (com.good.gd.whhmi.yfdke.lqox()) {
                    if (!com.good.gd.whhmi.yfdke.ztwf()) {
                        if (com.blackberry.bis.core.yfdke.sbesx().jwxax("isNeverAskAgainSelected")) {
                            z = true;
                        } else {
                            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location custom permission Dialog : Not denied with Don't ask again.");
                        }
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location custom permission Dialog : Permission already allowed.");
                    }
                    return z;
                }
                if (!com.good.gd.whhmi.yfdke.ztwf()) {
                    z = true;
                } else {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location custom permission Dialog : Permission already allowed.");
                }
                return z;
            }
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Display location custom permission dialog : Dialog was already displayed for this launch.");
            return false;
        }
        com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display location custom permission dialog : SIS participation is disabled, No need to display.");
        return false;
    }

    public void wpejt() {
        if (this.jcpqe) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "Display Analytics Settings : Analytics Settings screen is already visible.");
            return;
        }
        this.jcpqe = true;
        ((com.good.gd.gectx.hbfhc) this.glwwi).dbjc(this.muee);
        if (((com.good.gd.gectx.hbfhc) this.glwwi) == null) {
            throw null;
        }
        CoreUI.requestBISSettingsUI();
    }

    public void wxau() {
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar == null) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Cannot execute location request, geo location provider is null.");
        } else {
            hbfhcVar.jcpqe();
        }
    }

    public void xzwry() {
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        boolean z = true;
        if (hbfhcVar == null || !(hbfhcVar instanceof com.good.gd.xohxj.fdyxd) || true == com.good.gd.daq.hbfhc.lqox()) {
            z = false;
        }
        if (z) {
            com.good.gd.kloes.hbfhc.jwxax(this.lqox, "Require to Update Location Provider Instance.");
            ofrex();
            synchronized (this) {
                com.good.gd.daq.hbfhc.liflu();
                this.qkduk = com.good.gd.daq.hbfhc.qkduk();
            }
        }
    }

    public boolean yrp() {
        return odlf() && com.good.gd.whhmi.yfdke.ztwf() && com.good.gd.daq.hbfhc.dbjc() && liflu() == 0;
    }

    public void ztwf(boolean z) {
        this.bucpw = z;
    }

    static /* synthetic */ void lqox(hbfhc hbfhcVar) {
        ScheduledExecutorService scheduledExecutorService = hbfhcVar.vfle;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(hbfhcVar.lqox, "BIS executor service is either null or shutdown. Restarting!!");
            hbfhcVar.vfle = Executors.newSingleThreadScheduledExecutor();
        }
    }

    static /* synthetic */ void ztwf(hbfhc hbfhcVar) {
        com.good.gd.kloes.hbfhc.wxau(hbfhcVar.lqox, "SRA events retrofitting timer is expired.");
        hbfhcVar.tlske = false;
        Location location = hbfhcVar.kwm;
        if (location != null) {
            hbfhcVar.dbjc(location, 0L);
        } else {
            Location dbjc = hbfhcVar.qkduk.dbjc();
            if (dbjc != null) {
                hbfhcVar.dbjc(dbjc, SystemClock.elapsedRealtime() - TimeUnit.NANOSECONDS.toMillis(dbjc.getElapsedRealtimeNanos()));
            } else {
                hbfhcVar.dbjc((Location) null, 0L);
            }
        }
        hbfhcVar.kwm = null;
        hbfhcVar.muee();
        hbfhcVar.gbb();
    }

    public String jcpqe() {
        return this.ssosk;
    }

    public ScheduledExecutorService jsgtu() {
        znfb();
        return this.yrp;
    }

    public void jwxax(String str) {
        this.ssosk = str;
    }

    public int liflu() {
        int wxau = com.blackberry.bis.core.yfdke.sbesx().wxau("BISConsentStatus");
        com.good.gd.kloes.hbfhc.wxau(this.lqox, "Primer Status: " + (-1 == wxau ? "Waiting for User Action." : wxau == 0 ? "Agreed." : 1 == wxau ? "Disagreed." : 2 == wxau ? "Disagreed with Never Ask Again." : null));
        return wxau;
    }

    public boolean mvf() {
        boolean z = true;
        if (true != com.blackberry.bis.core.aqdzk.wxau()) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting location retrofitting needed as false since BIS persona metadata is not available.");
            z = false;
        } else if (com.good.gd.daq.hbfhc.qkduk() == null) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as false since Location Provider is neither GoogleLocationProvider nor FrameworkLocationProvider.");
            z = false;
        } else if (yrp()) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as true since Location Tracking is allowed.");
        } else if (!this.tlske && !com.good.gd.vnzf.yfdke.odlf().lqox()) {
            if (2 == liflu()) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as false since Primer is disagreed with never ask again.");
                z = false;
            } else if (true != odlf()) {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as false since Geo-Location Entitlement is disabled.");
                z = false;
            } else {
                if (true != (BlackberryAnalyticsCommon.rynix().wuird && ((PowerManager) BlackberryAnalyticsCommon.rynix().jwxax().getSystemService("power")).isInteractive())) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as false since application is in background where we can't display the BIS UX.");
                    z = false;
                } else if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).glwwi()) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as false since application is in Block State");
                    z = false;
                } else if (true == ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).ssosk()) {
                    z = false;
                } else {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as true application is in foreground with display in interactive mode and container is not authorized at least once where we display the BIS UX.");
                }
            }
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Setting Location Retrofit Needed as true since an event is already waiting for location update.");
        }
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar != null) {
            hbfhcVar.qkduk(z);
        }
        return z;
    }

    public boolean odlf() {
        return com.blackberry.bis.core.yfdke.sbesx().jwxax("BISEntitlementStatus");
    }

    public ScheduledExecutorService tlske() {
        ScheduledExecutorService scheduledExecutorService = this.iulf;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.lqox, "[SRA] Executor Service is either null or shutdown. Restarting!!");
            this.iulf = Executors.newSingleThreadScheduledExecutor();
        }
        return this.iulf;
    }

    public String wuird() {
        com.good.gd.oqpvt.yfdke yfdkeVar = this.dbjc;
        if (yfdkeVar != null) {
            if (yfdkeVar.wuird()) {
                return this.dbjc.liflu();
            }
            this.dbjc.jcpqe();
            return null;
        }
        return null;
    }

    public void jwxax(boolean z) {
        this.ugfcv = z;
    }

    public void qkduk(boolean z) {
        this.mloj = z;
    }

    public synchronized void jwxax() {
        if (true != this.ztwf) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Enabling Geo Location Provider.");
            if (this.qkduk == null) {
                com.good.gd.xohxj.hbfhc qkduk = com.good.gd.daq.hbfhc.qkduk();
                this.qkduk = qkduk;
                if (qkduk == null) {
                    com.good.gd.kloes.hbfhc.jwxax(this.lqox, "Could not enable Geo Location Provider.");
                    return;
                }
                qkduk.qkduk(this.wpejt);
            }
            this.ztwf = true;
            if (this.wxau == null) {
                this.wxau = new com.good.gd.daq.fdyxd();
            }
            IntentFilter intentFilter = new IntentFilter("android.location.MODE_CHANGED");
            Context jwxax = BlackberryAnalyticsCommon.rynix().jwxax();
            if (true != pqq().jwxax) {
                if (jwxax != null && this.wxau != null) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Registering geo location provider broadcast receiver.");
                    jwxax.registerReceiver(this.wxau, intentFilter);
                    this.jwxax = true;
                }
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "Location change broadcast receiver is already registered.");
            }
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Geo Location Provider already enabled.");
        }
    }

    public synchronized void qkduk() {
        if (this.ztwf) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Disabling Geo Location Provider.");
            ofrex();
            com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
            if (hbfhcVar != null) {
                hbfhcVar.dbjc(yfdke.APPROVAL_DENIED, null, null);
                this.qkduk.liflu();
            }
            Context jwxax = BlackberryAnalyticsCommon.rynix().jwxax();
            if (jwxax != null && this.wxau != null) {
                try {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Un-registering geo location provider change broadcast receiver.");
                    jwxax.unregisterReceiver(this.wxau);
                    this.jwxax = false;
                } catch (IllegalArgumentException e) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Geo Location provider change broadcast receiver is not registered or already un-registered!!. Can't be unregistered.");
                }
            }
            this.qkduk = null;
            this.ztwf = false;
            com.good.gd.daq.hbfhc.liflu();
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "Geo Location Monitor already disabled.");
        }
    }

    public void dbjc(Activity activity) {
        this.sbesx = activity;
    }

    public void dbjc(com.good.gd.oqpvt.yfdke yfdkeVar) {
        if (this.dbjc == null) {
            this.dbjc = yfdkeVar;
        }
    }

    static /* synthetic */ void wxau(hbfhc hbfhcVar) {
        if (hbfhcVar.tlske) {
            com.good.gd.kloes.hbfhc.wxau(hbfhcVar.lqox, "Restarting SRA events retrofitting timer.");
            hbfhcVar.vducr();
            hbfhcVar.vnvgl();
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(hbfhcVar.lqox, "Cannot restart SRA events retrofitting timer as it is already expired or cancelled.");
    }

    public void dbjc(boolean z) {
        this.uxw = z;
    }

    public void dbjc(com.good.gd.dvql.hbfhc hbfhcVar) {
        com.good.gd.dvql.hbfhc hbfhcVar2;
        boolean uxw = uxw();
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mvf;
        if (uxw && elapsedRealtime <= 60000) {
            com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Ignore adding event as we successfully uploaded a SRA event just now.");
            return;
        }
        com.good.gd.rutan.hbfhc.tlske().dbjc();
        if (hbfhcVar == null) {
            com.good.gd.kloes.hbfhc.dbjc(this.lqox, "[SRA] Cannot add null event.");
            return;
        }
        synchronized (this.odlf) {
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Add event to queue.");
            hbfhcVar2 = new com.good.gd.dvql.hbfhc(hbfhcVar);
            JSONObject dbjc = hbfhcVar2.dbjc();
            try {
                JSONObject ohjp = ohjp();
                if (ohjp != null) {
                    dbjc.put("telemetry", ohjp);
                }
            } catch (JSONException e) {
            }
            com.good.gd.kloes.ehnkx.jwxax(this.lqox, "[SRA] Adding Event: " + dbjc);
            if (20 <= this.odlf.size()) {
                this.odlf.removeFirst();
                com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Event queue capacity exceeded, removed first event and adding current event.");
            }
            this.odlf.add(hbfhcVar2);
            com.good.gd.kloes.hbfhc.wxau(this.lqox, "[SRA] Event queue size: " + this.odlf.size());
        }
        com.good.gd.kloes.hbfhc.jwxax(this.lqox, "[SRA] Event Added.");
        if (true != hbfhcVar2.qkduk()) {
            muee();
            return;
        }
        vnvgl();
        if (!yrp()) {
            return;
        }
        wxau();
    }

    public void wxau(boolean z) {
        this.jcpqe = z;
    }

    /* loaded from: classes.dex */
    class yfdke implements com.good.gd.daq.yfdke {

        /* renamed from: com.good.gd.idl.hbfhc$yfdke$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0011hbfhc implements Runnable {
            final /* synthetic */ Location dbjc;

            RunnableC0011hbfhc(Location location) {
                this.dbjc = location;
            }

            @Override // java.lang.Runnable
            public void run() {
                hbfhc.this.dbjc(this.dbjc, 0L);
                hbfhc.this.muee();
            }
        }

        /* renamed from: com.good.gd.idl.hbfhc$yfdke$yfdke  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0012yfdke implements Runnable {
            RunnableC0012yfdke() {
            }

            @Override // java.lang.Runnable
            public void run() {
                hbfhc.this.dbjc((Location) null, 0L);
                hbfhc.this.muee();
            }
        }

        yfdke() {
        }

        @Override // com.good.gd.daq.yfdke
        public void dbjc(Location location, String str) {
            if (true != hbfhc.this.tlske) {
                return;
            }
            if (str == null || !str.equals("network_provider") || !com.good.gd.daq.hbfhc.ztwf()) {
                hbfhc.this.kwm = null;
                hbfhc.this.vducr();
                hbfhc.this.tlske = false;
                hbfhc.this.znfb();
                hbfhc.this.yrp.execute(new RunnableC0011hbfhc(location));
                return;
            }
            hbfhc.this.kwm = location;
        }

        @Override // com.good.gd.daq.yfdke
        public void qkduk() {
            if (true != hbfhc.this.tlske) {
                return;
            }
            com.good.gd.kloes.hbfhc.dbjc(hbfhc.this.lqox, "Location request is denied for SRA events.");
            hbfhc.this.vducr();
            if (hbfhc.this.qkduk != null) {
                hbfhc.this.qkduk.qkduk(false);
            }
            hbfhc.this.tlske = false;
            hbfhc.this.kwm = null;
            hbfhc.this.yrp.schedule(new RunnableC0012yfdke(), 0L, TimeUnit.MILLISECONDS);
        }

        @Override // com.good.gd.daq.yfdke
        public void dbjc() {
            if (true != hbfhc.this.tlske) {
                return;
            }
            hbfhc.wxau(hbfhc.this);
        }
    }

    public void qkduk(String str) {
        boolean kwm = kwm();
        ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = this.odlf;
        if (arrayDeque == null || true == arrayDeque.isEmpty()) {
            return;
        }
        synchronized (this.odlf) {
            Iterator<com.good.gd.dvql.hbfhc> it = this.odlf.iterator();
            while (it.hasNext()) {
                com.good.gd.dvql.hbfhc next = it.next();
                if (next != null && kwm) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Retrofitting WAN IP data into SRA event.");
                    dbjc(next, str);
                }
            }
        }
    }

    public String ztwf() {
        com.good.gd.xohxj.hbfhc hbfhcVar = this.qkduk;
        return hbfhcVar != null ? hbfhcVar instanceof com.good.gd.xohxj.fdyxd ? "googleDevLocSettingsDialog" : "frameworkDevLocSettingsDialog" : "";
    }

    public void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.lqox, "Clean up SRA event queue.");
        this.rynix.set(false);
        synchronized (this.odlf) {
            this.odlf.clear();
        }
    }

    private void dbjc(com.good.gd.dvql.hbfhc hbfhcVar, Location location, long j) {
        JSONObject dbjc;
        String str;
        if (hbfhcVar == null || !hbfhcVar.qkduk() || (dbjc = hbfhcVar.dbjc()) == null) {
            return;
        }
        try {
            if (dbjc.has("telemetry")) {
                JSONObject jSONObject = dbjc.getJSONObject("telemetry");
                if (jSONObject != null && jSONObject.length() > 0 && ((true != jSONObject.has("lat") || true != jSONObject.has("lng")) && yrp() && location != null)) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Location data is missing in inner telemetry JSON of SRA event, adding it.");
                    jSONObject.put("lat", String.valueOf(location.getLatitude()));
                    jSONObject.put("lng", String.valueOf(location.getLongitude()));
                    if (j > 0) {
                        jSONObject.put("locstatus", 1);
                        jSONObject.put("locdelay", j);
                    } else {
                        jSONObject.put("locstatus", 0);
                    }
                    jSONObject.put("horizontal_accuracy", String.valueOf(location.getAccuracy()));
                    if (Build.VERSION.SDK_INT >= 26) {
                        jSONObject.put("vertical_accuracy", String.valueOf(location.getVerticalAccuracyMeters()));
                    }
                    jSONObject.put("alt", String.valueOf(location.getAltitude()));
                    if (location.isFromMockProvider()) {
                        jSONObject.put("loc_source", "mock");
                    } else {
                        String provider = location.getProvider();
                        if (provider != null && true != provider.trim().isEmpty()) {
                            jSONObject.put("loc_source", provider.toLowerCase());
                        } else {
                            jSONObject.put("loc_source", EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                    }
                }
            } else {
                JSONObject jSONObject2 = new JSONObject();
                if (!yrp() || location == null) {
                    str = "telemetry";
                } else {
                    str = "telemetry";
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Location data is missing in SRA event, adding it.");
                    jSONObject2.put("lat", String.valueOf(location.getLatitude()));
                    jSONObject2.put("lng", String.valueOf(location.getLongitude()));
                    jSONObject2.put("horizontal_accuracy", String.valueOf(location.getAccuracy()));
                    if (Build.VERSION.SDK_INT >= 26) {
                        jSONObject2.put("vertical_accuracy", String.valueOf(location.getVerticalAccuracyMeters()));
                    }
                    if (j > 0) {
                        jSONObject2.put("locstatus", 1);
                        jSONObject2.put("locdelay", j);
                    } else {
                        jSONObject2.put("locstatus", 0);
                    }
                    jSONObject2.put("alt", String.valueOf(location.getAltitude()));
                    if (location.isFromMockProvider()) {
                        jSONObject2.put("loc_source", "mock");
                    } else {
                        String provider2 = location.getProvider();
                        if (provider2 != null && true != provider2.trim().isEmpty()) {
                            jSONObject2.put("loc_source", provider2.toLowerCase());
                        } else {
                            jSONObject2.put("loc_source", EnvironmentCompat.MEDIA_UNKNOWN);
                        }
                    }
                }
                if (jSONObject2.length() > 0) {
                    dbjc.put(str, jSONObject2);
                }
            }
            hbfhcVar.dbjc(dbjc);
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.lqox, "Failed to retrofit telemetry data into SRA event " + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(Location location, long j) {
        boolean z = location != null && ujr.yrp();
        ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = this.odlf;
        if (arrayDeque == null || arrayDeque.size() <= 0) {
            return;
        }
        synchronized (this.odlf) {
            Iterator<com.good.gd.dvql.hbfhc> it = this.odlf.iterator();
            while (it.hasNext()) {
                com.good.gd.dvql.hbfhc next = it.next();
                if (next != null) {
                    if (z) {
                        com.good.gd.kloes.hbfhc.wxau(this.lqox, "Retrofitting location data into the SRA event.");
                        dbjc(next, location, j);
                    }
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "Resetting location retrofit needed status of the SRA event.");
                    next.dbjc(false);
                }
            }
        }
    }

    private void dbjc(com.good.gd.dvql.hbfhc hbfhcVar, String str) {
        JSONObject dbjc;
        if (hbfhcVar == null || (dbjc = hbfhcVar.dbjc()) == null) {
            return;
        }
        try {
            if (dbjc.has("telemetry")) {
                JSONObject jSONObject = dbjc.getJSONObject("telemetry");
                if (jSONObject != null && jSONObject.length() > 0 && hbfhcVar.jwxax() && true != jSONObject.has("wan_ip") && kwm() && str != null && true != str.trim().isEmpty()) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "WAN IP address is missing in inner telemetry JSON of SRA event, adding it.");
                    jSONObject.put("wan_ip", str);
                }
            } else {
                JSONObject jSONObject2 = new JSONObject();
                if (hbfhcVar.jwxax() && kwm() && str != null && true != str.trim().isEmpty()) {
                    com.good.gd.kloes.hbfhc.wxau(this.lqox, "WAN IP is missing in SRA event, adding it.");
                    jSONObject2.put("wan_ip", str);
                }
                if (jSONObject2.length() > 0) {
                    dbjc.put("telemetry", jSONObject2);
                }
            }
            hbfhcVar.dbjc(dbjc);
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.lqox, "Failed to retrofit Ip data into SRA event " + e.getLocalizedMessage());
        }
    }

    static /* synthetic */ void dbjc(hbfhc hbfhcVar, int i) {
        if (hbfhcVar != null) {
            if (2 == i || 1 == i) {
                com.good.gd.kloes.hbfhc.dbjc(hbfhcVar.lqox, 1 == i ? "BIS primer disagreed." : "BIS primer disagreed with never ask again.");
                com.good.gd.xohxj.hbfhc hbfhcVar2 = hbfhcVar.qkduk;
                if (hbfhcVar2 != null) {
                    hbfhcVar2.dbjc(yfdke.APPROVAL_DENIED, null, null);
                }
                com.good.gd.oqpvt.yfdke yfdkeVar = hbfhcVar.dbjc;
                if (yfdkeVar == null) {
                    return;
                }
                yfdkeVar.wxau();
                return;
            } else if (i != 0) {
                return;
            } else {
                com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.lqox, "BIS primer agreed.");
                if (hbfhcVar.qkduk != null && ujr.kwm()) {
                    com.good.gd.kloes.hbfhc.wxau(hbfhcVar.lqox, "BIS primer is allowed, Preparing WAN IP Address.");
                    hbfhcVar.glwwi();
                }
                if (!com.good.gd.whhmi.yfdke.ztwf()) {
                    return;
                }
                com.blackberry.bis.core.yfdke.sbesx().dbjc("isLocationPermissionGranted", true);
                if (hbfhcVar.qkduk == null || !hbfhcVar.yrp() || !hbfhcVar.qkduk.wxau()) {
                    return;
                }
                hbfhcVar.qkduk.dbjc(yfdke.LOCATION_PERMISSION_ALLOWED, null, null);
                hbfhcVar.qkduk.jcpqe();
                return;
            }
        }
        throw null;
    }

    public void dbjc(String str) {
        wrfrc();
        znfb();
        this.yrp.execute(new pmoiy(str));
    }
}
