package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.commons.logging.impl.MessageWithSessionId;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpHost;
import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.conn.OperatedClientConnection;
import com.good.gd.apache.http.impl.SocketHttpClientConnection;
import com.good.gd.apache.http.io.HttpMessageParser;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;

/* loaded from: classes.dex */
public class DefaultClientConnection extends SocketHttpClientConnection implements OperatedClientConnection {
    private boolean connSecure;
    public String sessionId;
    private volatile boolean shutdown;
    private volatile Socket socket;
    private HttpHost targetHost;
    private final Log log = LogFactory.getLog(DefaultClientConnection.class);
    private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
    private final Log wireLog = LogFactory.getLog("org.apache.http.wire");

    private String filterInfo(String str) {
        if (str.contains(":")) {
            String[] strArr = {"auth", "token", "cookie"};
            String lowerCase = str.substring(0, str.indexOf(":")).toLowerCase(Locale.ENGLISH);
            for (int i = 0; i < 3; i++) {
                if (lowerCase.contains(strArr[i])) {
                    return str.substring(0, str.indexOf(":") + 1) + " <filtered>";
                }
            }
            return str;
        }
        return str;
    }

    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection, com.good.gd.apache.http.HttpConnection
    public void close() throws IOException {
        this.log.debug("Connection closed");
        super.close();
    }

    @Override // com.good.gd.apache.http.impl.AbstractHttpClientConnection
    protected HttpMessageParser createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        SessionInputBuffer createSessionInputBuffer = super.createSessionInputBuffer(socket, i, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionInputBuffer(createSessionInputBuffer, new Wire(this.wireLog)) : createSessionInputBuffer;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int i, HttpParams httpParams) throws IOException {
        SessionOutputBuffer createSessionOutputBuffer = super.createSessionOutputBuffer(socket, i, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionOutputBuffer(createSessionOutputBuffer, new Wire(this.wireLog)) : createSessionOutputBuffer;
    }

    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection, com.good.gd.apache.http.conn.OperatedClientConnection
    public final Socket getSocket() {
        return this.socket;
    }

    @Override // com.good.gd.apache.http.conn.OperatedClientConnection
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override // com.good.gd.apache.http.conn.OperatedClientConnection
    public final boolean isSecure() {
        return this.connSecure;
    }

    @Override // com.good.gd.apache.http.conn.OperatedClientConnection
    public void openCompleted(boolean z, HttpParams httpParams) throws IOException {
        assertNotOpen();
        if (httpParams != null) {
            this.connSecure = z;
            bind(this.socket, httpParams);
            return;
        }
        throw new IllegalArgumentException("Parameters must not be null.");
    }

    @Override // com.good.gd.apache.http.conn.OperatedClientConnection
    public void opening(Socket socket, HttpHost httpHost) throws IOException {
        assertNotOpen();
        this.socket = socket;
        this.targetHost = httpHost;
        if (!this.shutdown) {
            return;
        }
        socket.close();
        throw new IOException("Connection already shutdown");
    }

    @Override // com.good.gd.apache.http.impl.AbstractHttpClientConnection, com.good.gd.apache.http.HttpClientConnection
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        Header[] allHeaders;
        HttpResponse receiveResponseHeader = super.receiveResponseHeader();
        if (this.headerLog.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<< ");
            sb.append(receiveResponseHeader.getStatusLine().toString());
            for (Header header : receiveResponseHeader.getAllHeaders()) {
                sb.append('\n');
                sb.append("<< ");
                sb.append(filterInfo(header.toString()));
            }
            this.headerLog.debug(new MessageWithSessionId(this.sessionId, sb.toString()));
        }
        return receiveResponseHeader;
    }

    @Override // com.good.gd.apache.http.impl.AbstractHttpClientConnection, com.good.gd.apache.http.HttpClientConnection
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        Header[] allHeaders;
        super.sendRequestHeader(httpRequest);
        if (this.headerLog.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append(">> ");
            sb.append(httpRequest.getRequestLine().toString());
            for (Header header : httpRequest.getAllHeaders()) {
                sb.append('\n');
                sb.append(">> ");
                sb.append(filterInfo(header.toString()));
            }
            this.headerLog.debug(new MessageWithSessionId(this.sessionId, sb.toString()));
        }
    }

    @Override // com.good.gd.apache.http.impl.SocketHttpClientConnection, com.good.gd.apache.http.HttpConnection
    public void shutdown() throws IOException {
        this.log.debug("Connection shut down");
        this.shutdown = true;
        super.shutdown();
        Socket socket = this.socket;
        if (socket != null) {
            socket.close();
        }
    }

    @Override // com.good.gd.apache.http.conn.OperatedClientConnection
    public void update(Socket socket, HttpHost httpHost, boolean z, HttpParams httpParams) throws IOException {
        assertOpen();
        if (httpHost != null) {
            if (httpParams != null) {
                if (socket != null) {
                    this.socket = socket;
                    bind(socket, httpParams);
                }
                this.targetHost = httpHost;
                this.connSecure = z;
                return;
            }
            throw new IllegalArgumentException("Parameters must not be null.");
        }
        throw new IllegalArgumentException("Target host must not be null.");
    }
}
