package com.good.gd.apache.http.impl.cookie;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes.dex */
public final class DateUtils {
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final String[] DEFAULT_PATTERNS = {PATTERN_RFC1036, "EEE, dd MMM yyyy HH:mm:ss zzz", PATTERN_ASCTIME};

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class hbfhc {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> dbjc = new C0006hbfhc();

        /* renamed from: com.good.gd.apache.http.impl.cookie.DateUtils$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        static class C0006hbfhc extends ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> {
            C0006hbfhc() {
            }

            @Override // java.lang.ThreadLocal
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<>(new HashMap());
            }
        }

        public static SimpleDateFormat dbjc(String str) {
            ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> threadLocal = dbjc;
            Map<String, SimpleDateFormat> map = threadLocal.get().get();
            if (map == null) {
                map = new HashMap<>();
                threadLocal.set(new SoftReference<>(map));
            }
            SimpleDateFormat simpleDateFormat = map.get(str);
            if (simpleDateFormat == null) {
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str, Locale.US);
                simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
                map.put(str, simpleDateFormat2);
                return simpleDateFormat2;
            }
            return simpleDateFormat;
        }
    }

    static {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        GMT = timeZone;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return formatDate(date, "EEE, dd MMM yyyy HH:mm:ss zzz");
    }

    public static Date parseDate(String str) throws DateParseException {
        return parseDate(str, null, null);
    }

    public static String formatDate(Date date, String str) {
        if (date != null) {
            if (str != null) {
                return hbfhc.dbjc(str).format(date);
            }
            throw new IllegalArgumentException("pattern is null");
        }
        throw new IllegalArgumentException("date is null");
    }

    public static Date parseDate(String str, String[] strArr) throws DateParseException {
        return parseDate(str, strArr, null);
    }

    public static Date parseDate(String str, String[] strArr, Date date) throws DateParseException {
        if (str != null) {
            if (strArr == null) {
                strArr = DEFAULT_PATTERNS;
            }
            if (date == null) {
                date = DEFAULT_TWO_DIGIT_YEAR_START;
            }
            if (str.length() > 1 && str.startsWith("'") && str.endsWith("'")) {
                str = str.substring(1, str.length() - 1);
            }
            for (String str2 : strArr) {
                SimpleDateFormat dbjc = hbfhc.dbjc(str2);
                dbjc.set2DigitYearStart(date);
                try {
                    return dbjc.parse(str);
                } catch (ParseException e) {
                }
            }
            throw new DateParseException("Unable to parse the date " + str);
        }
        throw new IllegalArgumentException("dateValue is null");
    }
}
