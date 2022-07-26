package com.good.gd.apachehttp.entity;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.ParseException;
import com.good.gd.apache.http.client.utils.URLEncodedUtils;
import com.good.gd.apache.http.message.BasicHeaderValueParser;
import com.good.gd.apache.http.message.HeaderValueParser;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.apachehttp.Consts;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;

/* loaded from: classes.dex */
public final class ContentType {
    public static final ContentType APPLICATION_OCTET_STREAM;
    public static final ContentType DEFAULT_BINARY;
    public static final ContentType DEFAULT_TEXT;
    public static final ContentType TEXT_PLAIN;
    private final Charset charset;
    private final String mimeType;
    public static final ContentType APPLICATION_ATOM_XML = create("application/atom+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_FORM_URLENCODED = create(URLEncodedUtils.CONTENT_TYPE, Consts.ISO_8859_1);
    public static final ContentType APPLICATION_JSON = create("application/json", Consts.UTF_8);
    public static final ContentType APPLICATION_SVG_XML = create("application/svg+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XHTML_XML = create("application/xhtml+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XML = create("application/xml", Consts.ISO_8859_1);
    public static final ContentType MULTIPART_FORM_DATA = create("multipart/form-data", Consts.ISO_8859_1);
    public static final ContentType TEXT_HTML = create("text/html", Consts.ISO_8859_1);
    public static final ContentType TEXT_XML = create("text/xml", Consts.ISO_8859_1);
    public static final ContentType WILDCARD = create("*/*", (Charset) null);

    static {
        ContentType create = create("application/octet-stream", (Charset) null);
        APPLICATION_OCTET_STREAM = create;
        ContentType create2 = create(HTTP.PLAIN_TEXT_TYPE, Consts.ISO_8859_1);
        TEXT_PLAIN = create2;
        DEFAULT_TEXT = create2;
        DEFAULT_BINARY = create;
    }

    ContentType(String str, Charset charset) {
        this.mimeType = str;
        this.charset = charset;
    }

    public static ContentType create(String str, Charset charset) {
        if (str != null) {
            String lowerCase = str.trim().toLowerCase(Locale.US);
            if (lowerCase.length() != 0) {
                if (valid(lowerCase)) {
                    return new ContentType(lowerCase, charset);
                }
                throw new IllegalArgumentException("MIME type may not contain reserved characters");
            }
            throw new IllegalArgumentException("MIME type may not be empty");
        }
        throw new IllegalArgumentException("MIME type may not be null");
    }

    public static ContentType get(HttpEntity httpEntity) throws ParseException, UnsupportedCharsetException {
        Header contentType;
        if (httpEntity != null && (contentType = httpEntity.getContentType()) != null) {
            HeaderElement[] elements = contentType.getElements();
            if (elements.length > 0) {
                return create(elements[0]);
            }
        }
        return null;
    }

    public static ContentType getOrDefault(HttpEntity httpEntity) throws ParseException {
        ContentType contentType = get(httpEntity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public static ContentType parse(String str) throws ParseException, UnsupportedCharsetException {
        if (str != null) {
            HeaderElement[] parseElements = BasicHeaderValueParser.parseElements(str, (HeaderValueParser) null);
            if (parseElements.length > 0) {
                return create(parseElements[0]);
            }
            throw new ParseException("Invalid content type: " + str);
        }
        throw new IllegalArgumentException("Content type may not be null");
    }

    private static boolean valid(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\"' || charAt == ',' || charAt == ';') {
                return false;
            }
        }
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mimeType);
        if (this.charset != null) {
            sb.append(HTTP.CHARSET_PARAM);
            sb.append(this.charset.name());
        }
        return sb.toString();
    }

    public static ContentType create(String str) {
        return new ContentType(str, null);
    }

    public static ContentType create(String str, String str2) throws UnsupportedCharsetException {
        return create(str, str2 != null ? Charset.forName(str2) : null);
    }

    private static ContentType create(HeaderElement headerElement) {
        String str;
        String name = headerElement.getName();
        NameValuePair parameterByName = headerElement.getParameterByName("charset");
        if (parameterByName == null) {
            str = null;
        } else {
            str = parameterByName.getValue();
        }
        return create(name, str);
    }
}
