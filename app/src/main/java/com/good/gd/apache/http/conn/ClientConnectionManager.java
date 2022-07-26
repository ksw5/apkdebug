package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public interface ClientConnectionManager {
    void closeExpiredConnections();

    void closeIdleConnections(long j, TimeUnit timeUnit);

    SchemeRegistry getSchemeRegistry();

    void releaseConnection(ManagedClientConnection managedClientConnection, long j, TimeUnit timeUnit);

    ClientConnectionRequest requestConnection(HttpRoute httpRoute, Object obj);

    void shutdown();
}
