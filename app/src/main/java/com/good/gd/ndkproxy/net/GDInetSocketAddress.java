package com.good.gd.ndkproxy.net;

import java.net.InetSocketAddress;

/* loaded from: classes.dex */
public class GDInetSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = -2924600538793140652L;

    public GDInetSocketAddress(String str, int i) {
        super(0);
        if (str == null || i < 0 || i > 65535) {
            throw new IllegalArgumentException("host=" + str + ", port=" + i);
        }
    }

    public static InetSocketAddress createUnresolved(String str, int i) {
        return new InetSocketAddress(str, i);
    }
}
