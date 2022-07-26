package com.good.gd.apache.http.impl;

import android.net.TrafficStats;
import com.good.gd.apache.http.HttpInetConnection;
import com.good.gd.apache.http.impl.io.SocketInputBuffer;
import com.good.gd.apache.http.impl.io.SocketOutputBuffer;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/* loaded from: classes.dex */
public class SocketHttpClientConnection extends AbstractHttpClientConnection implements HttpInetConnection {
    private volatile boolean open;
    private Socket socket = null;

    /* JADX INFO: Access modifiers changed from: protected */
    public void assertNotOpen() {
        if (!this.open) {
            return;
        }
        throw new IllegalStateException("Connection is already open");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.AbstractHttpClientConnection
    public void assertOpen() {
        if (this.open) {
            return;
        }
        throw new IllegalStateException("Connection is not open");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void bind(Socket socket, HttpParams httpParams) throws IOException {
        if (socket != null) {
            if (httpParams != null) {
                this.socket = socket;
                int socketBufferSize = HttpConnectionParams.getSocketBufferSize(httpParams);
                init(createSessionInputBuffer(socket, socketBufferSize, httpParams), createSessionOutputBuffer(socket, socketBufferSize, httpParams), httpParams);
                this.open = true;
                return;
            }
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        throw new IllegalArgumentException("Socket may not be null");
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void close() throws IOException {
        if (!this.open) {
            return;
        }
        this.open = false;
        doFlush();
        try {
            try {
                this.socket.shutdownOutput();
            } catch (IOException e) {
            }
            try {
                this.socket.shutdownInput();
            } catch (IOException e2) {
            }
        } catch (UnsupportedOperationException e3) {
        }
        TrafficStats.untagSocket(this.socket);
        this.socket.close();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        return new SocketInputBuffer(socket, i, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        return new SocketOutputBuffer(socket, i, httpParams);
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public InetAddress getLocalAddress() {
        Socket socket = this.socket;
        if (socket != null) {
            return socket.getLocalAddress();
        }
        return null;
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public int getLocalPort() {
        Socket socket = this.socket;
        if (socket != null) {
            return socket.getLocalPort();
        }
        return -1;
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public InetAddress getRemoteAddress() {
        Socket socket = this.socket;
        if (socket != null) {
            return socket.getInetAddress();
        }
        return null;
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public int getRemotePort() {
        Socket socket = this.socket;
        if (socket != null) {
            return socket.getPort();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Socket getSocket() {
        return this.socket;
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public int getSocketTimeout() {
        Socket socket = this.socket;
        if (socket != null) {
            try {
                return socket.getSoTimeout();
            } catch (SocketException e) {
            }
        }
        return -1;
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public boolean isOpen() {
        return this.open;
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void setSocketTimeout(int i) {
        assertOpen();
        Socket socket = this.socket;
        if (socket != null) {
            try {
                socket.setSoTimeout(i);
            } catch (SocketException e) {
            }
        }
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void shutdown() throws IOException {
        this.open = false;
        Socket socket = this.socket;
        if (socket != null) {
            socket.close();
        }
    }
}
