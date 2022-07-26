package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpConnectionMetrics;
import com.good.gd.apache.http.io.HttpTransportMetrics;
import java.util.HashMap;

/* loaded from: classes.dex */
public class HttpConnectionMetricsImpl implements HttpConnectionMetrics {
    public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
    public static final String REQUEST_COUNT = "http.request-count";
    public static final String RESPONSE_COUNT = "http.response-count";
    public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
    private final HttpTransportMetrics inTransportMetric;
    private HashMap metricsCache;
    private final HttpTransportMetrics outTransportMetric;
    private long requestCount = 0;
    private long responseCount = 0;

    public HttpConnectionMetricsImpl(HttpTransportMetrics httpTransportMetrics, HttpTransportMetrics httpTransportMetrics2) {
        this.inTransportMetric = httpTransportMetrics;
        this.outTransportMetric = httpTransportMetrics2;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public Object getMetric(String str) {
        Object obj;
        HashMap hashMap = this.metricsCache;
        if (hashMap == null) {
            obj = null;
        } else {
            obj = hashMap.get(str);
        }
        if (obj == null) {
            if (REQUEST_COUNT.equals(str)) {
                return new Long(this.requestCount);
            }
            if (RESPONSE_COUNT.equals(str)) {
                return new Long(this.responseCount);
            }
            if (RECEIVED_BYTES_COUNT.equals(str)) {
                if (this.inTransportMetric == null) {
                    return null;
                }
                return new Long(this.inTransportMetric.getBytesTransferred());
            } else if (!SENT_BYTES_COUNT.equals(str)) {
                return obj;
            } else {
                if (this.outTransportMetric == null) {
                    return null;
                }
                return new Long(this.outTransportMetric.getBytesTransferred());
            }
        }
        return obj;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public long getReceivedBytesCount() {
        HttpTransportMetrics httpTransportMetrics = this.inTransportMetric;
        if (httpTransportMetrics != null) {
            return httpTransportMetrics.getBytesTransferred();
        }
        return -1L;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public long getRequestCount() {
        return this.requestCount;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public long getResponseCount() {
        return this.responseCount;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public long getSentBytesCount() {
        HttpTransportMetrics httpTransportMetrics = this.outTransportMetric;
        if (httpTransportMetrics != null) {
            return httpTransportMetrics.getBytesTransferred();
        }
        return -1L;
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }

    public void incrementResponseCount() {
        this.responseCount++;
    }

    @Override // com.good.gd.apache.http.HttpConnectionMetrics
    public void reset() {
        HttpTransportMetrics httpTransportMetrics = this.outTransportMetric;
        if (httpTransportMetrics != null) {
            httpTransportMetrics.reset();
        }
        HttpTransportMetrics httpTransportMetrics2 = this.inTransportMetric;
        if (httpTransportMetrics2 != null) {
            httpTransportMetrics2.reset();
        }
        this.requestCount = 0L;
        this.responseCount = 0L;
        this.metricsCache = null;
    }

    public void setMetric(String str, Object obj) {
        if (this.metricsCache == null) {
            this.metricsCache = new HashMap();
        }
        this.metricsCache.put(str, obj);
    }
}
