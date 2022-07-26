package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes.dex */
public class DefaultHttpClientConnection extends SocketHttpClientConnection {
    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection
    public void bind(Socket socket, HttpParams httpParams) throws IOException {
        if (socket != null) {
            if (httpParams != null) {
                assertNotOpen();
                socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
                socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
                int linger = HttpConnectionParams.getLinger(httpParams);
                if (linger >= 0) {
                    socket.setSoLinger(linger > 0, linger);
                }
                super.bind(socket, httpParams);
                return;
            }
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        throw new IllegalArgumentException("Socket may not be null");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        if (isOpen()) {
            stringBuffer.append(getRemotePort());
        } else {
            stringBuffer.append("closed");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
