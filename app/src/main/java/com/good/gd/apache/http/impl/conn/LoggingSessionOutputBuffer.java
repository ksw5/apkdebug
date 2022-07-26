package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.io.HttpTransportMetrics;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public class LoggingSessionOutputBuffer implements SessionOutputBuffer {
    private final SessionOutputBuffer out;
    private final Wire wire;

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire) {
        this.out = sessionOutputBuffer;
        this.wire = wire;
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        if (this.wire.enabled()) {
            this.wire.output(bArr, i, i2);
        }
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        this.out.writeLine(charArrayBuffer);
        if (this.wire.enabled()) {
            this.wire.output(new String(charArrayBuffer.buffer(), 0, charArrayBuffer.length()) + "[EOL]");
        }
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(int i) throws IOException {
        this.out.write(i);
        if (this.wire.enabled()) {
            this.wire.output(i);
        }
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void writeLine(String str) throws IOException {
        this.out.writeLine(str);
        if (this.wire.enabled()) {
            this.wire.output(str + "[EOL]");
        }
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr) throws IOException {
        this.out.write(bArr);
        if (this.wire.enabled()) {
            this.wire.output(bArr);
        }
    }
}
