package com.good.gd.ndkproxy.native2javabridges;

import com.good.gd.GDAppServer;

/* loaded from: classes.dex */
final class GDAppServerBridge {
    GDAppServerBridge() {
    }

    private static String getGDAppServerClassString() {
        return GDAppServer.class.getName().replace(".", "/");
    }
}
