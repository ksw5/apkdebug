package com.good.gd.apache.http.conn.ssl;

import com.good.gd.apache.http.conn.scheme.HostNameResolver;
import com.good.gd.apache.http.conn.scheme.LayeredSocketFactory;
import com.good.gd.apache.http.params.HttpConnectionParams;
import com.good.gd.apache.http.params.HttpParams;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/* loaded from: classes.dex */
public class SSLSocketFactory implements LayeredSocketFactory {
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final String TLS = "TLS";
    private X509HostnameVerifier hostnameVerifier;
    private final HostNameResolver nameResolver;
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    private final SSLContext sslcontext;
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();

    /* loaded from: classes.dex */
    private static class hbfhc {
        private static final SSLSocketFactory dbjc = new SSLSocketFactory();
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

    public static SSLSocketFactory getSocketFactory() {
        return hbfhc.dbjc;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (str != null) {
            if (httpParams != null) {
                if (socket == null) {
                    socket = createSocket();
                }
                SSLSocket sSLSocket = (SSLSocket) socket;
                if (inetAddress != null || i2 > 0) {
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    sSLSocket.bind(new InetSocketAddress(inetAddress, i2));
                }
                int connectionTimeout = HttpConnectionParams.getConnectionTimeout(httpParams);
                int soTimeout = HttpConnectionParams.getSoTimeout(httpParams);
                if (this.nameResolver != null) {
                    inetSocketAddress = new InetSocketAddress(this.nameResolver.resolve(str), i);
                } else {
                    inetSocketAddress = new InetSocketAddress(str, i);
                }
                sSLSocket.connect(inetSocketAddress, connectionTimeout);
                sSLSocket.setSoTimeout(soTimeout);
                try {
                    sSLSocket.startHandshake();
                    this.hostnameVerifier.verify(str, sSLSocket);
                    return sSLSocket;
                } catch (IOException e) {
                    try {
                        sSLSocket.close();
                    } catch (Exception e2) {
                    }
                    throw e;
                }
            }
            throw new IllegalArgumentException("Parameters may not be null.");
        }
        throw new IllegalArgumentException("Target host may not be null.");
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        return (SSLSocket) this.socketfactory.createSocket();
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override // com.good.gd.apache.http.conn.scheme.SocketFactory
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        if (socket != null) {
            if (socket instanceof SSLSocket) {
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

    public SSLSocketFactory(String str, KeyStore keyStore, String str2, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        KeyManager[] keyManagerArr;
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
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

    @Override // com.good.gd.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException, UnknownHostException {
        SSLSocket sSLSocket = (SSLSocket) this.socketfactory.createSocket(socket, str, i, z);
        this.hostnameVerifier.verify(str, sSLSocket);
        return sSLSocket;
    }

    public SSLSocketFactory(KeyStore keyStore, String str, KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", keyStore, str, keyStore2, null, null);
    }

    public SSLSocketFactory(KeyStore keyStore, String str) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", keyStore, str, null, null, null);
    }

    public SSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this("TLS", null, null, keyStore, null, null);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory) {
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.sslcontext = null;
        this.socketfactory = sSLSocketFactory;
        this.nameResolver = null;
    }

    private SSLSocketFactory() {
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.sslcontext = null;
        this.socketfactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        this.nameResolver = null;
    }
}
