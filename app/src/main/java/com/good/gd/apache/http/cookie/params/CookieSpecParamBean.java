package com.good.gd.apache.http.cookie.params;

import com.good.gd.apache.http.params.HttpAbstractParamBean;
import com.good.gd.apache.http.params.HttpParams;
import java.util.Collection;

/* loaded from: classes.dex */
public class CookieSpecParamBean extends HttpAbstractParamBean {
    public CookieSpecParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setDatePatterns(Collection<String> collection) {
        this.params.setParameter(CookieSpecPNames.DATE_PATTERNS, collection);
    }

    public void setSingleHeader(boolean z) {
        this.params.setBooleanParameter(CookieSpecPNames.SINGLE_COOKIE_HEADER, z);
    }
}
