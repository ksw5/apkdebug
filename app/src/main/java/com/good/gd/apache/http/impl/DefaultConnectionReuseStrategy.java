package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpConnection;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.TokenIterator;
import com.good.gd.apache.http.message.BasicTokenIterator;
import com.good.gd.apache.http.protocol.ExecutionContext;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {
    protected TokenIterator createTokenIterator(HeaderIterator headerIterator) {
        return new BasicTokenIterator(headerIterator);
    }

    @Override // com.good.gd.apache.http.ConnectionReuseStrategy
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse != null) {
            if (httpContext != null) {
                HttpConnection httpConnection = (HttpConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
                if (httpConnection != null && !httpConnection.isOpen()) {
                    return false;
                }
                HttpEntity entity = httpResponse.getEntity();
                ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
                if (entity != null && entity.getContentLength() < 0 && (!entity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
                    return false;
                }
                HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_DIRECTIVE);
                if (!headerIterator.hasNext()) {
                    headerIterator = httpResponse.headerIterator("Proxy-Connection");
                }
                if (headerIterator.hasNext()) {
                    try {
                        TokenIterator createTokenIterator = createTokenIterator(headerIterator);
                        boolean z = false;
                        while (createTokenIterator.hasNext()) {
                            String nextToken = createTokenIterator.nextToken();
                            if (HTTP.CONN_CLOSE.equalsIgnoreCase(nextToken)) {
                                return false;
                            }
                            if (HTTP.CONN_KEEP_ALIVE.equalsIgnoreCase(nextToken)) {
                                z = true;
                            }
                        }
                        if (z) {
                            return true;
                        }
                    } catch (ParseException e) {
                        return false;
                    }
                }
                return !protocolVersion.lessEquals(HttpVersion.HTTP_1_0);
            }
            throw new IllegalArgumentException("HTTP context may not be null.");
        }
        throw new IllegalArgumentException("HTTP response may not be null.");
    }
}
