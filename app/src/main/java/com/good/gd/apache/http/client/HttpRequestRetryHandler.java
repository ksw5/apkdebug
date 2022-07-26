package com.good.gd.apache.http.client;

import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public interface HttpRequestRetryHandler {
    boolean retryRequest(IOException iOException, int i, HttpContext httpContext);
}
