package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import java.util.Date;

/* loaded from: classes.dex */
public class BasicExpiresHandler extends AbstractCookieAttributeHandler {
    private final String[] datepatterns;
    private final Log log = LogFactory.getLog(BasicExpiresHandler.class);

    public BasicExpiresHandler(String[] strArr) {
        if (strArr != null) {
            this.datepatterns = strArr;
            return;
        }
        throw new IllegalArgumentException("Array of date patterns may not be null");
    }

    @Override // com.good.gd.apache.http.impl.cookie.AbstractCookieAttributeHandler, com.good.gd.apache.http.cookie.CookieAttributeHandler
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie.isExpired(new Date())) {
            this.log.debug("Cookie is expired");
            return false;
        }
        return true;
    }

    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            if (str != null) {
                try {
                    setCookie.setExpiryDate(DateUtils.parseDate(str, this.datepatterns));
                    return;
                } catch (DateParseException e) {
                    throw new MalformedCookieException("Unable to parse expires attribute: " + str);
                }
            }
            throw new MalformedCookieException("Missing value for expires attribute");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
