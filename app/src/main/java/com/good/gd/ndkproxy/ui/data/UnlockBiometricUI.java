package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.ui.BBDUIDataStore;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.biometric.BBDDialogFactory;
import com.good.gd.ui.biometric.DialogArgs;
import com.good.gd.ui.biometric.DialogInterface;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.ndkproxy.util.GTLog;
import java.security.GeneralSecurityException;

/* loaded from: classes.dex */
public class UnlockBiometricUI extends hbfhc {
    private static final String TAG = "UnlockBiometricUI";
    private GDFingerprintUnlockAuthenticator authenticator;
    private DialogInterface dialogInterface;
    private final FingerprintAuthenticationManager fingerprintAuthenticationManager;
    private final PasswordUnlock passwordUnlock;

    public UnlockBiometricUI(long j, PasswordUnlock passwordUnlock, FingerprintAuthenticationManager fingerprintAuthenticationManager, CloseBiometryDialogChecker closeBiometryDialogChecker) {
        this(BBUIType.UI_UNLOCK_BIOMETRIC, j, passwordUnlock, fingerprintAuthenticationManager, closeBiometryDialogChecker);
    }

    private boolean checkFingerprintSetChanged() {
        if (this.fingerprintAuthenticationManager.hasDeviceFingerprintsChanged()) {
            return true;
        }
        return GDFingerprintUnlockAuthenticator.FingerprintUsage.NOT_ALLOWED != this.authenticator.getFingerprintUsage() && this.fingerprintAuthenticationManager.hasDevicePasswordSettingsChanged();
    }

