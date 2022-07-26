package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.FormattedHeader;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SM;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.message.ParserCursor;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class NetscapeDraftSpec extends CookieSpecBase {
    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    private final String[] datepatterns;

    public NetscapeDraftSpec(String[] strArr) {
        if (strArr != null) {
            this.datepatterns = (String[]) strArr.clone();
        } else {
            this.datepatterns = new String[]{EXPIRES_PATTERN};
        }
        registerAttribHandler(ClientCookie.PATH_ATTR, new BasicPathHandler());
        registerAttribHandler(ClientCookie.DOMAIN_ATTR, new NetscapeDomainHandler());
        registerAttribHandler(ClientCookie.MAX_AGE_ATTR, new BasicMaxAgeHandler());
        registerAttribHandler(ClientCookie.SECURE_ATTR, new BasicSecureHandler());
        registerAttribHandler(ClientCookie.COMMENT_ATTR, new BasicCommentHandler());
        registerAttribHandler(ClientCookie.EXPIRES_ATTR, new BasicExpiresHandler(this.datepatterns));
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 20);
                charArrayBuffer.append(SM.COOKIE);
                charArrayBuffer.append(": ");
                for (int i = 0; i < list.size(); i++) {
                    Cookie cookie = list.get(i);
                    if (i > 0) {
                        charArrayBuffer.append("; ");
                    }
                    charArrayBuffer.append(cookie.getName());
                    String value = cookie.getValue();
                    if (value != null) {
                        charArrayBuffer.append("=");
                        charArrayBuffer.append(value);
                    }
                }
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(new BufferedHeader(charArrayBuffer));
                return arrayList;
            }
            throw new IllegalArgumentException("List of cookies may not be empty");
        }
        throw new IllegalArgumentException("List of cookies may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public int getVersion() {
        return 0;
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public Header getVersionHeader() {
        return null;
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        CharArrayBuffer charArrayBuffer;
        ParserCursor parserCursor;
        if (header != null) {
            if (cookieOrigin != null) {
                NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
                if (header instanceof FormattedHeader) {
                    FormattedHeader formattedHeader = (FormattedHeader) header;
                    charArrayBuffer = formattedHeader.getBuffer();
                    parserCursor = new ParserCursor(formattedHeader.getValuePos(), charArrayBuffer.length());
                } else {
                    String value = header.getValue();
                    if (value != null) {
                        charArrayBuffer = new CharArrayBuffer(value.length());
                        charArrayBuffer.append(value);
                        parserCursor = new ParserCursor(0, charArrayBuffer.length());
                    } else {
                        throw new MalformedCookieException("Header value is null");
                    }
                }
                return parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor)}, cookieOrigin);
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Header may not be null");
    }

    public NetscapeDraftSpec() {
        this(null);
    }
}
