package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.params.HttpProtocolParams;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestUserAgent implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        String userAgent;
        if (httpRequest != null) {
            if (httpRequest.containsHeader(HTTP.USER_AGENT) || (userAgent = HttpProtocolParams.getUserAgent(httpRequest.getParams())) == null) {
                return;
            }
            httpRequest.addHeader(HTTP.USER_AGENT, userAgent);
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
