package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import android.text.TextUtils;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDEProvisionView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class ActivationUI extends BaseUI {
    private final ProvisionManager provisionManager;
    private String mEmail = "";
    private String mPin = "";
    private String mBcpUrl = "";
    private String mNonce = "";
    private String mLocalizableEmailKey = BBDUILocalizationHelper.getLocalizableEmailAddressKey(this);
    private String mLocalizablePinHintKey = BBDUILocalizationHelper.getLocalizablePinHintKey(this);
    private String mLocalizableBCPUrlKey = BBDUILocalizationHelper.getLocalizableBCPUrlKey(this);
    private String mLocalizableProvisionInfoMessageKey = BBDUILocalizationHelper.getLocalizableProvisionInfoMessageKey(this);
    private String mLocalizablePasswordEyeKey = BBDUILocalizationHelper.getLocalizablePasswordEyeKey(this);
    private String mEnterCredentialsTextKey = BBDUILocalizationHelper.getEnterCredentialsTextKey(this);
    private String mQRCodeButtonTextKey = BBDUILocalizationHelper.getLocalizableQRCodeButtonKey(this);

    public ActivationUI(long j, ProvisionManager provisionManager, BBUIType bBUIType) {
        super(bBUIType, j);
        this.provisionManager = provisionManager;
    }

    private void handleBottomButtonClick() {
        if (getBBDUIType() == BBUIType.UI_ADVANCED_SETTINGS) {
            CoreUI.closeUI(getCoreHandle());
            CoreUI.requestActivationUI(getEmail(), getPin());
        } else if (getBBDUIType() == BBUIType.UI_BASIC_SETTINGS) {
            CoreUI.requestAdvancedSettingsUI(getEmail(), getPin(), "");
        } else {
            bottomButton();
        }
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
        if (!hasCancelButton() && isLockFlow()) {
            BBDUIManager.getInstance().moveTaskToBackground();
            return;
        }
        resetUpdateData();
        BBDUIHelper.cancel(getCoreHandle());
    }

    private boolean hasCameraHardware() {
        return GTBaseContext.getInstance().getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    private void startProvision(ProvisionMsg provisionMsg) {
        if (TextUtils.isEmpty(provisionMsg.server)) {
            resetUpdateData();
            if (this.provisionManager.startProvision(getCoreHandle(), provisionMsg.email, provisionMsg.pin).isSuccess()) {
                resetUpdateData();
                setProvisionFields(provisionMsg.email, provisionMsg.pin, "");
                return;
            }
            BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_PROVISION_UPDATE).successful(false).addTitle(BBDUILocalizationHelper.getLocalizedError()).addText(GDLocalizer.getLocalizedString(BBDUILocalizationHelper.getLocalizableInvalidPinKey(this))).addAcknowledge(null).build(), this);
            return;
        }
        this.provisionManager.startProvisionOverBcp(getCoreHandle(), provisionMsg.email, provisionMsg.pin, provisionMsg.server);
        setProvisionFields(provisionMsg.email, "", "");
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDEProvisionView(context, viewInteractor, this, viewCustomizer);
    }

    public String getBcpUrl() {
        return this.mBcpUrl;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getEnteredBcpUrl() {
        return BBDUIHelper.getEnteredBcpUrl(getCoreHandle());
    }

    public String getEnteredEmail() {
        if (getBBDUIType() == BBUIType.UI_ADVANCED_SETTINGS) {
            return BBDUIHelper.getEnteredEmail(getCoreHandle());
        }
        return getBBDUIType() == BBUIType.UI_BASIC_SETTINGS ? BBDUIHelper.getEnteredActivationEmail(getCoreHandle()) : "";
    }

    public String getEnteredPassword() {
        if (getBBDUIType() == BBUIType.UI_ADVANCED_SETTINGS) {
            return BBDUIHelper.getEnteredPassword(getCoreHandle());
        }
        return getBBDUIType() == BBUIType.UI_BASIC_SETTINGS ? BBDUIHelper.getEnteredActivationPassword(getCoreHandle()) : "";
    }

    public String getLocalizedBCPUrlText() {
        return GDLocalizer.getLocalizedString(this.mLocalizableBCPUrlKey);
    }

    public String getLocalizedEmailText() {
        return GDLocalizer.getLocalizedString(this.mLocalizableEmailKey);
    }

    public String getLocalizedEnterCredentialsText() {
        return GDLocalizer.getLocalizedString(this.mEnterCredentialsTextKey);
    }

    public String getLocalizedPasswordEyeText() {
        return GDLocalizer.getLocalizedString(this.mLocalizablePasswordEyeKey);
    }

    public String getLocalizedPinHintText() {
        return GDLocalizer.getLocalizedString(this.mLocalizablePinHintKey);
    }

    public String getLocalizedProvisionInfoMessageText() {
        return GDLocalizer.getLocalizedString(this.mLocalizableProvisionInfoMessageKey);
    }

    public String getLocalizedQRCodeButtonText() {
        return GDLocalizer.getLocalizedString(this.mQRCodeButtonTextKey);
    }

    public String getNonce() {
        return this.mNonce;
    }

    public String getPin() {
        return this.mPin;
    }

    public boolean hasBCPURLField() {
        return BBDUIHelper.hasBCPURLField(getCoreHandle());
    }

    public boolean hasQRCodeOkButton() {
        return BBDUIHelper.hasQRCodeOkButton(getCoreHandle());
    }

    public boolean isLockFlow() {
        return BBDUIHelper.isRemoteLockFlow(getCoreHandle());
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        GDLog.DBGPRINTF(16, "ActivationUI.onMessage IN: " + bBDUIMessageType + "\n");
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal == 0) {
            startProvision((ProvisionMsg) obj);
        } else if (ordinal == 5) {
            handleButtonQRCodeScan();
        } else if (ordinal == 12) {
            handleBottomButtonClick();
        } else if (ordinal != 13) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            handleCancelAction();
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_PROVISION_ENABLE_CONTROLS).build(), this);
    }

    public void setProvisionFields(String str, String str2, String str3) {
        this.mEmail = str;
        this.mPin = str2;
        this.mBcpUrl = str3;
    }

    public void setProvisionFields(String str, String str2) {
        this.mPin = str;
        this.mBcpUrl = str2;
    }
}
