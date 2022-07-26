package com.good.gd.oqpvt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.RouteInfo;
import androidx.lifecycle.MutableLiveData;
import com.good.gd.kloes.ehnkx;
import com.good.gd.zwn.aqdzk;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes.dex */
public class yfdke implements hbfhc {
    private final ConnectivityManager dbjc;
    private String lqox;
    private com.good.gd.oqpvt.hbfhc qkduk;
    private String ztwf = null;
    private String liflu = null;
    private boolean jcpqe = false;
    private boolean tlske = false;
    private final String jsgtu = yfdke.class.getSimpleName();
    private final fdyxd jwxax = new fdyxd();
    MutableLiveData<Boolean> wuird = new MutableLiveData<>();
    private com.good.gd.rzh.hbfhc wxau = new com.good.gd.rzh.hbfhc();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {

        /* renamed from: com.good.gd.oqpvt.yfdke$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0023hbfhc implements aqdzk {
            C0023hbfhc() {
            }

            @Override // com.good.gd.zwn.aqdzk
            public void dbjc(boolean z, int i, String str) {
                boolean z2 = false;
                yfdke.this.jcpqe = false;
                if (z && com.good.gd.idl.hbfhc.pqq().kwm()) {
                    z2 = true;
                }
                if (!z2) {
                    yfdke.this.lqox = null;
                } else if (str != null && true != str.trim().isEmpty()) {
                    yfdke.this.lqox = str;
                }
                if (true != com.good.gd.whhmi.yfdke.qkduk(yfdke.this.lqox)) {
                    com.good.gd.idl.hbfhc.pqq().dbjc(yfdke.this.lqox);
                } else {
                    com.good.gd.idl.hbfhc.pqq().ssosk();
                }
            }
        }

        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            yfdke.this.wxau.dbjc(new C0023hbfhc());
        }
    }

    public yfdke(Context context) {
        this.dbjc = (ConnectivityManager) context.getSystemService("connectivity");
        qkduk(wuird());
    }

    private void iulf() {
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Preparing WiFi public WAN IP Address.");
        if (((com.blackberry.analytics.analyticsengine.fdyxd) com.blackberry.bis.core.hbfhc.dbjc()).wxau()) {
            com.good.gd.idl.hbfhc.pqq().ujr();
            ScheduledExecutorService jsgtu = com.good.gd.idl.hbfhc.pqq().jsgtu();
            this.jcpqe = true;
            jsgtu.execute(new hbfhc());
        }
    }

    private String rynix() {
        String str = null;
        if (true != com.good.gd.idl.hbfhc.pqq().bucpw()) {
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Router IP tracking not allowed");
            return null;
        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    if (true != nextElement.isLoopbackAddress()) {
                        String hostAddress = nextElement.getHostAddress();
                        try {
                            ehnkx.qkduk(this.jsgtu, "Assigned local IPv" + (nextElement instanceof Inet6Address ? "6" : "4") + " address " + hostAddress);
                            return hostAddress;
                        } catch (Exception e) {
                            str = hostAddress;
                            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Failed to fetch the assigned IP address.");
                            return str;
                        }
                    }
                }
            }
        } catch (Exception e2) {
        }
        return str;
    }

    private void vfle() {
        InetAddress gateway;
        if (true != com.good.gd.idl.hbfhc.pqq().bucpw()) {
            this.ztwf = null;
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Router IP tracking not allowed");
        } else if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot execute WiFi Router IP address request.");
        } else {
            try {
                Network activeNetwork = this.dbjc.getActiveNetwork();
                if (activeNetwork == null) {
                    this.ztwf = null;
                    return;
                }
                for (RouteInfo routeInfo : this.dbjc.getLinkProperties(activeNetwork).getRoutes()) {
                    if (routeInfo != null && routeInfo.isDefaultRoute() && (gateway = routeInfo.getGateway()) != null) {
                        if (gateway instanceof Inet6Address) {
                            this.ztwf = gateway.getHostAddress();
                            return;
                        } else if (gateway instanceof Inet4Address) {
                            this.ztwf = gateway.getHostAddress();
                        }
                    }
                }
            } catch (Exception e) {
                com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Exception while executing WiFi Router IP address request: " + e.getLocalizedMessage());
            }
        }
    }

    private void yrp() {
        this.liflu = null;
        this.lqox = null;
        this.ztwf = null;
    }

    public boolean jcpqe() {
        NetworkCapabilities networkCapabilities;
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot check cellular connection.");
            return false;
        }
        try {
            Network activeNetwork = this.dbjc.getActiveNetwork();
            if (activeNetwork != null && (networkCapabilities = this.dbjc.getNetworkCapabilities(activeNetwork)) != null) {
                return networkCapabilities.hasTransport(0);
            }
            return false;
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Security Exception while checking cellular connection: " + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean jsgtu() {
        NetworkCapabilities networkCapabilities;
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot check vpn connection.");
            return false;
        }
        try {
            Network activeNetwork = this.dbjc.getActiveNetwork();
            if (activeNetwork != null && (networkCapabilities = this.dbjc.getNetworkCapabilities(activeNetwork)) != null) {
                return networkCapabilities.hasTransport(4);
            }
            return false;
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Security Exception while checking vpn connection: " + e.getLocalizedMessage());
            return false;
        }
    }

    @Override // com.good.gd.oqpvt.hbfhc
    public void jwxax() {
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Internet Connection Established.");
        this.wuird.postValue(true);
        com.good.gd.oqpvt.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar != null) {
            hbfhcVar.jwxax();
        }
    }

    public String liflu() {
        if (true != com.good.gd.idl.hbfhc.pqq().kwm()) {
            this.lqox = null;
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "WAN IP tracking not allowed");
            return null;
        }
        String str = this.lqox;
        if (str == null || str.trim().isEmpty()) {
            if (true != this.jcpqe) {
                iulf();
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "WAN IP network request is already executing.");
            }
        }
        return this.lqox;
    }

    public String lqox() {
        if (true != com.good.gd.idl.hbfhc.pqq().bucpw()) {
            this.ztwf = null;
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Router IP tracking not allowed");
            return null;
        }
        String str = this.ztwf;
        if (str == null || str.trim().isEmpty()) {
            this.liflu = rynix();
            vfle();
        }
        return this.ztwf;
    }

    public void mvf() {
        if (this.tlske) {
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Internet monitoring is already started.");
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Internet Monitoring Started.");
        fdyxd fdyxdVar = this.jwxax;
        if (fdyxdVar != null) {
            fdyxdVar.dbjc(this);
            this.jwxax.dbjc();
        }
        this.tlske = true;
    }

    public void odlf() {
        com.good.gd.kloes.hbfhc.ztwf(this.jsgtu, "Internet Monitoring Stopped.");
        fdyxd fdyxdVar = this.jwxax;
        if (fdyxdVar != null) {
            fdyxdVar.dbjc((com.good.gd.oqpvt.hbfhc) null);
            this.jwxax.qkduk();
            this.tlske = false;
        }
    }

    public boolean tlske() {
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot check network connection.");
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = this.dbjc.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return false;
            }
            return activeNetworkInfo.isConnected();
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Exception while checking internet connection state: " + e.getLocalizedMessage());
            com.good.gd.kloes.hbfhc.ztwf(this.jsgtu, "Forcefully assuming internet is available to start BIS network calls.");
            return true;
        }
    }

    public boolean wuird() {
        NetworkCapabilities networkCapabilities;
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot check WiFi connection.");
            return false;
        }
        try {
            Network activeNetwork = this.dbjc.getActiveNetwork();
            if (activeNetwork == null || (networkCapabilities = this.dbjc.getNetworkCapabilities(activeNetwork)) == null || !networkCapabilities.hasTransport(1)) {
                return false;
            }
            return true != this.dbjc.isActiveNetworkMetered();
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Security Exception while checking WiFi connection: " + e.getLocalizedMessage());
            return false;
        }
    }

    public void wxau() {
        yrp();
    }

    public String ztwf() {
        LinkProperties linkProperties;
        if (!com.good.gd.whhmi.yfdke.jwxax()) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Access Network State permission not granted, cannot check network interface name.");
            return null;
        }
        try {
            Network activeNetwork = this.dbjc.getActiveNetwork();
            if (activeNetwork != null && (linkProperties = this.dbjc.getLinkProperties(activeNetwork)) != null) {
                return linkProperties.getInterfaceName();
            }
        } catch (SecurityException e) {
            com.good.gd.kloes.hbfhc.qkduk(this.jsgtu, "Security Exception while fetching network interface name: " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override // com.good.gd.oqpvt.hbfhc
    public void qkduk() {
        com.good.gd.kloes.hbfhc.ztwf(this.jsgtu, "Internet Disconnected.");
        this.wuird.postValue(false);
        yrp();
        com.good.gd.oqpvt.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar != null) {
            hbfhcVar.qkduk();
        }
    }

    public void dbjc(com.good.gd.oqpvt.hbfhc hbfhcVar) {
        this.qkduk = hbfhcVar;
    }

    @Override // com.good.gd.oqpvt.hbfhc
    public void dbjc(boolean z) {
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Internet state has been changed.");
        qkduk(z);
        com.good.gd.oqpvt.hbfhc hbfhcVar = this.qkduk;
        if (hbfhcVar != null) {
            hbfhcVar.dbjc(z);
        }
    }

    private void qkduk(boolean z) {
        if (true == com.good.gd.idl.hbfhc.pqq().bucpw() && true == com.good.gd.idl.hbfhc.pqq().bucpw()) {
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Update IP Addresses based on Internet Network Status.");
            if (z) {
                com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Wifi is Connected, Preparing the IP addresses.");
                String rynix = rynix();
                if (this.liflu != null && (rynix == null || true == rynix.trim().isEmpty() || true == this.liflu.equals(rynix))) {
                    return;
                }
                this.liflu = rynix;
                vfle();
                iulf();
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Cellular or other Network is Connected, Cleaning up stored IP values.");
            yrp();
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Router and WAN IP tracking not allowed.");
    }

    @Override // com.good.gd.oqpvt.hbfhc
    public void dbjc() {
        com.good.gd.kloes.hbfhc.wxau(this.jsgtu, "Current network is lost.");
    }
}
