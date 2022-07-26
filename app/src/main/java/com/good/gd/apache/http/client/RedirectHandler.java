package com.good.gd.apache.http.client;

import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.protocol.HttpContext;
import java.net.URI;

/* loaded from: classes.dex */
public interface RedirectHandler {
    URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException;

    boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext);
}
