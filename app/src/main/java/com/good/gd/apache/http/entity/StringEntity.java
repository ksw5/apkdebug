package com.good.gd.apache.http.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class StringEntity extends AbstractHttpEntity implements Cloneable {
    protected final byte[] content;

    public StringEntity(String str, String str2) throws UnsupportedEncodingException {
        if (str != null) {
            str2 = str2 == null ? "ISO-8859-1" : str2;
            this.content = str.getBytes(str2);
            setContentType("text/plain; charset=" + str2);
            return;
        }
        throw new IllegalArgumentException("Source string may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
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

    public StringEntity(String str) throws UnsupportedEncodingException {
        this(str, null);
    }
}
