package com.good.gd.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class BasicHttpEntity extends AbstractHttpEntity {
    private InputStream content;
    private boolean contentObtained;
    private long length = -1;

    @Override // com.good.gd.apache.http.entity.AbstractHttpEntity, com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        InputStream inputStream = this.content;
        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IllegalStateException {
        InputStream inputStream = this.content;
        if (inputStream != null) {
            if (!this.contentObtained) {
                this.contentObtained = true;
                return inputStream;
            }
            throw new IllegalStateException("Content has been consumed");
        }
        throw new IllegalStateException("Content has not been provided");
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public long getContentLength() {
        return this.length;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isStreaming() {
        return !this.contentObtained && this.content != null;
    }

    public void setContent(InputStream inputStream) {
        this.content = inputStream;
        this.contentObtained = false;
    }

    public void setContentLength(long j) {
        this.length = j;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            InputStream content = getContent();
            byte[] bArr = new byte[2048];
            while (true) {
                int read = content.read(bArr);
                if (read == -1) {
                    return;
                }
                outputStream.write(bArr, 0, read);
            }
        } else {
            throw new IllegalArgumentException("Output stream may not be null");
        }
    }
}
