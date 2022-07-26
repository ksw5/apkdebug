package com.good.gd.apache.http.client.methods;

import com.good.gd.apache.http.HttpRequest;
import java.net.URI;

/* loaded from: classes.dex */
public interface HttpUriRequest extends HttpRequest {
    void abort() throws UnsupportedOperationException;

    String getMethod();

    URI getURI();

    boolean isAborted();
}
