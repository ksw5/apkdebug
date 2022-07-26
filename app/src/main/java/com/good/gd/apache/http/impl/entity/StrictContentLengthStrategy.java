package com.good.gd.apache.http.impl.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.HttpVersion;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.entity.ContentLengthStrategy;
import com.good.gd.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class StrictContentLengthStrategy implements ContentLengthStrategy {
    @Override // com.good.gd.apache.http.entity.ContentLengthStrategy
    public long determineLength(HttpMessage httpMessage) throws HttpException {
        if (httpMessage != null) {
            Header firstHeader = httpMessage.getFirstHeader(HTTP.TRANSFER_ENCODING);
            Header firstHeader2 = httpMessage.getFirstHeader(HTTP.CONTENT_LEN);
            if (firstHeader == null) {
                if (firstHeader2 == null) {
                    return -1L;
                }
                String value = firstHeader2.getValue();
                try {
                    return Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new ProtocolException("Invalid content length: " + value);
                }
            }
            String value2 = firstHeader.getValue();
            if (HTTP.CHUNK_CODING.equalsIgnoreCase(value2)) {
                if (httpMessage.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                    throw new ProtocolException("Chunked transfer encoding not allowed for " + httpMessage.getProtocolVersion());
                }
                return -2L;
            } else if (!HTTP.IDENTITY_CODING.equalsIgnoreCase(value2)) {
                throw new ProtocolException("Unsupported transfer encoding: " + value2);
            } else {
                return -1L;
            }
        }
        throw new IllegalArgumentException("HTTP message may not be null");
    }
}
