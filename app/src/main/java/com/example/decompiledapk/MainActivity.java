package com.example.decompiledapk;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelLazy;
import com.good.gd.GDAndroid;
import com.good.gd.GDStateListener;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u000bH\u0016J\u001e\u0010\u0010\u001a\u00020\u000b2\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0012H\u0016J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\u001e\u0010\u0016\u001a\u00020\u000b2\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0012H\u0016J\b\u0010\u0017\u001a\u00020\u000bH\u0016J\b\u0010\u0018\u001a\u00020\u000bH\u0016R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Lcom/bold360/natwest/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/good/gd/GDStateListener;", "()V", "model", "Lcom/bold360/natwest/NatViewModel;", "getModel", "()Lcom/bold360/natwest/NatViewModel;", "model$delegate", "Lkotlin/Lazy;", "onAuthorized", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLocked", "onUpdateConfig", "p0", "", "", "", "onUpdateEntitlements", "onUpdatePolicy", "onUpdateServices", "onWiped", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainActivity extends AppCompatActivity implements GDStateListener {
    private final Lazy model$delegate;

    public MainActivity() {
        MainActivity $this$viewModels_u24default$iv = this;
        Function0 factoryPromise$iv = new MainActivity$special$$inlined$viewModels$default$1($this$viewModels_u24default$iv);
        this.model$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(NatViewModel.class), new MainActivity$special$$inlined$viewModels$default$2($this$viewModels_u24default$iv), factoryPromise$iv);
    }

    private final NatViewModel getModel() {
        return (NatViewModel) this.model$delegate.mo198getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        GDAndroid.getInstance().activityInit(this);
    }

    @Override // com.good.gd.GDStateListener
    public void onAuthorized() {
        getModel().updateUrl(Urls.Companion.getPageUrl());
        getModel().getEvent().observe(this, MainActivity$$ExternalSyntheticLambda0.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onAuthorized$lambda-3  reason: not valid java name */
    public static final void m282onAuthorized$lambda3(BoldEvent event) {
        if (event != null) {
            if ((Intrinsics.areEqual(event.getType(), UtilsKt.Error) ? event : null) != null) {
                Log.e("Web Event", event.toString());
            }
            Log.i("Web Event", event.toString());
        }
    }

    @Override // com.good.gd.GDStateListener
    public void onLocked() {
    }

    @Override // com.good.gd.GDStateListener
    public void onWiped() {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateConfig(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdatePolicy(Map<String, Object> map) {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateServices() {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateEntitlements() {
    }
}
