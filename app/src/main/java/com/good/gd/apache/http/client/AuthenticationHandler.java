package com.good.gd.apache.http.client;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthenticationException;
import com.good.gd.apache.http.auth.MalformedChallengeException;
import com.good.gd.apache.http.protocol.HttpContext;
import java.util.Map;

/* loaded from: classes.dex */
public interface AuthenticationHandler {
    Map<String, Header> getChallenges(HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException;

    boolean isAuthenticationRequested(HttpResponse httpResponse, HttpContext httpContext);

    AuthScheme selectScheme(Map<String, Header> map, HttpResponse httpResponse, HttpContext httpContext) throws AuthenticationException;
}
