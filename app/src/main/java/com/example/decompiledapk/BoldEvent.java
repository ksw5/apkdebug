package com.example.decompiledapk;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\b\u0010\f\u001a\u00020\u0003H\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/bold360/natwest/BoldEvent;", "", "type", "", UtilsKt.Data, "(Ljava/lang/String;Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "setData", "(Ljava/lang/Object;)V", "getType", "()Ljava/lang/String;", "toString", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public class BoldEvent {
    private Object data;
    private final String type;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BoldEvent(String type) {
        this(type, null, 2, null);
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public BoldEvent(String type, Object data) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.data = data;
    }

    public /* synthetic */ BoldEvent(String str, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : obj);
    }

    public final Object getData() {
        return this.data;
    }

    public final String getType() {
        return this.type;
    }

    public final void setData(Object obj) {
        this.data = obj;
    }

    public String toString() {
        return "Event type: " + this.type + ", Event data: " + this.data;
    }
}
