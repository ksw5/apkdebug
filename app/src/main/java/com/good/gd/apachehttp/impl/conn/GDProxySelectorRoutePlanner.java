package com.good.gd.apachehttp.impl.conn;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.conn.params.ConnRoutePNames;
import com.good.gd.apache.http.conn.params.ConnRouteParams;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.scheme.Scheme;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.impl.conn.ProxySelectorRoutePlanner;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apachehttp.conn.ssl.GDSSLPlainSocketFactory;
import com.good.gd.ndkproxy.GDLog;
import java.net.InetAddress;
import java.net.ProxySelector;

/* loaded from: classes.dex */
public class GDProxySelectorRoutePlanner extends ProxySelectorRoutePlanner {
    public GDProxySelectorRoutePlanner(SchemeRegistry schemeRegistry, ProxySelector proxySelector) {
        super(schemeRegistry, proxySelector);
    }

    @Override // com.good.gd.apache.http.impl.conn.ProxySelectorRoutePlanner, com.good.gd.apache.http.conn.routing.HttpRoutePlanner
    public HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        GDLog.DBGPRINTF(16, "GDProxySelectorRoutePlanner:determineRoute()\n");
        if (httpRequest != null) {
            HttpRoute forcedRoute = ConnRouteParams.getForcedRoute(httpRequest.getParams());
            if (forcedRoute != null) {
                return forcedRoute;
            }
            if (httpHost != null) {
                InetAddress localAddress = ConnRouteParams.getLocalAddress(httpRequest.getParams());
                HttpHost httpHost2 = (HttpHost) httpRequest.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
                Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
                boolean isLayered = scheme.isLayered();
                if (httpHost2 == null) {
                    if (!isLayered && (scheme.getSocketFactory() instanceof GDSSLPlainSocketFactory)) {
                        isLayered = true;
                    }
                    return new HttpRoute(httpHost, localAddress, isLayered);
                }
                GDLog.DBGPRINTF(14, "GDProxySelectorRoutePlanner:determineRoute() Client Proxy Set\n");
                return new HttpRoute(httpHost, localAddress, httpHost2, isLayered);
            }
            throw new IllegalStateException("Target host must not be null.");
        }
        throw new IllegalStateException("Request must not be null.");
    }

    @Override // com.good.gd.apache.http.impl.conn.ProxySelectorRoutePlanner
    public ProxySelector getProxySelector() {
        GDLog.DBGPRINTF(14, "GDProxySelectorRoutePlanner:getProxySelector()\n");
        return this.proxySelector;
    }

    @Override // com.good.gd.apache.http.impl.conn.ProxySelectorRoutePlanner
    public void setProxySelector(ProxySelector proxySelector) {
        GDLog.DBGPRINTF(14, "GDProxySelectorRoutePlanner:setProxySelector()\n");
        this.proxySelector = proxySelector;
    }
}
