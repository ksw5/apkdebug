package com.good.gd.apache.http.message;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.params.BasicHttpParams;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public abstract class AbstractHttpMessage implements HttpMessage {
    protected HeaderGroup headergroup;
    protected HttpParams params;

    protected AbstractHttpMessage(HttpParams httpParams) {
        this.headergroup = new HeaderGroup();
        this.params = httpParams;
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void addHeader(Header header) {
        this.headergroup.addHeader(header);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public boolean containsHeader(String str) {
        return this.headergroup.containsHeader(str);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public Header[] getAllHeaders() {
        return this.headergroup.getAllHeaders();
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public Header getFirstHeader(String str) {
        return this.headergroup.getFirstHeader(str);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public Header[] getHeaders(String str) {
        return this.headergroup.getHeaders(str);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public Header getLastHeader(String str) {
        return this.headergroup.getLastHeader(str);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public HttpParams getParams() {
        if (this.params == null) {
            this.params = new BasicHttpParams();
        }
        return this.params;
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public HeaderIterator headerIterator() {
        return this.headergroup.iterator();
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void removeHeader(Header header) {
        this.headergroup.removeHeader(header);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void removeHeaders(String str) {
        if (str == null) {
            return;
        }
        HeaderIterator it = this.headergroup.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(((Header) it.next()).getName())) {
                it.remove();
            }
        }
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void setHeader(Header header) {
        this.headergroup.updateHeader(header);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void setHeaders(Header[] headerArr) {
        this.headergroup.setHeaders(headerArr);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void setParams(HttpParams httpParams) {
        if (httpParams != null) {
            this.params = httpParams;
            return;
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void addHeader(String str, String str2) {
        if (str != null) {
            this.headergroup.addHeader(new BasicHeader(str, str2));
            return;
        }
        throw new IllegalArgumentException("Header name may not be null");
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public HeaderIterator headerIterator(String str) {
        return this.headergroup.iterator(str);
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public void setHeader(String str, String str2) {
        if (str != null) {
            this.headergroup.updateHeader(new BasicHeader(str, str2));
            return;
        }
        throw new IllegalArgumentException("Header name may not be null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractHttpMessage() {
        this(null);
    }
}
