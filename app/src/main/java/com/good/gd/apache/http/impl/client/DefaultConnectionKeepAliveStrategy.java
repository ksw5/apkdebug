package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy;
import com.good.gd.apache.http.message.BasicHeaderElementIterator;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.protocol.HttpContext;

/* loaded from: classes.dex */
public class DefaultConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {
    @Override // com.good.gd.apache.http.conn.ConnectionKeepAliveStrategy
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        if (httpResponse != null) {
            BasicHeaderElementIterator basicHeaderElementIterator = new BasicHeaderElementIterator(httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (basicHeaderElementIterator.hasNext()) {
                HeaderElement nextElement = basicHeaderElementIterator.nextElement();
                String name = nextElement.getName();
                String value = nextElement.getValue();
                if (value != null && name.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return -1L;
        }
        throw new IllegalArgumentException("HTTP response may not be null");
    }
}
