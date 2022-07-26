package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.params.HttpProtocolParams;
import java.io.IOException;

/* loaded from: classes.dex */
public class RequestExpectContinue implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        HttpEntity entity;
        if (httpRequest != null) {
            if (!(httpRequest instanceof HttpEntityEnclosingRequest) || (entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity()) == null || entity.getContentLength() == 0) {
                return;
            }
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            if (!HttpProtocolParams.useExpectContinue(httpRequest.getParams()) || protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                return;
            }
            httpRequest.addHeader(HTTP.EXPECT_DIRECTIVE, HTTP.EXPECT_CONTINUE);
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
