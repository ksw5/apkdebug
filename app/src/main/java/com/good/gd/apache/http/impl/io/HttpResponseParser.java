package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.NoHttpResponseException;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.message.LineParser;
import com.good.gd.apache.http.message.ParserCursor;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.io.IOException;

/* loaded from: classes.dex */
public class HttpResponseParser extends AbstractMessageParser {
    private final CharArrayBuffer lineBuf;
    private final HttpResponseFactory responseFactory;

    public HttpResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        if (httpResponseFactory != null) {
            this.responseFactory = httpResponseFactory;
            this.lineBuf = new CharArrayBuffer(128);
            return;
        }
        throw new IllegalArgumentException("Response factory may not be null");
    }

    @Override // com.good.gd.apache.http.impl.io.AbstractMessageParser
    protected HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException {
        this.lineBuf.clear();
        if (sessionInputBuffer.readLine(this.lineBuf) != -1) {
            return this.responseFactory.newHttpResponse(this.lineParser.parseStatusLine(this.lineBuf, new ParserCursor(0, this.lineBuf.length())), null);
        }
        throw new NoHttpResponseException("The target server failed to respond");
    }
}
