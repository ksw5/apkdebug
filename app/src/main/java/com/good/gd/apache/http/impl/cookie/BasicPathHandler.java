package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;

/* loaded from: classes.dex */
public class BasicPathHandler implements CookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                String path = cookieOrigin.getPath();
                String path2 = cookie.getPath();
                if (path2 == null) {
                    path2 = "/";
                }
                if (path2.length() > 1 && path2.endsWith("/")) {
                    path2 = path2.substring(0, path2.length() - 1);
                }
                boolean startsWith = path.startsWith(path2);
                return (!startsWith || path.length() == path2.length() || path2.endsWith("/")) ? startsWith : path.charAt(path2.length()) == '/';
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            if (str == null || str.trim().length() == 0) {
                str = "/";
            }
            setCookie.setPath(str);
            return;
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (match(cookie, cookieOrigin)) {
            return;
        }
        throw new MalformedCookieException("Illegal path attribute \"" + cookie.getPath() + "\". Path of origin: \"" + cookieOrigin.getPath() + "\"");
    }
}
