package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.ContextThemeWrapper;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.resources.R;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class MTDInsecureWiFiWarningAlert extends DialogUI {
    private Activity mActivity;
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
            MTDInsecureWiFiWarningAlert.this.openNetworkSettings();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements DialogInterface.OnClickListener {
        yfdke() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            MTDInsecureWiFiWarningAlert.this.closeAlert();
        }
    }

    public MTDInsecureWiFiWarningAlert(long j, String str, String str2) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_PERMISSION_WARNING, j);
        this.mHandle = j;
        this.mLocalizedTitle = str;
        this.mLocalizedMessage = str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeAlert() {
        BBDUIHelper.cancel(this.mHandle);
    }

    private String getLocalizedCloseButtonText() {
        return GDLocalizer.getLocalizedString("Button Close");
    }

    private String getLocalizedNetworkSettingsButtonText() {
        if (Build.VERSION.SDK_INT >= 29) {
            return GDLocalizer.getLocalizedString("MTD Insecure WiFi: Permission Warning UI: Network Settings Button");
        }
        return GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.BUTTON_GO_TO_SETTINGS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openNetworkSettings() {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mActivity.startActivity(new Intent("android.settings.panel.action.WIFI"));
        } else {
            this.mActivity.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        }
        BBDUIHelper.ok(this.mHandle);
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
        message.setNegativeButton(getLocalizedCloseButtonText(), new yfdke());
        AlertDialog create = message.create();
        this.mDialog = create;
        create.setCancelable(false);
        this.mDialog.show();
        return true;
    }
}
