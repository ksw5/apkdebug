package com.good.gd.ndkproxy.file;

import com.good.gd.error.GDError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes.dex */
public final class FileInputStreamImpl {
    private long m_FileReaderDetails_ptr;
    private final int m_containerType;
    private boolean m_open;
    private String m_path;

    public FileInputStreamImpl(String str) throws FileNotFoundException {
        this(str, 2);
    }

    private native int NDK_available(long j);

    private native void NDK_close(long j);

    private native long NDK_open(String str, int i);

    private native int NDK_read(long j);

    private native int NDK_read(long j, byte[] bArr);

    private native int NDK_read(long j, byte[] bArr, int i, int i2);

    private native boolean NDK_readerValid(long j);

    private native long NDK_skip(long j, long j2);

    private void finish() {
        GDLog.DBGPRINTF(16, "FileInputStreamImpl::finish() IN\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                finishNative();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "FileInputStreamImpl::finish(): ", e);
        }
        GDLog.DBGPRINTF(16, "FileInputStreamImpl::finish(): peer deinitialized\n");
    }

    private native void finishNative();

    private void init() {
        GDLog.DBGPRINTF(16, "FileInputStreamImpl::init(): Attempting to initialize C++ peer\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (GDError e) {
            GDLog.DBGPRINTF(12, "FileInputStreamImpl::init(): Cannot initialize C++ peer (authorize not called)", e);
            throw e;
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "FileInputStreamImpl::init(): Cannot initialize C++ peer", th);
        }
        GDLog.DBGPRINTF(16, "FileInputStreamImpl::init(): peer initialized\n");
    }

    private native void ndkInit();

    private boolean open() {
        boolean z;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            z = true;
            if (this.m_FileReaderDetails_ptr == 0) {
                long NDK_open = NDK_open(this.m_path, this.m_containerType);
                if (NDK_open != 0 && NDK_readerValid(NDK_open)) {
                    this.m_FileReaderDetails_ptr = NDK_open;
                }
            }
            if (this.m_FileReaderDetails_ptr == 0) {
                z = false;
            }
        }
        return z;
    }

    public int available() throws IOException {
        int NDK_available;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_available = NDK_available(this.m_FileReaderDetails_ptr);
        }
        return NDK_available;
    }

    public void close() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_close(this.m_FileReaderDetails_ptr);
            this.m_FileReaderDetails_ptr = 0L;
            this.m_open = false;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.m_open) {
                GDLog.DBGPRINTF(12, "FileInputStreamImpl::finalize file left open");
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public void mark(int i) {
    }

    public boolean markSupported() {
        return false;
    }

    public int read() {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_read = NDK_read(this.m_FileReaderDetails_ptr);
        }
        return NDK_read;
    }

    public void reset() throws IOException {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            throw new IOException();
        }
    }

    public long skip(long j) throws IOException {
        long NDK_skip;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (j >= 0) {
                NDK_skip = NDK_skip(this.m_FileReaderDetails_ptr, j);
            } else {
                throw new IOException("cannot skip < 0");
            }
        }
        return NDK_skip;
    }

    public FileInputStreamImpl(String str, int i) throws FileNotFoundException {
        this.m_path = null;
        this.m_open = false;
        this.m_FileReaderDetails_ptr = 0L;
        this.m_path = str;
        this.m_containerType = i;
        init();
        if (open()) {
            this.m_open = true;
            return;
        }
        throw new FileNotFoundException();
    }

    public int read(byte[] bArr) {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_read = NDK_read(this.m_FileReaderDetails_ptr, bArr);
        }
        return NDK_read;
    }

    public int read(byte[] bArr, int i, int i2) {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (i + i2 <= bArr.length) {
                NDK_read = NDK_read(this.m_FileReaderDetails_ptr, bArr, i, i2);
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return NDK_read;
    }
}
