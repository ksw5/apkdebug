package com.good.gd.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;

/* loaded from: classes.dex */
public final class GDApplicationUtils {
    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo;
        if (context == null || (applicationInfo = context.getApplicationInfo()) == null) {
            return "";
        }
        try {
            return context.getString(applicationInfo.labelRes);
        } catch (Resources.NotFoundException e) {
            return applicationInfo.loadLabel(context.getPackageManager()).toString();
        }
    }
}
