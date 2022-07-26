package com.good.gd.apache.commons.codec.language;

import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringEncoder;
import java.util.Locale;

@Deprecated
/* loaded from: classes.dex */
final class hbfhc {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String dbjc(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isLetter(str.charAt(i2))) {
                cArr[i] = str.charAt(i2);
                i++;
            }
        }
        if (i == length) {
            return str.toUpperCase(Locale.ENGLISH);
        }
        return new String(cArr, 0, i).toUpperCase(Locale.ENGLISH);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int dbjc(StringEncoder stringEncoder, String str, String str2) throws EncoderException {
        String encode = stringEncoder.encode(str);
        String encode2 = stringEncoder.encode(str2);
        if (encode == null || encode2 == null) {
            return 0;
        }
        int min = Math.min(encode.length(), encode2.length());
        int i = 0;
        for (int i2 = 0; i2 < min; i2++) {
            if (encode.charAt(i2) == encode2.charAt(i2)) {
                i++;
            }
        }
        return i;
    }
}
