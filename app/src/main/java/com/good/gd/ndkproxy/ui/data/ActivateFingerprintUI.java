package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.messages.FingerprintCheckedChangedMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDBiometricsUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDActivateFingerprintView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.biometric.BBDDialogFactory;
import com.good.gd.ui.biometric.DialogArgs;
import com.good.gd.ui.biometric.DialogInterface;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.UserChecker;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class ActivateFingerprintUI extends BaseUI implements GDFingerprintActivationAuthenticator.Callback {
    private GDFingerprintActivationAuthenticator authenticator;
    private WeakReference<Context> contextWeakReference;
    private DialogInterface dialogInterface = GDAbstractBiometricHelper.createDialogInterface();
    protected final FingerprintAuthenticationManager fingerprintAuthenticationManager;
    private final UserChecker userChecker;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements View.OnClickListener {
        ehnkx() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivateFingerprintUI.this.fingerprintAuthenticationManager.getAuthenticationHandler().startFingerprintEnrollment((Context) ActivateFingerprintUI.this.contextWeakReference.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements View.OnClickListener {
        fdyxd() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivateFingerprintUI.this.deactivate(true);
            ActivateFingerprintUI.this.cancel();
            ActivateFingerprintUI.this.dialogInterface.dismissDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements View.OnClickListener {
        hbfhc() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            BBDUIEventManager.sendMessage(ActivateFingerprintUI.this, BBDUIMessageType.MSG_UI_CANCEL);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements View.OnClickListener {
        yfdke() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ActivateFingerprintUI.this.fingerprintAuthenticationManager.getAuthenticationHandler().startFingerprintEnrollment((Context) ActivateFingerprintUI.this.contextWeakReference.get());
        }
    }

    public ActivateFingerprintUI(long j, UserChecker userChecker, FingerprintAuthenticationManager fingerprintAuthenticationManager) {
        super(BBUIType.UI_ACTIVATE_FINGERPRINT, j);
        this.userChecker = userChecker;
        this.fingerprintAuthenticationManager = fingerprintAuthenticationManager;
    }

    private boolean canUseFingerprint(AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2) {
        GDFingerprintActivationAuthenticator gDFingerprintActivationAuthenticator = this.authenticator;
        return gDFingerprintActivationAuthenticator != null && gDFingerprintActivationAuthenticator.canConfigureFingerprint(atomicBoolean, atomicBoolean2);
    }

    private void closeCurrent() {
        CoreUI.closeUI(getCoreHandle());
    }

    private void computeStateAndDialogs(boolean z, boolean z2) {
        Context context;
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        AtomicBoolean atomicBoolean2 = new AtomicBoolean();
        if (canUseFingerprint(atomicBoolean2, atomicBoolean)) {
            if (!z2) {
                if (!z) {
                    return;
                }
                hideDialogs();
                deactivate(true);
                cancel();
            } else if (atomicBoolean2.get() && atomicBoolean.get()) {
                if (!atomicBoolean.get() || this.authenticator.hasEnrolledFingerprints()) {
                    return;
                }
                hideDialogs();
                deactivate(false);
                sendSwitchUpdate(false);
                promptToEnrollFingerprints();
            } else {
                this.dialogInterface.dismissFingerprintDialog();
                this.dialogInterface.dismissDialog();
                if (!this.authenticator.hasEnrolledFingerprints()) {
                    sendSwitchUpdate(false);
                    this.fingerprintAuthenticationManager.getAuthenticationHandler().deleteKeyStoreKey();
                    this.fingerprintAuthenticationManager.resetActivation();
                    promptToEnrollFingerprints();
                } else if (this.dialogInterface.showFingerprintDialog()) {
                } else {
                    sendSwitchUpdate(false);
                    this.fingerprintAuthenticationManager.setUnlockAcceptedByUser(false);
                    WeakReference<Context> weakReference = this.contextWeakReference;
                    if (weakReference == null || (context = weakReference.get()) == null) {
                        return;
                    }
                    Toast.makeText(context, GDLocalizer.getLocalizedString("Problem activating. Try again"), 0).show();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deactivate(boolean z) {
        this.fingerprintAuthenticationManager.setDeviceFingerprintsChanged(false);
        GDLog.DBGPRINTF(14, "GDActivateFingerprintView: Deactivate " + z + "\n");
        if (z) {
            this.fingerprintAuthenticationManager.setUnlockAcceptedByUserAndNotify(false);
        } else {
            this.fingerprintAuthenticationManager.setUnlockAcceptedByUser(false);
        }
        sendSwitchUpdate(false);
    }

    private void hideDialogs() {
        this.dialogInterface.dismissDialog();
        this.dialogInterface.dismissFingerprintDialog();
    }

    private void hideDialogsAndUpdateSwitch() {
        hideDialogs();
        sendSwitchUpdate(false);
    }

    private void promptToEnrollFingerprints() {
        String str;
        String str2;
        if (this.userChecker.isUser0(this.contextWeakReference.get())) {
            str = GDAbstractBiometricHelper.DIALOG_TITLE_ENROLL_FINGERPRINT;
            str2 = GDAbstractBiometricHelper.DIALOG_TEXT_ENROLL_FINGERPRINT;
        } else {
            str = GDAbstractBiometricHelper.DIALOG_TITLE_ENROLL_FINGERPRINT_WORK;
            str2 = GDAbstractBiometricHelper.DIALOG_TEXT_ENROLL_FINGERPRINT_WORK;
        }
        DialogArgs dialogArgs = new DialogArgs(str, str2, GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_ENROLL);
        dialogArgs.setNegativeButtonCallback(new fdyxd());
        dialogArgs.setPositiveButtonCallback(new ehnkx());
        this.dialogInterface.showDialog(this.contextWeakReference.get(), dialogArgs);
    }

    private void sendSwitchUpdate(boolean z) {
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_UPDATE_BIOMETRY_ERROR).successful(z).build(), this);
    }

    private void showBiometryErrorDialog() {
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_BIOMETRY_ERROR_ACTIVATION, GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRY_ERROR_ACTIVATION, GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_GO_TO_SETTINGS);
        dialogArgs.setNegativeButtonCallback(new hbfhc());
        dialogArgs.setPositiveButtonCallback(new yfdke());
        this.dialogInterface.showDialog(this.contextWeakReference.get(), dialogArgs);
    }

    public GDFingerprintActivationAuthenticator createAuthenticator() {
        GDFingerprintActivationAuthenticator activationAuthenticator = this.fingerprintAuthenticationManager.getActivationAuthenticator(!GDActivitySupport.isAuthorised());
        this.authenticator = activationAuthenticator;
        return activationAuthenticator;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        DialogArgs createBasicActivationArgs = BBDDialogFactory.createBasicActivationArgs();
        this.dialogInterface.setUiData(this);
        this.dialogInterface.init((Activity) context, createBasicActivationArgs);
        this.dialogInterface.setAuthenticator(createAuthenticator());
        this.dialogInterface.registerForActivationCallback(this);
        this.contextWeakReference = new WeakReference<>(context);
        return new GDActivateFingerprintView(context, viewInteractor, this, viewCustomizer);
    }

    public GDFingerprintActivationAuthenticator getAuthenticator() {
        return this.authenticator;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleCancelEvent() {
        hideDialogsAndUpdateSwitch();
        deactivate(true);
        cancel();
    }

    public boolean hasEnrolledAndCanUseFingerprint(AtomicBoolean atomicBoolean, AtomicBoolean atomicBoolean2) {
        boolean canUseFingerprint = canUseFingerprint(atomicBoolean, atomicBoolean2);
        atomicBoolean2.set(this.authenticator.hasEnrolledFingerprints());
        return canUseFingerprint;
    }

    public boolean isDialogShowing() {
        return this.dialogInterface.isDialogShowing();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintActivationAuthenticator.Callback
    public void onActivationSuccess(byte[] bArr) {
        this.dialogInterface.onAuthenticationSuccess();
        sendSwitchUpdate(true);
        this.fingerprintAuthenticationManager.setDeviceFingerprintsChanged(false);
        GDLog.DBGPRINTF(14, "GDActivateFingerprintView: Activate with" + (bArr == null ? "out" : "") + " data");
        this.fingerprintAuthenticationManager.activateFingerprint(bArr);
        this.fingerprintAuthenticationManager.setUnlockAcceptedByUserAndNotify(true);
        cancel();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationError(int i, CharSequence charSequence) {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationFailed() {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationOperationDenied(String str) {
        sendSwitchUpdate(false);
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationTimedOut() {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        boolean z = false;
        if (ordinal == 11) {
            AtomicBoolean atomicBoolean = new AtomicBoolean();
            AtomicBoolean atomicBoolean2 = new AtomicBoolean();
            boolean canUseFingerprint = canUseFingerprint(atomicBoolean2, atomicBoolean);
            GDLog.DBGPRINTF(14, String.format("GDActivateFingerprintView: update %b %b %b\n", Boolean.valueOf(canUseFingerprint), Boolean.valueOf(atomicBoolean2.get()), Boolean.valueOf(atomicBoolean.get())));
            if (canUseFingerprint && atomicBoolean.get()) {
                return;
            }
            if (canUseFingerprint && !atomicBoolean.get()) {
                z = true;
            } else if (canUseFingerprint || !atomicBoolean.get()) {
                return;
            }
            BBDUIEventManager.sendUpdateEvent(new BBDBiometricsUpdateEvent(z, canUseFingerprint, true), this);
        } else if (ordinal == 13) {
            handleCancelEvent();
        } else if (ordinal == 18) {
            hideDialogs();
        } else if (ordinal != 19) {
            switch (ordinal) {
                case 21:
                    hideDialogs();
                    this.fingerprintAuthenticationManager.notifyUnlockAcknowledgedByUser();
                    closeCurrent();
                    return;
                case 22:
                    deactivate(false);
                    showBiometryErrorDialog();
                    return;
                case 23:
                    deactivate(false);
                    promptToEnrollFingerprints();
                    return;
                case 24:
                    hideDialogsAndUpdateSwitch();
                    return;
                default:
                    super.onMessage(bBDUIMessageType, obj);
                    return;
            }
        } else if (!isActive()) {
            GDLog.DBGPRINTF(13, "GDActivateFingerprintView: cannot handle msg_activate");
        } else if (!(obj instanceof FingerprintCheckedChangedMsg)) {
        } else {
            FingerprintCheckedChangedMsg fingerprintCheckedChangedMsg = (FingerprintCheckedChangedMsg) obj;
            computeStateAndDialogs(fingerprintCheckedChangedMsg.isUiUpdate(), fingerprintCheckedChangedMsg.isSwitchChecked());
        }
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onSensorLockout() {
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateDestroyed() {
        GDLog.DBGPRINTF(14, "GDActivateFingerprintView: clean\n");
        hideDialogs();
        this.dialogInterface.deregister();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        hideDialogs();
    }
}
