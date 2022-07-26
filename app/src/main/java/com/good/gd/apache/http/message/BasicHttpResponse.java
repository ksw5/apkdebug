package com.good.gd.apache.http.message;

import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpResponse;
import com.good.gd.apache.http.ProtocolVersion;
import com.good.gd.apache.http.ReasonPhraseCatalog;
import com.good.gd.apache.http.StatusLine;
import java.util.Locale;

/* loaded from: classes.dex */
public class BasicHttpResponse extends AbstractHttpMessage implements HttpResponse {
    private HttpEntity entity;
    private Locale locale;
    private ReasonPhraseCatalog reasonCatalog;
    private StatusLine statusline;

    public BasicHttpResponse(StatusLine statusLine, ReasonPhraseCatalog reasonPhraseCatalog, Locale locale) {
        if (statusLine != null) {
            this.statusline = statusLine;
            this.reasonCatalog = reasonPhraseCatalog;
            this.locale = locale == null ? Locale.getDefault() : locale;
            return;
        }
        throw new IllegalArgumentException("Status line may not be null.");
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.good.gd.apache.http.HttpMessage
    public ProtocolVersion getProtocolVersion() {
        return this.statusline.getProtocolVersion();
    }

    protected String getReason(int i) {
        ReasonPhraseCatalog reasonPhraseCatalog = this.reasonCatalog;
        if (reasonPhraseCatalog == null) {
            return null;
        }
        return reasonPhraseCatalog.getReason(i, this.locale);
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public StatusLine getStatusLine() {
        return this.statusline;
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setLocale(Locale locale) {
        if (locale != null) {
            this.locale = locale;
            int statusCode = this.statusline.getStatusCode();
            this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), statusCode, getReason(statusCode));
            return;
        }
        throw new IllegalArgumentException("Locale may not be null.");
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setReasonPhrase(String str) {
        if (str != null && (str.indexOf(10) >= 0 || str.indexOf(13) >= 0)) {
            throw new IllegalArgumentException("Line break in reason phrase.");
        }
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), this.statusline.getStatusCode(), str);
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setStatusCode(int i) {
        this.statusline = new BasicStatusLine(this.statusline.getProtocolVersion(), i, getReason(i));
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setStatusLine(StatusLine statusLine) {
        if (statusLine != null) {
            this.statusline = statusLine;
            return;
        }
        throw new IllegalArgumentException("Status line may not be null");
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
        this.statusline = new BasicStatusLine(protocolVersion, i, getReason(i));
    }

    @Override // com.good.gd.apache.http.HttpResponse
    public void setStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        this.statusline = new BasicStatusLine(protocolVersion, i, str);
    }

    public BasicHttpResponse(StatusLine statusLine) {
        this(statusLine, (ReasonPhraseCatalog) null, (Locale) null);
    }

    public BasicHttpResponse(ProtocolVersion protocolVersion, int i, String str) {
        this(new BasicStatusLine(protocolVersion, i, str), (ReasonPhraseCatalog) null, (Locale) null);
    }
}
