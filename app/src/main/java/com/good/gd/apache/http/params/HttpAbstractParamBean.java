package com.good.gd.apache.http.params;

/* loaded from: classes.dex */
public abstract class HttpAbstractParamBean {
    protected final HttpParams params;

    public HttpAbstractParamBean(HttpParams httpParams) {
        if (httpParams != null) {
            this.params = httpParams;
            return;
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }
}
