package com.good.gd.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class SensitiveDataUtils {
    public static char[] append(char[] cArr, char[] cArr2) {
        int length = cArr.length + cArr2.length;
        char[] cArr3 = new char[length];
        System.arraycopy(cArr, 0, cArr3, 0, cArr.length);
        System.arraycopy(cArr2, 0, cArr3, cArr.length, length - cArr.length);
        shredSensitiveData(cArr);
        shredSensitiveData(cArr2);
        return cArr3;
    }

    public static void callGarbageCollection() {
        Runtime.getRuntime().gc();
    }

    public static char[] charSequenceToCharArray(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = charSequence.charAt(i);
        }
        return cArr;
    }

    public static boolean isEmpty(char[] cArr) {
        return cArr == null || cArr.length == 0;
    }

    public static void shredSensitiveData(char[] cArr) {
        Arrays.fill(cArr, 'x');
    }

    public static char[] toLowerCase(char[] cArr) {
        for (int i = 0; i < cArr.length; i++) {
            cArr[i] = Character.toLowerCase(cArr[i]);
        }
        return cArr;
    }

    public static void shredSensitiveData(byte[] bArr) {
        Arrays.fill(bArr, (byte) 120);
    }

    public static char[] append(char[]... cArr) {
        char[] cArr2 = new char[0];
        for (char[] cArr3 : cArr) {
            cArr2 = append(cArr2, cArr3);
        }
        return cArr2;
    }
}
