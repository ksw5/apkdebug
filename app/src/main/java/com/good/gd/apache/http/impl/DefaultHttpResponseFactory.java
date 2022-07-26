package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.HttpResponseFactory;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.ReasonPhraseCatalog;
import com.good.gd.apache.http.StatusLine;
import com.good.gd.apache.http.message.BasicHttpResponse;
import com.good.gd.apache.http.message.BasicStatusLine;
import com.good.gd.apache.http.protocol.HttpContext;
import java.util.Locale;

/* loaded from: classes.dex */
public class DefaultHttpResponseFactory implements HttpResponseFactory {
    protected final ReasonPhraseCatalog reasonCatalog;

    public DefaultHttpResponseFactory(ReasonPhraseCatalog reasonPhraseCatalog) {
        if (reasonPhraseCatalog != null) {
            this.reasonCatalog = reasonPhraseCatalog;
            return;
        }
        throw new IllegalArgumentException("Reason phrase catalog must not be null.");
    }

    protected Locale determineLocale(HttpContext httpContext) {
        return Locale.getDefault();
    }

    @Override // com.good.gd.apache.http.HttpResponseFactory
    public HttpResponse newHttpResponse(ProtocolVersion protocolVersion, int i, HttpContext httpContext) {
        if (protocolVersion != null) {
            Locale determineLocale = determineLocale(httpContext);
            return new BasicHttpResponse(new BasicStatusLine(protocolVersion, i, this.reasonCatalog.getReason(i, determineLocale)), this.reasonCatalog, determineLocale);
        }
        throw new IllegalArgumentException("HTTP version may not be null");
    }

    @Override // com.good.gd.apache.http.HttpResponseFactory
    public HttpResponse newHttpResponse(StatusLine statusLine, HttpContext httpContext) {
        if (statusLine != null) {
            return new BasicHttpResponse(statusLine, this.reasonCatalog, determineLocale(httpContext));
        }
        throw new IllegalArgumentException("Status line may not be null");
    }

    public DefaultHttpResponseFactory() {
        this(EnglishReasonPhraseCatalog.INSTANCE);
    }
}
