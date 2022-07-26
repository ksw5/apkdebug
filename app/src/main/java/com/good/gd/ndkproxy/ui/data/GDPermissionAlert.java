package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.resources.R;
import com.good.gd.ui.utils.permissions.ButtonClickListener;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDPermissionAlert extends DialogUI {
    private Activity mActivity;
    private ButtonClickListener mButtonClickListener;
    private Dialog mDialog = null;
    private long mHandle;
    private String mLocalizedMessage;
    private String mLocalizedTitle;

    /* loaded from: classes.dex */
    class hbfhc implements DialogInterface.OnClickListener {
        hbfhc() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (GDPermissionAlert.this.mButtonClickListener != null) {
                GDPermissionAlert.this.mButtonClickListener.onButtonClicked(GDPermissionAlert.this.mActivity, GDPermissionAlert.this.mHandle);
            }
        }
    }

    public GDPermissionAlert(long j, String str, String str2, ButtonClickListener buttonClickListener) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_PERMISSION_WARNING, j);
        this.mHandle = j;
        this.mLocalizedTitle = str;
        this.mLocalizedMessage = str2;
        this.mButtonClickListener = buttonClickListener;
    }

    private String getLocalizedNetworkSettingsButtonText() {
        return GDLocalizer.getLocalizedString("Continue");
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedMessage() {
        return this.mLocalizedMessage;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedTitle() {
        return this.mLocalizedTitle;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        this.mDialog.cancel();
    }

    @Override // com.good.gd.ndkproxy.ui.data.dialog.DialogUI
    public boolean showDialog(Activity activity) {
        this.mActivity = activity;
        AlertDialog.Builder message = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.GDAlertDialogStyle)).setTitle(getLocalizedTitle()).setMessage(getLocalizedMessage());
        message.setPositiveButton(getLocalizedNetworkSettingsButtonText(), new hbfhc());
        AlertDialog create = message.create();
        this.mDialog = create;
        create.setCancelable(false);
        this.mDialog.show();
        return true;
    }
}
