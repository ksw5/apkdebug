package com.good.gd.xohxj;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.blackberry.bis.core.aqdzk;
import com.good.gd.kloes.ehnkx;
import com.good.gd.xohxj.hbfhc;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class yfdke extends hbfhc implements LocationListener {
    private static final String rynix = yfdke.class.getName();
    private static yfdke ugfcv;
    private LocationManager odlf;
    private boolean yrp = false;
    private boolean vfle = false;
    private Consumer<Location> iulf = new hbfhc();

    /* loaded from: classes.dex */
    class hbfhc implements Consumer<Location> {
        hbfhc() {
        }

        @Override // java.util.function.Consumer
        public void accept(Location location) {
            yfdke.this.qkduk(location);
        }
    }

    private yfdke() {
        if (aqdzk.dbjc() >= 30) {
            this.lqox = com.good.gd.daq.hbfhc.jwxax();
        } else {
            this.ztwf = com.good.gd.daq.hbfhc.wxau();
        }
    }

    public static com.good.gd.xohxj.hbfhc jsgtu() {
        if (ugfcv == null) {
            ugfcv = new yfdke();
        }
        return ugfcv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void qkduk(Location location) {
        String str;
        if (location != null) {
            if ("gps".equals(location.getProvider())) {
                str = "gps_provider";
            } else if ("network".equals(location.getProvider())) {
                str = "network_provider";
            }
            dbjc(location, str);
        }
        str = null;
        dbjc(location, str);
    }

    @Override // com.good.gd.xohxj.hbfhc
    public synchronized void jcpqe() {
        boolean isProviderEnabled;
        boolean z = this.yrp;
        if (z && this.vfle) {
            com.good.gd.kloes.hbfhc.wxau(rynix, this.liflu + ": Location request/ monitoring is already executing through both GPS and Network.");
        } else {
            if (true != z) {
                if (com.good.gd.daq.hbfhc.ztwf()) {
                    dbjc("gps");
                } else {
                    com.good.gd.kloes.hbfhc.ztwf(rynix, this.liflu + ": GPS Provider is not enabled.");
                }
            }
            if (true != this.vfle) {
                LocationManager locationManager = (LocationManager) BlackberryAnalyticsCommon.rynix().jwxax().getSystemService("location");
                if (locationManager == null) {
                    com.good.gd.kloes.hbfhc.ztwf("AnalyticsLocationFactory", "Unable to instantiate LocationManager.");
                    isProviderEnabled = false;
                } else {
                    isProviderEnabled = locationManager.isProviderEnabled("network");
                }
                if (isProviderEnabled) {
                    dbjc("network");
                } else {
                    com.good.gd.kloes.hbfhc.ztwf(rynix, this.liflu + ": Network Provider is not enabled.");
                }
            }
        }
    }

    @Override // com.good.gd.xohxj.hbfhc
    public boolean lqox() {
        boolean z = this.yrp || this.vfle;
        this.jwxax = z;
        return z;
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        qkduk(location);
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
        ehnkx.dbjc(rynix, this.liflu + ": On Provider Disabled: " + str);
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
        ehnkx.dbjc(rynix, this.liflu + ": On Provider Enabled: " + str);
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        ehnkx.dbjc(rynix, this.liflu + ": On Status Changed: " + str + "Status: " + i);
    }

    @Override // com.good.gd.xohxj.hbfhc
    public void tlske() {
        synchronized (this) {
            this.yrp = false;
            this.vfle = false;
            if (this.odlf != null) {
                com.good.gd.kloes.hbfhc.jwxax(rynix, this.liflu + ": Location Update Stopped.");
                this.odlf.removeUpdates(this);
            }
        }
        if (true != com.good.gd.daq.hbfhc.dbjc()) {
            com.good.gd.kloes.hbfhc.jwxax(rynix, this.liflu + ": Location fetching ceased as Device Location Services are disabled.");
            dbjc(hbfhc.yfdke.APPROVAL_DENIED, null, null);
        }
    }

    @Override // com.good.gd.xohxj.hbfhc
    public synchronized void dbjc(Location location, String str) {
        if (location != null) {
            ehnkx.dbjc(rynix, this.liflu + ": Analytics received location.");
        } else {
            com.good.gd.kloes.hbfhc.ztwf(rynix, this.liflu + ": Analytics received null location.");
        }
        dbjc(location);
        dbjc(hbfhc.yfdke.LOCATION_RECEIVED, location, str);
        if (str != null && str.equals("gps_provider")) {
            tlske();
        } else if (str != null && this.yrp && str.equals("network_provider")) {
            this.vfle = false;
        } else {
            tlske();
        }
    }

    private void dbjc(String str) {
        String str2;
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(rynix, this.liflu + ": Requesting Location Monitoring using GPS provider.");
            return;
        }
        if (this.odlf == null) {
            this.odlf = (LocationManager) this.dbjc.getSystemService("location");
        }
        if (str.equals("gps")) {
            this.yrp = true;
            str2 = "GPS";
        } else if (str.equals("network")) {
            this.vfle = true;
            str2 = "Network";
        } else {
            com.good.gd.kloes.hbfhc.wxau(rynix, "Unidentified provider, unable to initiate location request.");
            return;
        }
        if (aqdzk.dbjc() >= 30) {
            if (this.lqox) {
                this.odlf.getCurrentLocation(str, null, BlackberryAnalyticsCommon.rynix().jwxax().getMainExecutor(), this.iulf);
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(rynix, this.liflu + ": Requesting Location Monitoring using " + str2 + " provider.");
            this.odlf.requestLocationUpdates(str, 60000L, 10.0f, this, Looper.getMainLooper());
        } else if (this.ztwf) {
            com.good.gd.kloes.hbfhc.wxau(rynix, this.liflu + ": Requesting Single Location Update using " + str2 + " provider.");
            this.odlf.requestSingleUpdate(str, this, Looper.getMainLooper());
        } else {
            com.good.gd.kloes.hbfhc.wxau(rynix, this.liflu + ": Requesting Location Monitoring using " + str2 + " provider.");
            this.odlf.requestLocationUpdates(str, 60000L, 10.0f, this, Looper.getMainLooper());
        }
    }
}
