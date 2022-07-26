package com.good.gd.apache.http.impl.auth;

import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthSchemeFactory;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class DigestSchemeFactory implements AuthSchemeFactory {
    @Override // com.good.gd.apache.http.auth.AuthSchemeFactory
    public AuthScheme newInstance(HttpParams httpParams) {
        return new DigestScheme();
    }
}
