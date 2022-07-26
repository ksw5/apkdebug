package com.example.decompiledapk;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lcom/bold360/natwest/EventsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_event", "Lcom/bold360/natwest/SingleLiveData;", "Lcom/bold360/natwest/BoldEvent;", NotificationCompat.CATEGORY_EVENT, "getEvent", "()Lcom/bold360/natwest/SingleLiveData;", "postEvent", "", "boldEvent", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class EventsViewModel extends ViewModel {
    private final SingleLiveData<BoldEvent> _event = new SingleLiveData<>();

    public final SingleLiveData<BoldEvent> getEvent() {
        return this._event;
    }

    public final void postEvent(BoldEvent boldEvent) {
        Intrinsics.checkNotNullParameter(boldEvent, "boldEvent");
        this._event.postValue(boldEvent);
    }
}
