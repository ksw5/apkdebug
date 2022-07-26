package com.good.gd.ndkproxy.push;

import com.good.gd.ApplicationContext;
import com.good.gd.containerstate.ContainerState;

/* loaded from: classes.dex */
public class PushFactory {
    private static ApplicationContext applicationContext;
    private static ContainerState authChecker;
    private static IPushConnection pushConnection;
    private static PushConnectionStatusChangedListener statusChangedListener;

    public static IPushChannel createPushChannel() {
        return new PushChannelImpl(authChecker, applicationContext);
    }

    public static IPushConnection getPushConnection() {
        if (pushConnection == null) {
            pushConnection = new PushConnectionImpl(authChecker, applicationContext, statusChangedListener);
        }
        return pushConnection;
    }

    public static void initialize(ContainerState containerState, ApplicationContext applicationContext2, PushConnectionStatusChangedListener pushConnectionStatusChangedListener) {
        authChecker = containerState;
        applicationContext = applicationContext2;
        statusChangedListener = pushConnectionStatusChangedListener;
    }

    public static IPushChannel createPushChannel(String str) {
        return new PushChannelImpl(str, authChecker, applicationContext);
    }
}
