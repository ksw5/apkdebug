package com.good.gd.apachehttp.impl.conn;

import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.conn.ConnectTimeoutException;
import com.good.gd.apache.http.conn.HttpHostConnectException;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.conn.scheme.LayeredSocketFactory;
import com.good.gd.apache.http.conn.scheme.PlainSocketFactory;
import com.good.gd.apache.http.conn.scheme.Scheme;
import com.good.gd.apache.http.conn.scheme.SchemeRegistry;
import com.good.gd.apache.http.conn.scheme.SocketFactory;
import com.good.gd.apache.http.impl.conn.DefaultClientConnectionOperator;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.protocol.HttpContext;
import com.good.gd.apachehttp.conn.scheme.GDPlainSocketFactory;
import com.good.gd.hpm.HPMReport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.net.JavaNetSocketImpl;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.net.GDSocket;
import com.good.gd.net.GDSocketHelper;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/* loaded from: classes.dex */
public class GDDefaultClientConnectionOperator extends DefaultClientConnectionOperator {
    private static SocketFactory staticPlainSocketFactory;
    private OperatedClientConnection _operatedClientConnection = null;
    GDSingleClientConnManager manager;

    public GDDefaultClientConnectionOperator(SchemeRegistry schemeRegistry, GDSingleClientConnManager gDSingleClientConnManager) {
        super(schemeRegistry);
        this.manager = null;
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator() IN:\n");
        this.manager = gDSingleClientConnManager;
        GDLog.DBGPRINTF(19, "GDDefaultClientConnectionOperator( ) : parent static factory=" + getParentPlainSocketFactory() + "\n");
        staticPlainSocketFactory = GDPlainSocketFactory.getSocketFactory();
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator() OUT\n");
    }

    private PlainSocketFactory getParentPlainSocketFactory() {
        return super.getPlainSocketFactory();
    }

