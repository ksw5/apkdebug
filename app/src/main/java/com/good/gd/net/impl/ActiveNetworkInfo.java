package com.good.gd.net.impl;

import android.net.Network;
import android.net.NetworkInfo;

/* loaded from: classes.dex */
public class ActiveNetworkInfo {
    private DataConnectivityCheckResult checkResult;
    private Network network;
    private NetworkInfo networkInfo;

    public ActiveNetworkInfo(Network network, NetworkInfo networkInfo, DataConnectivityCheckResult dataConnectivityCheckResult) {
        this.network = network;
        this.networkInfo = networkInfo;
        this.checkResult = dataConnectivityCheckResult;
    }

    public Network getNetwork() {
        return this.network;
    }

    public NetworkInfo getNetworkInfo() {
        return this.networkInfo;
    }

    public DataConnectivityCheckResult getWalledGardenCheckResult() {
        return this.checkResult;
    }

    public boolean isCaptivePortal() {
        DataConnectivityCheckResult walledGardenCheckResult = getWalledGardenCheckResult();
        if (walledGardenCheckResult == null) {
            return false;
        }
        return walledGardenCheckResult.getResultString().equals(DataConnectivityType.DC_CAPTIVE_PORTAL.getName());
    }

    public boolean isUnknownOutage() {
        DataConnectivityCheckResult walledGardenCheckResult = getWalledGardenCheckResult();
        if (walledGardenCheckResult == null) {
            return false;
        }
        return walledGardenCheckResult.getResultString().equals(DataConnectivityType.DC_UNKNOWN.getName());
    }
}
