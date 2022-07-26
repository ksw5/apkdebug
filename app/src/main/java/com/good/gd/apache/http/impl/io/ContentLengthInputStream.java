package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.SessionInputBuffer;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ContentLengthInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private long contentLength;
    private SessionInputBuffer in;
    private long pos = 0;
    private boolean closed = false;

    public ContentLengthInputStream(SessionInputBuffer sessionInputBuffer, long j) {
        this.in = null;
        if (sessionInputBuffer != null) {
            if (j >= 0) {
                this.in = sessionInputBuffer;
                this.contentLength = j;
                return;
            }
            throw new IllegalArgumentException("Content length may not be negative");
        }
        throw new IllegalArgumentException("Input stream may not be null");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.closed) {
            try {
                do {
                } while (read(new byte[2048]) >= 0);
            } finally {
                this.closed = true;
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.closed) {
            long j = this.pos;
            if (j >= this.contentLength) {
                return -1;
            }
            this.pos = j + 1;
            return this.in.read();
        }
        throw new IOException("Attempted read from closed stream.");
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        int read;
        if (j <= 0) {
            return 0L;
        }
        byte[] bArr = new byte[2048];
        long min = Math.min(j, this.contentLength - this.pos);
        long j2 = 0;
        while (min > 0 && (read = read(bArr, 0, (int) Math.min(2048L, min))) != -1) {
            long j3 = read;
            j2 += j3;
            min -= j3;
        }
        this.pos += j2;
        return j2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.closed) {
            long j = this.pos;
            long j2 = this.contentLength;
            if (j >= j2) {
                return -1;
            }
            if (i2 + j > j2) {
                i2 = (int) (j2 - j);
            }
            int read = this.in.read(bArr, i, i2);
            this.pos += read;
            return read;
        }
        throw new IOException("Attempted read from closed stream.");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
