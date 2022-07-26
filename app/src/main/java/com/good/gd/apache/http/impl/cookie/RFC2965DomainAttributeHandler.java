package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import java.util.Locale;

/* loaded from: classes.dex */
public class RFC2965DomainAttributeHandler implements CookieAttributeHandler {
    public boolean domainMatch(String str, String str2) {
        return str.equals(str2) || (str2.startsWith(".") && str.endsWith(str2));
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String lowerCase = cookieOrigin.getHost().toLowerCase(Locale.ENGLISH);
                String domain = cookie.getDomain();
                return domainMatch(lowerCase, domain) && lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) == -1;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            if (str != null) {
                if (str.trim().length() != 0) {
                    String lowerCase = str.toLowerCase(Locale.ENGLISH);
                    if (!lowerCase.startsWith(".")) {
                        lowerCase = '.' + lowerCase;
                    }
                    setCookie.setDomain(lowerCase);
                    return;
                }
                throw new MalformedCookieException("Blank value for domain attribute");
            }
            throw new MalformedCookieException("Missing value for domain attribute");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String lowerCase = cookieOrigin.getHost().toLowerCase(Locale.ENGLISH);
                if (cookie.getDomain() != null) {
                    String lowerCase2 = cookie.getDomain().toLowerCase(Locale.ENGLISH);
                    if ((cookie instanceof ClientCookie) && ((ClientCookie) cookie).containsAttribute(ClientCookie.DOMAIN_ATTR)) {
                        if (lowerCase2.startsWith(".")) {
                            int indexOf = lowerCase2.indexOf(46, 1);
                            if ((indexOf >= 0 && indexOf != lowerCase2.length() - 1) || lowerCase2.equals(".local")) {
                                if (domainMatch(lowerCase, lowerCase2)) {
                                    if (lowerCase.substring(0, lowerCase.length() - lowerCase2.length()).indexOf(46) != -1) {
                                        throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host minus domain may not contain any dots");
                                    }
                                    return;
                                }
                                throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host name does not domain-match domain attribute.");
                            }
                            throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: the value contains no embedded dots and the value is not .local");
                        }
                        throw new MalformedCookieException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
                    } else if (!cookie.getDomain().equals(lowerCase)) {
                        throw new MalformedCookieException("Illegal domain attribute: \"" + cookie.getDomain() + "\".Domain of origin: \"" + lowerCase + "\"");
                    } else {
                        return;
                    }
                }
                throw new MalformedCookieException("Invalid cookie state: domain not specified");
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
