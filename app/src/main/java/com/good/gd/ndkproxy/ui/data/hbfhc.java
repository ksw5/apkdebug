package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.authenticator.GDFingerprintUnlockAuthenticator;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;

/* loaded from: classes.dex */
abstract class hbfhc extends DialogUI implements GDFingerprintUnlockAuthenticator.Callback {
    private final CloseBiometryDialogChecker closeBiometryDialogChecker;
    private boolean isUiPaused = false;

    public hbfhc(BBUIType bBUIType, long j, CloseBiometryDialogChecker closeBiometryDialogChecker) {
        super(bBUIType, j);
        this.closeBiometryDialogChecker = closeBiometryDialogChecker;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleCancelMessage() {
        GDLog.DBGPRINTF(14, "UnlockBiometricUI::onMessage::MSG_UI_CANCEL isUiPaused=" + this.isUiPaused + " needToCloseBiometryDialog=" + this.closeBiometryDialogChecker.needToCloseBiometryDialog() + "\n");
        if (this.isUiPaused || !this.closeBiometryDialogChecker.needToCloseBiometryDialog()) {
            return;
        }
        CoreUI.closeUI(getCoreHandle());
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        if (bBDUIMessageType.ordinal() != 13) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            handleCancelMessage();
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        super.onStateActive();
        this.closeBiometryDialogChecker.activate();
        this.isUiPaused = false;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStatePaused() {
        super.onStatePaused();
        this.closeBiometryDialogChecker.deactivate();
        this.isUiPaused = true;
    }
}
