package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import com.good.gd.apache.http.params.CoreProtocolPNames;
import java.io.IOException;

/* loaded from: classes.dex */
public class ResponseServer implements HttpResponseInterceptor {
    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        String str;
        if (httpResponse != null) {
            if (httpResponse.containsHeader(HTTP.SERVER_HEADER) || (str = (String) httpResponse.getParams().getParameter(CoreProtocolPNames.ORIGIN_SERVER)) == null) {
                return;
            }
            httpResponse.addHeader(HTTP.SERVER_HEADER, str);
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
