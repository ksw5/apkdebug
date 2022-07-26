package com.good.gd.bsvvm;

import android.content.SharedPreferences;
import com.good.gd.GDAndroidAPI;
import com.good.gd.error.GDNotAuthorizedError;
import java.util.Map;

/* loaded from: classes.dex */
public class hbfhc implements com.good.gd.whhmi.pmoiy {
    private static final String[] qkduk = {"keyStoreUpgradeStatusKey"};
    private SharedPreferences dbjc = null;

    private SharedPreferences jwxax() {
        if (true != com.blackberry.analytics.analyticsengine.fdyxd.pqq().ssosk()) {
            com.good.gd.kloes.hbfhc.wxau("BlackberryAnalytics", "Can't obtain the secure Shared Preferences, GD container is not authorized at least once.");
            return null;
        }
        GDAndroidAPI wxau = com.blackberry.analytics.analyticsengine.yfdke.wxau();
        if (wxau == null) {
            return null;
        }
        try {
            return wxau.getGDSharedPreferences("BISGDSharedPrefs", 0);
        } catch (GDNotAuthorizedError e) {
            com.good.gd.kloes.hbfhc.wxau("BlackberryAnalytics", "Can't obtain the secure Shared Preferences, GD container is not authorized at least once.");
            return null;
        }
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc() {
        this.dbjc = jwxax();
    }

    @Override // com.good.gd.whhmi.pmoiy
    public boolean qkduk(String str) {
        SharedPreferences sharedPreferences;
        return (true == com.good.gd.whhmi.yfdke.qkduk(str) || (sharedPreferences = this.dbjc) == null || !sharedPreferences.contains(str)) ? false : true;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc(String str, String str2) {
        SharedPreferences.Editor edit;
        if (true != com.good.gd.whhmi.yfdke.qkduk(str)) {
            if (this.dbjc == null) {
                this.dbjc = jwxax();
            }
            SharedPreferences sharedPreferences = this.dbjc;
            if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
                return;
            }
            edit.putString(str, str2);
            edit.commit();
        }
    }

    public void qkduk() {
        SharedPreferences.Editor edit;
        SharedPreferences sharedPreferences = this.dbjc;
        if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
            return;
        }
        com.good.gd.kloes.hbfhc.wxau("BlackberryAnalytics", "Cleaning up non essential data from GD shared preferences.");
        for (Map.Entry<String, ?> entry : this.dbjc.getAll().entrySet()) {
            String key = entry.getKey();
            int length = qkduk.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                } else if (qkduk[i].equals(key)) {
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                edit.remove(key);
            }
        }
        edit.commit();
    }

    @Override // com.good.gd.whhmi.pmoiy
    public String dbjc(String str) {
        SharedPreferences sharedPreferences;
        if (!com.good.gd.whhmi.yfdke.qkduk(str) && (sharedPreferences = this.dbjc) != null) {
            return sharedPreferences.getString(str, null);
        }
        return null;
    }

    @Override // com.good.gd.whhmi.pmoiy
    public void dbjc(String str, boolean z) {
        SharedPreferences.Editor edit;
        if (true != com.good.gd.whhmi.yfdke.qkduk(str)) {
            if (this.dbjc == null) {
                this.dbjc = jwxax();
            }
            SharedPreferences sharedPreferences = this.dbjc;
            if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null) {
                return;
            }
            edit.putBoolean(str, z);
            edit.commit();
        }
    }
}
