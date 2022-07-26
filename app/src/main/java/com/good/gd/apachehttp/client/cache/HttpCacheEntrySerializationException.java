package com.good.gd.apachehttp.client.cache;

import java.io.IOException;

/* loaded from: classes.dex */
public class HttpCacheEntrySerializationException extends IOException {
    private static final long serialVersionUID = 9219188365878433519L;

    public HttpCacheEntrySerializationException(String str) {
    }

    public HttpCacheEntrySerializationException(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}
