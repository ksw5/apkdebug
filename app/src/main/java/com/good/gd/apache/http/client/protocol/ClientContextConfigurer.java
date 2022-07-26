package com.good.gd.apache.http.client.protocol;

import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.client.CredentialsProvider;
import com.good.gd.apache.http.cookie.CookieSpecRegistry;
import com.good.gd.apache.http.protocol.HttpContext;
import java.util.List;

/* loaded from: classes.dex */
public class ClientContextConfigurer implements ClientContext {
    private final HttpContext context;

    public ClientContextConfigurer(HttpContext httpContext) {
        if (httpContext != null) {
            this.context = httpContext;
            return;
        }
        throw new IllegalArgumentException("HTTP context may not be null");
    }

    public void setAuthSchemePref(List<String> list) {
        this.context.setAttribute(ClientContext.AUTH_SCHEME_PREF, list);
    }

    public void setAuthSchemeRegistry(AuthSchemeRegistry authSchemeRegistry) {
        this.context.setAttribute(ClientContext.AUTHSCHEME_REGISTRY, authSchemeRegistry);
    }

    public void setCookieSpecRegistry(CookieSpecRegistry cookieSpecRegistry) {
        this.context.setAttribute(ClientContext.COOKIESPEC_REGISTRY, cookieSpecRegistry);
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.context.setAttribute(ClientContext.CREDS_PROVIDER, credentialsProvider);
    }
}
