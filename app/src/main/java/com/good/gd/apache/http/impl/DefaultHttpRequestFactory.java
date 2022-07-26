package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpRequestFactory;
import com.good.gd.apache.http.MethodNotSupportedException;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.client.methods.HttpDelete;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.apache.http.client.methods.HttpHead;
import com.good.gd.apache.http.client.methods.HttpOptions;
import com.good.gd.apache.http.client.methods.HttpPost;
import com.good.gd.apache.http.client.methods.HttpPut;
import com.good.gd.apache.http.client.methods.HttpTrace;
import com.good.gd.apache.http.message.BasicHttpEntityEnclosingRequest;
import com.good.gd.apache.http.message.BasicHttpRequest;

/* loaded from: classes.dex */
public class DefaultHttpRequestFactory implements HttpRequestFactory {
    private static final String[] RFC2616_COMMON_METHODS = {HttpGet.METHOD_NAME};
    private static final String[] RFC2616_ENTITY_ENC_METHODS = {HttpPost.METHOD_NAME, HttpPut.METHOD_NAME};
    private static final String[] RFC2616_SPECIAL_METHODS = {HttpHead.METHOD_NAME, HttpOptions.METHOD_NAME, HttpDelete.METHOD_NAME, HttpTrace.METHOD_NAME};

    private static boolean isOneOf(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.good.gd.apache.http.HttpRequestFactory
    public HttpRequest newHttpRequest(RequestLine requestLine) throws MethodNotSupportedException {
        if (requestLine != null) {
            String method = requestLine.getMethod();
            if (isOneOf(RFC2616_COMMON_METHODS, method)) {
                return new BasicHttpRequest(requestLine);
            }
            if (isOneOf(RFC2616_ENTITY_ENC_METHODS, method)) {
                return new BasicHttpEntityEnclosingRequest(requestLine);
            }
            if (isOneOf(RFC2616_SPECIAL_METHODS, method)) {
                return new BasicHttpRequest(requestLine);
            }
            throw new MethodNotSupportedException(method + " method not supported");
        }
        throw new IllegalArgumentException("Request line may not be null");
    }

    @Override // com.good.gd.apache.http.HttpRequestFactory
    public HttpRequest newHttpRequest(String str, String str2) throws MethodNotSupportedException {
        if (isOneOf(RFC2616_COMMON_METHODS, str)) {
            return new BasicHttpRequest(str, str2);
        }
        if (isOneOf(RFC2616_ENTITY_ENC_METHODS, str)) {
            return new BasicHttpEntityEnclosingRequest(str, str2);
        }
        if (isOneOf(RFC2616_SPECIAL_METHODS, str)) {
            return new BasicHttpRequest(str, str2);
        }
        throw new MethodNotSupportedException(str + " method not supported");
    }
}
