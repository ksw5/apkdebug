package com.good.gd.ndkproxy.ui.data;

import android.app.Activity;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.ui.BBDUIDataStore;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
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
public class ActivationUnlockBiometricUI extends hbfhc {
    private static final String TAG = "ActivationUnlockBiometricUI";
    private GDFingerprintUnlockAuthenticator authenticator;
    private DialogInterface dialogInterface = GDAbstractBiometricHelper.createDialogInterface();
    private final FingerprintAuthenticationManager fingerprintAuthenticationManager;
    private final PasswordUnlock passwordUnlock;

    public ActivationUnlockBiometricUI(long j, PasswordUnlock passwordUnlock, FingerprintAuthenticationManager fingerprintAuthenticationManager, CloseBiometryDialogChecker closeBiometryDialogChecker) {
        super(BBUIType.UI_ACTIVATION_UNLOCK_BIOMETRIC, j, closeBiometryDialogChecker);
        this.passwordUnlock = passwordUnlock;
        this.fingerprintAuthenticationManager = fingerprintAuthenticationManager;
    }

    private void handleFingerprintUnlockResult(boolean z) {
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(BBUIType.UI_ACTIVATION_UNLOCK);
        if (z) {
            this.dialogInterface.onAuthenticationSuccess();
            this.passwordUnlock.submitMatchingBiometry(getCoreHandle());
            return;
        }
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_UNLOCK_RESULT).successful(false).addTitle(GDLocalizer.getLocalizedString("Unlock Failure")).addText(GDLocalizer.getLocalizedString("The fingerprint failed to unlock the application. Unlock with password then disable and re-enable fingerprint unlock to reset this feature")).build(), uIData);
        CoreUI.closeUI(getCoreHandle());
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

    public boolean initFingerprintDialog() {
        this.dialogInterface.setAuthenticator(getAuthenticator());
        boolean z = false;
        if (this.fingerprintAuthenticationManager.hasDevicePasswordSettingsChanged()) {
            CoreUI.closeUI(getCoreHandle());
            return false;
        }
        if (this.authenticator.getFingerprintUsage() == GDFingerprintUnlockAuthenticator.FingerprintUsage.ALLOWED) {
            z = this.dialogInterface.showFingerprintDialog();
        } else {
            this.dialogInterface.dismissFingerprintDialog();
        }
        if (!z) {
            CoreUI.closeUI(getCoreHandle());
        }
        return z;
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
        DialogArgs createBasicUnlockArgs = BBDDialogFactory.createBasicUnlockArgs();
        this.dialogInterface.setUiData(this);
        this.dialogInterface.init(activity, createBasicUnlockArgs);
        this.dialogInterface.setAuthenticator(createAuthenticator());
        this.dialogInterface.registerForUnlockCallback(this);
        return initFingerprintDialog();
    }
}
