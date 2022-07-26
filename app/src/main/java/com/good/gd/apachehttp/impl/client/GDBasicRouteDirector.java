package com.good.gd.apachehttp.impl.client;

import com.good.gd.apache.http.conn.routing.BasicRouteDirector;
import com.good.gd.apache.http.conn.routing.RouteInfo;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDBasicRouteDirector extends BasicRouteDirector {
    @Override // com.good.gd.apache.http.conn.routing.BasicRouteDirector
    protected int directStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        GDLog.DBGPRINTF(16, "GDBasicRouteDirector::directStep() IN: plan.isSecure() =" + routeInfo.isSecure() + ", fact.isSecure() = " + routeInfo2.isSecure() + "\n");
        if (routeInfo2.getHopCount() <= 1 && routeInfo.getTargetHost().equals(routeInfo2.getTargetHost())) {
            if (routeInfo.isSecure() && !routeInfo2.isSecure()) {
                GDLog.DBGPRINTF(16, "GDBasicRouteDirector::directStep() - fact route not secure\n");
                return -1;
            } else if (routeInfo.getLocalAddress() != null && !routeInfo.getLocalAddress().equals(routeInfo2.getLocalAddress())) {
                return -1;
            } else {
                GDLog.DBGPRINTF(16, "GDBasicRouteDirector::directStep() OUT\n");
                return 0;
            }
        }
        return -1;
    }

    @Override // com.good.gd.apache.http.conn.routing.BasicRouteDirector, com.good.gd.apache.http.conn.routing.HttpRouteDirector
    public int nextStep(RouteInfo routeInfo, RouteInfo routeInfo2) {
        int firstStep;
        GDLog.DBGPRINTF(16, "GDBasicRouteDirector::nextStep() IN\n");
        if (routeInfo != null) {
            if (routeInfo2 != null && routeInfo2.getHopCount() >= 1) {
                if (routeInfo.getHopCount() > 1) {
                    firstStep = proxiedStep(routeInfo, routeInfo2);
                } else {
                    firstStep = directStep(routeInfo, routeInfo2);
                }
            } else {
                firstStep = firstStep(routeInfo);
            }
            GDLog.DBGPRINTF(16, "GDBasicRouteDirector::nextStep() OUT\n");
            return firstStep;
        }
        throw new IllegalArgumentException("Planned route may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.routing.BasicRouteDirector
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
            if (!routeInfo.isSecure() || routeInfo2.isSecure()) {
                return 0;
            }
            GDLog.DBGPRINTF(16, "GDBasicRouteDirector::proxiedStep() - fact route not secure\n");
            return -1;
        }
        return -1;
    }
}
