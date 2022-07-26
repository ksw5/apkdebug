package com.example.decompiledapk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentContainerView;
import com.example.decompiledapk.R;

/* loaded from: classes4.dex */
public abstract class ActivityMainBinding extends ViewDataBinding {
    public final FragmentContainerView navHostFragment;

    /* JADX INFO: Access modifiers changed from: protected */
    public ActivityMainBinding(Object _bindingComponent, View _root, int _localFieldCount, FragmentContainerView navHostFragment) {
        super(_bindingComponent, _root, _localFieldCount);
        this.navHostFragment = navHostFragment;
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater);
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, root, attachToRoot, component);
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater);
    }

    @Deprecated
    public static ActivityMainBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_main, null, false, component);
    }

    public static ActivityMainBinding bind(View view) {
        return bind(view);
    }

    @Deprecated
    public static ActivityMainBinding bind(View view, Object component) {
        return (ActivityMainBinding) bind(component, view, R.layout.activity_main);
    }
}
