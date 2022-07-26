package com.good.gd.apache.http.client.protocol;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.client.params.ClientPNames;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.util.Collection;

/* loaded from: classes.dex */
public class RequestDefaultHeaders implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            Collection<Header> collection = (Collection) httpRequest.getParams().getParameter(ClientPNames.DEFAULT_HEADERS);
            if (collection == null) {
                return;
            }
            for (Header header : collection) {
                httpRequest.addHeader(header);
            }
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
