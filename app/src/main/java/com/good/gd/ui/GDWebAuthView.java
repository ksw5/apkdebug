package com.good.gd.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.WebAuthUI;
import com.good.gd.resources.R;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class GDWebAuthView extends GDView {
    private final ProgressBar progressBar;
    private final WebAuthUI ui;
    private final WebView webView;

    /* loaded from: classes.dex */
    class hbfhc extends WebChromeClient {
        hbfhc() {
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            GDWebAuthView.this.progressBar.setProgress(i);
        }
    }

    /* loaded from: classes.dex */
    class yfdke extends WebViewClient {
        final /* synthetic */ WebAuthUI dbjc;

        yfdke(WebAuthUI webAuthUI) {
            this.dbjc = webAuthUI;
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            GDWebAuthView.this.progressBar.setVisibility(8);
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            GDLog.DBGPRINTF(14, "GDWebAuthView::onPageStarted " + str + "\n");
            GDWebAuthView.this.progressBar.setVisibility(0);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            GDLog.DBGPRINTF(14, "GDWebAuthView::onReceivedError code:" + webResourceError.getErrorCode() + " description: " + ((Object) webResourceError.getDescription()) + "\n");
            this.dbjc.onError(webResourceError.getErrorCode());
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            String uri = webResourceRequest.getUrl().toString();
            if (uri.startsWith(this.dbjc.getCallbackUrl())) {
                this.dbjc.onCallbackUrl(uri);
                return true;
            }
            return false;
        }
    }

    public GDWebAuthView(Context context, ViewInteractor viewInteractor, WebAuthUI webAuthUI, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        GDLog.DBGPRINTF(14, "GDWebAuthView::GDWebAuthView\n");
        this.ui = webAuthUI;
        inflateLayout(R.layout.bbd_web_auth_view, this);
        WebView webView = (WebView) findViewById(R.id.bbd_web_auth_view_UI);
        this.webView = webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(2);
        settings.setSafeBrowsingEnabled(true);
        webView.setImportantForAutofill(2);
        webView.setLayerType(2, null);
        webView.clearCache(true);
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.bbd_web_auth_progress_UI);
        this.progressBar = progressBar;
        progressBar.setProgress(1);
        progressBar.setMax(100);
        webView.setWebChromeClient(new hbfhc());
        webView.setWebViewClient(new yfdke(webAuthUI));
        webView.loadUrl(webAuthUI.getUrlToOpen());
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            this.ui.close();
        }
    }
}
