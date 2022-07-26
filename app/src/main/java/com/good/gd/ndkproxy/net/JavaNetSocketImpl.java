package com.good.gd.ndkproxy.net;

import com.good.gd.ndkproxy.GDLog;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/* loaded from: classes.dex */
public class JavaNetSocketImpl extends SocketImpl implements GDSocketSSLSettings {
    static final int FLAG_SHUTDOWN = 8;
    static final int MULTICAST_IF = 1;
    static final int MULTICAST_TTL = 2;
    static final int SO_SND_TIMEOUT = 4101;
    static final int TCP_NODELAY = 4;
    String cached_currentGPS;
    String cached_currentRoute;
    boolean cached_isDirectConnect;
    boolean flagsAndDataCanBeQueried;
    private GDSocketImpl netImpl;
    private Proxy proxy;
    public int receiveTimeout;
    public boolean shutdownInput;
    public int sndTimeout;
    public boolean streaming;
    private boolean tcpNoDelay;
    private int trafficClass;
    private static String TAG = JavaNetSocketImpl.class.getSimpleName();
    private static boolean IS_NOT_SECURE_DEFAULT = false;

    public JavaNetSocketImpl() {
        this(IS_NOT_SECURE_DEFAULT);
    }

    private void checkNotClosed() throws IOException {
        GDLog.DBGPRINTF(19, TAG + "::checkNotClosed( ) IN\n");
        if (((SocketImpl) this).fd.valid()) {
            GDLog.DBGPRINTF(19, TAG + "::checkNotClosed( ) OUT\n");
            return;
        }
        throw new SocketException("Socket is closed");
    }

    private void releaseSocketResources() throws IOException {
        try {
            this.netImpl.releaseNativeResources();
        } finally {
            ((SocketImpl) this).fd = new FileDescriptor();
        }
    }

    private boolean usingSocks() {
        GDLog.DBGPRINTF(17, TAG + "::usingSocks() IN: proxy=" + this.proxy + "\n");
        Proxy proxy = this.proxy;
        return proxy != null && proxy.type() == Proxy.Type.SOCKS;
    }

    @Override // java.net.SocketImpl
    protected void accept(SocketImpl socketImpl) throws IOException {
        throw new UnsupportedOperationException("Server side accept() is not supported by GD");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // java.net.SocketImpl
    public int available() throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::available( ) IN\n");
        checkNotClosed();
        if (this.shutdownInput) {
            return 0;
        }
        int availableStream = this.netImpl.availableStream(((SocketImpl) this).fd);
        GDLog.DBGPRINTF(17, TAG + "::available( ) OUT: res=" + availableStream + "\n");
        return availableStream;
    }

