package com.good.gd.ui.biometric;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.resources.R;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class BBDDialog {
    private WeakReference<Activity> activityWeakReference;
    private BBDDialogView bbdDialogView;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public void cancel() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.cancel();
            this.dialog = null;
        }
    }

    public void create(DialogArgs dialogArgs) {
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference != null) {
            create(weakReference.get(), dialogArgs);
        }
    }

    public void dismiss() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            Context context = alertDialog.getContext();
            if (context instanceof ContextThemeWrapper) {
                Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
                if (baseContext instanceof Activity) {
                    Activity activity = (Activity) baseContext;
                    if (!activity.isFinishing() && !activity.isDestroyed()) {
                        this.dialog.dismiss();
                    } else {
                        GDLog.DBGPRINTF(13, "can't dismiss dialog: Illegal state\n");
                    }
                } else {
                    GDLog.DBGPRINTF(13, "can't dismiss dialog. Base context: " + baseContext + "\n");
                }
            } else {
                GDLog.DBGPRINTF(13, "can't dismiss dialog. Dialog context " + context + "\n");
            }
            this.dialog = null;
            return;
        }
        GDLog.DBGPRINTF(16, "dismiss null dialog\n");
    }

    public Context getContext() {
        WeakReference<Activity> weakReference = this.activityWeakReference;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public boolean isShowing() {
        AlertDialog alertDialog = this.dialog;
        return alertDialog != null && alertDialog.isShowing();
    }

    public void setActivity(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    public void show() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog == null || alertDialog.isShowing()) {
            return;
        }
        this.dialog.show();
    }

    public void showAuthenticationSuccess(String str) {
        this.bbdDialogView.showAuthenticationSuccess(str);
    }

    public void showError(String str, String str2) {
        this.bbdDialogView.showError(str, str2);
    }

    public void showErrorStatus(String str) {
        this.bbdDialogView.showErrorStatus(str);
    }

    public void showListening() {
        this.bbdDialogView.showListening();
    }

    public void updateWithArgs(DialogArgs dialogArgs) {
        this.bbdDialogView.updateViewForArgs(dialogArgs);
    }

    public void create(Activity activity, DialogArgs dialogArgs) {
        if (activity == null || dialogArgs == null || activity.isFinishing()) {
            return;
        }
        BBDDialogView bBDDialogView = new BBDDialogView(activity);
        this.bbdDialogView = bBDDialogView;
        bBDDialogView.initView(dialogArgs);
        View dialogView = this.bbdDialogView.getDialogView();
        this.builder = new AlertDialog.Builder(activity, R.style.GDAlertDialogStyle).setView(dialogView).setCancelable(false);
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.dialog.setContentView(dialogView);
            this.dialog.setCancelable(false);
            return;
        }
        this.dialog = this.builder.create();
    }
}
