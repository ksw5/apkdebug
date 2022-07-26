package com.example.decompiledapk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.bold360.natwest.R;

/* loaded from: classes4.dex */
public abstract class FragmentNetBinding extends ViewDataBinding {
    public final WebView webContentView;
    public final ProgressBar webLoadingView;

    /* JADX INFO: Access modifiers changed from: protected */
    public FragmentNetBinding(Object _bindingComponent, View _root, int _localFieldCount, WebView webContentView, ProgressBar webLoadingView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.webContentView = webContentView;
        this.webLoadingView = webLoadingView;
    }

    public static FragmentNetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentNetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_net, root, attachToRoot, component);
    }

    public static FragmentNetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNetBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentNetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_net, null, false, component);
    }

    public static FragmentNetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentNetBinding bind(View view, Object component) {
        return (FragmentNetBinding) bind(component, view, R.layout.fragment_net);
    }
}
