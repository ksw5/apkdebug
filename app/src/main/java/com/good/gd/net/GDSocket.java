package com.good.gd.net;

import android.net.Proxy;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/* loaded from: classes.dex */
public class GDSocket extends GDSocketImpl {
    public GDSocket() throws IOException {
    }

    @Override // com.good.gd.net.GDSocketImpl, java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        super.close();
    }

    @Override // com.good.gd.net.GDSocketImpl, java.net.Socket
    public void connect(SocketAddress socketAddress, int i) throws IOException {
        super.connect(socketAddress, i);
    }

    @Override // com.good.gd.net.GDSocketImpl
    public void disableHostVerification() {
        super.disableHostVerification();
    }

    @Override // com.good.gd.net.GDSocketImpl
    public void disablePeerVerification() {
        super.disablePeerVerification();
    }

    public GDSocket(boolean z) throws IOException {
        super(z);
    }

    @Override // com.good.gd.net.GDSocketImpl
    public void connect(String str, int i, int i2) throws IOException {
        super.connect(str, i, i2);
    }

    public GDSocket(Proxy proxy) throws Exception {
        super(proxy);
    }

    @Override // com.good.gd.net.GDSocketImpl, java.net.Socket
    public void connect(SocketAddress socketAddress) {
        super.connect(socketAddress);
    }

    public GDSocket(String str, int i) throws Exception {
        super(str, i);
    }

    public GDSocket(String str, int i, InetAddress inetAddress, int i2) throws SocketException {
        super(str, i, inetAddress, i2);
    }

    public GDSocket(String str, int i, boolean z) throws Exception {
        super(str, i, z);
    }

    public GDSocket(InetAddress inetAddress, int i) throws Exception {
        super(inetAddress, i);
    }

    public GDSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws Exception {
        super(inetAddress, i, inetAddress2, i2);
    }
}
