package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestConnControl implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (httpRequest.containsHeader(HTTP.CONN_DIRECTIVE)) {
                return;
            }
            httpRequest.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
