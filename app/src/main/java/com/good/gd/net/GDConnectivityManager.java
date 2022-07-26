package com.good.gd.net;

import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;

/* loaded from: classes.dex */
public class GDConnectivityManager {
    public static final String GD_CONNECTIVITY_ACTION = "com.good.gd.CONNECTIVITY_CHANGE";

    public static boolean forceResetAllConnections() throws GDNotAuthorizedError {
        return GDConnectivityManagerImpl.performResetConnectivityImpl();
    }

    public static GDNetworkInfo getActiveNetworkInfo() {
        return NetworkStateMonitor.getInstance().getNetworkInfo();
    }
}
