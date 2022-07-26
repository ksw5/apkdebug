package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.ConnectionClosedException;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.HttpRequestFactory;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.message.LineParser;
import com.good.gd.apache.http.message.ParserCursor;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public class HttpRequestParser extends AbstractMessageParser {
    private final CharArrayBuffer lineBuf;
    private final HttpRequestFactory requestFactory;

    public HttpRequestParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpRequestFactory httpRequestFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        if (httpRequestFactory != null) {
            this.requestFactory = httpRequestFactory;
            this.lineBuf = new CharArrayBuffer(128);
            return;
        }
        throw new IllegalArgumentException("Request factory may not be null");
    }

    @Override // com.good.gd.apache.http.impl.io.AbstractMessageParser
    protected HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) != -1) {
            return this.requestFactory.newHttpRequest(this.lineParser.parseRequestLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())));
        }
        throw new ConnectionClosedException("Client closed connection");
    }
}
