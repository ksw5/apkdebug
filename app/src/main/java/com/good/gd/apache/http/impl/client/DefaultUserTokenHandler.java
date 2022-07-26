package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthState;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.client.UserTokenHandler;
import com.good.gd.apache.http.client.protocol.ClientContext;
import com.good.gd.apache.http.conn.ManagedClientConnection;
import com.good.gd.apache.http.protocol.ExecutionContext;
import com.good.gd.apache.http.protocol.HttpContext;
import java.security.Principal;
import javax.net.ssl.SSLSession;

/* loaded from: classes.dex */
public class DefaultUserTokenHandler implements UserTokenHandler {
    private static Principal getAuthPrincipal(AuthState authState) {
        Credentials credentials;
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete() || !authScheme.isConnectionBased() || (credentials = authState.getCredentials()) == null) {
            return null;
        }
        return credentials.getUserPrincipal();
    }

    @Override // com.good.gd.apache.http.client.UserTokenHandler
    public Object getUserToken(HttpContext httpContext) {
        Principal principal;
        SSLSession sSLSession;
        AuthState authState = (AuthState) httpContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
        if (authState == null) {
            principal = null;
        } else {
            principal = getAuthPrincipal(authState);
            if (principal == null) {
                principal = getAuthPrincipal((AuthState) httpContext.getAttribute(ClientContext.PROXY_AUTH_STATE));
            }
        }
        if (principal == null) {
            ManagedClientConnection managedClientConnection = (ManagedClientConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
            return (!managedClientConnection.isOpen() || (sSLSession = managedClientConnection.getSSLSession()) == null) ? principal : sSLSession.getLocalPrincipal();
        }
        return principal;
    }
}
