package com.good.gd.ndkproxy.util.impl;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.util.GDNetworkStateMonitor;
import com.good.gd.ndkproxy.util.IGDSpecifiedNetworkStateMonitor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class GDSpecifiedNetworkStateMonitorImpl extends Handler implements IGDSpecifiedNetworkStateMonitor {
    private final SpecifiedNetworksCallback specifiedNetworksCallback = new SpecifiedNetworksCallback();
    private final Map<Integer, Network> networkMap = new HashMap();
    private final ConnectivityManager connectivityManager = GDNetworkStateMonitor.getConnectivityManager();
    private final String INTENT_ACTION_FOR_SPECIFIED_NETWORK_CHANGE = "com.blackberry.endpoint.core_services.utils.android.network.SPECIFIED_NETWORK_CHANGE" + UUID.randomUUID().toString();

    /* loaded from: classes.dex */
    private class SpecifiedNetworksCallback extends ConnectivityManager.NetworkCallback {
        private SpecifiedNetworksCallback() {
        }

        private void dbjc(Network network, GDNetworkStateMonitor.NetworkState networkState) {
            Message obtain = Message.obtain();
            Bundle bundle = new Bundle();
            if (network != null) {
                NetworkCapabilities networkCapabilities = GDSpecifiedNetworkStateMonitorImpl.this.connectivityManager.getNetworkCapabilities(network);
                int i = 6;
                if (networkCapabilities == null) {
                    i = 1001;
                } else if (networkCapabilities.hasTransport(0)) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(0, network);
                    i = 0;
                } else if (networkCapabilities.hasTransport(1)) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(1, network);
                    i = 1;
                } else if (networkCapabilities.hasTransport(2)) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(2, network);
                    i = 2;
                } else if (networkCapabilities.hasTransport(3)) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(3, network);
                    i = 3;
                } else if (networkCapabilities.hasTransport(4)) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(4, network);
                    i = 4;
                } else if (networkCapabilities.hasTransport(5) && Build.VERSION.SDK_INT >= 26) {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(5, network);
                    i = 5;
                } else if (!networkCapabilities.hasTransport(6) || Build.VERSION.SDK_INT < 27) {
                    i = 1000;
                } else {
                    GDSpecifiedNetworkStateMonitorImpl.this.networkMap.put(6, network);
                }
                bundle.putInt(GDNetworkStateMonitor.NetworkInterfaceKey, i);
            }
            bundle.putInt(GDNetworkStateMonitor.NetworkStateKey, networkState.getValue());
            obtain.setData(bundle);
            GDSpecifiedNetworkStateMonitorImpl.this.sendMessage(obtain);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            dbjc(network, GDNetworkStateMonitor.NetworkState.AVAILABLE);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onBlockedStatusChanged(Network network, boolean z) {
            super.onBlockedStatusChanged(network, z);
            dbjc(network, GDNetworkStateMonitor.NetworkState.BLOCKED_STATUS_CHANGED);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            dbjc(network, GDNetworkStateMonitor.NetworkState.CAPABILITIES_CHANGED);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            dbjc(network, GDNetworkStateMonitor.NetworkState.LINK_PROPERTIES_CHANGED);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLosing(Network network, int i) {
            super.onLosing(network, i);
            dbjc(network, GDNetworkStateMonitor.NetworkState.LOSING);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            super.onLost(network);
            dbjc(network, GDNetworkStateMonitor.NetworkState.LOST);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onUnavailable() {
            super.onUnavailable();
            dbjc(null, GDNetworkStateMonitor.NetworkState.UNAVAILABLE);
        }
    }

    public GDSpecifiedNetworkStateMonitorImpl() {
        super(Looper.getMainLooper());
    }

    @Override // com.good.gd.ndkproxy.util.INetworkStateMonitor
    public String getIntentActionForThisInstance() {
        return this.INTENT_ACTION_FOR_SPECIFIED_NETWORK_CHANGE;
    }

    public Network getNetwork(int i) {
        return this.networkMap.get(Integer.valueOf(i));
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        Bundle data = message.getData();
        Intent intent = new Intent(this.INTENT_ACTION_FOR_SPECIFIED_NETWORK_CHANGE);
        intent.putExtra(GDNetworkStateMonitor.NetworkStateKey, data.getInt(GDNetworkStateMonitor.NetworkStateKey));
        GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
    }

    public void startListenToNetworkInterfaces(int[] iArr) {
        try {
            this.connectivityManager.unregisterNetworkCallback(this.specifiedNetworksCallback);
        } catch (Exception e) {
            GDLog.DBGPRINTF(14, "Network callback was not registered");
        }
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        for (int i : iArr) {
            builder.addTransportType(i);
        }
        this.connectivityManager.registerNetworkCallback(builder.build(), this.specifiedNetworksCallback);
    }
}
