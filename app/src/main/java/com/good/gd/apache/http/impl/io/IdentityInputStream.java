package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.SessionInputBuffer;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class IdentityInputStream extends InputStream {
    private boolean closed = false;
    private final SessionInputBuffer in;

    public IdentityInputStream(SessionInputBuffer sessionInputBuffer) {
        if (sessionInputBuffer != null) {
            this.in = sessionInputBuffer;
            return;
        }
        throw new IllegalArgumentException("Session input buffer may not be null");
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return (this.closed || !this.in.isDataAvailable(10)) ? 0 : 1;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closed = true;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.in.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.in.read(bArr, i, i2);
    }
}
