package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolVersion;
import java.io.IOException;

/* loaded from: classes.dex */
public class ResponseConnControl implements HttpResponseInterceptor {
    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Header firstHeader;
        if (httpResponse != null) {
            if (httpContext != null) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode != 400 && statusCode != 408 && statusCode != 411 && statusCode != 413 && statusCode != 414 && statusCode != 503 && statusCode != 501) {
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
                        if (entity.getContentLength() < 0 && (!entity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
                            httpResponse.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
                            return;
                        }
                    }
                    HttpRequest httpRequest = (HttpRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
                    if (httpRequest == null || (firstHeader = httpRequest.getFirstHeader(HTTP.CONN_DIRECTIVE)) == null) {
                        return;
                    }
                    httpResponse.setHeader(HTTP.CONN_DIRECTIVE, firstHeader.getValue());
                    return;
                }
                httpResponse.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
                return;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
