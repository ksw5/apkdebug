package com.good.gd.ui.subcontainer_activationui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.GDInterDeviceContainerControl;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.ui_control.IntentAndContextForStart;
import com.good.gd.ui_control.WearableNotificationManager;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;
import com.good.gt.interdevice_icc.InterDeviceActivationControlInterface;
import com.good.gt.ndkproxy.util.GTLog;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class GTWearActivationUIControl {
    private static final String TAG = "GTWearActivationUIControl";
    private static GTWearActivationUIControl _instance;
    private String mRemoteDeviceAddress;
    private ActivationUIState mState = ActivationUIState.StateInitialSetupUI;
    private String mValidationCode;
    private WeakReference<GTWearActivationUIActivity> mWeakRefActivationActivity;

    /* loaded from: classes.dex */
    public enum ActivationUIState {
        StateInitialSetupUI,
        StateActivationInProgressUI,
        StateVerificationUI,
        StateActivationInProgressPostValidationUI,
        StateSetupCompleteUI,
        StateSetupCompleteError
    }

    private GTWearActivationUIControl() {
    }

    public static GTWearActivationUIControl createInstance() {
        if (_instance == null) {
            _instance = new GTWearActivationUIControl();
        }
        return _instance;
    }

    public static GTWearActivationUIControl getInstance() {
        return _instance;
    }

    private void startActivationActivity(Context context) {
        if (context != null && GDActivityStateManager.getInstance().inForeground()) {
            GDLog.DBGPRINTF(16, TAG, "startActivationActivity aActivityContext = " + context + "\n");
            Intent intent = new Intent(context, GTWearActivationUIActivity.class);
            intent.addFlags(537001984);
            context.startActivity(intent);
            return;
        }
        GDLog.DBGPRINTF(16, TAG, "startActivationActivity: is in background - showing notification\n");
        Context applicationContext = GTBaseContext.getInstance().getApplicationContext();
        Intent intent2 = new Intent(applicationContext, GTWearActivationUIActivity.class);
        intent2.addFlags(131072);
        WearableNotificationManager.showNotification(IntentAndContextForStart.from(intent2, applicationContext), WearableNotificationManager.NotificationType.ACTIVATION);
    }

    public void activationCompleted(boolean z, int i) {
        GDLog.DBGPRINTF(16, TAG, " activationCompleted Success =" + z + "\n");
        if (z) {
            setActivationUIState(ActivationUIState.StateSetupCompleteUI);
        } else {
            displayActivationError(i);
        }
    }

    public void clearActivationActivity(Activity activity) {
        GTLog.DBGPRINTF(16, TAG, "clearActivationActivity");
        if (this.mWeakRefActivationActivity == null || getActivationActivity() != activity) {
            return;
        }
        this.mWeakRefActivationActivity.clear();
        this.mWeakRefActivationActivity = null;
    }

    public void displayActivationError(int i) {
        setActivationUIState(ActivationUIState.StateSetupCompleteError);
        if (getActivationActivity() != null) {
            getActivationActivity().startActivationErrorUI(i);
        }
    }

    public void displayUserValidationCode(String str, Context context) {
        setActivationUIState(ActivationUIState.StateVerificationUI);
        if (getActivationActivity() != null) {
            getActivationActivity().startUserValidationUI(str);
            return;
        }
        this.mValidationCode = str;
        startActivationActivity(context);
    }

    public GTWearActivationUIActivity getActivationActivity() {
        WeakReference<GTWearActivationUIActivity> weakReference = this.mWeakRefActivationActivity;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public ActivationUIState getActivationUIState() {
        if (this.mState == ActivationUIState.StateVerificationUI) {
            getActivationActivity().startUserValidationUI(this.mValidationCode);
            this.mValidationCode = null;
        }
        return this.mState;
    }

    public String getRemoteAddress() {
        return this.mRemoteDeviceAddress;
    }

    public void promptStartActivation(String str) {
        GDLog.DBGPRINTF(16, TAG, " promptStartActivation\n");
        GDInterDeviceContainerControl.getInstance().promptUserStartSubContainerActivation(str, GDLocalizer.getLocalizedString("WF Setup Requested "));
    }

    public void setActivationActivity(GTWearActivationUIActivity gTWearActivationUIActivity) {
        GTLog.DBGPRINTF(16, TAG, "setActivationActivity");
        this.mWeakRefActivationActivity = new WeakReference<>(gTWearActivationUIActivity);
    }

    public void setActivationUIState(ActivationUIState activationUIState) {
        GTLog.DBGPRINTF(16, TAG, "setActivationUIState state = " + activationUIState);
        this.mState = activationUIState;
        if (getActivationActivity() != null) {
            getActivationActivity().setActivationUI(this.mState);
        }
    }

    public void setRemoteAddress(String str) {
        this.mRemoteDeviceAddress = str;
    }

    public void startActivationUI(String str, Context context) {
        setActivationUIState(ActivationUIState.StateInitialSetupUI);
        setRemoteAddress(str);
        startActivationActivity(context);
    }

    public void userValidationCodeResult(String str, InterDeviceActivationControlInterface.ValidationCodeResponse validationCodeResponse) {
        GDLog.DBGPRINTF(16, TAG, " userValidationCodeResult state = " + validationCodeResponse + "\n");
        GDInterDeviceContainerControl.getInstance().userValidationCodeResult(str, validationCodeResponse);
    }
}
