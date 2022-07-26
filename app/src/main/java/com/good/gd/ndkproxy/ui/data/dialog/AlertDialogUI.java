package com.good.gd.ndkproxy.ui.data.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.native2javabridges.ui.AlertButtons;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import java.util.List;

/* loaded from: classes.dex */
public class AlertDialogUI extends DialogUI {
    private List<String> mAdditionalMessageKeys;
    private List<AlertButtons.AlertButton> mButtons;
    private Dialog mDialog = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements DialogInterface.OnClickListener {
        final /* synthetic */ AlertButtons.AlertButton dbjc;

        hbfhc(AlertDialogUI alertDialogUI, AlertButtons.AlertButton alertButton) {
            this.dbjc = alertButton;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            GDLog.DBGPRINTF(14, "AlertDialogUI btn clicked: " + i + "\n");
            BBDUIHelper.dialogAction(this.dbjc.getHandle());
        }
    }

    public AlertDialogUI(long j, boolean z, int i, AlertButtons alertButtons, AdditionalErrorMessageCreator additionalErrorMessageCreator) {
        super(BBUIType.UI_ALERT, j, z);
        setUserActionRequired(!z);
        this.mButtons = alertButtons.getButtons();
        this.mAdditionalMessageKeys = additionalErrorMessageCreator.additionalErrorMessageKeys(i);
    }

    private DialogInterface.OnClickListener getListener(AlertButtons.AlertButton alertButton) {
        return new hbfhc(this, alertButton);
    }

    private String getTitle(AlertButtons.AlertButton alertButton) {
        return GDLocalizer.getLocalizedString(alertButton.getTitleKey());
    }

    private void setButtons(AlertDialog.Builder builder) {
        int size = this.mButtons.size();
        GDLog.DBGPRINTF(16, "AlertDialogUI.setButtons: create dialog with " + size + " buttons \n");
        if (size > 0) {
            AlertButtons.AlertButton alertButton = this.mButtons.get(0);
            builder.setPositiveButton(getTitle(alertButton), getListener(alertButton));
        }
        if (size > 1) {
            AlertButtons.AlertButton alertButton2 = this.mButtons.get(1);
            builder.setNegativeButton(getTitle(alertButton2), getListener(alertButton2));
        }
        if (size > 2) {
            AlertButtons.AlertButton alertButton3 = this.mButtons.get(2);
            builder.setNeutralButton(getTitle(alertButton3), getListener(alertButton3));
        }
        if (size > 3) {
            GDLog.DBGPRINTF(13, "AlertDialogUI more than 3 buttons need to be shown\n");
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        this.mDialog.cancel();
    }

    @Override // com.good.gd.ndkproxy.ui.data.dialog.DialogUI
    public boolean showDialog(Activity activity) {
        StringBuilder sb = new StringBuilder(BBDUIHelper.getLocalizedCoreMessage(getCoreHandle()));
        for (String str : this.mAdditionalMessageKeys) {
            sb.append("\n").append(GDLocalizer.getLocalizedString(str));
        }
        AlertDialog.Builder message = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.GDAlertDialogStyle)).setTitle(BBDUIHelper.getLocalizedCoreTitle(getCoreHandle())).setMessage(sb);
        setButtons(message);
        AlertDialog create = message.create();
        this.mDialog = create;
        create.setCancelable(false);
        this.mDialog.show();
        return true;
    }
}
