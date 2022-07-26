package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;

/* loaded from: classes.dex */
public class BasicCommentHandler extends AbstractCookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            setCookie.setComment(str);
            return;
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
