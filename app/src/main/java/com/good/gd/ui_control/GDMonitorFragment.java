package com.good.gd.ui_control;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.good.gd.ndkproxy.GDLog;

/* loaded from: classes.dex */
public class GDMonitorFragment extends Fragment implements GDMonitorFragmentImplInterface {
    private GDMonitorFragmentImpl mFragmentImpl;

    public GDMonitorFragment() {
        GDLog.DBGPRINTF(14, "GDMonitorFragment() " + this + "\n");
    }

    public static GDMonitorFragment newInstance(boolean z) {
        GDMonitorFragment gDMonitorFragment = new GDMonitorFragment();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::newInstance()\n");
        Bundle bundle = new Bundle();
        bundle.putBoolean("shouldCallAuth", true);
        bundle.putBoolean("isIccIntent", z);
        gDMonitorFragment.setArguments(bundle);
        return gDMonitorFragment;
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public void fragmentStartActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i);
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public Activity getContainerActivity() {
        return getActivity();
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public Bundle getFragmentArguments() {
        return getArguments();
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mFragmentImpl.onFragmentActivityResult(i, i2, intent);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragmentImpl = GDMonitorFragmentImpl.createGDMonitorFragmentImpl(this, getContainerActivity().getComponentName().getClassName());
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onCreate() FragmentImpl =" + this.mFragmentImpl + "this = " + this + "\n");
        this.mFragmentImpl.onFragmentCreate();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onDestroy() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentDestroy();
        super.onDestroy();
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onDetach() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentDetach();
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onPause() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentPause();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onResume() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentResume();
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onStart() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentStart();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::onStop() FragmentImpl =" + this.mFragmentImpl + "\n");
        this.mFragmentImpl.onFragmentStop();
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public void setFragmentRetainInstance(boolean z) {
        setRetainInstance(z);
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImplInterface
    public boolean shouldIgnoreDetach() {
        Activity activity = getActivity();
        boolean z = activity == null || activity.isChangingConfigurations();
        GDLog.DBGPRINTF(14, "GDMonitorFragment::shouldIgnoreDetach() FragmentImpl =" + this.mFragmentImpl + "should ignore = " + z + "\n");
        return z;
    }
}
