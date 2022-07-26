package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.protocol.HttpContext;
import java.util.Map;

/* loaded from: classes.dex */
public class DefaultTargetAuthenticationHandler extends AbstractAuthenticationHandler {
    @Override // com.good.gd.apache.http.client.AuthenticationHandler
    public Map<String, Header> getChallenges(HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        if (httpResponse != null) {
            return parseChallenges(httpResponse.getHeaders(AUTH.WWW_AUTH));
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }

    @Override // com.good.gd.apache.http.client.AuthenticationHandler
    public boolean isAuthenticationRequested(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse != null) {
            return httpResponse.getStatusLine().getStatusCode() == 401;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
