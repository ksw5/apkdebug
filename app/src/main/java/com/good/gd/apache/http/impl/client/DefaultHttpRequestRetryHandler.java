package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.NoHttpResponseException;
import com.good.gd.apache.http.client.HttpRequestRetryHandler;
import com.good.gd.apache.http.protocol.ExecutionContext;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;

/* loaded from: classes.dex */
public class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler {
    private final boolean requestSentRetryEnabled;
    private final int retryCount;

    public DefaultHttpRequestRetryHandler(int i, boolean z) {
        this.retryCount = i;
        this.requestSentRetryEnabled = z;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public boolean isRequestSentRetryEnabled() {
        return this.requestSentRetryEnabled;
    }

    @Override // com.good.gd.apache.http.client.HttpRequestRetryHandler
    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        if (iOException != null) {
            if (httpContext != null) {
                if (i > this.retryCount) {
                    return false;
                }
                if (iOException instanceof NoHttpResponseException) {
                    return true;
                }
                if ((iOException instanceof InterruptedIOException) || (iOException instanceof UnknownHostException) || (iOException instanceof SSLHandshakeException)) {
                    return false;
                }
                Boolean bool = (Boolean) httpContext.getAttribute(ExecutionContext.HTTP_REQ_SENT);
                return !(bool != null && bool.booleanValue()) || this.requestSentRetryEnabled;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("Exception parameter may not be null");
    }

    public DefaultHttpRequestRetryHandler() {
        this(3, false);
    }
}
