package com.good.gd.ndkproxy;

import android.app.Activity;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.utils.GDActivityUtils;
import com.good.gd.utils.GDDisplayUtils;

/* loaded from: classes.dex */
public class GDDLPScreenCaptureControl {
    private boolean alertRequested = false;
    private boolean screenCaptureDisabled;

    private void checkForNonSecureDisplayOutput(Activity activity) {
        if (this.screenCaptureDisabled && !GDDisplayUtils.isSecureDisplayOutput(activity)) {
            processNonSecureDisplayCase();
        } else {
            processSecureDisplayCase();
        }
    }

    private static native boolean dontShowAgain();

    private void processNonSecureDisplayCase() {
        if (!this.alertRequested) {
            if (!dontShowAgain()) {
                CoreUI.requestScreenCaptureAlertUI();
            }
            this.alertRequested = true;
        }
    }

    private void processSecureDisplayCase() {
        if (this.alertRequested) {
            CoreUI.closeScreenCaptureAlertUI();
            this.alertRequested = false;
        }
    }

    public synchronized boolean isScreenCaptureDisabled() {
        return this.screenCaptureDisabled;
    }

    public synchronized void setScreenCaptureStatus(boolean z) {
        this.screenCaptureDisabled = z;
    }

    public void updateScreenCaptureStatus(Activity activity) {
        GDActivityUtils.configureActivityScreenCapture(activity, this.screenCaptureDisabled);
        checkForNonSecureDisplayOutput(activity);
    }
}
