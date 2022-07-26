package com.good.gd.apache.http.impl.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpMessage;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.ProtocolException;
import com.good.gd.apache.http.entity.ContentLengthStrategy;
import com.good.gd.apache.http.params.CoreProtocolPNames;
import com.good.gd.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public class LaxContentLengthStrategy implements ContentLengthStrategy {
    @Override // com.good.gd.apache.http.entity.ContentLengthStrategy
    public long determineLength(HttpMessage httpMessage) throws HttpException {
        long j;
        Header header;
        if (httpMessage != null) {
            boolean isParameterTrue = httpMessage.getParams().isParameterTrue(CoreProtocolPNames.STRICT_TRANSFER_ENCODING);
            Header firstHeader = httpMessage.getFirstHeader(HTTP.TRANSFER_ENCODING);
            Header firstHeader2 = httpMessage.getFirstHeader(HTTP.CONTENT_LEN);
            if (firstHeader == null) {
                if (firstHeader2 != null) {
                    Header[] headers = httpMessage.getHeaders(HTTP.CONTENT_LEN);
                    if (isParameterTrue && headers.length > 1) {
                        throw new ProtocolException("Multiple content length headers");
                    }
                    int length = headers.length - 1;
                    while (true) {
                        if (length < 0) {
                            j = -1;
                            break;
                        }
                        try {
                            j = Long.parseLong(headers[length].getValue());
                            break;
                        } catch (NumberFormatException e) {
                            if (isParameterTrue) {
                                throw new ProtocolException("Invalid content length: " + header.getValue());
                            }
                            length--;
                        }
                    }
                    if (j >= 0) {
                        return j;
                    }
                }
                return -1L;
            }
            try {
                HeaderElement[] elements = firstHeader.getElements();
                if (isParameterTrue) {
                    for (HeaderElement headerElement : elements) {
                        String name = headerElement.getName();
                        if (name != null && name.length() > 0 && !name.equalsIgnoreCase(HTTP.CHUNK_CODING) && !name.equalsIgnoreCase(HTTP.IDENTITY_CODING)) {
                            throw new ProtocolException("Unsupported transfer encoding: " + name);
                        }
                    }
                }
                int length2 = elements.length;
                if (HTTP.IDENTITY_CODING.equalsIgnoreCase(firstHeader.getValue())) {
                    return -1L;
                }
                if (length2 > 0 && HTTP.CHUNK_CODING.equalsIgnoreCase(elements[length2 - 1].getName())) {
                    return -2L;
                }
                if (isParameterTrue) {
                    throw new ProtocolException("Chunk-encoding must be the last one applied");
                }
                return -1L;
            } catch (ParseException e2) {
                throw new ProtocolException("Invalid Transfer-Encoding header value: " + firstHeader, e2);
            }
        }
        throw new IllegalArgumentException("HTTP message may not be null");
    }
}
