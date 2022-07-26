package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import java.util.Locale;

/* loaded from: classes.dex */
public class RFC2109DomainHandler implements CookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String host = cookieOrigin.getHost();
                String domain = cookie.getDomain();
                if (domain == null) {
                    return false;
                }
                return host.equals(domain) || (domain.startsWith(".") && host.endsWith(domain));
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
                    setCookie.setDomain(str);
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
                String host = cookieOrigin.getHost();
                String domain = cookie.getDomain();
                if (domain != null) {
                    if (domain.equals(host)) {
                        return;
                    }
                    if (domain.indexOf(46) != -1) {
                        if (domain.startsWith(".")) {
                            int indexOf = domain.indexOf(46, 1);
                            if (indexOf >= 0 && indexOf != domain.length() - 1) {
                                String lowerCase = host.toLowerCase(Locale.ENGLISH);
                                if (lowerCase.endsWith(domain)) {
                                    if (lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) != -1) {
                                        throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: host minus domain may not contain any dots");
                                    }
                                    return;
                                }
                                throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + lowerCase + "\"");
                            }
                            throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must contain an embedded dot");
                        }
                        throw new MalformedCookieException("Domain attribute \"" + domain + "\" violates RFC 2109: domain must start with a dot");
                    }
                    throw new MalformedCookieException("Domain attribute \"" + domain + "\" does not match the host \"" + host + "\"");
                }
                throw new MalformedCookieException("Cookie domain may not be null");
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
