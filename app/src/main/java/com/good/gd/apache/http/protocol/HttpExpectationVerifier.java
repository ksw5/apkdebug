package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;

/* loaded from: classes.dex */
public interface HttpExpectationVerifier {
    void verify(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException;
}
