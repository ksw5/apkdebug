package com.good.gd.interception;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public class Utils {
    static final String TAG = "Utils";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ResolveInfo> getActivitiesInfo(Intent intent, Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            try {
                GDLog.DBGPRINTF(16, resolveInfo.activityInfo.packageName + " appName : " + packageManager.getApplicationLabel(packageManager.getApplicationInfo(resolveInfo.activityInfo.packageName, 0)).toString());
                GDLog.DBGPRINTF(16, resolveInfo.activityInfo.packageName + " : " + resolveInfo.activityInfo.name);
            } catch (PackageManager.NameNotFoundException e) {
                GDLog.DBGPRINTF(12, "getActivitiesInfo :", e);
            }
        }
        return queryIntentActivities;
    }

    static String getAppName(String str, Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
        } catch (Exception e) {
            GDLog.DBGPRINTF(12, "getAppReadableName :", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<String> getAppReadableNames(Collection<String> collection, Context context) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (String str : collection) {
            arrayList.add(getAppName(str, context));
        }
        return arrayList;
    }
}
