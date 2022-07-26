package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.io.HttpMessageWriter;
import com.good.gd.apache.http.io.SessionOutputBuffer;
import com.good.gd.apache.http.message.BasicLineFormatter;
import com.good.gd.apache.http.message.LineFormatter;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractMessageWriter implements HttpMessageWriter {
    protected final CharArrayBuffer lineBuf;
    protected final LineFormatter lineFormatter;
    protected final SessionOutputBuffer sessionBuffer;

    public AbstractMessageWriter(SessionOutputBuffer sessionOutputBuffer, LineFormatter lineFormatter, HttpParams httpParams) {
        if (sessionOutputBuffer != null) {
            this.sessionBuffer = sessionOutputBuffer;
            this.lineBuf = new CharArrayBuffer(128);
            this.lineFormatter = lineFormatter == null ? BasicLineFormatter.DEFAULT : lineFormatter;
            return;
        }
        throw new IllegalArgumentException("Session input buffer may not be null");
    }

    @Override // com.good.gd.apache.http.io.HttpMessageWriter
    public void write(HttpMessage httpMessage) throws IOException, HttpException {
        if (httpMessage != null) {
            writeHeadLine(httpMessage);
            HeaderIterator headerIterator = httpMessage.headerIterator();
            while (headerIterator.hasNext()) {
                this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, (Header) headerIterator.next()));
            }
            this.lineBuf.clear();
            this.sessionBuffer.writeLine(this.lineBuf);
            return;
        }
        throw new IllegalArgumentException("HTTP message may not be null");
    }

    protected abstract void writeHeadLine(HttpMessage httpMessage) throws IOException;
}
