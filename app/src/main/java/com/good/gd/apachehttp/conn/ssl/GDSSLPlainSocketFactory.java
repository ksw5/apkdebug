package com.good.gd.apachehttp.conn.ssl;

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

/* loaded from: classes.dex */
public class GDSSLPlainSocketFactory implements SocketFactory {
    private static String TAG = "GDSSLPlainSocketFactory";
    private final HostNameResolver nameResolver = null;

    public GDSSLPlainSocketFactory() {
        GDLog.DBGPRINTF(16, TAG + "()\n");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
        InetSocketAddress createUnresolved;
        GDLog.DBGPRINTF(16, TAG + "::connectSocket(host: " + str + ") IN\n");
        if (str != null) {
            if (httpParams != null) {
                if (socket instanceof GDSocket) {
                    if (socket == null) {
                        socket = createSocket();
                    }
                    GDSocket gDSocket = (GDSocket) socket;
                    if (inetAddress != null || i2 > 0) {
                        if (i2 < 0) {
                            i2 = 0;
                        }
                        gDSocket.bind(new InetSocketAddress(inetAddress, i2));
                    }
                    int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
                    int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
                    if (this.nameResolver != null) {
                        createUnresolved = new InetSocketAddress(this.nameResolver.resolve(str), i);
                    } else {
                        createUnresolved = InetSocketAddress.createUnresolved(str, i);
                    }
                    gDSocket.connect(createUnresolved, connectionTimeout);
                    gDSocket.setSoTimeout(soTimeout);
                    GDLog.DBGPRINTF(16, TAG + "::connectSocket() OUT: sslsock=" + gDSocket + "\n");
                    return gDSocket;
                }
                throw new IllegalArgumentException("Socket not created by this factory.");
            }
            throw new IllegalArgumentException("Parameters may not be null.");
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        GDSocket gDSocket;
        try {
            gDSocket = new GDSocket(true);
        } catch (Exception e) {
            GDLog.DBGPRINTF(16, TAG + "::createSocket() : " + e + "\n");
            gDSocket = null;
        }
        GDLog.DBGPRINTF(16, TAG + "::createSocket() -  " + gDSocket + "\n");
        return gDSocket;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket != null) {
            if (socket instanceof GDSocket) {
                if (socket.isClosed()) {
                    throw new IllegalArgumentException("Socket is closed.");
                }
                return true;
            }
            throw new IllegalArgumentException("Socket not created by this factory.");
        }
        throw new IllegalArgumentException("Socket may not be null.");
    }
}
