package com.good.gd.apache.http.message;

import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class BasicRequestLine implements RequestLine, Cloneable {
    private final String method;
    private final ProtocolVersion protoversion;
    private final String uri;

    public BasicRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        if (str != null) {
            if (str2 == null) {
                throw new IllegalArgumentException("URI must not be null.");
            }
            if (protocolVersion != null) {
                this.method = str;
                this.uri = str2;
                this.protoversion = protocolVersion;
                return;
            }
            throw new IllegalArgumentException("Protocol version must not be null.");
        }
        throw new IllegalArgumentException("Method must not be null.");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.RequestLine
    public String getMethod() {
        return this.method;
    }

    @Override // com.good.gd.apache.http.RequestLine
    public ProtocolVersion getProtocolVersion() {
        return this.protoversion;
    }

    @Override // com.good.gd.apache.http.RequestLine
    public String getUri() {
        return this.uri;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatRequestLine((CharArrayBuffer) null, this).toString();
    }
}
