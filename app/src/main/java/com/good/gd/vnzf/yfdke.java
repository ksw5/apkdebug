package com.good.gd.vnzf;

import android.location.Location;
import android.os.SystemClock;
import com.good.gd.kloes.ehnkx;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class yfdke implements com.good.gd.daq.yfdke {
    private static yfdke wuird;
    private ScheduledFuture dbjc;
    private ScheduledFuture jcpqe;
    private com.good.gd.cth.hbfhc lqox;
    private static final ArrayDeque<com.good.gd.dvql.hbfhc> tlske = new ArrayDeque<>();
    private static final String jsgtu = yfdke.class.getSimpleName();
    private ScheduledExecutorService qkduk = Executors.newSingleThreadScheduledExecutor();
    private boolean jwxax = false;
    private AtomicBoolean wxau = new AtomicBoolean(false);
    private boolean ztwf = false;
    private boolean liflu = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            yfdke.qkduk(yfdke.this);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ Location dbjc;

        hbfhc(Location location) {
            this.dbjc = location;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (yfdke.this.lqox != null) {
                yfdke.this.lqox.dbjc(false);
                yfdke.this.lqox.dbjc(this.dbjc);
                yfdke.this.lqox.dbjc(0L);
            }
            yfdke.this.tlske();
        }
    }

    /* renamed from: com.good.gd.vnzf.yfdke$yfdke  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0033yfdke implements Runnable {
        RunnableC0033yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (yfdke.this.lqox != null) {
                yfdke.this.lqox.dbjc(false);
                yfdke.this.lqox.dbjc((Location) null);
                yfdke.this.lqox.dbjc(0L);
            }
            yfdke.this.tlske();
        }
    }

    private yfdke() {
    }

    private void iulf() {
        ScheduledFuture scheduledFuture = this.dbjc;
        if (scheduledFuture != null) {
            if (scheduledFuture.cancel(false)) {
                com.good.gd.kloes.hbfhc.wxau(jsgtu, "[CA] Continuous auth events wait timer is stopped.");
                this.jwxax = false;
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "[CA] Continuous auth events wait timer is already stopped.");
        }
    }

    private void mvf() {
        ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = tlske;
        if (arrayDeque != null) {
            synchronized (arrayDeque) {
                arrayDeque.clear();
            }
        }
    }

    public static yfdke odlf() {
        if (wuird == null) {
            synchronized (yfdke.class) {
                if (wuird == null) {
                    wuird = new yfdke();
                }
            }
        }
        return wuird;
    }

    private void vfle() {
        yrp();
        if (true != this.liflu) {
            this.liflu = true;
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Starting timer to wait for location of CA events upload batch.");
            this.jcpqe = this.qkduk.schedule(new fdyxd(), 30L, TimeUnit.SECONDS);
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(jsgtu, "Timer to wait for location of CA events upload batch is already running.");
    }

    private void wuird() {
        if (this.liflu) {
            ScheduledFuture scheduledFuture = this.jcpqe;
            if (scheduledFuture == null) {
                return;
            }
            if (scheduledFuture.cancel(false)) {
                com.good.gd.kloes.hbfhc.wxau(jsgtu, "Timer to wait for location of CA events upload batch has been cancelled.");
                this.liflu = false;
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Timer to wait for location CA events upload batch is already cancelled.");
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(jsgtu, "Timer to wait for location of CA events upload batch is not running.");
    }

    private void yrp() {
        ScheduledExecutorService scheduledExecutorService = this.qkduk;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ehnkx.dbjc(jsgtu, "App usage executor service is either null or shutdown. Restarting!!");
            this.qkduk = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public void jcpqe() {
        this.lqox = null;
    }

    public void jsgtu() {
        vfle();
        com.good.gd.idl.hbfhc pqq = com.good.gd.idl.hbfhc.pqq();
        if (pqq.yrp()) {
            pqq.wxau();
        }
    }

    public com.good.gd.cth.hbfhc jwxax() {
        return this.lqox;
    }

    public ArrayList<com.good.gd.dvql.hbfhc> liflu() {
        ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = tlske;
        if (arrayDeque == null) {
            return null;
        }
        ArrayList<com.good.gd.dvql.hbfhc> arrayList = new ArrayList<>();
        int i = 10;
        if (10 > arrayDeque.size()) {
            i = arrayDeque.size();
        }
        synchronized (arrayDeque) {
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(tlske.pollFirst());
            }
        }
        return arrayList;
    }

    public boolean lqox() {
        return this.liflu;
    }

    @Override // com.good.gd.daq.yfdke
    public void qkduk() {
        if (true != this.liflu) {
            return;
        }
        com.good.gd.kloes.hbfhc.ztwf(jsgtu, "Location request is denied for CA events upload batch.");
        wuird();
        com.good.gd.xohxj.hbfhc qkduk = com.good.gd.daq.hbfhc.qkduk();
        if (qkduk != null) {
            qkduk.qkduk(false);
        }
        this.liflu = false;
        yrp();
        this.qkduk.execute(new RunnableC0033yfdke());
    }

    public synchronized void tlske() {
        if (this.jwxax) {
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Event upload wait timer is already running for Continuous Auth events.");
        } else if (this.liflu) {
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Location fetching is already in progress for CA events upload batch.");
        } else {
            ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = tlske;
            if (arrayDeque != null && !arrayDeque.isEmpty()) {
                if (this.lqox == null) {
                    this.lqox = new com.good.gd.cth.hbfhc();
                    this.lqox.dbjc(com.good.gd.idl.hbfhc.pqq().mvf());
                }
                com.good.gd.idl.hbfhc.pqq().muee();
                return;
            }
            com.good.gd.kloes.hbfhc.ztwf(jsgtu, "No event(s) in Continuous Auth queue.");
        }
    }

    public boolean wxau() {
        return true == this.ztwf && tlske.size() > 0;
    }

    public boolean ztwf() {
        return this.ztwf;
    }

    public void dbjc(com.good.gd.cth.hbfhc hbfhcVar) {
        this.lqox = hbfhcVar;
    }

    public void dbjc(com.good.gd.dvql.hbfhc hbfhcVar) {
        String str;
        if (hbfhcVar == null) {
            com.good.gd.kloes.hbfhc.ztwf(jsgtu, "[CA] Cannot add null event.");
            return;
        }
        ArrayDeque<com.good.gd.dvql.hbfhc> arrayDeque = tlske;
        synchronized (arrayDeque) {
            if (40 <= arrayDeque.size()) {
                arrayDeque.removeFirst();
                com.good.gd.kloes.hbfhc.wxau(jsgtu, "[CA] Event queue capacity exceeded, removed first event and adding current event.");
            }
            arrayDeque.add(hbfhcVar);
            str = jsgtu;
            com.good.gd.kloes.hbfhc.wxau(str, "[CA] Event queue size: " + arrayDeque.size());
        }
        com.good.gd.kloes.hbfhc.jwxax(str, "[CA] Event Added.");
        synchronized (this) {
            if (10 <= arrayDeque.size()) {
                iulf();
                tlske();
            } else {
                yrp();
                if (true != this.jwxax) {
                    this.jwxax = true;
                    com.good.gd.kloes.hbfhc.wxau(str, "[CA] Starting continuous auth events wait timer.");
                    this.dbjc = this.qkduk.schedule(new com.good.gd.vnzf.hbfhc(this), 60L, TimeUnit.SECONDS);
                } else {
                    com.good.gd.kloes.hbfhc.wxau(str, "[CA] Events wait timer is already running.");
                }
            }
        }
    }

    static /* synthetic */ void qkduk(yfdke yfdkeVar) {
        if (yfdkeVar != null) {
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Timer to wait for location of CA events upload batch is expired.");
            yfdkeVar.liflu = false;
            com.good.gd.cth.hbfhc hbfhcVar = yfdkeVar.lqox;
            if (hbfhcVar != null) {
                hbfhcVar.dbjc(false);
            }
            Location dbjc = com.good.gd.daq.hbfhc.qkduk().dbjc();
            if (dbjc != null) {
                long elapsedRealtime = SystemClock.elapsedRealtime() - TimeUnit.NANOSECONDS.toMillis(dbjc.getElapsedRealtimeNanos());
                com.good.gd.cth.hbfhc hbfhcVar2 = yfdkeVar.lqox;
                if (hbfhcVar2 != null) {
                    hbfhcVar2.dbjc(dbjc);
                    yfdkeVar.lqox.dbjc(elapsedRealtime);
                }
            } else {
                com.good.gd.cth.hbfhc hbfhcVar3 = yfdkeVar.lqox;
                if (hbfhcVar3 != null) {
                    hbfhcVar3.dbjc((Location) null);
                    yfdkeVar.lqox.dbjc(0L);
                }
            }
            yfdkeVar.tlske();
            com.good.gd.idl.hbfhc.pqq().gbb();
            return;
        }
        throw null;
    }

    public void dbjc(boolean z) {
        this.ztwf = z;
        if (true != z) {
            mvf();
            iulf();
            ehnkx.dbjc(jsgtu, "Shutdown app usage event manager worker.");
            this.qkduk.shutdown();
            try {
                if (true != this.qkduk.awaitTermination(5000L, TimeUnit.MILLISECONDS)) {
                    this.qkduk.shutdownNow();
                }
            } catch (InterruptedException e) {
                this.qkduk.shutdownNow();
                Thread.currentThread().interrupt();
            }
            this.lqox = null;
            this.wxau.set(false);
            this.jwxax = false;
            com.good.gd.daq.hbfhc.qkduk().dbjc(this);
            return;
        }
        com.good.gd.daq.hbfhc.qkduk().qkduk(this);
    }

    @Override // com.good.gd.daq.yfdke
    public void dbjc(Location location, String str) {
        if (true != this.liflu) {
            return;
        }
        wuird();
        this.liflu = false;
        yrp();
        this.qkduk.execute(new hbfhc(location));
    }

    @Override // com.good.gd.daq.yfdke
    public void dbjc() {
        boolean z = this.liflu;
        if (true != z) {
            return;
        }
        if (z) {
            com.good.gd.kloes.hbfhc.wxau(jsgtu, "Restarting timer to wait for location of CA events upload batch.");
            wuird();
            vfle();
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(jsgtu, "Cannot restart timer to wait for location of CA events upload batch as it is already expired or cancelled.");
    }
}
