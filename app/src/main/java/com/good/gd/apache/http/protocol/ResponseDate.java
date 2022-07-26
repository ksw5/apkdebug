package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import java.io.IOException;

/* loaded from: classes.dex */
public class ResponseDate implements HttpResponseInterceptor {
    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse != null) {
            if (httpResponse.getStatusLine().getStatusCode() < 200 || httpResponse.containsHeader(HTTP.DATE_HEADER)) {
                return;
            }
            httpResponse.setHeader(HTTP.DATE_HEADER, DATE_GENERATOR.getCurrentDate());
            return;
        }
        throw new IllegalArgumentException("HTTP response may not be null.");
    }
}
