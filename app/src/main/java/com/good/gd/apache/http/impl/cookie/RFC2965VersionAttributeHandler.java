package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.ClientCookie;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import com.good.gd.apache.http.cookie.SetCookie2;

/* loaded from: classes.dex */
public class RFC2965VersionAttributeHandler implements CookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        return true;
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        int i;
        if (setCookie != null) {
            if (str != null) {
                try {
                    i = Integer.parseInt(str);
                } catch (NumberFormatException e) {
                    i = -1;
                }
                if (i >= 0) {
                    setCookie.setVersion(i);
                    return;
                }
                throw new MalformedCookieException("Invalid cookie version.");
            }
            throw new MalformedCookieException("Missing value for version attribute");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if ((cookie instanceof SetCookie2) && (cookie instanceof ClientCookie) && !((ClientCookie) cookie).containsAttribute("version")) {
                throw new MalformedCookieException("Violates RFC 2965. Version attribute is required.");
            }
            return;
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
