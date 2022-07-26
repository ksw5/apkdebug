package com.good.gd.file;

import com.good.gd.ndkproxy.file.RandomAccessFileImpl;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes.dex */
public class RandomAccessFile implements DataInput, DataOutput, Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private RandomAccessFileImpl _impl;

    public RandomAccessFile(File file, String str) throws FileNotFoundException {
        this(file.getPath(), str);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.close();
    }

    public long getFilePointer() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.getFilePointer();
    }

    public long length() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.length();
    }

    public int read() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.read();
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readBoolean();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readByte();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readChar();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readDouble();
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readFloat();
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.readFully(bArr);
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readInt();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readLine();
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readLong();
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readShort();
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readUTF();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readUnsignedByte();
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.readUnsignedShort();
    }

    public void seek(long j) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.seek(j);
    }

    public void setLength(long j) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.setLength(j);
    }

    @Override // java.io.DataInput
    public int skipBytes(int i) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.skipBytes(i);
    }

    @Override // java.io.DataOutput
    public void write(int i) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.write(i);
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean z) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeBoolean(z);
    }

    @Override // java.io.DataOutput
    public void writeByte(int i) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.write(i);
    }

    @Override // java.io.DataOutput
    public void writeBytes(String str) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeBytes(str);
    }

    @Override // java.io.DataOutput
    public void writeChar(int i) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeChar(i);
    }

    @Override // java.io.DataOutput
    public void writeChars(String str) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeChars(str);
    }

    @Override // java.io.DataOutput
    public void writeDouble(double d) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeDouble(d);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float f) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeFloat(f);
    }

    @Override // java.io.DataOutput
    public void writeInt(int i) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.writeInt(i);
    }

    @Override // java.io.DataOutput
    public void writeLong(long j) throws IOException {
        this._impl.writeLong(j);
    }

    @Override // java.io.DataOutput
    public void writeShort(int i) throws IOException {
        this._impl.writeShort(i);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        this._impl.writeUTF(str);
    }

    public RandomAccessFile(String str, String str2) throws FileNotFoundException {
        this._impl = null;
        this._impl = new RandomAccessFileImpl(str);
    }

    public int read(byte[] bArr) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.read(bArr);
    }

    @Override // java.io.DataInput
    public final void readFully(byte[] bArr, int i, int i2) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.readFully(bArr, i, i2);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.write(bArr);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        return randomAccessFileImpl.read(bArr, i, i2);
    }

    @Override // java.io.DataOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        RandomAccessFileImpl randomAccessFileImpl = this._impl;
        if (randomAccessFileImpl == null) {
            throw new AssertionError();
        }
        randomAccessFileImpl.write(bArr, i, i2);
    }
}
