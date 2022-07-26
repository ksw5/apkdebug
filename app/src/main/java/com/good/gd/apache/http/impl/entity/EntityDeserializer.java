package com.good.gd.apache.http.impl.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.entity.BasicHttpEntity;
import com.good.gd.apache.http.entity.ContentLengthStrategy;
import com.good.gd.apache.http.impl.io.ChunkedInputStream;
import com.good.gd.apache.http.impl.io.ContentLengthInputStream;
import com.good.gd.apache.http.impl.io.IdentityInputStream;
import com.good.gd.apache.http.io.SessionInputBuffer;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.IOException;

/* loaded from: classes.dex */
public class EntityDeserializer {
    private final ContentLengthStrategy lenStrategy;

    public EntityDeserializer(ContentLengthStrategy contentLengthStrategy) {
        if (contentLengthStrategy != null) {
            this.lenStrategy = contentLengthStrategy;
            return;
        }
        throw new IllegalArgumentException("Content length strategy may not be null");
    }

    public HttpEntity deserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        if (sessionInputBuffer != null) {
            if (httpMessage != null) {
                return doDeserialize(sessionInputBuffer, httpMessage);
            }
            throw new IllegalArgumentException("HTTP message may not be null");
        }
        throw new IllegalArgumentException("Session input buffer may not be null");
    }

    protected BasicHttpEntity doDeserialize(SessionInputBuffer sessionInputBuffer, HttpMessage httpMessage) throws HttpException, IOException {
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        long determineLength = this.lenStrategy.determineLength(httpMessage);
        if (determineLength == -2) {
            basicHttpEntity.setChunked(true);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new ChunkedInputStream(sessionInputBuffer));
        } else if (determineLength == -1) {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(-1L);
            basicHttpEntity.setContent(new IdentityInputStream(sessionInputBuffer));
        } else {
            basicHttpEntity.setChunked(false);
            basicHttpEntity.setContentLength(determineLength);
            basicHttpEntity.setContent(new ContentLengthInputStream(sessionInputBuffer, determineLength));
        }
        Header firstHeader = httpMessage.getFirstHeader(HTTP.CONTENT_TYPE);
        if (firstHeader != null) {
            basicHttpEntity.setContentType(firstHeader);
        }
        Header firstHeader2 = httpMessage.getFirstHeader(HTTP.CONTENT_ENCODING);
        if (firstHeader2 != null) {
            basicHttpEntity.setContentEncoding(firstHeader2);
        }
        return basicHttpEntity;
    }
}
