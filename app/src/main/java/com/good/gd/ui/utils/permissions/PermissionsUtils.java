package com.good.gd.ui.utils.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/* loaded from: classes.dex */
public class PermissionsUtils {
    public static boolean hasPermissionInManifest(String str, Context context) {
        try {
            for (String str2 : context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPermissionGranted(String str, Context context) {
        return context.checkSelfPermission(str) == 0;
    }

    public static boolean shouldCheckBackgroundLocationPermission(Context context, int i, int i2) {
        int i3;
        try {
            i3 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            i3 = 0;
        }
        return Build.VERSION.SDK_INT >= i && i3 >= i2;
    }
}
