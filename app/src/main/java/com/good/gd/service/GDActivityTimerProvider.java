package com.good.gd.service;

import android.os.Build;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.service.activity_timer.ActivityTimer;
import com.good.gd.service.activity_timer.ActivityTimerAndroidS;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class GDActivityTimerProvider {
    private static GDActivityTimerProvider activityTimerProvider;
    private final ActivityTimer activityTimer;

    public GDActivityTimerProvider() {
        if (isTargetingAndroidSOrHigher()) {
            GDLog.DBGPRINTF(14, "ActivityTimerProvider::ActivityTimerProvider() create ActivityTimer for Android S or higher \n");
            this.activityTimer = new ActivityTimerAndroidS();
            return;
        }
        GDLog.DBGPRINTF(14, "ActivityTimerProvider::ActivityTimerProvider() create ActivityTimer for Android R or lower \n");
        this.activityTimer = hbfhc.qkduk();
    }

    public static void createInstance() {
        if (activityTimerProvider == null) {
            activityTimerProvider = new GDActivityTimerProvider();
        }
    }

    public static GDActivityTimerProvider getInstance() {
        GDLog.DBGPRINTF(14, "ActivityTimerProvider::getInstance " + activityTimerProvider + "\n");
        return activityTimerProvider;
    }

    private boolean isTargetingAndroidSOrHigher() {
        return GTBaseContext.getInstance().getApplicationContext().getApplicationInfo().targetSdkVersion > 30 && ((Build.VERSION.SDK_INT > 30) || (Build.VERSION.PREVIEW_SDK_INT != 0));
    }

    public ActivityTimer getActivityTimer() {
        return this.activityTimer;
    }
}
