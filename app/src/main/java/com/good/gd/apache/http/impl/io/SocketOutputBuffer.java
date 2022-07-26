package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes.dex */
public class SocketOutputBuffer extends AbstractSessionOutputBuffer {
    public SocketOutputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        if (socket != null) {
            init(socket.getOutputStream(), 8192, httpParams);
            return;
        }
        throw new IllegalArgumentException("Socket may not be null");
    }
}
