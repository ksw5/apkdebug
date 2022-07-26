package com.good.gd.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.TextUtils;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class GDLocalizer {
    private static final String DEFAULT_LANGUAGE = "en";
    public static final String DEPRECATED_API_WARN = "Usage of Deprecated GD SDK API, this API will be removed in a future release";
    private static final String STRINGS_FILE_BASE = "localizable_strings_";
    private static GDLocalizer _instance;
    private static Map<String, String> _strings;
    private static String effectiveLanguage;
    private static BroadcastReceiver localeChangedBroadcastReceiver = new hbfhc();
    private static ReadWriteLock mTranslationStringsLock = new ReentrantReadWriteLock();
    private static Class<?> rClass;
    private static Locale specifiedLocale;

    /* loaded from: classes.dex */
    static class hbfhc extends BroadcastReceiver {
        hbfhc() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            GDLog.DBGPRINTF(16, "GDLocalizer::localeChangedBroadcastReceiver IN. Locale changed to + " + Locale.getDefault().toString() + "\n");
            GDLocalizer.reloadTranslations();
            GDLog.DBGPRINTF(16, "GDLocalizer::localeChangedBroadcastReceiver OUT\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum yfdke {
        expecting_lhs,
        in_lhs,
        in_lhs_expecting_literal,
        expecting_equals,
        expecting_rhs,
        in_rhs,
        in_rhs_expecting_literal,
        expecting_semicolon
    }

    private GDLocalizer(Class<?> cls) throws Exception {
        rClass = cls;
        _strings = new HashMap();
        GTBaseContext.getInstance().getApplicationContext().registerReceiver(localeChangedBroadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        loadTranslations();
        ndkInit();
    }

    private static void addStringDefinition(String str, String str2) {
        if (_strings.containsKey(str)) {
            GDLog.DBGPRINTF(13, "GDLocalizer: duplicate definition: " + str + "\n");
        }
        _strings.put(str, str2);
    }

    public static GDLocalizer createInstance(Class<?> cls) throws Exception {
        if (_instance == null) {
            _instance = new GDLocalizer(cls);
        }
        return _instance;
    }

    public static String getEffectiveLanguage() {
        return effectiveLanguage;
    }

    public static String getLocalizedString(String str) {
        String str2;
        mTranslationStringsLock.readLock().lock();
        Map<String, String> map = _strings;
        if (map != null && map.containsKey(str)) {
            str2 = _strings.get(str);
        } else {
            str2 = "";
        }
        mTranslationStringsLock.readLock().unlock();
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        GDLog.DBGPRINTF(13, "GDLocalizer: lookup of key [" + str + "] failed, check localizable_strings_" + effectiveLanguage + " file\n");
        return str;
    }

    public static Locale getSpecifiedLocale() {
        return specifiedLocale;
    }

    private static void loadTranslations() throws Exception {
        int i;
        boolean z;
        InputStream inputStream = null;
        try {
            try {
                Resources resources = GTBaseContext.getInstance().getApplicationContext().getResources();
                Locale locale = resources.getConfiguration().locale;
                specifiedLocale = locale;
                effectiveLanguage = locale.getLanguage();
                try {
                    i = rClass.getField(STRINGS_FILE_BASE + specifiedLocale.toString().toLowerCase(Locale.ENGLISH)).getInt(null);
                    z = true;
                } catch (NoSuchFieldException e) {
                    GDLog.DBGPRINTF(14, "GDLocalizer: Localizable strings for specified locale: " + specifiedLocale.toString() + " not found. Trying to find effective language\n");
                    i = 0;
                    z = false;
                }
                if (!z) {
                    try {
                        i = rClass.getField(STRINGS_FILE_BASE + effectiveLanguage.toLowerCase(Locale.ENGLISH)).getInt(null);
                    } catch (NoSuchFieldException e2) {
                        GDLog.DBGPRINTF(12, "GDLocalizer: " + e2);
                    }
                }
                if (i == 0) {
                    try {
                        for (Field field : rClass.getFields()) {
                            String name = field.getName();
                            if (name.toLowerCase(Locale.ENGLISH).startsWith(STRINGS_FILE_BASE)) {
                                String substring = name.toLowerCase(Locale.ENGLISH).substring(20);
                                if (substring.contains(effectiveLanguage.toLowerCase(Locale.ENGLISH))) {
                                    String country = specifiedLocale.getCountry();
                                    if (!substring.contains("_") || (substring.contains("_") && substring.contains(country.toLowerCase(Locale.ENGLISH)))) {
                                        i = rClass.getField(name.toLowerCase(Locale.ENGLISH).substring(name.toLowerCase(Locale.ENGLISH).lastIndexOf(STRINGS_FILE_BASE))).getInt(null);
                                        break;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                    } catch (NoSuchFieldException e3) {
                        GDLog.DBGPRINTF(12, "GDLocalizer: " + e3);
                    }
                }
                if (i == 0) {
                    try {
                        i = rClass.getField("localizable_strings_en").getInt(null);
                    } catch (NoSuchFieldException e4) {
                        throw new Exception("No resource for raw/localizable_strings_" + effectiveLanguage);
                    }
                }
                InputStream openRawResource = resources.openRawResource(i);
                if (openRawResource != null) {
                    parseStringsFile(openRawResource);
                    try {
                        openRawResource.close();
                        return;
                    } catch (Exception e5) {
                        return;
                    }
                }
                throw new Exception("Can't read strings file \"raw/localizable_strings_" + effectiveLanguage + "\"!");
            } catch (Exception e6) {
                GDLog.DBGPRINTF(12, "GDLocalizer: " + e6);
                throw new Exception("Error reading strings file", e6);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (Exception e7) {
                }
            }
            throw th;
        }
    }

    private native void ndkInit();

    private static String newlinesFromText(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int indexOf = str.indexOf(10);
        while (indexOf != -1) {
            stringBuffer.append('\n');
            indexOf = str.indexOf(10, indexOf + 1);
        }
        return stringBuffer.toString();
    }

    private static void parseStringsFile(InputStream inputStream) throws Exception {
        int indexOf;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            String byteArrayOutputStream2 = byteArrayOutputStream.toString(HTTP.UTF_8);
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            while (i < byteArrayOutputStream2.length() && (indexOf = byteArrayOutputStream2.indexOf("/*", i)) != -1) {
                stringBuffer.append(byteArrayOutputStream2.substring(i, indexOf));
                int indexOf2 = byteArrayOutputStream2.indexOf("*/", indexOf + 2);
                if (indexOf2 != -1) {
                    stringBuffer.append(newlinesFromText(byteArrayOutputStream2.substring(indexOf, indexOf2)));
                    i = indexOf2 + 2;
                } else {
                    throw new Exception("unterminated comment at end of file!");
                }
            }
            if (i < byteArrayOutputStream2.length()) {
                stringBuffer.append(byteArrayOutputStream2.substring(i));
            }
            String stringBuffer2 = stringBuffer.toString();
            StringBuffer stringBuffer3 = new StringBuffer();
            StringBuffer stringBuffer4 = new StringBuffer();
            yfdke yfdkeVar = yfdke.expecting_lhs;
            mTranslationStringsLock.writeLock().lock();
            _strings.clear();
            int i2 = 1;
            for (int i3 = 0; i3 < stringBuffer2.length(); i3++) {
                char charAt = stringBuffer2.charAt(i3);
                switch (yfdkeVar.ordinal()) {
                    case 0:
                        if (charAt == '\"') {
                            yfdkeVar = yfdke.in_lhs;
                            stringBuffer3.setLength(0);
                            break;
                        } else if (Character.isWhitespace(charAt)) {
                            break;
                        } else {
                            throw new Exception("expecting '\"' or whitespace, got '" + charAt + "' at line " + i2);
                        }
                    case 1:
                        if (charAt == '\"') {
                            yfdkeVar = yfdke.expecting_equals;
                        } else if (charAt == '\\') {
                            yfdkeVar = yfdke.in_lhs_expecting_literal;
                        } else {
                            stringBuffer3.append(charAt);
                            break;
                        }
                        break;
                    case 2:
                        if (charAt == 'n') {
                            stringBuffer3.append('\n');
                        } else {
                            stringBuffer3.append(charAt);
                        }
                        yfdkeVar = yfdke.in_lhs;
                        break;
                    case 3:
                        if (charAt == '=') {
                            yfdkeVar = yfdke.expecting_rhs;
                            break;
                        } else if (Character.isWhitespace(charAt)) {
                            break;
                        } else {
                            throw new Exception("expecting '=' or whitespace, got '" + charAt + "' at line " + i2);
                        }
                    case 4:
                        if (charAt == '\"') {
                            yfdkeVar = yfdke.in_rhs;
                            stringBuffer4.setLength(0);
                            break;
                        } else if (Character.isWhitespace(charAt)) {
                            break;
                        } else {
                            throw new Exception("expecting '\"' or whitespace, got '" + charAt + "' at line " + i2);
                        }
                    case 5:
                        if (charAt == '\"') {
                            yfdkeVar = yfdke.expecting_semicolon;
                        } else if (charAt == '\\') {
                            yfdkeVar = yfdke.in_rhs_expecting_literal;
                        } else {
                            stringBuffer4.append(charAt);
                            break;
                        }
                        break;
                    case 6:
                        if (charAt == 'n') {
                            stringBuffer4.append('\n');
                        } else {
                            stringBuffer4.append(charAt);
                        }
                        yfdkeVar = yfdke.in_rhs;
                        break;
                    case 7:
                        if (charAt == ';') {
                            yfdkeVar = yfdke.expecting_lhs;
                            addStringDefinition(stringBuffer3.toString(), stringBuffer4.toString());
                            break;
                        } else if (Character.isWhitespace(charAt)) {
                            break;
                        } else {
                            throw new Exception("expecting ';' or whitespace, got '" + charAt + "' at line " + i2);
                        }
                }
                if (charAt == '\n') {
                    i2++;
                }
            }
        } finally {
            mTranslationStringsLock.writeLock().unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reloadTranslations() {
        try {
            loadTranslations();
        } catch (Exception e) {
            GDLog.DBGPRINTF(13, "GDLocalizer: exception during reloadTranslations: " + e.toString() + "\n");
        }
    }
}
