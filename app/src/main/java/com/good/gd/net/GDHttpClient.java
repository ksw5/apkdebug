package com.good.gd.net;

import com.good.gd.GDApacheHttpContainerState;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.auth.AuthScope;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apachehttp.auth.Kerberos5Credentials;
import com.good.gd.apachehttp.impl.client.GDDefaultHttpClient;
import java.io.IOException;

/* loaded from: classes.dex */
public final class GDHttpClient extends GDDefaultHttpClient {
    public static final int HTTP_AUTH_ALL_METHODS = 0;
    public static final int HTTP_AUTH_METHOD_BASIC = 1;
    public static final int HTTP_AUTH_METHOD_DIGEST = 2;
    public static final int HTTP_AUTH_METHOD_NEGOTIATE = 4;
    public static final int HTTP_AUTH_METHOD_NTLM = 3;

    /* loaded from: classes.dex */
    class hbfhc implements HttpRequestInterceptor {
        hbfhc() {
        }

        @Override // com.good.gd.apache.http.HttpRequestInterceptor
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, -1);
            if (GDHttpClient.this.getCredentialsProvider().getCredentials(authScope) == null) {
                GDHttpClient.this.getCredentialsProvider().setCredentials(authScope, new Kerberos5Credentials("", "", ""));
            }
        }
    }

    public GDHttpClient() {
        GDApacheHttpContainerState.getContainerState().checkAuthorized();
        addRequestInterceptor(new hbfhc());
    }

    @Override // com.good.gd.apachehttp.impl.client.GDDefaultHttpClient
    public final void clearCredentialsForScheme(int i) {
        super.clearCredentialsForScheme(i);
    }

    public final void disableHostVerification() {
        super.internal_disableHostVerification();
    }

    public final void disablePeerVerification() {
        super.internal_disablePeerVerification();
    }

    @Override // com.good.gd.apachehttp.impl.client.GDDefaultHttpClient
    public final void kerberosAllowDelegation(boolean z) {
        super.kerberosAllowDelegation(z);
    }
}
