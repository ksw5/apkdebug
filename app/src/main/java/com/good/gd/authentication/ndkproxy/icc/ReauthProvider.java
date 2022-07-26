package com.good.gd.authentication.ndkproxy.icc;

import com.good.gd.authentication.ReAuthResult;
import com.good.gd.authentication.ReAuthType;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.icc.AppControl;
import com.good.gt.icc.FrontParams;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCControllerFactory;
import com.good.gt.icc.ListenerAlreadySetException;
import com.good.gt.icc.ReauthClient;
import com.good.gt.icc.ReauthClientListener;
import com.good.gt.icc.ReauthServer;
import com.good.gt.icc.ReauthServerListener;

/* loaded from: classes.dex */
public class ReauthProvider implements AppControl, ReauthClientListener, ReauthServerListener {
    private static ReauthProvider _instance;
    private ReauthRequest currentRequest;
    private ReauthClient reauthClient = ICCControllerFactory.getICCController(this).getReauthClient();
    private ReauthServer reauthServer;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ReauthRequest {
        private String dbjc;
        private String qkduk;

        public ReauthRequest(ReauthProvider reauthProvider, String str) {
            this.qkduk = str;
        }

        public String dbjc() {
            return this.qkduk;
        }

        public String qkduk() {
            return this.dbjc;
        }

        public void dbjc(String str) {
            this.dbjc = str;
        }
    }

    private ReauthProvider() {
        ndkInit();
        ReauthServer reauthServer = ICCControllerFactory.getICCController(this).getReauthServer();
        this.reauthServer = reauthServer;
        try {
            reauthServer.setListener(this);
        } catch (ListenerAlreadySetException e) {
            GDLog.DBGPRINTF(12, "Activation Delegation Provider - listener error: " + e.toString() + "\n");
        }
        try {
            this.reauthClient.setListener(this);
        } catch (ListenerAlreadySetException e2) {
            GDLog.DBGPRINTF(12, "Activation Delegation Provider - listener error: " + e2.toString() + "\n");
        }
    }

    public static synchronized ReauthProvider getInstance() {
        ReauthProvider reauthProvider;
        synchronized (ReauthProvider.class) {
            if (_instance == null) {
                _instance = new ReauthProvider();
            }
            reauthProvider = _instance;
        }
        return reauthProvider;
    }

    private static native void ndkInit();

    private static void onReAuthResult(String str, int i, int i2) {
        getInstance().sendResult(str, ReAuthResult.get(i), ReAuthType.get(i2));
    }

    private static native String onReauthRequestNative(String str, String str2, long j, long j2, int i, boolean z, boolean z2);

    private static native void onReauthResponseNative(String str, int i, int i2);

    private static boolean requestReauth(String str, String str2, String str3, String str4, long j, long j2, int i, boolean z, boolean z2) {
        return getInstance().sendRequest(str, str2, str3, str4, j, j2, i, z, z2);
    }

    @Override // com.good.gt.icc.AppControl
    public void becomeForeground(FrontParams frontParams) {
    }

    @Override // com.good.gt.icc.ReauthServerListener
    public synchronized boolean onReauthRequest(String str, String str2, String str3, long j, long j2, int i, boolean z, boolean z2) {
        if (this.currentRequest == null) {
            this.currentRequest = new ReauthRequest(this, str);
            String onReauthRequestNative = onReauthRequestNative(str2, str3, j, j2, i, z, z2);
            this.currentRequest.dbjc(onReauthRequestNative);
            GDLog.DBGPRINTF(16, String.format("ReauthProvider.onReauthRequest(%s | %s)\n", str, onReauthRequestNative));
        }
        return true;
    }

    @Override // com.good.gt.icc.ReauthClientListener
    public void onReauthResponse(String str, int i, int i2) {
        GDLog.DBGPRINTF(16, "ReauthProvider.onReauthResponse IN\n");
        ReauthRequest reauthRequest = this.currentRequest;
        if (reauthRequest == null) {
            GDLog.DBGPRINTF(16, "ReauthProvider.onReauthResponse response dropped\n");
            return;
        }
        if (reauthRequest.dbjc().equals(str)) {
            onReauthResponseNative(str, i, i2);
        } else if (ReAuthResult.get(i) == null) {
            onReauthResponseNative(this.currentRequest.dbjc(), ReAuthResult.ErrorUnknown.getCode(), ReAuthType.None.getCode());
        }
        this.currentRequest = null;
    }

    public synchronized boolean sendRequest(String str, String str2, String str3, String str4, long j, long j2, int i, boolean z, boolean z2) {
        try {
            this.currentRequest = new ReauthRequest(this, str2);
            this.reauthClient.sendReauthRequest(str, str2, str3, str4, j, j2, i, z, z2);
        } catch (GTServicesException e) {
            onReauthResponse(str2, ReAuthResult.ErrorUnknown.getCode(), ReAuthType.None.getCode());
        }
        return true;
    }

    public synchronized void sendResult(String str, ReAuthResult reAuthResult, ReAuthType reAuthType) {
        ReauthRequest reauthRequest = this.currentRequest;
        if (reauthRequest == null) {
            GDLog.DBGPRINTF(16, "ReauthProvider doesn't have pending reauth\n");
            return;
        }
        boolean z = true;
        if (reauthRequest.qkduk() != null && this.currentRequest.qkduk().equals(str)) {
            int code = reAuthResult.getCode();
            try {
                if (reAuthResult != ReAuthResult.Success || reAuthType != ReAuthType.GracePeriod) {
                    z = false;
                }
                this.reauthServer.sendReauthResult(this.currentRequest.dbjc(), code, reAuthType.getCode(), z);
            } catch (GTServicesException e) {
            }
            this.currentRequest = null;
            return;
        }
        GDLog.DBGPRINTF(16, String.format("Wrong local token. Expected %s but received %s\n", this.currentRequest.qkduk(), str));
    }
}
