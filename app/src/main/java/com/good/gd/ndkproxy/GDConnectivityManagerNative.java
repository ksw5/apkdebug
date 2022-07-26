package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public class GDConnectivityManagerNative {

    /* loaded from: classes.dex */
    public enum RouteType {
        UNDEFINED,
        INTERNET,
        GOOD_PROXY,
        BCP_DIRECT,
        DENY
    }

    public static native void drainSocketPool();

    public static RouteType getRouteType(String str, int i) {
        RouteType routeType = RouteType.UNDEFINED;
        switch (routeType(str, i)) {
            case 0:
                return RouteType.UNDEFINED;
            case 1:
            case 5:
                return RouteType.INTERNET;
            case 2:
            case 6:
                return RouteType.GOOD_PROXY;
            case 3:
                return RouteType.BCP_DIRECT;
            case 4:
            case 7:
                return RouteType.DENY;
            default:
                return routeType;
        }
    }

    public static native boolean performResetConnectivity();

    private static native int routeType(String str, int i);
}
