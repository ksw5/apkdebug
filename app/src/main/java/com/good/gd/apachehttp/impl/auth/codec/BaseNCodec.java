package com.good.gd.apachehttp.impl.auth.codec;

import com.good.gd.apachehttp.impl.auth.StringUtils;

/* loaded from: classes.dex */
public abstract class BaseNCodec {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    protected final byte PAD = PAD_DEFAULT;
    protected byte[] buffer;
    private final int chunkSeparatorLength;
    protected int currentLinePos;
    private final int encodedBlockSize;
    protected boolean eof;
    protected final int lineLength;
    protected int modulus;
    protected int pos;
    private int readPos;
    private final int unencodedBlockSize;

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseNCodec(int i, int i2, int i3, int i4) {
        this.unencodedBlockSize = i;
        this.encodedBlockSize = i2;
        this.lineLength = (i3 <= 0 || i4 <= 0) ? 0 : (i3 / i2) * i2;
        this.chunkSeparatorLength = i4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static boolean isWhiteSpace(byte b) {
        return b == 9 || b == 10 || b == 13 || b == 32;
    }

    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }

    private void resizeBuffer() {
        byte[] bArr = this.buffer;
        if (bArr == null) {
            this.buffer = new byte[getDefaultBufferSize()];
            this.pos = 0;
            this.readPos = 0;
            return;
        }
        byte[] bArr2 = new byte[bArr.length * 2];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        this.buffer = bArr2;
    }

    int available() {
        if (this.buffer != null) {
            return this.pos - this.readPos;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean containsAlphabetOrPad(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if (61 == b || isInAlphabet(b)) {
                return true;
            }
        }
        return false;
    }

    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    abstract void decode(byte[] bArr, int i, int i2);

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    abstract void encode(byte[] bArr, int i, int i2);

    public String encodeAsString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String encodeToString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void ensureBufferSize(int i) {
        byte[] bArr = this.buffer;
        if (bArr == null || bArr.length < this.pos + i) {
            resizeBuffer();
        }
    }

    protected int getDefaultBufferSize() {
        return 8192;
    }

    public long getEncodedLength(byte[] bArr) {
        int length = bArr.length;
        int i = this.unencodedBlockSize;
        long j = (((length + i) - 1) / i) * this.encodedBlockSize;
        int i2 = this.lineLength;
        if (i2 > 0) {
            long j2 = i2;
            return j + ((((j + j2) - 1) / j2) * this.chunkSeparatorLength);
        }
        return j;
    }

    boolean hasData() {
        return this.buffer != null;
    }

    protected abstract boolean isInAlphabet(byte b);

    public boolean isInAlphabet(byte[] bArr, boolean z) {
        for (int i = 0; i < bArr.length; i++) {
            if (!isInAlphabet(bArr[i]) && (!z || (bArr[i] != 61 && !isWhiteSpace(bArr[i])))) {
                return false;
            }
        }
        return true;
    }

    int readResults(byte[] bArr, int i, int i2) {
        if (this.buffer == null) {
            return this.eof ? -1 : 0;
        }
        int min = Math.min(available(), i2);
        System.arraycopy(this.buffer, this.readPos, bArr, i, min);
        int i3 = this.readPos + min;
        this.readPos = i3;
        if (i3 >= this.pos) {
            this.buffer = null;
        }
        return min;
    }

    public boolean isInAlphabet(String str) {
        return isInAlphabet(StringUtils.getBytesUtf8(str), true);
    }

    public byte[] encode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        encode(bArr, 0, bArr.length);
        encode(bArr, 0, -1);
        int i = this.pos - this.readPos;
        byte[] bArr2 = new byte[i];
        readResults(bArr2, 0, i);
        return bArr2;
    }

    public byte[] decode(String str) {
        return decode(StringUtils.getBytesUtf8(str));
    }

    public byte[] decode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        decode(bArr, 0, bArr.length);
        decode(bArr, 0, -1);
        int i = this.pos;
        byte[] bArr2 = new byte[i];
        readResults(bArr2, 0, i);
        return bArr2;
    }
}
