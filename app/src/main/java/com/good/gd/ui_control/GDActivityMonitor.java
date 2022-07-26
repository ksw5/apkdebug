package com.good.gd.ui_control;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.good.gd.GDAndroid;
import com.good.gd.context.GDContext;
import com.good.gd.machines.activation.GDActivationManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDActivityUtils;
import com.good.gd.utils.UserAuthUtils;

/* loaded from: classes.dex */
public class GDActivityMonitor implements Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "GDActivityMonitor ";
    public static String nameGDMonitorActivityAutoInsert = "GDMonitorActivity";

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor Activity " + activity.getLocalClassName() + " onActivityCreated\n");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor Activity " + activity.getLocalClassName() + " onActivityDestroyed\n");
        UniversalActivityController.getInstance().clearContentActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor Activity " + activity.getLocalClassName() + " onActivityPaused\n");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor Activity " + activity.getLocalClassName() + " onActivityResumed\n");
        GDContext.getInstance().setContext(activity.getApplicationContext());
        if (!UserAuthUtils.isActivated()) {
            GDActivationManager gDActivationManager = GDActivationManager.getInstance();
            if (gDActivationManager == null) {
                return;
            }
            gDActivationManager.rollbackActivity(activity);
        } else if (GDActivityUtils.checkIsGDInternalActivity(activity) || GDActivityUtils.checkIsGDActivity(activity) || activity.getFragmentManager().findFragmentByTag(GDMonitorFragmentImpl.GDMONITORFRAGMENT) != null) {
        } else {
            ActivityInfo activityInfo = null;
            try {
                activityInfo = activity.getPackageManager().getActivityInfo(activity.getComponentName(), 128);
            } catch (PackageManager.NameNotFoundException e) {
                GDLog.DBGPRINTF(12, "GDActivityMonitor Activity metaData is null. Unable to get meta data for " + nameGDMonitorActivityAutoInsert + "\n", e);
            }
            boolean z = false;
            Bundle bundle = activityInfo.metaData;
            if (bundle == null) {
                GDLog.DBGPRINTF(16, "GDActivityMonitor Activity metaData is null. Unable to get meta data for " + nameGDMonitorActivityAutoInsert + "\n");
            } else {
                Object obj = bundle.get(nameGDMonitorActivityAutoInsert);
                if (obj != null) {
                    z = ((Boolean) obj).booleanValue();
                }
            }
            if (!z) {
                GDLog.DBGPRINTF(12, "GDActivityMonitor  ERROR Activity - " + activity.getLocalClassName() + " - Doesn't (1) call GDAndroid.ActivityInit( ) or (2) extend a GDActivity class or (3) use the GDMonitorActivity Manifest flag. Therefore GD can not track this Activity so calling finish( ) on Activity to avoid security risk\n");
                if (activity.isFinishing()) {
                    return;
                }
                activity.finish();
                return;
            }
            GDAndroid.getInstance().activityInit(activity);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor  Activity " + activity.getLocalClassName() + " onActivitySaveInstanceState\n");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor  Activity " + activity.getLocalClassName() + " onActivityStarted\n");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        GDLog.DBGPRINTF(16, "GDActivityMonitor  Activity " + activity.getLocalClassName() + " onActivityStopped\n");
    }
}
