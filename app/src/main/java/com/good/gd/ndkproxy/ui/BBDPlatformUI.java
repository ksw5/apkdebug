package com.good.gd.ndkproxy.ui;

import com.good.gd.apache.http.protocol.HTTP;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.auth.GDFingerprintAuthenticationManager;
import com.good.gd.ndkproxy.enterprise.GDEPasswordChanger;
import com.good.gd.ndkproxy.enterprise.GDEPasswordUnlock;
import com.good.gd.ndkproxy.enterprise.GDEProvisionManager;
import com.good.gd.ndkproxy.enterprise.GDEProvisionUtil;
import com.good.gd.ndkproxy.icc.AuthDelegationProvider;
import com.good.gd.ndkproxy.native2javabridges.ui.AlertButtons;
import com.good.gd.ndkproxy.ui.GDPKCSPassword;
import com.good.gd.ndkproxy.ui.data.ActivateFingerprintUI;
import com.good.gd.ndkproxy.ui.data.ActivateFingerprintWithConfirmationUI;
import com.good.gd.ndkproxy.ui.data.ActivationProgressUI;
import com.good.gd.ndkproxy.ui.data.ActivationTypeSelectionUI;
import com.good.gd.ndkproxy.ui.data.ActivationUI;
import com.good.gd.ndkproxy.ui.data.ActivationUnlockBiometricUI;
import com.good.gd.ndkproxy.ui.data.ActivationUnlockUI;
import com.good.gd.ndkproxy.ui.data.AuthDelegationBlockedUI;
import com.good.gd.ndkproxy.ui.data.AuthenticatingUI;
import com.good.gd.ndkproxy.ui.data.BlockedUI;
import com.good.gd.ndkproxy.ui.data.CertificateImportUI;
import com.good.gd.ndkproxy.ui.data.CertificateSharingUI;
import com.good.gd.ndkproxy.ui.data.ChangePasswordEnforcedUI;
import com.good.gd.ndkproxy.ui.data.ChangePasswordOptionalUI;
import com.good.gd.ndkproxy.ui.data.CloseBiometryDialogCheckerSystemBroadcastReceiver;
import com.good.gd.ndkproxy.ui.data.ContainerWipedUI;
import com.good.gd.ndkproxy.ui.data.DisclaimerUI;
import com.good.gd.ndkproxy.ui.data.EasyActivationSelectionUI;
import com.good.gd.ndkproxy.ui.data.GDPermissionAlert;
import com.good.gd.ndkproxy.ui.data.LoadingUI;
import com.good.gd.ndkproxy.ui.data.LocationServicesDisabledAlert;
import com.good.gd.ndkproxy.ui.data.LogUploadUI;
import com.good.gd.ndkproxy.ui.data.MDMBlockedUI;
import com.good.gd.ndkproxy.ui.data.MTDBlockedUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiBlockedUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiPermissionUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiPermissionsBlockedUI;
import com.good.gd.ndkproxy.ui.data.MTDInsecureWiFiWarningAlert;
import com.good.gd.ndkproxy.ui.data.MTDPrivacyUI;
import com.good.gd.ndkproxy.ui.data.MigrationProgressUI;
import com.good.gd.ndkproxy.ui.data.NocSelectionUI;
import com.good.gd.ndkproxy.ui.data.PrivacySplashUI;
import com.good.gd.ndkproxy.ui.data.QRCodeScanUI;
import com.good.gd.ndkproxy.ui.data.ReauthenticationUnlockBiometricUI;
import com.good.gd.ndkproxy.ui.data.ReauthenticationUnlockUI;
import com.good.gd.ndkproxy.ui.data.RetrievingAccessKeyUI;
import com.good.gd.ndkproxy.ui.data.SISLearnMoreUI;
import com.good.gd.ndkproxy.ui.data.SISPrimerUI;
import com.good.gd.ndkproxy.ui.data.SISSettingsUI;
import com.good.gd.ndkproxy.ui.data.SetPasswordUI;
import com.good.gd.ndkproxy.ui.data.SigningCertificateRequestUI;
import com.good.gd.ndkproxy.ui.data.TrustedAuthenticationPlaceholder;
import com.good.gd.ndkproxy.ui.data.UnlockBiometricUI;
import com.good.gd.ndkproxy.ui.data.UnlockUI;
import com.good.gd.ndkproxy.ui.data.WebAuthLoadingUI;
import com.good.gd.ndkproxy.ui.data.WebAuthUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BBDUIState;
import com.good.gd.ndkproxy.ui.data.base.LearnMoreUI;
import com.good.gd.ndkproxy.ui.data.dialog.AlertDialogUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.log_upload.GDLogUploadStateChangesReceiver;
import com.good.gd.ui.utils.permissions.ButtonClickListener;
import com.good.gd.utils.GDApplicationInfoProvider;
import com.good.gd.utils.GDApplicationLifecycleListener;
import com.good.gd.utils.GDClientDefinedStrings;
import com.good.gd.utils.GDUserChecker;
import com.good.gd.webauth.GDWebAuth;
import com.good.gd.webauth.WeAuthUICallbackHolder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class BBDPlatformUI {
    private static boolean createActivateFingerprintUI(long j, boolean z) {
        GDUserChecker gDUserChecker = new GDUserChecker();
        GDContext.getInstance();
        GDFingerprintAuthenticationManager gDFingerprintAuthenticationManager = GDFingerprintAuthenticationManager.getInstance();
        if (z) {
            GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivateFingerprintWithConfirmationUI()\n");
            BBDUIDataStore.getInstance().addUIData(new ActivateFingerprintWithConfirmationUI(j, gDUserChecker, gDFingerprintAuthenticationManager));
            return true;
        }
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivateFingerprintUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivateFingerprintUI(j, gDUserChecker, gDFingerprintAuthenticationManager));
        return true;
    }

    private static boolean createActivationProgressUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivationProgressUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationProgressUI(j));
        return true;
    }

    public static boolean createActivationTypeSelectionUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivationTypeSelectionUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationTypeSelectionUI(j, new GDWebAuth(new GDEProvisionUtil().getCurrentEidConfig()), GDEProvisionManager.getInstance()));
        return true;
    }

    public static boolean createActivationUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivationUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationUI(j, GDEProvisionManager.getInstance(), BBUIType.UI_PROVISION));
        return true;
    }

    public static boolean createActivationWebAuthLoadingUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivationWebAuthLoadingUI()\n");
        BBDUIDataStore.getInstance().addUIData(new WebAuthLoadingUI(j));
        return true;
    }

    public static boolean createActivationWebAuthUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createActivationWebAuthUI()\n");
        BBDUIDataStore.getInstance().addUIData(new WebAuthUI(j, str, str2, WeAuthUICallbackHolder.getUrlCallback()));
        return true;
    }

    public static boolean createAdvancedSettingsUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createAdvancedSettingsUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationUI(j, GDEProvisionManager.getInstance(), BBUIType.UI_ADVANCED_SETTINGS));
        return true;
    }

    private static boolean createAlertDialogUI(long j, boolean z, int i, Object obj) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createAlertDialogUI()\n");
        BBDUIDataStore.getInstance().addUIData(new AlertDialogUI(j, z, i, (AlertButtons) obj, new hbfhc()));
        return true;
    }

    private static boolean createAuthDelegationBlockedUI(boolean z, String str, int i, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createAuthDelegationBlockedUI(willUpdate = " + z + ", bundleId = " + str + ", failureReason = " + i + ")\n");
        BBDUIDataStore.getInstance().addUIData(new AuthDelegationBlockedUI(j, z, str, i, new GDBlock(), new GDApplicationInfoProvider()));
        return true;
    }

    private static boolean createAuthenticatingUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createAuthenticatingUI()\n");
        BBDUIDataStore.getInstance().addUIData(new AuthenticatingUI(j));
        return true;
    }

    private static boolean createBBDLocationServicesDisabledAlertUI(long j, String str, String str2) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBBDLocationServicesDisabledAlertUI()\n");
        BBDUIDataStore.getInstance().addUIData(new LocationServicesDisabledAlert(j, str, str2));
        return true;
    }

    private static boolean createBISLearnMoreUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBISLearnMoreUI()\n");
        BBDUIDataStore.getInstance().addUIData(new SISLearnMoreUI(j));
        return true;
    }

    private static boolean createBISPrimerUI(long j, int i) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBISPrimerUI()\n");
        BBDUIDataStore.getInstance().addUIData(new SISPrimerUI(j, i));
        return true;
    }

    private static boolean createBISSettingsUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBISSettingsUI()\n");
        BBDUIDataStore.getInstance().addUIData(new SISSettingsUI(j));
        return true;
    }

    public static boolean createBasicSettingsUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBasicSettingsUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationUI(j, GDEProvisionManager.getInstance(), BBUIType.UI_BASIC_SETTINGS));
        return true;
    }

    private static boolean createBiometricUnlockUI(long j) {
        GDLog.DBGPRINTF(16, "GDLibraryUI.createBiometricUnlockUI()\n");
        BBDUIDataStore.getInstance().addUIData(new UnlockBiometricUI(j, GDEPasswordUnlock.getInstance(), GDFingerprintAuthenticationManager.getInstance(), new CloseBiometryDialogCheckerSystemBroadcastReceiver()));
        return true;
    }

    private static boolean createBlockedUI(int i, String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createBlockedUI(title = " + str + ", body = " + str2 + ")\n");
        BBDUIDataStore.getInstance().addUIData(new BlockedUI(i, str, str2, j, new GDBlock(), new GDApplicationInfoProvider(), new GDClientDefinedStrings()));
        return true;
    }

    private static boolean createCertificateImportUI(int i, String str, byte[] bArr, boolean z, boolean z2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createCertificateImportUI()\n");
        try {
            BBDUIDataStore.getInstance().addUIData(new CertificateImportUI(j, GDPKCSPassword.StateType.fromNativeInteger(i), str, new String(bArr, HTTP.UTF_8), z, z2));
            return true;
        } catch (UnsupportedEncodingException e) {
            GDLog.DBGPRINTF(12, "GDLibraryUI.createCertificateImportUI() : Certificate with unsupported encoding\n");
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createCertificateSharingUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createCertificateSharingUI()\n");
        BBDUIDataStore.getInstance().addUIData(new CertificateSharingUI(j, str, str2));
        return true;
    }

    private static boolean createChangePasswordEnforcedUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createChangePasswordEnforcedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ChangePasswordEnforcedUI(j, GDEPasswordChanger.getInstance(), GDEPasswordChanger.getInstance()));
        return true;
    }

    private static boolean createChangePasswordOptionalUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createChangePasswordOptionalUI \n");
        BBDUIDataStore.getInstance().addUIData(new ChangePasswordOptionalUI(j, GDEPasswordChanger.getInstance(), GDEPasswordChanger.getInstance()));
        return true;
    }

    private static boolean createContainerWipedUI(int i, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createContainerWipedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ContainerWipedUI(j, i, new GDBlock(), new GDApplicationInfoProvider(), new GDClientDefinedStrings()));
        return true;
    }

    private static boolean createDisclaimerUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createDisclaimerUI()\n");
        BBDUIDataStore.getInstance().addUIData(new DisclaimerUI(j, new GDClientDefinedStrings()));
        return true;
    }

    private static boolean createEasyActivationBiometricUnlockUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createEasyActivationBiometricUnlockUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationUnlockBiometricUI(j, GDEPasswordUnlock.getInstance(), GDFingerprintAuthenticationManager.getInstance(), new CloseBiometryDialogCheckerSystemBroadcastReceiver()));
        return true;
    }

    private static boolean createEasyActivationSelectionUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createEasyActivationSelectionUI()\n");
        BBDUIDataStore.getInstance().addUIData(new EasyActivationSelectionUI(j, GDEProvisionManager.getInstance()));
        return true;
    }

    private static boolean createEasyActivationUnlockUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createEasyActivationUnlockUI()\n");
        GDContext gDContext = GDContext.getInstance();
        GDApplicationLifecycleListener gDApplicationLifecycleListener = new GDApplicationLifecycleListener();
        BBDUIDataStore.getInstance().addUIData(new ActivationUnlockUI(j, str, str2, false, GDEPasswordUnlock.getInstance(), gDContext, gDApplicationLifecycleListener));
        return true;
    }

    private static boolean createLearnMoreUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createLearnMoreUI()\n");
        BBDUIDataStore.getInstance().addUIData(new LearnMoreUI(j));
        return true;
    }

    private static boolean createLoadingUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createLoadingUI(" + j + ")\n");
        BBDUIDataStore.getInstance().addUIData(new LoadingUI(j));
        return true;
    }

    private static boolean createLogUploadUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createLogUploadUI()\n");
        GDLogUploadStateChangesReceiver gDLogUploadStateChangesReceiver = new GDLogUploadStateChangesReceiver();
        BBDUIDataStore.getInstance().addUIData(new LogUploadUI(j, gDLogUploadStateChangesReceiver, gDLogUploadStateChangesReceiver.getCurrentLogUploadNetworkState()));
        return true;
    }

    private static boolean createMDMBlockedUI(int i, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMDMBlockedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MDMBlockedUI(j, i, new GDBlock(), new GDApplicationInfoProvider()));
        return true;
    }

    private static boolean createMTDBlockedUI(long j, String[] strArr) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDBlockedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDBlockedUI(j, strArr));
        AuthDelegationProvider.getInstance().sendComplianceActionBroadCast();
        return true;
    }

    private static boolean createMTDDisclaimerUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDDisclaimerUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDPrivacyUI(j, new GDClientDefinedStrings()));
        return true;
    }

    private static boolean createMTDInsecureWiFiBlockedUI(long j, String str) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDInsecureWiFiBlockedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDInsecureWiFiBlockedUI(j, str));
        return true;
    }

    private static boolean createMTDInsecureWiFiPermissionUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDInsecureWiFiPermissionUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDInsecureWiFiPermissionUI(j));
        return true;
    }

    private static boolean createMTDInsecureWiFiPermissionsBlockedUI(long j, String str) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDInsecureWiFiPermissionsBlockedUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDInsecureWiFiPermissionsBlockedUI(j, str));
        return true;
    }

    private static boolean createMTDInsecureWiFiWarningAlertUI(long j, String str, String str2) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMTDInsecureWiFiWarningAlertUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MTDInsecureWiFiWarningAlert(j, str, str2));
        return true;
    }

    private static boolean createMigrationUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createMigrationUI()\n");
        BBDUIDataStore.getInstance().addUIData(new MigrationProgressUI(j));
        return true;
    }

    private static boolean createNocSelectionUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createNocSelectionUI()\n");
        BBDUIDataStore.getInstance().addUIData(new NocSelectionUI(j, new GDEProvisionUtil()));
        return true;
    }

    private static boolean createPermissionAlertUI(long j, String str, String str2, Object obj) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createPermissionAlertUI()\n");
        BBDUIDataStore.getInstance().addUIData(new GDPermissionAlert(j, str, str2, (ButtonClickListener) obj));
        return true;
    }

    private static boolean createPrivacySplashUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createPrivacySplashUI(" + j + ")\n");
        BBDUIDataStore.getInstance().addUIData(new PrivacySplashUI(j));
        return true;
    }

    private static boolean createQRCodeScanUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createQRCodeScanUI()\n");
        BBDUIDataStore.getInstance().addUIData(new QRCodeScanUI(j, GDEProvisionManager.getInstance()));
        return true;
    }

    private static boolean createReauthenticationBiometricUnlockUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createReauthenticationBiometricUnlockUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ReauthenticationUnlockBiometricUI(j, str, str2, GDEPasswordUnlock.getInstance(), GDFingerprintAuthenticationManager.getInstance(), new CloseBiometryDialogCheckerSystemBroadcastReceiver()));
        return true;
    }

    private static boolean createReauthenticationUnlockUI(String str, String str2, boolean z, long j, long j2, long j3) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createReauthenticationUnlockUI()\n");
        GDContext gDContext = GDContext.getInstance();
        GDApplicationLifecycleListener gDApplicationLifecycleListener = new GDApplicationLifecycleListener();
        BBDUIDataStore.getInstance().addUIData(new ReauthenticationUnlockUI(j3, str, str2, z, j, j2, gDContext, GDEPasswordUnlock.getInstance(), gDApplicationLifecycleListener));
        return true;
    }

    private static boolean createRetrievingAccessKeyUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createRetrievingAccessKeyUI()\n");
        BBDUIDataStore.getInstance().addUIData(new RetrievingAccessKeyUI(j));
        return true;
    }

    private static boolean createSetPasswordUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createSetPasswordUI\n");
        BBDUIDataStore.getInstance().addUIData(new SetPasswordUI(j, GDEPasswordChanger.getInstance(), GDEPasswordChanger.getInstance()));
        return true;
    }

    private static boolean createSigningCertificateRequesstUnlockUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createSigningCertificateRequesstUnlockUI()\n");
        GDContext gDContext = GDContext.getInstance();
        GDApplicationLifecycleListener gDApplicationLifecycleListener = new GDApplicationLifecycleListener();
        BBDUIDataStore.getInstance().addUIData(new ActivationUnlockUI(j, str, str2, true, GDEPasswordUnlock.getInstance(), gDContext, gDApplicationLifecycleListener));
        return true;
    }

    private static boolean createSigningCertificateRequestBiometricUnlockUI(String str, String str2, long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createSigningCertificateRequestBiometricUnlockUI()\n");
        BBDUIDataStore.getInstance().addUIData(new ActivationUnlockBiometricUI(j, GDEPasswordUnlock.getInstance(), GDFingerprintAuthenticationManager.getInstance(), new CloseBiometryDialogCheckerSystemBroadcastReceiver()));
        return true;
    }

    private static boolean createSigningCertificateUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createSigningCertificateUI()\n");
        BBDUIDataStore.getInstance().addUIData(new SigningCertificateRequestUI(j));
        return true;
    }

    private static boolean createTrustedAuthenticationUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createTrustedAuthenticationUI(" + j + ")\n");
        BBDUIDataStore.getInstance().addUIData(new TrustedAuthenticationPlaceholder(j));
        return true;
    }

    private static boolean createUnlockUI(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.createUnlockUI()\n");
        BBDUIDataStore.getInstance().addUIData(new UnlockUI(j, GDEPasswordUnlock.getInstance()));
        return true;
    }

    private static void destroy(long j) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.destroy: " + getUIDataInfo(j) + "\n");
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(j);
        if (uIData != null) {
            uIData.onStateChanged(BBDUIState.STATE_PAUSED);
            uIData.onStateChanged(BBDUIState.STATE_DESTROYED);
        }
        BBDUIDataStore.getInstance().removeUIData(j);
    }

    private static String getUIDataInfo(long j) {
        return String.format("%s:%d %s", BBDUIHelper.getUIName(j), Integer.valueOf(BBDUIHelper.getUIPriority(j)), BBDUIHelper.toHex(j));
    }

    private static boolean sendUpdateEventToUI(long j) {
        BBDUIObject currentUI = BBDUIManager.getInstance().getCurrentUI();
        boolean z = currentUI != null && currentUI.getCoreHandle() == j;
        if (z) {
            BBDUIEventManager.sendMessage(currentUI, BBDUIMessageType.MSG_UI_CORE_UPDATE);
        }
        GDLog.DBGPRINTF(16, "BBDPlatformUI.sendUpdateEventToUI() " + z + "\n");
        return z;
    }

    private static void transitionState(long[] jArr, long[] jArr2) {
        GDLog.DBGPRINTF(16, "BBDPlatformUI.transitionState IN previousUIState.length=" + jArr.length + " newUIState.length=" + jArr2.length + "\n");
        int length = jArr2.length;
        for (int i = 0; i < length; i++) {
            GDLog.DBGPRINTF(16, "BBDPlatformUI.newUIState= " + getUIDataInfo(jArr2[i]) + "\n");
        }
        ArrayList arrayList = null;
        if (jArr2.length != 0) {
            GDLog.DBGPRINTF(16, "BBDPlatformUI.transitionState: UIs to show: " + jArr2.length + "\n");
            arrayList = new ArrayList();
            for (long j : jArr2) {
                BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(j);
                if (uIData != null) {
                    if (!uIData.isPlaceholder()) {
                        arrayList.add(uIData);
                    } else {
                        GDLog.DBGPRINTF(16, "BBDPlatformUI.transitionState skip UI\n");
                    }
                }
            }
        }
        BBDUIManager.getInstance().processUIs(arrayList);
        GDLog.DBGPRINTF(16, "BBDPlatformUI.transitionState OUT\n");
    }
}
