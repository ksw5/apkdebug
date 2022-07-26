package com.good.gd.apache.http.message;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public interface HeaderValueParser {
    HeaderElement[] parseElements(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;

    HeaderElement parseHeaderElement(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;

    NameValuePair parseNameValuePair(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;

    NameValuePair[] parseParameters(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException;
}
