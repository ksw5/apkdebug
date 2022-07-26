package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDMTDInsecureWiFiPermissionsView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class MTDInsecureWiFiPermissionUI extends BaseUI {
    private long uiData;

    public MTDInsecureWiFiPermissionUI(long j) {
        super(BBUIType.UI_MTD_INSECURE_WIFI_PERMISSION, j);
        this.uiData = j;
    }

    private static native void _onUserAction(long j);

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMTDInsecureWiFiPermissionsView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLocalizedContinueButtonText() {
        return GDLocalizer.getLocalizedString("Continue");
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public String getLocalizedTitle() {
        return "BlackBerry Protect";
    }

    public void onUserAction() {
        _onUserAction(this.uiData);
    }
}
