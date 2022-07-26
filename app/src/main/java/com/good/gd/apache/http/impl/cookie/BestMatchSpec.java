package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.CookieSpec;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import java.util.List;

/* loaded from: classes.dex */
public class BestMatchSpec implements CookieSpec {
    private BrowserCompatSpec compat;
    private final String[] datepatterns;
    private NetscapeDraftSpec netscape;
    private final boolean oneHeader;
    private RFC2965Spec strict;

    public BestMatchSpec(String[] strArr, boolean z) {
        this.datepatterns = strArr;
        this.oneHeader = z;
    }

    private BrowserCompatSpec getCompat() {
        if (this.compat == null) {
            this.compat = new BrowserCompatSpec(this.datepatterns);
        }
        return this.compat;
    }

    private NetscapeDraftSpec getNetscape() {
        if (this.netscape == null) {
            String[] strArr = this.datepatterns;
            if (strArr == null) {
                strArr = BrowserCompatSpec.DATE_PATTERNS;
            }
            this.netscape = new NetscapeDraftSpec(strArr);
        }
        return this.netscape;
    }

    private RFC2965Spec getStrict() {
        if (this.strict == null) {
            this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
        }
        return this.strict;
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        if (list != null) {
            int i = Integer.MAX_VALUE;
            for (Cookie cookie : list) {
                if (cookie.getVersion() < i) {
                    i = cookie.getVersion();
                }
            }
            if (i > 0) {
                return getStrict().formatCookies(list);
            }
            return getCompat().formatCookies(list);
        }
        throw new IllegalArgumentException("List of cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public int getVersion() {
        return getStrict().getVersion();
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public Header getVersionHeader() {
        return getStrict().getVersionHeader();
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                if (cookie.getVersion() > 0) {
                    return getStrict().match(cookie, cookieOrigin);
                }
                return getCompat().match(cookie, cookieOrigin);
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (header != null) {
            if (cookieOrigin != null) {
                HeaderElement[] elements = header.getElements();
                boolean z = false;
                boolean z2 = false;
                for (HeaderElement headerElement : elements) {
                    if (headerElement.getParameterByName("version") != null) {
                        z = true;
                    }
                    if (headerElement.getParameterByName(ClientCookie.EXPIRES_ATTR) != null) {
                        z2 = true;
                    }
                }
                if (z) {
                    return getStrict().parse(elements, cookieOrigin);
                }
                if (z2) {
                    return getNetscape().parse(header, cookieOrigin);
                }
                return getCompat().parse(elements, cookieOrigin);
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Header may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if (cookieOrigin != null) {
                if (cookie.getVersion() > 0) {
                    getStrict().validate(cookie, cookieOrigin);
                    return;
                } else {
                    getCompat().validate(cookie, cookieOrigin);
                    return;
                }
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    public BestMatchSpec() {
        this(null, false);
    }
}
