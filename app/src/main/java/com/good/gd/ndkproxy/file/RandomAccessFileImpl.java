package com.good.gd.ndkproxy.file;

import com.good.gd.error.GDError;
import com.good.gd.file.File;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.utils.DataUtils;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import kotlin.UShort;

/* loaded from: classes.dex */
public final class RandomAccessFileImpl implements DataInput, DataOutput {
    public static final char separatorChar = '/';
    private long m_handle;
    private String m_path;
    private final byte[] scratch = new byte[8];

    public RandomAccessFileImpl(String str) {
        this.m_path = null;
        this.m_path = str;
        init();
    }

    private native void NDK_close(long j);

    private native boolean NDK_fpValid(long j);

    private native long NDK_getFilePointer(long j);

    private native int NDK_read(long j);

    private native int NDK_read(long j, byte[] bArr);

    private native int NDK_read(long j, byte[] bArr, int i, int i2);

    private native int NDK_readFully(long j, byte[] bArr);

    private native int NDK_readFully(long j, byte[] bArr, int i, int i2);

    private native void NDK_seek(long j, long j2);

    private native void NDK_truncate(long j, long j2);

    private native void NDK_write(long j, byte b);

    private native void NDK_write(long j, byte[] bArr);

    private native void NDK_write(long j, byte[] bArr, int i, int i2);

