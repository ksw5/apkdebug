package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpHost;
import java.net.ConnectException;

/* loaded from: classes.dex */
public class HttpHostConnectException extends ConnectException {
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost host;

    public HttpHostConnectException(HttpHost httpHost, ConnectException connectException) {
        super("Connection to " + httpHost + " refused");
        this.host = httpHost;
        initCause(connectException);
    }

    public HttpHost getHost() {
        return this.host;
    }
}
