package com.good.gd.apachehttp.impl.client;

import android.database.Cursor;
import com.good.gd.apache.http.cookie.Cookie;
import com.good.gd.apache.http.cookie.CookieIdentityComparator;
import com.good.gd.apache.http.impl.cookie.BasicClientCookie;
import com.good.gd.apachehttp.impl.client.cache.CacheKeyGenerator;
import com.good.gd.apachehttp.impl.client.cache.DefaultHttpCacheEntrySerializer;
import com.good.gd.apachehttp.impl.cookie.BasicClientCookieSerializer;
import com.good.gd.database.sqlite.DatabaseUtils;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
class GDHttpClientDatabase {
    private static GDHttpClientDatabase jwxax;
    private static int liflu;
    private static int lqox;
    private static SQLiteDatabase qkduk;
    private static DatabaseUtils.InsertHelper wxau;
    private static DatabaseUtils.InsertHelper ztwf;
    private BasicClientCookieSerializer dbjc = new BasicClientCookieSerializer();

    static {
        new CacheKeyGenerator();
    }

    protected GDHttpClientDatabase() {
        new DefaultHttpCacheEntrySerializer();
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::GDHttpClientDatabase() IN\n");
        jwxax();
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::GDHttpClientDatabase() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized GDHttpClientDatabase wxau() {
        GDHttpClientDatabase gDHttpClientDatabase;
        synchronized (GDHttpClientDatabase.class) {
            if (jwxax == null) {
                jwxax = new GDHttpClientDatabase();
            }
            gDHttpClientDatabase = jwxax;
        }
        return gDHttpClientDatabase;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void dbjc(Cookie cookie, CookieIdentityComparator cookieIdentityComparator) {
        Cursor cursor;
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie() IN\n");
        Cursor cursor2 = null;
        try {
            try {
                cursor = qkduk.rawQuery("SELECT * FROM cookies WHERE name = ?", new String[]{cookie.getName()});
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (IllegalStateException e2) {
            e = e2;
        }
        try {
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie - quering done, count:" + cursor.getCount() + "\n");
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int columnIndex = cursor.getColumnIndex("entry");
                    int columnIndex2 = cursor.getColumnIndex("_id");
                    byte[] blob = cursor.getBlob(columnIndex);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie - blob:" + blob + "\n");
                    Cookie dbjc = dbjc(blob);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie - return cookie:" + dbjc.toString() + "\n");
                    int i = cursor.getInt(columnIndex2);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie - rowID  " + i + "\n");
                    if (cookieIdentityComparator.compare(dbjc, cookie) == 0) {
                        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie - deleting row id=" + i + "\n");
                        qkduk.delete("cookies", "_id=" + i, null);
                    }
                    cursor.moveToNext();
                }
            }
        } catch (IOException e3) {
            e = e3;
            cursor2 = cursor;
            LogUtils.logStackTrace(e);
            if (cursor2 != null) {
                cursor = cursor2;
                cursor.close();
            }
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie() OUT\n");
        } catch (IllegalStateException e4) {
            e = e4;
            cursor2 = cursor;
            GDLog.DBGPRINTF(16, "GDHttpClientDatabaseremoveCookie", e);
            if (cursor2 != null) {
                cursor = cursor2;
                cursor.close();
            }
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie() OUT\n");
        } catch (Throwable th2) {
            th = th2;
            cursor2 = cursor;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
        cursor.close();
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeCookie() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void jwxax() {
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::openDatabase() IN\n");
        SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase("gdapachehttpcache3.db", (SQLiteDatabase.CursorFactory) null);
        qkduk = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS httpcacheentries (url TEXT PRIMARY KEY NOT NULL,cacheentry BLOB)");
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(qkduk, "httpcacheentries");
        wxau = insertHelper;
        insertHelper.getColumnIndex("url");
        wxau.getColumnIndex("cacheentry");
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase - creating cookies table\n");
        qkduk.execSQL("CREATE TABLE IF NOT EXISTS cookies (_id INTEGER PRIMARY KEY NOT NULL,name TEXT,entry BLOB)");
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase - creating cookies table index\n");
        qkduk.execSQL("CREATE INDEX IF NOT EXISTS nameIndex ON cookies (name)");
        ztwf = new DatabaseUtils.InsertHelper(qkduk, "cookies");
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase - initializing column numbers:\n");
        lqox = ztwf.getColumnIndex("name");
        liflu = ztwf.getColumnIndex("entry");
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::openDatabase() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void qkduk() {
        qkduk.close();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void dbjc(Date date) {
        Cursor cursor;
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies(date: " + date + ") IN\n");
        Cursor cursor2 = null;
        try {
            try {
                cursor = qkduk.rawQuery("SELECT * FROM cookies", null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (IllegalStateException e2) {
            e = e2;
        }
        try {
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies - quering done, count:" + cursor.getCount() + "\n");
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String[] columnNames = cursor.getColumnNames();
                    int length = columnNames.length;
                    for (int i = 0; i < length; i++) {
                        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::col name: " + columnNames[i] + "\n");
                    }
                    int columnIndex = cursor.getColumnIndex("entry");
                    int columnIndex2 = cursor.getColumnIndex("_id");
                    byte[] blob = cursor.getBlob(columnIndex);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies - blob:" + blob + "\n");
                    Cookie dbjc = dbjc(blob);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies -  cookie:" + dbjc.toString() + "\n");
                    int i2 = cursor.getInt(columnIndex2);
                    GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies - rowID  " + i2 + "\n");
                    if (dbjc.isExpired(date)) {
                        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies - deleting row id=" + i2 + "\n");
                        qkduk.delete("cookies", "_id=" + i2, null);
                    }
                    cursor.moveToNext();
                }
            }
        } catch (IOException e3) {
            e = e3;
            cursor2 = cursor;
            LogUtils.logStackTrace(e);
            if (cursor2 != null) {
                cursor = cursor2;
                cursor.close();
            }
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies() OUT\n");
        } catch (IllegalStateException e4) {
            e = e4;
            cursor2 = cursor;
            GDLog.DBGPRINTF(16, "GDHttpClientDatabaseremoveExpiredCookies", e);
            if (cursor2 != null) {
                cursor = cursor2;
                cursor.close();
            }
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies() OUT\n");
        } catch (Throwable th2) {
            th = th2;
            cursor2 = cursor;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
        cursor.close();
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::removeExpiredCookies() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void dbjc(Cookie cookie) {
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::addCookie() IN\n");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BasicClientCookie basicClientCookie = (BasicClientCookie) cookie;
            String domain = basicClientCookie.getDomain();
            if (domain != null) {
                basicClientCookie.setDomain(domain.toLowerCase(Locale.US));
            }
            this.dbjc.writeTo(basicClientCookie, new ObjectOutputStream(byteArrayOutputStream));
            ztwf.prepareForReplace();
            ztwf.bind(lqox, basicClientCookie.getName());
            ztwf.bind(liflu, byteArrayOutputStream.toByteArray());
            GDLog.DBGPRINTF(16, "GDHttpClientDatabase::addCookie(): bos size:" + byteArrayOutputStream.size() + "\n");
            ztwf.execute();
        } catch (IOException e) {
            LogUtils.logStackTrace(e);
        }
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::addCookie() OUT\n");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c6, code lost:
        if (r0 == null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void dbjc(java.util.TreeSet<Cookie> r7) throws IOException {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.String r0 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(TreeSet) IN\n"
            r1 = 16
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r0)     // Catch: java.lang.Throwable -> Ld8
            r0 = 0
            com.good.gd.database.sqlite.SQLiteDatabase r2 = com.good.gd.apachehttp.impl.client.GDHttpClientDatabase.qkduk     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = "SELECT * FROM cookies"
            android.database.Cursor r0 = r2.rawQuery(r3, r0)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            r2.<init>()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = "GDHttpClientDatabase::loadCookiesFromCookiesTable - found: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            int r3 = r0.getCount()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = "\n"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            if (r2 == 0) goto Lc8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            r2.<init>()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = "GDHttpClientDatabase::col names: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String[] r3 = r0.getColumnNames()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = "\n"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
        L5b:
            boolean r2 = r0.isAfterLast()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            if (r2 != 0) goto Lc8
            java.lang.String r2 = "entry"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            byte[] r3 = r0.getBlob(r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            r4.<init>()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r5 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(): blob.size= "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            int r5 = r3.length     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r5 = " entryCol="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            com.good.gd.apache.http.cookie.Cookie r2 = r6.dbjc(r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            if (r2 == 0) goto Lb7
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            r3.<init>()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r4 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(): cookie="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            r7.add(r2)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
        Lb7:
            r0.moveToNext()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbd
            goto L5b
        Lbb:
            r7 = move-exception
            goto Ld2
        Lbd:
            r7 = move-exception
            java.lang.String r2 = "GDHttpClientDatabaseloadCookiesFromCookiesTable - exception: "
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2, r7)     // Catch: java.lang.Throwable -> Lbb
            com.good.gd.utils.LogUtils.logStackTrace(r7)     // Catch: java.lang.Throwable -> Lbb
            if (r0 == 0) goto Lcb
        Lc8:
            r0.close()     // Catch: java.lang.Throwable -> Ld8
        Lcb:
            java.lang.String r7 = "GDHttpClientDatabase::loadCookiesFromCookiesTable() OUT\n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r7)     // Catch: java.lang.Throwable -> Ld8
            monitor-exit(r6)
            return
        Ld2:
            if (r0 == 0) goto Ld7
            r0.close()     // Catch: java.lang.Throwable -> Ld8
        Ld7:
            throw r7     // Catch: java.lang.Throwable -> Ld8
        Ld8:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apachehttp.impl.client.GDHttpClientDatabase.dbjc(java.util.TreeSet):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00cb, code lost:
        if (r0 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void dbjc(java.util.List<Cookie> r9) throws IOException {
        /*
            r8 = this;
            monitor-enter(r8)
            java.lang.String r0 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(List) IN\n"
            r1 = 16
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r0)     // Catch: java.lang.Throwable -> Ldd
            r0 = 0
            com.good.gd.database.sqlite.SQLiteDatabase r2 = com.good.gd.apachehttp.impl.client.GDHttpClientDatabase.qkduk     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r3 = "SELECT * FROM cookies"
            android.database.Cursor r0 = r2.rawQuery(r3, r0)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r2.<init>()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r3 = "GDHttpClientDatabase::loadCookiesFromCookiesTable - found: "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            int r3 = r0.getCount()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r3 = "\n"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            if (r2 == 0) goto Lcd
            java.lang.String[] r2 = r0.getColumnNames()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            int r3 = r2.length     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r4 = 0
        L3d:
            if (r4 >= r3) goto L60
            r5 = r2[r4]     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r6.<init>()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r7 = "GDHttpClientDatabase::col name: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r5 = r6.append(r5)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r6 = "\n"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r5)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            int r4 = r4 + 1
            goto L3d
        L60:
            boolean r2 = r0.isAfterLast()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            if (r2 != 0) goto Lcd
            java.lang.String r2 = "entry"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            byte[] r3 = r0.getBlob(r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r4.<init>()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r5 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(): blob size= "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            int r5 = r3.length     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r5 = " entryCol="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            com.good.gd.apache.http.cookie.Cookie r2 = r8.dbjc(r3)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            if (r2 == 0) goto Lbc
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r3.<init>()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r4 = "GDHttpClientDatabase::loadCookiesFromCookiesTable(): adding cookie="
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r4 = "\n"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r3)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            r9.add(r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
        Lbc:
            r0.moveToNext()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            goto L60
        Lc0:
            r9 = move-exception
            goto Ld7
        Lc2:
            r9 = move-exception
            java.lang.String r2 = "GDHttpClientDatabaseloadCookiesFromCookiesTable - exception: "
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r2, r9)     // Catch: java.lang.Throwable -> Lc0
            com.good.gd.utils.LogUtils.logStackTrace(r9)     // Catch: java.lang.Throwable -> Lc0
            if (r0 == 0) goto Ld0
        Lcd:
            r0.close()     // Catch: java.lang.Throwable -> Ldd
        Ld0:
            java.lang.String r9 = "GDHttpClientDatabase::loadCookiesFromCookiesTable() OUT\n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r1, r9)     // Catch: java.lang.Throwable -> Ldd
            monitor-exit(r8)
            return
        Ld7:
            if (r0 == 0) goto Ldc
            r0.close()     // Catch: java.lang.Throwable -> Ldd
        Ldc:
            throw r9     // Catch: java.lang.Throwable -> Ldd
        Ldd:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apachehttp.impl.client.GDHttpClientDatabase.dbjc(java.util.List):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void dbjc() {
        if (qkduk == null) {
            return;
        }
        GDLog.DBGPRINTF(16, "GDHttpClientDatabase::clearAllCookies() - cleaning...\n");
        qkduk.delete("cookies", null, null);
    }

    private Cookie dbjc(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof byte[])) {
            GDLog.DBGPRINTF(16, "GDHttpClientDatabasemakeCookieFromBlob() - got a non-bytearray back from db: " + obj + "\n");
            return null;
        }
        try {
            BasicClientCookie readFrom = this.dbjc.readFrom(new ObjectInputStream(new ByteArrayInputStream((byte[]) obj)));
            String domain = readFrom.getDomain();
            if (domain != null) {
                readFrom.setDomain(domain.toLowerCase(Locale.US));
            }
            return readFrom;
        } catch (ClassNotFoundException e) {
            GDLog.DBGPRINTF(12, "GDHttpClientDatabasemakeCookieFromBlob() exception", e);
            return null;
        }
    }
}
