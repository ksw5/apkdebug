package com.good.gd.apache.commons.codec.net;

import com.good.gd.apache.commons.codec.DecoderException;
import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringDecoder;
import com.good.gd.apache.commons.codec.StringEncoder;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/* loaded from: classes.dex */
public class QCodec extends RFC1522Codec implements StringEncoder, StringDecoder {
    private static byte BLANK;
    private static final BitSet PRINTABLE_CHARS;
    private static byte UNDERSCORE;
    private String charset;
    private boolean encodeBlanks;

    static {
        BitSet bitSet = new BitSet(256);
        PRINTABLE_CHARS = bitSet;
        bitSet.set(32);
        bitSet.set(33);
        bitSet.set(34);
        bitSet.set(35);
        bitSet.set(36);
        bitSet.set(37);
        bitSet.set(38);
        bitSet.set(39);
        bitSet.set(40);
        bitSet.set(41);
        bitSet.set(42);
        bitSet.set(43);
        bitSet.set(44);
        bitSet.set(45);
        bitSet.set(46);
        bitSet.set(47);
        for (int i = 48; i <= 57; i++) {
            PRINTABLE_CHARS.set(i);
        }
        BitSet bitSet2 = PRINTABLE_CHARS;
        bitSet2.set(58);
        bitSet2.set(59);
        bitSet2.set(60);
        bitSet2.set(62);
        bitSet2.set(64);
        for (int i2 = 65; i2 <= 90; i2++) {
            PRINTABLE_CHARS.set(i2);
        }
        BitSet bitSet3 = PRINTABLE_CHARS;
        bitSet3.set(91);
        bitSet3.set(92);
        bitSet3.set(93);
        bitSet3.set(94);
        bitSet3.set(96);
        for (int i3 = 97; i3 <= 122; i3++) {
            PRINTABLE_CHARS.set(i3);
        }
        BitSet bitSet4 = PRINTABLE_CHARS;
        bitSet4.set(123);
        bitSet4.set(124);
        bitSet4.set(125);
        bitSet4.set(126);
        BLANK = (byte) 32;
        UNDERSCORE = (byte) 95;
    }

    public QCodec() {
        this.charset = HTTP.UTF_8;
        this.encodeBlanks = false;
    }

    @Override // com.good.gd.apache.commons.codec.StringDecoder
    public String decode(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        try {
            return decodeText(str);
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException(e.getMessage());
        }
    }

    @Override // com.good.gd.apache.commons.codec.net.RFC1522Codec
    protected byte[] doDecoding(byte[] bArr) throws DecoderException {
        boolean z;
        if (bArr == null) {
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= bArr.length) {
                z = false;
                break;
            } else if (bArr[i] == UNDERSCORE) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            byte[] bArr2 = new byte[bArr.length];
            for (int i2 = 0; i2 < bArr.length; i2++) {
                byte b = bArr[i2];
                if (b != UNDERSCORE) {
                    bArr2[i2] = b;
                } else {
                    bArr2[i2] = BLANK;
                }
            }
            return QuotedPrintableCodec.decodeQuotedPrintable(bArr2);
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(bArr);
    }

    @Override // com.good.gd.apache.commons.codec.net.RFC1522Codec
    protected byte[] doEncoding(byte[] bArr) throws EncoderException {
        if (bArr == null) {
            return null;
        }
        byte[] encodeQuotedPrintable = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, bArr);
        if (this.encodeBlanks) {
            for (int i = 0; i < encodeQuotedPrintable.length; i++) {
                if (encodeQuotedPrintable[i] == BLANK) {
                    encodeQuotedPrintable[i] = UNDERSCORE;
                }
            }
        }
        return encodeQuotedPrintable;
    }

    public String encode(String str, String str2) throws EncoderException {
        if (str == null) {
            return null;
        }
        try {
            return encodeText(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new EncoderException(e.getMessage());
        }
    }

    public String getDefaultCharset() {
        return this.charset;
    }

    @Override // com.good.gd.apache.commons.codec.net.RFC1522Codec
    protected String getEncoding() {
        return "Q";
    }

    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }

    public void setEncodeBlanks(boolean z) {
        this.encodeBlanks = z;
    }

    @Override // com.good.gd.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using Q codec");
    }

    @Override // com.good.gd.apache.commons.codec.StringEncoder
    public String encode(String str) throws EncoderException {
        if (str == null) {
            return null;
        }
        return encode(str, getDefaultCharset());
    }

    @Override // com.good.gd.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using Q codec");
    }

    public QCodec(String str) {
        this.charset = HTTP.UTF_8;
        this.encodeBlanks = false;
        this.charset = str;
    }
}
