package com.good.gd.ndkproxy.native2javabridges.apachehttp.impl.auth;

import com.good.gd.apachehttp.impl.auth.KerberosHandler;

/* loaded from: classes.dex */
public final class KerberosHelper {
    private static void checkCache() {
        KerberosHandler.getInstance().checkCache();
    }
}
