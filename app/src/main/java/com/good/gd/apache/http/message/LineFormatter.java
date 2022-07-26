package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public interface LineFormatter {
    CharArrayBuffer appendProtocolVersion(CharArrayBuffer charArrayBuffer, ProtocolVersion protocolVersion);

    CharArrayBuffer formatHeader(CharArrayBuffer charArrayBuffer, Header header);

    CharArrayBuffer formatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine);

    CharArrayBuffer formatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine);
}
