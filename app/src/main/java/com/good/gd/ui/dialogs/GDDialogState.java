package com.good.gd.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.resources.R;
import com.good.gd.ui.ViewInteractor;

/* loaded from: classes.dex */
public class GDDialogState {
    private static final boolean DEFAULT_CANCEL_ON_TOUCH_OUTSIDE_VALUE = false;
    private static GDDialogState _instance;
    private static NoActionClickListener noActionClickListener = new NoActionClickListener();
    private boolean _dialogPending = false;
    private boolean _isCriticalDialog = false;
    private volatile boolean _canceledOnTouchOutside = false;
    private String _title = null;
    private String _content = null;
    private String _positiveButtonTitle = null;
    private DialogInterface.OnClickListener _positiveClickListener = null;
    private String _negativeButtonTitle = null;
    private DialogInterface.OnClickListener _negativeClickListener = null;
    private String _neutralButtonTitle = null;
    private DialogInterface.OnClickListener _neutralClickListener = null;
    private boolean _spinner = false;
    private Dialog _customDialog = null;
    private final OrientationLocker orientationLocker = new OrientationLocker();

    /* loaded from: classes.dex */
    public class OrientationLocker {
        public OrientationLocker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void lock(Activity activity) {
            if (activity.getResources().getConfiguration().orientation == 2) {
                activity.setRequestedOrientation(6);
            } else {
                activity.setRequestedOrientation(7);
            }
        }