    @Override // java.net.SocketImpl
    protected void bind(InetAddress inetAddress, int i) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::bind() IN:  super.fd=" + ((SocketImpl) this).fd + " address=" + inetAddress + " port=" + i + "\n");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // java.net.SocketImpl
    public void close() throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::close( ) IN\n");
        this.flagsAndDataCanBeQueried = false;
        closeNativeSocket(false);
        GDLog.DBGPRINTF(17, TAG + "::close( ) OUT\n");
    }

    protected void closeNativeSocket(boolean z) throws IOException {
        synchronized (((SocketImpl) this).fd) {
            if (((SocketImpl) this).fd.valid()) {
                if (z) {
                    GDLog.DBGPRINTF(12, "GDSocketImpl::finalize - socket was not closed\n");
                }
                if ((this.netImpl.getSocketFlags() & 8) != 0) {
                    try {
                        shutdownOutput();
                    } catch (Exception e) {
                    }
                }
                this.netImpl.socketClose(((SocketImpl) this).fd);
                releaseSocketResources();
            }
        }
    }

    @Override // java.net.SocketImpl
    protected void connect(String str, int i) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::connect( Host=" + str + ", Port=" + i + ") IN\n");
        connect(InetAddress.getByName(str), i);
    }

    @Override // com.good.gd.ndkproxy.net.GDSocketSSLSettings
    public boolean convertToSSL(String str) {
        GDLog.DBGPRINTF(17, TAG + "::convertToSSL, host = " + str + "\n");
        return this.netImpl.convertToSSL(str);
    }

    @Override // java.net.SocketImpl
    protected void create(boolean z) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::create( streaming=" + z + ") IN: super.fd=" + ((SocketImpl) this).fd + "\n");
        this.streaming = z;
        this.netImpl.socket(((SocketImpl) this).fd, z);
        GDLog.DBGPRINTF(17, TAG + "::create( ) OUT: super.fd=" + ((SocketImpl) this).fd + "\n");
    }

    public String currentGPS() {
        if (this.flagsAndDataCanBeQueried) {
            this.cached_currentGPS = this.netImpl.currentGPS();
        }
        return this.cached_currentGPS;
    }

    public String currentRoute() {
        if (this.flagsAndDataCanBeQueried) {
            this.cached_currentRoute = this.netImpl.currentRoute();
        }
        return this.cached_currentRoute;
    }

    @Override // com.good.gd.ndkproxy.net.GDSocketSSLSettings
    public void disableHostVerification() {
        this.netImpl.disableHostVerification();
    }

    @Override // com.good.gd.ndkproxy.net.GDSocketSSLSettings
    public void disablePeerVerification() {
        this.netImpl.disablePeerVerification();
    }

    protected void finalize() throws Throwable {
        GDLog.DBGPRINTF(17, "GDSocketImpl::finalize()\n");
        try {
            closeNativeSocket(true);
        } finally {
            super.finalize();
        }
    }

    @Override // java.net.SocketImpl
    protected InetAddress getInetAddress() {
        try {
            return InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
        } catch (UnknownHostException e) {
            return null;
        }
    }

    @Override // java.net.SocketImpl
    protected InputStream getInputStream() throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::getInputStream( ) IN\n");
        checkNotClosed();
        return new SocketInputStream(this);
    }

    @Override // java.net.SocketOptions
    public Object getOption(int i) throws SocketException {
        Object obj;
        GDLog.DBGPRINTF(17, TAG + "::getOption( " + i + " ) IN: super.fd=" + ((SocketImpl) this).fd + "\n");
        if (i == 4102) {
            obj = Integer.valueOf(this.receiveTimeout);
        } else if (i == 3) {
            obj = Integer.valueOf(this.trafficClass);
        } else if (i == SO_SND_TIMEOUT) {
            obj = Integer.valueOf(this.sndTimeout);
        } else {
            Object socketOption = this.netImpl.getSocketOption(((SocketImpl) this).fd, i);
            if (i == 1 && (this.netImpl.getSocketFlags() & 4) != 0) {
                obj = Boolean.valueOf(this.tcpNoDelay);
            } else {
                obj = socketOption;
            }
        }
        GDLog.DBGPRINTF(17, TAG + "::getOption() OUT\n");
        return obj;
    }

    @Override // java.net.SocketImpl
    protected OutputStream getOutputStream() throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::getOutputStream( ) IN\n");
        checkNotClosed();
        return new SocketOutputStream(this);
    }

    public String getSessionId() {
        return this.netImpl.getSessionId();
    }

    public void initLocalPort(int i) {
        GDLog.DBGPRINTF(17, TAG + "::initLocalPort( ) IN\n");
        ((SocketImpl) this).localport = i;
    }

    public void initRemoteAddressAndPort(InetAddress inetAddress, int i) {
        GDLog.DBGPRINTF(17, TAG + "::initRemoteAddressAndPort( ) IN\n");
        ((SocketImpl) this).address = inetAddress;
        ((SocketImpl) this).port = i;
    }

    protected void internalConnect(InetAddress inetAddress, String str, int i, int i2) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::internalConnect( anAddr=" + inetAddress + ", hostname=" + str + ", port=" + i + ", timeout=" + i2 + " ) IN\n");
        InetAddress localHost = inetAddress.isAnyLocalAddress() ? InetAddress.getLocalHost() : inetAddress;
        try {
            this.flagsAndDataCanBeQueried = true;
            if (this.streaming && usingSocks()) {
                throw new UnsupportedOperationException("SOCKS is not supported");
            }
            this.netImpl.connect(((SocketImpl) this).fd, str, i, i2);
            this.cached_currentGPS = currentGPS();
            this.cached_currentRoute = currentRoute();
            this.cached_isDirectConnect = isDirectConnect();
            ((SocketImpl) this).address = localHost;
            ((SocketImpl) this).port = i;
        } catch (ConnectException e) {
            throw new ConnectException(inetAddress + ":" + i + " - " + e.getMessage());
        } catch (IOException e2) {
            this.cached_currentGPS = currentGPS();
            this.cached_currentRoute = currentRoute();
            this.cached_isDirectConnect = isDirectConnect();
            this.flagsAndDataCanBeQueried = false;
            throw e2;
        }
    }

    public boolean isDirectConnect() {
        if (this.flagsAndDataCanBeQueried) {
            this.cached_isDirectConnect = this.netImpl.isDirectConnect();
        }
        return this.cached_isDirectConnect;
    }

    @Override // java.net.SocketImpl
    protected void listen(int i) throws IOException {
        throw new UnsupportedOperationException("Server side listen() is not supported");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int read(byte[] bArr, int i, int i2) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::read() IN: receiveTimeout=" + this.receiveTimeout + "\n");
        if (this.shutdownInput) {
            return -1;
        }
        int read = this.netImpl.read(((SocketImpl) this).fd, bArr, i, i2, this.receiveTimeout);
        if (read != 0) {
            if (read == -1) {
                this.shutdownInput = true;
            }
            GDLog.DBGPRINTF(17, TAG + "::read() OUT\n");
            return read;
        }
        throw new SocketTimeoutException();
    }

    @Override // java.net.SocketImpl
    protected void sendUrgentData(int i) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::sendUrgentData( ) IN\n");
        throw new UnsupportedOperationException("TCP Priority Data Transfer is not supported");
    }

    @Override // java.net.SocketOptions
    public void setOption(int i, Object obj) throws SocketException {
        GDLog.DBGPRINTF(17, TAG + "::setOption( " + i + ", " + obj + " ) IN\n");
        if (i == 4102) {
            this.receiveTimeout = ((Integer) obj).intValue();
        } else if (i == SO_SND_TIMEOUT) {
            this.sndTimeout = ((Integer) obj).intValue();
        } else {
            try {
                this.netImpl.setSocketOption(((SocketImpl) this).fd, i, obj);
                if (i == 1 && (this.netImpl.getSocketFlags() & 4) != 0) {
                    this.tcpNoDelay = ((Boolean) obj).booleanValue();
                }
            } catch (SocketException e) {
                if (i != 3) {
                    throw e;
                }
            }
            if (i != 3) {
                return;
            }
            this.trafficClass = ((Integer) obj).intValue();
        }
    }

    public void setSessionId(String str) {
        this.netImpl.setSessionId(str);
    }

    @Override // java.net.SocketImpl
    protected void shutdownInput() throws IOException {
        this.shutdownInput = true;
        this.netImpl.shutdownInput(((SocketImpl) this).fd);
    }

    @Override // java.net.SocketImpl
    protected void shutdownOutput() throws IOException {
        this.netImpl.shutdownOutput(((SocketImpl) this).fd);
    }

    @Override // java.net.SocketImpl
    protected boolean supportsUrgentData() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int write(byte[] bArr, int i, int i2) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::write()\n");
        return this.netImpl.write(((SocketImpl) this).fd, bArr, i, i2);
    }

    public JavaNetSocketImpl(boolean z) {
        this.flagsAndDataCanBeQueried = false;
        this.cached_isDirectConnect = false;
        this.cached_currentRoute = "";
        this.cached_currentGPS = "";
        this.tcpNoDelay = true;
        this.netImpl = null;
        this.receiveTimeout = 0;
        this.sndTimeout = 0;
        this.streaming = true;
        GDLog.DBGPRINTF(17, TAG + "::JavaNetSocketImpl() 5 IN\n");
        ((SocketImpl) this).fd = new FileDescriptor();
        GDSocketImpl gDSocketImpl = new GDSocketImpl(z);
        this.netImpl = gDSocketImpl;
        gDSocketImpl.init();
    }

    @Override // java.net.SocketImpl
    protected void connect(InetAddress inetAddress, int i) throws IOException {
        GDLog.DBGPRINTF(17, TAG + "::connect( InetAddress=" + inetAddress + ", aPort=" + i + ") IN\n");
        internalConnect(inetAddress, inetAddress.getHostName(), i, 0);
    }

    @Override // java.net.SocketImpl
    protected void connect(SocketAddress socketAddress, int i) throws IOException {
        String hostNameFromInetSocketAddress;
        GDLog.DBGPRINTF(17, TAG + "::connect( SocketAddress=" + socketAddress + ", timeout=" + i + " ) IN\n");
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        InetAddress address = inetSocketAddress.getAddress();
        if (address != null && !address.isAnyLocalAddress()) {
            hostNameFromInetSocketAddress = address.getHostName();
        } else {
            hostNameFromInetSocketAddress = SocketReflectionUtils.getHostNameFromInetSocketAddress(inetSocketAddress);
        }
        if (address == null) {
            address = SocketReflectionUtils.createDummyInetAddress();
        }
        int port = inetSocketAddress.getPort();
        GDLog.DBGPRINTF(17, TAG + "::connect( ) socketAddr.host:" + hostNameFromInetSocketAddress + "\n");
        internalConnect(address, hostNameFromInetSocketAddress, port, i);
        GDLog.DBGPRINTF(17, TAG + "::connect( ) OUT\n");
    }

    public JavaNetSocketImpl(FileDescriptor fileDescriptor) {
        this.flagsAndDataCanBeQueried = false;
        this.cached_isDirectConnect = false;
        this.cached_currentRoute = "";
        this.cached_currentGPS = "";
        this.tcpNoDelay = true;
        this.netImpl = null;
        this.receiveTimeout = 0;
        this.sndTimeout = 0;
        this.streaming = true;
        GDLog.DBGPRINTF(17, TAG + "::JavaNetSocketImpl() 1a IN: fd=" + fileDescriptor + "\n");
        ((SocketImpl) this).fd = fileDescriptor;
        GDSocketImpl gDSocketImpl = new GDSocketImpl(IS_NOT_SECURE_DEFAULT);
        this.netImpl = gDSocketImpl;
        gDSocketImpl.init();
    }

    public JavaNetSocketImpl(Proxy proxy) {
        this();
        GDLog.DBGPRINTF(17, TAG + "::JavaNetSocketImpl() 2 IN\n");
        this.proxy = proxy;
    }

    public JavaNetSocketImpl(FileDescriptor fileDescriptor, int i, InetAddress inetAddress, int i2) {
        this.flagsAndDataCanBeQueried = false;
        this.cached_isDirectConnect = false;
        this.cached_currentRoute = "";
        this.cached_currentGPS = "";
        this.tcpNoDelay = true;
        this.netImpl = null;
        this.receiveTimeout = 0;
        this.sndTimeout = 0;
        this.streaming = true;
        GDLog.DBGPRINTF(17, TAG + "::JavaNetSocketImpl() 4 IN\n");
        ((SocketImpl) this).fd = fileDescriptor;
        ((SocketImpl) this).localport = i;
        ((SocketImpl) this).address = inetAddress;
        ((SocketImpl) this).port = i2;
        GDSocketImpl gDSocketImpl = new GDSocketImpl(IS_NOT_SECURE_DEFAULT);
        this.netImpl = gDSocketImpl;
        gDSocketImpl.init();
    }
}
