package com.good.gd.ndkproxy.native2javabridges.utils;

import com.good.gd.ndkproxy.native2javabridges.utils.StringCompareUtils;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class StringUtils {
    private static Map<Integer, Pattern> sRegexPatterns = new HashMap();

    public static int customLengthFunction(String str) {
        String normalize = normalize(str);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < normalize.length(); i2++) {
            int codePointAt = normalize.codePointAt(i2);
            int type = Character.getType(codePointAt);
            if (sb.length() > 0 && type != 6 && type != 8 && type != 15 && type != 28) {
                i++;
                sb.delete(0, sb.length());
            }
            sb.appendCodePoint(codePointAt);
        }
        return sb.length() > 0 ? i + 1 : i;
    }

    public static boolean equals(String str, String str2, int i) {
        return StringCompareUtils.getStringComparator(i).equals(str, str2);
    }

    public static boolean match(String str, StringList stringList, int i) {
        StringCompareUtils.StringComparator stringComparator = StringCompareUtils.getStringComparator(i);
        Iterator<String> it = stringList.iterator();
        while (it.hasNext()) {
            if (stringComparator.equals(str, it.next())) {
                return true;
            }
        }
        return false;
    }

    public static String normalize(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFC);
    }

    public static int regexPatternCompile(String str) {
        int nextInt;
        do {
            nextInt = ThreadLocalRandom.current().nextInt();
        } while (sRegexPatterns.containsKey(Integer.valueOf(nextInt)));
        sRegexPatterns.put(Integer.valueOf(nextInt), Pattern.compile(str));
        return nextInt;
    }

    public static StringList regexPatternMatch(int i, String str) {
        StringList stringList = new StringList();
        Matcher matcher = sRegexPatterns.get(Integer.valueOf(i)).matcher(str);
        while (matcher.find()) {
            stringList.add(matcher.group());
        }
        return stringList;
    }

    public static void regexPatternRelease(int i) {
        sRegexPatterns.remove(Integer.valueOf(i));
    }

    public static String stringSafeNullCheck(String str) {
        return str == null ? "" : str;
    }

    public static String toLowerCase(String str) {
        return normalize(str).toLowerCase(Locale.getDefault());
    }

    public static String toTitleCase(String str) {
        char[] chars;
        String normalize = normalize(str);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        boolean z = true;
        for (int i = 0; i < normalize.length(); i++) {
            int codePointAt = normalize.codePointAt(i);
            int type = Character.getType(codePointAt);
            if (sb.length() > 0 && type != 6 && type != 8 && type != 15 && type != 28) {
                if (z) {
                    if (1 == sb.length()) {
                        for (int i2 = 0; i2 < Character.toChars(Character.toTitleCase(sb.codePointAt(0))).length; i2++) {
                            str2 = str2 + chars[i2];
                        }
                    } else {
                        str2 = str2 + sb.toString().toUpperCase(Locale.getDefault());
                    }
                    z = false;
                } else {
                    str2 = str2 + sb.toString().toLowerCase(Locale.getDefault());
                }
                if (Character.isSpaceChar(codePointAt)) {
                    z = true;
                }
                sb.delete(0, sb.length());
            }
            sb.appendCodePoint(codePointAt);
        }
        if (sb.length() > 0) {
            if (z) {
                if (1 == sb.length()) {
                    return str2 + Character.toTitleCase(sb.charAt(0));
                }
                return str2 + sb.toString().toUpperCase(Locale.getDefault());
            }
            return str2 + sb.toString().toLowerCase(Locale.getDefault());
        }
        return str2;
    }

    public static String toUpperCase(String str) {
        return normalize(str).toUpperCase(Locale.getDefault());
    }
}
