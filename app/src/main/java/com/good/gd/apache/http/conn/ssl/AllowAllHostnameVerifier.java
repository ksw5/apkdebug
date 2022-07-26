package com.good.gd.apache.http.conn.ssl;

/* loaded from: classes.dex */
public class AllowAllHostnameVerifier extends AbstractVerifier {
    public final String toString() {
        return "ALLOW_ALL";
    }

    @Override // com.good.gd.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, String[] strArr, String[] strArr2) {
    }
}
