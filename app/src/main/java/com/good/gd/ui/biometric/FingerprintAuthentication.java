package com.good.gd.ui.biometric;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.android.BBDAndroidMAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.biometric.callback.ActivationCallbackAdapter;
import com.good.gd.ui.biometric.callback.CallbackAdapter;
import com.good.gd.ui.biometric.callback.UnlockCallbackAdapter;
import com.good.gd.utils.GDLocalizer;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class FingerprintAuthentication implements DialogInterface, GDFingerprintAuthenticator.Callback {
    private CallbackAdapter adapter;
    private GDFingerprintAuthenticator authenticator;
    private BBDFingerprintDialog fingerprintDialog;
    private BBDDialog internalDialog;
    private BBDUIObject uiData;

    /* loaded from: classes.dex */
    class aqdzk implements View.OnClickListener {
        aqdzk() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FingerprintAuthentication.this.dismissDialog();
            FingerprintAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class ehnkx implements View.OnClickListener {
        ehnkx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements View.OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class gioey implements View.OnClickListener {
        gioey() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements View.OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements View.OnClickListener {
        mjbm() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class orlrx implements View.OnClickListener {
        orlrx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FingerprintAuthentication.this.internalDialog.dismiss();
            FingerprintAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements View.OnClickListener {
        pmoiy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FingerprintAuthentication.this.dismissDialog();
            FingerprintAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class vzw implements View.OnClickListener {
        vzw() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(FingerprintAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements View.OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FingerprintAuthentication.this.dismissDialog();
            FingerprintAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class yjh implements View.OnClickListener {
        yjh() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FingerprintAuthentication.this.dismissDialog();
            FingerprintAuthentication.this.showFingerprintDialog();
        }
    }

    private void dismissAndCreateNewInternalDialog() {
        BBDDialog bBDDialog = this.internalDialog;
        if (bBDDialog != null) {
            bBDDialog.dismiss();
        }
        this.internalDialog = new BBDDialog();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void deregister() {
        CallbackAdapter callbackAdapter = this.adapter;
        if (callbackAdapter != null) {
            callbackAdapter.setCallback(null);
        }
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void dismissDialog() {
        this.internalDialog.dismiss();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void dismissFingerprintDialog() {
        GDFingerprintAuthenticator gDFingerprintAuthenticator = this.authenticator;
        if (gDFingerprintAuthenticator != null) {
            gDFingerprintAuthenticator.cancel();
        }
        BBDFingerprintDialog bBDFingerprintDialog = this.fingerprintDialog;
        if (bBDFingerprintDialog != null) {
            bBDFingerprintDialog.cancel();
        }
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void init(Activity activity, DialogArgs dialogArgs) {
        BBDFingerprintDialog bBDFingerprintDialog = this.fingerprintDialog;
        if (bBDFingerprintDialog != null) {
            bBDFingerprintDialog.dismiss();
        }
        BBDFingerprintDialog bBDFingerprintDialog2 = new BBDFingerprintDialog(activity);
        this.fingerprintDialog = bBDFingerprintDialog2;
        bBDFingerprintDialog2.setArgs(dialogArgs);
        BBDAndroidMAuthenticator bBDAndroidMAuthenticator = (BBDAndroidMAuthenticator) GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().getBBDAndroidAuthenticator();
        if (bBDAndroidMAuthenticator != null) {
            bBDAndroidMAuthenticator.initFingerprintManager(activity);
        }
        dismissAndCreateNewInternalDialog();
        this.internalDialog.setActivity(activity);
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public boolean isDialogShowing() {
        return this.internalDialog.isShowing();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        this.authenticator.cancel();
        this.fingerprintDialog.dismiss();
        if (i == 5 || i == 1 || i == -1) {
            return;
        }
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, charSequence.toString(), (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setNegativeButtonCallback(new ehnkx());
        dialogArgs.setPositiveButtonCallback(new pmoiy());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED), charSequence.toString());
        this.internalDialog.show();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationFailed() {
        this.fingerprintDialog.onAuthenticationFailed();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        this.fingerprintDialog.onAuthenticationHelp(i, charSequence);
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationOperationDenied(String str) {
        this.authenticator.cancel();
        this.fingerprintDialog.dismiss();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setNegativeButtonCallback(new hbfhc());
        dialogArgs.setPositiveButtonCallback(new yfdke());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED), GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void onAuthenticationSuccess() {
        this.authenticator.cancel();
        this.fingerprintDialog.showSuccess(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_AUTHENTICATED));
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationTimedOut() {
        this.authenticator.cancel();
        this.fingerprintDialog.dismiss();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_TIMED_OUT, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TIMED_OUT, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setNegativeButtonCallback(new vzw());
        dialogArgs.setPositiveButtonCallback(new yjh());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_TIMED_OUT), GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TIMED_OUT));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
        this.authenticator.cancel();
        String str = z ? GDAbstractBiometricHelper.DIALOG_TEXT_INTERNAL_ERROR_RETRY : GDAbstractBiometricHelper.DIALOG_TEXT_INTERNAL_ERROR;
        this.fingerprintDialog.dismiss();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_CRYPTO_FAILURE, str, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, null);
        dialogArgs.setNegativeButtonCallback(new mjbm());
        if (z) {
            dialogArgs.setPositiveButtonTextKey(GDAbstractBiometricHelper.BUTTON_RETRY);
            dialogArgs.setPositiveButtonCallback(new aqdzk());
        }
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_CRYPTO_FAILURE), GDLocalizer.getLocalizedString(str));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onSensorLockout() {
        this.authenticator.cancel();
        this.fingerprintDialog.dismiss();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setPositiveButtonCallback(new orlrx());
        dialogArgs.setNegativeButtonCallback(new gioey());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.show();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void registerForActivationCallback(GDFingerprintActivationAuthenticator.Callback callback) {
        ActivationCallbackAdapter activationCallbackAdapter = new ActivationCallbackAdapter();
        this.adapter = activationCallbackAdapter;
        activationCallbackAdapter.setCallback(this);
        ((ActivationCallbackAdapter) this.adapter).setActivationCallback(callback);
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void registerForUnlockCallback(GDFingerprintUnlockAuthenticator.Callback callback) {
        UnlockCallbackAdapter unlockCallbackAdapter = new UnlockCallbackAdapter();
        this.adapter = unlockCallbackAdapter;
        unlockCallbackAdapter.setCallback(this);
        ((UnlockCallbackAdapter) this.adapter).setUnlockCallback(callback);
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void setAuthenticator(GDFingerprintAuthenticator gDFingerprintAuthenticator) {
        this.authenticator = gDFingerprintAuthenticator;
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void setUiData(BBDUIObject bBDUIObject) {
        this.uiData = bBDUIObject;
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public boolean showDialog(Context context, DialogArgs dialogArgs) {
        dismissFingerprintDialog();
        dismissAndCreateNewInternalDialog();
        this.internalDialog.create((Activity) context, dialogArgs);
        this.internalDialog.show();
        return true;
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public boolean showFingerprintDialog() {
        this.fingerprintDialog.showFingerprintDialog(new fdyxd());
        boolean listen = this.authenticator.listen(this.adapter);
        if (!listen) {
            this.fingerprintDialog.dismiss();
            this.authenticator.cancel();
        }
        return listen;
    }
}
