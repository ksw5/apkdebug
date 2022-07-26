package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieIdentityComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class BasicCookieStore implements CookieStore {
    private final ArrayList<Cookie> cookies = new ArrayList<>();
    private final Comparator<Cookie> cookieComparator = new CookieIdentityComparator();

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized void addCookie(Cookie cookie) {
        if (cookie != null) {
            Iterator<Cookie> it = this.cookies.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (this.cookieComparator.compare(cookie, it.next()) == 0) {
                        it.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!cookie.isExpired(new Date())) {
                this.cookies.add(cookie);
            }
        }
    }

    public synchronized void addCookies(Cookie[] cookieArr) {
        if (cookieArr != null) {
            for (Cookie cookie : cookieArr) {
                addCookie(cookie);
            }
        }
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized void clear() {
        this.cookies.clear();
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized boolean clearExpired(Date date) {
        boolean z = false;
        if (date == null) {
            return false;
        }
        Iterator<Cookie> it = this.cookies.iterator();
        while (it.hasNext()) {
            if (it.next().isExpired(date)) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized List<Cookie> getCookies() {
        return Collections.unmodifiableList(this.cookies);
    }

    public String toString() {
        return this.cookies.toString();
    }
}
