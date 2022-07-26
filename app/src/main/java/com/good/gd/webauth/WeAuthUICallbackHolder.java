package com.good.gd.webauth;

import com.good.gd.ndkproxy.ui.data.WebAuthUI;

/* loaded from: classes.dex */
public class WeAuthUICallbackHolder {
    private static WebAuthUI.UrlCallback urlCallback;

    public static WebAuthUI.UrlCallback getUrlCallback() {
        return urlCallback;
    }

    public static void setUrlCallback(WebAuthUI.UrlCallback urlCallback2) {
        urlCallback = urlCallback2;
    }
}
