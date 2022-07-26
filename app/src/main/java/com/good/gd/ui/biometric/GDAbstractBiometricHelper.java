package com.good.gd.ui.biometric;

import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;

/* loaded from: classes.dex */
public abstract class GDAbstractBiometricHelper {
    public static final String BUTTON_CANCEL = "CANCEL";
    public static final String BUTTON_DEVICE_SETTINGS = "deviceSettingsButton";
    public static final String BUTTON_ENABLE = "ENABLE";
    public static final String BUTTON_ENROLL = "ENROLL";
    public static final String BUTTON_GO_TO_SETTINGS = "gotoSettingsbutton";
    public static final String BUTTON_RETRY = "RETRY";
    public static final String BUTTON_SETTINGS = "Settings";
    public static final String BUTTON_USE_PASSWORD = "USE PASSWORD";
    public static final String DIALOG_TEXT_ACTIVATE_FINGERPRINT = "Fingerprint activation dialog message";
    public static final String DIALOG_TEXT_BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = "BiometricErrorSecurity";
    public static final String DIALOG_TEXT_BIOMETRY_ERROR_ACTIVATION = "Biometry is not allowed for activation";
    public static final String DIALOG_TEXT_BIOMETRY_ERROR_UNLOCK = "Biometry is not allowed for unlock";
    public static final String DIALOG_TEXT_ENABLE_SCREENLOCK = "Screen lock is required for Fingerprint";
    public static final String DIALOG_TEXT_ENROLL_FINGERPRINT = "To log in with a fingerprint you must enroll at least one";
    public static final String DIALOG_TEXT_ENROLL_FINGERPRINT_WORK = "To log in with a fingerprint you must enroll at least one in your work profile";
    public static final String DIALOG_TEXT_FINGERPRINT_AUTHENTICATED = "Fingerprint recognized";
    public static final String DIALOG_TEXT_FINGERPRINT_CHANGED = "Fingerprint requires password due settings changes";
    public static final String DIALOG_TEXT_FINGERPRINT_EXPIRED = "Fingerprint requires password after some period";
    public static final String DIALOG_TEXT_FINGERPRINT_NOT_ENROLLED_ONE = "Fingerprint is not recognized dialog message";
    public static final String DIALOG_TEXT_FINGERPRINT_TIMED_OUT = "Fingerprint time out dialog message";
    public static final String DIALOG_TEXT_FINGERPRINT_TOO_MANY_ATTEMPTS = "Fingerprint is not recognized too many times";
    public static final String DIALOG_TEXT_FINGERPRINT_TOUCH_SENSOR = "Touch sensor";
    public static final String DIALOG_TEXT_INTERNAL_ERROR = "Fingerprint authentication failed";
    public static final String DIALOG_TEXT_INTERNAL_ERROR_RETRY = "Internal error: Retry or restart";
    public static final String DIALOG_TEXT_LOGIN = "Confirm fingerprint to continue";
    public static final String DIALOG_TITLE_ACTIVATE_FINGERPRINT = "Activate fingerprint";
    public static final String DIALOG_TITLE_AUTHENTICATION_FAILED = "Authentication Failed";
    public static final String DIALOG_TITLE_AUTHENTICATION_TIMED_OUT = "Authentication Timed Out";
    public static final String DIALOG_TITLE_BIOMETRY_ERROR_ACTIVATION = "Activation failure";
    public static final String DIALOG_TITLE_CRYPTO_FAILURE = "Crypto Failure";
    public static final String DIALOG_TITLE_ENABLE_SCREENLOCK = "No Screen Lock";
    public static final String DIALOG_TITLE_ENROLL_FINGERPRINT = "No Fingerprints";
    public static final String DIALOG_TITLE_ENROLL_FINGERPRINT_WORK = "No Fingerprints in Work Profile";
    public static final String DIALOG_TITLE_FINGERPRINT_CHANGED = "Fingerprints Changed";
    public static final String DIALOG_TITLE_FINGERPRINT_EXPIRED = "Fingerprint Expired";
    public static final String DIALOG_TITLE_LOGIN = "Login";
    public static final long ERROR_DELAY = 1500;
    public static final long SUCCESS_DELAY = 1000;

    public static DialogInterface createDialogInterface() {
        int uiUnlockType = GDFingerprintAuthenticationManager.getInstance().getAuthenticationHandler().getUiUnlockType();
        if (uiUnlockType != 1) {
            if (uiUnlockType != 2) {
                return new FingerprintAuthentication();
            }
            return new BiometricAuthentication();
        }
        return new FingerprintAuthentication();
    }
}
