package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apache.http.auth.AuthScheme;
import com.good.gd.apache.http.auth.AuthSchemeFactory;
import com.good.gd.apache.http.params.HttpParams;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class NegotiateSchemeFactory implements AuthSchemeFactory {
    private final SpnegoTokenGenerator spengoGenerator;
    private final boolean stripPort;

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegoTokenGenerator, boolean z) {
        this.spengoGenerator = spnegoTokenGenerator;
        this.stripPort = z;
    }

    public SpnegoTokenGenerator getSpengoGenerator() {
        return this.spengoGenerator;
    }

    public boolean isStripPort() {
        return this.stripPort;
    }

    @Override // com.good.gd.apache.http.auth.AuthSchemeFactory
    public AuthScheme newInstance(HttpParams httpParams) {
        GDLog.DBGPRINTF(16, "NegotiateSchemeFactory::newInstance()\n");
        return new NegotiateScheme();
    }

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }

    public NegotiateSchemeFactory() {
        this(null, false);
    }
}
