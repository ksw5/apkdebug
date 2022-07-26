package com.good.gd.whhmi;

import android.content.Context;
import android.content.res.AssetManager;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.apache.http.protocol.HTTP;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes.dex */
public class fdyxd {
    private String dbjc;
    private JSONObject qkduk;

    public fdyxd(String str) {
        jwxax(str);
    }

    private void jwxax(String str) {
        if (yfdke.qkduk(str)) {
            com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Proper asset file name required, to read a asset file.");
            return;
        }
        try {
            String dbjc = dbjc(str, BlackberryAnalyticsCommon.rynix().jwxax());
            if (true != yfdke.qkduk(dbjc)) {
                if (dbjc.startsWith(Character.toString((char) 65279))) {
                    this.dbjc = dbjc.substring(1);
                    com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Skipping BOM");
                } else {
                    this.dbjc = dbjc;
                }
            }
        } catch (Exception e) {
            com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Unable to read asset file content. " + e.getLocalizedMessage());
        }
        String str2 = this.dbjc;
        if (str2 == null) {
            return;
        }
        try {
            this.qkduk = new JSONObject(new JSONTokener(str2));
        } catch (JSONException e2) {
            com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Unable to parse given asset based file content into JSON.");
        }
        if (this.qkduk == null) {
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("fdyxd", "Successfully read and converted the asset file content into JSON" + str);
    }

    public Boolean dbjc(String str) {
        JSONObject jSONObject = this.qkduk;
        if (jSONObject != null && true == jSONObject.has(str)) {
            try {
                return Boolean.valueOf(this.qkduk.getBoolean(str));
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Unable to find entry for given key into the asset file.");
            }
        }
        return null;
    }

    public String qkduk(String str) {
        JSONObject jSONObject = this.qkduk;
        if (jSONObject != null && true == jSONObject.has(str)) {
            try {
                return this.qkduk.getString(str);
            } catch (JSONException e) {
                com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Unable to find entry for given key into the asset file.");
            }
        }
        return null;
    }

    private String dbjc(String str, Context context) throws Exception {
        String str2;
        AssetManager assets = context.getAssets();
        InputStream inputStream = null;
        if (assets == null) {
            str2 = null;
        } else {
            try {
                inputStream = assets.open(str);
                if (inputStream != null) {
                    byte[] bArr = new byte[2048];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (-1 == read) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    str2 = new String(byteArrayOutputStream.toByteArray(), HTTP.UTF_8);
                } else {
                    throw new Exception("No such file");
                }
            } finally {
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        com.good.gd.kloes.hbfhc.ztwf("fdyxd", "Could not read file " + context + " [" + e.getMessage() + "]\n");
                    }
                }
            }
        }
        return str2;
    }
}
