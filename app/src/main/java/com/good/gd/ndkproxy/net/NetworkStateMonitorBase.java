package com.good.gd.ndkproxy.net;

import com.good.gd.net.impl.DataConnectivityCheckObserver;
import com.good.gd.net.impl.DataConnectivityCheckResult;
import com.good.gd.net.impl.DataConnectivityCheckTask;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class NetworkStateMonitorBase implements DataConnectivityCheckObserver {
    private static NetworkStateMonitorBase instance = new NetworkStateMonitorBase();
    private Set<DataConnectivityCheckObserver> connectivityCheckObservers = new HashSet();
    private boolean isInitialised = false;
    private boolean connectivityCheckHasStarted = false;

    private NetworkStateMonitorBase() {
    }

    private void dataConnectivityCheck() {
        this.connectivityCheckHasStarted = DataConnectivityCheckTask.submit(this);
    }

    public static NetworkStateMonitorBase getInstance() {
        return instance;
    }

    private native void ndkInit();

    public void addConnectivityCheckListener(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        this.connectivityCheckObservers.add(dataConnectivityCheckObserver);
    }

    @Override // com.good.gd.net.impl.DataConnectivityCheckObserver
    public void dataConnectivityCheckResult(DataConnectivityCheckResult dataConnectivityCheckResult) {
        this.connectivityCheckHasStarted = false;
        for (DataConnectivityCheckObserver dataConnectivityCheckObserver : this.connectivityCheckObservers) {
            dataConnectivityCheckObserver.dataConnectivityCheckResult(dataConnectivityCheckResult);
        }
    }

    public void initialise() {
        if (this.isInitialised) {
            return;
        }
        ndkInit();
        this.isInitialised = true;
    }

    public boolean isCheckStarted() {
        return this.connectivityCheckHasStarted;
    }

    public void removeConnectivityCheckListener(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        this.connectivityCheckObservers.remove(dataConnectivityCheckObserver);
    }

    public void startWalledGardenCheck() {
        dataConnectivityCheck();
    }

    public native void updateConnectivityCheckResult(String str, String str2, String str3);

    public native void updateStatus(boolean z, boolean z2, boolean z3, boolean z4);
}
