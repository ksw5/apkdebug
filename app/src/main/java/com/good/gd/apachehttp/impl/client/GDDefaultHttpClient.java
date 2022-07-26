package com.good.gd.apachehttp.impl.client;

import com.good.gd.GDApacheHttpContainerState;
import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.auth.AuthSchemeRegistry;
import com.good.gd.apache.http.client.AuthenticationHandler;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.client.HttpRequestRetryHandler;
import com.good.gd.apache.http.client.RedirectHandler;
import com.good.gd.apache.http.client.RequestDirector;
import com.good.gd.apache.http.client.UserTokenHandler;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy;
import com.good.gd.apache.http.conn.routing.HttpRoutePlanner;
import com.good.gd.apache.http.impl.client.DefaultHttpClient;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpProcessor;
import com.good.gd.apache.http.protocol.HttpRequestExecutor;
import com.good.gd.apachehttp.impl.auth.KerberosHandler;
import com.good.gd.apachehttp.impl.auth.NTLMSchemeFactory;
import com.good.gd.apachehttp.impl.auth.NegotiateSchemeFactory;
import com.good.gd.apachehttp.impl.conn.GDDefaultClientConfigs;
import com.good.gd.apachehttp.impl.conn.GDProxySelectorRoutePlanner;
import com.good.gd.apachehttp.impl.conn.GDSingleClientConnManager;
import com.good.gd.apachehttp.protocol.GDHttpRequestExecutor;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDDefaultHttpClient extends DefaultHttpClient {
    private static String TAG = "GDDefaultHttpClient";
    private ContainerState containerState;
    private boolean layeredSSLFactoryByDefault = false;
    private GDDefaultClientConfigsImpl _configsImpl = null;
    private boolean hostVerificationDisabled = false;
    private boolean certVerificationDisabled = false;
    private GDSingleClientConnManager _connManager = null;

    /* loaded from: classes.dex */
    private class GDDefaultClientConfigsImpl implements GDDefaultClientConfigs {
        private GDDefaultClientConfigsImpl() {
        }

        @Override // com.good.gd.apachehttp.impl.conn.GDDefaultClientConfigs
        public boolean isCertVerificationDisabled() {
            return GDDefaultHttpClient.this.certVerificationDisabled;
        }

        @Override // com.good.gd.apachehttp.impl.conn.GDDefaultClientConfigs
        public boolean isHostVerificationDisabled() {
            return GDDefaultHttpClient.this.hostVerificationDisabled;
        }
    }

    public GDDefaultHttpClient() {
        ContainerState containerState = GDApacheHttpContainerState.getContainerState();
        this.containerState = containerState;
        containerState.checkAuthorized();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void clearCredentialsForScheme(int i) {
        if (i == 4) {
            KerberosHandler.getInstance().clearCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    public AuthSchemeRegistry createAuthSchemeRegistry() {
        GDLog.DBGPRINTF(16, TAG + "::createAuthSchemeRegistry() IN\n");
        AuthSchemeRegistry createAuthSchemeRegistry = super.createAuthSchemeRegistry();
        createAuthSchemeRegistry.register("negotiate", new NegotiateSchemeFactory());
        createAuthSchemeRegistry.register("ntlm", new NTLMSchemeFactory());
        GDLog.DBGPRINTF(16, TAG + "::createAuthSchemeRegistry() OUT\n");
        return createAuthSchemeRegistry;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0093  */
    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected ClientConnectionManager createClientConnectionManager() {
        /*
            r8 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = com.good.gd.apachehttp.impl.client.GDDefaultHttpClient.TAG
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "::createClientConnectionManager() IN\n"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = 16
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r0)
            r0 = 0
            boolean r2 = r8.layeredSSLFactoryByDefault     // Catch: java.lang.Exception -> L3c
            if (r2 != 0) goto L32
            com.good.gd.apache.http.params.HttpParams r2 = r8.getParams()     // Catch: java.lang.Exception -> L3c
            java.lang.String r3 = "http.route.default-proxy"
            java.lang.Object r2 = r2.getParameter(r3)     // Catch: java.lang.Exception -> L3c
            if (r2 == 0) goto L2c
            goto L32
        L2c:
            com.good.gd.apachehttp.conn.ssl.GDSSLPlainSocketFactory r2 = new com.good.gd.apachehttp.conn.ssl.GDSSLPlainSocketFactory     // Catch: java.lang.Exception -> L3c
            r2.<init>()     // Catch: java.lang.Exception -> L3c
            goto L56
        L32:
            com.good.gd.apachehttp.conn.ssl.GDSSLSocketFactory r2 = new com.good.gd.apachehttp.conn.ssl.GDSSLSocketFactory     // Catch: java.lang.Exception -> L3c
            boolean r3 = r8.certVerificationDisabled     // Catch: java.lang.Exception -> L3c
            boolean r4 = r8.hostVerificationDisabled     // Catch: java.lang.Exception -> L3c
            r2.<init>(r3, r4)     // Catch: java.lang.Exception -> L3c
            goto L56
        L3c:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = com.good.gd.apachehttp.impl.client.GDDefaultHttpClient.TAG
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "::createClientConnectionManager() exception"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r3, r2)
            r2 = r0
        L56:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = com.good.gd.apachehttp.impl.client.GDDefaultHttpClient.TAG
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "::createClientConnectionManager() - creating GD socket factories.\n"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r3)
            com.good.gd.apache.http.conn.scheme.SchemeRegistry r3 = new com.good.gd.apache.http.conn.scheme.SchemeRegistry
            r3.<init>()
            com.good.gd.apache.http.conn.scheme.Scheme r4 = new com.good.gd.apache.http.conn.scheme.Scheme
            com.good.gd.apachehttp.conn.scheme.GDPlainSocketFactory r5 = com.good.gd.apachehttp.conn.scheme.GDPlainSocketFactory.getSocketFactory()
            r6 = 80
            java.lang.String r7 = "http"
            r4.<init>(r7, r5, r6)
            r3.register(r4)
            com.good.gd.apache.http.conn.scheme.Scheme r4 = new com.good.gd.apache.http.conn.scheme.Scheme
            r5 = 443(0x1bb, float:6.21E-43)
            java.lang.String r6 = "https"
            r4.<init>(r6, r2, r5)
            r3.register(r4)
            com.good.gd.apachehttp.impl.client.GDDefaultHttpClient$GDDefaultClientConfigsImpl r2 = r8._configsImpl
            if (r2 != 0) goto L9a
            com.good.gd.apachehttp.impl.client.GDDefaultHttpClient$GDDefaultClientConfigsImpl r2 = new com.good.gd.apachehttp.impl.client.GDDefaultHttpClient$GDDefaultClientConfigsImpl
            r2.<init>()
            r8._configsImpl = r2
        L9a:
            com.good.gd.apachehttp.impl.conn.GDSingleClientConnManager r0 = new com.good.gd.apachehttp.impl.conn.GDSingleClientConnManager
            com.good.gd.apache.http.params.HttpParams r2 = r8.getParams()
            com.good.gd.apachehttp.impl.client.GDDefaultHttpClient$GDDefaultClientConfigsImpl r4 = r8._configsImpl
            r0.<init>(r2, r3, r4)
            r8._connManager = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = com.good.gd.apachehttp.impl.client.GDDefaultHttpClient.TAG
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = "::createClientConnectionManager() OUT - connManager:"
            java.lang.StringBuilder r0 = r0.append(r2)
            com.good.gd.apachehttp.impl.conn.GDSingleClientConnManager r2 = r8._connManager
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r0)
            com.good.gd.apachehttp.impl.conn.GDSingleClientConnManager r0 = r8._connManager
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apachehttp.impl.client.GDDefaultHttpClient.createClientConnectionManager():com.good.gd.apache.http.conn.ClientConnectionManager");
    }

    @Override // com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        return new GDDefaultRequestDirector(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected CookieStore createCookieStore() {
        return GDPersistentCookieStore.getInstance();
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new GDProxySelectorRoutePlanner(getConnectionManager().getSchemeRegistry(), null);
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected AuthenticationHandler createProxyAuthenticationHandler() {
        return new GDDefaultProxyAuthenticationHandler();
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected HttpRequestExecutor createRequestExecutor() {
        return new GDHttpRequestExecutor();
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultHttpClient, com.good.gd.apache.http.impl.client.AbstractHttpClient
    protected AuthenticationHandler createTargetAuthenticationHandler() {
        return new GDDefaultTargetAuthenticationHandler();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void internal_disableHostVerification() {
        this.hostVerificationDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void internal_disablePeerVerification() {
        this.hostVerificationDisabled = true;
        this.certVerificationDisabled = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void kerberosAllowDelegation(boolean z) {
        KerberosHandler.getInstance().allowDelegation(z);
    }
}
