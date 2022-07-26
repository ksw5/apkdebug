package com.good.gd.net;

import android.net.Proxy;
import com.good.gd.GDApacheHttpContainerState;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.net.GDSocketEventsListener;
import com.good.gd.ndkproxy.net.GDSocketEventsListenerHolder;
import com.good.gd.ndkproxy.net.JavaNetSocketImpl;
import com.good.gd.ndkproxy.net.SocketReflectionUtils;
import com.good.gd.utils.ErrorUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/* loaded from: classes.dex */
public class GDSocketImpl extends Socket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static boolean IS_NOT_SECURE_DEFAULT = false;
    private ContainerState containerState;
    private JavaNetSocketImpl gdImpl;
    protected boolean isSecure;
    private GDSocketEventsListener socketEventsListener;

    public GDSocketImpl() throws IOException {
        this(IS_NOT_SECURE_DEFAULT);
    }

    @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        super.close();
        this.socketEventsListener.onGDSocketClosed();
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i) throws IOException {
        if (!GDActivitySupport.isWiped()) {
            try {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
                if (inetSocketAddress.isUnresolved()) {
                    GDLog.DBGPRINTF(16, "GDSocket::connect(): retrieved host name=" + SocketReflectionUtils.getHostNameFromInetSocketAddress(inetSocketAddress) + "\n");
                    SocketReflectionUtils.initInetAddrInInetSocketAddress(inetSocketAddress);
                    this.socketEventsListener.onGDSocketCreated();
                    super.connect(socketAddress, i);
                    return;
                }
                throw new UnsupportedOperationException("This constructor does not support Resolved InetSocketAddress, use createUnresolve() method.");
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                GDLog.DBGPRINTF(12, "GDSocket::connect() non-IO exception " + th + " with message " + th.getMessage() + "\n");
                throw new IOException(th);
            }
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean convertToSecure(String str) throws GDSocketException {
        if (!this.isSecure) {
            boolean convertToSSL = this.gdImpl.convertToSSL(str);
            this.isSecure = convertToSSL;
            return convertToSSL;
        }
        throw new GDSocketException("GD Socket already secure");
    }

    public void disableHostVerification() {
        JavaNetSocketImpl javaNetSocketImpl = this.gdImpl;
        if (javaNetSocketImpl != null) {
            javaNetSocketImpl.disableHostVerification();
        }
    }

    public void disablePeerVerification() {
        JavaNetSocketImpl javaNetSocketImpl = this.gdImpl;
        if (javaNetSocketImpl != null) {
            javaNetSocketImpl.disablePeerVerification();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JavaNetSocketImpl getImpl() {
        return this.gdImpl;
    }

    public String getSessionId() {
        JavaNetSocketImpl javaNetSocketImpl = this.gdImpl;
        if (javaNetSocketImpl != null) {
            return javaNetSocketImpl.getSessionId();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isSecure() {
        return this.isSecure;
    }

    public void setSessionId(String str) {
        JavaNetSocketImpl javaNetSocketImpl = this.gdImpl;
        if (javaNetSocketImpl != null) {
            javaNetSocketImpl.setSessionId(str);
        }
    }

    public GDSocketImpl(boolean z) throws IOException {
        this(new JavaNetSocketImpl(z), z);
    }

    private GDSocketImpl(JavaNetSocketImpl javaNetSocketImpl, boolean z) throws IOException {
        super(javaNetSocketImpl);
        this.isSecure = false;
        this.gdImpl = null;
        this.containerState = GDApacheHttpContainerState.getContainerState();
        this.socketEventsListener = GDSocketEventsListenerHolder.getListener();
        this.isSecure = z;
        GDLog.DBGPRINTF(16, "GDSocket(isSecure = " + this.isSecure + " ) IN\n");
        if (!this.containerState.isWiped()) {
            this.gdImpl = javaNetSocketImpl;
            if (javaNetSocketImpl == null) {
                throw new AssertionError();
            }
            GDLog.DBGPRINTF(16, "GDSocket() OUT\n");
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public GDSocketImpl(Proxy proxy) throws Exception {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }

    public void connect(String str, int i, int i2) throws IOException {
        if (!this.containerState.isWiped()) {
            try {
                GDLog.DBGPRINTF(16, "GDSocket::connect() IN: (host=" + str + ", port=" + i + ", tm=" + i2 + "\n");
                InetSocketAddress createUnresolved = InetSocketAddress.createUnresolved(str, i);
                GDLog.DBGPRINTF(16, "GDSocket::connect(): unresolved (InetSocketAddress=" + createUnresolved + ", retrieved host name=" + SocketReflectionUtils.getHostNameFromInetSocketAddress(createUnresolved) + "\n");
                SocketReflectionUtils.initInetAddrInInetSocketAddress(createUnresolved);
                GDLog.DBGPRINTF(16, "GDSocket::connect() OUT\n");
                this.socketEventsListener.onGDSocketClosed();
                super.connect(createUnresolved, i2);
                return;
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                GDLog.DBGPRINTF(12, "GDSocket::connect() non-IO exception " + th + " with message " + th.getMessage() + "\n");
                throw new IOException(th);
            }
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress) {
        throw new UnsupportedOperationException("This method is not supported by GD");
    }

    public GDSocketImpl(String str, int i) throws Exception {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        if (!getClass().getSuperclass().getName().equalsIgnoreCase("java.net.Socket")) {
            return;
        }
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }

    public GDSocketImpl(String str, int i, InetAddress inetAddress, int i2) throws SocketException {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }

    public GDSocketImpl(String str, int i, boolean z) throws Exception {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }

    public GDSocketImpl(InetAddress inetAddress, int i) throws Exception {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }

    public GDSocketImpl(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws Exception {
        super(new JavaNetSocketImpl());
        this.isSecure = false;
        this.gdImpl = null;
        throw new UnsupportedOperationException("This constructor is not supported by GD");
    }
}
