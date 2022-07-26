package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import com.good.gd.apache.http.cookie.SetCookie2;

@Deprecated
/* loaded from: classes.dex */
public class RFC2965CommentUrlAttributeHandler implements CookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        return true;
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie instanceof SetCookie2) {
            ((SetCookie2) setCookie).setCommentURL(str);
        }
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
    }
}
