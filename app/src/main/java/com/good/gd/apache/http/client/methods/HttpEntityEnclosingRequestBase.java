package com.good.gd.apache.http.client.methods;

import com.good.gd.apache.http.Header;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.HttpEntityEnclosingRequest;
import com.good.gd.apache.http.client.utils.CloneUtils;
import com.good.gd.apache.http.protocol.HTTP;

/* loaded from: classes.dex */
public abstract class HttpEntityEnclosingRequestBase extends HttpRequestBase implements HttpEntityEnclosingRequest {
    private HttpEntity entity;

    @Override // com.good.gd.apache.http.client.methods.HttpRequestBase
    public Object clone() throws CloneNotSupportedException {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase) super.clone();
        HttpEntity httpEntity = this.entity;
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.entity = (HttpEntity) CloneUtils.clone(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }

    @Override // com.good.gd.apache.http.HttpEntityEnclosingRequest
    public boolean expectContinue() {
        Header firstHeader = getFirstHeader(HTTP.EXPECT_DIRECTIVE);
        return firstHeader != null && HTTP.EXPECT_CONTINUE.equalsIgnoreCase(firstHeader.getValue());
    }

    @Override // com.good.gd.apache.http.HttpEntityEnclosingRequest
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override // com.good.gd.apache.http.HttpEntityEnclosingRequest
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }
}
