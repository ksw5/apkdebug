package com.good.gd.apache.http.conn.ssl;

import javax.net.ssl.SSLException;

/* loaded from: classes.dex */
public class BrowserCompatHostnameVerifier extends AbstractVerifier {
    public final String toString() {
        return "BROWSER_COMPATIBLE";
    }

    @Override // com.good.gd.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, String[] strArr, String[] strArr2) throws SSLException {
        verify(str, strArr, strArr2, false);
    }
}
