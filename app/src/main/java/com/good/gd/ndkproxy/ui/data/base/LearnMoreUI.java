package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ui.GDLearnMoreView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class LearnMoreUI extends BaseUI {
    public LearnMoreUI(long j) {
        super(BBUIType.UI_LEARN_MORE, j);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public final GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDLearnMoreView(context, viewInteractor, this, viewCustomizer);
    }

    public String getLearnMoreDetailsTextKey() {
        return BBDUIHelper.getLocalizableMessage(getCoreHandle());
    }

    public String getLearnMoreTitleTextKey() {
        return BBDUIHelper.getLocalizableTitle(getCoreHandle());
    }
}
