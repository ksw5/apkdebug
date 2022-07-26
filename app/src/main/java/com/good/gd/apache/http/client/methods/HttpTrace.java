package com.good.gd.apache.http.client.methods;

import java.net.URI;

/* loaded from: classes.dex */
public class HttpTrace extends HttpRequestBase {
    public static final String METHOD_NAME = "TRACE";

    public HttpTrace() {
    }

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase, com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpTrace(URI uri) {
        setURI(uri);
    }

    public HttpTrace(String str) {
        setURI(URI.create(str));
    }
}
