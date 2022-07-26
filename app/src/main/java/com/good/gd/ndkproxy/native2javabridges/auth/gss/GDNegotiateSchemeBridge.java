package com.good.gd.ndkproxy.native2javabridges.auth.gss;

import com.good.gd.auth.gss.GDNegotiateScheme;

/* loaded from: classes.dex */
final class GDNegotiateSchemeBridge {
    GDNegotiateSchemeBridge() {
    }

    private static Class getGssStatusCodeClass() {
        return GDNegotiateScheme.GssStatusCode.class;
    }

    private static String getGssStatusCodeSignature() {
        return "L" + GDNegotiateScheme.GssStatusCode.class.getName().replace(".", "/") + ";";
    }
}
