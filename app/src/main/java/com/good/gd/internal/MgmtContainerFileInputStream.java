package com.good.gd.internal;

import com.good.gd.ndkproxy.file.FileInputStreamImpl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class MgmtContainerFileInputStream extends InputStream {
    private FileInputStreamImpl _impl;

    public MgmtContainerFileInputStream(String str) throws FileNotFoundException {
        this._impl = null;
        if (!GDSDKState.getInstance().isWiped()) {
            this._impl = new FileInputStreamImpl(str, 4);
            return;
        }
        throw new FileNotFoundException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.available();
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.close();
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return;
        }
        this._impl.mark(i);
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        if (this._impl == null || GDSDKState.getInstance().isWiped()) {
            return true;
        }
        return this._impl.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.read();
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.reset();
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.skip(j);
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.read(bArr);
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public MgmtContainerFileInputStream(File file) throws FileNotFoundException {
        this(file.getPath());
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            return this._impl.read(bArr, i, i2);
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }
}
