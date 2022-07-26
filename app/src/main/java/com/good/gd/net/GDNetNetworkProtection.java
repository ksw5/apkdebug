package com.good.gd.net;

import com.good.gd.NetworkProtectionConfigProvider;

/* loaded from: classes.dex */
public class GDNetNetworkProtection implements NetworkProtectionConfigProvider {
    private static GDNetNetworkProtection _instance;

    public static GDNetNetworkProtection getInstance() {
        if (_instance == null) {
            _instance = new GDNetNetworkProtection();
        }
        return _instance;
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public String getConnectivityActionIntentAction() {
        return GDConnectivityManager.GD_CONNECTIVITY_ACTION;
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public boolean isCaptivePortal() {
        return GDConnectivityManager.getActiveNetworkInfo().isCaptivePortal();
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public boolean isUnknownOutage() {
        return GDConnectivityManager.getActiveNetworkInfo().isUnknownOutage();
    }
}
