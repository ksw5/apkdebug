package com.good.gd.apachehttp.impl.client;

import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.client.AuthenticationHandler;
import com.good.gd.apache.http.client.HttpRequestRetryHandler;
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.UserTokenHandler;
import com.good.gd.apache.http.client.params.HttpClientParams;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.impl.client.DefaultRequestDirector;
import com.good.gd.apache.http.impl.client.RoutedRequest;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.protocol.HttpProcessor;
import com.good.gd.apache.http.protocol.HttpRequestExecutor;
import com.good.gd.mtd.GDSafeUrl;
import com.good.gd.ndkproxy.GDConnectivityManagerNative;
import com.good.gd.ndkproxy.GDLog;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class GDDefaultRequestDirector extends DefaultRequestDirector {
    public GDDefaultRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        super(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
        GDLog.DBGPRINTF(16, "GDDefaultRequestDirector::GDDefaultRequestDirector()\n");
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultRequestDirector
    protected void establishRoute(HttpRoute httpRoute, HttpContext httpContext) throws HttpException, IOException {
        int nextStep;
        GDBasicRouteDirector gDBasicRouteDirector = new GDBasicRouteDirector();
        do {
            HttpRoute route = this.managedConn.getRoute();
            nextStep = gDBasicRouteDirector.nextStep(httpRoute, route);
            switch (nextStep) {
                case -1:
                    throw new IllegalStateException("Unable to establish route.\nplanned = " + httpRoute + "\ncurrent = " + route);
                case 0:
                    break;
                case 1:
                case 2:
                    this.managedConn.open(httpRoute, httpContext, this.params);
                    continue;
                case 3:
                    boolean createTunnelToTarget = createTunnelToTarget(httpRoute, httpContext);
                    GDLog.DBGPRINTF(16, "Tunnel to target created.");
                    this.managedConn.tunnelTarget(createTunnelToTarget, this.params);
                    continue;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean createTunnelToProxy = createTunnelToProxy(httpRoute, hopCount, httpContext);
                    GDLog.DBGPRINTF(16, "Tunnel to proxy created.");
                    this.managedConn.tunnelProxy(httpRoute.getHopTarget(hopCount), createTunnelToProxy, this.params);
                    continue;
                case 5:
                    this.managedConn.layerProtocol(httpContext, this.params);
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + nextStep + " from RouteDirector.");
            }
        } while (nextStep > 0);
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultRequestDirector, com.good.gd.apache.http.client.RequestDirector
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpHost != null) {
            String hostName = httpHost.getHostName();
            int port = httpHost.getPort() == -1 ? "https".equalsIgnoreCase(httpHost.getSchemeName()) ? 443 : 80 : httpHost.getPort();
            if (hostName != null && GDConnectivityManagerNative.RouteType.DENY == GDConnectivityManagerNative.getRouteType(hostName, port)) {
                GDLog.DBGPRINTF(13, "Connection is Denied Route");
                throw new HttpException("Connection is Denied Route");
            }
            String originalUri = httpHost.getOriginalUri();
            if (originalUri != null && !new GDSafeUrl().checkUrl(originalUri)) {
                GDLog.DBGPRINTF(16, "URL is unsafe. \n");
                throw new HttpException("URL is unsafe.");
            }
        }
        return super.execute(httpHost, httpRequest, httpContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.client.DefaultRequestDirector
    public RoutedRequest handleResponse(RoutedRequest routedRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (HttpClientParams.isRedirecting(routedRequest.getRequest().getParams()) && this.redirectHandler.isRedirectRequested(httpResponse, httpContext)) {
            if (httpResponse != null) {
                Header firstHeader = httpResponse.getFirstHeader("location");
                if (firstHeader != null) {
                    String value = firstHeader.getValue();
                    try {
                        URI uri = new URI(value);
                        GDLog.DBGPRINTF(16, "SafeUrl: Apache: checkUrl for redirect");
                        if (!new GDSafeUrl().checkUrl(uri.toString())) {
                            GDLog.DBGPRINTF(16, "Redirect URL is unsafe");
                            throw new HttpException("Redirect URL is unsafe.");
                        }
                    } catch (URISyntaxException e) {
                        throw new ProtocolException("Invalid redirect URI: " + value, e);
                    }
                } else {
                    throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
                }
            } else {
                throw new IllegalArgumentException("HTTP response should not be null");
            }
        }
        return super.handleResponse(routedRequest, httpResponse, httpContext);
    }
}
