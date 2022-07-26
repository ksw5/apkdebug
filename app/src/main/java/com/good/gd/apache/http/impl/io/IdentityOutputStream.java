package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.SessionOutputBuffer;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class IdentityOutputStream extends OutputStream {
    private boolean closed = false;
    private final SessionOutputBuffer out;

    public IdentityOutputStream(SessionOutputBuffer sessionOutputBuffer) {
        if (sessionOutputBuffer != null) {
            this.out = sessionOutputBuffer;
            return;
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
            this.out.write(bArr, i, i2);
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
            this.out.write(i);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
