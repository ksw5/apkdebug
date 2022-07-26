package com.good.gd.ndkproxy.ui.data;

import com.good.gd.ndkproxy.auth.FingerprintAuthenticationManager;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ui.biometric.BBDDialogFactory;
import com.good.gd.ui.biometric.DialogArgs;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;

/* loaded from: classes.dex */
public class ReauthenticationUnlockBiometricUI extends UnlockBiometricUI {
    private final String text;
    private final String title;

    public ReauthenticationUnlockBiometricUI(long j, String str, String str2, PasswordUnlock passwordUnlock, FingerprintAuthenticationManager fingerprintAuthenticationManager, CloseBiometryDialogChecker closeBiometryDialogChecker) {
        super(BBUIType.UI_REAUTH_UNLOCK_BIOMETRIC, j, passwordUnlock, fingerprintAuthenticationManager, closeBiometryDialogChecker);
        this.title = str;
        this.text = str2;
    }

    @Override // com.good.gd.ndkproxy.ui.data.UnlockBiometricUI
    protected DialogArgs getDialogArgs() {
        return BBDDialogFactory.createArgs(this.title, this.text, GDAbstractBiometricHelper.BUTTON_CANCEL);
    }
}
