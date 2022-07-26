package com.good.gd.ui_control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.good.gd.client.GDClient;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.GDActivityInfo;
import com.good.gd.utils.IccIntentUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class GDMonitorActivityImpl implements GDMonitorFragmentImplInterface {
    private Bundle mArgs;
    private WeakReference<Activity> mContainerActivityWeakRef;
    private GDMonitorFragmentImpl mFragmentImpl;

    public GDMonitorActivityImpl(Activity activity) {
        this.mContainerActivityWeakRef = new WeakReference<>(activity);
    }

    public void ActivityImpl_onActivityResult(int i, int i2, Intent intent) {
        this.mFragmentImpl.onFragmentActivityResult(i, i2, intent);
    }

    public void ActivityImpl_onCreate(Bundle bundle) {
        ActivityImpl_onCreate(bundle, false);
    }

    public void ActivityImpl_onDestroy() {
        this.mFragmentImpl.onFragmentDestroy();
    }

    public void ActivityImpl_onPause() {
        this.mFragmentImpl.onFragmentPause();
    }

    public void ActivityImpl_onResume() {
        this.mFragmentImpl.onFragmentResume();
    }

    public void ActivityImpl_onStart() {
        this.mFragmentImpl.onFragmentStart();
    }

    public void ActivityImpl_onStop() {
        this.mFragmentImpl.onFragmentStop();
    }

    public void ActivityImpl_onUserInteraction() {
        this.mFragmentImpl.onUserInteraction();
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public void fragmentStartActivityForResult(Intent intent, int i) {
        Activity activity = this.mContainerActivityWeakRef.get();
        if (activity != null) {
            activity.startActivityForResult(intent, i);
        }
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public Activity getContainerActivity() {
        return this.mContainerActivityWeakRef.get();
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public Bundle getFragmentArguments() {
        return this.mArgs;
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public void setFragmentRetainInstance(boolean z) {
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public boolean shouldIgnoreDetach() {
        return true;
    }

    public boolean ActivityImpl_onCreate(Bundle bundle, boolean z) {
        Activity activity = this.mContainerActivityWeakRef.get();
        if (activity == null) {
            return false;
        }
        GDContext.getInstance().setContext(activity.getApplicationContext());
        GDClient.getInstance().initForeground(new GDActivityInfo(activity));
        GDLog.DBGPRINTF(14, "GDMonitorActivityImpl::onCreate() --" + activity.getLocalClassName() + "\n");
        boolean checkForIccIntent = IccIntentUtils.checkForIccIntent(activity.getIntent());
        if (checkForIccIntent) {
            IccIntentUtils.processIccIntent(activity.getIntent(), activity);
        }
        Bundle bundle2 = new Bundle();
        this.mArgs = bundle2;
        bundle2.putBoolean("shouldCallAuth", z);
        this.mArgs.putBoolean("isIccIntent", checkForIccIntent);
        GDMonitorFragmentImpl createGDMonitorFragmentImpl = GDMonitorFragmentImpl.createGDMonitorFragmentImpl(this, activity.getComponentName().getClassName());
        this.mFragmentImpl = createGDMonitorFragmentImpl;
        createGDMonitorFragmentImpl.onFragmentCreate();
        return checkForIccIntent;
    }
}
