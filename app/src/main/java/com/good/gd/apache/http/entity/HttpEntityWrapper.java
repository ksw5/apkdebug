package com.good.gd.apache.http.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpEntity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class HttpEntityWrapper implements HttpEntity {
    protected HttpEntity wrappedEntity;

    public HttpEntityWrapper(HttpEntity httpEntity) {
        if (httpEntity != null) {
            this.wrappedEntity = httpEntity;
            return;
        }
        throw new IllegalArgumentException("wrapped entity must not be null");
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        this.wrappedEntity.consumeContent();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return this.wrappedEntity.getContent();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public Header getContentEncoding() {
        return this.wrappedEntity.getContentEncoding();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        return this.wrappedEntity.getContentLength();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public Header getContentType() {
        return this.wrappedEntity.getContentType();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isChunked() {
        return this.wrappedEntity.isChunked();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return this.wrappedEntity.isRepeatable();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return this.wrappedEntity.isStreaming();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        this.wrappedEntity.writeTo(outputStream);
    }
}
