package com.good.gd.apache.http.protocol;

import com.good.gd.apache.http.HttpResponseInterceptor;
import java.util.List;

/* loaded from: classes.dex */
public interface HttpResponseInterceptorList {
    void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor);

    void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int i);

    void clearResponseInterceptors();

    HttpResponseInterceptor getResponseInterceptor(int i);

    int getResponseInterceptorCount();

    void removeResponseInterceptorByClass(Class cls);

    void setInterceptors(List list);
}
