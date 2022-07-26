package com.good.gd.apache.http;

import java.io.IOException;

/* loaded from: classes.dex */
public interface HttpConnection {
    void close() throws IOException;

    HttpConnectionMetrics getMetrics();

    int getSocketTimeout();

    boolean isOpen();

    boolean isStale();

    void setSocketTimeout(int i);

    void shutdown() throws IOException;
}
