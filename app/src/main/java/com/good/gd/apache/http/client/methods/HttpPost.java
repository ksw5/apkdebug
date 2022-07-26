package com.good.gd.apache.http.client.methods;

import java.net.URI;

/* loaded from: classes.dex */
public class HttpPost extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "POST";

    public HttpPost() {
    }

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase, com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpPost(URI uri) {
        setURI(uri);
    }

    public HttpPost(String str) {
        setURI(URI.create(str));
    }
}
