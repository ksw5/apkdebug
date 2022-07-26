package com.good.gd.apache.http.impl.cookie;

import com.good.gd.apache.http.cookie.CookieSpec;
import com.good.gd.apache.http.cookie.CookieSpecFactory;
import com.good.gd.apache.http.cookie.params.CookieSpecPNames;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class NetscapeDraftSpecFactory implements CookieSpecFactory {
    @Override // com.good.gd.apache.http.cookie.CookieSpecFactory
    public CookieSpec newInstance(HttpParams httpParams) {
        if (httpParams != null) {
            return new NetscapeDraftSpec((String[]) httpParams.getParameter(CookieSpecPNames.DATE_PATTERNS));
        }
        return new NetscapeDraftSpec();
    }
}
