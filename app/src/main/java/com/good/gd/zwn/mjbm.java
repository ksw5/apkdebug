package com.good.gd.zwn;

import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.apache.http.auth.AUTH;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class mjbm {
    private static boolean dbjc = false;
    private static String jwxax;
    private static String qkduk;
    private static String wxau;

    /* loaded from: classes.dex */
    public static class hbfhc {
        static final int[] dbjc = {2, 4, 8};
        static String qkduk = "receiver.analytics.blackberry.com";
        public static String jwxax = String.format(Locale.getDefault(), "%s%s:%d", "https://", qkduk, 443);
        public static String wxau = String.format(Locale.getDefault(), "%s%s:%d", "http://", "docker-001.bb-c7101.devlab.sis.bblabs", 8100);
        public static final char[] ztwf = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        static final Class lqox = mjbm.class;
    }

    public static void dbjc(boolean z) {
        dbjc = z;
    }

    public static void jwxax(String str) {
        if (!com.good.gd.whhmi.yfdke.qkduk(str)) {
            hbfhc.jwxax = com.good.gd.whhmi.ehnkx.dbjc(str);
            com.good.gd.kloes.hbfhc.jwxax(hbfhc.lqox, "Setting Analytics Base Url.");
            com.good.gd.kloes.ehnkx.qkduk(hbfhc.lqox, "Analytics Base Url: " + hbfhc.jwxax);
        }
    }

    public static boolean liflu() {
        return dbjc;
    }

    public static void lqox(String str) {
        qkduk = str;
    }

    public static String qkduk(int i) {
        return i == 1000 ? "Error occur in network request." : i == 1002 ? "Upload Configs are invalid." : i == 1003 ? "Network is turned off." : i == 1004 ? "All files are successfully uploaded." : i == 1005 ? "File System is locked." : i == 1007 ? "No file to upload." : i == 1008 ? "No file to upload after deleting the file having compression error." : i == 1009 ? "Error in generating Token." : i == 1010 ? "Application is background." : i == 1012 ? "File is empty." : i == 1013 ? "Application Id is missing." : i == 1014 ? "Authorization Secret Key is missing." : i == 1015 ? "Token is timed out." : i == 1016 ? "Analytics is disabled." : i == 1020 ? "Get WAN-IP request is failed." : i == 1021 ? "No route to host." : i == 1022 ? "SRA network request is failed." : i == 1023 ? "SRA request cannot be executed." : i == 1024 ? "CA request cannot be executed." : i == 1025 ? "Client Id cannot be null or empty." : i == 1026 ? "Scope cannot be null or empty." : i == 1027 ? "Resource Server cannot be null." : i == 1028 ? "EID Token cannot be fetched in Non-Dynamics App." : i == 1029 ? "EID Token cannot be fetched as EID Token infrastructure is not activated." : i == 1031 ? "ReAuthentication Failed." : i == 1032 ? "CA request is failed." : i == 1033 ? "Payload formed is null or empty." : i == 1034 ? "SRA Events are null" : i == 1035 ? "CA Events are null" : i == 1036 ? "BIS is disabled, can't perform operation" : i == 1037 ? "Geozone network request is failed" : i == 200 ? "Get data, Request Success." : i == 204 ? "Post data, Request Success." : i == 400 ? "Bad Request." : i == 401 ? "Error in Token." : i == 404 ? "Receiver end point, not found." : i == 429 ? "Too many request at Receiver." : (i == 500 || i == 501 || i == 503 || i == 505) ? "Internal Server Error." : i == 1039 ? "SDE request cannot be executed." : i == 1038 ? "SDE request is failed." : i == 1043 ? "Network request has been timed out." : "Unknown error.";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, String> qkduk() {
        return ((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()).dbjc();
    }

    public static void wxau(String str) {
        wxau = str;
    }

    public static void ztwf(String str) {
        jwxax = str;
    }

    public static void dbjc(String str, int i) {
        if (!com.good.gd.whhmi.yfdke.qkduk(str)) {
            hbfhc.jwxax = com.good.gd.whhmi.ehnkx.dbjc(str, i);
            com.good.gd.kloes.hbfhc.jwxax(hbfhc.lqox, "Setting Analytics Base Url and Port.");
            com.good.gd.kloes.ehnkx.qkduk(hbfhc.lqox, "Analytics Base Url and Port: " + hbfhc.jwxax);
        }
    }

    public static String lqox() {
        return qkduk;
    }

    public static void qkduk(Map<String, String> map, String str) {
        if (((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()) != null) {
            switch (BlackberryAnalyticsCommon.rynix().lqox()) {
                case 1:
                    map.put(AUTH.WWW_AUTH_RESP, "Good-GD-GNPToken " + str);
                    return;
                case 2:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-GNP-Token " + str);
                    return;
                case 3:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-GNP-Token " + str);
                    return;
                case 4:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-JWT-Token " + str);
                    return;
                default:
                    return;
            }
        }
        throw null;
    }

    public static String wxau() {
        return wxau;
    }

    public static String ztwf() {
        return jwxax;
    }

    public static String jwxax() {
        StringBuilder sb = new StringBuilder();
        sb.append("corID-");
        new SecureRandom();
        sb.append(dbjc(20));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static HashMap<String, String> dbjc() {
        if (((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()) != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            if (BlackberryAnalyticsCommon.rynix().ztwf() == 1) {
                hashMap.put("Accept", "application/vnd.good.analytics.ecs.clientsettings.1");
            }
            int ztwf = BlackberryAnalyticsCommon.rynix().ztwf();
            if (ztwf == 2) {
                hashMap.put("Accept", "application/vnd.bberry.analytics.settings.dynamics.2");
                hashMap.put("X-BBRY-Timestamp", String.valueOf(System.currentTimeMillis()));
            } else if (ztwf == 3 || ztwf == 4) {
                hashMap.put("Accept", "application/vnd.bbry.analytics.settings.3");
                hashMap.put("X-BBRY-Timestamp", String.valueOf(System.currentTimeMillis()));
            }
            return hashMap;
        }
        throw null;
    }

    public static com.good.gd.dvql.fdyxd qkduk(String str) {
        JSONObject jSONObject;
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        try {
            Object nextValue = new JSONTokener(str).nextValue();
            if (nextValue instanceof JSONObject) {
                jSONObject = new JSONObject(str);
            } else if (!(nextValue instanceof JSONArray)) {
                jSONObject = null;
            } else {
                jSONObject = new JSONArray(str).getJSONObject(0);
            }
            if (jSONObject == null) {
                return null;
            }
            String optString = jSONObject.optString("errCode");
            jSONObject.optString("errMsg");
            com.good.gd.dvql.fdyxd fdyxdVar = new com.good.gd.dvql.fdyxd();
            fdyxdVar.dbjc(optString);
            return fdyxdVar;
        } catch (JSONException e) {
            com.good.gd.kloes.hbfhc.wxau(hbfhc.lqox, "Unable to parse network error response : " + e.getLocalizedMessage());
            return null;
        }
    }

    public static void dbjc(Map<String, String> map, String str) {
        if (((com.good.gd.npgvd.vzw) com.blackberry.bis.core.yfdke.ssosk()) != null) {
            switch (BlackberryAnalyticsCommon.rynix().ztwf()) {
                case 1:
                    map.put(AUTH.WWW_AUTH_RESP, "Good-GD-GNPToken " + str);
                    return;
                case 2:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-GNP-Token " + str);
                    return;
                case 3:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-GNP-Token " + str);
                    return;
                case 4:
                    map.put(AUTH.WWW_AUTH_RESP, "BBRY-JWT-Token " + str);
                    return;
                default:
                    return;
            }
        }
        throw null;
    }

    public static String dbjc(int i) {
        StringBuilder sb = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i2 = 0; i2 < i; i2++) {
            char[] cArr = hbfhc.ztwf;
            sb.append(cArr[secureRandom.nextInt(cArr.length)]);
        }
        return sb.toString();
    }

    public static void dbjc(Map<String, String> map) {
        if (map == null || true == map.isEmpty()) {
            return;
        }
        for (String str : map.keySet()) {
            com.good.gd.kloes.ehnkx.jwxax(hbfhc.lqox, String.format("[%s] = %s", str, map.get(str)));
        }
    }

    public static String dbjc(boolean z, InputStream inputStream) {
        Throwable th;
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        if (inputStream == null) {
            return "No Content";
        }
        InputStreamReader inputStreamReader2 = null;
        try {
            if (z) {
                inputStreamReader = new InputStreamReader(new GZIPInputStream(inputStream), HTTP.UTF_8);
            } else {
                inputStreamReader = new InputStreamReader(inputStream, HTTP.UTF_8);
            }
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (IOException e) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (IOException e2) {
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    String sb2 = sb.toString();
                    com.good.gd.ovnkx.mjbm.dbjc(bufferedReader);
                    com.good.gd.ovnkx.mjbm.dbjc(inputStreamReader);
                    return sb2;
                }
            }
        } catch (IOException e3) {
            inputStreamReader2 = inputStreamReader;
            com.good.gd.ovnkx.mjbm.dbjc(bufferedReader);
            com.good.gd.ovnkx.mjbm.dbjc(inputStreamReader2);
            return "No Content";
        } catch (Throwable th4) {
            th = th4;
            inputStreamReader2 = inputStreamReader;
            com.good.gd.ovnkx.mjbm.dbjc(bufferedReader);
            com.good.gd.ovnkx.mjbm.dbjc(inputStreamReader2);
            throw th;
        }
    }

    public static boolean dbjc(String str) {
        if (str != null && str.trim().length() != 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("getClientSettings");
                JSONObject jSONObject3 = jSONObject.getJSONObject("uploadEventsSettings");
                if (!jSONObject2.has("getInterval") || !jSONObject2.has("randomizationWindow") || !jSONObject3.has("uploadInterval")) {
                    return false;
                }
                return jSONObject3.has("randomizationWindow");
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.wxau(hbfhc.lqox, "Required keys not found in Config response: " + e.getLocalizedMessage());
            }
        }
        return false;
    }
}
