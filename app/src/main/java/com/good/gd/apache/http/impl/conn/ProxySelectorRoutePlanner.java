package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.conn.params.ConnRoutePNames;
import com.good.gd.apache.http.conn.params.ConnRouteParams;
import com.good.gd.apache.http.conn.routing.HttpRoute;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.protocol.HttpContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/* loaded from: classes.dex */
public class ProxySelectorRoutePlanner implements HttpRoutePlanner {
    protected ProxySelector proxySelector;
    protected SchemeRegistry schemeRegistry;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static /* synthetic */ class hbfhc {
        static final /* synthetic */ int[] dbjc;

        static {
            int[] iArr = new int[Proxy.Type.values().length];
            dbjc = iArr;
            try {
                iArr[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                dbjc[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                dbjc[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public ProxySelectorRoutePlanner(SchemeRegistry schemeRegistry, ProxySelector proxySelector) {
        if (schemeRegistry != null) {
            this.schemeRegistry = schemeRegistry;
            this.proxySelector = proxySelector;
            return;
        }
        throw new IllegalArgumentException("SchemeRegistry must not be null.");
    }

    protected Proxy chooseProxy(List<Proxy> list, HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        if (list != null && !list.isEmpty()) {
            Proxy proxy = null;
            for (int i = 0; proxy == null && i < list.size(); i++) {
                Proxy proxy2 = list.get(i);
                int i2 = hbfhc.dbjc[proxy2.type().ordinal()];
                if (i2 == 1 || i2 == 2) {
                    proxy = proxy2;
                }
            }
            return proxy == null ? Proxy.NO_PROXY : proxy;
        }
        throw new IllegalArgumentException("Proxy list must not be empty.");
    }

    protected HttpHost determineProxy(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        ProxySelector proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector = ProxySelector.getDefault();
        }
        if (proxySelector == null) {
            return null;
        }
        try {
            Proxy chooseProxy = chooseProxy(proxySelector.select(new URI(httpHost.toURI())), httpHost, httpRequest, httpContext);
            if (chooseProxy.type() != Proxy.Type.HTTP) {
                return null;
            }
            if (chooseProxy.address() instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) chooseProxy.address();
                return new HttpHost(getHost(inetSocketAddress), inetSocketAddress.getPort());
            }
            throw new HttpException("Unable to handle non-Inet proxy address: " + chooseProxy.address());
        } catch (URISyntaxException e) {
            throw new HttpException("Cannot convert host to URI: " + httpHost, e);
        }
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
                HttpHost httpHost2 = (HttpHost) httpRequest.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
                if (httpHost2 == null) {
                    httpHost2 = determineProxy(httpHost, httpRequest, httpContext);
                } else if (ConnRouteParams.NO_HOST.equals(httpHost2)) {
                    httpHost2 = null;
                }
                boolean isLayered = this.schemeRegistry.getScheme(httpHost.getSchemeName()).isLayered();
                if (httpHost2 == null) {
                    return new HttpRoute(httpHost, localAddress, isLayered);
                }
                return new HttpRoute(httpHost, localAddress, httpHost2, isLayered);
            }
            throw new IllegalStateException("Target host must not be null.");
        }
        throw new IllegalStateException("Request must not be null.");
    }

    protected String getHost(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }

    public ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public void setProxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
    }
}
