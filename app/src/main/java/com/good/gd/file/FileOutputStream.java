package com.good.gd.file;

import com.good.gd.ndkproxy.file.FileOutputStreamImpl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class FileOutputStream extends OutputStream {
    private FileOutputStreamImpl _impl;

    public FileOutputStream(File file) throws FileNotFoundException {
        this(file.getPath());
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.close();
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.flush();
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.write(i);
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public FileOutputStream(File file, boolean z) throws FileNotFoundException {
        this(file.getPath(), z);
    }

    public FileOutputStream(String str) throws FileNotFoundException {
        this._impl = null;
        if (!GDSDKState.getInstance().isWiped()) {
            this._impl = new FileOutputStreamImpl(str, false);
            return;
        }
        throw new FileNotFoundException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.write(bArr);
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this._impl != null && !GDSDKState.getInstance().isWiped()) {
            this._impl.write(bArr, i, i2);
            return;
        }
        throw new IOException(ErrorUtils.CONTAINER_WIPED_MSG);
    }

    public FileOutputStream(String str, boolean z) throws FileNotFoundException {
        this._impl = null;
        if (!GDSDKState.getInstance().isWiped()) {
            this._impl = new FileOutputStreamImpl(str, z);
            return;
        }
        throw new FileNotFoundException(ErrorUtils.CONTAINER_WIPED_MSG);
    }
}
