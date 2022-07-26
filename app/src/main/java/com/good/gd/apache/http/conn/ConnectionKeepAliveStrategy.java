package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public interface ConnectionKeepAliveStrategy {
    long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext);
}
