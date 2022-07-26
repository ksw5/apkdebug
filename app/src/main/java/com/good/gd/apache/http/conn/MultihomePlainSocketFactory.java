package com.good.gd.apache.http.conn;

import com.good.gd.apache.http.conn.scheme.PlainSocketFactory;
import com.good.gd.apache.http.conn.scheme.SocketFactory;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class MultihomePlainSocketFactory implements SocketFactory {
    private static final MultihomePlainSocketFactory DEFAULT_FACTORY = new MultihomePlainSocketFactory();

    private MultihomePlainSocketFactory() {
    }

    public static MultihomePlainSocketFactory getSocketFactory() {
        return DEFAULT_FACTORY;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
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
                InetAddress[] allByName = InetAddress.getAllByName(str);
                ArrayList arrayList = new ArrayList(allByName.length);
                arrayList.addAll(Arrays.asList(allByName));
                Collections.shuffle(arrayList);
                IOException iOException = null;
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    try {
                        socket.connect(new InetSocketAddress((InetAddress) it.next(), i), connectionTimeout);
                        break;
                    } catch (SocketTimeoutException e) {
                        throw e;
                    } catch (IOException e2) {
                        iOException = e2;
                        socket = new Socket();
                    }
                }
                if (iOException != null) {
                    throw iOException;
                }
                return socket;
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
}
