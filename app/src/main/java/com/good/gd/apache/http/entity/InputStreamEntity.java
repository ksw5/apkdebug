package com.good.gd.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class InputStreamEntity extends AbstractHttpEntity {
    private static final int BUFFER_SIZE = 2048;
    private boolean consumed = false;
    private final InputStream content;
    private final long length;

    public InputStreamEntity(InputStream inputStream, long j) {
        if (inputStream != null) {
            this.content = inputStream;
            this.length = j;
            return;
        }
        throw new IllegalArgumentException("Source input stream may not be null");
    }

    @Override // com.good.gd.apache.http.entity.AbstractHttpEntity, com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException {
        this.consumed = true;
        this.content.close();
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public InputStream getContent() throws IOException {
        return this.content;
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
        return !this.consumed;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public void writeTo(OutputStream outputStream) throws IOException {
        int read;
        if (outputStream != null) {
            InputStream inputStream = this.content;
            byte[] bArr = new byte[2048];
            long j = this.length;
            if (j < 0) {
                while (true) {
                    int read2 = inputStream.read(bArr);
                    if (read2 == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, read2);
                }
            } else {
                while (j > 0 && (read = inputStream.read(bArr, 0, (int) Math.min(2048L, j))) != -1) {
                    outputStream.write(bArr, 0, read);
                    j -= read;
                }
            }
            this.consumed = true;
            return;
        }
        throw new IllegalArgumentException("Output stream may not be null");
    }
}
