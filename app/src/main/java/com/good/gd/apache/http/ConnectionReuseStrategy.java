package com.good.gd.apache.http;

import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public interface ConnectionReuseStrategy {
    boolean keepAlive(HttpResponse httpResponse, HttpContext httpContext);
}
