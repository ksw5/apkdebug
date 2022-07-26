package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.ui.data.ActivationTypeSelectionUI;
import com.good.gd.ndkproxy.ui.data.ActivationUI;
import com.good.gd.ndkproxy.ui.data.QRCodeScanUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.data.base.SetChangePasswordBaseUI;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class BBDUILocalizationHelper {
    private static native String _getEnterCredentialsTextKey(long j);

    private static native String _getLocalizableBCPUrlKey(long j);

    private static native String _getLocalizableBottomButtonKey(long j);

    private static native String _getLocalizableConfirmPasswordKey(long j);

    private static native String _getLocalizableCredentialsText(long j);

    private static native String _getLocalizableEmailAddressKey(long j);

    private static native String _getLocalizableInvalidPinKey(long j);

    private static native String _getLocalizableNewPasswordKey(long j);

    private static native String _getLocalizableNoQRCodeTitleKey(long j);

    private static native String _getLocalizableOldPasswordKey(long j);

    private static native String _getLocalizablePasswordEyeKey(long j);

    private static native String _getLocalizablePasswordNowRequiredKey(long j);

    private static native String _getLocalizablePasswordRequirementsKey(long j);

    private static native String _getLocalizablePinHintKey(long j);

    private static native String _getLocalizableProvisionInfoMessageKey(long j);

    private static native String _getLocalizableQRCodeButtonKey(long j);

    private static native String _getLocalizableQRCodeErrorMessageKey(long j);

    private static native String _getLocalizableQRCodeMessageMessageKey(long j);

    private static native String _getLocalizableQRCodeTitleKey(long j);

    private static native String _getLocalizableScanQRButtonKey(long j);

    private static native String _getLocalizableSignInText(long j);

    public static String getEnterCredentialsTextKey(BaseUI baseUI) {
        long coreHandle = baseUI.getCoreHandle();
        return coreHandle != 0 ? _getEnterCredentialsTextKey(coreHandle) : "";
    }

    public static String getLocalizableBCPUrlKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableBCPUrlKey(coreHandle) : "";
    }

    public static String getLocalizableBottomButtonKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableBottomButtonKey(coreHandle) : "";
    }

    public static String getLocalizableCredentialsText(ActivationTypeSelectionUI activationTypeSelectionUI) {
        long coreHandle = activationTypeSelectionUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableCredentialsText(coreHandle) : "";
    }

    public static String getLocalizableEmailAddressKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableEmailAddressKey(coreHandle) : "";
    }

    public static String getLocalizableInvalidPinKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableInvalidPinKey(coreHandle) : "";
    }

    public static String getLocalizableNoQRCodeTitleKey(ActivationTypeSelectionUI activationTypeSelectionUI) {
        long coreHandle = activationTypeSelectionUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableNoQRCodeTitleKey(coreHandle) : "";
    }

    public static String getLocalizablePasswordEyeKey(BaseUI baseUI) {
        long coreHandle = baseUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizablePasswordEyeKey(coreHandle) : "";
    }

    public static String getLocalizablePinHintKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizablePinHintKey(coreHandle) : "";
    }

    public static String getLocalizableProvisionInfoMessageKey(ActivationUI activationUI) {
        long coreHandle = activationUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableProvisionInfoMessageKey(coreHandle) : "";
    }

    public static String getLocalizableQRCodeButtonKey(BaseUI baseUI) {
        long coreHandle = baseUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableQRCodeButtonKey(coreHandle) : "";
    }

    public static String getLocalizableQRCodeErrorMessageKey(QRCodeScanUI qRCodeScanUI) {
        long coreHandle = qRCodeScanUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableQRCodeErrorMessageKey(coreHandle) : "";
    }

    public static String getLocalizableQRCodeMessageKey(QRCodeScanUI qRCodeScanUI) {
        long coreHandle = qRCodeScanUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableQRCodeMessageMessageKey(coreHandle) : "";
    }

    public static String getLocalizableQRCodeTitleKey(QRCodeScanUI qRCodeScanUI) {
        long coreHandle = qRCodeScanUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableQRCodeTitleKey(coreHandle) : "";
    }

    public static String getLocalizableScanQRButtonKey(ActivationTypeSelectionUI activationTypeSelectionUI) {
        long coreHandle = activationTypeSelectionUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableScanQRButtonKey(coreHandle) : "";
    }

    public static String getLocalizableSignInText(ActivationTypeSelectionUI activationTypeSelectionUI) {
        long coreHandle = activationTypeSelectionUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableSignInText(coreHandle) : "";
    }

    public static String getLocalizedCancel() {
        return GDLocalizer.getLocalizedString("Cancel");
    }

    public static String getLocalizedConfirmPasswordKey(SetChangePasswordBaseUI setChangePasswordBaseUI) {
        long coreHandle = setChangePasswordBaseUI.getCoreHandle();
        return coreHandle != 0 ? GDLocalizer.getLocalizedString(_getLocalizableConfirmPasswordKey(coreHandle)) : "";
    }

    public static String getLocalizedError() {
        return GDLocalizer.getLocalizedString("Error");
    }

    public static String getLocalizedLearnMore() {
        return GDLocalizer.getLocalizedString("Learn More");
    }

    public static String getLocalizedNewPasswordKey(SetChangePasswordBaseUI setChangePasswordBaseUI) {
        long coreHandle = setChangePasswordBaseUI.getCoreHandle();
        return coreHandle != 0 ? GDLocalizer.getLocalizedString(_getLocalizableNewPasswordKey(coreHandle)) : "";
    }

    public static String getLocalizedOK() {
        return GDLocalizer.getLocalizedString("OK");
    }

    public static String getLocalizedOldPasswordKey(SetChangePasswordBaseUI setChangePasswordBaseUI) {
        long coreHandle = setChangePasswordBaseUI.getCoreHandle();
        return coreHandle != 0 ? GDLocalizer.getLocalizedString(_getLocalizableOldPasswordKey(coreHandle)) : "";
    }

    public static String getLocalizedPasswordNowRequiredKey(SetChangePasswordBaseUI setChangePasswordBaseUI) {
        long coreHandle = setChangePasswordBaseUI.getCoreHandle();
        return coreHandle != 0 ? GDLocalizer.getLocalizedString(_getLocalizablePasswordNowRequiredKey(coreHandle)) : "";
    }

    public static String getLocalizedPasswordRequirementsKey(SetChangePasswordBaseUI setChangePasswordBaseUI) {
        long coreHandle = setChangePasswordBaseUI.getCoreHandle();
        return coreHandle != 0 ? GDLocalizer.getLocalizedString(_getLocalizablePasswordRequirementsKey(coreHandle)) : "";
    }

    public static String getLocalizableInvalidPinKey(QRCodeScanUI qRCodeScanUI) {
        long coreHandle = qRCodeScanUI.getCoreHandle();
        return coreHandle != 0 ? _getLocalizableInvalidPinKey(coreHandle) : "";
    }
}
