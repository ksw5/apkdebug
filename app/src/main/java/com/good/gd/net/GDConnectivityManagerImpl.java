package com.good.gd.net;

import com.good.gd.containerstate.ContainerState;
import com.good.gd.ndkproxy.GDConnectivityManagerNative;
import com.good.gd.ndkproxy.push.PushFactory;

/* loaded from: classes.dex */
public final class GDConnectivityManagerImpl {
    private static ContainerState containerState;

    public static void drainSocketPool() {
        GDConnectivityManagerNative.drainSocketPool();
    }

    public static void initialize(ContainerState containerState2) {
        containerState = containerState2;
    }

    public static boolean performResetConnectivityImpl() {
        if (containerState.isWiped()) {
            return false;
        }
        containerState.checkAuthorized();
        PushFactory.getPushConnection().checkStatus();
        return GDConnectivityManagerNative.performResetConnectivity();
    }
}
