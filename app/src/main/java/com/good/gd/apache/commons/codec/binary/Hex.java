package com.good.gd.apache.commons.codec.binary;

import com.good.gd.apache.commons.codec.BinaryDecoder;
import com.good.gd.apache.commons.codec.BinaryEncoder;
import com.good.gd.apache.commons.codec.DecoderException;
import com.good.gd.apache.commons.codec.EncoderException;

/* loaded from: classes.dex */
public class Hex implements BinaryEncoder, BinaryDecoder {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static byte[] decodeHex(char[] cArr) throws DecoderException {
        int length = cArr.length;
        if ((length & 1) == 0) {
            byte[] bArr = new byte[length >> 1];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i + 1;
                i = i3 + 1;
                bArr[i2] = (byte) (((toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3)) & 255);
                i2++;
            }
            return bArr;
        }
        throw new DecoderException("Odd number of characters.");
    }

    public static char[] encodeHex(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            char[] cArr2 = DIGITS;
            cArr[i] = cArr2[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = cArr2[bArr[i2] & 15];
        }
        return cArr;
    }

    protected static int toDigit(char c, int i) throws DecoderException {
        int digit = Character.digit(c, 16);
        if (digit != -1) {
            return digit;
        }
        throw new DecoderException("Illegal hexadecimal charcter " + c + " at index " + i);
    }

    @Override // com.good.gd.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) throws DecoderException {
        return decodeHex(new String(bArr).toCharArray());
    }

    @Override // com.good.gd.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        return new String(encodeHex(bArr)).getBytes();
    }

    @Override // com.good.gd.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        try {
            return decodeHex(obj instanceof String ? ((String) obj).toCharArray() : (char[]) obj);
        } catch (ClassCastException e) {
            throw new DecoderException(e.getMessage());
        }
    }

    @Override // com.good.gd.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        try {
            return encodeHex(obj instanceof String ? ((String) obj).getBytes() : (byte[]) obj);
        } catch (ClassCastException e) {
            throw new EncoderException(e.getMessage());
        }
    }
}