        public void unlock(Activity activity) {
            if (!GDDialogState.this._dialogPending) {
                activity.setRequestedOrientation(-1);
            }
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements DialogInterface.OnShowListener {
        final /* synthetic */ Activity dbjc;

        fdyxd(Activity activity) {
            this.dbjc = activity;
        }

        @Override // android.content.DialogInterface.OnShowListener
        public void onShow(DialogInterface dialogInterface) {
            GDLog.DBGPRINTF(16, "GDDialogState: onShow\n");
            GDDialogState.this.orientationLocker.lock(this.dbjc);
            AlertDialog alertDialog = (AlertDialog) dialogInterface;
            GDDialogState.this.setButtonColor(alertDialog.getButton(-2));
            GDDialogState.this.setButtonColor(alertDialog.getButton(-3));
            GDDialogState.this.setButtonColor(alertDialog.getButton(-1));
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements DialogInterface.OnCancelListener {
        hbfhc() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            GDDialogState.this._negativeClickListener.onClick(dialogInterface, -2);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements DialogInterface.OnCancelListener {
        final /* synthetic */ Activity dbjc;
        final /* synthetic */ DialogInterface.OnCancelListener qkduk;

        yfdke(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
            this.dbjc = activity;
            this.qkduk = onCancelListener;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            GDLog.DBGPRINTF(16, "GDDialogState: onCancel\n");
            GDDialogState.this.orientationLocker.unlock(this.dbjc);
            DialogInterface.OnCancelListener onCancelListener = this.qkduk;
            if (onCancelListener != null) {
                onCancelListener.onCancel(dialogInterface);
            }
        }
    }

    public static synchronized GDDialogState getInstance() {
        GDDialogState gDDialogState;
        synchronized (GDDialogState.class) {
            if (_instance == null) {
                _instance = new GDDialogState();
            }
            gDDialogState = _instance;
        }
        return gDDialogState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setButtonColor(Button button) {
        if (button == null) {
            return;
        }
        button.setTextColor(button.getContext().getColor(R.color.bbd_blue));
    }

    public boolean canCancelPendingDialog() {
        return !this._isCriticalDialog;
    }

    public void cancelCriticalDialog() {
        this._isCriticalDialog = false;
        this._dialogPending = false;
    }

    public boolean cancelPendingDialog() {
        boolean canCancelPendingDialog = canCancelPendingDialog();
        if (canCancelPendingDialog) {
            this._dialogPending = false;
        }
        return canCancelPendingDialog;
    }

    public OrientationLocker getOrientationLocker() {
        return this.orientationLocker;
    }

    public synchronized Dialog getPendingDialog(Activity activity) {
        hbfhc hbfhcVar = null;
        AlertDialog alertDialog = null;
        hbfhcVar = null;
        if (!this._dialogPending) {
            return null;
        }
        if (!this._isCriticalDialog) {
            this._dialogPending = false;
        }
        Dialog dialog = this._customDialog;
        if (dialog != null) {
            return dialog;
        }
        if (this._title != null || this._content != null || this._positiveButtonTitle != null || this._positiveClickListener != null) {
            AlertDialog.Builder message = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.GDAlertDialogStyle)).setTitle(this._title).setMessage(this._content);
            String str = this._negativeButtonTitle;
            if (str != null) {
                message.setNegativeButton(str, this._negativeClickListener);
            }
            String str2 = this._positiveButtonTitle;
            if (str2 != null) {
                message.setPositiveButton(str2, this._positiveClickListener);
            }
            String str3 = this._neutralButtonTitle;
            if (str3 != null) {
                message.setNeutralButton(str3, this._neutralClickListener);
            }
            if (this._spinner) {
                message.setView(R.layout.bbd_dialog_waiting_view);
            }
            AlertDialog create = message.create();
            if (this._negativeButtonTitle == null && this._negativeClickListener != null) {
                hbfhcVar = new hbfhc();
            }
            create.setOnCancelListener(new yfdke(activity, hbfhcVar));
            alertDialog = create;
        }
        alertDialog.setCanceledOnTouchOutside(this._canceledOnTouchOutside);
        this._canceledOnTouchOutside = false;
        alertDialog.setOnShowListener(new fdyxd(activity));
        return alertDialog;
    }

    public boolean isCanceledOnTouchOutside() {
        return this._canceledOnTouchOutside;
    }

    public boolean isCurrentDialogCritical() {
        return this._isCriticalDialog;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        this._canceledOnTouchOutside = z;
    }

    public synchronized void setCurrentDialogCritical() {
        this._isCriticalDialog = true;
    }

    public synchronized void setCurrentDialogPending() {
        this._dialogPending = true;
    }

    public void showCustomDialog(Dialog dialog, ViewInteractor viewInteractor) {
        this._customDialog = dialog;
        this._dialogPending = true;
        viewInteractor.showDialog();
    }

    public void showPopupDialog(ViewInteractor viewInteractor, String str, String str2, String str3, DialogInterface.OnClickListener onClickListener) {
        synchronized (this) {
            this._title = str;
            this._content = str2;
            this._spinner = false;
            this._positiveButtonTitle = str3;
            if (onClickListener == null) {
                onClickListener = noActionClickListener;
            }
            this._positiveClickListener = onClickListener;
            this._negativeButtonTitle = null;
            this._neutralButtonTitle = null;
            NoActionClickListener noActionClickListener2 = noActionClickListener;
            this._negativeClickListener = noActionClickListener2;
            this._neutralClickListener = noActionClickListener2;
            this._isCriticalDialog = false;
            this._dialogPending = true;
            this._customDialog = null;
        }
        viewInteractor.showDialog();
    }

    public void showPopupDialog2(ViewInteractor viewInteractor, String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        synchronized (this) {
            this._title = str;
            this._content = str2;
            this._negativeButtonTitle = str3;
            if (onClickListener == null) {
                onClickListener = noActionClickListener;
            }
            this._negativeClickListener = onClickListener;
            this._positiveButtonTitle = str4;
            if (onClickListener2 == null) {
                onClickListener2 = noActionClickListener;
            }
            this._positiveClickListener = onClickListener2;
            this._spinner = false;
            this._neutralButtonTitle = null;
            this._neutralClickListener = noActionClickListener;
            this._isCriticalDialog = false;
            this._dialogPending = true;
            this._customDialog = null;
        }
        viewInteractor.showDialog();
    }

    public void showPopupDialog4(ViewInteractor viewInteractor, String str, String str2, boolean z) {
        this._title = str;
        this._content = str2;
        this._spinner = z;
        this._isCriticalDialog = false;
        this._dialogPending = true;
        this._customDialog = null;
        viewInteractor.showDialog();
    }
}
