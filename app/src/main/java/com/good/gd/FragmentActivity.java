package com.good.gd;

import android.os.Bundle;
import com.good.gd.ui_control.GDMonitorActivityImpl;

/* loaded from: classes.dex */
public class FragmentActivity extends androidx.fragment.app.FragmentActivity {
    private GDMonitorActivityImpl mActivityImpl;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GDMonitorActivityImpl gDMonitorActivityImpl = new GDMonitorActivityImpl(this);
        this.mActivityImpl = gDMonitorActivityImpl;
        gDMonitorActivityImpl.ActivityImpl_onCreate(bundle);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mActivityImpl.ActivityImpl_onDestroy();
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mActivityImpl.ActivityImpl_onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.mActivityImpl.ActivityImpl_onResume();
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.mActivityImpl.ActivityImpl_onStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        this.mActivityImpl.ActivityImpl_onStop();
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        this.mActivityImpl.ActivityImpl_onUserInteraction();
        super.onUserInteraction();
    }
}
