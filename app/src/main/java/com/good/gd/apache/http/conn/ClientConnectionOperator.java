package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.InetAddress;

/* loaded from: classes.dex */
public interface ClientConnectionOperator {
    OperatedClientConnection createConnection();

    void openConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, InetAddress inetAddress, HttpContext httpContext, HttpParams httpParams) throws IOException;

    void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException;
}
