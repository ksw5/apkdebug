package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.io.HttpTransportMetrics;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public class LoggingSessionInputBuffer implements SessionInputBuffer {
    private final SessionInputBuffer in;
    private final Wire wire;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this.in = sessionInputBuffer;
        this.wire = wire;
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public boolean isDataAvailable(int i) throws IOException {
        return this.in.isDataAvailable(i);
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(bArr, i, read);
        }
        return read;
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public String readLine() throws IOException {
        String readLine = this.in.readLine();
        if (this.wire.enabled() && readLine != null) {
            this.wire.input(readLine + "[EOL]");
        }
        return readLine;
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public int read() throws IOException {
        int read = this.in.read();
        if (this.wire.enabled() && read > 0) {
            this.wire.input(read);
        }
        return read;
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        int readLine = this.in.readLine(charArrayBuffer);
        if (this.wire.enabled() && readLine > 0) {
            this.wire.input(new String(charArrayBuffer.buffer(), charArrayBuffer.length() - readLine, readLine) + "[EOL]");
        }
        return readLine;
    }

    @Override // com.good.gd.apache.http.io.SessionInputBuffer
    public int read(byte[] bArr) throws IOException {
        int read = this.in.read(bArr);
        if (this.wire.enabled() && read > 0) {
            this.wire.input(bArr, 0, read);
        }
        return read;
    }
}
