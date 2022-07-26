package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class HeaderGroup implements Cloneable {
    private List headers = new ArrayList(16);

    public void addHeader(Header header) {
        if (header == null) {
            return;
        }
        this.headers.add(header);
    }

    public void clear() {
        this.headers.clear();
    }

    public Object clone() throws CloneNotSupportedException {
        HeaderGroup headerGroup = (HeaderGroup) super.clone();
        headerGroup.headers = new ArrayList(this.headers);
        return headerGroup;
    }

    public boolean containsHeader(String str) {
        for (int i = 0; i < this.headers.size(); i++) {
            if (((Header) this.headers.get(i)).getName().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public HeaderGroup copy() {
        HeaderGroup headerGroup = new HeaderGroup();
        headerGroup.headers.addAll(this.headers);
        return headerGroup;
    }

    public Header[] getAllHeaders() {
        List list = this.headers;
        return (Header[]) list.toArray(new Header[list.size()]);
    }

    public Header getCondensedHeader(String str) {
        Header[] headers = getHeaders(str);
        if (headers.length == 0) {
            return null;
        }
        if (headers.length == 1) {
            return headers[0];
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(128);
        charArrayBuffer.append(headers[0].getValue());
        for (int i = 1; i < headers.length; i++) {
            charArrayBuffer.append(", ");
            charArrayBuffer.append(headers[i].getValue());
        }
        return new BasicHeader(str.toLowerCase(Locale.ENGLISH), charArrayBuffer.toString());
    }

    public Header getFirstHeader(String str) {
        for (int i = 0; i < this.headers.size(); i++) {
            Header header = (Header) this.headers.get(i);
            if (header.getName().equalsIgnoreCase(str)) {
                return header;
            }
        }
        return null;
    }

    public Header[] getHeaders(String str) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.headers.size(); i++) {
            Header header = (Header) this.headers.get(i);
            if (header.getName().equalsIgnoreCase(str)) {
                arrayList.add(header);
            }
        }
        return (Header[]) arrayList.toArray(new Header[arrayList.size()]);
    }

    public Header getLastHeader(String str) {
        for (int size = this.headers.size() - 1; size >= 0; size--) {
            Header header = (Header) this.headers.get(size);
            if (header.getName().equalsIgnoreCase(str)) {
                return header;
            }
        }
        return null;
    }

    public HeaderIterator iterator() {
        return new BasicListHeaderIterator(this.headers, null);
    }

    public void removeHeader(Header header) {
        if (header == null) {
            return;
        }
        this.headers.remove(header);
    }

    public void setHeaders(Header[] headerArr) {
        clear();
        if (headerArr == null) {
            return;
        }
        for (Header header : headerArr) {
            this.headers.add(header);
        }
    }

    public void updateHeader(Header header) {
        if (header == null) {
            return;
        }
        for (int i = 0; i < this.headers.size(); i++) {
            if (((Header) this.headers.get(i)).getName().equalsIgnoreCase(header.getName())) {
                this.headers.set(i, header);
                return;
            }
        }
        this.headers.add(header);
    }

    public HeaderIterator iterator(String str) {
        return new BasicListHeaderIterator(this.headers, str);
    }
}
