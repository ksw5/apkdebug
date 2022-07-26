package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDMTDInsecureWiFiPermissionsBlockedView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.biometric.GDAbstractBiometricHelper;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class MTDInsecureWiFiPermissionsBlockedUI extends BaseUI {
    private String mLocalizedMessage;

    public MTDInsecureWiFiPermissionsBlockedUI(long j, String str) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_PERMISSION_BLOCK, j);
        this.mLocalizedMessage = str;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMTDInsecureWiFiPermissionsBlockedView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLocalizedEnableButtonText() {
        return GDLocalizer.getLocalizedString(GDAbstractBiometricHelper.BUTTON_ENABLE);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedMessage() {
        return this.mLocalizedMessage;
    }
}
