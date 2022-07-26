package com.example.decompiledapk;

import com.good.gd.webauth.AuthCodeResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\b\u0016\u0018\u0000 \f2\u00020\u0001:\u0001\fB)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/bold360/natwest/ErrorEvent;", "Lcom/bold360/natwest/BoldEvent;", AuthCodeResponse.AUTH_CODE_KEY, "", "description", "extraData", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "getCode", "()Ljava/lang/String;", "getExtraData", "()Ljava/lang/Object;", "Companion", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public class ErrorEvent extends BoldEvent {
    public static final Companion Companion = new Companion(null);
    public static final String ConnectionException = "Connection exception";
    public static final String EmptyResponse = "received empty response";
    private final String code;
    private final String description;
    private final Object extraData;

    public ErrorEvent() {
        this(null, null, null, 7, null);
    }

    /* compiled from: Utils.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lcom/bold360/natwest/ErrorEvent$Companion;", "", "()V", "ConnectionException", "", "EmptyResponse", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ErrorEvent(String code, String description, Object extraData) {
        super(UtilsKt.Error, description);
        this.code = code;
        this.description = description;
        this.extraData = extraData;
    }

    public /* synthetic */ ErrorEvent(String str, String str2, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : obj);
    }

    public final String getCode() {
        return this.code;
    }

    public final Object getExtraData() {
        return this.extraData;
    }
}
