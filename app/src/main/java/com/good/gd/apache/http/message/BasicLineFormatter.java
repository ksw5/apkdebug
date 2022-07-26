package com.good.gd.apache.http.message;

import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.RequestLine;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.util.CharArrayBuffer;
import com.good.gd.ndkproxy.file.RandomAccessFileImpl;

/* loaded from: classes.dex */
public class BasicLineFormatter implements LineFormatter {
    public static final BasicLineFormatter DEFAULT = new BasicLineFormatter();

    public static final String formatHeader(Header header, LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = DEFAULT;
        }
        return lineFormatter.formatHeader(null, header).toString();
    }

    public static final String formatProtocolVersion(ProtocolVersion protocolVersion, LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = DEFAULT;
        }
        return lineFormatter.appendProtocolVersion(null, protocolVersion).toString();
    }

    public static final String formatRequestLine(RequestLine requestLine, LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = DEFAULT;
        }
        return lineFormatter.formatRequestLine(null, requestLine).toString();
    }

    public static final String formatStatusLine(StatusLine statusLine, LineFormatter lineFormatter) {
        if (lineFormatter == null) {
            lineFormatter = DEFAULT;
        }
        return lineFormatter.formatStatusLine(null, statusLine).toString();
    }

    @Override // com.good.gd.apache.http.message.LineFormatter
    public CharArrayBuffer appendProtocolVersion(CharArrayBuffer charArrayBuffer, ProtocolVersion protocolVersion) {
        if (protocolVersion != null) {
            int estimateProtocolVersionLen = estimateProtocolVersionLen(protocolVersion);
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(estimateProtocolVersionLen);
            } else {
                charArrayBuffer.ensureCapacity(estimateProtocolVersionLen);
            }
            charArrayBuffer.append(protocolVersion.getProtocol());
            charArrayBuffer.append(RandomAccessFileImpl.separatorChar);
            charArrayBuffer.append(Integer.toString(protocolVersion.getMajor()));
            charArrayBuffer.append('.');
            charArrayBuffer.append(Integer.toString(protocolVersion.getMinor()));
            return charArrayBuffer;
        }
        throw new IllegalArgumentException("Protocol version may not be null");
    }

    protected void doFormatHeader(CharArrayBuffer charArrayBuffer, Header header) {
        String name = header.getName();
        String value = header.getValue();
        int length = name.length() + 2;
        if (value != null) {
            length += value.length();
        }
        charArrayBuffer.ensureCapacity(length);
        charArrayBuffer.append(name);
        charArrayBuffer.append(": ");
        if (value != null) {
            charArrayBuffer.append(value);
        }
    }

    protected void doFormatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        String method = requestLine.getMethod();
        String uri = requestLine.getUri();
        charArrayBuffer.ensureCapacity(method.length() + 1 + uri.length() + 1 + estimateProtocolVersionLen(requestLine.getProtocolVersion()));
        charArrayBuffer.append(method);
        charArrayBuffer.append(' ');
        charArrayBuffer.append(uri);
        charArrayBuffer.append(' ');
        appendProtocolVersion(charArrayBuffer, requestLine.getProtocolVersion());
    }

    protected void doFormatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        int estimateProtocolVersionLen = estimateProtocolVersionLen(statusLine.getProtocolVersion()) + 1 + 3 + 1;
        String reasonPhrase = statusLine.getReasonPhrase();
        if (reasonPhrase != null) {
            estimateProtocolVersionLen += reasonPhrase.length();
        }
        charArrayBuffer.ensureCapacity(estimateProtocolVersionLen);
        appendProtocolVersion(charArrayBuffer, statusLine.getProtocolVersion());
        charArrayBuffer.append(' ');
        charArrayBuffer.append(Integer.toString(statusLine.getStatusCode()));
        charArrayBuffer.append(' ');
        if (reasonPhrase != null) {
            charArrayBuffer.append(reasonPhrase);
        }
    }

    protected int estimateProtocolVersionLen(ProtocolVersion protocolVersion) {
        return protocolVersion.getProtocol().length() + 4;
    }

    protected CharArrayBuffer initBuffer(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer != null) {
            charArrayBuffer.clear();
            return charArrayBuffer;
        }
        return new CharArrayBuffer(64);
    }

    @Override // com.good.gd.apache.http.message.LineFormatter
    public CharArrayBuffer formatHeader(CharArrayBuffer charArrayBuffer, Header header) {
        if (header != null) {
            if (header instanceof FormattedHeader) {
                return ((FormattedHeader) header).getBuffer();
            }
            CharArrayBuffer initBuffer = initBuffer(charArrayBuffer);
            doFormatHeader(initBuffer, header);
            return initBuffer;
        }
        throw new IllegalArgumentException("Header may not be null");
    }

    @Override // com.good.gd.apache.http.message.LineFormatter
    public CharArrayBuffer formatRequestLine(CharArrayBuffer charArrayBuffer, RequestLine requestLine) {
        if (requestLine != null) {
            CharArrayBuffer initBuffer = initBuffer(charArrayBuffer);
            doFormatRequestLine(initBuffer, requestLine);
            return initBuffer;
        }
        throw new IllegalArgumentException("Request line may not be null");
    }

    @Override // com.good.gd.apache.http.message.LineFormatter
    public CharArrayBuffer formatStatusLine(CharArrayBuffer charArrayBuffer, StatusLine statusLine) {
        if (statusLine != null) {
            CharArrayBuffer initBuffer = initBuffer(charArrayBuffer);
            doFormatStatusLine(initBuffer, statusLine);
            return initBuffer;
        }
        throw new IllegalArgumentException("Status line may not be null");
    }
}
