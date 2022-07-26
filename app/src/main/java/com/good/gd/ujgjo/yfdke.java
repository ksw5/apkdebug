package com.good.gd.ujgjo;

import android.content.Context;
import androidx.core.view.PointerIconCompat;
import com.blackberry.analytics.analyticsengine.AppUsageEventType;
import com.blackberry.analytics.analyticsengine.SecurityEventType;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.pmoiy;
import com.good.gd.npgvd.zngm;
import com.good.gd.ovnkx.mjbm;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class yfdke {
    static final /* synthetic */ boolean mloj = true;
    private static int uxw = 0;
    private final Context iulf;
    private com.good.gd.zwn.ooowe jwxax;
    private com.good.gd.ovnkx.gioey mvf;
    private final com.good.gd.ujgjo.hbfhc qkduk;
    private final com.good.gd.oqpvt.yfdke rynix;
    private final Class ugfcv;
    private com.good.gd.ovnkx.yfdke wuird;
    private com.good.gd.oqpvt.hbfhc wxau;
    private ScheduledExecutorService dbjc = Executors.newSingleThreadScheduledExecutor();
    private AtomicBoolean ztwf = new AtomicBoolean(false);
    private AtomicBoolean lqox = new AtomicBoolean(false);
    private AtomicBoolean liflu = new AtomicBoolean(false);
    private Set<String> jcpqe = new HashSet();
    private Set<String> tlske = new HashSet();
    private Set<String> jsgtu = new HashSet();
    private final ArrayDeque<com.good.gd.dvql.hbfhc> odlf = new ArrayDeque<>();
    private final ArrayDeque<com.good.gd.dvql.hbfhc> yrp = new ArrayDeque<>();
    private final ConcurrentHashMap<String, com.good.gd.dvql.yfdke> vfle = new ConcurrentHashMap<>();
    private boolean bucpw = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class aqdzk implements Runnable {
        aqdzk() {
        }

        @Override // java.lang.Runnable
        public void run() {
            yfdke yfdkeVar = yfdke.this;
            yfdke.dbjc(yfdkeVar, "historical", yfdkeVar.odlf);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements Runnable {
        ehnkx() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (true != yfdke.this.bucpw) {
                boolean wxau = com.good.gd.whhmi.yfdke.wxau();
                yfdke.this.bucpw = BlackberryAnalyticsCommon.rynix().registerBBRYAnalyticsCrashHandlers(wxau);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (yfdke.this.qkduk != null) {
                com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Create new file as timezone header got changed.");
                yfdke.this.qkduk.dbjc("historical");
                yfdke.this.qkduk.dbjc("appIntelligence");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class gioey implements Runnable {
        final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;

        gioey(com.good.gd.zwn.aqdzk aqdzkVar) {
            this.dbjc = aqdzkVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                int unused = yfdke.uxw = 0;
                yfdke.dbjc(yfdke.this, this.dbjc);
            } catch (Exception e) {
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Unable to upload logs through POST request: " + e.getLocalizedMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {

        /* renamed from: com.good.gd.ujgjo.yfdke$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0031hbfhc implements mjbm.aqdzk {
            C0031hbfhc(hbfhc hbfhcVar) {
            }

            @Override // com.good.gd.ovnkx.mjbm.aqdzk
            public void dbjc(boolean z) {
            }
        }

        hbfhc(yfdke yfdkeVar) {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.good.gd.ovnkx.mjbm.dbjc(new C0031hbfhc(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class mjbm implements com.good.gd.zwn.aqdzk {
        final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;
        final /* synthetic */ com.good.gd.zwn.yfdke qkduk;

        mjbm(com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.zwn.yfdke yfdkeVar) {
            this.dbjc = aqdzkVar;
            this.qkduk = yfdkeVar;
        }

        @Override // com.good.gd.zwn.aqdzk
        public void dbjc(boolean z, int i, String str) {
            if (z) {
                yfdke.qkduk(yfdke.this, str, this.dbjc, this, this.qkduk, false);
                return;
            }
            com.good.gd.zwn.aqdzk aqdzkVar = this.dbjc;
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public class ooowe implements pmoiy.hbfhc {
        final Context dbjc;
        final com.good.gd.idl.hbfhc jwxax;
        final com.blackberry.bis.core.pmoiy qkduk;
        private long wxau;

        public ooowe(Context context, com.blackberry.bis.core.pmoiy pmoiyVar, com.good.gd.idl.hbfhc hbfhcVar) {
            this.dbjc = context;
            this.qkduk = pmoiyVar;
            this.jwxax = hbfhcVar;
        }

        @Override // com.blackberry.bis.core.pmoiy.hbfhc
        public void dbjc() {
            com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Soft unlocked.");
            this.jwxax.xzwry();
        }

        @Override // com.blackberry.bis.core.pmoiy.hbfhc
        public void jwxax() {
            if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) this.qkduk).sbesx().booleanValue()) {
                yfdke.this.jwxax();
                return;
            }
            this.wxau = System.currentTimeMillis();
            yfdke.dbjc(yfdke.this, this.dbjc);
        }

        @Override // com.blackberry.bis.core.pmoiy.hbfhc
        public void qkduk() {
            boolean odlf = this.jwxax.odlf();
            com.good.gd.dkesx.hbfhc hbfhcVar = (com.good.gd.dkesx.hbfhc) com.blackberry.bis.core.yfdke.muee();
            if (hbfhcVar != null) {
                if (!odlf) {
                    hbfhcVar.qkduk();
                }
                String ztwf = com.good.gd.rutan.hbfhc.tlske().ztwf();
                String jwxax = com.good.gd.rutan.hbfhc.tlske().jwxax();
                if (com.good.gd.whhmi.yfdke.liflu() && com.good.gd.whhmi.yfdke.qkduk(ztwf) && !"discovery.bis.blackberry.com".equals(jwxax)) {
                    com.good.gd.rutan.hbfhc.tlske().qkduk();
                }
                if (odlf) {
                    this.jwxax.jwxax();
                    return;
                }
                this.jwxax.qkduk();
                ((com.good.gd.yufa.hbfhc) com.blackberry.bis.core.yfdke.jwxax()).dbjc();
                com.good.gd.dkesx.hbfhc hbfhcVar2 = (com.good.gd.dkesx.hbfhc) com.blackberry.bis.core.yfdke.muee();
                if (hbfhcVar2 != null) {
                    hbfhcVar2.qkduk();
                    com.good.gd.vnzf.yfdke.odlf().dbjc(false);
                    return;
                }
                throw null;
            }
            throw null;
        }

        @Override // com.blackberry.bis.core.pmoiy.hbfhc
        public void wxau() {
            com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Hard unlocked.");
            if (com.good.gd.ovnkx.mjbm.liflu()) {
                yfdke.this.liflu();
            }
            yfdke.this.lqox();
            this.jwxax.xzwry();
        }

        public void ztwf() {
            if (com.good.gd.whhmi.yfdke.lqox()) {
                if (System.currentTimeMillis() - this.wxau > ((long) yfdke.uxw)) {
                    return;
                }
                yfdke yfdkeVar = yfdke.this;
                if (yfdkeVar != null) {
                    yfdkeVar.mvf.liflu();
                    yfdke yfdkeVar2 = yfdke.this;
                    if (yfdkeVar2 != null) {
                        yfdkeVar2.mvf.jcpqe();
                        int unused = yfdke.uxw = 0;
                        yfdke.this.tlske();
                        return;
                    }
                    throw null;
                }
                throw null;
            }
        }
    }

    /* loaded from: classes.dex */
    protected class opjy implements com.good.gd.ghhwi.yfdke {
        /* JADX INFO: Access modifiers changed from: protected */
        public opjy() {
        }

        @Override // com.good.gd.ghhwi.yfdke
        public void dbjc(boolean z) {
            yfdke.this.mvf.liflu();
            if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).vfle().booleanValue()) {
                com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Analytics not Supported or not configured yet, Exit.");
                return;
            }
            long jwxax = yfdke.this.jwxax.jwxax();
            if (jwxax > 0) {
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Schedule Next GET Request");
                yfdke yfdkeVar = yfdke.this;
                if (yfdkeVar != null) {
                    yfdkeVar.dbjc(new com.good.gd.ujgjo.ehnkx(yfdkeVar, null, false), jwxax, TimeUnit.MINUTES);
                    return;
                }
                throw null;
            }
            com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Network delay interval not existing, do not schedule next request");
        }

        @Override // com.good.gd.ghhwi.yfdke
        public void qkduk(boolean z) {
            yfdke.this.mvf.jcpqe();
            if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).vfle().booleanValue()) {
                com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Analytics not Supported or not configured yet, Exit.");
            } else if (z) {
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Schedule Next POST Request");
                long wxau = yfdke.this.jwxax.wxau();
                yfdke yfdkeVar = yfdke.this;
                if (yfdkeVar != null) {
                    yfdkeVar.qkduk(new com.good.gd.ujgjo.pmoiy(yfdkeVar, null), wxau, TimeUnit.MINUTES);
                    return;
                }
                throw null;
            } else {
                com.good.gd.kloes.hbfhc.jwxax(yfdke.this.ugfcv, "Do NOT Schedule, try to upload next GD/Foreground event");
                BlackberryAnalyticsCommon.rynix().dbjc(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class orlrx implements Runnable {
        orlrx() {
        }

        @Override // java.lang.Runnable
        public void run() {
            yfdke yfdkeVar = yfdke.this;
            yfdke.dbjc(yfdkeVar, "appIntelligence", yfdkeVar.yrp);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class pmoiy implements com.good.gd.zwn.yfdke {
        final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;

        pmoiy(com.good.gd.zwn.aqdzk aqdzkVar) {
            this.dbjc = aqdzkVar;
        }

        @Override // com.good.gd.zwn.yfdke
        public void dbjc(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
            com.good.gd.kloes.hbfhc.dbjc(yfdke.this.ugfcv, "App Intelligence Event File compression failed.");
            com.good.gd.kloes.ehnkx.wxau(yfdke.this.ugfcv, String.format("App Intelligence File : %s", file.getName()));
            yfdke.this.qkduk.qkduk(file.getAbsolutePath());
            yfdke.qkduk(yfdke.this, "App Intelligence Event GZIP File Compression failed, Scheduling the next file.", this.dbjc, aqdzkVar, this, true);
        }

        @Override // com.good.gd.zwn.yfdke
        public void qkduk(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
            com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "App Intelligence Event File compression successful.");
            com.good.gd.kloes.ehnkx.qkduk(yfdke.this.ugfcv, String.format("App Intelligence File : %s", file.getName()));
            yfdke.this.dbjc(file, aqdzkVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class vzw implements Runnable {
        final /* synthetic */ com.good.gd.zwn.aqdzk dbjc;

        vzw(com.good.gd.zwn.aqdzk aqdzkVar) {
            this.dbjc = aqdzkVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (yfdke.this.qkduk(this.dbjc)) {
                    return;
                }
                int unused = yfdke.uxw = 0;
                yfdke.this.jwxax.dbjc(yfdke.this.wuird, this.dbjc);
            } catch (Exception e) {
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Unable to fetch config through GET request: " + e.getLocalizedMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yjh implements com.good.gd.zwn.aqdzk {
        final /* synthetic */ File dbjc;
        final /* synthetic */ com.good.gd.zwn.aqdzk qkduk;

        /* loaded from: classes.dex */
        class hbfhc implements com.good.gd.zwn.aqdzk {
            hbfhc() {
            }

            @Override // com.good.gd.zwn.aqdzk
            public void dbjc(boolean z, int i, String str) {
                if (z) {
                    yjh yjhVar = yjh.this;
                    yfdke.this.dbjc(yjhVar.dbjc, yjhVar.qkduk);
                    return;
                }
                yjh.this.qkduk.dbjc(false, 1002, str);
            }
        }

        yjh(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
            this.dbjc = file;
            this.qkduk = aqdzkVar;
        }

        @Override // com.good.gd.zwn.aqdzk
        public void dbjc(boolean z, int i, String str) {
            if (z) {
                String absolutePath = this.dbjc.getAbsolutePath();
                boolean qkduk = yfdke.this.qkduk.qkduk(absolutePath);
                com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Upload file operation completed successfully.");
                com.good.gd.kloes.ehnkx.qkduk(yfdke.this.ugfcv, String.format(Locale.getDefault(), "File %s was removed - %s", absolutePath, Boolean.valueOf(qkduk)));
            } else if (i == 1002) {
                yfdke.this.jwxax.dbjc(yfdke.this.wuird, new hbfhc());
            }
            this.qkduk.dbjc(z, i, str);
        }
    }

    public yfdke(Context context) {
        Class<?> cls = getClass();
        this.ugfcv = cls;
        this.iulf = context.getApplicationContext();
        com.good.gd.oqpvt.yfdke yfdkeVar = new com.good.gd.oqpvt.yfdke(context);
        this.rynix = yfdkeVar;
        com.good.gd.idl.hbfhc pqq = com.good.gd.idl.hbfhc.pqq();
        com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
        fdyxdVar.dbjc(yfdkeVar);
        yfdkeVar.mvf();
        pqq.dbjc(yfdkeVar);
        boolean jwxax = com.blackberry.bis.core.yfdke.sbesx().jwxax("AnalyticsEntitlementStatus");
        if (pqq.odlf()) {
            pqq.jwxax();
        }
        com.good.gd.ujgjo.hbfhc dbjc = com.blackberry.bis.core.yfdke.wuird().dbjc(context);
        this.qkduk = dbjc;
        if (mloj || dbjc != null) {
            fdyxdVar.xzwry();
            com.good.gd.kloes.hbfhc.jwxax(cls, "Register State Listener.");
            fdyxdVar.dbjc(new ooowe(context, fdyxdVar, pqq));
            if (!jwxax || !com.good.gd.whhmi.yfdke.lqox()) {
                return;
            }
            odlf();
            return;
        }
        throw new AssertionError();
    }

    private void mvf() {
        ScheduledExecutorService scheduledExecutorService = this.dbjc;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            com.good.gd.kloes.ehnkx.qkduk(this.ugfcv, "EventManager executor service is either null or shutdown. Restarting!!");
            this.dbjc = Executors.newSingleThreadScheduledExecutor();
        }
    }

    private void odlf() {
        mvf();
        this.dbjc.schedule(new ehnkx(), 0L, TimeUnit.MILLISECONDS);
    }

    protected abstract com.good.gd.oqpvt.hbfhc dbjc();

    protected abstract boolean dbjc(com.good.gd.whhmi.pmoiy pmoiyVar);

    protected abstract boolean jcpqe();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract com.good.gd.ghhwi.yfdke wxau();

    protected Runnable ztwf() {
        return new hbfhc(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void jsgtu(yfdke yfdkeVar) {
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Handle header changes.");
        if (!com.blackberry.bis.core.yfdke.sbesx().jwxax("AnalyticsEntitlementStatus") || !yfdkeVar.dbjc(com.blackberry.bis.core.yfdke.wpejt())) {
            return;
        }
        com.good.gd.kloes.hbfhc.jwxax(yfdkeVar.ugfcv, "Create new file as headers got changed.");
        yfdkeVar.qkduk.dbjc("historical");
        yfdkeVar.qkduk.dbjc("appIntelligence");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wuird() {
        if (this.qkduk.wxau()) {
            com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Analytics enabled and File system is ready. Flushing events to log store.");
            if (this.odlf.size() > 0) {
                synchronized (this.odlf) {
                    com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Flushing historical events to log store.");
                    mvf();
                    this.dbjc.schedule(new aqdzk(), 0L, TimeUnit.MILLISECONDS);
                }
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "There is no historical event to flush.");
            }
            if (this.yrp.size() <= 0) {
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Flushing app intelligence events to log store.");
            mvf();
            this.dbjc.schedule(new orlrx(), 0L, TimeUnit.MILLISECONDS);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "File system is not ready or Analytics status did not received from server or disabled.");
    }

    static /* synthetic */ void ztwf(yfdke yfdkeVar) {
        com.good.gd.ujgjo.hbfhc hbfhcVar = yfdkeVar.qkduk;
        if (hbfhcVar == null || !hbfhcVar.ztwf()) {
            return;
        }
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.jwxax("historical"));
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.lqox("historical"));
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.ztwf("historical"));
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.jwxax("appIntelligence"));
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.lqox("appIntelligence"));
        com.good.gd.ovnkx.mjbm.qkduk(yfdkeVar.qkduk.ztwf("appIntelligence"));
    }

    protected void jwxax() {
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Analytics Not Supported, De-initializing the resources due to analytics toggled to false.");
        if (this.bucpw) {
            mvf();
            this.dbjc.schedule(new com.good.gd.ujgjo.mjbm(this, false), 0L, TimeUnit.MILLISECONDS);
        }
        BlackberryAnalyticsCommon.rynix().odlf();
        BlackberryAnalyticsCommon.rynix().yrp();
        BlackberryAnalyticsCommon.rynix().mvf();
        com.good.gd.idl.hbfhc.pqq().qkduk();
        this.ztwf.set(false);
        this.lqox.set(false);
        this.liflu.set(false);
        this.jcpqe.clear();
        this.tlske.clear();
        this.jsgtu.clear();
        com.blackberry.bis.core.yfdke.sbesx().dbjc();
        com.blackberry.bis.core.ehnkx.qkduk();
        com.good.gd.zwn.ooowe oooweVar = this.jwxax;
        if (oooweVar != null) {
            oooweVar.qkduk();
            this.jwxax.dbjc(false);
            com.good.gd.ovnkx.gioey gioeyVar = this.mvf;
            if (gioeyVar != null) {
                gioeyVar.dbjc();
            }
            com.good.gd.ovnkx.yfdke yfdkeVar = this.wuird;
            if (yfdkeVar != null) {
                yfdkeVar.dbjc();
            }
            this.vfle.clear();
            this.jwxax.wuird();
        }
        com.good.gd.kloes.ehnkx.qkduk(this.ugfcv, "Shutdown event manager worker.");
        this.dbjc.shutdown();
        try {
            if (true != this.dbjc.awaitTermination(5000L, TimeUnit.MILLISECONDS)) {
                this.dbjc.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.dbjc.shutdownNow();
            Thread.currentThread().interrupt();
        }
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Clean up Event Log Store.");
        this.odlf.clear();
        com.good.gd.ujgjo.hbfhc hbfhcVar = this.qkduk;
        com.good.gd.ovnkx.mjbm.dbjc("historical", hbfhcVar.jwxax("historical"));
        com.good.gd.ovnkx.mjbm.dbjc("historical", hbfhcVar.lqox("historical"));
        com.good.gd.ovnkx.mjbm.dbjc("historical", hbfhcVar.ztwf("historical"));
        com.good.gd.ovnkx.mjbm.dbjc("appIntelligence", hbfhcVar.jwxax("appIntelligence"));
        com.good.gd.ovnkx.mjbm.dbjc("appIntelligence", hbfhcVar.lqox("appIntelligence"));
        com.good.gd.ovnkx.mjbm.dbjc("appIntelligence", hbfhcVar.ztwf("appIntelligence"));
        com.good.gd.ovnkx.mjbm.dbjc("historical", hbfhcVar.dbjc());
    }

    public void liflu() {
        mvf();
        this.dbjc.schedule(ztwf(), 0L, TimeUnit.MILLISECONDS);
    }

    public void lqox() {
        mvf();
        this.dbjc.schedule(new com.good.gd.ujgjo.aqdzk(this), 0L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void tlske() {
        com.good.gd.ovnkx.gioey gioeyVar = this.mvf;
        if (gioeyVar == null) {
            return;
        }
        boolean z = true;
        if (gioeyVar.qkduk()) {
            long j = 0;
            if (this.mvf.wxau() > -1) {
                long wxau = this.mvf.wxau();
                long j2 = uxw;
                if (wxau < j2) {
                    wxau = j2;
                } else if (wxau > this.jwxax.lqox()) {
                    wxau = this.jwxax.lqox();
                }
                if (wxau < 0) {
                    wxau = 0;
                }
                com.good.gd.kloes.ehnkx.qkduk(this.ugfcv, "Update Scheduler: Schedule Uploading Logs. UPLOAD FLAG: " + this.mvf.lqox());
                qkduk(new com.good.gd.ujgjo.pmoiy(this, null), wxau, TimeUnit.MILLISECONDS);
            }
            if (this.mvf.jwxax() <= -1) {
                return;
            }
            if (this.mvf.wxau() != -1) {
                z = false;
            }
            long jwxax = this.mvf.jwxax();
            long j3 = uxw;
            if (jwxax < j3) {
                jwxax = j3;
            } else if (jwxax > this.jwxax.ztwf()) {
                jwxax = this.jwxax.ztwf();
            }
            if (jwxax >= 0) {
                j = jwxax;
            }
            com.good.gd.kloes.ehnkx.qkduk(this.ugfcv, "Update Scheduler: Schedule Get Config. GET CONFIG FLAG: " + this.mvf.ztwf());
            dbjc(new com.good.gd.ujgjo.ehnkx(this, null, z), j, TimeUnit.MILLISECONDS);
            return;
        }
        com.good.gd.kloes.ehnkx.qkduk(this.ugfcv, "Update Scheduler: No Tasks Scheduled. GET CONFIG FLAG: " + this.mvf.ztwf());
        dbjc(new com.good.gd.ujgjo.ehnkx(this, null, true), uxw, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.good.gd.ujgjo.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0032yfdke implements mjbm.AbstractC0024mjbm {
        C0032yfdke() {
        }

        @Override // com.good.gd.ovnkx.mjbm.AbstractC0024mjbm
        public void dbjc(String str) {
            com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Move operation started.");
            com.good.gd.kloes.ehnkx.qkduk(yfdke.this.ugfcv, "Move operation : " + str);
            yfdke.this.qkduk.lqox();
        }

        @Override // com.good.gd.ovnkx.mjbm.AbstractC0024mjbm
        public void dbjc() {
            com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, "Move operation finished.");
            yfdke.this.qkduk.liflu();
            yfdke.ztwf(yfdke.this);
            yfdke.this.wuird();
        }

        @Override // com.good.gd.ovnkx.mjbm.AbstractC0024mjbm
        public void dbjc(int i, int i2) {
            com.good.gd.kloes.hbfhc.wxau(yfdke.this.ugfcv, String.format(Locale.getDefault(), "Move operation in progress %d/%d", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public void qkduk(com.good.gd.zwn.aqdzk aqdzkVar, long j, TimeUnit timeUnit) {
        if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue()) {
            com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, "Analytics Not Supported, Exit");
        } else if (true != this.mvf.lqox()) {
            com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, String.format(Locale.getDefault(), "Scheduling logs upload after delay: %d timeUnit: %s [in %d minute(s)] ", Long.valueOf(j), timeUnit, Long.valueOf(timeUnit.toMinutes(j))));
            this.mvf.qkduk(j, timeUnit);
            mvf();
            this.dbjc.schedule(new gioey(aqdzkVar), j, timeUnit);
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Upload request already scheduled.");
        }
    }

    static /* synthetic */ void dbjc(yfdke yfdkeVar, Context context) {
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Initializing the resources due to analytics toggled to true.");
        if (com.good.gd.whhmi.yfdke.lqox()) {
            yfdkeVar.mvf();
            yfdkeVar.dbjc.schedule(new ehnkx(), 0L, TimeUnit.MILLISECONDS);
            if (yfdkeVar.bucpw) {
                yfdkeVar.mvf();
                yfdkeVar.dbjc.schedule(new com.good.gd.ujgjo.mjbm(yfdkeVar, true), 0L, TimeUnit.MILLISECONDS);
            }
        }
        BlackberryAnalyticsCommon.rynix().jsgtu();
        BlackberryAnalyticsCommon.rynix().tlske();
        BlackberryAnalyticsCommon.rynix().wuird();
        if (yfdkeVar.wxau == null) {
            yfdkeVar.wxau = yfdkeVar.dbjc();
        }
        if (yfdkeVar.wuird == null) {
            if (com.blackberry.bis.core.yfdke.jsgtu() != null) {
                yfdkeVar.wuird = new com.good.gd.tpgyf.fdyxd();
            } else {
                throw null;
            }
        }
        if (yfdkeVar.mvf == null) {
            yfdkeVar.mvf = new com.good.gd.ovnkx.gioey();
        }
        if (yfdkeVar.jwxax == null) {
            zngm ujr = com.blackberry.bis.core.yfdke.ujr();
            com.good.gd.ovnkx.yfdke yfdkeVar2 = yfdkeVar.wuird;
            com.good.gd.oqpvt.yfdke yfdkeVar3 = yfdkeVar.rynix;
            com.good.gd.oqpvt.hbfhc hbfhcVar = yfdkeVar.wxau;
            if (ujr != null) {
                yfdkeVar.jwxax = new com.good.gd.npgvd.opjy(yfdkeVar2, yfdkeVar3, hbfhcVar);
            } else {
                throw null;
            }
        }
        yfdkeVar.wuird();
        yfdkeVar.jwxax.dbjc(yfdkeVar.wxau());
        if (true != yfdkeVar.jwxax.tlske()) {
            yfdkeVar.jwxax.liflu();
            yfdkeVar.jwxax.dbjc(true);
        }
        uxw = (!com.good.gd.whhmi.yfdke.lqox() || ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wpejt()) ? 5000 : 60000;
        yfdkeVar.tlske();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Long qkduk(String str) {
        com.good.gd.dvql.yfdke remove;
        remove = this.vfle.remove(str);
        return remove != null ? Long.valueOf(remove.dbjc()) : null;
    }

    public void qkduk() {
        mvf();
        this.dbjc.schedule(new fdyxd(), 0L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean qkduk(com.good.gd.zwn.aqdzk aqdzkVar) {
        if (true != BlackberryAnalyticsCommon.rynix().wuird) {
            com.good.gd.kloes.hbfhc.dbjc(this.ugfcv, "Cannot execute Request as App is in Background");
            BlackberryAnalyticsCommon.rynix().dbjc(true);
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(false, PointerIconCompat.TYPE_ALIAS, "Cannot execute Request as App is in Background");
            }
            return true;
        } else if (!this.qkduk.jwxax()) {
            return false;
        } else {
            com.good.gd.kloes.hbfhc.dbjc(this.ugfcv, "File System Unavailable.");
            if (aqdzkVar != null) {
                aqdzkVar.dbjc(false, 1005, "File System is locked.");
            }
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0208 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0209  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.blackberry.analytics.analyticsengine.AnalyticsResponse dbjc(String r12, String r13, String r14) {
        /*
            Method dump skipped, instructions count: 1057
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ujgjo.yfdke.dbjc(java.lang.String, java.lang.String, java.lang.String):com.blackberry.analytics.analyticsengine.AnalyticsResponse");
    }

    private boolean qkduk(SecurityEventType securityEventType, String str) {
        if (true == com.blackberry.bis.core.aqdzk.wxau() && true == com.good.gd.idl.hbfhc.pqq().odlf()) {
            switch (securityEventType.ordinal()) {
                case 0:
                    return this.lqox.get();
                case 1:
                    return this.liflu.get();
                case 2:
                    return this.jcpqe.contains(str);
                case 3:
                    return this.tlske.contains(str);
                case 4:
                    return this.jsgtu.contains(str);
                default:
                    return false;
            }
        }
        return false;
    }

    private void qkduk(com.good.gd.dvql.hbfhc hbfhcVar) {
        if (com.good.gd.idl.hbfhc.pqq().mvf()) {
            hbfhcVar.dbjc(true);
        } else {
            hbfhcVar.dbjc(false);
        }
    }

    static /* synthetic */ void qkduk(yfdke yfdkeVar, String str, com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.zwn.aqdzk aqdzkVar2, com.good.gd.zwn.yfdke yfdkeVar2, boolean z) {
        File wxau = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", yfdkeVar.qkduk.lqox("appIntelligence"));
        if (wxau != null && wxau.length() != 0) {
            yfdkeVar.dbjc("appIntelligence", wxau, aqdzkVar2, yfdkeVar2);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "No file in App Intelligence Upload directory, Moving App Intelligence Events Logs-->> Upload first.");
        File wxau2 = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", yfdkeVar.qkduk.jwxax("appIntelligence"));
        if (wxau2 != null && wxau2.length() != 0) {
            com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Uploading moved file App Intelligence Events Logs-->> Upload");
            yfdkeVar.dbjc("appIntelligence", wxau2);
            yfdkeVar.dbjc("appIntelligence", com.good.gd.ovnkx.mjbm.wxau("appIntelligence", yfdkeVar.qkduk.lqox("appIntelligence")), aqdzkVar2, yfdkeVar2);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "App Intelligence Directory Empty");
        if (z) {
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, 1008, str);
        } else if (aqdzkVar == null) {
        } else {
            aqdzkVar.dbjc(false, 1004, str);
        }
    }

    public void dbjc(AppUsageEventType appUsageEventType, JSONObject jSONObject) {
        if (jSONObject == null || jSONObject.length() < 0) {
            return;
        }
        int ordinal = appUsageEventType.ordinal();
        if (ordinal == 0) {
            com.good.gd.vnzf.yfdke.odlf().dbjc(new com.good.gd.dvql.hbfhc("continuousAuth", jSONObject));
        } else if (ordinal != 1) {
        } else {
            dbjc(jSONObject);
            wuird();
        }
    }

    public void dbjc(com.good.gd.zwn.aqdzk aqdzkVar, long j, TimeUnit timeUnit) {
        if (true != ((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue()) {
            return;
        }
        if (true != this.mvf.ztwf()) {
            com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, String.format(Locale.getDefault(), "Scheduling next update for GET Config after delay: %d timeUnit: %s [in %d minute(s)]", Long.valueOf(j), timeUnit, Long.valueOf(timeUnit.toMinutes(j))));
            this.mvf.dbjc(j, timeUnit);
            mvf();
            this.dbjc.schedule(new vzw(aqdzkVar), j, timeUnit);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "GetConfig request already scheduled.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(File file, com.good.gd.zwn.aqdzk aqdzkVar) {
        com.good.gd.kloes.ehnkx.dbjc(this.ugfcv, "Upload File: " + file.getAbsolutePath());
        yjh yjhVar = new yjh(file, aqdzkVar);
        byte[] qkduk = com.good.gd.ovnkx.mjbm.qkduk(file, this.qkduk);
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Archived Upload Events file size is: " + com.good.gd.ovnkx.mjbm.dbjc(file.length()));
        this.jwxax.dbjc(qkduk, yjhVar);
    }

    static /* synthetic */ void dbjc(yfdke yfdkeVar, com.good.gd.zwn.aqdzk aqdzkVar) {
        File[] listFiles;
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Prepare Historical Files and Corresponding Events for Upload.");
        com.blackberry.bis.core.pmoiy dbjc = com.blackberry.bis.core.hbfhc.dbjc();
        if (yfdkeVar.qkduk(aqdzkVar)) {
            com.good.gd.kloes.hbfhc.jwxax(yfdkeVar.ugfcv, "Application is in background or container gets locked.");
            return;
        }
        com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) dbjc;
        if (true == fdyxdVar.sbesx().booleanValue() && true == fdyxdVar.vfle().booleanValue()) {
            if (true != yfdkeVar.jwxax.jcpqe()) {
                com.good.gd.kloes.hbfhc.jwxax(yfdkeVar.ugfcv, "Internet connection not available.");
                if (aqdzkVar == null) {
                    return;
                }
                aqdzkVar.dbjc(false, PointerIconCompat.TYPE_HELP, "network is turned off, can't perform operation");
                return;
            }
            yfdkeVar.jcpqe();
            com.good.gd.ujgjo.orlrx orlrxVar = new com.good.gd.ujgjo.orlrx(yfdkeVar, aqdzkVar);
            com.good.gd.ujgjo.gioey gioeyVar = new com.good.gd.ujgjo.gioey(yfdkeVar, aqdzkVar, orlrxVar);
            File ztwf = yfdkeVar.qkduk.ztwf("historical");
            File file = (ztwf == null || (listFiles = ztwf.listFiles()) == null || listFiles.length == 0) ? null : listFiles[0];
            if (file == null || file.length() == 0) {
                File wxau = com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.lqox("historical"));
                if (wxau == null || wxau.length() == 0) {
                    File wxau2 = com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.jwxax("historical"));
                    if (wxau2 != null && wxau2.length() != 0) {
                        yfdkeVar.dbjc("historical", wxau2);
                        wxau = com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.lqox("historical"));
                    } else {
                        com.good.gd.kloes.hbfhc.dbjc(yfdkeVar.ugfcv, "Historical Event Logs Directory Empty");
                        if (!com.good.gd.ovnkx.mjbm.jsgtu("appIntelligence")) {
                            yfdkeVar.dbjc(aqdzkVar);
                            return;
                        } else if (aqdzkVar == null) {
                            return;
                        } else {
                            aqdzkVar.dbjc(false, PointerIconCompat.TYPE_CROSSHAIR, "No files in Historical directories.");
                            return;
                        }
                    }
                }
                yfdkeVar.dbjc("historical", wxau, gioeyVar, orlrxVar);
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, String.format("Found unsent file in [%s] folder", yfdkeVar.qkduk.ztwf("historical")));
            JSONObject dbjc2 = com.good.gd.ovnkx.mjbm.dbjc(file, yfdkeVar.qkduk);
            if (dbjc2 == null) {
                return;
            }
            BlackberryAnalyticsCommon.rynix().qkduk(file.getName().contains("4.0_") ? 3 : 2);
            com.good.gd.zwn.mjbm.lqox(com.good.gd.whhmi.mjbm.dbjc(dbjc2));
            yfdkeVar.dbjc(file, gioeyVar);
            return;
        }
        com.good.gd.kloes.hbfhc.jwxax(yfdkeVar.ugfcv, "Analytics not Supported or not configured yet, Exit.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void dbjc(yfdke yfdkeVar, String str, com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.zwn.aqdzk aqdzkVar2, com.good.gd.zwn.yfdke yfdkeVar2, boolean z) {
        File wxau = com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.lqox("historical"));
        if (wxau != null && wxau.length() != 0) {
            yfdkeVar.dbjc("historical", wxau, aqdzkVar2, yfdkeVar2);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "No historical file in Upload directory, Moving Event Logs-->> Upload first.");
        File wxau2 = com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.jwxax("historical"));
        if (wxau2 != null && wxau2.length() != 0) {
            com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Uploading moved file Event Logs-->> Upload");
            yfdkeVar.dbjc("historical", wxau2);
            yfdkeVar.dbjc("historical", com.good.gd.ovnkx.mjbm.wxau("historical", yfdkeVar.qkduk.lqox("historical")), aqdzkVar2, yfdkeVar2);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(yfdkeVar.ugfcv, "Event Logs Directory Empty");
        if (!com.good.gd.ovnkx.mjbm.jsgtu("appIntelligence")) {
            yfdkeVar.dbjc(aqdzkVar);
        } else if (z) {
            if (aqdzkVar == null) {
                return;
            }
            aqdzkVar.dbjc(false, 1008, str);
        } else if (aqdzkVar == null) {
        } else {
            aqdzkVar.dbjc(false, 1004, str);
        }
    }

    private void dbjc(String str, File file) {
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Move File to Upload Directory.");
        try {
            try {
                com.good.gd.ovnkx.mjbm.dbjc(file, this.qkduk.lqox(str), file.getName(), new C0032yfdke());
            } catch (IOException e) {
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Unable to move file to upload directory: " + e.getLocalizedMessage());
            }
        } finally {
            this.qkduk.liflu();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void dbjc(yfdke yfdkeVar, String str, ArrayDeque arrayDeque) {
        if (yfdkeVar != null) {
            com.good.gd.ovnkx.mjbm.dbjc(arrayDeque, new com.good.gd.ujgjo.fdyxd(yfdkeVar, str), str);
            return;
        }
        throw null;
    }

    public void dbjc(String str) {
        char c;
        String str2;
        ConcurrentHashMap concurrentHashMap;
        Long valueOf;
        int hashCode = str.hashCode();
        boolean z = true;
        if (hashCode == -2056513613) {
            if (str.equals("LAUNCH")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 64383879) {
            if (str.equals("CRASH")) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 81036673) {
            if (hashCode == 1925346054 && str.equals("ACTIVE")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("USAGE")) {
                c = 2;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                str2 = "AppLaunch";
                break;
            case 1:
                str2 = "AppActive";
                break;
            case 2:
                str2 = "AppInactive";
                break;
            case 3:
                str2 = "AppCrash";
                break;
            default:
                str2 = str;
                break;
        }
        com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, "Record Event: " + str2);
        com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
        if (!fdyxdVar.muee() && !fdyxdVar.sbesx().booleanValue()) {
            com.good.gd.kloes.hbfhc.dbjc(this.ugfcv, "BIS and Analytics feature disabled.");
            return;
        }
        com.good.gd.dvql.hbfhc wxau = com.blackberry.bis.core.ehnkx.wxau(str);
        wxau.qkduk(this.rynix.wuird());
        dbjc(wxau);
        if ("AppInactive".equals(str2)) {
            if (qkduk(SecurityEventType.SECURITY_APP_INACTIVE, (String) null)) {
                qkduk(wxau);
                com.good.gd.idl.hbfhc.pqq().dbjc(wxau);
            }
            ConcurrentHashMap<String, com.good.gd.dvql.yfdke> concurrentHashMap2 = this.vfle;
            if (concurrentHashMap2 != null && concurrentHashMap2.size() > 0) {
                synchronized (this) {
                    concurrentHashMap = new ConcurrentHashMap(this.vfle);
                    this.vfle.clear();
                }
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "App is going inactive, Inactive feature active records.");
                com.good.gd.idl.hbfhc pqq = com.good.gd.idl.hbfhc.pqq();
                ArrayDeque arrayDeque = new ArrayDeque();
                Long valueOf2 = Long.valueOf(System.currentTimeMillis());
                for (String str3 : concurrentHashMap.keySet()) {
                    if (str3 != null && true != str3.isEmpty() && (valueOf = Long.valueOf(((com.good.gd.dvql.yfdke) concurrentHashMap.get(str3)).dbjc())) != null && valueOf.longValue() != 0) {
                        boolean qkduk = qkduk(SecurityEventType.SECURITY_FEATURE_INACTIVE, str3);
                        com.good.gd.dvql.hbfhc dbjc = com.blackberry.bis.core.ehnkx.dbjc("FI", str3, null, valueOf2, valueOf);
                        dbjc.qkduk(this.rynix.wuird());
                        arrayDeque.add(dbjc);
                        if (qkduk) {
                            qkduk(dbjc);
                            pqq.dbjc(dbjc);
                        }
                    }
                }
                Iterator it = arrayDeque.iterator();
                while (it.hasNext()) {
                    dbjc((com.good.gd.dvql.hbfhc) it.next());
                }
                wuird();
            }
        } else if ("ACTIVE".equals(str)) {
            if (com.good.gd.whhmi.yfdke.ztwf() != com.blackberry.bis.core.yfdke.sbesx().jwxax("isLocationPermissionGranted")) {
                boolean ztwf = com.good.gd.whhmi.yfdke.ztwf();
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Updating Location Permission Status in PreUnlockStorage.");
                com.blackberry.bis.core.yfdke.sbesx().dbjc("isLocationPermissionGranted", ztwf);
                if (com.good.gd.whhmi.yfdke.ztwf()) {
                    com.good.gd.daq.hbfhc.qkduk().dbjc(false);
                }
            }
            boolean wxau2 = com.blackberry.bis.core.aqdzk.wxau();
            boolean odlf = com.good.gd.idl.hbfhc.pqq().odlf();
            if (wxau2 && odlf) {
                if (true == this.ztwf.get() && !qkduk(SecurityEventType.SECURITY_APP_ACTIVE, (String) null)) {
                    z = false;
                }
                if (z) {
                    qkduk(wxau);
                    com.good.gd.idl.hbfhc.pqq().dbjc(wxau);
                }
            }
            if (com.good.gd.idl.hbfhc.pqq() != null) {
                if (com.good.gd.whhmi.yfdke.ztwf()) {
                    com.blackberry.bis.core.yfdke.sbesx().dbjc("isNeverAskAgainSelected", false);
                }
                if (BlackberryAnalyticsCommon.rynix().jcpqe() && fdyxdVar.vfle().booleanValue()) {
                    BlackberryAnalyticsCommon.rynix().dbjc(false);
                    if (fdyxdVar.dbjc().booleanValue()) {
                        tlske();
                    }
                }
            } else {
                throw null;
            }
        }
        wuird();
    }

    private synchronized void dbjc(String str, com.good.gd.dvql.yfdke yfdkeVar) {
        this.vfle.put(str, yfdkeVar);
    }

    private void dbjc(String str, File file, com.good.gd.zwn.aqdzk aqdzkVar, com.good.gd.zwn.yfdke yfdkeVar) {
        JSONObject dbjc = com.good.gd.whhmi.mjbm.dbjc(this.iulf, file);
        if (dbjc != null) {
            String str2 = file.getName() + ".gzip";
            if (dbjc.has("schema")) {
                str2 = dbjc.optString("schema") + "_" + str2;
            }
            File file2 = new File(this.qkduk.ztwf(str), str2);
            for (int i = 1; i <= 3; i++) {
                boolean z = com.good.gd.ovnkx.mjbm.dbjc(this.qkduk, dbjc, file2.getAbsolutePath()) && com.good.gd.ovnkx.mjbm.dbjc(file2, this.qkduk) != null;
                com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, String.format("Trying to create GZIP with compression attempt(s): %s", Integer.valueOf(i)));
                if (z) {
                    com.good.gd.kloes.hbfhc.dbjc((Object) this.ugfcv, String.format("After compression file was tried to remove with status: %b", Boolean.valueOf(this.qkduk.qkduk(file.getAbsolutePath()))));
                    com.good.gd.kloes.ehnkx.dbjc(this.ugfcv, String.format("Removed file after compression : %s", file.getName()));
                    yfdkeVar.qkduk(file2, aqdzkVar);
                    return;
                }
                com.good.gd.kloes.hbfhc.qkduk(this.ugfcv, "GZIP compression is improper.");
                if (i == 3) {
                    this.qkduk.qkduk(file2.getAbsolutePath());
                    yfdkeVar.dbjc(file, aqdzkVar);
                }
            }
            return;
        }
        this.qkduk.qkduk(file.getAbsolutePath());
        com.good.gd.kloes.hbfhc.dbjc((Object) this.ugfcv, "Current file is empty, Uploading remaining files.");
        com.good.gd.kloes.ehnkx.dbjc(this.ugfcv, "Empty file : " + file.getName());
        aqdzkVar.dbjc(true, 1012, "File " + file.getName() + " is empty, Uploading remaining files.");
    }

    public void dbjc(SecurityEventType securityEventType, String str) {
        switch (securityEventType.ordinal()) {
            case 0:
                this.lqox.set(true);
                break;
            case 1:
                this.liflu.set(true);
                break;
            case 2:
                this.jcpqe.add(str);
                break;
            case 3:
                this.tlske.add(str);
                break;
            case 4:
                this.jsgtu.add(str);
                break;
        }
        if (true != this.ztwf.get()) {
            this.ztwf.set(true);
            com.good.gd.idl.hbfhc.pqq().dbjc();
        }
    }

    private void dbjc(com.good.gd.dvql.hbfhc hbfhcVar) {
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).sbesx().booleanValue()) {
            synchronized (this.odlf) {
                if (this.odlf.size() >= 40) {
                    com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Historical event queue capacity exceeded. Removed first object and added current object.");
                    this.odlf.remove();
                }
                if (this.odlf.add(hbfhcVar)) {
                    com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Event added to Historical Event Queue.");
                }
            }
        }
    }

    private void dbjc(JSONObject jSONObject) {
        synchronized (this.yrp) {
            if (this.yrp.size() >= 40) {
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "App Intelligence Event Queue capacity exceeded. Removed first object and added current object.");
                this.yrp.remove();
            }
            if (this.yrp.add(new com.good.gd.dvql.hbfhc("appIntelligence", jSONObject))) {
                com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Event added to App Intelligence Event Queue.");
            }
        }
    }

    public void dbjc(com.good.gd.zwn.aqdzk aqdzkVar) {
        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "Prepare App Intelligence Files and Corresponding Events for Upload.");
        if (com.good.gd.ovnkx.mjbm.jsgtu("appIntelligence")) {
            com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, "There is no app intelligence event file to upload.");
            return;
        }
        com.blackberry.analytics.analyticsengine.fdyxd fdyxdVar = (com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc();
        if (true == fdyxdVar.sbesx().booleanValue() && true == fdyxdVar.vfle().booleanValue()) {
            if (true != this.jwxax.jcpqe()) {
                com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, "Internet connection not available.");
                if (aqdzkVar == null) {
                    return;
                }
                aqdzkVar.dbjc(false, PointerIconCompat.TYPE_HELP, "network is turned off, can't perform operation");
                return;
            }
            jcpqe();
            pmoiy pmoiyVar = new pmoiy(aqdzkVar);
            mjbm mjbmVar = new mjbm(aqdzkVar, pmoiyVar);
            File wxau = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", this.qkduk.ztwf("appIntelligence"));
            if (wxau == null || wxau.length() == 0) {
                File wxau2 = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", this.qkduk.lqox("appIntelligence"));
                if (wxau2 == null || wxau2.length() == 0) {
                    File wxau3 = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", this.qkduk.jwxax("appIntelligence"));
                    if (wxau3 != null && wxau3.length() != 0) {
                        dbjc("appIntelligence", wxau3);
                        wxau2 = com.good.gd.ovnkx.mjbm.wxau("appIntelligence", this.qkduk.lqox("appIntelligence"));
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(this.ugfcv, "App Intelligence Events Logs Directory Empty.");
                        return;
                    }
                }
                dbjc("appIntelligence", wxau2, mjbmVar, pmoiyVar);
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(this.ugfcv, String.format("Found unsent file in [%s] folder", this.qkduk.ztwf("appIntelligence")));
            JSONObject dbjc = com.good.gd.ovnkx.mjbm.dbjc(wxau, this.qkduk);
            if (dbjc == null) {
                return;
            }
            BlackberryAnalyticsCommon.rynix().qkduk(wxau.getName().contains("4.0_") ? 3 : 2);
            com.good.gd.zwn.mjbm.lqox(com.good.gd.whhmi.mjbm.dbjc(dbjc));
            dbjc(wxau, mjbmVar);
            return;
        }
        com.good.gd.kloes.hbfhc.jwxax(this.ugfcv, "Analytics not Supported or not configured yet, Exit.");
    }
}
