package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ui.GDEProvisionProgressView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public abstract class ProgressBaseUI extends BaseUI {
    public ProgressBaseUI(BBUIType bBUIType, long j) {
        super(bBUIType, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDEProvisionProgressView(context, viewInteractor, this, viewCustomizer);
    }

    public abstract boolean isMigrationFlow();
}
