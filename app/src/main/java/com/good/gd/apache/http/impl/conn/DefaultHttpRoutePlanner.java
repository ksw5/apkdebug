package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.conn.params.ConnRouteParams;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.protocol.HttpContext;
import java.net.InetAddress;

/* loaded from: classes.dex */
public class DefaultHttpRoutePlanner implements HttpRoutePlanner {
    protected SchemeRegistry schemeRegistry;

    public DefaultHttpRoutePlanner(SchemeRegistry schemeRegistry) {
        if (schemeRegistry != null) {
            this.schemeRegistry = schemeRegistry;
            return;
        }
        throw new IllegalArgumentException("SchemeRegistry must not be null.");
    }

    @Override // com.good.gd.apache.http.conn.routing.HttpRoutePlanner
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        if (httpRequest != null) {
            HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
            if (forcedRoute != null) {
                return forcedRoute;
            }
            if (httpHost != null) {
                InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
                HttpHost defaultProxy = ConnRouteParams.getDefaultProxy(httpRequest.getParams());
                boolean isLayered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
                if (defaultProxy == null) {
                    return new HttpRoute(httpHost, localAddress, isLayered);
                }
                return new HttpRoute(httpHost, localAddress, defaultProxy, isLayered);
            }
            throw new IllegalStateException("Target host must not be null.");
        }
        throw new IllegalStateException("Request must not be null.");
    }
}
