package com.good.gd.apache.http.auth;

import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public interface AuthSchemeFactory {
    AuthScheme newInstance(HttpParams httpParams);
}
