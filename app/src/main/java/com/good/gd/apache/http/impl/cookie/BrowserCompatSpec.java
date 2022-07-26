package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.SM;
import com.good.gd.apache.http.message.BufferedHeader;
import com.good.gd.apache.http.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class BrowserCompatSpec extends CookieSpecBase {
    protected static final String[] DATE_PATTERNS = {"EEE, dd MMM yyyy HH:mm:ss zzz", DateUtils.PATTERN_RFC1036, DateUtils.PATTERN_ASCTIME, "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};
    private final String[] datepatterns;

    public BrowserCompatSpec(String[] strArr) {
        if (strArr != null) {
            this.datepatterns = (String[]) strArr.clone();
        } else {
            this.datepatterns = DATE_PATTERNS;
        }
        registerAttribHandler(ClientCookie.PATH_ATTR, new BasicPathHandler());
        registerAttribHandler(ClientCookie.DOMAIN_ATTR, new BasicDomainHandler());
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
                    charArrayBuffer.append("=");
                    String value = cookie.getValue();
                    if (value != null) {
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007d  */
    @Override // com.good.gd.apache.http.cookie.CookieSpec
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public List<Cookie> parse(Header r8, com.good.gd.apache.http.cookie.CookieOrigin r9) throws com.good.gd.apache.http.cookie.MalformedCookieException {
        /*
            r7 = this;
            if (r8 == 0) goto L8e
            if (r9 == 0) goto L86
            java.lang.String r0 = r8.getValue()
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r1 = r0.toLowerCase(r1)
            java.lang.String r2 = "expires="
            int r1 = r1.indexOf(r2)
            r2 = 1
            r3 = -1
            r4 = 0
            if (r1 == r3) goto L33
            int r1 = r1 + 8
            r5 = 59
            int r5 = r0.indexOf(r5, r1)
            if (r5 != r3) goto L27
            int r5 = r0.length()
        L27:
            java.lang.String r1 = r0.substring(r1, r5)     // Catch: com.good.gd.apache.http.impl.cookie.DateParseException -> L32
            java.lang.String[] r3 = r7.datepatterns     // Catch: com.good.gd.apache.http.impl.cookie.DateParseException -> L32
            com.good.gd.apache.http.impl.cookie.DateUtils.parseDate(r1, r3)     // Catch: com.good.gd.apache.http.impl.cookie.DateParseException -> L32
            r1 = r2
            goto L34
        L32:
            r1 = move-exception
        L33:
            r1 = r4
        L34:
            if (r1 == 0) goto L7d
            com.good.gd.apache.http.impl.cookie.NetscapeDraftHeaderParser r1 = com.good.gd.apache.http.impl.cookie.NetscapeDraftHeaderParser.DEFAULT
            boolean r3 = r8 instanceof com.good.gd.apache.http.FormattedHeader
            if (r3 == 0) goto L50
            com.good.gd.apache.http.FormattedHeader r8 = (com.good.gd.apache.http.FormattedHeader) r8
            com.good.gd.apache.http.util.CharArrayBuffer r3 = r8.getBuffer()
            com.good.gd.apache.http.message.ParserCursor r5 = new com.good.gd.apache.http.message.ParserCursor
            int r8 = r8.getValuePos()
            int r6 = r3.length()
            r5.<init>(r8, r6)
            goto L6b
        L50:
            java.lang.String r8 = r8.getValue()
            if (r8 == 0) goto L75
            com.good.gd.apache.http.util.CharArrayBuffer r3 = new com.good.gd.apache.http.util.CharArrayBuffer
            int r5 = r8.length()
            r3.<init>(r5)
            r3.append(r8)
            com.good.gd.apache.http.message.ParserCursor r5 = new com.good.gd.apache.http.message.ParserCursor
            int r8 = r3.length()
            r5.<init>(r4, r8)
        L6b:
            com.good.gd.apache.http.HeaderElement[] r8 = new com.good.gd.apache.http.HeaderElement[r2]
            com.good.gd.apache.http.HeaderElement r1 = r1.parseHeader(r3, r5)
            r8[r4] = r1
            goto L81
        L75:
            com.good.gd.apache.http.cookie.MalformedCookieException r8 = new com.good.gd.apache.http.cookie.MalformedCookieException
            java.lang.String r9 = "Header value is null"
            r8.<init>(r9)
            throw r8
        L7d:
            com.good.gd.apache.http.HeaderElement[] r8 = r8.getElements()
        L81:
            java.util.List r8 = r7.parse(r8, r9, r0)
            return r8
        L86:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "Cookie origin may not be null"
            r8.<init>(r9)
            throw r8
        L8e:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "Header may not be null"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.impl.cookie.BrowserCompatSpec.parse(com.good.gd.apache.http.Header, com.good.gd.apache.http.cookie.CookieOrigin):java.util.List");
    }

    public BrowserCompatSpec() {
        this(null);
    }
}
