package com.good.gd.ndkproxy.util;

import android.net.ConnectivityManager;
import com.good.gd.ndkproxy.net.NetworkStateMonitorBase;
import com.good.gd.ndkproxy.util.impl.GDActiveNetworkStateMonitorImpl;
import com.good.gd.ndkproxy.util.impl.GDNetworkRestrictionsStateMonitorImpl;
import com.good.gd.ndkproxy.util.impl.GDSpecifiedNetworkStateMonitorImpl;
import com.good.gd.ndkproxy.util.impl.NetworkStateMonitorSettings;
import com.good.gd.net.impl.DataConnectivityCheckObserver;

/* loaded from: classes.dex */
public final class GDNetworkStateMonitor {
    public static final int NetworkCannotGetCapabilities = 1001;
    public static final String NetworkInterfaceKey = "com.blackberry.endpoint.core_services.utils.android.keys.NetworkInterfaceBundleKey";
    public static final String NetworkStateKey = "com.blackberry.endpoint.core_services.utils.android.keys.NetworkStateBundleKey";
    public static final int NetworkUnknownInterface = 1000;
    private static final Object syncObject = new Object();

    /* loaded from: classes.dex */
    public enum NetworkState {
        AVAILABLE(0),
        LOSING(1),
        LOST(2),
        UNAVAILABLE(3),
        CAPABILITIES_CHANGED(4),
        LINK_PROPERTIES_CHANGED(5),
        BLOCKED_STATUS_CHANGED(6),
        UNKNOWN(7);
        
        private int value;

        NetworkState(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static void addWalledGardenCheckListener(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        NetworkStateMonitorBase.getInstance().addConnectivityCheckListener(dataConnectivityCheckObserver);
    }

    public static IGDActiveNetworkStateMonitor createActiveNetworkStateMonitor() {
        return createActiveNetworkStateMonitor(new NetworkStateMonitorSettings());
    }

    public static IGDSpecifiedNetworkStateMonitor createSpecifiedNetworkStateMonitor() {
        return new GDSpecifiedNetworkStateMonitorImpl();
    }

    public static ConnectivityManager getConnectivityManager() {
        return GDNetworkRestrictionsStateMonitorImpl.getSharedInstance().getConnectivityManager();
    }

    public static String getNetworkRestrictionChangeIntentAction() {
        return GDNetworkRestrictionsStateMonitorImpl.INTENT_ACTION_FOR_NETWORK_RESTRICTIONS_CHANGE;
    }

    public static void initialise() {
        GDNetworkRestrictionsStateMonitorImpl.getSharedInstance().initialise();
        NetworkStateMonitorBase.getInstance().initialise();
    }

    public static void removeWalledGardenCheckListener(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        NetworkStateMonitorBase.getInstance().removeConnectivityCheckListener(dataConnectivityCheckObserver);
    }

    public static boolean startWalledGardenCheck() {
        synchronized (syncObject) {
            NetworkStateMonitorBase networkStateMonitorBase = NetworkStateMonitorBase.getInstance();
            if (!networkStateMonitorBase.isCheckStarted()) {
                networkStateMonitorBase.startWalledGardenCheck();
                return true;
            }
            return false;
        }
    }

    public static IGDActiveNetworkStateMonitor createActiveNetworkStateMonitor(NetworkStateMonitorSettings networkStateMonitorSettings) {
        return new GDActiveNetworkStateMonitorImpl(networkStateMonitorSettings);
    }
}
