package com.good.gd.apache.http.conn.routing;

/* loaded from: classes.dex */
public class BasicRouteDirector implements HttpRouteDirector {
    protected int directStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo2.getHopCount() <= 1 && routeInfo.getTargetHost().equals(routeInfo2.getTargetHost()) && routeInfo.isSecure() == routeInfo2.isSecure()) {
            return (routeInfo.getLocalAddress() == null || routeInfo.getLocalAddress().equals(routeInfo2.getLocalAddress())) ? 0 : -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int firstStep(RouteInfo routeInfo) {
        return routeInfo.getHopCount() > 1 ? 2 : 1;
    }

    @Override // com.good.gd.apache.http.conn.routing.HttpRouteDirector
    public int nextStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        if (routeInfo != null) {
            if (routeInfo2 != null && routeInfo2.getHopCount() >= 1) {
                if (routeInfo.getHopCount() > 1) {
                    return proxiedStep(routeInfo, routeInfo2);
                }
                return directStep(routeInfo, routeInfo2);
            }
            return firstStep(routeInfo);
        }
        throw new IllegalArgumentException("Planned route may not be null.");
    }

    protected int proxiedStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        int hopCount;
        int hopCount2;
        if (routeInfo2.getHopCount() > 1 && routeInfo.getTargetHost().equals(routeInfo2.getTargetHost()) && (hopCount = routeInfo.getHopCount()) >= (hopCount2 = routeInfo2.getHopCount())) {
            for (int i = 0; i < hopCount2 - 1; i++) {
                if (!routeInfo.getHopTarget(i).equals(routeInfo2.getHopTarget(i))) {
                    return -1;
                }
            }
            if (hopCount > hopCount2) {
                return 4;
            }
            if ((routeInfo2.isTunnelled() && !routeInfo.isTunnelled()) || (routeInfo2.isLayered() && !routeInfo.isLayered())) {
                return -1;
            }
            if (routeInfo.isTunnelled() && !routeInfo2.isTunnelled()) {
                return 3;
            }
            if (routeInfo.isLayered() && !routeInfo2.isLayered()) {
                return 5;
            }
            return routeInfo.isSecure() != routeInfo2.isSecure() ? -1 : 0;
        }
        return -1;
    }
}
