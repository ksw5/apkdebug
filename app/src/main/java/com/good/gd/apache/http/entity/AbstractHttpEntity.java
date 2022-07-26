package com.good.gd.apache.http.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.message.BasicHeader;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractHttpEntity implements HttpEntity {
    protected boolean chunked;
    protected Header contentEncoding;
    protected Header contentType;

    @Override // com.good.gd.apache.http.HttpEntity
    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (!isStreaming()) {
            return;
        }
        throw new UnsupportedOperationException("streaming entity does not implement consumeContent()");
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public Header getContentType() {
        return this.contentType;
    }

    @Override // com.good.gd.apache.http.HttpEntity
    public boolean isChunked() {
        return this.chunked;
    }

    public void setChunked(boolean z) {
        this.chunked = z;
    }

    public void setContentEncoding(Header header) {
        this.contentEncoding = header;
    }

    public void setContentType(Header header) {
        this.contentType = header;
    }

    public void setContentEncoding(String str) {
        setContentEncoding(str != null ? new BasicHeader(HTTP.CONTENT_ENCODING, str) : null);
    }

    public void setContentType(String str) {
        setContentType(str != null ? new BasicHeader(HTTP.CONTENT_TYPE, str) : null);
    }
}
