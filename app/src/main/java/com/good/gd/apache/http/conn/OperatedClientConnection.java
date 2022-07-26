package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.HttpClientConnection;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpInetConnection;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes.dex */
public interface OperatedClientConnection extends HttpClientConnection, HttpInetConnection {
    Socket getSocket();

    HttpHost getTargetHost();

    boolean isSecure();

    void openCompleted(boolean z, HttpParams httpParams) throws IOException;

    void opening(Socket socket, HttpHost httpHost) throws IOException;

    void update(Socket socket, HttpHost httpHost, boolean z, HttpParams httpParams) throws IOException;
}
