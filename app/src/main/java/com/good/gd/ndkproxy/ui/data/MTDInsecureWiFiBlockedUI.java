package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import android.os.Build;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDMTDInsecureWiFiBlockedView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class MTDInsecureWiFiBlockedUI extends BaseUI {
    private String mLocalizedMessage;

    public MTDInsecureWiFiBlockedUI(long j, String str) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_BLOCK, j);
        this.mLocalizedMessage = str;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMTDInsecureWiFiBlockedView(context, viewInteractor, this, viewCustomizer);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedMessage() {
        return this.mLocalizedMessage;
    }

    public String getLocalizedNetworkSettingsButtonText() {
        if (Build.VERSION.SDK_INT >= 29) {
            return GDLocalizer.getLocalizedString("MTD Insecure WiFi: Permission Warning UI: Network Settings Button");
        }
        return GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.BUTTON_GO_TO_SETTINGS);
    }
}
