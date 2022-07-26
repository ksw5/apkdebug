package com.good.gd.ndkproxy.native2javabridges;

import com.good.gd.GDServiceProvider;

/* loaded from: classes.dex */
final class GDServiceProviderBridge {
    GDServiceProviderBridge() {
    }

    private static String getGDServiceProviderClassString() {
        return GDServiceProvider.class.getName().replace(".", "/");
    }
}
