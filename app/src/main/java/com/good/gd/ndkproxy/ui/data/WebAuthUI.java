package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.CoreUI;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ui.GDWebAuthView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class WebAuthUI extends BaseUI {
    private final UrlCallback callback;
    private final String callbackUrl;
    private final String urlToOpen;

    /* loaded from: classes.dex */
    public interface UrlCallback {
        void onCallbackUrl(String str);

        void onError(int i);
    }

    public WebAuthUI(long j, String str, String str2, UrlCallback urlCallback) {
        super(BBUIType.UI_WEB_AUTH, j);
        this.urlToOpen = str;
        this.callbackUrl = str2;
        this.callback = urlCallback;
    }

    public void close() {
        CoreUI.closeUI(getCoreHandle());
        CoreUI.closeActivationWebAuthLoadingUI();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDWebAuthView(context, viewInteractor, this, viewCustomizer);
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public String getUrlToOpen() {
        return this.urlToOpen;
    }

    public void onCallbackUrl(String str) {
        UrlCallback urlCallback = this.callback;
        if (urlCallback != null) {
            urlCallback.onCallbackUrl(str);
        }
        close();
    }

    public void onError(int i) {
        UrlCallback urlCallback = this.callback;
        if (urlCallback != null) {
            urlCallback.onError(i);
        }
        close();
    }
}
