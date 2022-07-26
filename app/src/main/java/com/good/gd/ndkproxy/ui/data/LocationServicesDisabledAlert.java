package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.resources.R;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class LocationServicesDisabledAlert extends DialogUI {
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
            BBDUIHelper.ok(LocationServicesDisabledAlert.this.mHandle);
            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            if (intent.resolveActivity(LocationServicesDisabledAlert.this.mActivity.getPackageManager()) != null) {
                LocationServicesDisabledAlert.this.mActivity.startActivity(intent);
            }
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements DialogInterface.OnClickListener {
        yfdke() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            BBDUIHelper.cancel(LocationServicesDisabledAlert.this.mHandle);
        }
    }

    public LocationServicesDisabledAlert(long j, String str, String str2) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_LOCATION_SERVICES_DISABLED_WARNING, j);
        this.mHandle = j;
        this.mLocalizedTitle = str;
        this.mLocalizedMessage = str2;
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
        message.setPositiveButton(GDAbstractBiometricHelper.BUTTON_SETTINGS, new hbfhc());
        message.setNegativeButton(GDLocalizer.getLocalizedString("_CloseButton"), new yfdke());
        AlertDialog create = message.create();
        this.mDialog = create;
        create.setCancelable(false);
        this.mDialog.show();
        return true;
    }
}
