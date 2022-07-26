package com.good.gd.ndkproxy;

import android.content.Context;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.settings.GDSettingsJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class GDSettings {
    private static final String CONFIG_FILE = "config.info";
    private static final String SETTINGS_FILE = "settings.json";
    private static final String SETTINGS_FILE_V2 = "com.blackberry.dynamics.settings.json";
    private static boolean lightweightInfoInitialized = false;
    private static GDSettingsJson settingsJsonV1;
    private static GDSettingsJson settingsJsonV2;

    public static void applyOverrides() {
        String userSelectedConfigOverride;
        synchronized (NativeExecutionHandler.nativeLock) {
            userSelectedConfigOverride = getUserSelectedConfigOverride();
        }
        if (userSelectedConfigOverride == null || userSelectedConfigOverride.isEmpty()) {
            cacheConfigFromAssets(readConfigFromAssets());
        }
    }

    private static native void cacheConfigFromAssets(String str);

    public static String checkSettingsFileForKey(String str, boolean z) {
        if (z) {
            return settingsJsonV2.checkSettingsFileForKey(str);
        }
        return settingsJsonV1.checkSettingsFileForKey(str);
    }

    private static native void developmentToolsFromAssets(String str);

    public static String getConfigOverride() {
        return getUserSelectedConfigOverride();
    }

    public static JSONObject getMainSettings() {
        GDSettingsJson gDSettingsJson = settingsJsonV1;
        if (gDSettingsJson != null) {
            return gDSettingsJson.getJSONObject();
        }
        return null;
    }

    public static JSONObject getSettingsV2() {
        GDSettingsJson gDSettingsJson = settingsJsonV2;
        if (gDSettingsJson != null) {
            return gDSettingsJson.getJSONObject();
        }
        return null;
    }

    private static native String getUserSelectedConfigOverride();

    public static void initialize() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (settingsJsonV1 == null) {
            settingsJsonV1 = new GDSettingsJson(SETTINGS_FILE, applicationContext, true);
        }
        if (settingsJsonV2 == null) {
            settingsJsonV2 = new GDSettingsJson(SETTINGS_FILE_V2, applicationContext, false);
        }
        String packageName = GDContext.getInstance().getApplicationContext().getPackageName();
        if (settingsJsonV1.getContent() == null && packageName == null) {
            return;
        }
        synchronized (NativeExecutionHandler.nativeLock) {
            initializeAppSettings(packageName, settingsJsonV1.getContent());
            developmentToolsFromAssets(settingsJsonV1.getDevelopmentToolsJSON());
        }
    }

    private static native void initializeAppSettings(String str, String str2);

    public static void initializeLightweightInfo(Context context) {
        if (!lightweightInfoInitialized) {
            if (settingsJsonV2 == null) {
                settingsJsonV2 = new GDSettingsJson(SETTINGS_FILE_V2, context, false);
            }
            lightweightInfoInitialized = true;
        }
    }

    private static byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 != read) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    private static String readConfigFromAssets() {
        try {
            InputStream open = GDContext.getInstance().getApplicationContext().getAssets().open(CONFIG_FILE);
            String str = new String(readBytes(open), HTTP.UTF_8);
            if (open == null) {
                return str;
            }
            try {
                open.close();
                return str;
            } catch (Exception e) {
                return str;
            }
        } catch (Exception e2) {
            return null;
        }
    }
}
