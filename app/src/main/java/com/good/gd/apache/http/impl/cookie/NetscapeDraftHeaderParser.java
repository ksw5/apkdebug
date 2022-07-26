package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.message.BasicHeaderElement;
import com.good.gd.apache.http.message.BasicHeaderValueParser;
import com.good.gd.apache.http.message.ParserCursor;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class NetscapeDraftHeaderParser {
    public static final NetscapeDraftHeaderParser DEFAULT = new NetscapeDraftHeaderParser();
    private static final char[] DELIMITERS = {';'};
    private final BasicHeaderValueParser nvpParser = BasicHeaderValueParser.DEFAULT;

    public HeaderElement parseHeader(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException {
        if (charArrayBuffer != null) {
            if (parserCursor != null) {
                NameValuePair parseNameValuePair = this.nvpParser.parseNameValuePair(charArrayBuffer, parserCursor, DELIMITERS);
                ArrayList arrayList = new ArrayList();
                while (!parserCursor.atEnd()) {
                    arrayList.add(this.nvpParser.parseNameValuePair(charArrayBuffer, parserCursor, DELIMITERS));
                }
                return new BasicHeaderElement(parseNameValuePair.getName(), parseNameValuePair.getValue(), (NameValuePair[]) arrayList.toArray(new NameValuePair[arrayList.size()]));
            }
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        throw new IllegalArgumentException("Char array buffer may not be null");
    }
}
