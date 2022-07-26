package com.good.gd.ui_control;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.LogUtils;

/* loaded from: classes.dex */
public class ActivityStarter {
    public Context context;
    public Intent launchIntent;
    public PendingIntent pendingIntent;

    public ActivityStarter() {
        this.launchIntent = null;
        this.context = null;
        this.pendingIntent = null;
    }

    private void startViaPendingIntent(PendingIntent pendingIntent, Context context, Intent intent) {
        try {
            pendingIntent.send(context, 100, intent);
        } catch (PendingIntent.CanceledException e) {
            GDLog.DBGPRINTF(12, "Got an exception while trying to send pending intent\n");
            LogUtils.logStackTrace(e);
        }
    }

    public void startActivity() {
        if (this.launchIntent != null && this.context != null) {
            if (this.pendingIntent != null) {
                GDLog.DBGPRINTF(16, "ActivityStarter: using PI\n");
                startViaPendingIntent(this.pendingIntent, this.context, this.launchIntent);
                return;
            }
            GDLog.DBGPRINTF(16, "ActivityStarter: using Context\n");
            this.context.startActivity(this.launchIntent);
            return;
        }
        GDLog.DBGPRINTF(12, "ActivityStarter: incorrect params\n");
    }

    public ActivityStarter(Intent intent, Context context, PendingIntent pendingIntent) {
        this.launchIntent = intent;
        this.context = context;
        this.pendingIntent = pendingIntent;
    }
}
