package com.good.gd.net;

import com.good.gd.ndkproxy.net.JavaNetSocketImpl;
import java.net.SocketImpl;
import java.net.SocketImplFactory;

/* loaded from: classes.dex */
public class GDSocketImplFactory implements SocketImplFactory {
    @Override // java.net.SocketImplFactory
    public SocketImpl createSocketImpl() {
        return new JavaNetSocketImpl();
    }
}
