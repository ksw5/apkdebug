package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpStatus;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.client.CircularRedirectException;
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.params.ClientPNames;
import com.good.gd.apache.http.client.utils.URIUtils;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.ExecutionContext;
import com.good.gd.apache.http.protocol.HttpContext;
import java.net.URI;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class DefaultRedirectHandler implements RedirectHandler {
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final Log log = LogFactory.getLog(DefaultRedirectHandler.class);

    @Override // com.good.gd.apache.http.client.RedirectHandler
    public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        URI rewriteURI;
        if (httpResponse != null) {
            Header firstHeader = httpResponse.getFirstHeader("location");
            if (firstHeader != null) {
                String value = firstHeader.getValue();
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Redirect requested to location '" + value + "'");
                }
                try {
                    URI uri = new URI(value);
                    HttpParams params = httpResponse.getParams();
                    if (!uri.isAbsolute()) {
                        if (!params.isParameterTrue(ClientPNames.REJECT_RELATIVE_REDIRECT)) {
                            HttpHost httpHost = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                            if (httpHost != null) {
                                try {
                                    uri = URIUtils.resolve(URIUtils.rewriteURI(new URI(((HttpRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST)).getRequestLine().getUri()), httpHost, true), uri);
                                } catch (URISyntaxException e) {
                                    throw new ProtocolException(e.getMessage(), e);
                                }
                            } else {
                                throw new IllegalStateException("Target host not available in the HTTP context");
                            }
                        } else {
                            throw new ProtocolException("Relative redirect location '" + uri + "' not allowed");
                        }
                    }
                    if (params.isParameterFalse(ClientPNames.ALLOW_CIRCULAR_REDIRECTS)) {
                        RedirectLocations redirectLocations = (RedirectLocations) httpContext.getAttribute(REDIRECT_LOCATIONS);
                        if (redirectLocations == null) {
                            redirectLocations = new RedirectLocations();
                            httpContext.setAttribute(REDIRECT_LOCATIONS, redirectLocations);
                        }
                        if (uri.getFragment() != null) {
                            try {
                                rewriteURI = URIUtils.rewriteURI(uri, new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), true);
                            } catch (URISyntaxException e2) {
                                throw new ProtocolException(e2.getMessage(), e2);
                            }
                        } else {
                            rewriteURI = uri;
                        }
                        if (!redirectLocations.contains(rewriteURI)) {
                            redirectLocations.add(rewriteURI);
                        } else {
                            throw new CircularRedirectException("Circular redirect to '" + rewriteURI + "'");
                        }
                    }
                    return uri;
                } catch (URISyntaxException e3) {
                    throw new ProtocolException("Invalid redirect URI: " + value, e3);
                }
            }
            throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    @Override // com.good.gd.apache.http.client.RedirectHandler
    public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 307) {
                return true;
            }
            switch (statusCode) {
                case HttpStatus.SC_MOVED_PERMANENTLY /* 301 */:
                case HttpStatus.SC_MOVED_TEMPORARILY /* 302 */:
                case HttpStatus.SC_SEE_OTHER /* 303 */:
                    return true;
                default:
                    return false;
            }
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
