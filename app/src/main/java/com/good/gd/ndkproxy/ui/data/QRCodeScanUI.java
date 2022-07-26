package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.messages.ProvisionMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBDUILocalizationHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIEventBuilder;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDQRCodeScanView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class QRCodeScanUI extends BaseUI {
    private final ProvisionManager provisionManager;
    private String localizableQRCodeTitleKey = BBDUILocalizationHelper.getLocalizableQRCodeTitleKey(this);
    private String localizableQRCodeMessageKey = BBDUILocalizationHelper.getLocalizableQRCodeMessageKey(this);
    private String localizableQRCodeErrorMessageKey = BBDUILocalizationHelper.getLocalizableQRCodeErrorMessageKey(this);

    public QRCodeScanUI(long j, ProvisionManager provisionManager) {
        super(BBUIType.UI_QR_CODE_SCAN, j);
        this.provisionManager = provisionManager;
    }

    private void startProvision(ProvisionMsg provisionMsg) {
        this.provisionManager.startProvisionOverBcp(getCoreHandle(), provisionMsg.email, provisionMsg.pin, provisionMsg.server);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDQRCodeScanView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLocalizedQRCodeErrorMessageText() {
        return GDLocalizer.getLocalizedString(this.localizableQRCodeErrorMessageKey);
    }

    public String getLocalizedQRCodeMessageText() {
        return GDLocalizer.getLocalizedString(this.localizableQRCodeMessageKey);
    }

    public String getLocalizedQRCodeTitleText() {
        return GDLocalizer.getLocalizedString(this.localizableQRCodeTitleKey);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal == 13) {
            CoreUI.closeUI(getCoreHandle());
        } else if (ordinal != 14) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            ProvisionMsg provisionMsg = (ProvisionMsg) obj;
            if (provisionMsg.pin.isEmpty()) {
                GDLog.DBGPRINTF(14, "QRCodeScanUI.onMessage: password is empty");
                CoreUI.requestQRCodeNoPasswordUI();
                CoreUI.requestAdvancedSettingsUI(provisionMsg.email, "", provisionMsg.server);
                return;
            }
            GDLog.DBGPRINTF(14, "QRCodeScanUI.onMessage: start provision");
            startProvision(provisionMsg);
            CoreUI.closeUI(getCoreHandle());
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onStateActive() {
        BBDUIEventManager.sendUpdateEvent(new BBDUIEventBuilder(UIEventType.UI_QR_CODE_PROVISION_UPDATE).build(), this);
    }
}
