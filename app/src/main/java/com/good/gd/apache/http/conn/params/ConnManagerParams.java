package com.good.gd.apache.http.conn.params;

import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public final class ConnManagerParams implements ConnManagerPNames {
    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new hbfhc();
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

    /* loaded from: classes.dex */
    static class hbfhc implements ConnPerRoute {
        hbfhc() {
        }

        @Override // com.good.gd.apache.http.conn.params.ConnPerRoute
        public int getMaxForRoute(HttpRoute httpRoute) {
            return 2;
        }
    }

    public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams httpParams) {
        if (httpParams != null) {
            ConnPerRoute connPerRoute = (ConnPerRoute) httpParams.getParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE);
            return connPerRoute == null ? DEFAULT_CONN_PER_ROUTE : connPerRoute;
        }
        throw new IllegalArgumentException("HTTP parameters must not be null.");
    }

    public static int getMaxTotalConnections(HttpParams httpParams) {
        if (httpParams != null) {
            return httpParams.getIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 20);
        }
        throw new IllegalArgumentException("HTTP parameters must not be null.");
    }

    public static long getTimeout(HttpParams httpParams) {
        if (httpParams != null) {
            return httpParams.getLongParameter(ConnManagerPNames.TIMEOUT, 0L);
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }

    public static void setMaxConnectionsPerRoute(HttpParams httpParams, ConnPerRoute connPerRoute) {
        if (httpParams != null) {
            httpParams.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, connPerRoute);
            return;
        }
        throw new IllegalArgumentException("HTTP parameters must not be null.");
    }

    public static void setMaxTotalConnections(HttpParams httpParams, int i) {
        if (httpParams != null) {
            httpParams.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, i);
            return;
        }
        throw new IllegalArgumentException("HTTP parameters must not be null.");
    }

    public static void setTimeout(HttpParams httpParams, long j) {
        if (httpParams != null) {
            httpParams.setLongParameter(ConnManagerPNames.TIMEOUT, j);
            return;
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }
}
