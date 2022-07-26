package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.ProtocolVersion;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestContent implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (!(httpRequest instanceof HttpEntityEnclosingRequest)) {
                return;
            }
            if (!httpRequest.containsHeader(HTTP.TRANSFER_ENCODING)) {
                if (!httpRequest.containsHeader(HTTP.CONTENT_LEN)) {
                    ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
                    HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
                    if (entity == null) {
                        httpRequest.addHeader(HTTP.CONTENT_LEN, "0");
                        return;
                    }
                    if (!entity.isChunked() && entity.getContentLength() >= 0) {
                        httpRequest.addHeader(HTTP.CONTENT_LEN, Long.toString(entity.getContentLength()));
                    } else if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                        httpRequest.addHeader(HTTP.TRANSFER_ENCODING, HTTP.CHUNK_CODING);
                    } else {
                        throw new ProtocolException("Chunked transfer encoding not allowed for " + protocolVersion);
                    }
                    if (entity.getContentType() != null && !httpRequest.containsHeader(HTTP.CONTENT_TYPE)) {
                        httpRequest.addHeader(entity.getContentType());
                    }
                    if (entity.getContentEncoding() == null || httpRequest.containsHeader(HTTP.CONTENT_ENCODING)) {
                        return;
                    }
                    httpRequest.addHeader(entity.getContentEncoding());
                    return;
                }
                throw new ProtocolException("Content-Length header already present");
            }
            throw new ProtocolException("Transfer-encoding header already present");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
