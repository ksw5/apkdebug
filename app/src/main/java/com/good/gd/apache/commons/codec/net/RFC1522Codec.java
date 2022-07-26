package com.good.gd.apache.commons.codec.net;

import com.good.gd.apache.commons.codec.DecoderException;
import com.good.gd.apache.commons.codec.EncoderException;
import java.io.UnsupportedEncodingException;

@Deprecated
/* loaded from: classes.dex */
public abstract class RFC1522Codec {
    /* JADX INFO: Access modifiers changed from: protected */
    public String decodeText(String str) throws DecoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        if (str.startsWith("=?") && str.endsWith("?=")) {
            int length = str.length() - 2;
            int indexOf = str.indexOf("?", 2);
            if (indexOf != -1 && indexOf != length) {
                String substring = str.substring(2, indexOf);
                if (!substring.equals("")) {
                    int i = indexOf + 1;
                    int indexOf2 = str.indexOf("?", i);
                    if (indexOf2 != -1 && indexOf2 != length) {
                        String substring2 = str.substring(i, indexOf2);
                        if (getEncoding().equalsIgnoreCase(substring2)) {
                            int i2 = indexOf2 + 1;
                            return new String(doDecoding(str.substring(i2, str.indexOf("?", i2)).getBytes("US-ASCII")), substring);
                        }
                        throw new DecoderException("This codec cannot decode " + substring2 + " encoded content");
                    }
                    throw new DecoderException("RFC 1522 violation: encoding token not found");
                }
                throw new DecoderException("RFC 1522 violation: charset not specified");
            }
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        throw new DecoderException("RFC 1522 violation: malformed encoded content");
    }

    protected abstract byte[] doDecoding(byte[] bArr) throws DecoderException;

    protected abstract byte[] doEncoding(byte[] bArr) throws EncoderException;

    /* JADX INFO: Access modifiers changed from: protected */
    public String encodeText(String str, String str2) throws EncoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("=?");
        stringBuffer.append(str2);
        stringBuffer.append('?');
        stringBuffer.append(getEncoding());
        stringBuffer.append('?');
        stringBuffer.append(new String(doEncoding(str.getBytes(str2)), "US-ASCII"));
        stringBuffer.append("?=");
        return stringBuffer.toString();
    }

    protected abstract String getEncoding();
}
