package com.good.gd.ndkproxy.util.impl;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.ndkproxy.net.NetworkStateMonitorBase;
import com.good.gd.ndkproxy.util.GDNetworkStateMonitor;
import com.good.gd.ndkproxy.util.IGDActiveNetworkStateMonitor;
import com.good.gd.net.impl.ActiveNetworkInfo;
import com.good.gd.net.impl.DataConnectivityCheckObserver;
import com.good.gd.net.impl.DataConnectivityCheckResult;
import java.util.UUID;

/* loaded from: classes.dex */
public class GDActiveNetworkStateMonitorImpl extends Handler implements DataConnectivityCheckObserver, IGDActiveNetworkStateMonitor {
    private static final HandlerThread activeNetworkMonitorThread;
    private Network activeNetwork;
    private ConnectivityManager connectivityManager;
    private NetworkInfo lastActiveNetworkInfo;
    private DataConnectivityCheckResult lastWalledGardenCheckResult;
    private NetworkStateMonitorSettings settings;
    private final int ACTIVE_NETWORK_CHANGE_MESSAGE = 0;
    private final String EXECUTE_WALLED_GARDEN_CHECK_KEY = "com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey";
    private final ConditionVariable walledGardenCheckConditionVariable = new ConditionVariable();
    private final String INTENT_ACTION_FOR_ACTIVE_NETWORK_CHANGE = "com.blackberry.endpoint.core_services.utils.android.network.ACTIVE_NETWORK_CHANGE" + UUID.randomUUID().toString();

    /* loaded from: classes.dex */
    class hbfhc extends ConnectivityManager.NetworkCallback {
        hbfhc() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey", true);
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.AVAILABLE.getValue());
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onBlockedStatusChanged(Network network, boolean z) {
            super.onBlockedStatusChanged(network, z);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.BLOCKED_STATUS_CHANGED.getValue());
            if (!z) {
                bundle.putBoolean("com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey", true);
            }
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey", true);
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.CAPABILITIES_CHANGED.getValue());
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey", true);
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.LINK_PROPERTIES_CHANGED.getValue());
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLosing(Network network, int i) {
            super.onLosing(network, i);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.LOSING.getValue());
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            super.onLost(network);
            GDActiveNetworkStateMonitorImpl.this.activeNetwork = network;
            Bundle bundle = new Bundle();
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, GDNetworkStateMonitor.NetworkState.LOST.getValue());
            GDActiveNetworkStateMonitorImpl.this.sendMessage(bundle, 0);
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("ASMHT");
        activeNetworkMonitorThread = handlerThread;
        handlerThread.start();
    }

    public GDActiveNetworkStateMonitorImpl(NetworkStateMonitorSettings networkStateMonitorSettings) {
        super(activeNetworkMonitorThread.getLooper());
        this.settings = networkStateMonitorSettings;
        ConnectivityManager connectivityManager = GDNetworkStateMonitor.getConnectivityManager();
        this.connectivityManager = connectivityManager;
        this.lastActiveNetworkInfo = connectivityManager.getActiveNetworkInfo();
        this.activeNetwork = this.connectivityManager.getActiveNetwork();
        GDNetworkStateMonitor.addWalledGardenCheckListener(this);
        this.connectivityManager.registerDefaultNetworkCallback(new hbfhc());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(Bundle bundle, int i) {
        Message obtain = Message.obtain();
        obtain.setData(bundle);
        obtain.what = i;
        sendMessage(obtain);
    }

    @Override // com.good.gd.net.impl.DataConnectivityCheckObserver
    public void dataConnectivityCheckResult(DataConnectivityCheckResult dataConnectivityCheckResult) {
        this.lastWalledGardenCheckResult = dataConnectivityCheckResult;
        NetworkStateMonitorBase.getInstance().updateConnectivityCheckResult(dataConnectivityCheckResult.getFormattedTime(), dataConnectivityCheckResult.getURLString(), dataConnectivityCheckResult.getResultString());
        this.walledGardenCheckConditionVariable.open();
        this.walledGardenCheckConditionVariable.close();
    }

    @Override // com.good.gd.ndkproxy.util.IGDActiveNetworkStateMonitor
    public ActiveNetworkInfo getActiveNetworkInfo(boolean z) {
        if (!z) {
            this.activeNetwork = this.connectivityManager.getActiveNetwork();
            this.lastActiveNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        }
        return new ActiveNetworkInfo(this.activeNetwork, this.lastActiveNetworkInfo, this.lastWalledGardenCheckResult);
    }

    @Override // com.good.gd.ndkproxy.util.INetworkStateMonitor
    public String getIntentActionForThisInstance() {
        return this.INTENT_ACTION_FOR_ACTIVE_NETWORK_CHANGE;
    }

    public NetworkStateMonitorSettings getSettings() {
        return this.settings;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Bundle data = message.getData();
        NetworkInfo activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        this.lastActiveNetworkInfo = activeNetworkInfo;
        boolean z = false;
        if (activeNetworkInfo != null) {
            NetworkStateMonitorBase.getInstance().updateStatus(activeNetworkInfo.isConnected(), "WIFI".equals(activeNetworkInfo.getTypeName()), "MOBILE".equals(activeNetworkInfo.getTypeName()), activeNetworkInfo.isAvailable());
        } else {
            NetworkStateMonitorBase.getInstance().updateStatus(false, false, false, false);
        }
        if (activeNetworkInfo != null && data.getBoolean("com.blackberry.endpoint.core_services.utils.keys.ExecuteWalledGardenCheckKey", false) && this.settings.executeWalledGardenCheckWhenNetworkChanged) {
            z = true;
        }
        if (z) {
            GDNetworkStateMonitor.startWalledGardenCheck();
            this.walledGardenCheckConditionVariable.block();
        }
        Intent intent = new Intent(this.INTENT_ACTION_FOR_ACTIVE_NETWORK_CHANGE);
        intent.putExtra(GDNetworkStateMonitor.NetworkStateKey, data.getInt(GDNetworkStateMonitor.NetworkStateKey));
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    public void setSettings(NetworkStateMonitorSettings networkStateMonitorSettings) {
        this.settings = networkStateMonitorSettings;
    }
}
