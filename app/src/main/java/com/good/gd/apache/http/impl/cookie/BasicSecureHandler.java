package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;

/* loaded from: classes.dex */
public class BasicSecureHandler extends AbstractCookieAttributeHandler {
    @Override // com.good.gd.apache.http.impl.cookie.AbstractCookieAttributeHandler, com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin == null) {
                throw new IllegalArgumentException("Cookie origin may not be null");
            }
            return !cookie.isSecure() || cookieOrigin.isSecure();
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            setCookie.setSecure(true);
            return;
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
