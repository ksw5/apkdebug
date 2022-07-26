package com.good.gd.ndkproxy.net;

import com.good.gd.ndkproxy.GDLog;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/* loaded from: classes.dex */
public final class GDSocketImpl {
    boolean deferredDisableHostVerification;
    boolean deferredDisablePeerVerification;
    boolean isSecure;
    long nativeGDSocketEventSinkPtr;
    long nativeGDSocketPtr;
    String originalHostname;
    String sessionId;

    public GDSocketImpl() {
        this.nativeGDSocketPtr = 0L;
        this.nativeGDSocketEventSinkPtr = 0L;
        this.originalHostname = "";
        this.isSecure = false;
        this.sessionId = "";
        this.deferredDisableHostVerification = false;
        this.deferredDisablePeerVerification = false;
        GDLog.DBGPRINTF(16, "GDSocketImpl::GDSocketImpl()\n");
    }

    private native void finishNative();

    private native boolean nativeConvertToSSL(String str);

    private native String nativeCurrentGPS();

    private native String nativeCurrentRoute();

    private native boolean nativeDisableHostVerification();

    private native boolean nativeDisablePeerVerification();

    private native boolean nativeIsDirectConnect();

    private native void nativeReleaseNativeResources();

    private native boolean nativeSetSessionId(String str);

    private native void setSocketBooleanOption(FileDescriptor fileDescriptor, int i, boolean z) throws SocketException;

    private native void setSocketIntOption(FileDescriptor fileDescriptor, int i, int i2) throws SocketException;

    public native int availableStream(FileDescriptor fileDescriptor) throws SocketException;

    public native void bind(FileDescriptor fileDescriptor, InetAddress inetAddress, int i) throws SocketException;

    public native void close(FileDescriptor fileDescriptor) throws IOException;

    public void connect(FileDescriptor fileDescriptor, String str, int i, int i2) throws SocketException {
        this.originalHostname = str;
        connectNative(fileDescriptor, str, i, i2);
    }

    public native void connectNative(FileDescriptor fileDescriptor, String str, int i, int i2) throws SocketException;

    public boolean convertToSSL(String str) {
        return nativeConvertToSSL(str);
    }

    public String currentGPS() {
        GDLog.DBGPRINTF(16, "GDSocketImpl::currentGPS()\n");
        return nativeCurrentGPS();
    }

    public String currentRoute() {
        GDLog.DBGPRINTF(16, "GDSocketImpl::currentRoute()\n");
        return nativeCurrentRoute();
    }

    public boolean disableHostVerification() {
        return nativeDisableHostVerification();
    }

    public boolean disablePeerVerification() {
        return nativeDisablePeerVerification();
    }

    public void finish() {
        GDLog.DBGPRINTF(16, "GDSocketImpl::finish() IN\n");
        try {
            finishNative();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDSocketImpl::finish():\n", e);
        }
        GDLog.DBGPRINTF(16, "GDSocketImpl::finish(): peer deinitialized\n");
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public native int getSocketFlags();

    public native InetAddress getSocketLocalAddress(FileDescriptor fileDescriptor);

    public native int getSocketLocalPort(FileDescriptor fileDescriptor);

    public native Object getSocketOption(FileDescriptor fileDescriptor, int i) throws SocketException;

    public void init() {
        GDLog.DBGPRINTF(16, "GDSocketImpl::init(): Attempting to initialize C++ peer\n");
        try {
            ndkInit();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "GDSocketImpl::init(): Cannot initialize C++ peer\n", e);
        }
        GDLog.DBGPRINTF(16, "GDSocketImpl::init(): peer initialized\n");
    }

    public native boolean isConnected(FileDescriptor fileDescriptor, int i) throws IOException;

    public boolean isDirectConnect() {
        GDLog.DBGPRINTF(16, "GDSocketImpl::isDirectConnect()\n");
        return nativeIsDirectConnect();
    }

    native void ndkInit();

    public native int read(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2, int i3) throws IOException;

    public native int readDirect(FileDescriptor fileDescriptor, long j, int i, int i2) throws IOException;

    public void releaseNativeResources() {
        nativeReleaseNativeResources();
    }

    public native int send(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2, int i3, InetAddress inetAddress) throws IOException;

    public boolean setSessionId(String str) {
        this.sessionId = str;
        return nativeSetSessionId(str);
    }

    public void setSocketOption(FileDescriptor fileDescriptor, int i, Object obj) throws SocketException {
        if (obj instanceof Integer) {
            setSocketIntOption(fileDescriptor, i, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            setSocketBooleanOption(fileDescriptor, i, ((Boolean) obj).booleanValue());
        } else {
            throw new SocketException("Unknown socket option value type");
        }
    }

    public native void shutdownInput(FileDescriptor fileDescriptor) throws IOException;

    public native void shutdownOutput(FileDescriptor fileDescriptor) throws IOException;

    public native void socket(FileDescriptor fileDescriptor, boolean z) throws SocketException;

    public native void socketClose(FileDescriptor fileDescriptor) throws IOException;

    public native int write(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2) throws IOException;

    public native int writeDirect(FileDescriptor fileDescriptor, int i, int i2, int i3) throws IOException;

    public GDSocketImpl(boolean z) {
        this.nativeGDSocketPtr = 0L;
        this.nativeGDSocketEventSinkPtr = 0L;
        this.originalHostname = "";
        this.isSecure = false;
        this.sessionId = "";
        this.deferredDisableHostVerification = false;
        this.deferredDisablePeerVerification = false;
        GDLog.DBGPRINTF(16, "GDSocketImpl::GDSocketImpl() 1\n");
        this.isSecure = z;
    }
}
