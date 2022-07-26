package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.ConnectionReuseStrategy;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public class NoConnectionReuseStrategy implements ConnectionReuseStrategy {
    @Override // com.good.gd.apache.http.ConnectionReuseStrategy
    public boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse != null) {
            if (httpContext == null) {
                throw new IllegalArgumentException("HTTP context may not be null");
            }
            return false;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
