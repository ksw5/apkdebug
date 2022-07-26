package com.good.gd.apachehttp.conn.scheme;

import com.good.gd.apache.http.conn.ConnectTimeoutException;
import com.good.gd.apache.http.conn.scheme.HostNameResolver;
import com.good.gd.apache.http.conn.scheme.SocketFactory;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.net.GDSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/* loaded from: classes.dex */
public final class GDPlainSocketFactory implements SocketFactory {
    private static final GDPlainSocketFactory DEFAULT_FACTORY = new GDPlainSocketFactory();
    private static String TAG = GDPlainSocketFactory.class.getSimpleName();
    private final HostNameResolver nameResolver;

    public GDPlainSocketFactory(HostNameResolver hostNameResolver) {
        this.nameResolver = hostNameResolver;
    }

    public static GDPlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
        InetSocketAddress createUnresolved;
        GDLog.DBGPRINTF(16, TAG + "::connectSocket( " + socket + " ) IN\n");
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
                    createUnresolved = new InetSocketAddress(this.nameResolver.resolve(str), i);
                } else {
                    createUnresolved = InetSocketAddress.createUnresolved(str, i);
                }
                try {
                    socket.connect(createUnresolved, connectionTimeout);
                    GDLog.DBGPRINTF(16, TAG + "::connectSocket( " + socket + " ) OUT\n");
                    return socket;
                } catch (SocketTimeoutException e) {
                    String str2 = str + ":" + i;
                    if (this.nameResolver != null) {
                        str2 = createUnresolved.toString();
                    }
                    throw new ConnectTimeoutException("GDPlainSocketFactory::Connect to " + str2 + " timed out.");
                }
            }
            throw new IllegalArgumentException("GDPlainSocketFactory::Parameters may not be null.");
        }
        throw new IllegalArgumentException("GDPlainSocketFactory::Target host may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() {
        GDSocket gDSocket;
        GDLog.DBGPRINTF(16, TAG + "::createSocket() IN\n");
        try {
            gDSocket = new GDSocket();
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, TAG + "::createSocket() : " + e + "\n");
            gDSocket = null;
        }
        GDLog.DBGPRINTF(16, TAG + "::createSocket() OUT: " + gDSocket + "\n");
        return gDSocket;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int hashCode() {
        return GDPlainSocketFactory.class.hashCode();
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        GDLog.DBGPRINTF(16, TAG + "::isSecure( " + socket + ")\n");
        if (socket != null) {
            if (socket.getClass() == GDSocket.class) {
                if (socket.isClosed()) {
                    throw new IllegalArgumentException(TAG + "::Socket is closed.");
                }
                return false;
            }
            throw new IllegalArgumentException(TAG + "::Socket not created by this factory.");
        }
        throw new IllegalArgumentException(TAG + "::Socket may not be null.");
    }

    public GDPlainSocketFactory() {
        this(null);
    }
}
