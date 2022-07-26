package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.SessionOutputBuffer;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class ContentLengthOutputStream extends OutputStream {
    private final long contentLength;
    private final SessionOutputBuffer out;
    private long total = 0;
    private boolean closed = false;

    public ContentLengthOutputStream(SessionOutputBuffer sessionOutputBuffer, long j) {
        if (sessionOutputBuffer != null) {
            if (j >= 0) {
                this.out = sessionOutputBuffer;
                this.contentLength = j;
                return;
            }
            throw new IllegalArgumentException("Content length may not be negative");
        }
        throw new IllegalArgumentException("Session output buffer may not be null");
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.out.flush();
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (!this.closed) {
            long j = this.total;
            long j2 = this.contentLength;
            if (j >= j2) {
                return;
            }
            long j3 = j2 - j;
            if (i2 > j3) {
                i2 = (int) j3;
            }
            this.out.write(bArr, i, i2);
            this.total += i2;
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        if (!this.closed) {
            if (this.total >= this.contentLength) {
                return;
            }
            this.out.write(i);
            this.total++;
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
