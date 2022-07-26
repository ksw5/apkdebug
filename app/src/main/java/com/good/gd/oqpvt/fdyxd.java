package com.good.gd.oqpvt;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class fdyxd extends ConnectivityManager.NetworkCallback {
    private com.good.gd.oqpvt.hbfhc lqox;
    private final com.good.gd.oqpvt.hbfhc ztwf;
    private final Class jwxax = fdyxd.class;
    private int wxau = 0;
    private final NetworkRequest dbjc = new NetworkRequest.Builder().addTransportType(0).addTransportType(1).build();
    private final ConnectivityManager qkduk = (ConnectivityManager) BlackberryAnalyticsCommon.rynix().jwxax().getSystemService("connectivity");

    /* loaded from: classes.dex */
    class hbfhc implements com.good.gd.oqpvt.hbfhc {
        hbfhc() {
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void dbjc(boolean z) {
            com.good.gd.kloes.hbfhc.wxau(fdyxd.this.jwxax, "A network is connected.");
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void jwxax() {
            com.good.gd.kloes.hbfhc.wxau(fdyxd.this.jwxax, "Internet connection established.");
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void qkduk() {
            com.good.gd.kloes.hbfhc.wxau(fdyxd.this.jwxax, "Internet connection disconnected.");
        }

        @Override // com.good.gd.oqpvt.hbfhc
        public void dbjc() {
            com.good.gd.kloes.hbfhc.wxau(fdyxd.this.jwxax, "Current network is lost.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public fdyxd() {
        hbfhc hbfhcVar = new hbfhc();
        this.ztwf = hbfhcVar;
        this.lqox = hbfhcVar;
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(Network network) {
        boolean z = false;
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Access Network State permission not granted, cannot check WiFi network connection.");
        } else {
            try {
                NetworkCapabilities networkCapabilities = this.qkduk.getNetworkCapabilities(network);
                if (networkCapabilities != null && networkCapabilities.hasTransport(1)) {
                    if (true != this.qkduk.isActiveNetworkMetered()) {
                        z = true;
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Connected to a mobile hotspot network.");
                    }
                }
            } catch (SecurityException e) {
                com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Security Exception while checking if connected network is a WiFi connection: " + e.getLocalizedMessage());
            }
        }
        this.lqox.dbjc(z);
        int i = this.wxau + 1;
        this.wxau = i;
        if (i == 1) {
            this.lqox.jwxax();
        }
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(Network network) {
        this.wxau--;
        this.lqox.dbjc();
        if (this.wxau == 0) {
            this.lqox.qkduk();
        }
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onUnavailable() {
        super.onUnavailable();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void qkduk() {
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Access Network State permission not granted, cannot un-registered network state callbacks.");
            return;
        }
        try {
            this.qkduk.unregisterNetworkCallback(this);
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Security Exception while un-registering network state callbacks: " + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc() {
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Access Network State permission not granted, cannot registered network state callbacks.");
            return;
        }
        try {
            this.qkduk.registerNetworkCallback(this.dbjc, this);
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jwxax, "Security Exception while registering network state callbacks: " + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dbjc(com.good.gd.oqpvt.hbfhc hbfhcVar) {
        if (hbfhcVar != null) {
            this.lqox = hbfhcVar;
        }
    }
}
