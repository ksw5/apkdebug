package com.good.gd.utils;

import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.GDSettings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ApiPermission {
    private static final String TAG = "GD";
    private String m_signScheme;
    private String m_signature;
    private JSONObject m_stringMap;
    private boolean m_valid;

    /* loaded from: classes.dex */
    public interface SignatureChecker {
        boolean checkSignature(byte[] bArr, String str);
    }

    private ApiPermission(JSONObject jSONObject) {
        try {
            this.m_signature = jSONObject.getString("Signature");
            this.m_signScheme = jSONObject.getString("SignatureScheme");
            this.m_stringMap = jSONObject.getJSONObject("Permission");
            this.m_valid = true;
        } catch (JSONException e) {
            GDLog.DBGPRINTF(12, "GDApiPermission: invalid due to JSON error: " + e.getMessage());
            this.m_valid = false;
        }
    }

    private boolean _matchValue(String str, String str2) {
        try {
            return str2.equals(this.m_stringMap.getString(str));
        } catch (JSONException e) {
            return false;
        }
    }

    public static ApiPermission[] getFromSettings() {
        JSONObject mainSettings = GDSettings.getMainSettings();
        if (mainSettings == null) {
            return null;
        }
        try {
            JSONArray jSONArray = mainSettings.getJSONArray("GDPermissions");
            int length = jSONArray.length();
            ApiPermission[] apiPermissionArr = new ApiPermission[length];
            for (int i = 0; i < length; i++) {
                apiPermissionArr[i] = new ApiPermission(jSONArray.getJSONObject(i));
            }
            return apiPermissionArr;
        } catch (JSONException e) {
            GDLog.DBGPRINTF(12, "GDApiPermission: getFromSettings: JSON error: " + e.getMessage());
            return null;
        }
    }

    public boolean checkPermission(String str, String str2, String str3) {
        GDLog.DBGPRINTF(16, "ApiPermission: checkPermission (" + str + "," + str2 + "," + str3 + ")");
        return this.m_valid && _matchValue(str3, "1") && _matchValue("GDApplicationId", str) && _matchValue("nativeApplicationId", str2) && this.m_stringMap.length() == 3;
    }

    public boolean checkSignature(SignatureChecker signatureChecker) {
        if (this.m_valid && this.m_signScheme.equals("RSAv1")) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                int length = this.m_stringMap.length();
                String[] strArr = new String[length];
                Iterator<String> keys = this.m_stringMap.keys();
                int i = 0;
                while (keys.hasNext()) {
                    strArr[i] = keys.next();
                    i++;
                }
                Arrays.sort(strArr);
                for (int i2 = 0; i2 < length; i2++) {
                    writeStringEncoding('T', strArr[i2], byteArrayOutputStream);
                    writeStringEncoding('S', this.m_stringMap.getString(strArr[i2]), byteArrayOutputStream);
                }
                if (signatureChecker.checkSignature(byteArrayOutputStream.toByteArray(), this.m_signature)) {
                    return true;
                }
                GDLog.DBGPRINTF(12, "GDApiPermission: Signature failed to verify");
                return false;
            } catch (UnsupportedEncodingException e) {
                GDLog.DBGPRINTF(12, "GDApiPermission: Error (Encoding): " + e.getMessage());
                return false;
            } catch (IOException e2) {
                GDLog.DBGPRINTF(12, "GDApiPermission: Error (IOError): " + e2.getMessage());
                return false;
            } catch (JSONException e3) {
                GDLog.DBGPRINTF(12, "GDApiPermission: Error (JSON): " + e3.getMessage());
                return false;
            }
        }
        GDLog.DBGPRINTF(12, "GDApiPermission: Invalid signature scheme");
        return false;
    }

    public void writeStringEncoding(char c, String str, OutputStream outputStream) throws UnsupportedEncodingException, IOException {
        byte[] bytes = str.getBytes(HTTP.UTF_8);
        outputStream.write(String.format("%c%08X", Character.valueOf(c), Integer.valueOf(bytes.length)).getBytes());
        outputStream.write(bytes);
    }
}
