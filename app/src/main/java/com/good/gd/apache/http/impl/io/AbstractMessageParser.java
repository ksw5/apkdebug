package com.good.gd.apache.http.impl.io;

import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.io.HttpMessageParser;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.message.BasicLineParser;
import com.good.gd.apache.http.message.LineParser;
import com.good.gd.apache.http.params.CoreConnectionPNames;
import com.good.gd.apache.http.params.HttpParams;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractMessageParser implements HttpMessageParser {
    protected final LineParser lineParser;
    private final int maxHeaderCount;
    private final int maxLineLen;
    private final SessionInputBuffer sessionBuffer;

    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpParams httpParams) {
        if (sessionInputBuffer != null) {
            if (httpParams != null) {
                this.sessionBuffer = sessionInputBuffer;
                this.maxHeaderCount = httpParams.getIntParameter(CoreConnectionPNames.MAX_HEADER_COUNT, -1);
                this.maxLineLen = httpParams.getIntParameter(CoreConnectionPNames.MAX_LINE_LENGTH, -1);
                this.lineParser = lineParser == null ? BasicLineParser.DEFAULT : lineParser;
                return;
            }
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        throw new IllegalArgumentException("Session input buffer may not be null");
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x008b, code lost:
        r9 = new com.good.gd.apache.http.Header[r0.size()];
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0095, code lost:
        if (r6 >= r0.size()) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x009d, code lost:
        r9[r6] = r12.parseHeader((com.good.gd.apache.http.util.CharArrayBuffer) r0.get(r6));
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a3, code lost:
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a6, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b0, code lost:
        throw new com.good.gd.apache.http.ProtocolException(r9.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b1, code lost:
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.good.gd.apache.http.Header[] parseHeaders(SessionInputBuffer r9, int r10, int r11, LineParser r12) throws HttpException, IOException {
        /*
            if (r9 == 0) goto Lb2
            if (r12 != 0) goto L6
            com.good.gd.apache.http.message.BasicLineParser r12 = com.good.gd.apache.http.message.BasicLineParser.DEFAULT
        L6:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = r1
            r3 = r2
        Le:
            if (r2 != 0) goto L18
            com.good.gd.apache.http.util.CharArrayBuffer r2 = new com.good.gd.apache.http.util.CharArrayBuffer
            r4 = 64
            r2.<init>(r4)
            goto L1b
        L18:
            r2.clear()
        L1b:
            int r4 = r9.readLine(r2)
            r5 = -1
            r6 = 0
            if (r4 == r5) goto L8b
            int r4 = r2.length()
            r5 = 1
            if (r4 >= r5) goto L2b
            goto L8b
        L2b:
            char r4 = r2.charAt(r6)
            r7 = 9
            r8 = 32
            if (r4 == r8) goto L3b
            char r4 = r2.charAt(r6)
            if (r4 != r7) goto L74
        L3b:
            if (r3 == 0) goto L74
        L3d:
            int r4 = r2.length()
            if (r6 >= r4) goto L4f
            char r4 = r2.charAt(r6)
            if (r4 == r8) goto L4c
            if (r4 == r7) goto L4c
            goto L4f
        L4c:
            int r6 = r6 + 1
            goto L3d
        L4f:
            if (r11 <= 0) goto L67
            int r4 = r3.length()
            int r4 = r4 + r5
            int r5 = r2.length()
            int r4 = r4 + r5
            int r4 = r4 - r6
            if (r4 > r11) goto L5f
            goto L67
        L5f:
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Maximum line length limit exceeded"
            r9.<init>(r10)
            throw r9
        L67:
            r3.append(r8)
            int r4 = r2.length()
            int r4 = r4 - r6
            r3.append(r2, r6, r4)
            goto L79
        L74:
            r0.add(r2)
            r3 = r2
            r2 = r1
        L79:
            if (r10 <= 0) goto L8a
            int r4 = r0.size()
            if (r4 >= r10) goto L82
            goto L8a
        L82:
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Maximum header count exceeded"
            r9.<init>(r10)
            throw r9
        L8a:
            goto Le
        L8b:
            int r9 = r0.size()
            com.good.gd.apache.http.Header[] r9 = new com.good.gd.apache.http.Header[r9]
        L91:
            int r10 = r0.size()
            if (r6 >= r10) goto Lb1
            java.lang.Object r10 = r0.get(r6)
            com.good.gd.apache.http.util.CharArrayBuffer r10 = (com.good.gd.apache.http.util.CharArrayBuffer) r10
            com.good.gd.apache.http.Header r10 = r12.parseHeader(r10)     // Catch: com.good.gd.apache.http.ParseException -> La6
            r9[r6] = r10     // Catch: com.good.gd.apache.http.ParseException -> La6
            int r6 = r6 + 1
            goto L91
        La6:
            r9 = move-exception
            com.good.gd.apache.http.ProtocolException r10 = new com.good.gd.apache.http.ProtocolException
            java.lang.String r9 = r9.getMessage()
            r10.<init>(r9)
            throw r10
        Lb1:
            return r9
        Lb2:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Session input buffer may not be null"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.impl.io.AbstractMessageParser.parseHeaders(com.good.gd.apache.http.io.SessionInputBuffer, int, int, com.good.gd.apache.http.message.LineParser):com.good.gd.apache.http.Header[]");
    }

    @Override // com.good.gd.apache.http.io.HttpMessageParser
    public HttpMessage parse() throws IOException, HttpException {
        try {
            HttpMessage parseHead = parseHead(this.sessionBuffer);
            parseHead.setHeaders(parseHeaders(this.sessionBuffer, this.maxHeaderCount, this.maxLineLen, this.lineParser));
            return parseHead;
        } catch (ParseException e) {
            throw new ProtocolException(e.getMessage(), e);
        }
    }

    protected abstract HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) throws IOException, HttpException, ParseException;
}
