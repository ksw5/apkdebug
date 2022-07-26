package com.good.gd.apache.http.protocol;

import java.util.Map;

/* loaded from: classes.dex */
public class HttpRequestHandlerRegistry implements HttpRequestHandlerResolver {
    private final UriPatternMatcher matcher = new UriPatternMatcher();

    @Override // com.good.gd.apache.http.protocol.HttpRequestHandlerResolver
    public HttpRequestHandler lookup(String str) {
        return (HttpRequestHandler) this.matcher.lookup(str);
    }

    @Deprecated
    protected boolean matchUriRequestPattern(String str, String str2) {
        return this.matcher.matchUriRequestPattern(str, str2);
    }

    public void register(String str, HttpRequestHandler httpRequestHandler) {
        this.matcher.register(str, httpRequestHandler);
    }

    public void setHandlers(Map map) {
        this.matcher.setHandlers(map);
    }

    public void unregister(String str) {
        this.matcher.unregister(str);
    }
}
