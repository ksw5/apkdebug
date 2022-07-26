package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.MalformedChunkCodingException;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.apache.http.util.ExceptionUtils;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ChunkedInputStream extends InputStream {
    private final CharArrayBuffer buffer;
    private int chunkSize;
    private SessionInputBuffer in;
    private int pos;
    private boolean bof = true;
    private boolean eof = false;
    private boolean closed = false;
    private Header[] footers = new Header[0];

    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer) {
        if (sessionInputBuffer != null) {
            this.in = sessionInputBuffer;
            this.pos = 0;
            this.buffer = new CharArrayBuffer(16);
            return;
        }
        throw new IllegalArgumentException("Session input buffer may not be null");
    }

    static void exhaustInputStream(InputStream inputStream) throws IOException {
        do {
        } while (inputStream.read(new byte[1024]) >= 0);
    }

    private int getChunkSize() throws IOException {
        if (!this.bof) {
            int read = this.in.read();
            int read2 = this.in.read();
            if (read != 13 || read2 != 10) {
                throw new MalformedChunkCodingException("CRLF expected at end of chunk");
            }
        }
        this.buffer.clear();
        if (this.in.readLine(this.buffer) != -1) {
            int indexOf = this.buffer.indexOf(59);
            if (indexOf < 0) {
                indexOf = this.buffer.length();
            }
            try {
                return Integer.parseInt(this.buffer.substringTrimmed(0, indexOf), 16);
            } catch (NumberFormatException e) {
                throw new MalformedChunkCodingException("Bad chunk header");
            }
        }
        throw new MalformedChunkCodingException("Chunked stream ended unexpectedly");
    }

    private void nextChunk() throws IOException {
        int chunkSize = getChunkSize();
        this.chunkSize = chunkSize;
        if (chunkSize >= 0) {
            this.bof = false;
            this.pos = 0;
            if (chunkSize != 0) {
                return;
            }
            this.eof = true;
            parseTrailerHeaders();
            return;
        }
        throw new MalformedChunkCodingException("Negative chunk size");
    }

    private void parseTrailerHeaders() throws IOException {
        try {
            this.footers = AbstractMessageParser.parseHeaders(this.in, -1, -1, null);
        } catch (HttpException e) {
            MalformedChunkCodingException malformedChunkCodingException = new MalformedChunkCodingException("Invalid footer: " + e.getMessage());
            ExceptionUtils.initCause(malformedChunkCodingException, e);
            throw malformedChunkCodingException;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.closed) {
            try {
                if (!this.eof) {
                    exhaustInputStream(this);
                }
            } finally {
                this.eof = true;
                this.closed = true;
            }
        }
    }

    public Header[] getFooters() {
        return (Header[]) this.footers.clone();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.closed) {
            if (this.eof) {
                return -1;
            }
            if (this.pos >= this.chunkSize) {
                nextChunk();
                if (this.eof) {
                    return -1;
                }
            }
            this.pos++;
            return this.in.read();
        }
        throw new IOException("Attempted read from closed stream.");
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.closed) {
            if (this.eof) {
                return -1;
            }
            if (this.pos >= this.chunkSize) {
                nextChunk();
                if (this.eof) {
                    return -1;
                }
            }
            int read = this.in.read(bArr, i, Math.min(i2, this.chunkSize - this.pos));
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
