package com.good.gd.apache.commons.codec.language;

import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringEncoder;

/* loaded from: classes.dex */
public class Soundex implements StringEncoder {
    @Deprecated
    private int maxLength;
    private char[] soundexMapping;
    public static final Soundex US_ENGLISH = new Soundex();
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    public static final char[] US_ENGLISH_MAPPING = US_ENGLISH_MAPPING_STRING.toCharArray();

    public Soundex() {
        this(US_ENGLISH_MAPPING);
    }

    private char getMappingCode(String str, int i) {
        char charAt;
        char map = map(str.charAt(i));
        if (i > 1 && map != '0' && ('H' == (charAt = str.charAt(i - 1)) || 'W' == charAt)) {
            char charAt2 = str.charAt(i - 2);
            if (map(charAt2) == map || 'H' == charAt2 || 'W' == charAt2) {
                return (char) 0;
            }
        }
        return map;
    }

    private char[] getSoundexMapping() {
        return this.soundexMapping;
    }

    private char map(char c) {
        int i = c - 'A';
        if (i >= 0 && i < getSoundexMapping().length) {
            return getSoundexMapping()[i];
        }
        throw new IllegalArgumentException("The character is not mapped: " + c);
    }

    private void setSoundexMapping(char[] cArr) {
        this.soundexMapping = cArr;
    }

    public int difference(String str, String str2) throws EncoderException {
        return hbfhc.dbjc(this, str, str2);
    }

    @Override // com.good.gd.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
    }

    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }

    @Deprecated
    public void setMaxLength(int i) {
        this.maxLength = i;
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String dbjc = hbfhc.dbjc(str);
        if (dbjc.length() == 0) {
            return dbjc;
        }
        char[] cArr = {'0', '0', '0', '0'};
        cArr[0] = dbjc.charAt(0);
        char mappingCode = getMappingCode(dbjc, 0);
        int i = 1;
        int i2 = 1;
        while (i < dbjc.length() && i2 < 4) {
            int i3 = i + 1;
            char mappingCode2 = getMappingCode(dbjc, i);
            if (mappingCode2 != 0) {
                if (mappingCode2 != '0' && mappingCode2 != mappingCode) {
                    cArr[i2] = mappingCode2;
                    i2++;
                }
                mappingCode = mappingCode2;
            }
            i = i3;
        }
        return new String(cArr);
    }

    public Soundex(char[] cArr) {
        this.maxLength = 4;
        setSoundexMapping(cArr);
    }

    @Override // com.good.gd.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return soundex(str);
    }
}
