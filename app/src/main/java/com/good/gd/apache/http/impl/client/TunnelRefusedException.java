package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpResponse;

/* loaded from: classes.dex */
public class TunnelRefusedException extends HttpException {
    private static final long serialVersionUID = -8646722842745617323L;
    private final HttpResponse response;

    public TunnelRefusedException(String str, HttpResponse httpResponse) {
        super(str);
        this.response = httpResponse;
    }

    public HttpResponse getResponse() {
        return this.response;
    }
}
