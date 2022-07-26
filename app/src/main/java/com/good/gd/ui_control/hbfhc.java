package com.good.gd.ui_control;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ui.GDInternalActivity;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class hbfhc {
    private static ActivityManager dbjc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void dbjc() {
        ActivityManager.RecentTaskInfo taskInfo;
        boolean z;
        ComponentName componentName;
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        Context applicationContext2 = GDContext.getInstance().getApplicationContext();
        if (dbjc == null) {
            dbjc = (ActivityManager) applicationContext2.getSystemService("activity");
        }
        for (ActivityManager.AppTask appTask : dbjc.getAppTasks()) {
            try {
                taskInfo = appTask.getTaskInfo();
                z = false;
            } catch (Exception e) {
                GDLog.DBGPRINTF(12, "GDActivityTaskManager.checkTask failed", e);
            }
            if (!(taskInfo.numActivities == 0)) {
                if (taskInfo.numActivities == 1 && (componentName = taskInfo.topActivity) != null && componentName.equals(new ComponentName(applicationContext, GDInternalActivity.class))) {
                    z = true;
                }
                if (z) {
                }
            }
            GDLog.DBGPRINTF(14, "GDActivityTaskManager::checkForEmptyTasks()empty task --" + appTask + "\n");
            appTask.finishAndRemoveTask();
        }
    }
}
