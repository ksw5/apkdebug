package com.good.gd.apache.http.client.methods;

import java.net.URI;

/* loaded from: classes.dex */
public class HttpPatch extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "PATCH";

    public HttpPatch() {
    }

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase, com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpPatch(URI uri) {
        setURI(uri);
    }

    public HttpPatch(String str) {
        setURI(URI.create(str));
    }
}
