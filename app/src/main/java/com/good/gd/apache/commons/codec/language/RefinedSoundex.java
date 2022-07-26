package com.good.gd.apache.commons.codec.language;

import com.good.gd.apache.commons.codec.EncoderException;
import com.good.gd.apache.commons.codec.StringEncoder;

/* loaded from: classes.dex */
public class RefinedSoundex implements StringEncoder {
    public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();
    public static final char[] US_ENGLISH_MAPPING = "01360240043788015936020505".toCharArray();
    private char[] soundexMapping;

    public RefinedSoundex() {
        this(US_ENGLISH_MAPPING);
    }

    public int difference(String str, String str2) throws EncoderException {
        return hbfhc.dbjc(this, str, str2);
    }

    @Override // com.good.gd.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return soundex((String) obj);
        }
        throw new EncoderException("Parameter supplied to RefinedSoundex encode is not of type java.lang.String");
    }

    char getMappingCode(char c) {
        if (!Character.isLetter(c)) {
            return (char) 0;
        }
        return this.soundexMapping[Character.toUpperCase(c) - 'A'];
    }

    public String soundex(String str) {
        if (str == null) {
            return null;
        }
        String dbjc = hbfhc.dbjc(str);
        if (dbjc.length() == 0) {
            return dbjc;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(dbjc.charAt(0));
        char c = '*';
        for (int i = 0; i < dbjc.length(); i++) {
            char mappingCode = getMappingCode(dbjc.charAt(i));
            if (mappingCode != c) {
                if (mappingCode != 0) {
                    stringBuffer.append(mappingCode);
                }
                c = mappingCode;
            }
        }
        return stringBuffer.toString();
    }

    public RefinedSoundex(char[] cArr) {
        this.soundexMapping = cArr;
    }

    @Override // com.good.gd.apache.commons.codec.StringEncoder
    public String encode(String str) {
        return soundex(str);
    }
}
