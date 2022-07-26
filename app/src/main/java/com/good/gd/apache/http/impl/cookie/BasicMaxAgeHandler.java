package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SetCookie;
import java.util.Date;

/* loaded from: classes.dex */
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler {
    @Override // com.good.gd.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException {
        if (setCookie != null) {
            if (str != null) {
                try {
                    int parseInt = Integer.parseInt(str);
                    if (parseInt >= 0) {
                        setCookie.setExpiryDate(new Date(System.currentTimeMillis() + (parseInt * 1000)));
                        return;
                    }
                    throw new MalformedCookieException("Negative max-age attribute: " + str);
                } catch (NumberFormatException e) {
                    throw new MalformedCookieException("Invalid max-age attribute: " + str);
                }
            }
            throw new MalformedCookieException("Missing value for max-age attribute");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }
}
