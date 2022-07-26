package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpConnection;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpInetConnection;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestInterceptor;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolException;
import java.io.IOException;
import java.net.InetAddress;

/* loaded from: classes.dex */
public class RequestTargetHost implements HttpRequestInterceptor {
    @Override // com.good.gd.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest != null) {
            if (httpContext != null) {
                if (httpRequest.containsHeader(HTTP.TARGET_HOST)) {
                    return;
                }
                HttpHost httpHost = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (httpHost == null) {
                    HttpConnection httpConnection = (HttpConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
                    if (httpConnection instanceof HttpInetConnection) {
                        HttpInetConnection httpInetConnection = (HttpInetConnection) httpConnection;
                        InetAddress remoteAddress = httpInetConnection.getRemoteAddress();
                        int remotePort = httpInetConnection.getRemotePort();
                        if (remoteAddress != null) {
                            httpHost = new HttpHost(remoteAddress.getHostName(), remotePort);
                        }
                    }
                    if (httpHost == null) {
                        if (!httpRequest.getRequestLine().getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                            throw new ProtocolException("Target host missing");
                        }
                        return;
                    }
                }
                httpRequest.addHeader(HTTP.TARGET_HOST, httpHost.toHostString());
                return;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
