package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.HeaderElement;
import com.good.gd.apache.http.NameValuePair;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class CookieSpecBase extends AbstractCookieSpec {
    private static final String TAG = "CookieSpecBase";

    /* JADX INFO: Access modifiers changed from: protected */
    public static String getDefaultDomain(CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String getDefaultPath(CookieOrigin cookieOrigin) {
        String path = cookieOrigin.getPath();
        int lastIndexOf = path.lastIndexOf(47);
        if (lastIndexOf >= 0) {
            if (lastIndexOf == 0) {
                lastIndexOf = 1;
            }
            return path.substring(0, lastIndexOf);
        }
        return path;
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie != null) {
            if (cookieOrigin != null) {
                for (CookieAttributeHandler cookieAttributeHandler : getAttribHandlers()) {
                    if (!cookieAttributeHandler.match(cookie, cookieOrigin)) {
                        return false;
                    }
                }
                return true;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Cookie> parse(HeaderElement[] headerElementArr, CookieOrigin cookieOrigin, String str) throws MalformedCookieException {
        List<Cookie> parse = parse(headerElementArr, cookieOrigin);
        if (parse.size() == 1) {
            BasicClientCookie basicClientCookie = (BasicClientCookie) parse.get(0);
            if (basicClientCookie != null && str != null) {
                basicClientCookie.setOriginalCookieValue(str);
            }
        } else {
            GDLog.DBGPRINTF(13, TAG, "parse(), more then 1 cookie retrieved - " + parse.size());
        }
        return parse;
    }

    @Override // com.good.gd.apache.http.cookie.CookieSpec
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie != null) {
            if (cookieOrigin != null) {
                for (CookieAttributeHandler cookieAttributeHandler : getAttribHandlers()) {
                    cookieAttributeHandler.validate(cookie, cookieOrigin);
                }
                return;
            }
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        throw new IllegalArgumentException("Cookie may not be null");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Cookie> parse(HeaderElement[] headerElementArr, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ArrayList arrayList = new ArrayList(headerElementArr.length);
        for (HeaderElement headerElement : headerElementArr) {
            String name = headerElement.getName();
            String value = headerElement.getValue();
            if (name != null && name.length() != 0) {
                BasicClientCookie basicClientCookie = new BasicClientCookie(name, value);
                basicClientCookie.setPath(getDefaultPath(cookieOrigin));
                basicClientCookie.setDomain(getDefaultDomain(cookieOrigin));
                NameValuePair[] parameters = headerElement.getParameters();
                for (int length = parameters.length - 1; length >= 0; length--) {
                    NameValuePair nameValuePair = parameters[length];
                    String lowerCase = nameValuePair.getName().toLowerCase(Locale.ENGLISH);
                    basicClientCookie.setAttribute(lowerCase, nameValuePair.getValue());
                    CookieAttributeHandler findAttribHandler = findAttribHandler(lowerCase);
                    if (findAttribHandler != null) {
                        findAttribHandler.parse(basicClientCookie, nameValuePair.getValue());
                    }
                }
                arrayList.add(basicClientCookie);
            } else {
                throw new MalformedCookieException("Cookie name may not be empty");
            }
        }
        return arrayList;
    }
}
