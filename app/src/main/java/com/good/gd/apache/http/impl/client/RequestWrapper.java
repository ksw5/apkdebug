package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.client.methods.HttpUriRequest;
import com.good.gd.apache.http.message.AbstractHttpMessage;
import com.good.gd.apache.http.message.BasicRequestLine;
import com.good.gd.apache.http.params.HttpProtocolParams;
import java.net.URI;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class RequestWrapper extends AbstractHttpMessage implements HttpUriRequest {
    private int execCount;
    private String method;
    private final HttpRequest original;
    private URI uri;
    private ProtocolVersion version;

    public RequestWrapper(HttpRequest httpRequest) throws ProtocolException {
        if (httpRequest != null) {
            this.original = httpRequest;
            setParams(httpRequest.getParams());
            if (httpRequest instanceof HttpUriRequest) {
                HttpUriRequest httpUriRequest = (HttpUriRequest) httpRequest;
                this.uri = httpUriRequest.getURI();
                this.method = httpUriRequest.getMethod();
                this.version = null;
            } else {
                RequestLine requestLine = httpRequest.getRequestLine();
                try {
                    this.uri = new URI(requestLine.getUri());
                    this.method = requestLine.getMethod();
                    this.version = httpRequest.getProtocolVersion();
                } catch (URISyntaxException e) {
                    throw new ProtocolException("Invalid request URI: " + requestLine.getUri(), e);
                }
            }
            this.execCount = 0;
            return;
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest, com.good.gd.apache.http.client.methods.AbortableHttpRequest
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public int getExecCount() {
        return this.execCount;
    }

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest
    public String getMethod() {
        return this.method;
    }

    public HttpRequest getOriginal() {
        return this.original;
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        ProtocolVersion protocolVersion = this.version;
        return protocolVersion != null ? protocolVersion : HttpProtocolParams.getVersion(getParams());
    }

    @Override // com.good.gd.apache.http.HttpRequest
    public RequestLine getRequestLine() {
        String str;
        String method = getMethod();
        ProtocolVersion protocolVersion = getProtocolVersion();
        URI uri = this.uri;
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

    public void incrementExecCount() {
        this.execCount++;
    }

    @Override // com.good.gd.apache.http.client.methods.HttpUriRequest
    public boolean isAborted() {
        return false;
    }

    public boolean isRepeatable() {
        return true;
    }

    public void resetHeaders() {
        this.headergroup.clear();
        setHeaders(this.original.getAllHeaders());
    }

    public void setMethod(String str) {
        if (str != null) {
            this.method = str;
            return;
        }
        throw new IllegalArgumentException("Method name may not be null");
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }
}
