package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestDate implements HttpRequestInterceptor {
    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (!(httpRequest instanceof HttpEntityEnclosingRequest) || httpRequest.containsHeader(HTTP.DATE_HEADER)) {
                return;
            }
            httpRequest.setHeader(HTTP.DATE_HEADER, DATE_GENERATOR.getCurrentDate());
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null.");
    }
}
