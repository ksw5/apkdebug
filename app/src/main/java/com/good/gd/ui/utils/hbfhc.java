package com.good.gd.ui.utils;

import android.content.Context;
import android.view.ContextThemeWrapper;

/* loaded from: classes.dex */
class hbfhc extends ContextThemeWrapper {
    public hbfhc(Context context, int i) {
        super(context, i);
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        try {
            applyOverrideConfiguration(GDInternalThemeHelper.createConfigOverride(context));
        } catch (NoClassDefFoundError e) {
        }
        super.attachBaseContext(context);
    }
}
