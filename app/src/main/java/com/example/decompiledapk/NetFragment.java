package com.example.decompiledapk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bold360.natwest.databinding.FragmentNetBinding;
import com.example.decompiledapk.databinding.FragmentNetBinding;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: NatFragment.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002'(B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0003J\b\u0010\"\u001a\u00020!H\u0016J\u001a\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010&H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\b\u0010\u0003\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR!\u0010\r\u001a\u00020\u000e8FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u0012\u0004\b\u000f\u0010\u0003\u001a\u0004\b\u0010\u0010\u0011R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006)"}, d2 = {"Lcom/bold360/natwest/NetFragment;", "Lcom/bold360/natwest/BoundFragment;", "Lcom/bold360/natwest/databinding/FragmentNetBinding;", "()V", "mLoadingView", "Landroid/view/View;", "mWebView", "Landroid/webkit/WebView;", "getMWebView$annotations", "getMWebView", "()Landroid/webkit/WebView;", "setMWebView", "(Landroid/webkit/WebView;)V", "netViewModel", "Lcom/bold360/natwest/NatViewModel;", "getNetViewModel$annotations", "getNetViewModel", "()Lcom/bold360/natwest/NatViewModel;", "netViewModel$delegate", "Lkotlin/Lazy;", "viewModelProvider", "Lkotlin/Function0;", "Landroidx/lifecycle/ViewModelProvider;", "getViewModelProvider", "()Lkotlin/jvm/functions/Function0;", "setViewModelProvider", "(Lkotlin/jvm/functions/Function0;)V", "fetchViewBinding", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "loadPage", "", "onDetach", "onViewCreated", "view", "savedInstanceState", "Landroid/os/Bundle;", "BoldPresenterWebClient", "Companion", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NetFragment extends BoundFragment<FragmentNetBinding> {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "NetFragment";
    private static final String webLoadFailure = "Failed to load resource";
    private static final String webSslError = "ssl error";
    private View mLoadingView;
    public WebView mWebView;
    private final Lazy netViewModel$delegate = LazyKt.lazy(new NetFragment$netViewModel$2(this));
    private Function0<? extends ViewModelProvider> viewModelProvider = new NetFragment$viewModelProvider$1(this);

    public static /* synthetic */ void getMWebView$annotations() {
    }

    public static /* synthetic */ void getNetViewModel$annotations() {
    }

    @Override // com.bold360.natwest.BoundFragment
    public FragmentNetBinding fetchViewBinding(LayoutInflater inflater, ViewGroup container) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        FragmentNetBinding inflate = FragmentNetBinding.inflate(inflater, container, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(inflater, container, false)");
        return inflate;
    }

    public final WebView getMWebView() {
        WebView webView = this.mWebView;
        if (webView != null) {
            return webView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        return null;
    }

    public final void setMWebView(WebView webView) {
        Intrinsics.checkNotNullParameter(webView, "<set-?>");
        this.mWebView = webView;
    }

    public final NatViewModel getNetViewModel() {
        return (NatViewModel) this.netViewModel$delegate.mo198getValue();
    }

    public final Function0<ViewModelProvider> getViewModelProvider() {
        return this.viewModelProvider;
    }

    public final void setViewModelProvider(Function0<? extends ViewModelProvider> function0) {
        Intrinsics.checkNotNullParameter(function0, "<set-?>");
        this.viewModelProvider = function0;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        loadPage();
    }

    private final void loadPage() {
        ProgressBar progressBar = getBinding().webLoadingView;
        Intrinsics.checkNotNullExpressionValue(progressBar, "binding.webLoadingView");
        this.mLoadingView = progressBar;
        WebView webView = getBinding().webContentView;
        Intrinsics.checkNotNullExpressionValue(webView, "binding.webContentView");
        setMWebView(webView);
        WebView $this$loadPage_u24lambda_u2d1 = getMWebView();
        $this$loadPage_u24lambda_u2d1.getSettings().setJavaScriptEnabled(true);
        $this$loadPage_u24lambda_u2d1.setWebViewClient(new BoldPresenterWebClient(this));
        getMWebView().setOnKeyListener(new View.OnKeyListener() { // from class: com.bold360.natwest.NetFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                boolean m285loadPage$lambda1$lambda0;
                m285loadPage$lambda1$lambda0 = NetFragment.m285loadPage$lambda1$lambda0(NetFragment.this, view, i, keyEvent);
                return m285loadPage$lambda1$lambda0;
            }
        });
        getNetViewModel().getUrl().observe(getViewLifecycleOwner(), new Observer() { // from class: com.bold360.natwest.NetFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NetFragment.m286loadPage$lambda4(NetFragment.this, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: loadPage$lambda-1$lambda-0  reason: not valid java name */
    public static final boolean m285loadPage$lambda1$lambda0(NetFragment this$0, View v, int keyCode, KeyEvent event) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (event.getAction() == 0 && keyCode == 4) {
            Uri uri = Uri.parse(this$0.getMWebView().getUrl());
            String query = uri.getQuery();
            if (query == null) {
                query = "";
            }
            Log.i("Log BACK", String.valueOf(this$0.getMWebView().getUrl()));
            if (this$0.getMWebView().canGoBack() && ((StringsKt.contains$default((CharSequence) query, (CharSequence) "UserRole=None", false, 2, (Object) null) && uri.getQueryParameterNames().size() > 1) || Intrinsics.areEqual(Intrinsics.stringPlus("UserRole=None", "/"), query) || StringsKt.contains$default((CharSequence) query, (CharSequence) "UserRole", false, 2, (Object) null))) {
                this$0.getMWebView().goBack();
            } else {
                FragmentActivity activity = this$0.getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: loadPage$lambda-4  reason: not valid java name */
    public static final void m286loadPage$lambda4(NetFragment this$0, String it) {
        Unit unit;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (it == null) {
            unit = null;
        } else {
            this$0.getMWebView().loadUrl(it);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this$0.getNetViewModel().postEvent(new ErrorEvent(ErrorEvent.EmptyResponse, webLoadFailure, null, 4, null));
            this$0.popBack();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        getMWebView().stopLoading();
    }

    /* compiled from: NatFragment.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0017J(\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0016J \u0010\u0015\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0017J \u0010\u0018\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u001bH\u0016J\u001c\u0010\u001c\u001a\u00020\u001d2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u001e"}, d2 = {"Lcom/bold360/natwest/NetFragment$BoldPresenterWebClient;", "Landroid/webkit/WebViewClient;", "(Lcom/bold360/natwest/NetFragment;)V", "handleWebError", "", "errorCode", "", "description", "failingUrl", UtilsKt.Data, "", "onPageFinished", "view", "Landroid/webkit/WebView;", "url", "onReceivedError", "request", "Landroid/webkit/WebResourceRequest;", UtilsKt.Error, "Landroid/webkit/WebResourceError;", "", "onReceivedHttpError", "errorResponse", "Landroid/webkit/WebResourceResponse;", "onReceivedSslError", "handler", "Landroid/webkit/SslErrorHandler;", "Landroid/net/http/SslError;", "shouldOverrideUrlLoading", "", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public final class BoldPresenterWebClient extends WebViewClient {
        final /* synthetic */ NetFragment this$0;

        public BoldPresenterWebClient(NetFragment this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Context $this$shouldOverrideUrlLoading_u24lambda_u2d3_u24lambda_u2d2;
            Log.i("Log URL UPDATE", String.valueOf(url));
            if (url != null) {
                if ((CollectionsKt.contains(Urls.Companion.getInternalWebAuthorities(), Uri.parse(url).getAuthority()) ? url : null) != null) {
                    this.this$0.getNetViewModel().updateUrl(url);
                    return false;
                }
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            if (view != null && ($this$shouldOverrideUrlLoading_u24lambda_u2d3_u24lambda_u2d2 = view.getContext()) != null) {
                ContextCompat.startActivity($this$shouldOverrideUrlLoading_u24lambda_u2d3_u24lambda_u2d2, intent, null);
                return true;
            }
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(url, "url");
            View view2 = this.this$0.mLoadingView;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mLoadingView");
                view2 = null;
            }
            view2.setVisibility(8);
            this.this$0.getNetViewModel().postEvent(new BoldEvent(UtilsKt.Data, "Page loaded"));
        }

        static /* synthetic */ void handleWebError$default(BoldPresenterWebClient boldPresenterWebClient, String str, String str2, String str3, Object obj, int i, Object obj2) {
            if ((i & 8) != 0) {
                obj = null;
            }
            boldPresenterWebClient.handleWebError(str, str2, str3, obj);
        }

        private final void handleWebError(String errorCode, String description, String failingUrl, Object data) {
            boolean z = false;
            if (failingUrl != null && !StringsKt.contains$default((CharSequence) failingUrl, (CharSequence) "favicon", false, 2, (Object) null)) {
                z = true;
            }
            if (z) {
                this.this$0.getNetViewModel().postEvent(new ErrorEvent(errorCode, "Error description: " + description + ", Failing url: " + ((Object) failingUrl), data));
                this.this$0.popBack();
                return;
            }
            Log.e(NetFragment.TAG, "WebPage Error: " + errorCode + ", description: " + description + ", url info: " + ((Object) failingUrl));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(error, "error");
            super.onReceivedError(view, request, error);
            String valueOf = String.valueOf(error.getErrorCode());
            String it = error.getDescription().toString();
            String it2 = null;
            if (!(it.length() > 0)) {
                it = null;
            }
            if (it == null) {
                it = NetFragment.webLoadFailure;
            }
            Uri url = request.getUrl();
            if (url != null) {
                it2 = url.toString();
            }
            handleWebError(valueOf, it, it2, error);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(errorResponse, "errorResponse");
            super.onReceivedHttpError(view, request, errorResponse);
            String valueOf = String.valueOf(errorResponse.getStatusCode());
            String it = errorResponse.getReasonPhrase();
            Intrinsics.checkNotNullExpressionValue(it, "it");
            String it2 = null;
            if (!(it.length() > 0)) {
                it = null;
            }
            if (it == null) {
                it = NetFragment.webLoadFailure;
            }
            Uri url = request.getUrl();
            if (url != null) {
                it2 = url.toString();
            }
            handleWebError(valueOf, it, it2, errorResponse);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(handler, "handler");
            Intrinsics.checkNotNullParameter(error, "error");
            super.onReceivedSslError(view, handler, error);
            handleWebError(ErrorEvent.ConnectionException, NetFragment.webSslError, error.getUrl(), error);
            handler.cancel();
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(description, "description");
            Intrinsics.checkNotNullParameter(failingUrl, "failingUrl");
            super.onReceivedError(view, errorCode, description, failingUrl);
            String valueOf = String.valueOf(errorCode);
            String str = description.length() > 0 ? description : null;
            if (str == null) {
                str = NetFragment.webLoadFailure;
            }
            handleWebError$default(this, valueOf, str, failingUrl, null, 8, null);
        }
    }

    /* compiled from: NatFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/bold360/natwest/NetFragment$Companion;", "", "()V", "TAG", "", "webLoadFailure", "webSslError", "app_debug"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
