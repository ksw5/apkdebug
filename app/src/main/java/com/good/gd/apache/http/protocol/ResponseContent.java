package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.ProtocolVersion;
import java.io.IOException;

/* loaded from: classes.dex */
public class ResponseContent implements HttpResponseInterceptor {
    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse != null) {
            if (!httpResponse.containsHeader(HTTP.TRANSFER_ENCODING)) {
                if (!httpResponse.containsHeader(HTTP.CONTENT_LEN)) {
                    ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        long contentLength = entity.getContentLength();
                        if (entity.isChunked() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                            httpResponse.addHeader(HTTP.TRANSFER_ENCODING, HTTP.CHUNK_CODING);
                        } else if (contentLength >= 0) {
                            httpResponse.addHeader(HTTP.CONTENT_LEN, Long.toString(entity.getContentLength()));
                        }
                        if (entity.getContentType() != null && !httpResponse.containsHeader(HTTP.CONTENT_TYPE)) {
                            httpResponse.addHeader(entity.getContentType());
                        }
                        if (entity.getContentEncoding() == null || httpResponse.containsHeader(HTTP.CONTENT_ENCODING)) {
                            return;
                        }
                        httpResponse.addHeader(entity.getContentEncoding());
                        return;
                    }
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode == 204 || statusCode == 304 || statusCode == 205) {
                        return;
                    }
                    httpResponse.addHeader(HTTP.CONTENT_LEN, "0");
                    return;
                }
                throw new ProtocolException("Content-Length header already present");
            }
            throw new ProtocolException("Transfer-encoding header already present");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
