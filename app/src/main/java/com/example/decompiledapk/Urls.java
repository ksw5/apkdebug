package com.example.decompiledapk;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/bold360/natwest/Urls;", "", "()V", "Companion", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class Urls {
    public static final Companion Companion = new Companion(null);
    private static final String pageUrl = "https://myknowledgechat.rbspeople.com?UserRole=None/";
    private static final List<String> InternalWebAuthorities = CollectionsKt.listOf((Object[]) new String[]{"fsso.rbspeople.com", "myknowledgebusiness.rbspeople.com", "myknowledgechat.rbspeople.com", "myknowledgewealth.rbspeople.com", "myknowledgefhs.rbspeople.com", "myknowledgeced.rbspeople.com", "rbs.nanorep.co"});

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/bold360/natwest/Urls$Companion;", "", "()V", "InternalWebAuthorities", "", "", "getInternalWebAuthorities", "()Ljava/util/List;", "pageUrl", "getPageUrl", "()Ljava/lang/String;", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getPageUrl() {
            return Urls.pageUrl;
        }

        public final List<String> getInternalWebAuthorities() {
            return Urls.InternalWebAuthorities;
        }
    }
}
