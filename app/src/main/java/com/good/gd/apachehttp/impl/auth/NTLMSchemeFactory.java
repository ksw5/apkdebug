package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthSchemeFactory;
import com.good.gd.apache.http.impl.auth.NTLMScheme;
import com.good.gd.apache.http.params.HttpParams;

/* loaded from: classes.dex */
public class NTLMSchemeFactory implements AuthSchemeFactory {
    @Override // com.good.gd.apache.http.auth.AuthSchemeFactory
    public AuthScheme newInstance(HttpParams httpParams) {
        return new NTLMScheme(new NTLMEngineImpl());
    }
}
