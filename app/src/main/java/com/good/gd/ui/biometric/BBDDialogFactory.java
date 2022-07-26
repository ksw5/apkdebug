package com.good.gd.ui.biometric;

import android.app.Activity;
import android.view.View;

/* loaded from: classes.dex */
public class BBDDialogFactory {
    public static DialogArgs createArgs(String str, String str2, String str3) {
        return new DialogArgs(str, str2, str3, null, GDAbstractBiometricHelper.DIALOG_TEXT_FINGERPRINT_TOUCH_SENSOR);
    }

    public static DialogArgs createBasicActivationArgs() {
        return createArgs(GDAbstractBiometricHelper.DIALOG_TITLE_ACTIVATE_FINGERPRINT, GDAbstractBiometricHelper.DIALOG_TEXT_ACTIVATE_FINGERPRINT, GDAbstractBiometricHelper.BUTTON_CANCEL);
    }

    public static DialogArgs createBasicUnlockArgs() {
        return createArgs(GDAbstractBiometricHelper.DIALOG_TITLE_LOGIN, GDAbstractBiometricHelper.DIALOG_TEXT_LOGIN, GDAbstractBiometricHelper.BUTTON_USE_PASSWORD);
    }

    public static BBDDialog createEnableScreenLockDialog(Activity activity, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        BBDDialog bBDDialog = new BBDDialog();
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_ENABLE_SCREENLOCK, GDAbstractBiometricHelper.DIALOG_TEXT_ENABLE_SCREENLOCK, GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_ENABLE);
        dialogArgs.setNegativeButtonCallback(onClickListener);
        dialogArgs.setPositiveButtonCallback(onClickListener2);
        bBDDialog.create(activity, dialogArgs);
        return bBDDialog;
    }

    public static BBDDialog createEnrollFingerprintsDialog(Activity activity, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        BBDDialog bBDDialog = new BBDDialog();
        DialogArgs dialogArgs = new DialogArgs(GDAbstractBiometricHelper.DIALOG_TITLE_ENROLL_FINGERPRINT, GDAbstractBiometricHelper.DIALOG_TEXT_ENROLL_FINGERPRINT, GDAbstractBiometricHelper.BUTTON_CANCEL, GDAbstractBiometricHelper.BUTTON_ENROLL);
        dialogArgs.setNegativeButtonCallback(onClickListener);
        dialogArgs.setPositiveButtonCallback(onClickListener2);
        bBDDialog.create(activity, dialogArgs);
        return bBDDialog;
    }
}
