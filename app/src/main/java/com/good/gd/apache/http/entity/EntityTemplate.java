package com.good.gd.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class EntityTemplate extends AbstractHttpEntity {
    private final ContentProducer contentproducer;

    public EntityTemplate(ContentProducer contentProducer) {
        if (contentProducer != null) {
            this.contentproducer = contentProducer;
            return;
        }
        throw new IllegalArgumentException("Content producer may not be null");
    }

    @Override // com.good.gd.apache.http.entity.AbstractHttpEntity, com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException {
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() {
        throw new UnsupportedOperationException("Entity template does not implement getContent()");
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        return -1L;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return true;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            this.contentproducer.writeTo(outputStream);
            return;
        }
        throw new IllegalArgumentException("Output stream may not be null");
    }
}
