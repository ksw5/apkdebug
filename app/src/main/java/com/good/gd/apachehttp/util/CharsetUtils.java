package com.good.gd.apachehttp.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: classes.dex */
public class CharsetUtils {
    public static Charset get(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        try {
            return Charset.forName(str);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(str);
        }
    }

    public static Charset lookup(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Charset.forName(str);
        } catch (UnsupportedCharsetException e) {
            return null;
        }
    }
}
