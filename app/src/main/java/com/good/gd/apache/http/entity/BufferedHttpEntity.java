package com.good.gd.apache.http.entity;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.util.EntityUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class BufferedHttpEntity extends HttpEntityWrapper {
    private final byte[] buffer;

    public BufferedHttpEntity(HttpEntity httpEntity) throws IOException {
        super(httpEntity);
        if (httpEntity.isRepeatable() && httpEntity.getContentLength() >= 0) {
            this.buffer = null;
        } else {
            this.buffer = EntityUtils.toByteArray(httpEntity);
        }
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        if (this.buffer != null) {
            return new ByteArrayInputStream(this.buffer);
        }
        return this.wrappedEntity.getContent();
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        byte[] bArr = this.buffer;
        if (bArr != null) {
            return bArr.length;
        }
        return this.wrappedEntity.getContentLength();
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public boolean isChunked() {
        return this.buffer == null && this.wrappedEntity.isChunked();
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return true;
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return this.buffer == null && this.wrappedEntity.isStreaming();
    }

    @Override // com.good.gd.apache.http.entity.HttpEntityWrapper, com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            byte[] bArr = this.buffer;
            if (bArr != null) {
                outputStream.write(bArr);
                return;
            } else {
                this.wrappedEntity.writeTo(outputStream);
                return;
            }
        }
        throw new IllegalArgumentException("Output stream may not be null");
    }
}
