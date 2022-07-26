package com.good.gd.net;

import com.good.gd.ndkproxy.net.JavaNetSocketImpl;

/* loaded from: classes.dex */
public class GDSocketHelper {
    public static boolean convertToSecure(GDSocket gDSocket, String str) throws GDSocketException {
        return gDSocket.convertToSecure(str);
    }

    public static JavaNetSocketImpl getImpl(GDSocket gDSocket) {
        return gDSocket.getImpl();
    }

    public static boolean isSecure(GDSocket gDSocket) {
        return gDSocket.isSecure();
    }
}
