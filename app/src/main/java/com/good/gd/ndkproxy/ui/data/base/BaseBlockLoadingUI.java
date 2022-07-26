package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ui.GDBlockedLoadingView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class BaseBlockLoadingUI extends BaseUI {
    public BaseBlockLoadingUI(BBUIType bBUIType, long j) {
        super(bBUIType, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDBlockedLoadingView(context, viewInteractor, this, viewCustomizer);
    }
}
