package com.good.gd.apache.http.client;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.client.methods.HttpUriRequest;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public interface HttpClient {
    HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException;

    HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException;

    HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException;

    HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException;

    <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException;

    <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException;

    <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException;

    <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException;

    ClientConnectionManager getConnectionManager();

    HttpParams getParams();
}
