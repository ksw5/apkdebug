package com.good.gd.apache.http.impl.client;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.apache.http.params.AbstractHttpParams;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class ClientParamsStack extends AbstractHttpParams {
    protected final HttpParams applicationParams;
    protected final HttpParams clientParams;
    private final Log log;
    protected final HttpParams overrideParams;
    protected final HttpParams requestParams;

    public ClientParamsStack(HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        this.log = LogFactory.getLog(ClientParamsStack.class);
        this.applicationParams = httpParams;
        this.clientParams = httpParams2;
        this.requestParams = httpParams3;
        this.overrideParams = httpParams4;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams copy() {
        return this;
    }

    public final HttpParams getApplicationParams() {
        return this.applicationParams;
    }

    public final HttpParams getClientParams() {
        return this.clientParams;
    }

    public final HttpParams getOverrideParams() {
        return this.overrideParams;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public Object getParameter(String str) {
        HttpParams httpParams;
        HttpParams httpParams2;
        HttpParams httpParams3;
        if (str != null) {
            Object obj = null;
            HttpParams httpParams4 = this.overrideParams;
            if (httpParams4 != null) {
                obj = httpParams4.getParameter(str);
            }
            if (obj == null && (httpParams3 = this.requestParams) != null) {
                obj = httpParams3.getParameter(str);
            }
            if (obj == null && (httpParams2 = this.clientParams) != null) {
                obj = httpParams2.getParameter(str);
            }
            if (obj == null && (httpParams = this.applicationParams) != null) {
                obj = httpParams.getParameter(str);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("'" + str + "': " + obj);
            }
            return obj;
        }
        throw new IllegalArgumentException("Parameter name must not be null.");
    }

    public final HttpParams getRequestParams() {
        return this.requestParams;
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public boolean removeParameter(String str) {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }

    @Override // com.good.gd.apache.http.params.HttpParams
    public HttpParams setParameter(String str, Object obj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }

    public ClientParamsStack(ClientParamsStack clientParamsStack) {
        this(clientParamsStack.getApplicationParams(), clientParamsStack.getClientParams(), clientParamsStack.getRequestParams(), clientParamsStack.getOverrideParams());
    }

    public ClientParamsStack(ClientParamsStack clientParamsStack, HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        this(httpParams == null ? clientParamsStack.getApplicationParams() : httpParams, httpParams2 == null ? clientParamsStack.getClientParams() : httpParams2, httpParams3 == null ? clientParamsStack.getRequestParams() : httpParams3, httpParams4 == null ? clientParamsStack.getOverrideParams() : httpParams4);
    }
}
