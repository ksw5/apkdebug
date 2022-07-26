package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDStartingView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class DynamicsBackgroundUI extends BaseUI {
    private boolean mDynamicsBackgroundEnabled;

    public DynamicsBackgroundUI() {
        this(true);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDStartingView(context, viewInteractor, viewCustomizer, this.mDynamicsBackgroundEnabled);
    }

    public DynamicsBackgroundUI(boolean z) {
        super(BBUIType.UI_STARTING_WINDOW, 0L);
        this.mDynamicsBackgroundEnabled = z;
    }
}
