package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.client.AuthenticationHandler;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.client.CredentialsProvider;
import com.good.gd.apache.http.client.HttpRequestRetryHandler;
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.UserTokenHandler;
import com.good.gd.apache.http.client.params.AuthPolicy;
import com.good.gd.apache.http.client.params.CookiePolicy;
import com.good.gd.apache.http.client.protocol.ClientContext;
import com.good.gd.apache.http.client.protocol.RequestAddCookies;
import com.good.gd.apache.http.client.protocol.RequestDefaultHeaders;
import com.good.gd.apache.http.client.protocol.RequestProxyAuthentication;
import com.good.gd.apache.http.client.protocol.RequestTargetAuthentication;
import com.good.gd.apache.http.client.protocol.ResponseProcessCookies;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.conn.scheme.PlainSocketFactory;
import com.good.gd.apache.http.conn.scheme.Scheme;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.conn.ssl.SSLSocketFactory;
import com.good.gd.apache.http.cookie.CookieSpecRegistry;
import com.good.gd.apache.http.impl.DefaultConnectionReuseStrategy;
import com.good.gd.apache.http.impl.auth.BasicSchemeFactory;
import com.good.gd.apache.http.impl.auth.DigestSchemeFactory;
import com.good.gd.apache.http.impl.conn.ProxySelectorRoutePlanner;
import com.good.gd.apache.http.impl.conn.SingleClientConnManager;
import com.good.gd.apache.http.impl.cookie.BestMatchSpecFactory;
import com.good.gd.apache.http.impl.cookie.BrowserCompatSpecFactory;
import com.good.gd.apache.http.impl.cookie.NetscapeDraftSpecFactory;
import com.good.gd.apache.http.impl.cookie.RFC2109SpecFactory;
import com.good.gd.apache.http.impl.cookie.RFC2965SpecFactory;
import com.good.gd.apache.http.params.BasicHttpParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.params.HttpProtocolParams;
import com.good.gd.apache.http.protocol.BasicHttpContext;
import com.good.gd.apache.http.protocol.BasicHttpProcessor;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.protocol.HttpRequestExecutor;
import com.good.gd.apache.http.protocol.RequestConnControl;
import com.good.gd.apache.http.protocol.RequestContent;
import com.good.gd.apache.http.protocol.RequestExpectContinue;
import com.good.gd.apache.http.protocol.RequestTargetHost;
import com.good.gd.apache.http.protocol.RequestUserAgent;
import com.good.gd.apache.http.util.VersionInfo;

/* loaded from: classes.dex */
public class DefaultHttpClient extends AbstractHttpClient {
    public DefaultHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    public AuthSchemeRegistry createAuthSchemeRegistry() {
        AuthSchemeRegistry authSchemeRegistry = new AuthSchemeRegistry();
        authSchemeRegistry.register(AuthPolicy.BASIC, new BasicSchemeFactory());
        authSchemeRegistry.register(AuthPolicy.DIGEST, new DigestSchemeFactory());
        return authSchemeRegistry;
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected ClientConnectionManager createClientConnectionManager() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        getParams();
        return new SingleClientConnManager(getParams(), schemeRegistry);
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        return new DefaultConnectionReuseStrategy();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected CookieSpecRegistry createCookieSpecRegistry() {
        CookieSpecRegistry cookieSpecRegistry = new CookieSpecRegistry();
        cookieSpecRegistry.register(CookiePolicy.BEST_MATCH, new BestMatchSpecFactory());
        cookieSpecRegistry.register(CookiePolicy.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory());
        cookieSpecRegistry.register(CookiePolicy.NETSCAPE, new NetscapeDraftSpecFactory());
        cookieSpecRegistry.register(CookiePolicy.RFC_2109, new RFC2109SpecFactory());
        cookieSpecRegistry.register(CookiePolicy.RFC_2965, new RFC2965SpecFactory());
        return cookieSpecRegistry;
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected CookieStore createCookieStore() {
        return new BasicCookieStore();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected CredentialsProvider createCredentialsProvider() {
        return new BasicCredentialsProvider();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpContext createHttpContext() {
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute(ClientContext.AUTHSCHEME_REGISTRY, getAuthSchemes());
        basicHttpContext.setAttribute(ClientContext.COOKIESPEC_REGISTRY, getCookieSpecs());
        basicHttpContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
        basicHttpContext.setAttribute(ClientContext.CREDS_PROVIDER, getCredentialsProvider());
        return basicHttpContext;
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpParams createHttpParams() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(basicHttpParams, "ISO-8859-1");
        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
        VersionInfo loadVersionInfo = VersionInfo.loadVersionInfo("org.apache.http.client", getClass().getClassLoader());
        HttpProtocolParams.setUserAgent(basicHttpParams, "Apache-HttpClient/" + (loadVersionInfo != null ? loadVersionInfo.getRelease() : VersionInfo.UNAVAILABLE) + " (java 1.4)");
        return basicHttpParams;
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        basicHttpProcessor.addInterceptor(new RequestDefaultHeaders());
        basicHttpProcessor.addInterceptor(new RequestContent());
        basicHttpProcessor.addInterceptor(new RequestTargetHost());
        basicHttpProcessor.addInterceptor(new RequestConnControl());
        basicHttpProcessor.addInterceptor(new RequestUserAgent());
        basicHttpProcessor.addInterceptor(new RequestExpectContinue());
        basicHttpProcessor.addInterceptor(new RequestAddCookies());
        basicHttpProcessor.addInterceptor(new ResponseProcessCookies());
        basicHttpProcessor.addInterceptor(new RequestTargetAuthentication());
        basicHttpProcessor.addInterceptor(new RequestProxyAuthentication());
        return basicHttpProcessor;
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new ProxySelectorRoutePlanner(getConnectionManager().getSchemeRegistry(), null);
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected AuthenticationHandler createProxyAuthenticationHandler() {
        return new DefaultProxyAuthenticationHandler();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected RedirectHandler createRedirectHandler() {
        return new DefaultRedirectHandler();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpRequestExecutor createRequestExecutor() {
        return new HttpRequestExecutor();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected AuthenticationHandler createTargetAuthenticationHandler() {
        return new DefaultTargetAuthenticationHandler();
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected UserTokenHandler createUserTokenHandler() {
        return new DefaultUserTokenHandler();
    }

    public DefaultHttpClient(HttpParams httpParams) {
        super(null, httpParams);
    }

    public DefaultHttpClient() {
        super(null, null);
    }
}
