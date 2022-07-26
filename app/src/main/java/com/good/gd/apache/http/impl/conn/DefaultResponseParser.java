package com.good.gd.apache.http.impl.conn;

import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.conn.params.ConnConnectionPNames;
import com.good.gd.apache.http.impl.io.AbstractMessageParser;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.message.LineParser;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class DefaultResponseParser extends AbstractMessageParser {
    private final CharArrayBuffer lineBuf;
    private final int maxGarbageLines;
    private final HttpResponseFactory responseFactory;

    public DefaultResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        if (httpResponseFactory != null) {
            this.responseFactory = httpResponseFactory;
            this.lineBuf = new CharArrayBuffer(128);
            this.maxGarbageLines = httpParams.getIntParameter(ConnConnectionPNames.MAX_STATUS_LINE_GARBAGE, Integer.MAX_VALUE);
            return;
        }
        throw new IllegalArgumentException("Response factory may not be null");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0050, code lost:
        throw new com.good.gd.apache.http.ProtocolException("The server failed to respond with a valid HTTP response");
     */
    @Override // com.good.gd.apache.http.impl.io.AbstractMessageParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.good.gd.apache.http.HttpMessage parseHead(SessionInputBuffer r8) throws java.io.IOException, com.good.gd.apache.http.HttpException {
        /*
            r7 = this;
            com.good.gd.apache.http.util.CharArrayBuffer r0 = r7.lineBuf
            r0.clear()
            r0 = 0
            r1 = r0
        L7:
            com.good.gd.apache.http.util.CharArrayBuffer r2 = r7.lineBuf
            int r2 = r8.readLine(r2)
            r3 = -1
            if (r2 != r3) goto L1b
            if (r1 == 0) goto L13
            goto L1b
        L13:
            com.good.gd.apache.http.NoHttpResponseException r8 = new com.good.gd.apache.http.NoHttpResponseException
            java.lang.String r0 = "The target server failed to respond"
            r8.<init>(r0)
            throw r8
        L1b:
            com.good.gd.apache.http.message.ParserCursor r4 = new com.good.gd.apache.http.message.ParserCursor
            com.good.gd.apache.http.util.CharArrayBuffer r5 = r7.lineBuf
            int r5 = r5.length()
            r4.<init>(r0, r5)
            com.good.gd.apache.http.message.LineParser r5 = r7.lineParser
            com.good.gd.apache.http.util.CharArrayBuffer r6 = r7.lineBuf
            boolean r5 = r5.hasProtocolVersion(r6, r4)
            if (r5 == 0) goto L40
            com.good.gd.apache.http.message.LineParser r8 = r7.lineParser
            com.good.gd.apache.http.util.CharArrayBuffer r0 = r7.lineBuf
            com.good.gd.apache.http.StatusLine r8 = r8.parseStatusLine(r0, r4)
            com.good.gd.apache.http.HttpResponseFactory r0 = r7.responseFactory
            r1 = 0
            com.good.gd.apache.http.HttpResponse r8 = r0.newHttpResponse(r8, r1)
            return r8
        L40:
            if (r2 == r3) goto L49
            int r2 = r7.maxGarbageLines
            if (r1 >= r2) goto L49
            int r1 = r1 + 1
            goto L7
        L49:
            com.good.gd.apache.http.ProtocolException r8 = new com.good.gd.apache.http.ProtocolException
            java.lang.String r0 = "The server failed to respond with a valid HTTP response"
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.impl.conn.DefaultResponseParser.parseHead(com.good.gd.apache.http.io.SessionInputBuffer):com.good.gd.apache.http.HttpMessage");
    }
}
