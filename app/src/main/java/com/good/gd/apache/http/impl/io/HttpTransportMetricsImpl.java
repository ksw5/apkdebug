package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.HttpTransportMetrics;

/* loaded from: classes.dex */
public class HttpTransportMetricsImpl implements HttpTransportMetrics {
    private long bytesTransferred = 0;

    @Override // com.good.gd.apache.http.io.HttpTransportMetrics
    public long getBytesTransferred() {
        return this.bytesTransferred;
    }

    public void incrementBytesTransferred(long j) {
        this.bytesTransferred += j;
    }

    @Override // com.good.gd.apache.http.io.HttpTransportMetrics
    public void reset() {
        this.bytesTransferred = 0L;
    }

    public void setBytesTransferred(long j) {
        this.bytesTransferred = j;
    }
}
