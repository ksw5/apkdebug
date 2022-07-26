package com.good.gd.ndkproxy.settings;

import android.content.Context;
import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.ndkproxy.GDLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class GDSettingsJson {
    private static final String TAG = "GD";
    private String mDevelopmentTools;
    private String mSettingsContent;
    private JSONObject mSettingsObject;

    public GDSettingsJson(String str, Context context, boolean z) {
        try {
            this.mDevelopmentTools = getSettingsFileContent("development-tools-info.json", context);
            GDLog.DBGPRINTF(16, "abx1: " + this.mDevelopmentTools.length());
        } catch (Exception e) {
            GDLog.DBGPRINTF_UNSECURE(13, TAG, "GDSettingsJson: could not initialize " + str + " [" + e.getMessage() + "]\n");
        }
        String str2 = this.mDevelopmentTools;
        if (str2 == null || str2.length() == 0) {
            try {
                this.mDevelopmentTools = getSettingsFileContent("xamarin-info.json", context);
                GDLog.DBGPRINTF(16, "abx2: " + this.mDevelopmentTools.length());
            } catch (Exception e2) {
                GDLog.DBGPRINTF_UNSECURE(13, TAG, "GDSettingsJson: could not initialize " + str + " [" + e2.getMessage() + "]\n");
            }
        }
        try {
            String settingsFileContent = getSettingsFileContent(str, context);
            if (settingsFileContent.startsWith(Character.toString((char) 65279))) {
                this.mSettingsContent = settingsFileContent.substring(1);
                GDLog.DBGPRINTF_UNSECURE(13, TAG, "GDSettingsJson: skipping BOM");
            } else {
                this.mSettingsContent = settingsFileContent;
            }
        } catch (Exception e3) {
            if (z) {
                GDLog.DBGPRINTF_UNSECURE(13, TAG, "GDSettingsJson: could not initialize " + str + " [" + e3.getMessage() + "]\n");
            }
        }
        String str3 = this.mSettingsContent;
        if (str3 != null) {
            try {
                this.mSettingsObject = parseJSONObject(str3);
            } catch (JSONException e4) {
                GDLog.DBGPRINTF_UNSECURE(13, TAG, "GDSettingsJson: could not parse " + str + " [" + e4.getMessage() + "]\n");
            }
            if (this.mSettingsObject == null) {
                return;
            }
            GDLog.DBGPRINTF_UNSECURE(16, TAG, "GDSettingsJson: initialized " + str + "\n");
        }
    }

    private static String getSettingsFileContent(String str, Context context) throws Exception {
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(str);
            try {
                if (inputStream != null) {
                    String str2 = new String(readBytes(inputStream), HTTP.UTF_8);
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        GDLog.DBGPRINTF_UNSECURE(13, TAG, "Could not read file " + str + " [" + e.getMessage() + "]\n");
                    }
                    return str2;
                }
                throw new Exception("No such file");
            } catch (Throwable th) {
                th = th;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                        GDLog.DBGPRINTF_UNSECURE(13, TAG, "Could not read file " + str + " [" + e2.getMessage() + "]\n");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
    }

    private static JSONObject parseJSONObject(String str) throws JSONException {
        return new JSONObject(new JSONTokener(str));
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

    public String checkSettingsFileForKey(String str) {
        JSONObject jSONObject = this.mSettingsObject;
        if (jSONObject != null && jSONObject.has(str)) {
            try {
                String string = this.mSettingsObject.getString(str);
                if (string != null && !string.isEmpty()) {
                    GDLog.DBGPRINTF(16, "GDSettingsJson: key = " + str + " has value " + string + "\n");
                    return string;
                }
                GDLog.DBGPRINTF(13, "GDSettingsJson: Key = " + str + " has empty value\n");
                return null;
            } catch (JSONException e) {
                GDLog.DBGPRINTF(16, "GDSettingsJson: Missing key " + str + "\n");
            }
        }
        return null;
    }

    public String getContent() {
        return this.mSettingsContent;
    }

    public String getDevelopmentToolsJSON() {
        return this.mDevelopmentTools;
    }

    public JSONObject getJSONObject() {
        return this.mSettingsObject;
    }

    public boolean isValid() {
        return this.mSettingsObject != null;
    }
}
