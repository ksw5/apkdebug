package com.good.gd.apachehttp.impl.client;

import com.good.gd.apache.http.client.CookieStore;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieIdentityComparator;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class GDPersistentCookieStore implements CookieStore {
    private static GDHttpClientDatabase _db = GDHttpClientDatabase.wxau();
    private static GDPersistentCookieStore _instance = null;
    private final CookieIdentityComparator cookieComparator;
    private TreeSet<Cookie> cookies;
    private boolean cacheInMemoryToo = false;
    private final String TAG = "GDPersistentCookieStore";

    public GDPersistentCookieStore() {
        CookieIdentityComparator cookieIdentityComparator = new CookieIdentityComparator();
        this.cookieComparator = cookieIdentityComparator;
        GDLog.DBGPRINTF(16, "GDPersistentCookieStore::GDPersistentCookieStore() IN\n");
        try {
            TreeSet<Cookie> treeSet = new TreeSet<>(cookieIdentityComparator);
            this.cookies = treeSet;
            if (this.cacheInMemoryToo) {
                _db.dbjc(treeSet);
                clearExpired(new Date());
            }
        } catch (Exception e) {
            LogUtils.logStackTrace(e);
        }
        GDLog.DBGPRINTF(16, "GDPersistentCookieStore::GDPersistentCookieStore() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized GDPersistentCookieStore getInstance() {
        GDPersistentCookieStore gDPersistentCookieStore;
        synchronized (GDPersistentCookieStore.class) {
            if (_instance == null) {
                _instance = new GDPersistentCookieStore();
            }
            gDPersistentCookieStore = _instance;
        }
        return gDPersistentCookieStore;
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.cookies.remove(cookie);
            _db.dbjc(cookie, this.cookieComparator);
            if (!cookie.isPersistent()) {
                this.cookies.add(cookie);
            } else if (!cookie.isExpired(new Date())) {
                if (this.cacheInMemoryToo) {
                    this.cookies.add(cookie);
                }
                _db.dbjc(cookie);
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
        _db.dbjc();
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized boolean clearExpired(Date date) {
        boolean z = false;
        if (date == null) {
            return false;
        }
        if (this.cacheInMemoryToo) {
            Iterator<Cookie> it = this.cookies.iterator();
            while (it.hasNext()) {
                Cookie next = it.next();
                if (next.isPersistent() && next.isExpired(date)) {
                    _db.dbjc(next, this.cookieComparator);
                    it.remove();
                    z = true;
                }
            }
        } else {
            _db.dbjc(date);
        }
        return z;
    }

    public synchronized void closeDb() {
        _db.qkduk();
        if (this.cacheInMemoryToo) {
            this.cookies.clear();
        }
    }

    @Override // com.good.gd.apache.http.client.CookieStore
    public synchronized List<Cookie> getCookies() {
        ArrayList arrayList;
        if (this.cacheInMemoryToo) {
            arrayList = new ArrayList(this.cookies);
        } else {
            arrayList = new ArrayList();
            try {
                _db.dbjc((List<Cookie>) arrayList);
                arrayList.addAll(this.cookies);
            } catch (IOException e) {
                LogUtils.logStackTrace(e);
            }
        }
        return arrayList;
    }

    public synchronized void openDb() {
        _db.jwxax();
        try {
            if (this.cacheInMemoryToo) {
                _db.dbjc(this.cookies);
            }
        } catch (IOException e) {
            LogUtils.logStackTrace(e);
        }
    }

    public synchronized String toString() {
        String obj;
        if (this.cacheInMemoryToo) {
            obj = this.cookies.toString();
        } else {
            ArrayList arrayList = new ArrayList();
            try {
                _db.dbjc((List<Cookie>) arrayList);
                arrayList.addAll(this.cookies);
            } catch (IOException e) {
                LogUtils.logStackTrace(e);
            }
            obj = arrayList.toString();
        }
        return obj;
    }
}
