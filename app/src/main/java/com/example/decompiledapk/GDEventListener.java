package com.example.decompiledapk;

import com.good.gd.GDStateListener;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GDEventListener.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0007\b\u0000\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u001c\u0010\u0006\u001a\u00020\u00042\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\u001c\u0010\f\u001a\u00020\u00042\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016¨\u0006\u0011"}, d2 = {"Lcom/bold360/natwest/GDEventListener;", "Lcom/good/gd/GDStateListener;", "()V", "onAuthorized", "", "onLocked", "onUpdateConfig", "settings", "", "", "", "onUpdateEntitlements", "onUpdatePolicy", "policyValues", "onUpdateServices", "onWiped", "Companion", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class GDEventListener implements GDStateListener {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = GDEventListener.class.getSimpleName();

    @Override // com.good.gd.GDStateListener
    public void onAuthorized() {
    }

    @Override // com.good.gd.GDStateListener
    public void onLocked() {
    }

    @Override
    public void onUpdateConfig(Map<String, Object> settings) {
        Intrinsics.checkNotNullParameter(settings, "settings");
    }

    @Override // com.good.gd.GDStateListener
    public void onWiped() {
    }


    @Override // com.good.gd.GDStateListener
    public void onUpdatePolicy(Map<String, Object> policyValues) {
        Intrinsics.checkNotNullParameter(policyValues, "policyValues");
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateServices() {
    }

    @Override // com.good.gd.GDStateListener
    public void onUpdateEntitlements() {
    }


    /* compiled from: GDEventListener.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/bold360/natwest/GDEventListener$Companion;", "", "()V", "TAG", "", "kotlin.jvm.PlatformType", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
