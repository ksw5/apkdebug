package com.good.gd.profileoverride;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public class GDBISProfileOverrideAsyncCallback implements GDBISProfileOverrideCallback {
    private GDBISProfileOverrideCallback callback;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ int dbjc;
        final /* synthetic */ String qkduk;

        hbfhc(int i, String str) {
            this.dbjc = i;
            this.qkduk = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (GDBISProfileOverrideAsyncCallback.this.callback != null) {
                GDBISProfileOverrideAsyncCallback.this.callback.onProfileOverrideApplied(this.dbjc, this.qkduk);
            }
        }
    }

    public GDBISProfileOverrideAsyncCallback(GDBISProfileOverrideCallback gDBISProfileOverrideCallback) {
        this.callback = gDBISProfileOverrideCallback;
    }

    @Override // com.good.gd.profileoverride.GDBISProfileOverrideCallback
    public void onProfileOverrideApplied(int i, String str) {
        this.uiHandler.post(new hbfhc(i, str));
    }
}
