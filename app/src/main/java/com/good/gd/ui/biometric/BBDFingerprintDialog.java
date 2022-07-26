package com.good.gd.ui.biometric;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import com.good.gd.utils.GDLocalizer;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class BBDFingerprintDialog {
    private BBDDialog bbdDialog;
    private WeakReference<Context> contextWeakReference;
    private DialogArgs defaultArgs;
    private Handler handler = new Handler();

    /* loaded from: classes.dex */
    class aqdzk implements Runnable {
        aqdzk() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.dismiss();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements Runnable {
        final /* synthetic */ CharSequence dbjc;

        ehnkx(CharSequence charSequence) {
            this.dbjc = charSequence;
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.showErrorStatus(this.dbjc.toString());
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements Runnable {
        fdyxd() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.showListening();
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        hbfhc() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.create(BBDFingerprintDialog.this.defaultArgs);
            BBDFingerprintDialog.this.bbdDialog.show();
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements Runnable {
        final /* synthetic */ String dbjc;

        mjbm(String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.showAuthenticationSuccess(this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements Runnable {
        pmoiy() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.showListening();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDFingerprintDialog.this.bbdDialog.showErrorStatus(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_NOT_ENROLLED_ONE));
        }
    }

    public BBDFingerprintDialog(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
        BBDDialog bBDDialog = new BBDDialog();
        this.bbdDialog = bBDDialog;
        bBDDialog.setActivity((Activity) context);
    }

    private Context getLocalContextOrNull() {
        WeakReference<Context> weakReference = this.contextWeakReference;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public void cancel() {
        this.handler.removeCallbacksAndMessages(null);
        this.bbdDialog.cancel();
    }

    public void dismiss() {
        this.handler.removeCallbacksAndMessages(null);
        this.bbdDialog.dismiss();
    }

    public void onAuthenticationFailed() {
        BBDDialog bBDDialog = this.bbdDialog;
        if (bBDDialog == null || bBDDialog.isShowing()) {
            this.handler.post(new yfdke());
            this.handler.postDelayed(new fdyxd(), GDAbstractBiometricHelper.ERROR_DELAY);
        }
    }

    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        BBDDialog bBDDialog = this.bbdDialog;
        if (bBDDialog == null || bBDDialog.isShowing()) {
            this.handler.post(new ehnkx(charSequence));
            this.handler.postDelayed(new pmoiy(), GDAbstractBiometricHelper.ERROR_DELAY);
        }
    }

    public void setArgs(DialogArgs dialogArgs) {
        this.defaultArgs = dialogArgs;
    }

    public void showFingerprintDialog(View.OnClickListener onClickListener) {
        BBDDialog bBDDialog = this.bbdDialog;
        if (bBDDialog == null || !bBDDialog.isShowing()) {
            if (this.defaultArgs == null) {
                this.defaultArgs = BBDDialogFactory.createBasicActivationArgs();
            }
            this.defaultArgs.setNegativeButtonCallback(onClickListener);
            this.handler.post(new hbfhc());
        }
    }

    public void showSuccess(String str) {
        BBDDialog bBDDialog = this.bbdDialog;
        if (bBDDialog == null || bBDDialog.isShowing()) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler.post(new mjbm(str));
            this.handler.postDelayed(new aqdzk(), 1000L);
        }
    }
}
