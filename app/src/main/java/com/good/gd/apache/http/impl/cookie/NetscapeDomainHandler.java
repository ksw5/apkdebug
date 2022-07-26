package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import java.util.Locale;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public class NetscapeDomainHandler extends BasicDomainHandler {
    private static boolean isSpecialDomain(String str) {
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        return upperCase.endsWith(".COM") || upperCase.endsWith(".EDU") || upperCase.endsWith(".NET") || upperCase.endsWith(".GOV") || upperCase.endsWith(".MIL") || upperCase.endsWith(".ORG") || upperCase.endsWith(".INT");
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicDomainHandler, com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String host = cookieOrigin.getHost();
                String domain = cookie.getDomain();
                if (domain != null) {
                    return host.endsWith(domain);
                }
                return false;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.impl.cookie.BasicDomainHandler, com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        super.validate(cookie, cookieOrigin);
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (host.contains(".")) {
            int countTokens = new StringTokenizer(domain, ".").countTokens();
            if (isSpecialDomain(domain)) {
                if (countTokens < 2) {
                    throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification for special domains");
                }
            } else if (countTokens < 3) {
                throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates the Netscape cookie specification");
            }
        }
    }
}
