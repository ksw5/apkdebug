package com.good.gd.apache.http.message;

import com.good.gd.apache.http.HttpRequest;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.params.HttpProtocolParams;

/* loaded from: classes.dex */
public class BasicHttpRequest extends AbstractHttpMessage implements HttpRequest {
    private final String method;
    private final RequestLine requestline;
    private final String uri;

    public BasicHttpRequest(String str, String str2) {
        if (str != null) {
            if (str2 != null) {
                this.method = str;
                this.uri = str2;
                this.requestline = null;
                return;
            }
            throw new IllegalArgumentException("Request URI may not be null");
        }
        throw new IllegalArgumentException("Method name may not be null");
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        RequestLine requestLine = this.requestline;
        if (requestLine != null) {
            return requestLine.getProtocolVersion();
        }
        return HttpProtocolParams.getVersion(getParams());
    }

    @Override // com.good.gd.apache.http.HttpRequest
    public RequestLine getRequestLine() {
        RequestLine requestLine = this.requestline;
        if (requestLine != null) {
            return requestLine;
        }
        return new BasicRequestLine(this.method, this.uri, HttpProtocolParams.getVersion(getParams()));
    }

    public BasicHttpRequest(String str, String str2, ProtocolVersion protocolVersion) {
        this(new BasicRequestLine(str, str2, protocolVersion));
    }

    public BasicHttpRequest(RequestLine requestLine) {
        if (requestLine != null) {
            this.requestline = requestLine;
            this.method = requestLine.getMethod();
            this.uri = requestLine.getUri();
            return;
        }
        throw new IllegalArgumentException("Request line may not be null");
    }
}