    private void finish() {
        GDLog.DBGPRINTF(16, "FileImpl::finish() IN\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                finishNative(this.m_handle);
            }
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "FileImpl::finish(): ", e);
        }
        GDLog.DBGPRINTF(16, "FileImpl::finish(): peer deinitialized\n");
    }

    private native void finishNative(long j);

    private void init() {
        GDLog.DBGPRINTF(16, "FileImpl::init(): Attempting to initialize C++ peer\n");
        try {
            synchronized (NativeExecutionHandler.nativeLockApi) {
                if (this.m_handle == 0) {
                    long ndkInit = ndkInit(this.m_path);
                    if (!NDK_fpValid(ndkInit)) {
                        GDLog.DBGPRINTF(12, "FileImpl::init(): Cannot initialize C++ peer (ndkInit failed) error=" + ndkInit);
                        throw new RuntimeException("RandomAccessFileImpl creation failed: " + ndkInit);
                    } else {
                        this.m_handle = ndkInit;
                        GDLog.DBGPRINTF(16, "FileImpl::init(): peer initialized\n");
                    }
                }
            }
            GDLog.DBGPRINTF(16, "FileImpl::init()\n");
        } catch (GDError e) {
            GDLog.DBGPRINTF(12, "FileImpl::init(): Cannot initialize C++ peer (authorize not called)", e);
            throw e;
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "FileImpl::init(): Cannot initialize C++ peer (Throwable)", th);
            throw new RuntimeException(th);
        }
    }

    private native long ndkInit(String str);

    public void close() {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_close(this.m_handle);
            this.m_handle = 0L;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.m_handle > 0) {
                GDLog.DBGPRINTF(12, "RandomAccessFileImpl::finalize file left open");
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public long getFilePointer() {
        long NDK_getFilePointer;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_getFilePointer = NDK_getFilePointer(this.m_handle);
        }
        return NDK_getFilePointer;
    }

    public long length() {
        return new File(this.m_path).length();
    }

    public int read() {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_read = NDK_read(this.m_handle);
        }
        return NDK_read;
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        int read = read();
        if (read >= 0) {
            return read != 0;
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        int read = read();
        if (read >= 0) {
            return (byte) read;
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        return (char) readShort();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_readFully(this.m_handle, bArr);
        }
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        readFully(this.scratch, 0, 4);
        return DataUtils.byteArrayToInt(this.scratch);
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        StringBuilder sb = new StringBuilder(80);
        boolean z = false;
        long j = 0;
        while (true) {
            int read = read();
            if (read == -1) {
                if (sb.length() == 0) {
                    return null;
                }
                return sb.toString();
            } else if (read == 10) {
                return sb.toString();
            } else {
                if (read != 13) {
                    if (z) {
                        seek(j);
                        return sb.toString();
                    }
                    sb.append((char) read);
                } else if (z) {
                    seek(j);
                    return sb.toString();
                } else {
                    z = true;
                    j = getFilePointer();
                }
            }
        }
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        readFully(this.scratch, 0, 8);
        return DataUtils.byteArrayToLong(this.scratch);
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        readFully(this.scratch, 0, 2);
        return DataUtils.byteArrayToShort(this.scratch);
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        int readUnsignedShort = readUnsignedShort();
        if (readUnsignedShort == 0) {
            return "";
        }
        byte[] bArr = new byte[readUnsignedShort];
        if (read(bArr, 0, readUnsignedShort) == readUnsignedShort) {
            return DataUtils.decode(bArr, new char[readUnsignedShort], 0, readUnsignedShort);
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        int read = read();
        if (read >= 0) {
            return read;
        }
        throw new EOFException();
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        return readShort() & UShort.MAX_VALUE;
    }

    public void seek(long j) throws IOException {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (j >= 0) {
                NDK_seek(this.m_handle, j);
            } else {
                throw new IOException("cannot seek < 0");
            }
        }
    }

    public void setLength(long j) throws IOException {
        if (j == length()) {
            return;
        }
        if (j > length()) {
            byte[] bArr = new byte[5120];
            long filePointer = getFilePointer();
            seek(length());
            long length = j - length();
            while (length > 0) {
                long j2 = 5120;
                long j3 = length > j2 ? j2 : length;
                NDK_write(this.m_handle, bArr, 0, (int) j3);
                length -= j3;
            }
            seek(filePointer);
        } else if (j >= length()) {
        } else {
            NDK_truncate(this.m_handle, j);
        }
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            long j = this.m_handle;
            NDK_seek(j, NDK_getFilePointer(j) + i);
        }
        return i;
    }

    @Override // java.io.DataOutput
    public void write(int i) throws IOException {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_write(this.m_handle, (byte) i);
        }
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws IOException {
        write(z ? 1 : 0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i) throws IOException {
        write(i & 255);
    }

    @Override // java.io.DataOutput
    public void writeBytes(String str) throws IOException {
        byte[] bArr = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            bArr[i] = (byte) (str.charAt(i) & 255);
        }
        write(bArr);
    }

    @Override // java.io.DataOutput
    public void writeChar(int i) throws IOException {
        writeShort(i);
    }

    @Override // java.io.DataOutput
    public void writeChars(String str) throws IOException {
        write(str.getBytes("UTF-16BE"));
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d) throws IOException {
        writeLong(Double.doubleToLongBits(d));
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f) throws IOException {
        writeInt(Float.floatToIntBits(f));
    }

    @Override // java.io.DataOutput
    public void writeInt(int i) throws IOException {
        DataUtils.intToByteArray(i, this.scratch);
        write(this.scratch, 0, 4);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j) throws IOException {
        DataUtils.longToByteArray(j, this.scratch);
        write(this.scratch, 0, 8);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i) throws IOException {
        DataUtils.shortToByteArray(i, this.scratch);
        write(this.scratch, 0, 2);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        write(DataUtils.encode(str));
    }

    public int read(byte[] bArr, int i, int i2) {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_read = NDK_read(this.m_handle, bArr, i, i2);
        }
        return NDK_read;
    }

    @Override // java.io.DataInput
    public void readFully(byte[] bArr, int i, int i2) {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_readFully(this.m_handle, bArr, i, i2);
        }
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (bArr != null) {
                NDK_write(this.m_handle, bArr);
            } else {
                throw new NullPointerException();
            }
        }
    }

    public int read(byte[] bArr) {
        int NDK_read;
        synchronized (NativeExecutionHandler.nativeLockApi) {
            NDK_read = NDK_read(this.m_handle, bArr);
        }
        return NDK_read;
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        synchronized (NativeExecutionHandler.nativeLockApi) {
            if (i + i2 <= bArr.length) {
                NDK_write(this.m_handle, bArr, i, i2);
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }
}
