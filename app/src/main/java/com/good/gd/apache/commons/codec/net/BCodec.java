package com.good.gd.apache.commons.codec.net;

import com.good.gd.apache.commons.codec.DecoderException;
import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringDecoder;
import com.good.gd.apache.commons.codec.StringEncoder;
import com.good.gd.apache.commons.codec.binary.Base64;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class BCodec extends RFC1522Codec implements StringEncoder, StringDecoder {
    private String charset;

    public BCodec() {
        this.charset = HTTP.UTF_8;
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
        if (bArr == null) {
            return null;
        }
        return Base64.decodeBase64(bArr);
    }

    @Override // com.good.gd.apache.commons.codec.net.RFC1522Codec
    protected byte[] doEncoding(byte[] bArr) throws EncoderException {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeBase64(bArr);
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
        return "B";
    }

    public BCodec(String str) {
        this.charset = HTTP.UTF_8;
        this.charset = str;
    }

    @Override // com.good.gd.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using BCodec");
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
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using BCodec");
    }
}
