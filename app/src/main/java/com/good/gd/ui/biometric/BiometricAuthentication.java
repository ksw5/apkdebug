package com.good.gd.ui.biometric;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Handler;
import android.view.View;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationHandler;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.android.BBDAndroidPAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricFactory;
import com.good.gd.ndkproxy.auth.biometric.prompt.BiometricPromptData;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.biometric.callback.ActivationCallbackAdapter;
import com.good.gd.ui.biometric.callback.BiometricCancelListener;
import com.good.gd.ui.biometric.callback.CallbackAdapter;
import com.good.gd.ui.biometric.callback.UnlockCallbackAdapter;
import com.good.gd.utils.GDLocalizer;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class BiometricAuthentication implements DialogInterface, GDFingerprintAuthenticator.Callback {
    private CallbackAdapter adapter;
    private GDFingerprintAuthenticator authenticator;
    private BiometricPrompt biometricPrompt;
    private BBDDialog internalDialog = new BBDDialog();
    private BBDUIObject uiData;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class aqdzk implements View.OnClickListener {
        aqdzk() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements Runnable {
        ehnkx() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BiometricAuthentication.this.authenticator.cancel();
        }
    }

    /* loaded from: classes.dex */
    class fdyxd implements View.OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BiometricAuthentication.this.dismissDialog();
            BiometricAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class gioey implements View.OnClickListener {
        gioey() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class hbfhc implements View.OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!BiometricAuthentication.this.showFingerprintDialog()) {
                BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_SHOW_FINGERPRINT_DIALOG);
            }
        }
    }

    /* loaded from: classes.dex */
    class mjbm implements View.OnClickListener {
        mjbm() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class ooowe implements View.OnClickListener {
        ooowe() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class opjy implements View.OnClickListener {
        opjy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class orlrx implements View.OnClickListener {
        final /* synthetic */ Context dbjc;

        orlrx(BiometricAuthentication biometricAuthentication, Context context) {
            this.dbjc = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.dbjc.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }

    /* loaded from: classes.dex */
    class pmoiy implements View.OnClickListener {
        pmoiy() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BiometricAuthentication.this.dismissDialog();
            BiometricAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class vzw implements View.OnClickListener {
        vzw() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BiometricAuthentication.this.dismissDialog();
            BiometricAuthentication.this.showFingerprintDialog();
        }
    }

    /* loaded from: classes.dex */
    class yfdke implements View.OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(BiometricAuthentication.this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* loaded from: classes.dex */
    class yjh implements View.OnClickListener {
        yjh() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BiometricAuthentication.this.showFingerprintDialog();
        }
    }

    private void cancelAuthenticator() {
        new Handler().postDelayed(new ehnkx(), 100L);
    }

    private void createBiometricPrompt(Context context, DialogArgs dialogArgs) {
        this.biometricPrompt = BiometricFactory.getInstance().getBiometricPromptProvider().getBiometricPrompt(context, new BiometricPromptData(GDLocalizer.getLocalizedString(dialogArgs.getTitleKey()), GDLocalizer.getLocalizedString(dialogArgs.getTextKey()), GDLocalizer.getLocalizedString(dialogArgs.getNegativeButtonTextKey()), new BiometricCancelListener(this.uiData.getBBDUIType())));
    }

    private void showDeviceSecurityDialog() {
        Context context = this.internalDialog.getContext();
        boolean z = context != null;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED, GDAbstractBiometricHelper.BUTTON_USE_PASSWORD, z ? GDAbstractBiometricHelper.BUTTON_SETTINGS : null);
        dialogArgs.setNegativeButtonCallback(new aqdzk());
        if (z) {
            dialogArgs.setPositiveButtonCallback(new orlrx(this, context));
        }
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED), GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void deregister() {
        CallbackAdapter callbackAdapter = this.adapter;
        if (callbackAdapter != null) {
            if (callbackAdapter instanceof UnlockCallbackAdapter) {
                ((UnlockCallbackAdapter) callbackAdapter).setUnlockCallback(null);
            }
            CallbackAdapter callbackAdapter2 = this.adapter;
            if (callbackAdapter2 instanceof ActivationCallbackAdapter) {
                ((ActivationCallbackAdapter) callbackAdapter2).setActivationCallback(null);
            }
            this.adapter.setCallback(null);
        }
        this.uiData = null;
        this.biometricPrompt = null;
        BBDAndroidPAuthenticator bBDAndroidPAuthenticator = (BBDAndroidPAuthenticator) GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().getBBDAndroidAuthenticator();
        bBDAndroidPAuthenticator.setContext(null);
        bBDAndroidPAuthenticator.setBiometricPrompt(null);
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void dismissDialog() {
        this.internalDialog.dismiss();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void dismissFingerprintDialog() {
        this.authenticator.cancel();
    }

    public void init(Context context) {
        BBDDialog bBDDialog = this.internalDialog;
        if (bBDDialog != null) {
            bBDDialog.dismiss();
        }
        BBDAndroidPAuthenticator bBDAndroidPAuthenticator = (BBDAndroidPAuthenticator) GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().getBBDAndroidAuthenticator();
        if (bBDAndroidPAuthenticator != null) {
            bBDAndroidPAuthenticator.setContext(context);
            bBDAndroidPAuthenticator.setBiometricPrompt(this.biometricPrompt);
        }
        this.internalDialog.setActivity((Activity) context);
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public boolean isDialogShowing() {
        return this.internalDialog.isShowing();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        cancelAuthenticator();
        if (i == 5) {
            return;
        }
        if (i == 10) {
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_UI_CANCEL);
        } else if (i == -1) {
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_BIOMETRY_TYPE_NOT_ALLOWED);
        } else if (i == -2) {
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_BIOMETRY_TYPE_NOT_SUPPORTED);
        } else if (i == -3) {
            BBDUIEventManager.sendMessage(this.uiData, BBDUIMessageType.MSG_BIOMETRY_INTERNAL_ERROR);
            GDLog.DBGPRINTF(13, "Fingerprint error: " + ((Object) charSequence) + " \n");
        } else if (i == 15) {
            showDeviceSecurityDialog();
        } else {
            if (charSequence == null) {
                charSequence = GDAbstractBiometricHelper.DIALOG_TEXT_INTERNAL_ERROR_RETRY;
            }
            BBDUIObject bBDUIObject = this.uiData;
            DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, charSequence.toString(), (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
            dialogArgs.setPositiveButtonCallback(new pmoiy());
            dialogArgs.setNegativeButtonCallback(new mjbm());
            String charSequence2 = charSequence.toString();
            if (i == 1) {
                charSequence2 = GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRY_ERROR_ACTIVATION);
            }
            this.internalDialog.create(dialogArgs);
            this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED), charSequence2);
            this.internalDialog.show();
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationFailed() {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationOperationDenied(String str) {
        cancelAuthenticator();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setNegativeButtonCallback(new yfdke());
        dialogArgs.setPositiveButtonCallback(new fdyxd());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED), GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void onAuthenticationSuccess() {
        this.authenticator.cancel();
        dismissDialog();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationTimedOut() {
        cancelAuthenticator();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_TIMED_OUT, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TIMED_OUT, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setNegativeButtonCallback(new opjy());
        dialogArgs.setPositiveButtonCallback(new hbfhc());
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_TIMED_OUT), GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TIMED_OUT));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
        cancelAuthenticator();
        String str = z ? GDAbstractBiometricHelper.DIALOG_TEXT_INTERNAL_ERROR_RETRY : GDAbstractBiometricHelper.DIALOG_TEXT_INTERNAL_ERROR;
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_CRYPTO_FAILURE, str, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, null);
        dialogArgs.setNegativeButtonCallback(new gioey());
        if (z) {
            dialogArgs.setPositiveButtonTextKey(GDAbstractBiometricHelper.BUTTON_RETRY);
            dialogArgs.setPositiveButtonCallback(new vzw());
        }
        this.internalDialog.create(dialogArgs);
        this.internalDialog.showError(GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.DIALOG_TITLE_CRYPTO_FAILURE), GDLocalizer.getLocalizedString(str));
        this.internalDialog.show();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onSensorLockout() {
        cancelAuthenticator();
        BBDUIObject bBDUIObject = this.uiData;
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_AUTHENTICATION_FAILED, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS, (bBDUIObject == null || bBDUIObject.getBBDUIType() != BBUIType.UI_ACTIVATE_FINGERPRINT) ? GDAbstractBiometricHelper.BUTTON_USE_PASSWORD : GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_RETRY);
        dialogArgs.setPositiveButtonCallback(new yjh());
        dialogArgs.setNegativeButtonCallback(new ooowe());
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
        BBDDialog bBDDialog = new BBDDialog();
        this.internalDialog = bBDDialog;
        bBDDialog.create((Activity) context, dialogArgs);
        this.internalDialog.show();
        return true;
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public boolean showFingerprintDialog() {
        boolean listen = this.authenticator.listen(this.adapter);
        if (listen) {
            dismissDialog();
        }
        return listen;
    }

    @Override // com.good.gd.ui.biometric.DialogInterface
    public void init(Activity activity, DialogArgs dialogArgs) {
        createBiometricPrompt(activity, dialogArgs);
        GDFingerprintAuthenticationHandler authenticationHandler = GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler();
        if (authenticationHandler != null) {
            BBDAndroidPAuthenticator bBDAndroidPAuthenticator = (BBDAndroidPAuthenticator) authenticationHandler.getBBDAndroidAuthenticator();
            if (bBDAndroidPAuthenticator != null) {
                bBDAndroidPAuthenticator.setContext(activity);
                bBDAndroidPAuthenticator.setBiometricPrompt(this.biometricPrompt);
            } else {
                GDLog.DBGPRINTF(12, "BiometricAuthentication error: null authenticator\n");
            }
        } else {
            GDLog.DBGPRINTF(12, "BiometricAuthentication error: null authenticationHandler\n");
        }
        this.internalDialog.dismiss();
        this.internalDialog.setActivity(activity);
    }
}
