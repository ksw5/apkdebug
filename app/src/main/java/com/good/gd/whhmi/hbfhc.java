package com.good.gd.whhmi;

import android.app.Application;
import android.content.SharedPreferences;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import java.util.ArrayDeque;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes.dex */
public class hbfhc implements pmoiy {
    private static hbfhc wxau;
    private static Application ztwf;
    private SharedPreferences dbjc;
    private JSONArray qkduk = new JSONArray();
    private final Class jwxax = hbfhc.class;

    private hbfhc() {
    }

    public static hbfhc wxau() {
        if (wxau == null) {
            synchronized (hbfhc.class) {
                if (wxau == null) {
                    wxau = new hbfhc();
                }
            }
        }
        return wxau;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc() {
    }

    public void dbjc(Application application) {
        if (ztwf == null) {
            ztwf = application;
            SharedPreferences sharedPreferences = application.getSharedPreferences("Analytics_Preferences", 0);
            this.dbjc = sharedPreferences;
            String string = sharedPreferences.getString("Crash_Array", null);
            if (string == null || true == string.trim().isEmpty()) {
                return;
            }
            try {
                this.qkduk = new JSONArray(string);
            } catch (JSONException e) {
            }
        }
    }

    public void jwxax(String str) {
        SharedPreferences.Editor edit;
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        edit.remove(str);
        if (!edit.commit() || !"Crash_Array".equals(str)) {
            return;
        }
        this.qkduk = new JSONArray();
    }

    @Override // com.good.gd.whhmi.pmoiy
    public boolean qkduk(String str) {
        SharedPreferences sharedPreferences = this.dbjc;
        return sharedPreferences != null && sharedPreferences.contains(str);
    }

    public void qkduk() {
        com.good.gd.kloes.hbfhc.wxau(this.jwxax, "Deleting Analytics Preferences");
        BlackberryAnalyticsCommon.rynix().jwxax().deleteSharedPreferences("Analytics_Preferences");
        this.dbjc = null;
    }

    public ArrayDeque<String> jwxax() {
        JSONArray jSONArray;
        if (this.dbjc != null && (jSONArray = this.qkduk) != null && jSONArray.length() > 0) {
            ArrayDeque<String> arrayDeque = new ArrayDeque<>();
            try {
                int length = this.qkduk.length();
                for (int i = 0; i < length; i++) {
                    arrayDeque.add(this.qkduk.getString(i));
                }
                return arrayDeque;
            } catch (JSONException e) {
            }
        }
        return null;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc(String str, String str2) {
        SharedPreferences.Editor edit;
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        edit.putString(str, str2);
        edit.commit();
    }

    public boolean wxau(String str) {
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(str, false);
        }
        return false;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public String dbjc(String str) {
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str, null);
        }
        return null;
    }

    public int dbjc(String str, int i) {
        SharedPreferences sharedPreferences = this.dbjc;
        return sharedPreferences != null ? sharedPreferences.getInt(str, i) : i;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc(String str, boolean z) {
        SharedPreferences.Editor edit;
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        edit.putBoolean(str, z);
        edit.commit();
    }
}
