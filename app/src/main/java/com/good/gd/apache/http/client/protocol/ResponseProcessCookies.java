package com.good.gd.apache.http.client.protocol;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HeaderIterator;
import com.good.gd.apache.http.HttpException;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseInterceptor;
import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieOrigin;
import com.good.gd.apache.http.cookie.CookieSpec;
import com.good.gd.apache.http.cookie.MalformedCookieException;
import com.good.gd.apache.http.cookie.SM;
import com.good.gd.apache.http.protocol.HttpContext;
import java.io.IOException;

/* loaded from: classes.dex */
public class ResponseProcessCookies implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(ResponseProcessCookies.class);

    private String cookieToString(Cookie cookie) {
        return cookie.getClass().getSimpleName() + "[version=" + cookie.getVersion() + ",name=" + cookie.getName() + ",domain=" + cookie.getDomain() + ",path=" + cookie.getPath() + ",expiry=" + cookie.getExpiryDate() + "]";
    }

    private void processCookies(HeaderIterator headerIterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore) {
        while (headerIterator.hasNext()) {
            Header nextHeader = headerIterator.nextHeader();
            try {
                for (Cookie cookie : cookieSpec.parse(nextHeader, cookieOrigin)) {
                    try {
                        cookieSpec.validate(cookie, cookieOrigin);
                        cookieStore.addCookie(cookie);
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Cookie accepted: \"" + cookieToString(cookie) + "\". ");
                        }
                    } catch (MalformedCookieException e) {
                        if (this.log.isWarnEnabled()) {
                            this.log.warn("Cookie rejected: \"" + cookieToString(cookie) + "\". " + e.getMessage());
                        }
                    }
                }
            } catch (MalformedCookieException e2) {
                if (this.log.isWarnEnabled()) {
                    this.log.warn("Invalid cookie header: \"" + nextHeader + "\". " + e2.getMessage());
                }
            }
        }
    }

    @Override // com.good.gd.apache.http.HttpResponseInterceptor
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse != null) {
            if (httpContext != null) {
                CookieStore cookieStore = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
                if (cookieStore == null) {
                    this.log.info("Cookie store not available in HTTP context");
                    return;
                }
                CookieSpec cookieSpec = (CookieSpec) httpContext.getAttribute(ClientContext.COOKIE_SPEC);
                if (cookieSpec == null) {
                    this.log.info("CookieSpec not available in HTTP context");
                    return;
                }
                CookieOrigin cookieOrigin = (CookieOrigin) httpContext.getAttribute(ClientContext.COOKIE_ORIGIN);
                if (cookieOrigin == null) {
                    this.log.info("CookieOrigin not available in HTTP context");
                    return;
                }
                processCookies(httpResponse.headerIterator(SM.SET_COOKIE), cookieSpec, cookieOrigin, cookieStore);
                if (cookieSpec.getVersion() <= 0) {
                    return;
                }
                processCookies(httpResponse.headerIterator(SM.SET_COOKIE2), cookieSpec, cookieOrigin, cookieStore);
                return;
            }
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        throw new IllegalArgumentException("HTTP request may not be null");
    }
}
