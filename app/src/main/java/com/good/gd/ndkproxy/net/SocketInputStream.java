package com.good.gd.ndkproxy.net;

import com.good.gd.ndkproxy.GDLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketImpl;
import kotlin.UByte;

/* loaded from: classes.dex */
class SocketInputStream extends InputStream {
    private final JavaNetSocketImpl dbjc;

    public SocketInputStream(SocketImpl socketImpl) {
        this.dbjc = (JavaNetSocketImpl) socketImpl;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.dbjc.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.dbjc.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        GDLog.DBGPRINTF(17, "SocketInputStream::read() IN\n");
        byte[] bArr = new byte[1];
        int read = this.dbjc.read(bArr, 0, 1);
        if (-1 != read) {
            read = bArr[0] & UByte.MAX_VALUE;
        }
        GDLog.DBGPRINTF(17, "SocketInputStream::read() OUT\n");
        return read;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (0 == j) {
            return 0L;
        }
        return super.skip(j);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        GDLog.DBGPRINTF(17, "SocketInputStream::read( with buffer ) IN\n");
        int read = read(bArr, 0, bArr.length);
        GDLog.DBGPRINTF(17, "SocketInputStream::read() OUT\n");
        return read;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        GDLog.DBGPRINTF(17, "SocketInputStream::read( offset=" + i + ", count=" + i2 + " ) IN\n");
        if (bArr != null) {
            if (i2 == 0) {
                return 0;
            }
            if (i >= 0 && i < bArr.length) {
                if (i2 >= 0 && i + i2 <= bArr.length) {
                    int read = this.dbjc.read(bArr, i, i2);
                    GDLog.DBGPRINTF(17, "SocketInputStream::read() OUT\n");
                    return read;
                }
                throw new ArrayIndexOutOfBoundsException("luni.13");
            }
            throw new ArrayIndexOutOfBoundsException("Offset out of bounds" + i);
        }
        throw new IOException("luni.11");
    }
}
