package com.example.decompiledapk;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: NatFragment.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/bold360/natwest/NatViewModel;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
final class NetFragment$netViewModel$2 extends Lambda implements Function0<NatViewModel> {
    final /* synthetic */ NetFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetFragment$netViewModel$2(NetFragment netFragment) {
        super(0);
        this.this$0 = netFragment;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final NatViewModel mo2032invoke() {
        return (NatViewModel) this.this$0.getViewModelProvider().mo2032invoke().get(NatViewModel.class);
    }
}
