package com.good.gd;

import com.good.gd.utils.ErrorUtils;

/* loaded from: classes.dex */
public class GDNetworkProtection implements NetworkProtectionConfigProvider {
    private static GDNetworkProtection _instance;
    private NetworkProtectionConfigProvider mNetworkProtectionListener;

    public static GDNetworkProtection getInstance() {
        if (_instance == null) {
            _instance = new GDNetworkProtection();
        }
        return _instance;
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public String getConnectivityActionIntentAction() {
        NetworkProtectionConfigProvider networkProtectionConfigProvider = this.mNetworkProtectionListener;
        if (networkProtectionConfigProvider != null) {
            return networkProtectionConfigProvider.getConnectivityActionIntentAction();
        }
        ErrorUtils.throwGDNotAuthorizedError();
        return null;
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public boolean isCaptivePortal() {
        NetworkProtectionConfigProvider networkProtectionConfigProvider = this.mNetworkProtectionListener;
        if (networkProtectionConfigProvider != null) {
            return networkProtectionConfigProvider.isCaptivePortal();
        }
        ErrorUtils.throwGDNotAuthorizedError();
        return false;
    }

    @Override // com.good.gd.NetworkProtectionConfigProvider
    public boolean isUnknownOutage() {
        NetworkProtectionConfigProvider networkProtectionConfigProvider = this.mNetworkProtectionListener;
        if (networkProtectionConfigProvider != null) {
            return networkProtectionConfigProvider.isUnknownOutage();
        }
        ErrorUtils.throwGDNotAuthorizedError();
        return false;
    }

    public void setNetworkConfigProvider(NetworkProtectionConfigProvider networkProtectionConfigProvider) {
        this.mNetworkProtectionListener = networkProtectionConfigProvider;
    }
}
