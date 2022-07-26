package com.good.gd.webauth;

import android.net.Uri;
import com.good.gd.ndkproxy.ui.data.WebAuth;

/* loaded from: classes.dex */
public class GDWebAuth implements WebAuth {
    private final EidConfig eidConfig;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ WebAuthenticationSession dbjc;

        hbfhc(GDWebAuth gDWebAuth, WebAuthenticationSession webAuthenticationSession) {
            this.dbjc = webAuthenticationSession;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.dbjc.start();
        }
    }

    public GDWebAuth(EidConfig eidConfig) {
        this.eidConfig = eidConfig;
    }

    @Override // com.good.gd.ndkproxy.ui.data.WebAuth
    public void start(Listener listener) {
        listener.onStart();
        new Thread(new hbfhc(this, new WebAuthenticationSession(Uri.parse(this.eidConfig.getEidHostDiscovery()), Uri.parse(this.eidConfig.getRedirectUri()), this.eidConfig.getClientId(), this.eidConfig.getScope(), listener))).start();
    }
}
