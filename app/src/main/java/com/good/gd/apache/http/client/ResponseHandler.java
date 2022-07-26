package com.good.gd.apache.http.client;

import com.good.gd.apache.http.HttpResponse;
import java.io.IOException;

/* loaded from: classes.dex */
public interface ResponseHandler<T> {
    T handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException;
}
