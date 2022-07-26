package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpRequestInterceptor;
import java.util.List;

/* loaded from: classes.dex */
public interface HttpRequestInterceptorList {
    void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor);

    void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int i);

    void clearRequestInterceptors();

    HttpRequestInterceptor getRequestInterceptor(int i);

    int getRequestInterceptorCount();

    void removeRequestInterceptorByClass(Class cls);

    void setInterceptors(List list);
}
