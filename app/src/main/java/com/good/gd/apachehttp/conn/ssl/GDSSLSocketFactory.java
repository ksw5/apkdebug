package com.good.gd.apachehttp.conn.ssl;

import com.good.gd.apache.http.conn.scheme.HostNameResolver;
import com.good.gd.apache.http.conn.scheme.LayeredSocketFactory;
import com.good.gd.apache.http.conn.ssl.AllowAllHostnameVerifier;
import com.good.gd.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import com.good.gd.apache.http.conn.ssl.StrictHostnameVerifier;
import com.good.gd.apache.http.conn.ssl.X509HostnameVerifier;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.net.GDSocket;
import com.good.gd.net.GDSocketException;
import com.good.gd.net.GDSocketHelper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public class GDSSLSocketFactory implements LayeredSocketFactory {
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final String TLS = "TLS";
    private boolean disableHostVerification;
    private X509HostnameVerifier hostnameVerifier;
    private final HostNameResolver nameResolver;
    private final SSLSocketFactory socketfactory;
    private final SSLContext sslcontext;
    private static final TrustManager[] INSECURE_TRUST_MANAGER = {new hbfhc()};
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* loaded from: classes.dex */
    static class hbfhc implements X509TrustManager {
        hbfhc() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            GDLog.DBGPRINTF(16, "GDSSLSocketFactory()::TrustManager - client check\n");
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            GDLog.DBGPRINTF(16, "GDSSLSocketFactory()::TrustManager - server check\n");
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            GDLog.DBGPRINTF(16, "GDSSLSocketFactory()::TrustManager - getAcceptedIssuers\n");
            return null;
        }
    }

    public GDSSLSocketFactory(boolean z, boolean z2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        TrustManager[] trustManagerArr;
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.disableHostVerification = false;
        this.disableHostVerification = z2;
        GDLog.DBGPRINTF(16, "GDSSLSocketFactory() IN\n");
        if (z) {
            GDLog.DBGPRINTF(16, "GDSSLSocketFactory::connectSocket() - using insecure trust manager\n");
            trustManagerArr = INSECURE_TRUST_MANAGER;
        } else {
            trustManagerArr = null;
        }
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        this.sslcontext = sSLContext;
        sSLContext.init(null, trustManagerArr, null);
        this.socketfactory = sSLContext.getSocketFactory();
        this.nameResolver = null;
        GDLog.DBGPRINTF(16, "GDSSLSocketFactory() OUT\n");
    }

    private void convertToSecureSocket(GDSocket gDSocket, String str) throws GDSocketException {
        if (!GDSocketHelper.isSecure(gDSocket)) {
            if (GDSocketHelper.convertToSecure(gDSocket, str)) {
                return;
            }
            GDLog.DBGPRINTF(13, "GDSSLSocketFactory::convertToSecureSocket can't convert to secure\n");
            return;
        }
        GDLog.DBGPRINTF(13, "GDSSLSocketFactory::convertToSecureSocket socket already secure\n");
    }

    private static KeyManager[] createKeyManagers(KeyStore keyStore, String str) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (keyStore != null) {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, str != null ? str.toCharArray() : null);
            return keyManagerFactory.getKeyManagers();
        }
        throw new IllegalArgumentException("Keystore may not be null");
    }

    private static TrustManager[] createTrustManagers(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException {
        if (keyStore != null) {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        }
        throw new IllegalArgumentException("Keystore may not be null");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
        InetSocketAddress createUnresolved;
        GDLog.DBGPRINTF(19, "GDSSLSocketFactory::connectSocket(sock:" + socket + ") IN\n");
        if (str != null) {
            if (httpParams != null) {
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
                GDLog.DBGPRINTF(19, "GDSSLSocketFactory::connectSocket() OUT: sslsock=" + gDSocket + "\n");
                return gDSocket;
            }
            throw new IllegalArgumentException("Parameters may not be null.");
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    public Socket createSecureGDSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
        Socket createSocket;
        GDLog.DBGPRINTF(19, "GDSSLSocketFactory::createSecureGDSocket(socket:" + socket + ", host: " + str + ") IN\n");
        if (socket instanceof GDSocket) {
            GDLog.DBGPRINTF(19, "GDSSLSocketFactory::createSocket() - passed socket was ours.\n");
            createSocket = socket;
        } else {
            createSocket = createSocket();
            GDLog.DBGPRINTF(13, "GDSSLSocketFactory::createSocket() - passed socket is not ours! replaced socket ok.\n");
        }
        GDSocket gDSocket = (GDSocket) createSocket;
        convertToSecureSocket(gDSocket, str);
        GDLog.DBGPRINTF(19, "GDSSLSocketFactory::createSocket(socket:" + socket + ") OUT: " + gDSocket + "\n");
        return gDSocket;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        GDLog.DBGPRINTF(19, "GDSSLSocketFactory::createSocket() IN/OUT\n");
        return new GDSocket(true);
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
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

    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        if (x509HostnameVerifier != null) {
            this.hostnameVerifier = x509HostnameVerifier;
            return;
        }
        throw new IllegalArgumentException("Hostname verifier may not be null");
    }

    @Override // com.good.gd.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
        return createSecureGDSocket(socket, str, i, z);
    }

    public GDSSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        KeyManager[] keyManagerArr;
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.disableHostVerification = false;
        str = str == null ? "TLS" : str;
        TrustManager[] trustManagerArr = null;
        if (keyStore == null) {
            keyManagerArr = null;
        } else {
            keyManagerArr = createKeyManagers(keyStore, str2);
        }
        trustManagerArr = keyStore2 != null ? createTrustManagers(keyStore2) : trustManagerArr;
        SSLContext sSLContext = SSLContext.getInstance(str);
        this.sslcontext = sSLContext;
        sSLContext.init(keyManagerArr, trustManagerArr, secureRandom);
        this.socketfactory = sSLContext.getSocketFactory();
        this.nameResolver = hostNameResolver;
    }

    public GDSSLSocketFactory(KeyStore keyStore, String str, KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", keyStore, str, keyStore2, null, null);
    }

    public GDSSLSocketFactory(KeyStore keyStore, String str) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", keyStore, str, null, null, null);
    }

    public GDSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", null, null, keyStore, null, null);
    }

    public GDSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.disableHostVerification = false;
        this.sslcontext = null;
        this.socketfactory = sSLSocketFactory;
        this.nameResolver = null;
    }
}
