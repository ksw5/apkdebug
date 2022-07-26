package com.good.gd.ndkproxy.net;

import com.good.gd.ndkproxy.GDLog;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketImpl;

/* loaded from: classes.dex */
class SocketOutputStream extends OutputStream {
    private JavaNetSocketImpl dbjc;

    public SocketOutputStream(SocketImpl socketImpl) {
        this.dbjc = (JavaNetSocketImpl) socketImpl;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.dbjc.close();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        GDLog.DBGPRINTF(16, "SocketOutputStream::write( with buffer only ) IN\n");
        this.dbjc.write(bArr, 0, bArr.length);
        GDLog.DBGPRINTF(16, "SocketOutputStream::write() OUT\n");
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        GDLog.DBGPRINTF(16, "SocketOutputStream::write( offset=" + i + ", count=" + i2 + ") IN\n");
        if (bArr != null) {
            if (i >= 0 && i <= bArr.length && i2 >= 0 && i2 <= bArr.length - i) {
                this.dbjc.write(bArr, i, i2);
                GDLog.DBGPRINTF(16, "SocketOutputStream::write() OUT\n");
                return;
            }
            throw new ArrayIndexOutOfBoundsException("luni.13");
        }
        throw new NullPointerException("luni.11");
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        GDLog.DBGPRINTF(16, "SocketOutputStream::write( oneByte=" + i + ") IN\n");
        this.dbjc.write(new byte[]{(byte) (i & 255)}, 0, 1);
        GDLog.DBGPRINTF(16, "SocketOutputStream::write() OUT\n");
    }
}
