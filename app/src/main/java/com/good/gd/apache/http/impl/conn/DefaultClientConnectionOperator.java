package com.good.gd.apache.http.impl.conn;

import android.net.TrafficStats;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.ClientConnectionOperator;
import com.good.gd.apache.http.conn.ConnectTimeoutException;
import com.good.gd.apache.http.conn.HttpHostConnectException;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.conn.scheme.LayeredSocketFactory;
import com.good.gd.apache.http.conn.scheme.PlainSocketFactory;
import com.good.gd.apache.http.conn.scheme.Scheme;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.conn.scheme.SocketFactory;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/* loaded from: classes.dex */
public class DefaultClientConnectionOperator implements ClientConnectionOperator {
    private static final PlainSocketFactory staticPlainSocketFactory = new PlainSocketFactory();
    protected SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry) {
        if (schemeRegistry != null) {
            this.schemeRegistry = schemeRegistry;
            return;
        }
        throw new IllegalArgumentException("Scheme registry must not be null.");
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionOperator
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PlainSocketFactory getPlainSocketFactory() {
        return staticPlainSocketFactory;
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionOperator
    public void openConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, InetAddress inetAddress, HttpContext httpContext, HttpParams httpParams) throws IOException {
        LayeredSocketFactory layeredSocketFactory;
        PlainSocketFactory plainSocketFactory;
        int i;
        InetAddress[] inetAddressArr;
        SocketFactory socketFactory;
        int i2;
        int i3;
        InetAddress[] inetAddressArr2;
        if (operatedClientConnection != null) {
            if (httpHost == null) {
                throw new IllegalArgumentException("Target host must not be null.");
            }
            if (httpParams != null) {
                if (!operatedClientConnection.isOpen()) {
                    Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
                    SocketFactory socketFactory2 = scheme.getSocketFactory();
                    if (socketFactory2 instanceof LayeredSocketFactory) {
                        plainSocketFactory = staticPlainSocketFactory;
                        layeredSocketFactory = (LayeredSocketFactory) socketFactory2;
                    } else {
                        layeredSocketFactory = null;
                        plainSocketFactory = socketFactory2;
                    }
                    InetAddress[] allByName = InetAddress.getAllByName(httpHost.getHostName());
                    int i4 = 0;
                    while (i4 < allByName.length) {
                        Socket createSocket = plainSocketFactory.createSocket();
                        TrafficStats.setThreadStatsTag((int) Thread.currentThread().getId());
                        if (createSocket != null) {
                            TrafficStats.tagSocket(createSocket);
                            operatedClientConnection.opening(createSocket, httpHost);
                            try {
                                try {
                                    try {
                                        SocketFactory socketFactory3 = plainSocketFactory;
                                        socketFactory = plainSocketFactory;
                                        i2 = 1;
                                        i3 = i4;
                                        inetAddressArr2 = allByName;
                                        try {
                                            Socket connectSocket = socketFactory3.connectSocket(createSocket, allByName[i4].getHostAddress(), scheme.resolvePort(httpHost.getPort()), inetAddress, 0, httpParams);
                                            if (createSocket != connectSocket) {
                                                operatedClientConnection.opening(connectSocket, httpHost);
                                                createSocket = connectSocket;
                                            }
                                        } catch (ConnectTimeoutException e) {
                                            e = e;
                                        } catch (SocketException e2) {
                                            e = e2;
                                        }
                                    } catch (SocketException e3) {
                                        e = e3;
                                        socketFactory = plainSocketFactory;
                                        i2 = 1;
                                        i = i4;
                                        inetAddressArr = allByName;
                                    }
                                } catch (ConnectTimeoutException e4) {
                                    e = e4;
                                    i3 = i4;
                                    inetAddressArr2 = allByName;
                                    socketFactory = plainSocketFactory;
                                    i2 = 1;
                                }
                            } catch (SocketException e5) {
                                e = e5;
                                i = i4;
                                inetAddressArr = allByName;
                                socketFactory = plainSocketFactory;
                                i2 = 1;
                            }
                            try {
                                prepareSocket(createSocket, httpContext, httpParams);
                                if (layeredSocketFactory != null) {
                                    Socket createSocket2 = layeredSocketFactory.createSocket(createSocket, httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), true);
                                    if (createSocket2 != createSocket) {
                                        operatedClientConnection.opening(createSocket2, httpHost);
                                    }
                                    operatedClientConnection.openCompleted(socketFactory2.isSecure(createSocket2), httpParams);
                                    return;
                                }
                                operatedClientConnection.openCompleted(socketFactory2.isSecure(createSocket), httpParams);
                                return;
                            } catch (ConnectTimeoutException e6) {
                                e = e6;
                                inetAddressArr = inetAddressArr2;
                                i = i3;
                                if (i == inetAddressArr.length - i2) {
                                    throw e;
                                }
                                i4 = i + 1;
                                allByName = inetAddressArr;
                                plainSocketFactory = socketFactory;
                            } catch (SocketException e7) {
                                e = e7;
                                i = i3;
                                inetAddressArr = inetAddressArr2;
                                if (i == inetAddressArr.length - i2) {
                                    throw new HttpHostConnectException(httpHost, e instanceof ConnectException ? (ConnectException) e : new ConnectException(e.getMessage()));
                                }
                                i4 = i + 1;
                                allByName = inetAddressArr;
                                plainSocketFactory = socketFactory;
                            }
                        } else {
                            i = i4;
                            inetAddressArr = allByName;
                            socketFactory = plainSocketFactory;
                        }
                        i4 = i + 1;
                        allByName = inetAddressArr;
                        plainSocketFactory = socketFactory;
                    }
                    return;
                }
                throw new IllegalArgumentException("Connection must not be open.");
            }
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        throw new IllegalArgumentException("Connection must not be null.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int linger = HttpConnectionParams.getLinger(httpParams);
        if (linger >= 0) {
            socket.setSoLinger(linger > 0, linger);
        }
    }

    @Override // com.good.gd.apache.http.conn.ClientConnectionOperator
    public void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (operatedClientConnection != null) {
            if (httpHost == null) {
                throw new IllegalArgumentException("Target host must not be null.");
            }
            if (httpParams != null) {
                if (operatedClientConnection.isOpen()) {
                    Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
                    if (scheme.getSocketFactory() instanceof LayeredSocketFactory) {
                        LayeredSocketFactory layeredSocketFactory = (LayeredSocketFactory) scheme.getSocketFactory();
                        try {
                            Socket createSocket = layeredSocketFactory.createSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), true);
                            prepareSocket(createSocket, httpContext, httpParams);
                            operatedClientConnection.update(createSocket, httpHost, layeredSocketFactory.isSecure(createSocket), httpParams);
                            return;
                        } catch (ConnectException e) {
                            throw new HttpHostConnectException(httpHost, e);
                        }
                    }
                    throw new IllegalArgumentException("Target scheme (" + scheme.getName() + ") must have layered socket factory.");
                }
                throw new IllegalArgumentException("Connection must be open.");
            }
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        throw new IllegalArgumentException("Connection must not be null.");
    }
}
