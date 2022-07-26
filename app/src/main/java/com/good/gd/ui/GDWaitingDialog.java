package com.good.gd.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import com.good.gd.resources.R;

/* loaded from: classes.dex */
public class GDWaitingDialog extends Dialog {
    public GDWaitingDialog(Context context, ViewCustomizer viewCustomizer) {
        super(context, R.style.GDWaitingDialog);
        ProgressBar progressBar;
        setContentView(R.layout.bbd_dialog_waiting_view);
        if (!viewCustomizer.getEmulatorChecker().isEmulator() || (progressBar = (ProgressBar) findViewById(R.id.gd_spinner)) == null) {
            return;
        }
        progressBar.setEnabled(false);
        progressBar.setVisibility(4);
    }
}
