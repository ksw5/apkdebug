package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.CookieAttributeHandler;
import com.good.gd.apache.http.cookie.CookieSpec;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbstractCookieSpec implements CookieSpec {
    private final Map<String, CookieAttributeHandler> attribHandlerMap = new HashMap(10);

    /* JADX INFO: Access modifiers changed from: protected */
    public CookieAttributeHandler findAttribHandler(String str) {
        return this.attribHandlerMap.get(str);
    }

    protected CookieAttributeHandler getAttribHandler(String str) {
        CookieAttributeHandler findAttribHandler = findAttribHandler(str);
        if (findAttribHandler != null) {
            return findAttribHandler;
        }
        throw new IllegalStateException("Handler not registered for " + str + " attribute.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Collection<CookieAttributeHandler> getAttribHandlers() {
        return this.attribHandlerMap.values();
    }

    public void registerAttribHandler(String str, CookieAttributeHandler cookieAttributeHandler) {
        if (str != null) {
            if (cookieAttributeHandler != null) {
                this.attribHandlerMap.put(str, cookieAttributeHandler);
                return;
            }
            throw new IllegalArgumentException("Attribute handler may not be null");
        }
        throw new IllegalArgumentException("Attribute name may not be null");
    }
}
