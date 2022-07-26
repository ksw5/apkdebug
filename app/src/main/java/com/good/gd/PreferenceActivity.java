package com.good.gd;

import android.os.Bundle;
import com.good.gd.ui_control.GDMonitorActivityImpl;

/* loaded from: classes.dex */
public class PreferenceActivity extends android.preference.PreferenceActivity {
    private GDMonitorActivityImpl mActivityImpl;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GDMonitorActivityImpl gDMonitorActivityImpl = new GDMonitorActivityImpl(this);
        this.mActivityImpl = gDMonitorActivityImpl;
        gDMonitorActivityImpl.ActivityImpl_onCreate(bundle);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.mActivityImpl.ActivityImpl_onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mActivityImpl.ActivityImpl_onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        this.mActivityImpl.ActivityImpl_onResume();
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mActivityImpl.ActivityImpl_onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mActivityImpl.ActivityImpl_onStop();
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        this.mActivityImpl.ActivityImpl_onUserInteraction();
        super.onUserInteraction();
    }
}
