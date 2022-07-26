package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;

/* loaded from: classes.dex */
public class RFC2109VersionHandler extends AbstractCookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            if (str != null) {
                if (str.trim().length() != 0) {
                    try {
                        setCookie.setVersion(Integer.parseInt(str));
                        return;
                    } catch (NumberFormatException e) {
                        throw new MalformedCookieException("Invalid version: " + e.getMessage());
                    }
                }
                throw new MalformedCookieException("Blank value for version attribute");
            }
            throw new MalformedCookieException("Missing value for version attribute");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.impl.cookie.AbstractCookieAttributeHandler, com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if (cookie.getVersion() < 0) {
                throw new MalformedCookieException("Cookie version may not be negative");
            }
            return;
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
