package com.good.gd.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class ByteArrayEntity extends AbstractHttpEntity implements Cloneable {
    protected final byte[] content;

    public ByteArrayEntity(byte[] bArr) {
        if (bArr != null) {
            this.content = bArr;
            return;
        }
        throw new IllegalArgumentException("Source byte array may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() {
        return new ByteArrayInputStream(this.content);
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        return this.content.length;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            outputStream.write(this.content);
            outputStream.flush();
            return;
        }
        throw new IllegalArgumentException("Output stream may not be null");
    }
}
