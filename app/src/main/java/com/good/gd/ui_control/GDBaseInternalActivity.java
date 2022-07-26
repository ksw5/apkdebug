package com.good.gd.ui_control;

import android.app.Activity;
import android.content.Context;
import com.good.gd.ui.utils.GDInternalThemeHelper;

/* loaded from: classes.dex */
public abstract class GDBaseInternalActivity extends Activity {
    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        try {
            applyOverrideConfiguration(GDInternalThemeHelper.createConfigOverride(context));
        } catch (NoClassDefFoundError e) {
        }
        super.attachBaseContext(context);
    }
}
