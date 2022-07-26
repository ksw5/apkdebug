package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public abstract class BaseUI extends BBDUIObject {
    public BaseUI(BBUIType bBUIType, long j) {
        super(bBUIType, j);
    }

    /* renamed from: createView */
    public abstract GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer);

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public final boolean isModal() {
        return false;
    }
}
