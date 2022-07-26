package com.good.gd.xohxj;

import android.content.Context;
import android.location.Location;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.SIS.BISPreUnlockStorage.BISPreUnlockStorageManager;
import com.good.gd.kloes.ehnkx;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public abstract class hbfhc {
    private volatile ArrayList<com.good.gd.daq.yfdke> tlske;
    private boolean wuird;
    private boolean wxau;
    boolean jwxax = false;
    boolean ztwf = false;
    boolean lqox = false;
    private Location jcpqe = null;
    final Class jsgtu = hbfhc.class;
    final com.good.gd.wwiqd.hbfhc mvf = new C0035hbfhc();
    Context dbjc = BlackberryAnalyticsCommon.rynix().jwxax();
    private ExecutorService qkduk = Executors.newSingleThreadExecutor();
    String liflu = jwxax();

    /* renamed from: com.good.gd.xohxj.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class C0035hbfhc implements com.good.gd.wwiqd.hbfhc {

        /* renamed from: com.good.gd.xohxj.hbfhc$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class RunnableC0036hbfhc implements Runnable {
            final /* synthetic */ boolean dbjc;

            RunnableC0036hbfhc(boolean z) {
                this.dbjc = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                BISPreUnlockStorageManager sbesx = com.blackberry.bis.core.yfdke.sbesx();
                sbesx.dbjc("isLocationPermissionGranted", this.dbjc);
                boolean jwxax = sbesx.jwxax("BISEntitlementStatus");
                if (this.dbjc) {
                    com.good.gd.kloes.hbfhc.wxau(hbfhc.this.jsgtu, hbfhc.this.liflu + ": On Permission Granted.");
                    if (!jwxax) {
                        return;
                    }
                    if (com.good.gd.daq.hbfhc.dbjc()) {
                        if (!hbfhc.this.wxau()) {
                            return;
                        }
                        hbfhc.this.dbjc(yfdke.LOCATION_PERMISSION_ALLOWED, null, null);
                        hbfhc.this.jcpqe();
                        return;
                    }
                    com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.jsgtu, hbfhc.this.liflu + ": Cannot request/ monitor location update since device location services are disabled.");
                    return;
                }
                hbfhc.this.dbjc(yfdke.APPROVAL_DENIED, null, null);
                com.good.gd.kloes.hbfhc.dbjc(hbfhc.this.jsgtu, hbfhc.this.liflu + ": On Permission Denied.");
            }
        }

        C0035hbfhc() {
        }

        @Override // com.good.gd.wwiqd.hbfhc
        public void onLocationPermissionUpdated(boolean z) {
            hbfhc.dbjc(hbfhc.this);
            hbfhc.this.qkduk.execute(new RunnableC0036hbfhc(z));
        }

        @Override // com.good.gd.wwiqd.hbfhc
        public void onLocationSettingsUpdated(boolean z) {
            com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.jsgtu, hbfhc.this.liflu + ": On Setting Updated - " + z);
            if (z) {
                if (com.good.gd.idl.hbfhc.pqq().yrp()) {
                    if (!hbfhc.this.wxau()) {
                        return;
                    }
                    hbfhc.this.dbjc(yfdke.LOCATION_PERMISSION_ALLOWED, null, null);
                    hbfhc.this.jcpqe();
                    return;
                }
                com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.jsgtu, hbfhc.this.liflu + ": Cannot request/ monitor location update since device location services are disabled.");
            } else if (true == com.good.gd.daq.hbfhc.dbjc()) {
            } else {
                hbfhc.this.dbjc(yfdke.APPROVAL_DENIED, null, null);
            }
        }
    }

    /* loaded from: classes.dex */
    public enum yfdke {
        LOCATION_RECEIVED,
        LOCATION_REQUEST_DENIED,
        APPROVAL_DENIED,
        LOCATION_PERMISSION_ALLOWED
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public hbfhc() {
        this.wuird = false;
        this.wuird = com.blackberry.bis.core.yfdke.sbesx().jwxax("isLocationDenyOptionSelectedExplicitLy");
    }

    public void dbjc(boolean z) {
        this.wuird = z;
        com.blackberry.bis.core.yfdke.sbesx().dbjc("isLocationDenyOptionSelectedExplicitLy", z);
    }

    public abstract void jcpqe();

    public String jwxax() {
        return this instanceof fdyxd ? "Google Location Provider" : this.lqox ? "Framework Location, Get Current Location API" : this.ztwf ? "Framework Location, Single Request API" : "Framework Location Provider";
    }

    public void liflu() {
        synchronized (this) {
            if (this.tlske != null) {
                this.tlske.clear();
            }
        }
    }

    public boolean lqox() {
        return this.jwxax;
    }

    public abstract void tlske();

    public boolean wxau() {
        return this.wxau;
    }

    public boolean ztwf() {
        return this.wuird;
    }

    public void qkduk(boolean z) {
        this.wxau = z;
    }

    public com.good.gd.wwiqd.hbfhc qkduk() {
        return this.mvf;
    }

    public void qkduk(com.good.gd.daq.yfdke yfdkeVar) {
        if (this.tlske == null) {
            synchronized (this) {
                if (this.tlske == null) {
                    this.tlske = new ArrayList<>();
                }
            }
        }
        if (true != this.tlske.contains(yfdkeVar)) {
            this.tlske.add(yfdkeVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void dbjc(Location location, String str) {
        if (location != null) {
            ehnkx.qkduk(this.jsgtu, this.liflu + ": Analytics received location.");
        } else {
            com.good.gd.kloes.hbfhc.dbjc(this.jsgtu, this.liflu + ": Analytics received null location.");
        }
        this.jcpqe = location;
        dbjc(yfdke.LOCATION_RECEIVED, location, str);
        tlske();
    }

    public Location dbjc() {
        return this.jcpqe;
    }

    public void dbjc(Location location) {
        this.jcpqe = location;
    }

    public void dbjc(com.good.gd.daq.yfdke yfdkeVar) {
        synchronized (this) {
            if (this.tlske != null) {
                this.tlske.remove(yfdkeVar);
            }
        }
    }

    public void dbjc(yfdke yfdkeVar, Location location, String str) {
        if (this.tlske == null || this.tlske.size() <= 0) {
            return;
        }
        Iterator<com.good.gd.daq.yfdke> it = this.tlske.iterator();
        while (it.hasNext()) {
            com.good.gd.daq.yfdke next = it.next();
            if (next != null) {
                switch (yfdkeVar.ordinal()) {
                    case 0:
                        next.dbjc(location, str);
                        continue;
                    case 1:
                    case 2:
                        next.qkduk();
                        continue;
                    case 3:
                        next.dbjc();
                        continue;
                }
            }
        }
    }

    static /* synthetic */ void dbjc(hbfhc hbfhcVar) {
        ExecutorService executorService = hbfhcVar.qkduk;
        if (executorService == null || executorService.isShutdown()) {
            ehnkx.qkduk(hbfhcVar.jsgtu, hbfhcVar.liflu + ": Geo-Location Executor Service is either null or shutdown. Restarting!!");
            hbfhcVar.qkduk = Executors.newSingleThreadExecutor();
        }
    }
}
