package com.good.gd.apachehttp.client.cache;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.message.HeaderGroup;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class HttpCacheEntry implements Serializable {
    private static final long serialVersionUID = -6300496422359477413L;
    private final Date requestDate;
    private final Resource resource;
    private final Date responseDate;
    private final HeaderGroup responseHeaders;
    private final StatusLine statusLine;
    private final Map<String, String> variantMap;

    public HttpCacheEntry(Date date, Date date2, StatusLine statusLine, Header[] headerArr, Resource resource, Map<String, String> map) {
        if (date != null) {
            if (date2 == null) {
                throw new IllegalArgumentException("Response date may not be null");
            }
            if (statusLine == null) {
                throw new IllegalArgumentException("Status line may not be null");
            }
            if (headerArr == null) {
                throw new IllegalArgumentException("Response headers may not be null");
            }
            if (resource != null) {
                this.requestDate = date;
                this.responseDate = date2;
                this.statusLine = statusLine;
                HeaderGroup headerGroup = new HeaderGroup();
                this.responseHeaders = headerGroup;
                headerGroup.setHeaders(headerArr);
                this.resource = resource;
                this.variantMap = map != null ? new HashMap(map) : null;
                return;
            }
            throw new IllegalArgumentException("Resource may not be null");
        }
        throw new IllegalArgumentException("Request date may not be null");
    }

    public Header[] getAllHeaders() {
        return this.responseHeaders.getAllHeaders();
    }

    public Header getFirstHeader(String str) {
        return this.responseHeaders.getFirstHeader(str);
    }

    public Header[] getHeaders(String str) {
        return this.responseHeaders.getHeaders(str);
    }

    public ProtocolVersion getProtocolVersion() {
        return this.statusLine.getProtocolVersion();
    }

    public String getReasonPhrase() {
        return this.statusLine.getReasonPhrase();
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public Resource getResource() {
        return this.resource;
    }

    public Date getResponseDate() {
        return this.responseDate;
    }

    public int getStatusCode() {
        return this.statusLine.getStatusCode();
    }

    public StatusLine getStatusLine() {
        return this.statusLine;
    }

    public Map<String, String> getVariantMap() {
        Map<String, String> map = this.variantMap;
        if (map == null) {
            return null;
        }
        return Collections.unmodifiableMap(map);
    }

    public boolean hasVariants() {
        return getFirstHeader("Vary") != null;
    }

    public String toString() {
        return "[request date=" + this.requestDate + "; response date=" + this.responseDate + "; statusLine=" + this.statusLine + "]";
    }

    public HttpCacheEntry(Date date, Date date2, StatusLine statusLine, Header[] headerArr, Resource resource) {
        this(date, date2, statusLine, headerArr, resource, new HashMap());
    }
}