    private void reportConnectionError(HttpHost httpHost, GDSocket gDSocket) {
        String str;
        String str2 = "";
        if (!NetworkStateMonitor.getInstance().isNetworkAvailable()) {
            return;
        }
        try {
            JavaNetSocketImpl impl = GDSocketHelper.getImpl(gDSocket);
            str = impl.currentRoute();
            try {
                str2 = impl.currentGPS();
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            str = str2;
        }
        String str3 = str2;
        String str4 = str;
        HPMReport.getInstance().connectionErrorReport(httpHost.getHostName(), httpHost.getPort() == -1 ? httpHost.getSchemeName().equals("https") ? 443 : 80 : httpHost.getPort(), httpHost.getOriginalUri() != null ? httpHost.getOriginalUri() : httpHost.toURI(), NetworkStateMonitor.getInstance().isWiFiConnected(), str4, str3);
    }

    @Override // com.good.gd.apache.http.impl.conn.DefaultClientConnectionOperator, com.good.gd.apache.http.conn.ClientConnectionOperator
    public OperatedClientConnection createConnection() {
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::createConnection() IN\n");
        this._operatedClientConnection = super.createConnection();
        GDSocket underlyingGDSocket = getUnderlyingGDSocket();
        if (underlyingGDSocket != null) {
            if (this.manager.isHostVerificationDisabled()) {
                underlyingGDSocket.disableHostVerification();
            }
            if (this.manager.isCertVerificationDisabled()) {
                underlyingGDSocket.disablePeerVerification();
            }
        }
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::createConnection() OUT\n");
        return this._operatedClientConnection;
    }

    public final void disablePeerVerification() {
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::disablePeerVerification() IN: this=" + this + "\n");
        GDSocket underlyingGDSocket = getUnderlyingGDSocket();
        if (underlyingGDSocket != null) {
            underlyingGDSocket.disablePeerVerification();
        }
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::disablePeerVerification() OUT: this=" + this + "\n");
    }

    public final GDSocket getUnderlyingGDSocket() {
        GDSocket gDSocket;
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::getUnderlyingGDSocket() IN: this=" + this + "\n");
        OperatedClientConnection operatedClientConnection = this._operatedClientConnection;
        if (operatedClientConnection != null) {
            Socket socket = operatedClientConnection.getSocket();
            GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::getUnderlyingGDSocket() - underlyingSocket " + socket + "\n");
            if (socket != null) {
                if (socket instanceof GDSocket) {
                    gDSocket = (GDSocket) socket;
                    GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::getUnderlyingGDSocket() OUT: this=" + this + "\n");
                    return gDSocket;
                }
                throw new IllegalStateException("Socket not created by GD.");
            }
        }
        gDSocket = null;
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::getUnderlyingGDSocket() OUT: this=" + this + "\n");
        return gDSocket;
    }

    @Override // com.good.gd.apache.http.impl.conn.DefaultClientConnectionOperator, com.good.gd.apache.http.conn.ClientConnectionOperator
    public void openConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, InetAddress inetAddress, HttpContext httpContext, HttpParams httpParams) throws IOException {
        LayeredSocketFactory layeredSocketFactory;
        SocketFactory socketFactory;
        Socket socket;
        Socket socket2;
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::openConnection IN: this=" + this + " \n");
        if (operatedClientConnection != null) {
            if (httpHost == null) {
                throw new IllegalArgumentException("Target host must not be null.");
            }
            if (httpParams != null) {
                if (!operatedClientConnection.isOpen()) {
                    this._operatedClientConnection = operatedClientConnection;
                    Scheme scheme = this.schemeRegistry.getScheme(httpHost.getSchemeName());
                    SocketFactory socketFactory2 = scheme.getSocketFactory();
                    if (socketFactory2 instanceof LayeredSocketFactory) {
                        socketFactory = staticPlainSocketFactory;
                        LayeredSocketFactory layeredSocketFactory2 = (LayeredSocketFactory) socketFactory2;
                        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::openConnection() - using layered fac:" + layeredSocketFactory2 + "\n");
                        layeredSocketFactory = layeredSocketFactory2;
                    } else {
                        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::openConnection() using plain fac.\n");
                        layeredSocketFactory = null;
                        socketFactory = socketFactory2;
                    }
                    String hostName = httpHost.getHostName();
                    GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::openConnection() host=" + hostName + "\n");
                    Socket createSocket = socketFactory.createSocket();
                    if (createSocket != null) {
                        if (this.manager.isHostVerificationDisabled()) {
                            ((GDSocket) createSocket).disableHostVerification();
                        }
                        if (this.manager.isCertVerificationDisabled()) {
                            ((GDSocket) createSocket).disablePeerVerification();
                        }
                        ((GDSocket) createSocket).setSessionId((String) httpContext.getAttribute("webview.sessionId"));
                        operatedClientConnection.opening(createSocket, httpHost);
                        try {
                            socket = createSocket;
                            LayeredSocketFactory layeredSocketFactory3 = layeredSocketFactory;
                            try {
                                socket2 = socketFactory.connectSocket(createSocket, hostName, scheme.resolvePort(httpHost.getPort()), inetAddress, 0, httpParams);
                                if (socket != socket2) {
                                    try {
                                        operatedClientConnection.opening(socket2, httpHost);
                                    } catch (ConnectTimeoutException e) {
                                        e = e;
                                        reportConnectionError(httpHost, (GDSocket) socket2);
                                        throw e;
                                    } catch (ConnectException e2) {
                                        e = e2;
                                        reportConnectionError(httpHost, (GDSocket) socket2);
                                        throw new HttpHostConnectException(httpHost, e);
                                    } catch (IOException e3) {
                                        e = e3;
                                        reportConnectionError(httpHost, (GDSocket) socket2);
                                        throw e;
                                    }
                                } else {
                                    socket2 = socket;
                                }
                                prepareSocket(socket2, httpContext, httpParams);
                                if (layeredSocketFactory3 != null) {
                                    Socket createSocket2 = layeredSocketFactory3.createSocket(socket2, hostName, scheme.resolvePort(httpHost.getPort()), true);
                                    if (createSocket2 != socket2) {
                                        operatedClientConnection.opening(createSocket2, httpHost);
                                    }
                                    operatedClientConnection.openCompleted(socketFactory2.isSecure(createSocket2), httpParams);
                                } else {
                                    operatedClientConnection.openCompleted(socketFactory2.isSecure(socket2), httpParams);
                                }
                                GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::openConnection() OUT: this=" + this + "\n");
                            } catch (ConnectTimeoutException e4) {
                                e = e4;
                                socket2 = socket;
                                reportConnectionError(httpHost, (GDSocket) socket2);
                                throw e;
                            } catch (ConnectException e5) {
                                e = e5;
                                socket2 = socket;
                                reportConnectionError(httpHost, (GDSocket) socket2);
                                throw new HttpHostConnectException(httpHost, e);
                            } catch (IOException e6) {
                                e = e6;
                                socket2 = socket;
                                reportConnectionError(httpHost, (GDSocket) socket2);
                                throw e;
                            }
                        } catch (ConnectTimeoutException e7) {
                            e = e7;
                            socket = createSocket;
                        } catch (ConnectException e8) {
                            e = e8;
                            socket = createSocket;
                        } catch (IOException e9) {
                            e = e9;
                            socket = createSocket;
                        }
                    } else {
                        throw new IOException("Cannot create socket");
                    }
                } else {
                    throw new IllegalArgumentException("Connection must not be open.");
                }
            } else {
                throw new IllegalArgumentException("Parameters must not be null.");
            }
        } else {
            throw new IllegalArgumentException("Connection must not be null.");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.conn.DefaultClientConnectionOperator
    public void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        super.prepareSocket(socket, httpContext, httpParams);
        GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::prepareSocket( " + socket + " ) \n");
        if (socket.getClass() != GDSocket.class || !GDSocketHelper.isSecure((GDSocket) socket)) {
            return;
        }
        int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
        int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
        int soTimeout2 = socket.getSoTimeout();
        if (soTimeout2 > 0) {
            GDLog.DBGPRINTF(19, "GDDefaultClientConnectionOperator::prepareSocket: handshake duration is limited " + soTimeout2 + " \n");
            return;
        }
        boolean z = false;
        if (soTimeout <= 0) {
            soTimeout = connectionTimeout > 0 ? connectionTimeout : 0;
        }
        if (soTimeout > 0) {
            GDLog.DBGPRINTF(16, "GDDefaultClientConnectionOperator::prepareSocket: limiting handshake duration to " + soTimeout + " \n");
            socket.setSoTimeout(connectionTimeout);
            z = true;
        }
        if (z) {
            return;
        }
        GDLog.DBGPRINTF(13, "GDDefaultClientConnectionOperator::prepareSocket: unlimited handshake duration \n");
    }
}
