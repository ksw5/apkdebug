package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpConnectionMetrics;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.conn.ClientConnectionManager;
import com.good.gd.apache.http.conn.ManagedClientConnection;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/* loaded from: classes.dex */
public abstract class AbstractClientConnAdapter implements ManagedClientConnection {
    private volatile ClientConnectionManager connManager;
    private volatile OperatedClientConnection wrappedConnection;
    private final Thread executionThread = Thread.currentThread();
    private volatile boolean markedReusable = false;
    private volatile boolean aborted = false;
    private volatile long duration = Long.MAX_VALUE;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractClientConnAdapter(ClientConnectionManager clientConnectionManager, OperatedClientConnection operatedClientConnection) {
        this.connManager = clientConnectionManager;
        this.wrappedConnection = operatedClientConnection;
    }

    @Override // com.good.gd.apache.http.conn.ConnectionReleaseTrigger
    public void abortConnection() {
        if (this.aborted) {
            return;
        }
        this.aborted = true;
        unmarkReusable();
        try {
            shutdown();
        } catch (IOException e) {
        }
        if (!this.executionThread.equals(Thread.currentThread())) {
            return;
        }
        releaseConnection();
    }

    protected final void assertNotAborted() throws InterruptedIOException {
        if (!this.aborted) {
            return;
        }
        throw new InterruptedIOException("Connection has been shut down.");
    }

    protected final void assertValid(OperatedClientConnection operatedClientConnection) {
        if (operatedClientConnection != null) {
            return;
        }
        throw new IllegalStateException("No wrapped connection.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void detach() {
        this.wrappedConnection = null;
        this.connManager = null;
        this.duration = Long.MAX_VALUE;
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void flush() throws IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        wrappedConnection.flush();
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public InetAddress getLocalAddress() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getLocalAddress();
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public int getLocalPort() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getLocalPort();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ClientConnectionManager getManager() {
        return this.connManager;
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public HttpConnectionMetrics getMetrics() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getMetrics();
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public InetAddress getRemoteAddress() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getRemoteAddress();
    }

    @Override // com.good.gd.apache.http.HttpInetConnection
    public int getRemotePort() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getRemotePort();
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public SSLSession getSSLSession() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        if (!isOpen()) {
            return null;
        }
        Socket socket = wrappedConnection.getSocket();
        if (!(socket instanceof SSLSocket)) {
            return null;
        }
        return ((SSLSocket) socket).getSession();
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public int getSocketTimeout() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.getSocketTimeout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public OperatedClientConnection getWrappedConnection() {
        return this.wrappedConnection;
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public boolean isMarkedReusable() {
        return this.markedReusable;
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public boolean isOpen() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        if (wrappedConnection == null) {
            return false;
        }
        return wrappedConnection.isOpen();
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public boolean isResponseAvailable(int i) throws IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.isResponseAvailable(i);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public boolean isSecure() {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        return wrappedConnection.isSecure();
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public boolean isStale() {
        OperatedClientConnection wrappedConnection;
        if (!this.aborted && (wrappedConnection = getWrappedConnection()) != null) {
            return wrappedConnection.isStale();
        }
        return true;
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void markReusable() {
        this.markedReusable = true;
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        unmarkReusable();
        wrappedConnection.receiveResponseEntity(httpResponse);
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        unmarkReusable();
        return wrappedConnection.receiveResponseHeader();
    }

    @Override // com.good.gd.apache.http.conn.ConnectionReleaseTrigger
    public void releaseConnection() {
        if (this.connManager != null) {
            this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
        }
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        unmarkReusable();
        wrappedConnection.sendRequestEntity(httpEntityEnclosingRequest);
    }

    @Override // com.good.gd.apache.http.HttpClientConnection
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        assertNotAborted();
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        unmarkReusable();
        wrappedConnection.sendRequestHeader(httpRequest);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void setIdleDuration(long j, TimeUnit timeUnit) {
        if (j > 0) {
            this.duration = timeUnit.toMillis(j);
        } else {
            this.duration = -1L;
        }
    }

    @Override // com.good.gd.apache.http.HttpConnection
    public void setSocketTimeout(int i) {
        OperatedClientConnection wrappedConnection = getWrappedConnection();
        assertValid(wrappedConnection);
        wrappedConnection.setSocketTimeout(i);
    }

    @Override // com.good.gd.apache.http.conn.ManagedClientConnection
    public void unmarkReusable() {
        this.markedReusable = false;
    }
}
