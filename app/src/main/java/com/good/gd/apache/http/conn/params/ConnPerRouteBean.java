package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.conn.routing.HttpRoute;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class ConnPerRouteBean implements ConnPerRoute {
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private int defaultMax;
    private final Map<HttpRoute, Integer> maxPerHostMap;

    public ConnPerRouteBean(int i) {
        this.maxPerHostMap = new HashMap();
        setDefaultMaxPerRoute(i);
    }

    public int getDefaultMax() {
        return this.defaultMax;
    }

    @Override // com.good.gd.apache.http.conn.params.ConnPerRoute
    public int getMaxForRoute(HttpRoute httpRoute) {
        if (httpRoute != null) {
            Integer num = this.maxPerHostMap.get(httpRoute);
            if (num != null) {
                return num.intValue();
            }
            return this.defaultMax;
        }
        throw new IllegalArgumentException("HTTP route may not be null.");
    }

    public void setDefaultMaxPerRoute(int i) {
        if (i >= 1) {
            this.defaultMax = i;
            return;
        }
        throw new IllegalArgumentException("The maximum must be greater than 0.");
    }

    public void setMaxForRoute(HttpRoute httpRoute, int i) {
        if (httpRoute != null) {
            if (i >= 1) {
                this.maxPerHostMap.put(httpRoute, Integer.valueOf(i));
                return;
            }
            throw new IllegalArgumentException("The maximum must be greater than 0.");
        }
        throw new IllegalArgumentException("HTTP route may not be null.");
    }

    public void setMaxForRoutes(Map<HttpRoute, Integer> map) {
        if (map == null) {
            return;
        }
        this.maxPerHostMap.clear();
        this.maxPerHostMap.putAll(map);
    }

    public ConnPerRouteBean() {
        this(2);
    }
}
