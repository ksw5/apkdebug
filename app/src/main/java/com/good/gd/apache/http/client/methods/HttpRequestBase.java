package com.good.gd.apache.http.client.methods;

import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.client.utils.CloneUtils;
import com.good.gd.apache.http.conn.ClientConnectionRequest;
import com.good.gd.apache.http.conn.ConnectionReleaseTrigger;
import com.good.gd.apache.http.message.AbstractHttpMessage;
import com.good.gd.apache.http.message.BasicRequestLine;
import com.good.gd.apache.http.message.HeaderGroup;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.params.HttpProtocolParams;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public abstract class HttpRequestBase extends AbstractHttpMessage implements HttpUriRequest, AbortableHttpRequest, Cloneable {
    private Lock abortLock = new ReentrantLock();
    private boolean aborted;
    private ClientConnectionRequest connRequest;
    private ConnectionReleaseTrigger releaseTrigger;
    private URI uri;

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest, com.good.gd.apache.http.client.methods.AbortableHttpRequest
    public void abort() {
        this.abortLock.lock();
        try {
            if (this.aborted) {
                return;
            }
            this.aborted = true;
            ClientConnectionRequest clientConnectionRequest = this.connRequest;
            ConnectionReleaseTrigger connectionReleaseTrigger = this.releaseTrigger;
            if (clientConnectionRequest != null) {
                clientConnectionRequest.abortRequest();
            }
            if (connectionReleaseTrigger == null) {
                return;
            }
            try {
                connectionReleaseTrigger.abortConnection();
            } catch (IOException e) {
            }
        } finally {
            this.abortLock.unlock();
        }
    }

    public Object clone() throws CloneNotSupportedException {
        HttpRequestBase httpRequestBase = (HttpRequestBase) super.clone();
        httpRequestBase.abortLock = new ReentrantLock();
        httpRequestBase.aborted = false;
        httpRequestBase.releaseTrigger = null;
        httpRequestBase.connRequest = null;
        httpRequestBase.headergroup = (HeaderGroup) CloneUtils.clone(this.headergroup);
        httpRequestBase.params = (HttpParams) CloneUtils.clone(this.params);
        return httpRequestBase;
    }

    public abstract String getMethod();

    public int getPortForRequest() {
        int port = this.uri.getPort();
        return port == -1 ? "https".equalsIgnoreCase(this.uri.getScheme()) ? 443 : 80 : port;
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        return HttpProtocolParams.getVersion(getParams());
    }

    @Override // com.good.gd.apache.http.HttpRequest
    public RequestLine getRequestLine() {
        String str;
        String method = getMethod();
        ProtocolVersion protocolVersion = getProtocolVersion();
        URI uri = getURI();
        if (uri == null) {
            str = null;
        } else {
            str = uri.toASCIIString();
        }
        if (str == null || str.length() == 0) {
            str = "/";
        }
        return new BasicRequestLine(method, str, protocolVersion);
    }

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest
    public URI getURI() {
        return this.uri;
    }

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest
    public boolean isAborted() {
        return this.aborted;
    }

    @Override // com.good.gd.apache.http.client.methods.AbortableHttpRequest
    public void setConnectionRequest(ClientConnectionRequest clientConnectionRequest) throws IOException {
        this.abortLock.lock();
        try {
            if (!this.aborted) {
                this.releaseTrigger = null;
                this.connRequest = clientConnectionRequest;
                return;
            }
            throw new IOException("Request already aborted");
        } finally {
            this.abortLock.unlock();
        }
    }

    @Override // com.good.gd.apache.http.client.methods.AbortableHttpRequest
    public void setReleaseTrigger(ConnectionReleaseTrigger connectionReleaseTrigger) throws IOException {
        this.abortLock.lock();
        try {
            if (!this.aborted) {
                this.connRequest = null;
                this.releaseTrigger = connectionReleaseTrigger;
                return;
            }
            throw new IOException("Request already aborted");
        } finally {
            this.abortLock.unlock();
        }
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }
}
