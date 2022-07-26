package com.good.gd.apache.http.client;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public interface RequestDirector {
    HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException;
}
