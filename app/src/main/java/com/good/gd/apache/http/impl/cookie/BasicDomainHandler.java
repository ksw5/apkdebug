package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;

/* loaded from: classes.dex */
public class BasicDomainHandler implements CookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String host = cookieOrigin.getHost();
                String domain = cookie.getDomain();
                if (domain == null) {
                    return false;
                }
                if (host.equals(domain)) {
                    return true;
                }
                if (!domain.startsWith(".")) {
                    domain = '.' + domain;
                }
                return host.endsWith(domain) || host.equals(domain.substring(1));
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
                    if (host.contains(".")) {
                        if (host.endsWith(domain)) {
                            return;
                        }
                        if (domain.startsWith(".")) {
                            domain = domain.substring(1, domain.length());
                        }
                        if (!host.equals(domain)) {
                            throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
                        }
                        return;
                    } else if (!host.equals(domain)) {
                        throw new MalformedCookieException("Illegal domain attribute \"" + domain + "\". Domain of origin: \"" + host + "\"");
                    } else {
                        return;
                    }
                }
                throw new MalformedCookieException("Cookie domain may not be null");
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
