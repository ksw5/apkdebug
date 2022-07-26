package com.good.gd.apache.http.client.protocol;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.client.methods.HttpUriRequest;
import com.good.gd.apache.http.client.params.HttpClientParams;
import com.good.gd.apache.http.conn.ManagedClientConnection;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.CookieSpec;
import com.good.gd.apache.http.cookie.CookieSpecRegistry;
import com.good.gd.apache.http.protocol.ExecutionContext;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class RequestAddCookies implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(RequestAddCookies.class);

    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        URI uri;
        Header versionHeader;
        if (httpRequest != null) {
            if (httpContext != null) {
                CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
                if (cookieStore == null) {
                    this.log.info("Cookie store not available in HTTP context");
                    return;
                }
                CookieSpecRegistry cookieSpecRegistry = (CookieSpecRegistry) httpContext.getAttribute(ClientContext.COOKIESPEC_REGISTRY);
                if (cookieSpecRegistry == null) {
                    this.log.info("CookieSpec registry not available in HTTP context");
                    return;
                }
                HttpHost httpHost = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (httpHost != null) {
                    ManagedClientConnection managedClientConnection = (ManagedClientConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
                    if (managedClientConnection != null) {
                        String cookiePolicy = HttpClientParams.getCookiePolicy(httpRequest.getParams());
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("CookieSpec selected: " + cookiePolicy);
                        }
                        if (httpRequest instanceof HttpUriRequest) {
                            uri = ((HttpUriRequest) httpRequest).getURI();
                        } else {
                            try {
                                uri = new URI(httpRequest.getRequestLine().getUri());
                            } catch (URISyntaxException e) {
                                throw new ProtocolException("Invalid request URI: " + httpRequest.getRequestLine().getUri(), e);
                            }
                        }
                        String hostName = httpHost.getHostName();
                        int port = httpHost.getPort();
                        if (port < 0) {
                            port = managedClientConnection.getRemotePort();
                        }
                        CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, uri.getPath(), managedClientConnection.isSecure());
                        CookieSpec cookieSpec = cookieSpecRegistry.getCookieSpec(cookiePolicy, httpRequest.getParams());
                        ArrayList arrayList = new ArrayList(cookieStore.getCookies());
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            Cookie cookie = (Cookie) it.next();
                            if (cookieSpec.match(cookie, cookieOrigin)) {
                                if (this.log.isDebugEnabled()) {
                                    this.log.debug("Cookie " + cookie + " match " + cookieOrigin);
                                }
                                arrayList2.add(cookie);
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            for (Header header : cookieSpec.formatCookies(arrayList2)) {
                                httpRequest.addHeader(header);
                            }
                        }
                        int version = cookieSpec.getVersion();
                        if (version > 0) {
                            boolean z = false;
                            Iterator it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                if (version != ((Cookie) it2.next()).getVersion()) {
                                    z = true;
                                }
                            }
                            if (z && (versionHeader = cookieSpec.getVersionHeader()) != null) {
                                httpRequest.addHeader(versionHeader);
                            }
                        }
                        httpContext.setAttribute(ClientContext.COOKIE_SPEC, cookieSpec);
                        httpContext.setAttribute(ClientContext.COOKIE_ORIGIN, cookieOrigin);
                        return;
                    }
                    throw new IllegalStateException("Client connection not specified in HTTP context");
                }
                throw new IllegalStateException("Target host not specified in HTTP context");
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
