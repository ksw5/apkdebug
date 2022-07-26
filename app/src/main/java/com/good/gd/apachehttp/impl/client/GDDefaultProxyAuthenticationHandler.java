package com.good.gd.apachehttp.impl.client;

import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthState;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.Credentials;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.client.protocol.ClientContext;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apachehttp.impl.auth.NegotiateScheme;
import com.good.gd.ndkproxy.GDLog;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class GDDefaultProxyAuthenticationHandler extends GDAbstractFipsAuthenticationHandler {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.client.AbstractAuthenticationHandler
    public List<String> getAuthPreferences() {
        return GDAbstractFipsAuthenticationHandler.DEFAULT_SCHEME_PRIORITY;
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultTargetAuthenticationHandler, com.good.gd.apache.http.client.AuthenticationHandler
    public Map<String, Header> getChallenges(HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        if (httpResponse != null) {
            return parseChallenges(httpResponse.getHeaders(AUTH.PROXY_AUTH));
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    @Override // com.good.gd.apache.http.impl.client.DefaultTargetAuthenticationHandler, com.good.gd.apache.http.client.AuthenticationHandler
    public boolean isAuthenticationRequested(HttpResponse httpResponse, HttpContext httpContext) {
        Header[] headers;
        CharArrayBuffer charArrayBuffer;
        int i;
        if (httpResponse != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            boolean z = statusCode == 407;
            if (statusCode == 200) {
                GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested() - passing down auth response: " + httpResponse.toString() + "\n");
                for (Header header : httpResponse.getHeaders(AUTH.PROXY_AUTH_RESP)) {
                    if (header instanceof FormattedHeader) {
                        FormattedHeader formattedHeader = (FormattedHeader) header;
                        charArrayBuffer = formattedHeader.getBuffer();
                        i = formattedHeader.getValuePos();
                    } else {
                        String value = header.getValue();
                        if (value == null) {
                            GDLog.DBGPRINTF(16, "Header value is null\n");
                        }
                        charArrayBuffer = new CharArrayBuffer(value.length());
                        charArrayBuffer.append(value);
                        i = 0;
                    }
                    while (i < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(i))) {
                        i++;
                    }
                    int i2 = i;
                    while (i2 < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(i2))) {
                        i2++;
                    }
                    if (charArrayBuffer.substring(i, i2).equals("Negotiate")) {
                        AuthState authState = (AuthState) httpContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
                        if (authState == null) {
                            GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): auth state not available\n");
                            return false;
                        }
                        AuthScheme authScheme = authState.getAuthScheme();
                        if (authScheme == null) {
                            GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): auth scheme not available\n");
                            return false;
                        }
                        NegotiateScheme negotiateScheme = (NegotiateScheme) authScheme;
                        Credentials credentials = authState.getCredentials();
                        if (credentials == null) {
                            GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): User credentials not available\n");
                            return false;
                        } else if (authState.getAuthScope() == null && authScheme.isConnectionBased()) {
                            return true;
                        } else {
                            try {
                                if (negotiateScheme.isComplete()) {
                                    return true;
                                }
                                negotiateScheme.parseChallenge(charArrayBuffer, i2, charArrayBuffer.length());
                                boolean authenticateReponse = negotiateScheme.authenticateReponse(credentials, null);
                                GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): Authentication context has been established.\n");
                                return !authenticateReponse;
                            } catch (AuthenticationException e) {
                                GDLog.DBGPRINTF(12, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): Authentication error: " + e.getMessage() + "\n");
                                return true;
                            } catch (MalformedChallengeException e2) {
                                GDLog.DBGPRINTF(12, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested(): Authentication error: " + e2.getMessage() + "\n");
                                return true;
                            }
                        }
                    }
                }
            }
            GDLog.DBGPRINTF(16, "GDDefaultProxyAuthenticationHandler::isAuthenticationRequested() returning " + z + "\n");
            return z;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
