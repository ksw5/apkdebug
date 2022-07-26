package com.good.gd.apache.http.util;

import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class VersionInfo {
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    private final String infoClassloader;
    private final String infoModule;
    private final String infoPackage;
    private final String infoRelease;
    private final String infoTimestamp;

    protected VersionInfo(String str, String str2, String str3, String str4, String str5) {
        if (str != null) {
            this.infoPackage = str;
            this.infoModule = str2 == null ? UNAVAILABLE : str2;
            this.infoRelease = str3 == null ? UNAVAILABLE : str3;
            this.infoTimestamp = str4 == null ? UNAVAILABLE : str4;
            this.infoClassloader = str5 == null ? UNAVAILABLE : str5;
            return;
        }
        throw new IllegalArgumentException("Package identifier must not be null.");
    }

    protected static final VersionInfo fromMap(String str, Map map, ClassLoader classLoader) {
        String str2;
        String str3;
        String str4;
        String str5;
        if (str != null) {
            if (map != null) {
                String str6 = (String) map.get(PROPERTY_MODULE);
                if (str6 != null && str6.length() < 1) {
                    str6 = null;
                }
                String str7 = (String) map.get(PROPERTY_RELEASE);
                if (str7 != null && (str7.length() < 1 || str7.equals("${pom.version}"))) {
                    str7 = null;
                }
                String str8 = (String) map.get(PROPERTY_TIMESTAMP);
                str4 = (str8 == null || (str8.length() >= 1 && !str8.equals("${mvn.timestamp}"))) ? str8 : null;
                str2 = str6;
                str3 = str7;
            } else {
                str2 = null;
                str3 = null;
                str4 = null;
            }
            if (classLoader == null) {
                str5 = null;
            } else {
                str5 = classLoader.toString();
            }
            return new VersionInfo(str, str2, str3, str4, str5);
        }
        throw new IllegalArgumentException("Package identifier must not be null.");
    }

    public static final VersionInfo[] loadVersionInfo(String[] strArr, ClassLoader classLoader) {
        if (strArr != null) {
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str : strArr) {
                VersionInfo loadVersionInfo = loadVersionInfo(str, classLoader);
                if (loadVersionInfo != null) {
                    arrayList.add(loadVersionInfo);
                }
            }
            return (VersionInfo[]) arrayList.toArray(new VersionInfo[arrayList.size()]);
        }
        throw new IllegalArgumentException("Package identifier list must not be null.");
    }

    public final String getClassloader() {
        return this.infoClassloader;
    }

    public final String getModule() {
        return this.infoModule;
    }

    public final String getPackage() {
        return this.infoPackage;
    }

    public final String getRelease() {
        return this.infoRelease;
    }

    public final String getTimestamp() {
        return this.infoTimestamp;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.infoPackage.length() + 20 + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        stringBuffer.append("VersionInfo(").append(this.infoPackage).append(':').append(this.infoModule);
        if (!UNAVAILABLE.equals(this.infoRelease)) {
            stringBuffer.append(':').append(this.infoRelease);
        }
        if (!UNAVAILABLE.equals(this.infoTimestamp)) {
            stringBuffer.append(':').append(this.infoTimestamp);
        }
        stringBuffer.append(')');
        if (!UNAVAILABLE.equals(this.infoClassloader)) {
            stringBuffer.append('@').append(this.infoClassloader);
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final VersionInfo loadVersionInfo(String r4, ClassLoader r5) {
        /*
            if (r4 == 0) goto L52
            if (r5 != 0) goto Lc
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r5 = r5.getContextClassLoader()
        Lc:
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L49
            r1.<init>()     // Catch: java.io.IOException -> L49
            r2 = 46
            r3 = 47
            java.lang.String r2 = r4.replace(r2, r3)     // Catch: java.io.IOException -> L49
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.io.IOException -> L49
            java.lang.String r2 = "/"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.io.IOException -> L49
            java.lang.String r2 = "version.properties"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.io.IOException -> L49
            java.lang.String r1 = r1.toString()     // Catch: java.io.IOException -> L49
            java.io.InputStream r1 = r5.getResourceAsStream(r1)     // Catch: java.io.IOException -> L49
            if (r1 == 0) goto L4a
            java.util.Properties r2 = new java.util.Properties     // Catch: java.lang.Throwable -> L44
            r2.<init>()     // Catch: java.lang.Throwable -> L44
            r2.load(r1)     // Catch: java.lang.Throwable -> L44
            r1.close()     // Catch: java.io.IOException -> L42
            goto L4b
        L42:
            r1 = move-exception
            goto L4b
        L44:
            r2 = move-exception
            r1.close()     // Catch: java.io.IOException -> L49
            throw r2     // Catch: java.io.IOException -> L49
        L49:
            r1 = move-exception
        L4a:
            r2 = r0
        L4b:
            if (r2 == 0) goto L51
            com.good.gd.apache.http.util.VersionInfo r0 = fromMap(r4, r2, r5)
        L51:
            return r0
        L52:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Package identifier must not be null."
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.http.util.VersionInfo.loadVersionInfo(java.lang.String, java.lang.ClassLoader):com.good.gd.apache.http.util.VersionInfo");
    }
}
