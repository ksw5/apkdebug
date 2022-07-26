package com.good.gd.apache.http.client.protocol;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthState;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestProxyAuthentication implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(RequestProxyAuthentication.class);

    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        AuthState authState;
        AuthScheme authScheme;
        if (httpRequest != null) {
            if (httpContext != null) {
                if (httpRequest.containsHeader(AUTH.PROXY_AUTH_RESP) || (authState = (AuthState) httpContext.getAttribute(ClientContext.PROXY_AUTH_STATE)) == null || (authScheme = authState.getAuthScheme()) == null) {
                    return;
                }
                Credentials credentials = authState.getCredentials();
                if (credentials == null) {
                    this.log.debug("User credentials not available");
                    return;
                } else if (authState.getAuthScope() == null && authScheme.isConnectionBased()) {
                    return;
                } else {
                    try {
                        httpRequest.addHeader(authScheme.authenticate(credentials, httpRequest));
                        return;
                    } catch (AuthenticationException e) {
                        if (!this.log.isErrorEnabled()) {
                            return;
                        }
                        this.log.error("Proxy authentication error: " + e.getMessage());
                        return;
                    }
                }
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
