package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpClientConnection;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpInetConnection;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;

/* loaded from: classes.dex */
public interface ManagedClientConnection extends HttpClientConnection, HttpInetConnection, ConnectionReleaseTrigger {
    HttpRoute getRoute();

    SSLSession getSSLSession();

    Object getState();

    boolean isMarkedReusable();

    boolean isSecure();

    void layerProtocol(HttpContext httpContext, HttpParams httpParams) throws IOException;

    void markReusable();

    void open(HttpRoute httpRoute, HttpContext httpContext, HttpParams httpParams) throws IOException;

    void setIdleDuration(long j, TimeUnit timeUnit);

    void setState(Object obj);

    void tunnelProxy(HttpHost httpHost, boolean z, HttpParams httpParams) throws IOException;

    void tunnelTarget(boolean z, HttpParams httpParams) throws IOException;

    void unmarkReusable();
}
