package com.good.gd.xohxj;

import android.os.Looper;
import com.good.gd.xohxj.hbfhc;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/* loaded from: classes.dex */
public final class fdyxd extends hbfhc {
    private static fdyxd iulf;
    private LocationRequest odlf;
    private final LocationCallback vfle = new hbfhc();
    private FusedLocationProviderClient yrp;

    /* loaded from: classes.dex */
    class hbfhc extends LocationCallback {
        hbfhc() {
        }

        @Override // com.google.android.gms.location.LocationCallback
        public void onLocationResult(LocationResult locationResult) {
            fdyxd.this.dbjc(locationResult != null ? locationResult.getLastLocation() : null, "fused_provider");
        }
    }

    private fdyxd() {
        LocationRequest locationRequest = new LocationRequest();
        this.odlf = locationRequest;
        locationRequest.setInterval(60000L);
        this.odlf.setFastestInterval(60000L);
        this.odlf.setPriority(100);
    }

    public static com.good.gd.xohxj.hbfhc wuird() {
        if (iulf == null) {
            iulf = new fdyxd();
        }
        return iulf;
    }

    @Override // com.good.gd.xohxj.hbfhc
    public synchronized void jcpqe() {
        if (this.jwxax) {
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, this.liflu + ": Location monitoring is already executing.");
        } else {
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, this.liflu + ": Start Google Location Monitoring.");
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.dbjc);
            this.yrp = fusedLocationProviderClient;
            if (fusedLocationProviderClient != null) {
                fusedLocationProviderClient.requestLocationUpdates(this.odlf, this.vfle, Looper.getMainLooper());
                this.jwxax = true;
            } else {
                com.good.gd.kloes.hbfhc.dbjc(this.jsgtu, this.liflu + ": Unable to instantiate FusedLocationProviderClient.");
            }
        }
    }

    public LocationRequest jsgtu() {
        return this.odlf;
    }

    @Override // com.good.gd.xohxj.hbfhc
    public void tlske() {
        synchronized (this) {
            this.jwxax = false;
            if (this.yrp != null) {
                com.good.gd.kloes.hbfhc.jwxax(this.jsgtu, this.liflu + ": Stop Google Location Monitoring.");
                this.yrp.removeLocationUpdates(this.vfle);
            }
        }
        if (true != com.good.gd.daq.hbfhc.dbjc()) {
            com.good.gd.kloes.hbfhc.jwxax(this.jsgtu, this.liflu + " : Location fetching ceased as Device Location Services are disabled.");
            dbjc(hbfhc.yfdke.APPROVAL_DENIED, null, null);
        }
    }
}
