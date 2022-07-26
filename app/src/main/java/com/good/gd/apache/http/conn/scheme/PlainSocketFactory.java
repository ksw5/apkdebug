package com.good.gd.apache.http.conn.scheme;

import com.good.gd.apache.http.conn.ConnectTimeoutException;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/* loaded from: classes.dex */
public final class PlainSocketFactory implements SocketFactory {
    private static final PlainSocketFactory DEFAULT_FACTORY = new PlainSocketFactory();
    private final HostNameResolver nameResolver;

    public PlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public static PlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (str != null) {
            if (httpParams != null) {
                if (socket == null) {
                    socket = createSocket();
                }
                if (inetAddress != null || i2 > 0) {
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    socket.bind(new InetSocketAddress(inetAddress, i2));
                }
                int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
                if (this.nameResolver != null) {
                    inetSocketAddress = new InetSocketAddress(this.nameResolver.resolve(str), i);
                } else {
                    inetSocketAddress = new InetSocketAddress(str, i);
                }
                try {
                    socket.connect(inetSocketAddress, connectionTimeout);
                    return socket;
                } catch (SocketTimeoutException e) {
                    throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
                }
            }
            throw new IllegalArgumentException("Parameters may not be null.");
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() {
        return new Socket();
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int hashCode() {
        return PlainSocketFactory.class.hashCode();
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public final boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket != null) {
            if (socket.getClass() == Socket.class) {
                if (socket.isClosed()) {
                    throw new IllegalArgumentException("Socket is closed.");
                }
                return false;
            }
            throw new IllegalArgumentException("Socket not created by this factory.");
        }
        throw new IllegalArgumentException("Socket may not be null.");
    }

    public PlainSocketFactory() {
        this(null);
    }
}
