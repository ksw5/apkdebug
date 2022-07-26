package com.good.gd.ovnkx;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class mjbm {
    private static final Class dbjc = mjbm.class;

    /* loaded from: classes.dex */
    public interface aqdzk {
        void dbjc(boolean z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface ehnkx {
        void dbjc(String str);
    }

    /* loaded from: classes.dex */
    public static class fdyxd {
        static final String[] dbjc = {"name", "scope", "ts"};
        static final String[] qkduk = {"label", "start", "end"};
        static final String[] jwxax = {"appVersion", "tz", "bbdsdkversion", "osversion"};
    }

    /* loaded from: classes.dex */
    static class hbfhc implements ehnkx {
        final /* synthetic */ HashMap dbjc;
        final /* synthetic */ JSONArray qkduk;

        hbfhc(HashMap hashMap, JSONArray jSONArray) {
            this.dbjc = hashMap;
            this.qkduk = jSONArray;
        }

        @Override // com.good.gd.ovnkx.mjbm.ehnkx
        public void dbjc(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("osversion")) {
                    this.dbjc.put("LogFileHeader", jSONObject);
                } else if (jSONObject.has("appVersion")) {
                    this.dbjc.put("LogFileBatchHeader", jSONObject);
                } else if (jSONObject.has(NotificationCompat.CATEGORY_EVENT)) {
                    this.qkduk.put(jSONObject);
                } else if (!"CRASH".equals(jSONObject.optString("name"))) {
                } else {
                    com.good.gd.kloes.hbfhc.jwxax(mjbm.dbjc, "Crash event is older but prepared with new keys. Preparing with older keys.");
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(NotificationCompat.CATEGORY_EVENT, "C");
                    jSONObject2.put("ts", jSONObject.optString("ts"));
                    this.qkduk.put(jSONObject2);
                }
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.qkduk(mjbm.dbjc, "Error in creating json of logs.");
            }
        }
    }

    /* renamed from: com.good.gd.ovnkx.mjbm$mjbm  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface AbstractC0024mjbm {
        void dbjc();

        void dbjc(int i, int i2);

        void dbjc(String str);
    }

    /* loaded from: classes.dex */
    public interface pmoiy {
        String dbjc(com.good.gd.dvql.hbfhc hbfhcVar) throws JSONException;
    }

    /* loaded from: classes.dex */
    static class yfdke implements ehnkx {
        final /* synthetic */ JSONArray dbjc;
        final /* synthetic */ JSONObject jwxax;
        final /* synthetic */ JSONArray qkduk;

        yfdke(JSONArray jSONArray, JSONArray jSONArray2, JSONObject jSONObject) {
            this.dbjc = jSONArray;
            this.qkduk = jSONArray2;
            this.jwxax = jSONObject;
        }

        @Override // com.good.gd.ovnkx.mjbm.ehnkx
        public void dbjc(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (mjbm.dbjc(jSONObject, true)) {
                    this.dbjc.put(jSONObject);
                } else if (mjbm.dbjc(jSONObject, false)) {
                    this.qkduk.put(jSONObject);
                } else if (!mjbm.dbjc(jSONObject)) {
                } else {
                    if (jSONObject.has("tz")) {
                        this.jwxax.put("tz", jSONObject.getString("tz"));
                    }
                    if (jSONObject.has("osversion")) {
                        this.jwxax.put("osversion", jSONObject.getString("osversion"));
                    }
                    if (jSONObject.has("appVersion")) {
                        this.jwxax.put("appVersion", jSONObject.getString("appVersion"));
                    }
                    if (!jSONObject.has("bbdsdkversion")) {
                        return;
                    }
                    this.jwxax.put("bbdsdkversion", jSONObject.getString("bbdsdkversion"));
                }
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.qkduk(mjbm.dbjc, "Error in creating json of logs.");
            }
        }
    }

    public static String jcpqe(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "Event type is required to get Sending directory path.");
            return null;
        }
        String dbjc2 = com.blackberry.bis.core.yfdke.odlf().dbjc();
        String str2 = ".BIS" + dbjc2;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        if (c == 0) {
            return str2 + "Historical" + dbjc2 + "Sending";
        }
        if (c == 1) {
            return str2 + "AppIntelligence" + dbjc2 + "Sending";
        }
        return null;
    }

    public static boolean jsgtu(String str) {
        boolean z;
        boolean z2;
        File[] listFiles;
        com.good.gd.ujgjo.hbfhc jcpqe = com.good.gd.ujgjo.hbfhc.jcpqe();
        File jwxax = jcpqe.jwxax(str);
        if (jwxax == null) {
            z = true;
        } else {
            z = jwxax.list().length == 0;
        }
        if (true != z && jwxax != null && (listFiles = jwxax.listFiles()) != null && listFiles.length == 1 && listFiles[0].length() == 0) {
            z = true;
        }
        File lqox = jcpqe.lqox(str);
        if (lqox == null) {
            z2 = true;
        } else {
            z2 = lqox.list().length == 0;
        }
        File ztwf = jcpqe.ztwf(str);
        return z && z2 && (ztwf == null || ztwf.list().length == 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12 */
    private static ArrayList<String> jwxax(InputStream inputStream) {
        Throwable th;
        BufferedReader bufferedReader;
        ArrayList<String> arrayList = new ArrayList<>();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream), StandardCharsets.UTF_8));
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            String str = bufferedReader.readLine();
            while (str != null) {
                arrayList.add(str);
                str = bufferedReader.readLine();
            }
            dbjc(bufferedReader);
            bufferedReader2 = str;
        } catch (IOException e2) {
            bufferedReader2 = bufferedReader;
            e = e2;
            arrayList.clear();
            com.good.gd.kloes.hbfhc.qkduk(dbjc, "Error on printing compressed file, cause: " + e.getCause());
            dbjc(bufferedReader2);
            bufferedReader2 = bufferedReader2;
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            dbjc(bufferedReader);
            throw th;
        }
        return arrayList;
    }

    public static String liflu(InputStream inputStream) {
        BufferedReader bufferedReader;
        JSONObject jSONObject;
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Fetching Schema version from event log file.");
        Reader reader = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                try {
                    jSONObject = new JSONObject(bufferedReader.readLine());
                } catch (IOException e) {
                    e = e;
                    com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while reading schema version from file: " + e.getLocalizedMessage());
                    dbjc(bufferedReader);
                    return null;
                } catch (JSONException e2) {
                    e = e2;
                    com.good.gd.kloes.hbfhc.wxau(dbjc, "JSON Exception while reading schema version from file: " + e.getLocalizedMessage());
                    dbjc(bufferedReader);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                reader = "Fetching Schema version from event log file.";
                dbjc(reader);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
        } catch (JSONException e4) {
            e = e4;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            dbjc(reader);
            throw th;
        }
        if (!jSONObject.has("schema")) {
            dbjc(bufferedReader);
            return null;
        }
        String string = jSONObject.getString("schema");
        dbjc(bufferedReader);
        return string;
    }

    private static byte[] lqox(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byteArrayOutputStream.flush();
                    return byteArrayOutputStream.toByteArray();
                }
            } catch (IOException e) {
                com.good.gd.kloes.hbfhc.wxau(dbjc, "Unable to get data from stream: " + e.getLocalizedMessage());
                return null;
            }
        }
    }

    public static com.good.gd.ovnkx.aqdzk qkduk(String str, File file) throws IOException {
        String absolutePath = file.getAbsolutePath();
        com.good.gd.kloes.ehnkx.dbjc(dbjc, "Get Log File: [" + absolutePath + "]");
        return com.blackberry.bis.core.yfdke.rynix().dbjc(str, absolutePath);
    }

    public static String tlske(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "Event type is required to get Upload directory path.");
            return null;
        }
        String dbjc2 = com.blackberry.bis.core.yfdke.odlf().dbjc();
        String str2 = ".BIS" + dbjc2;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        if (c == 0) {
            return str2 + "Historical" + dbjc2 + "Upload";
        }
        if (c == 1) {
            return str2 + "AppIntelligence" + dbjc2 + "Upload";
        }
        return null;
    }

    private static String wuird(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            return null;
        }
        if (!str.startsWith("eventLog")) {
            return str;
        }
        return "hist_" + str.substring(str.indexOf("_") + 1);
    }

    public static File wxau(String str, File file) {
        File[] listFiles;
        com.good.gd.kloes.hbfhc.wxau(dbjc, String.format(Locale.getDefault(), "Get Oldest File for %s events.", str));
        String lqox = lqox(str);
        File file2 = null;
        if (file != null && true == file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length != 0) {
            file2 = listFiles[0];
            long lastModified = file2.lastModified();
            for (File file3 : listFiles) {
                if (file3.lastModified() < lastModified && file3.getName().startsWith(lqox) && file3.getName().endsWith(".txt")) {
                    lastModified = file3.lastModified();
                    file2 = file3;
                }
            }
        }
        return file2;
    }

    public static HashMap<String, Object> ztwf(InputStream inputStream) {
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Get All Logs From Input Stream.");
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        HashMap<String, Object> hashMap = new HashMap<>();
        dbjc(new DataInputStream(inputStream), new yfdke(jSONArray, jSONArray2, jSONObject));
        hashMap.put("LogFileBatchHeader", jSONObject);
        hashMap.put("EventLogs", jSONArray);
        hashMap.put("ActivityLogs", jSONArray2);
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String dbjc(String str, String str2, String str3) {
        com.good.gd.kloes.hbfhc.wxau(dbjc, String.format(Locale.getDefault(), "Create File Name for %s events.", str));
        return lqox(str) + "_" + new SimpleDateFormat(str2).format(new Date()) + str3;
    }

    public static void qkduk(File file) {
        if (file == null) {
            return;
        }
        File[] listFiles = file.listFiles();
        int length = listFiles != null ? listFiles.length : 0;
        com.good.gd.kloes.hbfhc.wxau(dbjc, String.format(Locale.getDefault(), "There are %d files in %s-%s dir", Integer.valueOf(length), file.getParent(), file.getName()));
        for (int i = 0; i < length; i++) {
            File file2 = listFiles[i];
            com.good.gd.kloes.ehnkx.qkduk(dbjc, file2.getName() + " " + dbjc(file2.length()));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0059, code lost:
        if (r18.equals("appIntelligence") != false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void dbjc(java.util.ArrayDeque<com.good.gd.dvql.hbfhc> r16, pmoiy r17, String r18) {
        /*
            Method dump skipped, instructions count: 633
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ovnkx.mjbm.dbjc(java.util.ArrayDeque, com.good.gd.ovnkx.mjbm$pmoiy, java.lang.String):void");
    }

    public static File lqox() {
        com.good.gd.ovnkx.aqdzk jwxax = jwxax(".BIS" + com.blackberry.bis.core.yfdke.odlf().dbjc() + "ProfileOverride");
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v2 */
    public static byte[] qkduk(File file, com.good.gd.ujgjo.hbfhc hbfhcVar) {
        InputStream inputStream;
        ?? r0 = 0;
        try {
            try {
                inputStream = hbfhcVar.dbjc(file);
                try {
                    byte[] lqox = lqox(inputStream);
                    dbjc(inputStream);
                    dbjc(inputStream);
                    return lqox;
                } catch (IOException e) {
                    com.good.gd.kloes.hbfhc.qkduk(dbjc, "Can't open file");
                    dbjc(inputStream);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                r0 = file;
                dbjc((InputStream) r0);
                throw th;
            }
        } catch (IOException e2) {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            dbjc((InputStream) r0);
            throw th;
        }
    }

    private static String lqox(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "File type is required to obtain file name prefix.");
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        return c != 0 ? c != 1 ? "" : "appIntelli" : "hist";
    }

    public static HashMap<String, Object> wxau(InputStream inputStream) {
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Get All Logs From Input Stream.");
        JSONArray jSONArray = new JSONArray();
        HashMap<String, Object> hashMap = new HashMap<>();
        dbjc(new DataInputStream(inputStream), new hbfhc(hashMap, jSONArray));
        hashMap.put("EventLogs", jSONArray);
        return hashMap;
    }

    public static com.good.gd.ovnkx.aqdzk jwxax(String str, File file) throws IOException {
        String absolutePath = file.getAbsolutePath();
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, String.format(Locale.getDefault(), "Get new Log File for %s events.", str));
        com.good.gd.kloes.ehnkx.qkduk(cls, "New Log File: [" + absolutePath + "]");
        dbjc(str, file, liflu(str));
        String dbjc2 = dbjc(str, "yyyyMMdd_HH:mm:ss.SSS", ".txt");
        String str2 = file.getAbsolutePath() + File.separator + dbjc2;
        if (com.blackberry.bis.core.yfdke.rynix() != null) {
            File createFile = com.blackberry.bis.core.yfdke.odlf().createFile(str2);
            createFile.createNewFile();
            return new com.good.gd.ovnkx.aqdzk(createFile.getAbsolutePath());
        }
        throw null;
    }

    private static String qkduk(InputStream inputStream) {
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                try {
                    try {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine).append("\n");
                        } catch (Exception e) {
                            com.good.gd.kloes.hbfhc.qkduk(dbjc, "Failed to convert stream to string due to : " + e.getLocalizedMessage());
                            bufferedReader.close();
                        }
                    } catch (IOException e2) {
                        com.good.gd.kloes.hbfhc.qkduk(dbjc, "Failed to close the buffered reader stream.");
                    }
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        com.good.gd.kloes.hbfhc.qkduk(dbjc, "Failed to close the buffered reader stream.");
                    }
                    throw th;
                }
            }
            bufferedReader.close();
            return sb.toString();
        }
        return null;
    }

    public static boolean liflu() {
        com.good.gd.ovnkx.ehnkx qkduk = com.blackberry.bis.core.yfdke.qkduk();
        boolean z = false;
        if (qkduk == null) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] App Container file factory is not available.");
            return false;
        }
        File createFile = qkduk.createFile(".BIS");
        if (createFile != null && createFile.exists()) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] 3.0 ==>> 3.1 migration is required.");
            return true;
        }
        File createFile2 = qkduk.createFile("analytics");
        File createFile3 = qkduk.createFile("upload");
        File createFile4 = qkduk.createFile("sending");
        File createFile5 = qkduk.createFile("task_scheduler_storage");
        File createFile6 = qkduk.createFile("persistent_storage");
        File createFile7 = qkduk.createFile("app_instance_id");
        File createFile8 = qkduk.createFile("auth_token_storage");
        if ((createFile2 != null && createFile2.exists()) || ((createFile3 != null && createFile3.exists()) || ((createFile4 != null && createFile4.exists()) || ((createFile5 != null && createFile5.exists()) || ((createFile6 != null && createFile6.exists()) || ((createFile7 != null && createFile7.exists()) || (createFile8 != null && createFile8.exists()))))))) {
            z = true;
        }
        if (z) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] 2.x ==>> 3.1 migration is required.");
        }
        return z;
    }

    private static String jwxax() {
        return ".BIS" + com.blackberry.bis.core.yfdke.odlf().dbjc() + "Configuration";
    }

    public static com.good.gd.ovnkx.aqdzk jwxax(String str) {
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "Get directory for given path.");
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(cls, "Unable to retrieve the directory, path is required.");
            return null;
        } else if (com.blackberry.bis.core.yfdke.iulf() != null) {
            com.good.gd.ovnkx.aqdzk aqdzkVar = new com.good.gd.ovnkx.aqdzk(str);
            if (aqdzkVar.jwxax() || aqdzkVar.wxau().mkdirs()) {
                return aqdzkVar;
            }
            return null;
        } else {
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.lang.Class, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static String qkduk(String str) {
        Throwable th;
        String str2 = null;
        str2 = null;
        str2 = null;
        FileInputStream fileInputStream = null;
        if (str != 0) {
            try {
                try {
                } catch (IOException e) {
                    str = dbjc;
                    com.good.gd.kloes.hbfhc.qkduk(str, "Failed to close input stream.");
                }
                if (!str.trim().isEmpty()) {
                    try {
                        str = new FileInputStream(new File((String) str));
                        try {
                            str2 = qkduk((InputStream) str);
                            str.close();
                            str = str;
                        } catch (Exception e2) {
                            com.good.gd.kloes.hbfhc.qkduk(dbjc, "Failed to read data from file.");
                            if (str != 0) {
                                str.close();
                                str = str;
                            }
                            return str2;
                        }
                    } catch (Exception e3) {
                        str = 0;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e4) {
                                com.good.gd.kloes.hbfhc.qkduk(dbjc, "Failed to close input stream.");
                            }
                        }
                        throw th;
                    }
                    return str2;
                }
            } catch (Throwable th3) {
                fileInputStream = str;
                th = th3;
            }
        }
        return null;
    }

    private static String ztwf() {
        return ".BIS" + com.blackberry.bis.core.yfdke.odlf().dbjc() + "KeyStorage";
    }

    public static long ztwf(String str) {
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "Get max file size.");
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(cls, "File type is required to fetch the max file size.");
            return 0L;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        return (c == 0 || c == 1) ? 512000L : 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int liflu(String str) {
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "Get max number of allowed files in log directory.");
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(cls, "File type is required to fetch number of allowed files.");
            return 0;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        return (c == 0 || c == 1) ? 15 : 0;
    }

    public static File wxau() {
        com.good.gd.ovnkx.aqdzk jwxax = jwxax(ztwf());
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    public static String wxau(String str) {
        if (com.good.gd.whhmi.yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "Event type is required to get Event Log directory path.");
            return null;
        }
        String dbjc2 = com.blackberry.bis.core.yfdke.odlf().dbjc();
        String str2 = ".BIS" + dbjc2;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1876566912) {
            if (hashCode == 1950555338 && str.equals("historical")) {
                c = 0;
            }
        } else if (str.equals("appIntelligence")) {
            c = 1;
        }
        if (c == 0) {
            return str2 + "Historical" + dbjc2 + "EventLogs";
        }
        if (c == 1) {
            return str2 + "AppIntelligence" + dbjc2 + "EventLogs";
        }
        return null;
    }

    public static File qkduk() {
        com.good.gd.ovnkx.aqdzk jwxax = jwxax(jwxax());
        if (jwxax != null) {
            return jwxax.wxau();
        }
        return null;
    }

    public static String dbjc(long j) {
        long j2;
        long j3 = j % 1024;
        if (j <= 1024) {
            j2 = 0;
        } else {
            j2 = j / 1024;
        }
        if (j2 > 0) {
            return j2 + " KB " + j3 + " Byte";
        }
        return j + " Byte";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void dbjc(String str, File file, int i) throws IOException {
        File[] listFiles;
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "Remove Oldest Files.");
        if (true == file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length >= i) {
            String lqox = lqox(str);
            com.good.gd.kloes.hbfhc.wxau(cls, "Get sorted files based on last modification time.");
            TreeSet treeSet = new TreeSet(new com.good.gd.ovnkx.pmoiy());
            for (File file2 : listFiles) {
                if (file2.getName().contains(lqox)) {
                    treeSet.add(file2);
                }
            }
            int i2 = 0;
            while (treeSet.size() >= i) {
                File file3 = (File) treeSet.first();
                treeSet.remove(file3);
                if (file3.delete()) {
                    i2++;
                }
            }
            com.good.gd.kloes.ehnkx.qkduk(dbjc, String.format(Locale.getDefault(), "%d oldest files removed.", Integer.valueOf(i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static File dbjc(String str, File file, String str2) {
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Get Latest File.");
        File file2 = null;
        if (true != file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length != 0) {
            String lqox = lqox(str);
            long j = 0;
            for (File file3 : listFiles) {
                if (file3.lastModified() >= j && file3.getName().startsWith(lqox) && file3.getName().endsWith(str2)) {
                    j = file3.lastModified();
                    file2 = file3;
                }
            }
        }
        return file2;
    }

    private static int dbjc(DataInputStream dataInputStream, ehnkx ehnkxVar) {
        BufferedReader bufferedReader;
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Fetching all the events records from event log file.");
        int i = 0;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e = e;
        } catch (Throwable th) {
            th = th;
        }
        try {
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                i++;
                if (ehnkxVar != null) {
                    ehnkxVar.dbjc(readLine);
                }
            }
            dbjc(bufferedReader);
        } catch (IOException e2) {
            e = e2;
            bufferedReader2 = bufferedReader;
            try {
                com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while reading data from file: " + e.getLocalizedMessage());
                dbjc(bufferedReader2);
                return i;
            } catch (Throwable th2) {
                th = th2;
                dbjc(bufferedReader2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader2 = bufferedReader;
            dbjc(bufferedReader2);
            throw th;
        }
        return i;
    }

    public static void dbjc(String str, File file) {
        File[] listFiles;
        if (file == null || (listFiles = file.listFiles()) == null || listFiles.length == 0) {
            return;
        }
        String lqox = lqox(str);
        for (File file2 : listFiles) {
            String name = file2.getName();
            if (((name != null && name.startsWith(lqox)) || name.startsWith("ndk_crash_timestamp_file")) && file2.delete()) {
                Class cls = dbjc;
                com.good.gd.kloes.hbfhc.wxau(cls, "File Deleted");
                com.good.gd.kloes.ehnkx.qkduk(cls, "Temporary stored file " + name + " deleted.");
            }
        }
    }

    static /* synthetic */ boolean dbjc(JSONObject jSONObject) {
        for (String str : fdyxd.jwxax) {
            if (jSONObject.has(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dbjc(JSONObject jSONObject, boolean z) throws JSONException {
        for (String str : z ? fdyxd.dbjc : fdyxd.qkduk) {
            if (true != jSONObject.has(str)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.OutputStream, java.lang.Throwable] */
    public static boolean dbjc(com.good.gd.ujgjo.hbfhc hbfhcVar, JSONObject jSONObject, String str) {
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Compress Json Into Gzip.");
        OutputStream outputStream = 0;
        try {
            if (hbfhcVar != null) {
                new com.good.gd.ovnkx.aqdzk(str).dbjc();
                outputStream = hbfhcVar.dbjc(str, false);
                if (jSONObject != null && outputStream != null) {
                    return dbjc(jSONObject, outputStream);
                }
                throw new NullPointerException("Compress Json Into Gzip. Json or output stream is empty.");
            }
            throw outputStream;
        } catch (IOException e) {
            com.good.gd.kloes.hbfhc.qkduk(dbjc, "Compress Json Into Gzip - Can't process file.");
            return false;
        } finally {
            dbjc((OutputStream) outputStream);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] dbjc(String r4) {
        /*
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            int r2 = r4.length()     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            byte[] r4 = r4.getBytes(r3)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r2.write(r4)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r2.close()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            byte[] r4 = r1.toByteArray()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r1.close()     // Catch: java.io.IOException -> L26 java.lang.Throwable -> L29
            dbjc(r1)
            return r4
        L26:
            r0 = move-exception
            r0 = r4
            goto L2d
        L29:
            r4 = move-exception
            r0 = r1
            goto L42
        L2c:
            r4 = move-exception
        L2d:
            r4 = r0
            r0 = r1
            goto L34
        L30:
            r4 = move-exception
            goto L42
        L32:
            r4 = move-exception
            r4 = r0
        L34:
            java.lang.Class r1 = com.good.gd.ovnkx.mjbm.dbjc     // Catch: java.lang.Throwable -> L41
            java.lang.String r2 = "Exception for realtime events GZIP compression."
            com.good.gd.kloes.hbfhc.qkduk(r1, r2)     // Catch: java.lang.Throwable -> L41
            if (r0 == 0) goto L40
            dbjc(r0)
        L40:
            return r4
        L41:
            r4 = move-exception
        L42:
            if (r0 == 0) goto L47
            dbjc(r0)
        L47:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ovnkx.mjbm.dbjc(java.lang.String):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean dbjc(JSONObject r4, OutputStream r5) throws IOException {
        /*
            r0 = 0
            java.util.zip.GZIPOutputStream r1 = new java.util.zip.GZIPOutputStream     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            java.io.BufferedWriter r5 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            java.io.OutputStreamWriter r2 = new java.io.OutputStreamWriter     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2d
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L28
            r5.append(r4)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L28
            java.lang.Class r4 = com.good.gd.ovnkx.mjbm.dbjc     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L28
            java.lang.String r0 = "Compress Json To Gzip Internal - Compression complete."
            com.good.gd.kloes.hbfhc.wxau(r4, r0)     // Catch: java.lang.Throwable -> L25 java.io.IOException -> L28
            r4 = 1
            r5.close()
            return r4
        L25:
            r4 = move-exception
            r0 = r5
            goto L52
        L28:
            r4 = move-exception
            r0 = r5
            goto L2e
        L2b:
            r4 = move-exception
            goto L52
        L2d:
            r4 = move-exception
        L2e:
            java.lang.Class r5 = com.good.gd.ovnkx.mjbm.dbjc     // Catch: java.lang.Throwable -> L51
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L51
            r1.<init>()     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = "Unable to compress in GZIP: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L51
            java.lang.String r4 = r4.getLocalizedMessage()     // Catch: java.lang.Throwable -> L51
            java.lang.StringBuilder r4 = r1.append(r4)     // Catch: java.lang.Throwable -> L51
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L51
            com.good.gd.kloes.hbfhc.wxau(r5, r4)     // Catch: java.lang.Throwable -> L51
            r4 = 0
            if (r0 == 0) goto L50
            r0.close()
        L50:
            return r4
        L51:
            r4 = move-exception
        L52:
            if (r0 == 0) goto L57
            r0.close()
        L57:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.ovnkx.mjbm.dbjc(org.json.JSONObject, java.io.OutputStream):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v2 */
    public static JSONObject dbjc(File file, com.good.gd.ujgjo.hbfhc hbfhcVar) {
        InputStream inputStream;
        ArrayList<String> jwxax;
        ?? r0 = 0;
        try {
            try {
                inputStream = hbfhcVar.dbjc(file);
                try {
                    jwxax = jwxax(inputStream);
                    Iterator<String> it = jwxax.iterator();
                    while (it.hasNext()) {
                        com.good.gd.kloes.ehnkx.dbjc(dbjc, "Print Compressed File Content:", it.next());
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    com.good.gd.kloes.hbfhc.qkduk(dbjc, "File not found, cause: " + e.getCause());
                    dbjc(inputStream);
                    return null;
                } catch (JSONException e2) {
                    e = e2;
                    com.good.gd.kloes.hbfhc.qkduk(dbjc, "Compressed file content is not in JSON format: " + e.getCause());
                    dbjc(inputStream);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                r0 = file;
                dbjc((InputStream) r0);
                throw th;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            inputStream = null;
        } catch (JSONException e4) {
            e = e4;
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
            dbjc((InputStream) r0);
            throw th;
        }
        if (jwxax.size() <= 0) {
            dbjc(inputStream);
            return null;
        }
        JSONObject jSONObject = new JSONObject(jwxax.get(0));
        dbjc(inputStream);
        return jSONObject;
    }

    public static void dbjc(File file, File file2, String str, AbstractC0024mjbm abstractC0024mjbm) throws IOException {
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "Move File to Directory.");
        if (file2 != null && file2.exists()) {
            if (abstractC0024mjbm != null) {
                abstractC0024mjbm.dbjc(file.getName() + " will be moved to " + file2.getName());
            }
            if (file.renameTo(new File(file2, str))) {
                com.good.gd.kloes.ehnkx.dbjc(cls, file.getName() + " file has been moved to " + file2.getName());
                if (abstractC0024mjbm != null) {
                    abstractC0024mjbm.dbjc(1, 1);
                }
            }
            if (abstractC0024mjbm == null) {
                return;
            }
            abstractC0024mjbm.dbjc();
            return;
        }
        Object[] objArr = new Object[1];
        objArr[0] = file2 == null ? null : file2.getAbsolutePath();
        throw new IOException(String.format("Can't reach new folder %s, folder doesn't exist", objArr));
    }

    public static void dbjc(Reader reader) {
        if (reader != null) {
            try {
                try {
                    reader.close();
                } catch (IOException e) {
                    com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing reader: " + e.getLocalizedMessage());
                    if (reader == null) {
                        return;
                    }
                    try {
                        reader.close();
                    } catch (IOException e2) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing reader: " + e2.getLocalizedMessage());
                    }
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing reader: " + e3.getLocalizedMessage());
                    }
                }
                throw th;
            }
        }
    }

    public static void dbjc(InputStream inputStream) {
        if (inputStream != null) {
            try {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream: " + e.getLocalizedMessage());
                    if (inputStream == null) {
                        return;
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream: " + e2.getLocalizedMessage());
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream: " + e3.getLocalizedMessage());
                    }
                }
                throw th;
            }
        }
    }

    public static void dbjc(InputStreamReader inputStreamReader) {
        if (inputStreamReader != null) {
            try {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream reader: " + e.getLocalizedMessage());
                    if (inputStreamReader == null) {
                        return;
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e2) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream reader: " + e2.getLocalizedMessage());
                    }
                }
            } catch (Throwable th) {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e3) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "IO Exception while closing input stream reader: " + e3.getLocalizedMessage());
                    }
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.lang.String] */
    public static void dbjc(OutputStream outputStream) {
        ?? r0 = "IO Exception while closing output stream: ";
        try {
            if (outputStream == 0) {
                return;
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                com.good.gd.kloes.hbfhc.wxau(dbjc, r0 + e.getLocalizedMessage());
                if (outputStream == 0) {
                    return;
                }
                try {
                    outputStream.close();
                } catch (IOException e2) {
                    Class cls = dbjc;
                    r0 = new StringBuilder().append(r0);
                    outputStream = r0.append(e2.getLocalizedMessage()).toString();
                    com.good.gd.kloes.hbfhc.wxau(cls, outputStream);
                }
            }
        } catch (Throwable th) {
            if (outputStream != 0) {
                try {
                    outputStream.close();
                } catch (IOException e3) {
                    com.good.gd.kloes.hbfhc.wxau(dbjc, r0 + e3.getLocalizedMessage());
                }
            }
            throw th;
        }
    }

    public static void dbjc(OutputStream outputStream, Context context) throws IOException {
        com.good.gd.kloes.hbfhc.wxau(dbjc, "Begin Writing header to newly created log file.");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("schema", "4.0");
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "Unable to prepare tracked headers: " + e.getLocalizedMessage());
        }
        String str = jSONObject + "\n" + com.blackberry.bis.core.yfdke.tlske().dbjc(context).toString() + "\n";
        if (str == null || true == str.trim().isEmpty()) {
            return;
        }
        outputStream.write(str.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    private static boolean dbjc(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            for (int i = 0; i < length; i++) {
                if (listFiles[i].isDirectory()) {
                    dbjc(listFiles[i]);
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    public static void dbjc(aqdzk aqdzkVar) {
        Class cls = dbjc;
        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Start BIS storage file migration.");
        File createFile = com.blackberry.bis.core.yfdke.qkduk().createFile(".BIS");
        if (createFile != null && createFile.exists()) {
            com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Starting BIS storage migration 3.0 ==>> 3.1.");
            com.good.gd.ovnkx.ehnkx qkduk = com.blackberry.bis.core.yfdke.qkduk();
            File createFile2 = qkduk.createFile(".BIS");
            if (createFile2 != null && true == createFile2.exists()) {
                dbjc(qkduk.createFile(jwxax()), qkduk());
                dbjc(qkduk.createFile(ztwf()), wxau());
                com.good.gd.ujgjo.hbfhc jcpqe = com.good.gd.ujgjo.hbfhc.jcpqe();
                dbjc(qkduk.createFile(wxau("historical")), jcpqe.jwxax("historical"));
                dbjc(qkduk.createFile(tlske("historical")), jcpqe.lqox("historical"));
                dbjc(qkduk.createFile(jcpqe("historical")), jcpqe.ztwf("historical"));
                if (createFile2.exists()) {
                    if (dbjc(createFile2)) {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Successfully removed old root directory.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Failed to remove old root directory.");
                    }
                }
                com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] BIS storage migration from 3.0 ==>> 3.1 is completed.");
            } else {
                com.good.gd.kloes.hbfhc.dbjc(cls, "[Storage Migration] Migration is not required as old BIS directory is not available.");
            }
        } else {
            com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Start - BIS storage migration from 2.x ==>> 3.1.");
            File createFile3 = com.blackberry.bis.core.yfdke.qkduk().createFile("app_instance_id");
            if (createFile3 != null && createFile3.exists()) {
                if (dbjc(createFile3.getPath(), new com.good.gd.ovnkx.aqdzk(wxau(), "app_identifier").wxau().getPath()) && createFile3.exists()) {
                    if (createFile3.delete()) {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Successfully removed old app instance ID file.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Unable to remove old app instance ID file.");
                    }
                }
            }
            File createFile4 = com.blackberry.bis.core.yfdke.qkduk().createFile("auth_token_storage");
            if (createFile4 != null && createFile4.exists() && createFile4.exists()) {
                if (createFile4.delete()) {
                    com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Successfully removed old key storage file.");
                } else {
                    com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Unable to remove old key storage file.");
                }
            }
            File createFile5 = com.blackberry.bis.core.yfdke.qkduk().createFile("persistent_storage");
            if (createFile5 != null && createFile5.exists()) {
                if (dbjc(createFile5.getPath(), new com.good.gd.ovnkx.aqdzk(qkduk(), "network_intervals").wxau().getPath()) && createFile5.exists()) {
                    if (createFile5.delete()) {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Successfully removed old network configuration file.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Unable to remove old network configuration file.");
                    }
                }
            }
            File createFile6 = com.blackberry.bis.core.yfdke.qkduk().createFile("task_scheduler_storage");
            if (createFile6 != null && createFile6.exists()) {
                if (dbjc(createFile6.getPath(), new com.good.gd.ovnkx.aqdzk(qkduk(), "task_scheduler").wxau().getPath()) && createFile6.exists()) {
                    if (createFile6.delete()) {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Successfully removed old task scheduler storage file.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Unable to remove old task scheduler storage file.");
                    }
                }
            }
            com.good.gd.ujgjo.hbfhc jcpqe2 = com.good.gd.ujgjo.hbfhc.jcpqe();
            File createFile7 = com.blackberry.bis.core.yfdke.qkduk().createFile("sending");
            if (createFile7 != null && createFile7.exists() && createFile7.isDirectory()) {
                if (createFile7.list().length > 0) {
                    File ztwf = jcpqe2.ztwf("historical");
                    if (ztwf != null && ztwf.exists() && ztwf.isDirectory()) {
                        for (File file : createFile7.listFiles()) {
                            dbjc(file.getPath(), jcpqe("historical") + wuird(file.getName()));
                        }
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] New historical sending directory is not available.");
                    }
                }
                if (createFile7.exists()) {
                    if (dbjc(createFile7)) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Successfully removed old sending directory.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Failed to remove old sending directory.");
                    }
                }
            } else {
                com.good.gd.kloes.hbfhc.wxau(cls, "[Storage Migration] Old sending directory is not available. upgrading is not required.");
            }
            com.good.gd.ujgjo.hbfhc jcpqe3 = com.good.gd.ujgjo.hbfhc.jcpqe();
            File createFile8 = com.blackberry.bis.core.yfdke.qkduk().createFile("upload");
            if (createFile8 != null && createFile8.exists() && createFile8.isDirectory()) {
                if (createFile8.list().length > 0) {
                    File lqox = jcpqe3.lqox("historical");
                    if (lqox != null && lqox.exists() && lqox.isDirectory()) {
                        for (File file2 : createFile8.listFiles()) {
                            dbjc(file2.getPath(), tlske("historical") + wuird(file2.getName()));
                        }
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] New historical upload directory is not available.");
                    }
                }
                if (createFile8.exists()) {
                    if (dbjc(createFile8)) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Successfully removed old upload directory.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Failed to remove old upload directory.");
                    }
                }
            } else {
                com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Old upload directory is not available. upgrading is not required.");
            }
            com.good.gd.ujgjo.hbfhc jcpqe4 = com.good.gd.ujgjo.hbfhc.jcpqe();
            File createFile9 = com.blackberry.bis.core.yfdke.qkduk().createFile("analytics");
            if (createFile9 != null && createFile9.exists() && createFile9.isDirectory()) {
                if (createFile9.list().length > 0) {
                    File jwxax = jcpqe4.jwxax("historical");
                    if (jwxax != null && jwxax.exists() && jwxax.isDirectory()) {
                        for (File file3 : createFile9.listFiles()) {
                            dbjc(file3.getPath(), wxau("historical") + wuird(file3.getName()));
                        }
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] New historical event logs directory is not available.");
                    }
                }
                if (createFile9.exists()) {
                    if (dbjc(createFile9)) {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Successfully removed old analytics directory.");
                    } else {
                        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Failed to remove old analytics directory.");
                    }
                }
            } else {
                com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Old analytics folder is not available so migration is not applicable.");
            }
            com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] BIS storage migration from 2.x ==>> 3.1 is completed.");
        }
        if (aqdzkVar != null) {
            aqdzkVar.dbjc(true);
        }
        com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] End BIS storage file migration.");
    }

    private static void dbjc(File file, File file2) {
        File[] listFiles;
        com.good.gd.ovnkx.ehnkx odlf = com.blackberry.bis.core.yfdke.odlf();
        if (file == null || !file.exists() || !file.isDirectory() || file2 == null || !file2.exists() || !file2.isDirectory()) {
            return;
        }
        for (File file3 : file.listFiles()) {
            dbjc(file3.getPath(), file2.getPath() + odlf.dbjc() + file3.getName());
        }
    }

    private static boolean dbjc(String str, String str2) {
        Throwable th;
        InputStream inputStream;
        OutputStream outputStream;
        Throwable th2;
        if (!com.good.gd.whhmi.yfdke.qkduk(str) && !com.good.gd.whhmi.yfdke.qkduk(str2)) {
            com.good.gd.ovnkx.ehnkx qkduk = com.blackberry.bis.core.yfdke.qkduk();
            File createFile = qkduk.createFile(str);
            if (createFile != null && true == createFile.exists()) {
                BufferedReader bufferedReader = null;
                if (com.blackberry.bis.core.yfdke.iulf() != null) {
                    com.good.gd.ovnkx.aqdzk aqdzkVar = new com.good.gd.ovnkx.aqdzk(str2);
                    try {
                        if (true != aqdzkVar.jwxax()) {
                            aqdzkVar.dbjc();
                        }
                        inputStream = qkduk.dbjc(createFile);
                        try {
                            outputStream = aqdzkVar.dbjc(false);
                        } catch (IOException e) {
                            outputStream = null;
                        } catch (Throwable th3) {
                            th = th3;
                            outputStream = null;
                        }
                    } catch (IOException e2) {
                        inputStream = null;
                        outputStream = null;
                    } catch (Throwable th4) {
                        th = th4;
                        inputStream = null;
                        outputStream = null;
                    }
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream));
                        try {
                            StringBuilder sb = new StringBuilder();
                            int i = 0;
                            while (true) {
                                String readLine = bufferedReader2.readLine();
                                if (true != com.good.gd.whhmi.yfdke.qkduk(readLine)) {
                                    i++;
                                    if (1 < i) {
                                        sb.append("\n");
                                    }
                                    sb.append(readLine);
                                    outputStream.write(sb.toString().getBytes(HTTP.UTF_8));
                                    sb.setLength(0);
                                } else {
                                    outputStream.flush();
                                    dbjc(bufferedReader2);
                                    dbjc(inputStream);
                                    dbjc(outputStream);
                                    return true;
                                }
                            }
                        } catch (IOException e3) {
                            bufferedReader = bufferedReader2;
                            try {
                                com.good.gd.kloes.hbfhc.dbjc(dbjc, "[Storage Migration] Unable to create destination file to perform file migration.");
                                dbjc(bufferedReader);
                                dbjc(inputStream);
                                dbjc(outputStream);
                                return false;
                            } catch (Throwable th5) {
                                th2 = th5;
                                dbjc(bufferedReader);
                                dbjc(inputStream);
                                dbjc(outputStream);
                                throw th2;
                            }
                        } catch (Throwable th6) {
                            th2 = th6;
                            bufferedReader = bufferedReader2;
                            dbjc(bufferedReader);
                            dbjc(inputStream);
                            dbjc(outputStream);
                            throw th2;
                        }
                    } catch (IOException e4) {
                    } catch (Throwable th7) {
                        th = th7;
                        th2 = th;
                        dbjc(bufferedReader);
                        dbjc(inputStream);
                        dbjc(outputStream);
                        throw th2;
                    }
                } else {
                    throw null;
                }
            } else {
                com.good.gd.kloes.hbfhc.dbjc(dbjc, "[Storage Migration] Source file path required to perform file migration.");
                return false;
            }
        } else {
            com.good.gd.kloes.hbfhc.wxau(dbjc, "[Storage Migration] Proper file paths are required to perform file migration.");
            return false;
        }
    }
}
