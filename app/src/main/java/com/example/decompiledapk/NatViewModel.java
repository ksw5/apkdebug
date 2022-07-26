package com.example.decompiledapk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NatFragment.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lcom/bold360/natwest/NatViewModel;", "Lcom/bold360/natwest/EventsViewModel;", "()V", "_url", "Landroidx/lifecycle/MutableLiveData;", "", "url", "Landroidx/lifecycle/LiveData;", "getUrl", "()Landroidx/lifecycle/LiveData;", "updateUrl", "", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NatViewModel extends EventsViewModel {
    private MutableLiveData<String> _url;
    private final LiveData<String> url;

    public NatViewModel() {
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
        this._url = mutableLiveData;
        this.url = mutableLiveData;
    }

    public final LiveData<String> getUrl() {
        return this.url;
    }

    public final void updateUrl(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        this._url.postValue(url);
    }
}
