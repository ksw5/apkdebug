package com.good.gd.apache.http;

import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public interface HttpResponseFactory {
    HttpResponse newHttpResponse(ProtocolVersion protocolVersion, int i, HttpContext httpContext);

    HttpResponse newHttpResponse(StatusLine statusLine, HttpContext httpContext);
}
