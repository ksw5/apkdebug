package com.good.gd.apache.http.message;

import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class BasicStatusLine implements StatusLine, Cloneable {
    private final ProtocolVersion protoVersion;
    private final String reasonPhrase;
    private final int statusCode;

    public BasicStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        if (protocolVersion != null) {
            if (i >= 0) {
                this.protoVersion = protocolVersion;
                this.statusCode = i;
                this.reasonPhrase = str;
                return;
            }
            throw new IllegalArgumentException("Status code may not be negative.");
        }
        throw new IllegalArgumentException("Protocol version may not be null.");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.StatusLine
    public ProtocolVersion getProtocolVersion() {
        return this.protoVersion;
    }

    @Override // com.good.gd.apache.http.StatusLine
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override // com.good.gd.apache.http.StatusLine
    public int getStatusCode() {
        return this.statusCode;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatStatusLine((CharArrayBuffer) null, this).toString();
    }
}
