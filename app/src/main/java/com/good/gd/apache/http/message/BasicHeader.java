package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.util.CharArrayBuffer;

/* loaded from: classes.dex */
public class BasicHeader implements Header, Cloneable {
    private final String name;
    private final String value;

    public BasicHeader(String str, String str2) {
        if (str != null) {
            this.name = str;
            this.value = str2;
            return;
        }
        throw new IllegalArgumentException("Name may not be null");
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // com.good.gd.apache.http.Header
    public HeaderElement[] getElements() throws ParseException {
        String str = this.value;
        if (str != null) {
            return BasicHeaderValueParser.parseElements(str, (HeaderValueParser) null);
        }
        return new HeaderElement[0];
    }

    @Override // com.good.gd.apache.http.Header
    public String getName() {
        return this.name;
    }

    @Override // com.good.gd.apache.http.Header
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatHeader((CharArrayBuffer) null, this).toString();
    }
}
