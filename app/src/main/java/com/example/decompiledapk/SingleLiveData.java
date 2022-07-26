package com.example.decompiledapk;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\"\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0010\u0010\b\u001a\f\u0012\b\b\u0000\u0012\u0004\u0018\u00018\u00000\tH\u0016¨\u0006\n"}, d2 = {"Lcom/bold360/natwest/SingleLiveData;", "T", "Landroidx/lifecycle/MutableLiveData;", "()V", "observe", "", "owner", "Landroidx/lifecycle/LifecycleOwner;", "observer", "Landroidx/lifecycle/Observer;", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SingleLiveData<T> extends MutableLiveData<T> {
    @Override // androidx.lifecycle.LiveData
    public void observe(LifecycleOwner owner, final Observer<? super T> observer) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        super.observe(owner, new Observer() { // from class: com.bold360.natwest.SingleLiveData$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SingleLiveData.m290observe$lambda0(Observer.this, this, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: observe$lambda-0  reason: not valid java name */
    public static final void m290observe$lambda0(Observer observer, SingleLiveData this$0, Object t) {
        Intrinsics.checkNotNullParameter(observer, "$observer");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (t != null) {
            observer.onChanged(t);
            this$0.postValue(null);
        }
    }
}
