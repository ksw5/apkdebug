package com.good.gd.ndkproxy.file;

import com.good.gd.error.GDError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import java.io.FileNotFoundException;

/* loaded from: classes.dex */
public final class FileOutputStreamImpl {
    private long m_GSCFileWriterV2_ptr;
    private boolean m_append;
    private final int m_containerType;
    private boolean m_open;
    private String m_path;

    public FileOutputStreamImpl(String str, boolean z) throws FileNotFoundException {
        this(str, z, 2);
    }

    private native void NDK_close(long j);

    private native void NDK_flush(long j);

    private native long NDK_open(String str, boolean z, int i);

    private native void NDK_write(long j, byte b);

    private native void NDK_write(long j, byte[] bArr);

    private native void NDK_write(long j, byte[] bArr, int i, int i2);

    private native boolean NDK_writerValid(long j);

    private void finish() {
        GDLog.DBGPRINTF(16, "FileOutputStreamImpl::finish() IN\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                finishNative();
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "FileOutputStreamImpl::finish(): ", e);
        }
        GDLog.DBGPRINTF(16, "FileOutputStreamImpl::finish(): peer deinitialized\n");
    }

    private native void finishNative();

    private void init() {
        GDLog.DBGPRINTF(16, "FileOutputStreamImpl::init(): Attempting to initialize C++ peer\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                ndkInit();
            }
        } catch (GDError e) {
            GDLog.DBGPRINTF(12, "FileOutputStreamImpl::init(): Cannot initialize C++ peer (authorize not called)", e);
            throw e;
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "FileOutputStreamImpl::init(): Cannot initialize C++ peer", th);
        }
        GDLog.DBGPRINTF(16, "FileOutputStreamImpl::init(): peer initialized\n");
    }

    private native void ndkInit();

    private boolean open() {
        boolean z;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (this.m_GSCFileWriterV2_ptr == 0) {
                long NDK_open = NDK_open(this.m_path, this.m_append, this.m_containerType);
                if (NDK_writerValid(NDK_open)) {
                    this.m_GSCFileWriterV2_ptr = NDK_open;
                }
            }
            z = this.m_GSCFileWriterV2_ptr != 0;
        }
        return z;
    }

    public void close() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_close(this.m_GSCFileWriterV2_ptr);
            this.m_GSCFileWriterV2_ptr = 0L;
            this.m_open = false;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.m_open) {
                GDLog.DBGPRINTF(12, "FileOutputStreamImpl::finalize file left open");
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public void flush() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_flush(this.m_GSCFileWriterV2_ptr);
        }
    }

    public void write(int i) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_write(this.m_GSCFileWriterV2_ptr, (byte) i);
        }
    }

    public FileOutputStreamImpl(String str, boolean z, int i) throws FileNotFoundException {
        this.m_path = null;
        this.m_append = false;
        this.m_open = false;
        this.m_GSCFileWriterV2_ptr = 0L;
        this.m_path = str;
        this.m_append = z;
        this.m_containerType = i;
        init();
        if (open()) {
            this.m_open = true;
            return;
        }
        throw new FileNotFoundException();
    }

    public void write(byte[] bArr) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (bArr != null) {
                NDK_write(this.m_GSCFileWriterV2_ptr, bArr);
            } else {
                throw new NullPointerException();
            }
        }
    }

    public void write(byte[] bArr, int i, int i2) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (i + i2 <= bArr.length) {
                NDK_write(this.m_GSCFileWriterV2_ptr, bArr, i, i2);
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }
}
