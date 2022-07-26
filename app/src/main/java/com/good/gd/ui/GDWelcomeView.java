package com.good.gd.ui;

import android.content.Context;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;

/* loaded from: classes.dex */
public final class GDWelcomeView extends GDStartingView {
    private final BBDUIObject uiData;

    public GDWelcomeView(Context context, ViewInteractor viewInteractor, BBDUIObject bBDUIObject, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.uiData = bBDUIObject;
    }
}
