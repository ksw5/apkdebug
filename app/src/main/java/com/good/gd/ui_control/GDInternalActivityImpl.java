package com.good.gd.ui_control;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.ViewGroup;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDDLPControl;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.MDMBlockedUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.dialogs.GDDialogState;
import com.good.gd.utils.GDInit;
import com.good.gd.utils.INIT_STATE;

/* loaded from: classes.dex */
public abstract class GDInternalActivityImpl extends GDBaseInternalActivity {
    private static boolean _screenPowerCycle = false;
    private GDDisplayStateBroadcastReceiver mDisplayStateBroadcastReceiver = null;
    private GDView _currentView = null;
    private BBUIType _currentType = null;
    private Dialog _dialog = null;
    private volatile boolean _dialogIsCanceledOnTouchOutside = false;
    private UniversalActivityController _uac = null;
    private Bundle _savedInstanceState = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class GDDisplayStateBroadcastReceiver extends BroadcastReceiver {
        GDDisplayStateBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
                GDLog.DBGPRINTF(16, "GDDisplayStateBroadcastReceiver: onReceive() action = " + action + "\n");
                GDInternalActivityImpl.this.clearSensitiveDataIfNeeded();
                boolean unused = GDInternalActivityImpl._screenPowerCycle = true;
            }
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {

        /* renamed from: com.good.gd.ui_control.GDInternalActivityImpl$hbfhc$hbfhc  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class DialogInterface$OnDismissListenerC0030hbfhc implements DialogInterface.OnDismissListener {
            final /* synthetic */ GDDialogState dbjc;

            DialogInterface$OnDismissListenerC0030hbfhc(GDDialogState gDDialogState) {
                this.dbjc = gDDialogState;
            }

            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                GDLog.DBGPRINTF(16, "GDDialogState: onDismiss\n");
                if (GDDialogState.getInstance().isCurrentDialogCritical()) {
                    GDDialogState.getInstance().cancelCriticalDialog();
                }
                this.dbjc.getOrientationLocker().unlock(GDInternalActivityImpl.this);
                if (dialogInterface == GDInternalActivityImpl.this._dialog) {
                    GDInternalActivityImpl.this._dialog = null;
                }
            }
        }

        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z = GDInternalActivityImpl.this._dialog != null && GDInternalActivityImpl.this._dialog.isShowing();
            GDDialogState gDDialogState = GDDialogState.getInstance();
            GDInternalActivityImpl.this._dialogIsCanceledOnTouchOutside = gDDialogState.isCanceledOnTouchOutside();
            if (z) {
                return;
            }
            Dialog pendingDialog = gDDialogState.getPendingDialog(GDInternalActivityImpl.this);
            if (pendingDialog != null) {
                GDInternalActivityImpl.this._dialog = pendingDialog;
                GDInternalActivityImpl.this._dialog.setOnDismissListener(new DialogInterface$OnDismissListenerC0030hbfhc(gDDialogState));
                GDInternalActivityImpl.this._dialog.show();
                return;
            }
            GDInternalActivityImpl.this._dialogIsCanceledOnTouchOutside = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearSensitiveDataIfNeeded() {
        GDView gDView = this._currentView;
        if (gDView == null || !gDView.hasTextContainers()) {
            return;
        }
        this._currentView.clearSensitiveData();
    }

    private void hideTitleBar() {
        requestWindowFeature(1);
    }

    private boolean isMdmBlockedView(BBDUIObject bBDUIObject) {
        return bBDUIObject instanceof MDMBlockedUI;
    }

    private void registerDisplayStateBroadcastReceiver() {
        if (this.mDisplayStateBroadcastReceiver != null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        GDDisplayStateBroadcastReceiver gDDisplayStateBroadcastReceiver = new GDDisplayStateBroadcastReceiver();
        this.mDisplayStateBroadcastReceiver = gDDisplayStateBroadcastReceiver;
        registerReceiver(gDDisplayStateBroadcastReceiver, intentFilter);
    }

    private void unregisterDisplayStateBroadcastReceiver() {
        GDDisplayStateBroadcastReceiver gDDisplayStateBroadcastReceiver = this.mDisplayStateBroadcastReceiver;
        if (gDDisplayStateBroadcastReceiver != null) {
            unregisterReceiver(gDDisplayStateBroadcastReceiver);
            this.mDisplayStateBroadcastReceiver = null;
        }
    }

    public synchronized void cancelDialog() {
        Dialog dialog = this._dialog;
        if (dialog != null) {
            dialog.cancel();
            this._dialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void checkDialogState() {
        runOnUiThread(new hbfhc());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void closeViewAndActivity() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: closeViewAndActivity() IN\n");
        cancelDialog();
        if (!isFinishing()) {
            finish();
        }
        GDLog.DBGPRINTF(16, "GDInternalActivity: closeViewAndActivity() OUT\n");
    }

    @Override // android.app.Activity
    public synchronized void finish() {
        GDLog.DBGPRINTF(16, "GDInternalActivity finish(" + this + ") IN\n");
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView.activityFinished();
        }
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.finish();
        GDLog.DBGPRINTF(16, "GDInternalActivity finish() OUT\n");
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        GDLog.DBGPRINTF(14, "GDInternalActivity onActivityResult()\n");
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView.onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Activity
    public synchronized void onBackPressed() {
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView.onBackPressed();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onCreate(" + this + ")\n");
        super.onCreate(bundle);
        CutOutUiSupportManager cutOutUiSupportManager = CutOutUiSupportManager.getInstance();
        if (cutOutUiSupportManager.checkForCutOut(this)) {
            cutOutUiSupportManager.applyCutOutUiChanges(this);
        }
        hideTitleBar();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        if (GDInit.ensureGDInitialized(this, INIT_STATE.STATE_FOREGROUND)) {
            GDLog.DBGPRINTF(14, "GDInternalActivity: onCreate( ) GD explictly initialized now call authorize( )\n");
            GDClient.getInstance().init(INIT_STATE.STATE_FOREGROUND);
            GDClient.getInstance().authorize(null, false);
        } else if (UniversalActivityController.getInstance().getNumberAppActivities() == 0 && !UniversalActivityController.getInstance().isViewWaitingOrOpen(bundle)) {
            GDLog.DBGPRINTF(14, "GDInternalActivity: onCreate( ) GD internal activity called without app activity( )\n");
            GDContext.getInstance().setContext(getApplicationContext());
            GDClient.getInstance().init(INIT_STATE.STATE_FOREGROUND);
            GDClient.getInstance().authorize(null, false);
        }
        UniversalActivityController universalActivityController = UniversalActivityController.getInstance();
        this._uac = universalActivityController;
        universalActivityController.internalActivityCreated(this);
        this._savedInstanceState = bundle;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onDestroy() IN\n");
        cancelDialog();
        if (this._currentView != null) {
            GDLog.DBGPRINTF(16, "GDInternalActivity destroyed while there is an active view.\n");
            this._currentView._delegate.onActivityDestroySafe();
        }
        this._uac.internalActivityDestroyed(this);
        super.onDestroy();
        GDLog.DBGPRINTF(16, "GDInternalActivity: onDestroy() OUT\n");
    }

    @Override // android.app.Activity
    protected void onPause() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onPause() IN\n");
        super.onPause();
        if (this._dialog != null) {
            GDDialogState.getInstance().setCurrentDialogPending();
            GDDialogState.getInstance().setCanceledOnTouchOutside(this._dialogIsCanceledOnTouchOutside);
            this._dialog.dismiss();
            this._dialog = null;
        }
        CutOutUiSupportManager.getInstance().unsubscribe();
        if (!((PowerManager) getSystemService("power")).isScreenOn()) {
            clearSensitiveDataIfNeeded();
        }
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView._delegate.onActivityPauseSafe();
        }
        this._uac.internalActivityPaused();
        GDLog.DBGPRINTF(16, "GDInternalActivity: onPause() OUT\n");
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        GDLog.DBGPRINTF(14, "GDInternalActivity onRequestPermissionsResult() \n");
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView.onPermissions(i, strArr, iArr);
        }
    }

    @Override // android.app.Activity
    public void onResume() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onResume() IN\n");
        GDDLPControl.getInstance().getScreenCaptureControl().updateScreenCaptureStatus(this);
        this._uac.internalActivityResumed(this);
        super.onResume();
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView._delegate.onActivityResumeSafe();
        }
        this._uac.updateInternalActivity();
        GDLog.DBGPRINTF(16, "GDInternalActivity: onResume() OUT\n");
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onSaveInstanceState IN\n");
        super.onSaveInstanceState(bundle);
        this._savedInstanceState = bundle;
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView._delegate.onSaveInstanceState(bundle);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            clearSensitiveDataIfNeeded();
        }
        bundle.putBoolean(UniversalActivityController.LOCATION_PERMISSION_STATE, checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0);
        GDLog.DBGPRINTF(16, "GDInternalActivity: onSaveInstanceState OUT\n");
    }

    @Override // android.app.Activity
    protected void onStart() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onStart() IN\n");
        super.onStart();
        registerDisplayStateBroadcastReceiver();
        this._uac.internalActivityStarted(this);
        GDLog.DBGPRINTF(16, "GDInternalActivity: onStart() OUT\n");
    }

    @Override // android.app.Activity
    public void onStop() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onStop() IN\n");
        unregisterDisplayStateBroadcastReceiver();
        super.onStop();
        if (Build.VERSION.SDK_INT < 28) {
            clearSensitiveDataIfNeeded();
        }
        this._uac.internalActivityStopped(this);
        GDLog.DBGPRINTF(16, "GDInternalActivity: onStop() OUT\n");
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        this._uac.onUserActivity(this);
        super.onUserInteraction();
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        GDLog.DBGPRINTF(16, "GDInternalActivity: onUserLeaveHint() IN\n");
        super.onUserLeaveHint();
        clearSensitiveDataIfNeeded();
        GDLog.DBGPRINTF(16, "GDInternalActivity: onUserLeaveHint() OUT\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void openView(BaseUI baseUI, boolean z) {
        BBUIType bBDUIType = baseUI.getBBDUIType();
        GDLog.DBGPRINTF(14, "GDInternalActivity openView view = " + bBDUIType + "\n");
        if (!z && !isMdmBlockedView(baseUI) && this._currentView != null && this._currentType == bBDUIType) {
            GDLog.DBGPRINTF(16, "GDInternalActivity:openView() GDView from class " + this._currentView.getClass().getName() + " is already opened.\n");
            return;
        }
        if (GDDialogState.getInstance().canCancelPendingDialog()) {
            cancelDialog();
        }
        GDView mo294createView = baseUI.mo294createView(this, this._uac, new GDViewCustomizer());
        if (mo294createView != null) {
            GDView gDView = this._currentView;
            if (gDView != null && gDView.getParent() != null) {
                ((ViewGroup) this._currentView.getParent()).removeView(this._currentView);
            }
            this._currentView = mo294createView;
            this._currentType = baseUI.getBBDUIType();
            setContentView(this._currentView);
            this._currentView._delegate.onActivityStartSafe();
            if (_screenPowerCycle) {
                this._savedInstanceState = null;
                _screenPowerCycle = false;
            }
            this._currentView._delegate.onActivityCreateSafe(this._savedInstanceState);
            this._currentView._delegate.onActivityResumeSafe();
            this._savedInstanceState = null;
            GDLog.DBGPRINTF(16, "GDInternalActivity openView OUT\n");
            return;
        }
        throw new RuntimeException("FAILED to create GDView object for " + baseUI.getBBDUIType());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void stateWasUpdated() {
        GDView gDView = this._currentView;
        if (gDView != null) {
            gDView.stateWasUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void update(BBDUIUpdateEvent bBDUIUpdateEvent) {
        if (bBDUIUpdateEvent.getType() != UIEventType.UI_KEYBOARD_VISIBILITY || this._currentView == null) {
            return;
        }
        if (bBDUIUpdateEvent.isSuccessful()) {
            this._currentView.requestShowKeyboard();
        } else {
            this._currentView.requestHideKeyboard();
        }
    }
}
