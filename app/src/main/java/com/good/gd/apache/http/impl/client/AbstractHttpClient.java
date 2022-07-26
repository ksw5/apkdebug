package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.client.AuthenticationHandler;
import com.good.gd.apache.http.client.ClientProtocolException;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.client.CredentialsProvider;
import com.good.gd.apache.http.client.HttpClient;
import com.good.gd.apache.http.client.HttpRequestRetryHandler;
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.RequestDirector;
import com.good.gd.apache.http.client.ResponseHandler;
import com.good.gd.apache.http.client.UserTokenHandler;
import com.good.gd.apache.http.client.methods.HttpUriRequest;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.cookie.CookieSpecRegistry;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.BasicHttpProcessor;
import com.good.gd.apache.http.protocol.DefaultedHttpContext;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.protocol.HttpProcessor;
import com.good.gd.apache.http.protocol.HttpRequestExecutor;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;

/* loaded from: classes.dex */
public abstract class AbstractHttpClient implements HttpClient {
    private ClientConnectionManager connManager;
    private CookieStore cookieStore;
    private CredentialsProvider credsProvider;
    private HttpParams defaultParams;
    private BasicHttpProcessor httpProcessor;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(getClass());
    private AuthenticationHandler proxyAuthHandler;
    private RedirectHandler redirectHandler;
    private HttpRequestExecutor requestExec;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private AuthSchemeRegistry supportedAuthSchemes;
    private CookieSpecRegistry supportedCookieSpecs;
    private AuthenticationHandler targetAuthHandler;
    private UserTokenHandler userTokenHandler;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.defaultParams = httpParams;
        this.connManager = clientConnectionManager;
    }

    private HttpHost determineTarget(HttpUriRequest httpUriRequest) {
        URI uri = httpUriRequest.getURI();
        if (!uri.isAbsolute()) {
            return null;
        }
        return new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme(), uri.toString());
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        getHttpProcessor().addInterceptor(httpRequestInterceptor);
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        getHttpProcessor().addInterceptor(httpResponseInterceptor);
    }

    public synchronized void clearRequestInterceptors() {
        getHttpProcessor().clearRequestInterceptors();
    }

    public synchronized void clearResponseInterceptors() {
        getHttpProcessor().clearResponseInterceptors();
    }

    protected abstract AuthSchemeRegistry createAuthSchemeRegistry();

    protected abstract ClientConnectionManager createClientConnectionManager();

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        return new DefaultRequestDirector(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }

    protected abstract ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy();

    protected abstract ConnectionReuseStrategy createConnectionReuseStrategy();

    protected abstract CookieSpecRegistry createCookieSpecRegistry();

    protected abstract CookieStore createCookieStore();

    protected abstract CredentialsProvider createCredentialsProvider();

    protected abstract HttpContext createHttpContext();

    protected abstract HttpParams createHttpParams();

    protected abstract BasicHttpProcessor createHttpProcessor();

    protected abstract HttpRequestRetryHandler createHttpRequestRetryHandler();

    protected abstract HttpRoutePlanner createHttpRoutePlanner();

    protected abstract AuthenticationHandler createProxyAuthenticationHandler();

    protected abstract RedirectHandler createRedirectHandler();

    protected abstract HttpRequestExecutor createRequestExecutor();

    protected abstract AuthenticationHandler createTargetAuthenticationHandler();

    protected abstract UserTokenHandler createUserTokenHandler();

    protected HttpParams determineParams(HttpRequest httpRequest) {
        return new ClientParamsStack(null, getParams(), httpRequest.getParams(), null);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return execute(httpUriRequest, (HttpContext) null);
    }

    public final synchronized AuthSchemeRegistry getAuthSchemes() {
        if (this.supportedAuthSchemes == null) {
            this.supportedAuthSchemes = createAuthSchemeRegistry();
        }
        return this.supportedAuthSchemes;
    }

    public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        if (this.keepAliveStrategy == null) {
            this.keepAliveStrategy = createConnectionKeepAliveStrategy();
        }
        return this.keepAliveStrategy;
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final synchronized ClientConnectionManager getConnectionManager() {
        if (this.connManager == null) {
            this.connManager = createClientConnectionManager();
        }
        return this.connManager;
    }

    public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy() {
        if (this.reuseStrategy == null) {
            this.reuseStrategy = createConnectionReuseStrategy();
        }
        return this.reuseStrategy;
    }

    public final synchronized CookieSpecRegistry getCookieSpecs() {
        if (this.supportedCookieSpecs == null) {
            this.supportedCookieSpecs = createCookieSpecRegistry();
        }
        return this.supportedCookieSpecs;
    }

    public final synchronized CookieStore getCookieStore() {
        if (this.cookieStore == null) {
            this.cookieStore = createCookieStore();
        }
        return this.cookieStore;
    }

    public final synchronized CredentialsProvider getCredentialsProvider() {
        if (this.credsProvider == null) {
            this.credsProvider = createCredentialsProvider();
        }
        return this.credsProvider;
    }

    protected final synchronized BasicHttpProcessor getHttpProcessor() {
        if (this.httpProcessor == null) {
            this.httpProcessor = createHttpProcessor();
        }
        return this.httpProcessor;
    }

    public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler() {
        if (this.retryHandler == null) {
            this.retryHandler = createHttpRequestRetryHandler();
        }
        return this.retryHandler;
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final synchronized HttpParams getParams() {
        if (this.defaultParams == null) {
            this.defaultParams = createHttpParams();
        }
        return this.defaultParams;
    }

    public final synchronized AuthenticationHandler getProxyAuthenticationHandler() {
        if (this.proxyAuthHandler == null) {
            this.proxyAuthHandler = createProxyAuthenticationHandler();
        }
        return this.proxyAuthHandler;
    }

    public final synchronized RedirectHandler getRedirectHandler() {
        if (this.redirectHandler == null) {
            this.redirectHandler = createRedirectHandler();
        }
        return this.redirectHandler;
    }

    public final synchronized HttpRequestExecutor getRequestExecutor() {
        if (this.requestExec == null) {
            this.requestExec = createRequestExecutor();
        }
        return this.requestExec;
    }

    public synchronized HttpRequestInterceptor getRequestInterceptor(int i) {
        return getHttpProcessor().getRequestInterceptor(i);
    }

    public synchronized int getRequestInterceptorCount() {
        return getHttpProcessor().getRequestInterceptorCount();
    }

    public synchronized HttpResponseInterceptor getResponseInterceptor(int i) {
        return getHttpProcessor().getResponseInterceptor(i);
    }

    public synchronized int getResponseInterceptorCount() {
        return getHttpProcessor().getResponseInterceptorCount();
    }

    public final synchronized HttpRoutePlanner getRoutePlanner() {
        if (this.routePlanner == null) {
            this.routePlanner = createHttpRoutePlanner();
        }
        return this.routePlanner;
    }

    public final synchronized AuthenticationHandler getTargetAuthenticationHandler() {
        if (this.targetAuthHandler == null) {
            this.targetAuthHandler = createTargetAuthenticationHandler();
        }
        return this.targetAuthHandler;
    }

    public final synchronized UserTokenHandler getUserTokenHandler() {
        if (this.userTokenHandler == null) {
            this.userTokenHandler = createUserTokenHandler();
        }
        return this.userTokenHandler;
    }

    public void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> cls) {
        getHttpProcessor().removeRequestInterceptorByClass(cls);
    }

    public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> cls) {
        getHttpProcessor().removeResponseInterceptorByClass(cls);
    }

    public synchronized void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry) {
        this.supportedAuthSchemes = authSchemeRegistry;
    }

    public synchronized void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry) {
        this.supportedCookieSpecs = cookieSpecRegistry;
    }

    public synchronized void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public synchronized void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credsProvider = credentialsProvider;
    }

    public synchronized void setHttpRequestRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.retryHandler = httpRequestRetryHandler;
    }

    public synchronized void setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.keepAliveStrategy = connectionKeepAliveStrategy;
    }

    public synchronized void setParams(HttpParams httpParams) {
        this.defaultParams = httpParams;
    }

    public synchronized void setProxyAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.proxyAuthHandler = authenticationHandler;
    }

    public synchronized void setRedirectHandler(RedirectHandler redirectHandler) {
        this.redirectHandler = redirectHandler;
    }

    public synchronized void setReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.reuseStrategy = connectionReuseStrategy;
    }

    public synchronized void setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.routePlanner = httpRoutePlanner;
    }

    public synchronized void setTargetAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        this.targetAuthHandler = authenticationHandler;
    }

    public synchronized void setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i) {
        getHttpProcessor().addInterceptor(httpRequestInterceptor, i);
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i) {
        getHttpProcessor().addInterceptor(httpResponseInterceptor, i);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        if (httpUriRequest != null) {
            return execute(determineTarget(httpUriRequest), httpUriRequest, httpContext);
        }
        throw new IllegalArgumentException("Request must not be null.");
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return execute(httpHost, httpRequest, (HttpContext) null);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        DefaultedHttpContext defaultedHttpContext;
        RequestDirector createClientRequestDirector;
        if (httpRequest != null) {
            synchronized (this) {
                HttpContext createHttpContext = createHttpContext();
                defaultedHttpContext = httpContext == null ? createHttpContext : new DefaultedHttpContext(httpContext, createHttpContext);
                createClientRequestDirector = createClientRequestDirector(getRequestExecutor(), getConnectionManager(), getConnectionReuseStrategy(), getConnectionKeepAliveStrategy(), getRoutePlanner(), getHttpProcessor().copy(), getHttpRequestRetryHandler(), getRedirectHandler(), getTargetAuthenticationHandler(), getProxyAuthenticationHandler(), getUserTokenHandler(), determineParams(httpRequest));
            }
            try {
                return createClientRequestDirector.execute(httpHost, httpRequest, defaultedHttpContext);
            } catch (HttpException e) {
                throw new ClientProtocolException(e);
            }
        }
        throw new IllegalArgumentException("Request must not be null.");
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return (T) execute(httpUriRequest, responseHandler, (HttpContext) null);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return (T) execute(determineTarget(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return (T) execute(httpHost, httpRequest, responseHandler, null);
    }

    @Override // com.good.gd.apache.http.client.HttpClient
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        if (responseHandler != null) {
            HttpResponse execute = execute(httpHost, httpRequest, httpContext);
            try {
                T handleResponse = responseHandler.handleResponse(execute);
                HttpEntity entity = execute.getEntity();
                if (entity != null) {
                    entity.consumeContent();
                }
                return handleResponse;
            } catch (Throwable th) {
                HttpEntity entity2 = execute.getEntity();
                if (entity2 != null) {
                    try {
                        entity2.consumeContent();
                    } catch (Throwable th2) {
                        this.log.warn("Error consuming content after an exception.", th2);
                    }
                }
                if (!(th instanceof Error)) {
                    if (!(th instanceof RuntimeException)) {
                        if (th instanceof IOException) {
                            throw th;
                        }
                        throw new UndeclaredThrowableException(th);
                    }
                    throw ((RuntimeException) th);
                }
                throw ((Error) th);
            }
        }
        throw new IllegalArgumentException("Response handler must not be null.");
    }
}
