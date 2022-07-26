package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public interface LineParser {
    boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor);

    Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException;

    ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;

    RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;

    StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;
}
