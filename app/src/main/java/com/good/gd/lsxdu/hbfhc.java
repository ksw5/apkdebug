package com.good.gd.lsxdu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.good.gd.GDAndroidAPI;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.authentication.AuthenticationManager;
import com.good.gd.authentication.ReAuthResult;
import com.good.gd.authentication.ReAuthType;
import com.good.gd.kloes.ehnkx;
import com.good.gd.utils.GDLocalizer;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class hbfhc {
    private static hbfhc jsgtu;
    private static GDAndroidAPI wuird;
    private String jwxax;
    private String wxau;
    private ScheduledFuture ztwf;
    private final Class dbjc = hbfhc.class;
    private ScheduledExecutorService qkduk = Executors.newSingleThreadScheduledExecutor();
    private ConcurrentLinkedQueue<fdyxd> lqox = new ConcurrentLinkedQueue<>();
    private boolean liflu = false;
    private boolean jcpqe = false;
    private BroadcastReceiver tlske = new C0018hbfhc();

    /* loaded from: classes.dex */
    private class fdyxd {
        private String dbjc;
        private boolean jwxax;
        private com.good.gd.lsxdu.fdyxd qkduk;

        private fdyxd(hbfhc hbfhcVar) {
        }

        void dbjc(String str) {
            this.dbjc = str;
        }

        boolean jwxax() {
            return this.jwxax;
        }

        String qkduk() {
            return this.dbjc;
        }

        /* synthetic */ fdyxd(hbfhc hbfhcVar, C0018hbfhc c0018hbfhc) {
            this(hbfhcVar);
        }

        void dbjc(boolean z) {
            this.jwxax = z;
        }

        com.good.gd.lsxdu.fdyxd dbjc() {
            return this.qkduk;
        }

        void dbjc(com.good.gd.lsxdu.fdyxd fdyxdVar) {
            this.qkduk = fdyxdVar;
        }
    }

    /* renamed from: com.good.gd.lsxdu.hbfhc$hbfhc  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class C0018hbfhc extends BroadcastReceiver {
        C0018hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String str;
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.dbjc, "[REACH] Received the re-authentication response.");
            String reauthenticationToken = AuthenticationManager.getReauthenticationToken(intent);
            ReAuthResult reauthenticationResult = AuthenticationManager.getReauthenticationResult(intent);
            ReAuthType reauthenticationAuthType = AuthenticationManager.getReauthenticationAuthType(intent);
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.dbjc, "[REACH] Re-authentication request token:  " + reauthenticationToken);
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.dbjc, "[REACH] Re-authentication auth result:   " + reauthenticationResult);
            com.good.gd.kloes.hbfhc.wxau(hbfhc.this.dbjc, "[REACH] Re-authentication auth type:   " + reauthenticationAuthType);
            if (hbfhc.this != null) {
                if (reauthenticationResult != null) {
                    switch (reauthenticationResult.ordinal()) {
                        case 0:
                            str = "Success";
                            break;
                        case 1:
                            str = "FailedAuth";
                            break;
                        case 2:
                            str = "UserCancelled";
                            break;
                        case 3:
                            str = "Expired";
                            break;
                        case 4:
                            str = "InProgress";
                            break;
                        case 5:
                            str = "NotSupported";
                            break;
                        case 6:
                            str = "InvalidRequest";
                            break;
                    }
                    hbfhc.this.dbjc(false, reauthenticationToken, str, hbfhc.this.dbjc(reauthenticationAuthType));
                    return;
                }
                str = "Unknown";
                hbfhc.this.dbjc(false, reauthenticationToken, str, hbfhc.this.dbjc(reauthenticationAuthType));
                return;
            }
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        final /* synthetic */ String dbjc;
        final /* synthetic */ String jwxax;
        final /* synthetic */ boolean qkduk;
        final /* synthetic */ String wxau;

        yfdke(String str, boolean z, String str2, String str3) {
            this.dbjc = str;
            this.qkduk = z;
            this.jwxax = str2;
            this.wxau = str3;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                if (hbfhc.this.lqox != null) {
                    Iterator it = hbfhc.this.lqox.iterator();
                    ReAuthResult reAuthResult = ReAuthResult.Success;
                    if ("Success".equals(this.dbjc)) {
                        com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, "[REACH] Re-authentication successful.");
                    } else {
                        com.good.gd.kloes.hbfhc.jwxax(hbfhc.this.dbjc, "[REACH] Re-authentication failed/rejected.");
                    }
                    while (it.hasNext()) {
                        fdyxd fdyxdVar = (fdyxd) it.next();
                        boolean z = false;
                        if (true != this.qkduk) {
                            if (this.jwxax != null && fdyxdVar.qkduk().equals(this.jwxax)) {
                                com.good.gd.lsxdu.fdyxd dbjc = fdyxdVar.dbjc();
                                if (dbjc != null) {
                                    String str = this.dbjc;
                                    if (str != null) {
                                        ReAuthResult reAuthResult2 = ReAuthResult.Success;
                                        if (str.equals("Success")) {
                                            z = true;
                                        }
                                    }
                                    dbjc.dbjc(z, this.jwxax, this.dbjc, this.wxau);
                                }
                                it.remove();
                            }
                        } else if (fdyxdVar.jwxax()) {
                            com.good.gd.lsxdu.fdyxd dbjc2 = fdyxdVar.dbjc();
                            if (dbjc2 != null) {
                                dbjc2.dbjc(false, fdyxdVar.qkduk(), this.dbjc, this.wxau);
                            }
                            it.remove();
                        }
                    }
                    if (hbfhc.this.lqox.isEmpty()) {
                        hbfhc.wxau(hbfhc.this);
                        if (hbfhc.this.jcpqe) {
                            hbfhc.lqox(hbfhc.this);
                        }
                    }
                    return;
                }
                hbfhc.wxau(hbfhc.this);
            }
        }
    }

    private hbfhc() {
        this.jwxax = null;
        this.wxau = null;
        this.jwxax = "BlackBerry Persona";
        this.wxau = "BlackBerry Persona requires you to authenticate before you use this app.";
        wuird = com.blackberry.analytics.analyticsengine.yfdke.wxau();
    }

    static /* synthetic */ void lqox(hbfhc hbfhcVar) {
        ScheduledFuture scheduledFuture;
        if (hbfhcVar.jcpqe && (scheduledFuture = hbfhcVar.ztwf) != null) {
            if (scheduledFuture.cancel(false)) {
                hbfhcVar.jcpqe = false;
                com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, "[REACH] Invalidated enforced re-authentication timeout timer.");
                return;
            }
            com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, "[REACH] Enforced re-authentication timeout timer is already invalidated.");
            return;
        }
        com.good.gd.kloes.hbfhc.wxau(hbfhcVar.dbjc, "[REACH] Enforced re-authentication timeout timer is not running.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void qkduk(hbfhc hbfhcVar) {
        hbfhcVar.jcpqe = false;
        com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[REACH] Enforced re-authentication timeout timer fired.");
        hbfhcVar.dbjc(true, null, "Timeout", hbfhcVar.dbjc(ReAuthType.None));
    }

    static /* synthetic */ void wxau(hbfhc hbfhcVar) {
        hbfhcVar.liflu = false;
        com.good.gd.kloes.hbfhc.jwxax(hbfhcVar.dbjc, "[REACH] Unregister re-authentication broadcast receiver.");
        wuird.unregisterReceiver(hbfhcVar.tlske);
    }

    public static hbfhc dbjc() {
        if (jsgtu == null) {
            synchronized (hbfhc.class) {
                if (jsgtu == null) {
                    jsgtu = new hbfhc();
                }
            }
        }
        return jsgtu;
    }

    public String dbjc(String str, String str2, int i, int i2, boolean z, boolean z2, com.good.gd.lsxdu.fdyxd fdyxdVar) {
        synchronized (this) {
            if (true != this.liflu && fdyxdVar != null) {
                this.liflu = true;
                com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[REACH] Register re-authentication broadcast receiver.");
                wuird.registerReceiver(this.tlske, new IntentFilter(AuthenticationManager.GD_RE_AUTHENTICATION_EVENT));
            }
        }
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            str = this.jwxax;
        }
        if (com.good.gd.whhmi.yfdke.qkduk(str2)) {
            str2 = this.wxau;
        }
        if (str.equalsIgnoreCase(this.jwxax) && str2.equalsIgnoreCase(this.wxau)) {
            str = GDLocalizer.getLocalizedString("BISReauthenticateDefaultTitle");
            str2 = GDLocalizer.getLocalizedString("BISReauthenticateDefaultMessage");
        }
        String str3 = str;
        String str4 = str2;
        int i3 = (i < 10 || i > 600) ? 600 : i;
        if (i2 < 0) {
            i2 = HttpStatus.SC_MULTIPLE_CHOICES;
        }
        int i4 = i2;
        fdyxd fdyxdVar2 = new fdyxd(this, null);
        fdyxdVar2.dbjc(fdyxdVar);
        if (z) {
            ScheduledExecutorService scheduledExecutorService = this.qkduk;
            if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
                ehnkx.qkduk(this.dbjc, "[REACH] Executor Service is either null or shutdown. Restarting!!");
                this.qkduk = Executors.newSingleThreadScheduledExecutor();
            }
            if (true != this.jcpqe) {
                this.jcpqe = true;
                com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[REACH] Enforce re-authenticate timeout timer.");
                this.ztwf = this.qkduk.schedule(new com.good.gd.lsxdu.yfdke(this), 60000L, TimeUnit.MILLISECONDS);
            } else {
                com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[REACH] Valid enforced re-authentication timeout timer available.");
            }
            fdyxdVar2.dbjc(true);
        }
        synchronized (this) {
            if (this.lqox == null) {
                this.lqox = new ConcurrentLinkedQueue<>();
            }
            this.lqox.add(fdyxdVar2);
        }
        com.good.gd.kloes.hbfhc.jwxax(this.dbjc, "[REACH] Perform Re-authentication");
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, String.format(Locale.getDefault(), "[REACH] Title: %s , Message: %s , Timeout: %d , GracePeriod: %d , Enforce: %b , RequiredPassword: %b", str3, str4, Integer.valueOf(i3), Integer.valueOf(i4), Boolean.valueOf(z), Boolean.valueOf(z2)));
        String reauthenticate = AuthenticationManager.reauthenticate(str3, str4, i3, i4, z, z2);
        fdyxdVar2.dbjc(reauthenticate);
        com.good.gd.kloes.hbfhc.wxau(this.dbjc, "[REACH] Re-authentication request token: " + reauthenticate);
        return reauthenticate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dbjc(boolean z, String str, String str2, String str3) {
        ScheduledExecutorService scheduledExecutorService = this.qkduk;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ehnkx.qkduk(this.dbjc, "[REACH] Executor Service is either null or shutdown. Restarting!!");
            this.qkduk = Executors.newSingleThreadScheduledExecutor();
        }
        this.qkduk.execute(new yfdke(str2, z, str, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String dbjc(ReAuthType reAuthType) {
        if (reAuthType != null) {
            switch (reAuthType.ordinal()) {
                case 0:
                    return "None";
                case 1:
                    return "NoPassword";
                case 2:
                    return "Password";
                case 3:
                    return "Biometric";
                case 4:
                    return "GracePeriod";
                case 5:
                    return "TrustedAuthenticator";
            }
        }
        return "Unknown";
    }
}
