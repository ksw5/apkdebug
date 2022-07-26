package com.good.gd.nme;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.os.Build;
import com.blackberry.dynamics.ndkproxy.utils.HWASettings;
import com.blackberry.dynamics.ndkproxy.utils.UserPreferences;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class hbfhc extends UserPreferences {
    private static hbfhc dbjc;

    private hbfhc() {
        try {
            HWASettings.NI();
            nI();
        } catch (Throwable th) {
            GDLog.DBGPRINTF(12, "devicesecurity.DeviceSecurity" + th.getStackTrace()[0].getMethodName() + " error:" + th);
        }
    }

    public static hbfhc wxau() {
        if (dbjc == null) {
            synchronized (hbfhc.class) {
                if (dbjc == null) {
                    dbjc = new hbfhc();
                }
            }
        }
        return dbjc;
    }

    private boolean ztwf() {
        if (Build.VERSION.SDK_INT >= 27) {
            return GTBaseContext.getInstance().getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.type.pc");
        }
        return false;
    }

    public boolean dbjc() {
        if (ztwf()) {
            return true;
        }
        int storageEncryptionStatus = ((DevicePolicyManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("device_policy")).getStorageEncryptionStatus();
        GDLog.DBGPRINTF(16, "devicesecurity.DeviceSecurity", "Device Encryption status is: " + storageEncryptionStatus);
        return storageEncryptionStatus == 3 || storageEncryptionStatus == 5 || storageEncryptionStatus == 4;
    }

    public boolean jwxax() {
        boolean isRunningInUserTestHarness;
        if (!ActivityManager.isUserAMonkey()) {
            if (Build.VERSION.SDK_INT < 29) {
                isRunningInUserTestHarness = ActivityManager.isRunningInTestHarness();
            } else {
                isRunningInUserTestHarness = ActivityManager.isRunningInUserTestHarness();
            }
            if (!isRunningInUserTestHarness) {
                return false;
            }
        }
        return true;
    }

    public int qkduk() {
        if (ztwf()) {
            return 1;
        }
        if (Build.VERSION.SDK_INT < 29) {
            return ((KeyguardManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("keyguard")).isKeyguardSecure() ? 1 : 0;
        }
        int passwordComplexity = ((DevicePolicyManager) GTBaseContext.getInstance().getApplicationContext().getSystemService("device_policy")).getPasswordComplexity();
        if (passwordComplexity == 0) {
            return 0;
        }
        if (passwordComplexity == 65536) {
            return 2;
        }
        if (passwordComplexity == 196608) {
            return 3;
        }
        return passwordComplexity != 327680 ? 1 : 4;
    }
}
