package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.io.HttpTransportMetrics;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.params.HttpProtocolParams;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apache.http.util.ByteArrayBuffer;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public abstract class AbstractSessionOutputBuffer implements SessionOutputBuffer {
    private static final byte[] CRLF = {13, 10};
    private static final int MAX_CHUNK = 256;
    private ByteArrayBuffer buffer;
    private HttpTransportMetricsImpl metrics;
    private OutputStream outstream;
    private String charset = "US-ASCII";
    private boolean ascii = true;

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void flush() throws IOException {
        flushBuffer();
        this.outstream.flush();
    }

    protected void flushBuffer() throws IOException {
        int length = this.buffer.length();
        if (length > 0) {
            this.outstream.write(this.buffer.buffer(), 0, length);
            this.buffer.clear();
            this.metrics.incrementBytesTransferred(length);
        }
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init(OutputStream outputStream, int i, HttpParams httpParams) {
        if (outputStream != null) {
            if (i <= 0) {
                throw new IllegalArgumentException("Buffer size may not be negative or zero");
            }
            if (httpParams != null) {
                this.outstream = outputStream;
                this.buffer = new ByteArrayBuffer(i);
                String httpElementCharset = HttpProtocolParams.getHttpElementCharset(httpParams);
                this.charset = httpElementCharset;
                this.ascii = httpElementCharset.equalsIgnoreCase("US-ASCII") || this.charset.equalsIgnoreCase(HTTP.ASCII);
                this.metrics = new HttpTransportMetricsImpl();
                return;
            }
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        throw new IllegalArgumentException("Input stream may not be null");
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (bArr == null) {
            return;
        }
        if (i2 <= 256 && i2 <= this.buffer.capacity()) {
            if (i2 > this.buffer.capacity() - this.buffer.length()) {
                flushBuffer();
            }
            this.buffer.append(bArr, i, i2);
            return;
        }
        flushBuffer();
        this.outstream.write(bArr, i, i2);
        this.metrics.incrementBytesTransferred(i2);
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void writeLine(String str) throws IOException {
        if (str == null) {
            return;
        }
        if (str.length() > 0) {
            write(str.getBytes(this.charset));
        }
        write(CRLF);
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            return;
        }
        if (this.ascii) {
            int i = 0;
            int length = charArrayBuffer.length();
            while (length > 0) {
                int min = Math.min(this.buffer.capacity() - this.buffer.length(), length);
                if (min > 0) {
                    this.buffer.append(charArrayBuffer, i, min);
                }
                if (this.buffer.isFull()) {
                    flushBuffer();
                }
                i += min;
                length -= min;
            }
        } else {
            write(charArrayBuffer.toString().getBytes(this.charset));
        }
        write(CRLF);
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(byte[] bArr) throws IOException {
        if (bArr == null) {
            return;
        }
        write(bArr, 0, bArr.length);
    }

    @Override // com.good.gd.apache.http.io.SessionOutputBuffer
    public void write(int i) throws IOException {
        if (this.buffer.isFull()) {
            flushBuffer();
        }
        this.buffer.append(i);
    }
}
