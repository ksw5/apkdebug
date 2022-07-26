package com.good.gd.ndkproxy.push;

import com.good.gd.ApplicationContext;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.error.GDInitializationError;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.push.PushConnectionListener;

/* loaded from: classes.dex */
public class PushConnectionImpl implements IPushConnection {
    private PushConnectionListener _listener = null;
    private ApplicationContext applicationContext;
    private ContainerState authChecker;
    private PushConnectionStatusChangedListener pushConnectionStatusChangedListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PushConnectionImpl(ContainerState containerState, ApplicationContext applicationContext, PushConnectionStatusChangedListener pushConnectionStatusChangedListener) {
        this.authChecker = containerState;
        this.applicationContext = applicationContext;
        this.pushConnectionStatusChangedListener = pushConnectionStatusChangedListener;
        try {
            containerState.checkAuthorized();
            NDK_init();
        } catch (GDInitializationError e) {
            throw new GDNotAuthorizedError("PushConnectioncreation" + Thread.currentThread().getStackTrace().toString());
        }
    }

    private native void NDK_checkStatus();

    private native void NDK_connect();

    private native void NDK_connectInternal(boolean z);

    private native void NDK_disconnect();

    private native void NDK_disconnectInternal();

    private native void NDK_init();

    private native boolean NDK_isConnected();

    private native boolean NDK_isWaiting();

    private native void NDK_testConnectionIntegrity();

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void checkStatus() {
        this.authChecker.checkAuthorized();
        synchronized (this) {
            NDK_checkStatus();
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void connect() {
        this.authChecker.checkAuthorized();
        synchronized (this) {
            NDK_connect();
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void connectInternal(boolean z) {
        this.authChecker.checkAuthorized();
        synchronized (this) {
            NDK_connectInternal(z);
            GDLog.DBGPRINTF(14, "PushConnectionImpl::NDK_connectInternal invoked\n");
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void disconnect() {
        this.authChecker.checkAuthorized();
        synchronized (this) {
            NDK_disconnect();
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void disconnectInternal() {
        this.authChecker.checkAuthorized();
        synchronized (this) {
            NDK_disconnectInternal();
            GDLog.DBGPRINTF(14, "PushConnectionImpl::NDK_disconnectInternal invoked\n");
        }
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public boolean isConnected() {
        this.authChecker.checkAuthorized();
        return NDK_isConnected();
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public boolean isWaiting() {
        this.authChecker.checkAuthorized();
        return NDK_isWaiting();
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void onNativeStatus(final int i) {
        this.applicationContext.getClientHandler().post(new Runnable() { // from class: com.good.gd.ndkproxy.push.PushConnectionImpl.1
            @Override // java.lang.Runnable
            public void run() {
                if (PushConnectionImpl.this.authChecker.isAuthorized() && PushConnectionImpl.this._listener != null) {
                    PushConnectionImpl.this._listener.onStatus(i);
                }
                GDLog.DBGPRINTF(14, "Notify push connection status changed (" + i + ")\n");
                PushConnectionImpl.this.pushConnectionStatusChangedListener.onPushConnectionStatusChanged();
            }
        });
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void setListener(PushConnectionListener pushConnectionListener) {
        this.authChecker.checkAuthorized();
        this._listener = pushConnectionListener;
    }

    @Override // com.good.gd.ndkproxy.push.IPushConnection
    public void testConnectionIntegrity() {
        NDK_testConnectionIntegrity();
    }
}
