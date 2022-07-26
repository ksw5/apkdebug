package com.good.gd.apache.commons.codec.net;

import com.good.gd.apache.commons.codec.BinaryDecoder;
import com.good.gd.apache.commons.codec.BinaryEncoder;
import com.good.gd.apache.commons.codec.DecoderException;
import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringDecoder;
import com.good.gd.apache.commons.codec.StringEncoder;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/* loaded from: classes.dex */
public class URLCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder {
    protected static byte ESCAPE_CHAR = 37;
    protected static final BitSet WWW_FORM_URL = new BitSet(256);
    protected String charset;

    static {
        for (int i = 97; i <= 122; i++) {
            WWW_FORM_URL.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            WWW_FORM_URL.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            WWW_FORM_URL.set(i3);
        }
        BitSet bitSet = WWW_FORM_URL;
        bitSet.set(45);
        bitSet.set(95);
        bitSet.set(46);
        bitSet.set(42);
        bitSet.set(32);
    }

    public URLCodec() {
        this.charset = HTTP.UTF_8;
    }

    public static final byte[] decodeUrl(byte[] bArr) throws DecoderException {
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == 43) {
                byteArrayOutputStream.write(32);
            } else if (b == 37) {
                int i2 = i + 1;
                try {
                    int digit = Character.digit((char) bArr[i2], 16);
                    i = i2 + 1;
                    int digit2 = Character.digit((char) bArr[i], 16);
                    if (digit != -1 && digit2 != -1) {
                        byteArrayOutputStream.write((char) ((digit << 4) + digit2));
                    } else {
                        throw new DecoderException("Invalid URL encoding");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DecoderException("Invalid URL encoding");
                }
            } else {
                byteArrayOutputStream.write(b);
            }
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static final byte[] encodeUrl(BitSet bitSet, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bitSet == null) {
            bitSet = WWW_FORM_URL;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i];
            if (i2 < 0) {
                i2 += 256;
            }
            if (bitSet.get(i2)) {
                if (i2 == 32) {
                    i2 = 43;
                }
                byteArrayOutputStream.write(i2);
            } else {
                byteArrayOutputStream.write(37);
                char upperCase = Character.toUpperCase(Character.forDigit((i2 >> 4) & 15, 16));
                char upperCase2 = Character.toUpperCase(Character.forDigit(i2 & 15, 16));
                byteArrayOutputStream.write(upperCase);
                byteArrayOutputStream.write(upperCase2);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // com.good.gd.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) throws DecoderException {
        return decodeUrl(bArr);
    }

    @Override // com.good.gd.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        return encodeUrl(WWW_FORM_URL, bArr);
    }

    public String getDefaultCharset() {
        return this.charset;
    }

    @Deprecated
    public String getEncoding() {
        return this.charset;
    }

    public String decode(String str, String str2) throws DecoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(decode(str.getBytes("US-ASCII")), str2);
    }

    public String encode(String str, String str2) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(encode(str.getBytes(str2)), "US-ASCII");
    }

    public URLCodec(String str) {
        this.charset = HTTP.UTF_8;
        this.charset = str;
    }

    @Override // com.good.gd.apache.commons.codec.StringDecoder
    public String decode(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        try {
            return decode(str, getDefaultCharset());
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException(e.getMessage());
        }
    }

    @Override // com.good.gd.apache.commons.codec.StringEncoder
    public String encode(String str) throws EncoderException {
        if (str == null) {
            return null;
        }
        try {
            return encode(str, getDefaultCharset());
        } catch (UnsupportedEncodingException e) {
            throw new EncoderException(e.getMessage());
        }
    }

    @Override // com.good.gd.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be URL decoded");
    }

    @Override // com.good.gd.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be URL encoded");
    }
}
