package com.good.gd.apache.http.client.methods;

import java.net.URI;

/* loaded from: classes.dex */
public class HttpHead extends HttpRequestBase {
    public static final String METHOD_NAME = "HEAD";

    public HttpHead() {
    }

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase, com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpHead(URI uri) {
        setURI(uri);
    }

    public HttpHead(String str) {
        setURI(URI.create(str));
    }
}
