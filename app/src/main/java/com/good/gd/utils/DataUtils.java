package com.good.gd.utils;

import java.io.UTFDataFormatException;
import kotlin.UByte;

/* loaded from: classes.dex */
public class DataUtils {
    public static final int sizeof_double = 8;
    public static final int sizeof_float = 4;
    public static final int sizeof_int = 4;
    public static final int sizeof_long = 8;
    public static final int sizeof_short = 2;

    private DataUtils() {
    }

    public static int byteArrayToInt(byte[] bArr) {
        return ((bArr[0] & UByte.MAX_VALUE) << 24) + ((bArr[1] & UByte.MAX_VALUE) << 16) + ((bArr[2] & UByte.MAX_VALUE) << 8) + (bArr[3] & UByte.MAX_VALUE);
    }

    public static long byteArrayToLong(byte[] bArr) {
        return ((bArr[0] & UByte.MAX_VALUE) << 56) + ((bArr[1] & UByte.MAX_VALUE) << 48) + ((bArr[2] & UByte.MAX_VALUE) << 40) + ((bArr[3] & UByte.MAX_VALUE) << 32) + ((bArr[4] & UByte.MAX_VALUE) << 24) + ((bArr[5] & UByte.MAX_VALUE) << 16) + ((bArr[6] & UByte.MAX_VALUE) << 8) + (bArr[7] & UByte.MAX_VALUE);
    }

    public static short byteArrayToShort(byte[] bArr) {
        return (short) (((bArr[0] & UByte.MAX_VALUE) << 8) + (bArr[1] & UByte.MAX_VALUE));
    }

    public static long countBytes(String str, boolean z) throws UTFDataFormatException {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            j += (charAt == 0 || charAt > 127) ? charAt <= 2047 ? 2L : 3L : 1L;
            if (z && j > 65535) {
                throw new UTFDataFormatException("String more than 65535 UTF bytes long");
            }
        }
        return j;
    }

    public static String decode(byte[] bArr, char[] cArr, int i, int i2) throws UTFDataFormatException {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i3 + 1;
            char c = (char) bArr[i3 + i];
            cArr[i4] = c;
            if (c < 128) {
                i4++;
                i3 = i5;
            } else {
                char c2 = cArr[i4];
                if ((c2 & 224) == 192) {
                    if (i5 < i2) {
                        int i6 = i5 + 1;
                        byte b = bArr[i5 + i];
                        if ((b & 192) == 128) {
                            cArr[i4] = (char) (((c2 & 31) << 6) | (b & 63));
                            i4++;
                            i3 = i6;
                        } else {
                            throw new UTFDataFormatException("bad second byte at " + (i6 - 1));
                        }
                    } else {
                        throw new UTFDataFormatException("bad second byte at " + i5);
                    }
                } else if ((c2 & 240) != 224) {
                    throw new UTFDataFormatException("bad byte at " + (i5 - 1));
                } else {
                    int i7 = i5 + 1;
                    if (i7 < i2) {
                        byte b2 = bArr[i5 + i];
                        int i8 = i7 + 1;
                        byte b3 = bArr[i7 + i];
                        if ((b2 & 192) == 128 && (b3 & 192) == 128) {
                            cArr[i4] = (char) (((c2 & 15) << 12) | ((b2 & 63) << 6) | (b3 & 63));
                            i4++;
                            i3 = i8;
                        } else {
                            throw new UTFDataFormatException("bad second or third byte at " + (i8 - 2));
                        }
                    } else {
                        throw new UTFDataFormatException("bad third byte at " + i7);
                    }
                }
            }
        }
        return new String(cArr, 0, i4);
    }

    public static void encode(byte[] bArr, int i, String str) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt != 0 && charAt <= 127) {
                bArr[i] = (byte) charAt;
                i++;
            } else if (charAt <= 2047) {
                int i3 = i + 1;
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                i = i3 + 1;
                bArr[i3] = (byte) ((charAt & '?') | 128);
            } else {
                int i4 = i + 1;
                bArr[i] = (byte) (((charAt >> '\f') & 15) | 224);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (((charAt >> 6) & 63) | 128);
                bArr[i5] = (byte) ((charAt & '?') | 128);
                i = i5 + 1;
            }
        }
    }

    public static void intToByteArray(int i, byte[] bArr) {
        bArr[0] = (byte) ((i >> 24) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[3] = (byte) (i & 255);
    }

    public static void longToByteArray(long j, byte[] bArr) {
        bArr[0] = (byte) ((j >> 56) & 255);
        bArr[1] = (byte) ((j >> 48) & 255);
        bArr[2] = (byte) ((j >> 40) & 255);
        bArr[3] = (byte) ((j >> 32) & 255);
        bArr[4] = (byte) ((j >> 24) & 255);
        bArr[5] = (byte) ((j >> 16) & 255);
        bArr[6] = (byte) ((j >> 8) & 255);
        bArr[7] = (byte) (j & 255);
    }

    public static void shortToByteArray(int i, byte[] bArr) {
        bArr[0] = (byte) ((i >> 8) & 255);
        bArr[1] = (byte) (i & 255);
    }

    public static byte[] encode(String str) throws UTFDataFormatException {
        int countBytes = (int) countBytes(str, true);
        byte[] bArr = new byte[countBytes + 2];
        shortToByteArray(countBytes, bArr);
        encode(bArr, 2, str);
        return bArr;
    }
}