    private void handleFingerprintUnlockResult(boolean z) {
        if (z) {
            this.dialogInterface.onAuthenticationSuccess();
            this.passwordUnlock.submitMatchingBiometry(getCoreHandle());
        } else if (this.fingerprintAuthenticationManager.hasDevicePasswordSettingsChanged()) {
            requestShowFingerprintSetChanged();
            CoreUI.closeUI(getCoreHandle());
        } else {
            BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_UNLOCK_RESULT).successful(false).addTitle(GDLocalizer.getLocalizedString("Unlock Failure")).addText(GDLocalizer.getLocalizedString("The fingerprint failed to unlock the application. Unlock with password then disable and re-enable fingerprint unlock to reset this feature")).build(), BBDUIDataStore.getInstance().getUIData(BBUIType.UI_ACTIVATION_UNLOCK));
            CoreUI.closeUI(getCoreHandle());
        }
    }

    private boolean isFingerprintExpired(GDFingerprintUnlockAuthenticator.FingerprintUsage fingerprintUsage) {
        return (fingerprintUsage == GDFingerprintUnlockAuthenticator.FingerprintUsage.ALLOWED || fingerprintUsage == GDFingerprintUnlockAuthenticator.FingerprintUsage.EXPIRED) && fingerprintUsage == GDFingerprintUnlockAuthenticator.FingerprintUsage.EXPIRED;
    }

    private void requestMessageOnLoginView(String str, UIEventType uIEventType) {
        this.dialogInterface.dismissFingerprintDialog();
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(uIEventType).successful(true).addText(str).build(), BBDUIDataStore.getInstance().getUIData(BBUIType.UI_UNLOCK));
    }

    private void requestShowFingerprintExpired() {
        requestMessageOnLoginView(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_EXPIRED, UIEventType.UI_FINGERPRINT_EXPIRED);
    }

    private void requestShowFingerprintSetChanged() {
        requestMessageOnLoginView(GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_CHANGED, UIEventType.UI_UPDATE_BIOMETRY_ERROR);
    }

    public GDFingerprintUnlockAuthenticator createAuthenticator() {
        boolean isAuthorised = GDActivitySupport.isAuthorised();
        boolean isBackgrondUnlocked = GDActivitySupport.isBackgrondUnlocked();
        GTLog.DBGPRINTF(16, TAG, "createAuthenticator first unlocked: " + isAuthorised + ", background unlocked: " + isBackgrondUnlocked + "\n");
        GDFingerprintUnlockAuthenticator unlockAuthenticator = this.fingerprintAuthenticationManager.getUnlockAuthenticator(!isAuthorised || isBackgrondUnlocked);
        this.authenticator = unlockAuthenticator;
        return unlockAuthenticator;
    }

    public GDFingerprintUnlockAuthenticator getAuthenticator() {
        return this.authenticator;
    }

    protected DialogArgs getDialogArgs() {
        return BBDDialogFactory.createBasicUnlockArgs();
    }

    public boolean initFingerprintDialog() {
        GDFingerprintUnlockAuthenticator.FingerprintUsage fingerprintUsage = this.authenticator.getFingerprintUsage();
        boolean checkFingerprintSetChanged = checkFingerprintSetChanged();
        boolean z = false;
        if (!checkFingerprintSetChanged && fingerprintUsage == GDFingerprintUnlockAuthenticator.FingerprintUsage.EXPIRED) {
            CoreUI.closeUI(getCoreHandle());
            requestShowFingerprintExpired();
            return false;
        } else if (checkFingerprintSetChanged) {
            CoreUI.closeUI(getCoreHandle());
            requestShowFingerprintSetChanged();
            return false;
        } else {
            if (!isFingerprintExpired(fingerprintUsage) && !checkFingerprintSetChanged && fingerprintUsage == GDFingerprintUnlockAuthenticator.FingerprintUsage.ALLOWED) {
                z = this.dialogInterface.showFingerprintDialog();
                if (!z) {
                    this.fingerprintAuthenticationManager.setDeviceFingerprintsDeactivated();
                    if (checkFingerprintSetChanged()) {
                        requestShowFingerprintSetChanged();
                    }
                }
            } else {
                this.dialogInterface.dismissFingerprintDialog();
            }
            if (!z) {
                CoreUI.closeUI(getCoreHandle());
            }
            return z;
        }
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
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onAuthenticationTimedOut() {
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator.Callback
    public void onColdStartAuthenticationSuccess(byte[] bArr) {
        handleFingerprintUnlockResult(this.fingerprintAuthenticationManager.unlockWithFingerprint(bArr));
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onCryptoFailure(GeneralSecurityException generalSecurityException, boolean z) {
    }

    @Override // com.good.gd.ndkproxy.ui.data.hbfhc, com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal != 13) {
            if (ordinal == 17) {
                initFingerprintDialog();
                return;
            } else if (ordinal != 22) {
                super.onMessage(bBDUIMessageType, obj);
                return;
            } else {
                requestMessageOnLoginView(GDAbstractBiometricHelper.DIALOG_TEXT_BIOMETRY_ERROR_UNLOCK, UIEventType.UI_UPDATE_BIOMETRY_NOT_ALLOWED);
            }
        }
        this.dialogInterface.deregister();
        handleCancelMessage();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintAuthenticator.Callback
    public void onSensorLockout() {
    }

    @Override // com.good.gd.ndkproxy.ui.data.hbfhc, com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        super.onStateActive();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateDestroyed() {
        super.onStateDestroyed();
        this.dialogInterface.deregister();
    }

    @Override // com.good.gd.ndkproxy.ui.data.hbfhc, com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        this.dialogInterface.dismissFingerprintDialog();
        this.dialogInterface.dismissDialog();
    }

    @Override // com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator.Callback
    public void onWarmStartAuthenticationSuccess() {
        handleFingerprintUnlockResult(this.fingerprintAuthenticationManager.unlockWithFingerprint());
    }

    @Override // com.good.gd.ndkproxy.ui.data.dialog.DialogUI
    public boolean showDialog(Activity activity) {
        DialogArgs dialogArgs = getDialogArgs();
        this.dialogInterface.setUiData(this);
        this.dialogInterface.init(activity, dialogArgs);
        this.dialogInterface.setAuthenticator(createAuthenticator());
        this.dialogInterface.registerForUnlockCallback(this);
        return initFingerprintDialog();
    }

    public UnlockBiometricUI(BBUIType bBUIType, long j, PasswordUnlock passwordUnlock, FingerprintAuthenticationManager fingerprintAuthenticationManager, CloseBiometryDialogChecker closeBiometryDialogChecker) {
        super(bBUIType, j, closeBiometryDialogChecker);
        this.passwordUnlock = passwordUnlock;
        this.fingerprintAuthenticationManager = fingerprintAuthenticationManager;
        this.dialogInterface = GDAbstractBiometricHelper.createDialogInterface();
    }
}
