package com.good.gd.ndkproxy;

import android.content.Context;
import android.os.Build;
import android.view.autofill.AutofillManager;
import com.good.gd.content_Impl.GDClipboardManagerImpl;
import com.good.gd.context.GDContext;
import com.good.gd.ui_control.UniversalActivityController;
import com.good.gd.widget.GDDLPKeyboardControl;

/* loaded from: classes.dex */
public class GDDLPControl {
    private static GDDLPControl _instance;
    private GDClipboardManagerImpl gdClipboardManager;
    private boolean mpreventPasteFromNonGDApps;
    private GDDLPScreenCaptureControl screenCaptureControl;

    /* loaded from: classes.dex */
    private static class PreventDataLeakageStatus {
        private boolean preventDataLeakage = false;
        private boolean preventPasteFromNonGDApps = false;
        private boolean androidDictationOn = false;
        private boolean preventScreenCapture = false;
        private boolean preventPasteFromGDApps = false;
        private boolean isOldDLPFormat = false;
        private boolean isIncognitoModeOn = false;

        private PreventDataLeakageStatus() {
        }

        boolean dbjc() {
            return this.preventPasteFromGDApps && this.preventPasteFromNonGDApps;
        }

        boolean jwxax() {
            return this.preventDataLeakage || this.preventPasteFromNonGDApps;
        }

        boolean qkduk() {
            if (this.isOldDLPFormat) {
                return this.preventDataLeakage;
            }
            return this.preventScreenCapture;
        }
    }

    private GDDLPControl() {
        this.screenCaptureControl = null;
        GDDLPScreenCaptureControl gDDLPScreenCaptureControl = new GDDLPScreenCaptureControl();
        this.screenCaptureControl = gDDLPScreenCaptureControl;
        gDDLPScreenCaptureControl.setScreenCaptureStatus(true);
        ndkInit();
    }

    public static synchronized GDDLPControl getInstance() {
        GDDLPControl gDDLPControl;
        synchronized (GDDLPControl.class) {
            if (_instance == null) {
                _instance = new GDDLPControl();
            }
            gDDLPControl = _instance;
        }
        return gDDLPControl;
    }

    private static Class getPreventDataLeakageStatusClass() {
        return PreventDataLeakageStatus.class;
    }

    private void logAutoFillInfo() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext != null && applicationContext.getApplicationInfo().targetSdkVersion >= 26) {
            AutofillManager autofillManager = (AutofillManager) applicationContext.getSystemService(AutofillManager.class);
            GDLog.DBGPRINTF(14, "GDDLPControl: logAutoFillInfo - provides service: " + autofillManager.hasEnabledAutofillServices() + ", autofill supported: " + autofillManager.isAutofillSupported() + ", autofill enabled: " + autofillManager.isEnabled() + "\n");
            return;
        }
        GDLog.DBGPRINTF(13, "GDDLPControl: logAutoFillInfo context is null\n");
    }

    private native void ndkInit();

    private native void retrieveSettingsFromPersisted();

    private synchronized void updatePreventDataLeakageStatus(PreventDataLeakageStatus preventDataLeakageStatus) {
        this.screenCaptureControl.setScreenCaptureStatus(preventDataLeakageStatus.qkduk());
        this.mpreventPasteFromNonGDApps = preventDataLeakageStatus.preventPasteFromNonGDApps;
        UniversalActivityController.getInstance().updateScreenCaptureStatus(this.screenCaptureControl);
        this.gdClipboardManager.setSecureClipboardEnabled(preventDataLeakageStatus.jwxax(), preventDataLeakageStatus.dbjc(), preventDataLeakageStatus.preventPasteFromGDApps, preventDataLeakageStatus.isOldDLPFormat);
        GDDLPKeyboardControl.DLPSettings dLPSettings = new GDDLPKeyboardControl.DLPSettings();
        dLPSettings.isKeyboardDLPOn = preventDataLeakageStatus.androidDictationOn;
        dLPSettings.isIncognitoModeOn = preventDataLeakageStatus.isIncognitoModeOn;
        GDDLPKeyboardControl.getInstance().updateDLPKeyboardControl(dLPSettings);
    }

    public GDDLPScreenCaptureControl getScreenCaptureControl() {
        return this.screenCaptureControl;
    }

    public void initializeInstance() {
        this.gdClipboardManager = GDClipboardManagerImpl.getInstance();
        GDLog.DBGPRINTF(16, "GDDLPControl: initializeInstance\n");
        if (Build.VERSION.SDK_INT >= 26) {
            logAutoFillInfo();
        }
        retrieveSettingsFromPersisted();
    }

    public synchronized boolean preventPasteFromNonGDApps() {
        return this.mpreventPasteFromNonGDApps;
    }
}
