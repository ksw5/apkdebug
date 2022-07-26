package com.example.decompiledapk;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NatFragment.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelProvider;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
final class NetFragment$viewModelProvider$1 extends Lambda implements Function0<ViewModelProvider> {
    final /* synthetic */ NetFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetFragment$viewModelProvider$1(NetFragment netFragment) {
        super(0);
        this.this$0 = netFragment;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final ViewModelProvider mo2032invoke() {
        ViewModelProvider of = ViewModelProviders.of(this.this$0.requireActivity());
        Intrinsics.checkNotNullExpressionValue(of, "of(requireActivity())");
        return of;
    }
}
