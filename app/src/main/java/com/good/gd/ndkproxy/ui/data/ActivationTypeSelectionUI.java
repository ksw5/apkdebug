package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.WebAuth;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.GDActivationTypeSelectionView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class ActivationTypeSelectionUI extends BaseUI {
    private final ProvisionManager provisionManager;
    private final WebAuth webAuth;
    private final String titleKey = BBDUIHelper.getLocalizableTitle(getCoreHandle());
    private final String scanQRButtonKey = BBDUILocalizationHelper.getLocalizableScanQRButtonKey(this);
    private final String noQRCodeTitleKey = BBDUILocalizationHelper.getLocalizableNoQRCodeTitleKey(this);
    private final String signInTextKey = BBDUILocalizationHelper.getLocalizableSignInText(this);
    private final String credentialsTextKey = BBDUILocalizationHelper.getLocalizableCredentialsText(this);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements WebAuth.Listener {
        hbfhc() {
        }

        @Override // com.good.gd.ndkproxy.ui.data.WebAuth.Listener
        public void onError(WebAuth.AuthError authError) {
            GDLog.DBGPRINTF(12, "ActivationTypeSelectionUI: WebAuth: onError - " + authError + "\n");
            CoreUI.requestIDPActivationErrorUI();
            CoreUI.closeActivationWebAuthLoadingUI();
        }

        @Override // com.good.gd.ndkproxy.ui.data.WebAuth.Listener
        public void onResult(String str, String str2) {
            CoreUI.closeActivationWebAuthLoadingUI();
            ActivationTypeSelectionUI.this.provisionManager.startProvisionUsingAuthCode(str, str2);
        }

        @Override // com.good.gd.ndkproxy.ui.data.WebAuth.Listener
        public void onStart() {
            CoreUI.requestActivationWebAuthLoadingUI();
        }
    }

    public ActivationTypeSelectionUI(long j, WebAuth webAuth, ProvisionManager provisionManager) {
        super(BBUIType.UI_ACTIVATION_TYPE_SELECTION, j);
        this.webAuth = webAuth;
        this.provisionManager = provisionManager;
    }

    private void handleButtonQRCodeScan() {
        if (hasCameraHardware()) {
            CoreUI.requestQRCodeScanUI();
            return;
        }
        GDLog.DBGPRINTF(14, "ActivationUI.handleButtonQRCodeScan: device doesn't have a camera \n");
        CoreUI.requestQRCodeNotSupportedUI();
    }

    private void handleCancelAction() {
        long coreHandle = getCoreHandle();
        if (BBDUIHelper.canBeClosed(coreHandle)) {
            CoreUI.closeUI(coreHandle);
        } else {
            BBDUIManager.getInstance().moveTaskToBackground();
        }
    }

    private void handleEnterCredentials() {
        CoreUI.requestActivationUI("", "");
    }

    private void handleSignIn() {
        this.webAuth.start(new hbfhc());
    }

    private boolean hasCameraHardware() {
        return GTBaseContext.getInstance().getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public String getLocalizedCredentialsText() {
        return GDLocalizer.getLocalizedString(this.credentialsTextKey);
    }

    public String getLocalizedNoQRCodeTitleKey() {
        return GDLocalizer.getLocalizedString(this.noQRCodeTitleKey);
    }

    public String getLocalizedScanQRButtonKey() {
        return GDLocalizer.getLocalizedString(this.scanQRButtonKey);
    }

    public String getLocalizedSignInText() {
        return GDLocalizer.getLocalizedString(this.signInTextKey);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedTitle() {
        return GDLocalizer.getLocalizedString(this.titleKey);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        GDLog.DBGPRINTF(16, "ActivationTypeSelectionUI.onMessage IN: " + bBDUIMessageType + "\n");
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal != 13) {
            switch (ordinal) {
                case 5:
                    handleButtonQRCodeScan();
                    return;
                case 6:
                    handleSignIn();
                    return;
                case 7:
                    handleEnterCredentials();
                    return;
                default:
                    super.onMessage(bBDUIMessageType, obj);
                    return;
            }
        }
        handleCancelAction();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDActivationTypeSelectionView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDActivationTypeSelectionView(context, viewInteractor, this, viewCustomizer);
    }
}
