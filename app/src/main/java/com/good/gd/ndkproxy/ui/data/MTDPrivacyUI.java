package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDMTDPrivacyUIView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class MTDPrivacyUI extends BaseUI {
    private final ClientDefinedStrings messageHolder;

    public MTDPrivacyUI(long j, ClientDefinedStrings clientDefinedStrings) {
        super(BBUIType.UI_MTD_PRIVACY, j);
        this.messageHolder = clientDefinedStrings;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMTDPrivacyUIView(context, viewInteractor, this, viewCustomizer);
    }

    public String getParsedDisclaimerMessage() {
        return this.messageHolder.getParsedDisclaimerMessage();
    }
}
