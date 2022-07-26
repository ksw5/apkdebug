package com.good.gd.apache.http.protocol;

/* loaded from: classes.dex */
public class SyncBasicHttpContext extends BasicHttpContext {
    public SyncBasicHttpContext(HttpContext httpContext) {
        super(httpContext);
    }

    @Override // com.good.gd.apache.http.protocol.BasicHttpContext, com.good.gd.apache.http.protocol.HttpContext
    public synchronized Object getAttribute(String str) {
        return super.getAttribute(str);
    }

    @Override // com.good.gd.apache.http.protocol.BasicHttpContext, com.good.gd.apache.http.protocol.HttpContext
    public synchronized Object removeAttribute(String str) {
        return super.removeAttribute(str);
    }

    @Override // com.good.gd.apache.http.protocol.BasicHttpContext, com.good.gd.apache.http.protocol.HttpContext
    public synchronized void setAttribute(String str, Object obj) {
        super.setAttribute(str, obj);
    }
}
